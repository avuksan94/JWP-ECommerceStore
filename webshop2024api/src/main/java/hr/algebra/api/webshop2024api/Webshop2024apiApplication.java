package hr.algebra.api.webshop2024api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EntityScan("hr.algebra.dal.webshop2024dal.Entity")
@EnableJpaRepositories("hr.algebra.dal.webshop2024dal.Repository")
@ComponentScan(basePackages = {"hr.algebra.dal.webshop2024dal","hr.algebra.bl.webshop2024bl","hr.algebra.api.webshop2024api"})
public class Webshop2024apiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Webshop2024apiApplication.class, args);
	}

}
