package system.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.boot.SpringApplication.run;

@EnableAsync
@EntityScan(basePackages = {"system.test.entity"})
@EnableJpaRepositories(basePackages = {"system.test.repository"})
@SpringBootApplication(scanBasePackages = {"system.test"})
public class SystemTestApplication {

	public static void main(final String[] args) {
		run(SystemTestApplication.class, args);
	}

}
