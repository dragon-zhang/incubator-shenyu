package org.apache.shenyu.plugin.wasm.base;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import org.apache.shenyu.common.dto.PluginData;
import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.plugin.base.handler.PluginDataHandler;

/**
 * @author HaiLang
 * @date 2023/8/14 10:23
 */
public abstract class AbstractWasmPluginDataHandler extends WasmLoader implements PluginDataHandler {
    
    public AbstractWasmPluginDataHandler() throws IOException, URISyntaxException {
        super();
    }
    
    @Override
    public void handlerPlugin(PluginData pluginData) {
        Optional.ofNullable(instance.getFunction("handlerPlugin"))
                .ifPresent(handlerPlugin -> handlerPlugin.apply(pluginData));
    }
    
    @Override
    public void removePlugin(PluginData pluginData) {
        Optional.ofNullable(instance.getFunction("removePlugin"))
                .ifPresent(handlerPlugin -> handlerPlugin.apply(pluginData));
    }
    
    @Override
    public void handlerSelector(SelectorData selectorData) {
        Optional.ofNullable(instance.getFunction("handlerSelector"))
                .ifPresent(handlerPlugin -> handlerPlugin.apply(selectorData));
    }
    
    @Override
    public void removeSelector(SelectorData selectorData) {
        Optional.ofNullable(instance.getFunction("removeSelector"))
                .ifPresent(handlerPlugin -> handlerPlugin.apply(selectorData));
    }
    
    @Override
    public void handlerRule(RuleData ruleData) {
        Optional.ofNullable(instance.getFunction("handlerRule"))
                .ifPresent(handlerPlugin -> handlerPlugin.apply(ruleData));
    }
    
    @Override
    public void removeRule(RuleData ruleData) {
        Optional.ofNullable(instance.getFunction("removeRule"))
                .ifPresent(handlerPlugin -> handlerPlugin.apply(ruleData));
    }
}
