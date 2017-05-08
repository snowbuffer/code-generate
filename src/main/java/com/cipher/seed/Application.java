package com.cipher.seed;

import com.cipher.seed.util.BeanTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 启动类
 * Created by cipher on 2017/5/5.
 */
@SpringBootApplication
public class Application {

    /**
     * 项目启动入口
     */
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationStartup("all"));
        springApplication.run(args);
    }

    /**
     * bean工具类，可以在普通类中获取spring创建的bean
     */
    @Bean
    public BeanTool beanTool() {
        return new BeanTool();
    }

}
