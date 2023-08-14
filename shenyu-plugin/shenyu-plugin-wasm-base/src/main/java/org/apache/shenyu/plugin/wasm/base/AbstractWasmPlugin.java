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

package org.apache.shenyu.plugin.wasm.base;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import org.apache.shenyu.plugin.api.ShenyuPlugin;
import org.apache.shenyu.plugin.api.ShenyuPluginChain;
import org.apache.shenyu.wasm.exports.Function;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author HaiLang
 * @date 2023/8/14 09:47
 */
public abstract class AbstractWasmPlugin extends WasmLoader implements ShenyuPlugin {
    
    public AbstractWasmPlugin() throws IOException, URISyntaxException {
        super();
    }
    
    @Override
    public Mono<Void> execute(final ServerWebExchange exchange, final ShenyuPluginChain chain) {
        Function execute = instance.getFunction("execute");
        if (Objects.isNull(execute)) {
            LOG.error("method execute not found in " + wasmName + " !");
            return chain.execute(exchange);
        }
        final Object[] results = execute.apply(exchange, chain);
        return doExecute(exchange, chain, results);
    }
    
    protected Mono<Void> doExecute(final ServerWebExchange exchange, final ShenyuPluginChain chain, final Object[] results) {
        return chain.execute(exchange);
    }
}
