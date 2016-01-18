package com.teamdev;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@EnableWebMvc
@Configuration
@ComponentScan({"com.teamdev.controller", "com.teamdev"})
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableTransactionManagement
public class SpringWebRepoConfig extends WebMvcConfigurationSupport {


}
