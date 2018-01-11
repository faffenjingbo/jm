/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.components.visualizers;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A extended class of DefaultMutableTreeNode except that it also attached XML
 * node and convert XML document into DefaultMutableTreeNode.
 *
 */
public class XMLDefaultMutableTreeNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 240L;

    private static final Logger log = LoggingManager.getLoggerForClass();
    private transient Node xmlNode;

    /**
     * @deprecated only for use by test code
     */
    @Deprecated
    public XMLDefaultMutableTreeNode(){
        log.warn("Constructor only intended for use in testing"); // $NON-NLS-1$
    }

    public XMLDefaultMutableTreeNode(Node root) throws SAXException {
        super(root.getNodeName());
        initAttributeNode(root, this);
        initRoot(root);

    }

    public XMLDefaultMutableTreeNode(String name, Node xmlNode) {
        super(name);
        this.xmlNode = xmlNode;

    }

    /**
     * init root
     *
     * @param xmlRoot
     * @throws org.xml.sax.SAXException
     */
    private void initRoot(Node xmlRoot) throws SAXException {

        NodeList childNodes = xmlRoot.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            initNode(childNode, this);
        }

    }

    /**
     * init node
     *
     * @param node
     * @param mTreeNode
     * @throws org.xml.sax.SAXException
     */
    private void initNode(Node node, org.apache.components.visualizers.XMLDefaultMutableTreeNode mTreeNode) throws SAXException {

        switch (node.getNodeType()) {
        case Node.ELEMENT_NODE:
            initElementNode(node, mTreeNode);
            break;

        case Node.TEXT_NODE:
            initTextNode((Text) node, mTreeNode);
            break;

        case Node.CDATA_SECTION_NODE:
            initCDATASectionNode((CDATASection) node, mTreeNode);
            break;
        case Node.COMMENT_NODE:
            initCommentNode((Comment) node, mTreeNode);
            break;

        default:
            // if other node type, we will just skip it
            break;

        }

    }

    /**
     * init element node
     *
     * @param node
     * @param mTreeNode
     * @throws org.xml.sax.SAXException
     */
    private void initElementNode(Node node, DefaultMutableTreeNode mTreeNode) throws SAXException {
        String nodeName = node.getNodeName();

        NodeList childNodes = node.getChildNodes();
        org.apache.components.visualizers.XMLDefaultMutableTreeNode childTreeNode = new org.apache.components.visualizers.XMLDefaultMutableTreeNode(nodeName, node);

        mTreeNode.add(childTreeNode);
        initAttributeNode(node, childTreeNode);
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            initNode(childNode, childTreeNode);
        }

    }

    /**
     * init attribute node
     *
     * @param node
     * @param mTreeNode
     * @throws org.xml.sax.SAXException
     */
    private void initAttributeNode(Node node, DefaultMutableTreeNode mTreeNode) throws SAXException {
        NamedNodeMap nm = node.getAttributes();
        for (int i = 0; i < nm.getLength(); i++) {
            Attr nmNode = (Attr) nm.item(i);
            String value = nmNode.getName() + " = \"" + nmNode.getValue() + "\""; // $NON-NLS-1$ $NON-NLS-2$
            org.apache.jmeter.visualizers.XMLDefaultMutableTreeNode attributeNode = new org.apache.jmeter.visualizers.XMLDefaultMutableTreeNode(value, nmNode);
            mTreeNode.add(attributeNode);

        }
    }

    /**
     * init comment Node
     *
     * @param node
     * @param mTreeNode
     * @throws org.xml.sax.SAXException
     */
    private void initCommentNode(Comment node, DefaultMutableTreeNode mTreeNode) throws SAXException {
        String data = node.getData();
        if (data != null && data.length() > 0) {
            String value = "<!--" + node.getData() + "-->"; // $NON-NLS-1$ $NON-NLS-2$
            org.apache.jmeter.visualizers.XMLDefaultMutableTreeNode commentNode = new org.apache.jmeter.visualizers.XMLDefaultMutableTreeNode(value, node);
            mTreeNode.add(commentNode);
        }
    }

    /**
     * init CDATASection Node
     *
     * @param node
     * @param mTreeNode
     * @throws org.xml.sax.SAXException
     */
    private void initCDATASectionNode(CDATASection node, DefaultMutableTreeNode mTreeNode) throws SAXException {
        String data = node.getData();
        if (data != null && data.length() > 0) {
            String value = "<!-[CDATA" + node.getData() + "]]>"; // $NON-NLS-1$ $NON-NLS-2$
            org.apache.jmeter.visualizers.XMLDefaultMutableTreeNode commentNode = new org.apache.jmeter.visualizers.XMLDefaultMutableTreeNode(value, node);
            mTreeNode.add(commentNode);
        }
    }

    /**
     * init the TextNode
     *
     * @param node
     * @param mTreeNode
     * @throws org.xml.sax.SAXException
     */
    private void initTextNode(Text node, DefaultMutableTreeNode mTreeNode) throws SAXException {
        String text = node.getNodeValue().trim();
        if (text != null && text.length() > 0) {
            org.apache.jmeter.visualizers.XMLDefaultMutableTreeNode textNode = new org.apache.jmeter.visualizers.XMLDefaultMutableTreeNode(text, node);
            mTreeNode.add(textNode);
        }
    }

    /**
     * get the xml node
     *
     * @return the XML node
     */
    public Node getXMLNode() {
        return xmlNode;
    }
}
