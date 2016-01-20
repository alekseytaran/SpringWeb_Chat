package com.teamdev;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.teamdev")
@EnableAspectJAutoProxy
@EnableJpaRepositories("com.teamdev.jpa")
public class SpringContextForTest {
}
