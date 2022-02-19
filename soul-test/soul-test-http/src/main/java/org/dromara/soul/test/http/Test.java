package org.dromara.soul.test.http;

import org.dromara.soul.client.common.utils.OkHttpTools;
import org.dromara.soul.client.springmvc.init.SpringMvcClientBeanPostProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author HaiLang
 * @date 2022/2/18 16:39
 */
@Component
public class Test implements InitializingBean {

    @Autowired
    private SpringMvcClientBeanPostProcessor processor;

    @Override
    public void afterPropertiesSet() throws Exception {
        String url = "http://localhost:9195/http/test/payment";
        String param = "{\"userId\":\"11\",\"userName\":\"haha\"}";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                final String response = OkHttpTools.getInstance().post(url,param);
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 100, 500, TimeUnit.MILLISECONDS);
    }
}
