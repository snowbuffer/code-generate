package com.cipher.seed;

import com.cipher.seed.service.TemplateService;
import com.cipher.seed.util.BeanTool;
import com.google.common.collect.Lists;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * 启动后执行操作
 * Created by cipher on 2017/5/5.
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private static final List<String> types =
            Lists.newArrayList("domain", "mapper", "condition", "dao");

    private String type;

    public ApplicationStartup(String type) {
        this.type = type;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        TemplateService service = (TemplateService) BeanTool.getBean(TemplateService.class);
        if ("all".equals(type)) {
            for (String t : types) {
                service.merge(t);
            }
        } else {
            service.merge(type);
        }
        EmbeddedWebApplicationContext context = (EmbeddedWebApplicationContext)BeanTool.getAppContext();
        context.getEmbeddedServletContainer().stop();
    }
}
