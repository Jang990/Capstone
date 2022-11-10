package com.inhatc.spring.capstone.config;

import java.io.IOException;

import org.kohsuke.github.GitHub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubConfig {
	@Bean
	public GitHub github() throws IOException {
		return GitHub.connectAnonymously();
	}
}
