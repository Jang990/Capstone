package com.inhatc.spring.capstone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.inhatc.spring.capstone.user.service.CustomOAuthUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final CustomOAuthUserService OAuthUserService;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(a -> a
				.antMatchers("/", "/error", "/webjars/**").permitAll()
				.antMatchers("/myprojectboard").hasAnyRole("ADMIN","USER")
				.mvcMatchers("/css/**", "/js/**", "/img/**", "/images/**").permitAll()
                .mvcMatchers("/", "/users/**", "/item/**", "/images/**").permitAll()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/test/**").permitAll()
                .mvcMatchers("/**").permitAll() // 일단 모든 것 허용
                .mvcMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.exceptionHandling(e -> e
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			)
			.csrf(c -> c
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.disable()
			)
			.logout(l -> l
				.logoutSuccessUrl("/")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			)
			.oauth2Login(o -> o
					.defaultSuccessUrl("/") // 기본 메인페이지로 리다이렉트
					
					.userInfoEndpoint().userService(OAuthUserService)
			);
		
		

        return http.build();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
