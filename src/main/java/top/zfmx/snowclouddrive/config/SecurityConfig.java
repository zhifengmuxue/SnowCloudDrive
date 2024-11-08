package top.zfmx.snowclouddrive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.*;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 权限设置
        http.authorizeHttpRequests(
                authorize -> authorize
                        .anyRequest().authenticated()

        );

        // 登录设置
        http.formLogin(login -> login
                .loginPage("/login").permitAll());
        // 登出设置
        http.logout(LogoutConfigurer::permitAll);
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
