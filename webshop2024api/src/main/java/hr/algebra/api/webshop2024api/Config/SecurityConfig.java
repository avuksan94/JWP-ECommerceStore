package hr.algebra.api.webshop2024api.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
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
        logger.info("Configuring Security Filter Chain...");
        http.authorizeHttpRequests(configurer ->
                configurer
                        //Categories
                        .requestMatchers(HttpMethod.GET, "/webShopApi/categories/allCategories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/webShopApi/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/webShopApi/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/webShopApi/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/webShopApi/categories/**").hasRole("ADMIN")

                        //Subcategories
                        .requestMatchers(HttpMethod.GET, "/webShopApi/subcategories/allSubcategories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/webShopApi/subcategories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/webShopApi/subcategories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/webShopApi/subcategories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/webShopApi/subcategories/**").hasRole("ADMIN")

                        //Products
                        .requestMatchers(HttpMethod.GET, "/webShopApi/products/allProducts").permitAll()
                        .requestMatchers(HttpMethod.GET, "/webShopApi/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/webShopApi/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/webShopApi/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/webShopApi/products/**").hasRole("ADMIN")

                        //Shopping
                        .requestMatchers(HttpMethod.GET, "/webShopApi/cartItems/allCartItems").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/webShopApi/cartItems/findByShoppingCartId/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/webShopApi/cartItems/findBySession/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/webShopApi/cartItems/findByUsername/**").hasRole("ADMIN")

        );
        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());
        // disable Cross Site Request Forgery (CSRF)
        http.csrf(csfr->csfr.disable());
        return http.build();
    }
}