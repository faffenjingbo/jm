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

package org.apache.components.visualizers.backend.graphite;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.jmeter.visualizers.backend.graphite.SocketConnectionInfos;
import org.apache.jmeter.visualizers.backend.graphite.SocketOutputStream;
import org.apache.jmeter.visualizers.backend.graphite.SocketOutputStreamPoolFactory;

import java.util.concurrent.TimeUnit;

/**
 * Base class for {@link org.apache.jmeter.visualizers.backend.graphite.GraphiteMetricsSender}
 * @since 2.13
 */
abstract class AbstractGraphiteMetricsSender implements org.apache.components.visualizers.backend.graphite.GraphiteMetricsSender {

    /**
     * Create a new keyed pool of {@link org.apache.jmeter.visualizers.backend.graphite.SocketOutputStream}s using a
     * {@link org.apache.jmeter.visualizers.backend.graphite.SocketOutputStreamPoolFactory}. The keys for the pool are
     * {@link org.apache.jmeter.visualizers.backend.graphite.SocketConnectionInfos} instances.
     *
     * @return GenericKeyedObjectPool the newly generated pool
     */
    protected GenericKeyedObjectPool<SocketConnectionInfos, SocketOutputStream> createSocketOutputStreamPool() {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        config.setMaxTotalPerKey(-1);
        config.setMaxTotal(-1);
        config.setMaxIdlePerKey(-1);
        config.setMinEvictableIdleTimeMillis(TimeUnit.MINUTES.toMillis(3));
        config.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES.toMillis(3));

        return new GenericKeyedObjectPool<>(
                new SocketOutputStreamPoolFactory(SOCKET_CONNECT_TIMEOUT_MS, SOCKET_TIMEOUT), config);
    }
    
    /**
     * Replaces Graphite reserved chars:
     * <ul>
     * <li>' ' by '-'</li>
     * <li>'\\' by '-'</li>
     * <li>'.' by '_'</li>
     * </ul>
     * 
     * @param s
     *            text to be sanitized
     * @return the sanitized text
     */
    static String sanitizeString(String s) {
        // String#replace uses regexp
        return StringUtils.replaceChars(s, "\\ .", "--_");
    }    
}
