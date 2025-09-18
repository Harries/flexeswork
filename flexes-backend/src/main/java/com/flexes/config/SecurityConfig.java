package com.flexes.config;

import com.flexes.security.JwtAuthenticationEntryPoint;
import com.flexes.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security 配置类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF
            .csrf().disable()
            
            // 配置CORS
            .cors().configurationSource(corsConfigurationSource())
            
            .and()
            
            // 配置异常处理
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            
            .and()
            
            // 配置会话管理
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            
            .and()
            
            // 配置请求授权
            .authorizeRequests()
            
            // 公开接口
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/public/**").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/api-docs/**").permitAll()
            .antMatchers("/v3/api-docs/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/webjars/**").permitAll()

            // 职位相关接口
            .antMatchers(HttpMethod.GET, "/jobs/**").permitAll()
            .antMatchers(HttpMethod.POST, "/jobs").hasRole("EMPLOYER")
            .antMatchers(HttpMethod.PUT, "/jobs/**").hasRole("EMPLOYER")
            .antMatchers(HttpMethod.DELETE, "/jobs/**").hasRole("EMPLOYER")
            
            // 申请相关接口
            .antMatchers(HttpMethod.POST, "/applications").hasRole("CANDIDATE")
            .antMatchers(HttpMethod.GET, "/applications/candidate/**").hasRole("CANDIDATE")
            .antMatchers(HttpMethod.GET, "/applications/employer/**").hasRole("EMPLOYER")
            .antMatchers(HttpMethod.PUT, "/applications/**").hasAnyRole("CANDIDATE", "EMPLOYER")
            
            // 候选人相关接口
            .antMatchers(HttpMethod.GET, "/candidates").hasRole("EMPLOYER")
            .antMatchers(HttpMethod.GET, "/candidates/**").hasAnyRole("CANDIDATE", "EMPLOYER")
            .antMatchers(HttpMethod.PUT, "/candidates/**").hasRole("CANDIDATE")
            
            // 雇主相关接口
            .antMatchers(HttpMethod.GET, "/employers").permitAll()
            .antMatchers(HttpMethod.GET, "/employers/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/employers/**").hasRole("EMPLOYER")
            
            // 管理员接口
            .antMatchers("/admin/**").hasRole("ADMIN")
            
            // 用户个人信息接口
            .antMatchers(HttpMethod.GET, "/users/profile").authenticated()
            .antMatchers(HttpMethod.PUT, "/users/profile").authenticated()
            .antMatchers(HttpMethod.POST, "/users/change-password").authenticated()
            
            // 其他所有请求需要认证
            .anyRequest().authenticated();

        // 添加JWT过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 允许的源
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:3000",
            "http://127.0.0.1:3000",
            "https://*.flexes.work"
        ));
        
        // 允许的方法
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // 允许的头部
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // 允许凭证
        configuration.setAllowCredentials(true);
        
        // 预检请求缓存时间
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
