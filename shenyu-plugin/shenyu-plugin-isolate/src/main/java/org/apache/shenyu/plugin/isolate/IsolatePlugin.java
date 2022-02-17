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

package org.apache.shenyu.plugin.isolate;

import org.apache.shenyu.common.enums.PluginEnum;
import org.apache.shenyu.plugin.api.ShenyuPlugin;
import org.apache.shenyu.plugin.api.ShenyuPluginChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * With the increasing application of accessing ShenYu, hot applications
 * and non hot applications will interact with each other, resulting
 * in the problem of application avalanche.
 *
 * <p>{@code HystrixPlugin}、{@code Resilience4JPlugin} and {@code SentinelPlugin},
 * these plugins do not distinguish traffic, the traffic of important applications
 * can be affected by the traffic of unimportant applications.
 *
 * <p>To solve this problem, IsolatePlugin is introduced to isolate
 * {@code shenyu-bootstrap} resources(such as memory, threads, etc).
 * Only thread pool resource is supported now.
 */
public class IsolatePlugin implements ShenyuPlugin {
    @Override
    public Mono<Void> execute(final ServerWebExchange exchange, final ShenyuPluginChain chain) {
        //todo
        return null;
    }

    @Override
    public int getOrder() {
        return PluginEnum.ISOLATE.getCode();
    }

    @Override
    public String named() {
        return PluginEnum.ISOLATE.getName();
    }
}
