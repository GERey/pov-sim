package com.example.springboot;

import java.util.Arrays;
import javax.annotation.PostConstruct;

import io.pyroscope.javaagent.PyroscopeAgent;
import io.pyroscope.javaagent.config.Config;
import io.pyroscope.javaagent.EventType;
import io.pyroscope.http.Format;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
		PyroscopeAgent.start(
			new Config.Builder()
				.setApplicationName("airlines")
				.setProfilingEvent(EventType.ITIMER)
				.setFormat(Format.JFR)
				.setServerAddress("https://profiles-prod-008.grafana.net")
				// Set these if using Grafana Cloud:
				.setBasicAuthUser("1119981")
				.setBasicAuthPassword("xxx")
				// Optional Pyroscope tenant ID (only needed if using multi-tenancy). Not needed for Grafana cloud.
				// .setTenantID("<TenantID>")
				.build()
		);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
}
