package org.apache.shenyu.plugin.wasm.base;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import org.apache.shenyu.common.dto.MetaData;
import org.apache.shenyu.plugin.base.handler.MetaDataHandler;

/**
 * @author HaiLang
 * @date 2023/8/14 10:28
 */
public abstract class AbstractWasmMetaDataHandler extends WasmLoader implements MetaDataHandler {
    
    public AbstractWasmMetaDataHandler() throws IOException, URISyntaxException {
        super();
    }
    
    @Override
    public void handle(MetaData metaData) {
        Optional.ofNullable(instance.getFunction("handle"))
                .ifPresent(handlerPlugin -> handlerPlugin.apply(metaData));
    }
    
    @Override
    public void remove(MetaData metaData) {
        Optional.ofNullable(instance.getFunction("remove"))
                .ifPresent(handlerPlugin -> handlerPlugin.apply(metaData));
    }
}
