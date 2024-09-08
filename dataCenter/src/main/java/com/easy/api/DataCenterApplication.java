package com.easy.api;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

/**
 * 系统启动类
 *
 * @author muchi
 */
@SpringBootApplication(scanBasePackages = {"com.easy"})
@EnableScheduling
@EnableCaching
@EnableAsync
@MapperScan("com.easy.**.dao")
public class DataCenterApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataCenterApplication.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(DataCenterApplication.class);
        ConfigurableApplicationContext run = app.run(args);
        Environment env = run.getEnvironment();
        String severPort = env.getProperty("server.port");
        String logo = """
                 █████╗ ██╗
                ██╔══██╗██║
                ███████║██║
                ██╔══██║██║
                ██║  ██║██║
                ╚═╝  ╚═╝╚═╝
                PROFILE: %s
                SERVER PORT: %s""";
        LOGGER.warn("\n" + String.format(logo, Arrays.toString(env.getActiveProfiles()), severPort));
    }
}