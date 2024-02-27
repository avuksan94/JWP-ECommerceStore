package hr.algebra.mvc.webshop2024;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
@EntityScan("hr.algebra.dal.webshop2024dal.Entity")
@EnableJpaRepositories("hr.algebra.dal.webshop2024dal.Repository")
@ComponentScan(basePackages = {"hr.algebra.dal.webshop2024dal","hr.algebra.bl.webshop2024bl","hr.algebra.mvc.webshop2024"})
public class Webshop2024Application {

	public static void main(String[] args) {
		SpringApplication.run(Webshop2024Application.class, args);
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
