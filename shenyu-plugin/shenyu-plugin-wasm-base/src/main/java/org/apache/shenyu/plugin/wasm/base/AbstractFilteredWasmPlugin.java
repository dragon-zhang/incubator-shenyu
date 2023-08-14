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
import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.plugin.api.ShenyuPluginChain;
import org.apache.shenyu.plugin.base.AbstractShenyuPlugin;
import org.apache.shenyu.wasm.Instance;
import org.apache.shenyu.wasm.exports.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author HaiLang
 * @date 2023/8/14 10:02
 */
public abstract class AbstractFilteredWasmPlugin extends AbstractShenyuPlugin {
    
    private static final Logger LOG = LoggerFactory.getLogger(AbstractFilteredWasmPlugin.class);
    
    protected final String wasmName;
    
    protected final Instance instance;
    
    public AbstractFilteredWasmPlugin() throws IOException, URISyntaxException {
        final Class<? extends AbstractFilteredWasmPlugin> clazz = this.getClass();
        this.wasmName = clazz.getName() + ".wasm";
        this.instance = WasmLoader.load(wasmName);
    }
    
    /**
     * this is Template Method child has Implement your own logic.
     *
     * @param exchange exchange the current server exchange {@linkplain ServerWebExchange}
     * @param chain    chain the current chain  {@linkplain ServerWebExchange}
     * @param selector selector    {@linkplain SelectorData}
     * @param rule     rule    {@linkplain RuleData}
     * @return {@code Mono<Void>} to indicate when request handling is complete
     */
    @Override
    protected Mono<Void> doExecute(final ServerWebExchange exchange, final ShenyuPluginChain chain,
            final SelectorData selector, final RuleData rule) {
        Function doExecute = instance.getFunction("doExecute");
        if (Objects.isNull(doExecute)) {
            LOG.error("method doExecute not found in " + wasmName + " !");
            return chain.execute(exchange);
        }
        final Object[] results = doExecute.apply(exchange, chain, selector, rule);
        return doExecute(exchange, chain, selector, rule, results);
    }
    
    protected abstract Mono<Void> doExecute(ServerWebExchange exchange, ShenyuPluginChain chain, SelectorData selector,
            RuleData rule, Object[] results);
    
}
