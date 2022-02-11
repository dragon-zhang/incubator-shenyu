/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.shenyu.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.lang.management.ManagementFactory;
import java.util.Set;

/**
 * get the port number exposed by the current tomcat
 */
public class PortUtils {

    /**
     * logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PortUtils.class);

    private static int tomcatPort = -1;

    /**
     * get the current tomcat port number
     */
    public static Integer getPort() throws MalformedObjectNameException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException {
        if (tomcatPort != -1) {
            return tomcatPort;
        }
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = mBeanServer.queryNames(new ObjectName("*:type=Connector,*"), null);
        if (CollectionUtils.isNotEmpty(objectNames)) {
            //fixme When there are many instances of external tomcat, there may be a problem here
            for (ObjectName objectName : objectNames) {
                String protocol = String.valueOf(mBeanServer.getAttribute(objectName, "protocol"));
                String port = String.valueOf(mBeanServer.getAttribute(objectName, "port"));
                // The property name is HTTP1.1, org.apache.coyote.http11.Http11NioProtocol under linux
                if ("HTTP/1.1".equals(protocol) || "org.apache.coyote.http11.Http11NioProtocol".equals(protocol)) {
                    return tomcatPort = Integer.parseInt(port);
                }
            }
        }
        throw new IllegalStateException("failed to get the HTTP port of the current tomcat");
    }

}
