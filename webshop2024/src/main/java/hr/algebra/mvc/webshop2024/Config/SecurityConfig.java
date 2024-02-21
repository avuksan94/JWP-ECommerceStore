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
                                .requestMatchers(HttpMethod.GET, "/webShop/products/findByKeyword").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/security/loginUser").permitAll()
                                .requestMatchers(HttpMethod.POST, "/webShop/security/manualLogout").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/security/showFormCreateUser").permitAll()
                                .requestMatchers(HttpMethod.POST, "/webShop/security/saveUser").permitAll()
                                .requestMatchers(HttpMethod.POST, "/webShop/security/saveUser").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/auth/status").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/navbar").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/products/showSelectedProduct").permitAll()

                                //ADMIN PAGES - PRODUCTS
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/products/list").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/products/showFormForAddProduct").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/products/showFormForUpdateProduct").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/products/findByKeyword").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/webShop/admin/products/save").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/products/delete").hasRole("ADMIN")

                                //ADMIN PAGES - CATEGORY
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/categories/list").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/categories/showFormForAddCategory").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/categories/showFormForUpdateCategory").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/webShop/admin/categories/save").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/categories/delete").hasRole("ADMIN")

                                //ADMIN PAGES - SUBCATEGORY
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/subcategories/list").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/subcategories/showFormForAddSubcategory").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/subcategories/showFormForUpdateSubcategory").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/webShop/admin/subcategories/save").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/subcategories/delete").hasRole("ADMIN")

                                //ADMIN PAGES - IMAGES
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/images/list").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/images/showFormForAddImage").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/images/showFormForUpdateImage").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/webShop/admin/images/save").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/images/delete").hasRole("ADMIN")

                                //ADMIN PAGES - ORDERS
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/order/allOrders").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/order/orderByUsernameAndDates").hasRole("ADMIN")

                                //ADMIN PAGES - CONNECTIONS
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/userConnections/list").hasRole("ADMIN")

                                //SHOPPER - ORDERS
                                .requestMatchers(HttpMethod.POST, "/webShop/order/finalize").hasRole("SHOPPER")
                                .requestMatchers(HttpMethod.GET, "/webShop/order/forShopper").hasRole("SHOPPER")

                                //ADMIN PAGES - NOTIFICATIONS
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/notifications/list").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/webShop/admin/notifications/showSelectedNotification").hasRole("ADMIN")

                                //Shopping controller
                                .requestMatchers(HttpMethod.POST, "/webShop/shopping/removeFromCartCart").permitAll()
                                .requestMatchers(HttpMethod.POST, "/webShop/shopping/addToCart").permitAll()
                                .requestMatchers(HttpMethod.POST, "/webShop/shopping/changeQuantity").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/shopping/cartItemCount").permitAll()
                                .requestMatchers(HttpMethod.GET, "/webShop/shopping/cart").permitAll()

                                .requestMatchers(HttpMethod.GET, "/error").permitAll()
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

        http.httpBasic(Customizer.withDefaults());
        http.csrf(csfr->csfr.disable());
        return http.build();
    }
}