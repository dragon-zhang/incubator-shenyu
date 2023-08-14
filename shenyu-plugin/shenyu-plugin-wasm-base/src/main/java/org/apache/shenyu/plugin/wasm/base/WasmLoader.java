package org.apache.shenyu.plugin.wasm.base;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.shenyu.wasm.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HaiLang
 * @date 2023/8/14 10:31
 */
public class WasmLoader {
    
    protected static final Logger LOG = LoggerFactory.getLogger(WasmLoader.class);
    
    protected final String wasmName;
    
    protected final Instance instance;
    
    public WasmLoader() throws IOException, URISyntaxException {
        final Class<? extends WasmLoader> clazz = this.getClass();
        this.wasmName = clazz.getName() + ".wasm";
        this.instance = load(wasmName);
    }
    
    public static Instance load(String wasmName) throws IOException, URISyntaxException {
        // locate `.wasm` lib.
        Path wasmPath = Paths.get(WasmLoader.class.getClassLoader().getResource(wasmName).toURI());
        // Reads the WebAssembly module as bytes.
        byte[] wasmBytes = Files.readAllBytes(wasmPath);
        // Instantiates the WebAssembly module.
        Instance instance = new Instance(wasmBytes);
        Runtime.getRuntime().addShutdownHook(new Thread(instance::close));
        return instance;
    }
}
