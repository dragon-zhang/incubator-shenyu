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
 * To solve this problem, IsolatePlugin is introduced to isolate
 * {@code shenyu-bootstrap} resources(such as memory, threads, etc).
 * Only thread pool resource is supported now.
 */
public class IsolatePlugin implements ShenyuPlugin {
    @Override
    public Mono<Void> execute(ServerWebExchange exchange, ShenyuPluginChain chain) {
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
