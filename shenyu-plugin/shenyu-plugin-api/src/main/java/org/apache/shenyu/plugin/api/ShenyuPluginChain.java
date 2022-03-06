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

package org.apache.shenyu.plugin.api;

import org.apache.shenyu.common.enums.PluginEnum;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * the shenyu plugin chain.
 */
public interface ShenyuPluginChain {

    /**
     * Delegate to the next {@code ShenyuPlugin} in the chain.
     *
     * @param exchange the current server exchange
     * @return {@code Mono<Void>} to indicate when request handling is complete
     */
    Mono<Void> execute(ServerWebExchange exchange);

    /**
     * Delegate to the target {@code ShenyuPlugin} in the chain.
     * Note: use this method carefully, it can lead to circular execution
     *
     * @param exchange   the current server exchange
     * @param pluginEnum locate target {@code ShenyuPlugin} by {@code PluginEnum}
     * @return {@code Mono<Void>} to indicate when request handling is complete
     */
    Mono<Void> executeFrom(ServerWebExchange exchange, PluginEnum pluginEnum);
}
