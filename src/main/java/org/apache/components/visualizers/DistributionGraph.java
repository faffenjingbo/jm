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

import org.apache.jmeter.samplers.Clearable;
import org.apache.jmeter.visualizers.SamplingStatCalculator;
import org.apache.jorphan.math.NumberComparator;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

// import org.apache.jorphan.logging.LoggingManager;

/**
 * New graph for drawing distribution graph of the results. It is intended as a
 * way to view the data after the stress has been performed. Although it can be
 * used at runtime, it is not recommended, since it is rather intensive. The
 * graph will draw a red line at 90% and an orange line at 50%. I like
 * distribution graphs because they allow me to see how the data clumps. In
 * general, the data will tend to clump in predictable ways when the application
 * is well designed and implemented. Data that generates erratic graphs are
 * generally not desirable.
 *
 */
public class DistributionGraph extends JComponent implements Scrollable, Clearable {

    private static final long serialVersionUID = 240L;

    private SamplingStatCalculator model;

    private static final int X_BORDER = 30;

    /**
     * Constructor for the Graph object.
     */
    public DistributionGraph() {
        init();
    }

    /**
     * Constructor for the Graph object.
     * @param model The container for the aggregated sample data
     */
    public DistributionGraph(SamplingStatCalculator model) {
        this();
        setModel(model);
    }

    private void init() {// called from ctor, so must not be overridable
        repaint();
    }

    /**
     * Gets the ScrollableTracksViewportWidth attribute of the Graph object.
     *
     * @return the ScrollableTracksViewportWidth value
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    /**
     * Gets the ScrollableTracksViewportHeight attribute of the Graph object.
     *
     * @return the ScrollableTracksViewportHeight value
     */
    @Override
    public boolean getScrollableTracksViewportHeight() {
        return true;
    }

    /**
     * Sets the Model attribute of the Graph object.
     */
    private void setModel(Object model) {
        this.model = (SamplingStatCalculator) model;
        repaint();
    }

    /**
     * Gets the PreferredScrollableViewportSize attribute of the Graph object.
     *
     * @return the PreferredScrollableViewportSize value
     */
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return this.getPreferredSize();
    }

    /**
     * Gets the ScrollableUnitIncrement attribute of the Graph object.
     *
     * @return the ScrollableUnitIncrement value
     */
    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 5;
    }

    /**
     * Gets the ScrollableBlockIncrement attribute of the Graph object.
     *
     * @return the ScrollableBlockIncrement value
     */
    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return (int) (visibleRect.width * .9);
    }

    /**
     * Clears this graph.
     */
    @Override
    public void clearData() {
        model.clear();
    }

    /**
     * Method is responsible for calling drawSample and updating the graph.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        final SamplingStatCalculator m = this.model;
        synchronized (m) {
            drawSample(m, g);
        }
    }

    private void drawSample(SamplingStatCalculator pModel, Graphics g) {
        int width = getWidth();
        double height = getHeight() - 1.0;

        // first lets draw the grid
        for (int y = 0; y < 4; y++) {
            int q1 = (int) (height - (height * 0.25 * y));
            g.setColor(Color.lightGray);
            g.drawLine(X_BORDER, q1, width, q1);
            g.setColor(Color.black);
            g.drawString(String.valueOf((25 * y) + "%"), 0, q1);
        }
        g.setColor(Color.black);
        // draw the X axis
        g.drawLine(X_BORDER, (int) height, width, (int) height);
        // draw the Y axis
        g.drawLine(X_BORDER, 0, X_BORDER, (int) height);
        // the test plan has to have more than 200 samples
        // for it to generate half way decent distribution
        // graph. the larger the sample, the better the
        // results.
        if (pModel != null && pModel.getCount() > 50) {
            // now draw the bar chart
            Number ninety = pModel.getPercentPoint(0.90);
            Number fifty = pModel.getPercentPoint(0.50);

            long total = pModel.getCount();
            Collection<Number[]> values = pModel.getDistribution().values();
            Number[][] objval = values.toArray(new Number[values.size()][]);
            // we sort the objects
            Arrays.sort(objval, new NumberComparator());
            int len = objval.length;
            for (int count = 0; count < len; count++) {
                // calculate the height
                Number[] num = objval[count];
                double iper = (double) num[1].intValue() / (double) total;
                double iheight = height * iper;
                // if the height is less than one, we set it
                // to one pixel
                if (iheight < 1) {
                    iheight = 1.0;
                }
                int ix = (count * 4) + X_BORDER + 5;
                int dheight = (int) (height - iheight);
                g.setColor(Color.blue);
                g.drawLine(ix - 1, (int) height, ix - 1, dheight);
                g.drawLine(ix, (int) height, ix, dheight);
                g.setColor(Color.black);
                // draw a red line for 90% point
                if (num[0].longValue() == ninety.longValue()) {
                    g.setColor(Color.red);
                    g.drawLine(ix, (int) height, ix, 55);
                    g.drawLine(ix, 35, ix, 0);
                    g.drawString("90%", ix - 30, 20);
                    g.drawString(String.valueOf(num[0].longValue()), ix + 8, 20);
                }
                // draw an orange line for 50% point
                if (num[0].longValue() == fifty.longValue()) {
                    g.setColor(Color.orange);
                    g.drawLine(ix, (int) height, ix, 30);
                    g.drawString("50%", ix - 30, 50);
                    g.drawString(String.valueOf(num[0].longValue()), ix + 8, 50);
                }
            }
        }
    }
}
