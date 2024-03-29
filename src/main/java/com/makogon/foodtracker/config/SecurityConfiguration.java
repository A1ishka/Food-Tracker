package com.makogon.foodtracker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                //.requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                //.requestMatchers(new AntPathRequestMatcher("/auth/register")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/home/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/completed")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/product/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/editproduct/**")).permitAll()
                //.requestMatchers(new AntPathRequestMatcher("/categories")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/categories/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/category/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/statistics/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/viewstatistics/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/vieweditprofile/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/viewregister/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/viewfavorite/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/viewcategories/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/viewuserpage/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/userpage/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/myfavorite/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/addproduct/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/removeProductFromStatistics/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/dailystatistics/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/questions")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/editprofile/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/map/**")).permitAll()



//                .requestMatchers(new AntPathRequestMatcher("/register/calculation/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/register/plan")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/register/completeReg")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/checkLogin")).permitAll()

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin123").roles("ADMIN")
                .and()
                .withUser("user").password("{noop}user123").roles("USER");
    }
}


//http.authorizeRequests()
//        .antMatchers("/admin/**").hasRole("ADMIN")
//        .antMatchers("/user/**").hasRole("USER")
//        .anyRequest().authenticated()
//        .and()
//        .formLogin(); // Включить форму входа

