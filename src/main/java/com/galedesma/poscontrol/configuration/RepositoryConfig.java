package com.galedesma.poscontrol.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.galedesma.poscontrol.repository")
public class RepositoryConfig {
}
