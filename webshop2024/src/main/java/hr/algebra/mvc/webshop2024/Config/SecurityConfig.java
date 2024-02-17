package hr.algebra.mvc.webshop2024.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        logger.info("Configuring UserDetailsManager...");
        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        theUserDetailsManager
                .setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        theUserDetailsManager
                .setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");

        return theUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(HttpMethod.GET, "/css/**", "/js/**", "/images/**", "shared/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/products/list").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/security/loginUser").permitAll()
                                .requestMatchers(HttpMethod.POST, "/webShop/security/manualLogout").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/security/showFormCreateUser").permitAll()
                                .requestMatchers(HttpMethod.POST, "/webShop/security/saveUser").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/auth/status").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/testing/list").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/error").permitAll()

                                //ADMIN PAGES
                                //.requestMatchers(HttpMethod.GET, "/webShop/loginUser").permitAll()
                )
                .formLogin(form ->
                        form
                                .loginPage("/webShop/security/loginUser")
                                .loginProcessingUrl("/security/security-login")
                                .successHandler(new CustomAuthenticationSuccessHandler())
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/webShop/security/manualLogout")
                        .logoutSuccessUrl("/webShop/security/loginUser?logoutManual=true") // the URL to redirect to after logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable Cross Site Request Forgery (CSRF)
        http.csrf(csfr->csfr.disable());
        return http.build();
    }
}