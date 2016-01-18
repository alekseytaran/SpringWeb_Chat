package com.teamdev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan({"com.teamdev.controller", "com.teamdev"})
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableSpringDataWebSupport
public class SpringWebRepoConfig extends WebMvcConfigurationSupport {

    @Autowired
    DataSource dataBase;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataBase);
    }
}
