package org.apache.shenyu.plugin.wasm;

import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.plugin.api.ShenyuPluginChain;
import org.apache.shenyu.plugin.base.AbstractShenyuPlugin;
import org.springframework.web.server.ServerWebExchange;
import org.wasmer.Instance;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * abstract wasm plugin please extends.
 */
public abstract class AbstractWasmPlugin extends AbstractShenyuPlugin {
    
    protected final Instance instance;
    
    public AbstractWasmPlugin() throws IOException, URISyntaxException {
        // locate `.wasm` lib.
        final Class<? extends AbstractWasmPlugin> clazz = this.getClass();
        Path wasmPath = Paths.get(clazz.getClassLoader().getResource(clazz.getName() + ".wasm").toURI());
        
        // Reads the WebAssembly module as bytes.
        byte[] wasmBytes = Files.readAllBytes(wasmPath);
        
        // Instantiates the WebAssembly module.
        this.instance = new Instance(wasmBytes);
        Runtime.getRuntime().addShutdownHook(new Thread(this.instance::close));
    }
    
    @Override
    protected Mono<Void> doExecute(final ServerWebExchange exchange, final ShenyuPluginChain chain, final SelectorData selector, final RuleData rule) {
        return chain.execute(exchange);
    }
}
