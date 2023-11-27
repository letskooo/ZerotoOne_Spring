package pm2_5.studypartner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudypartnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudypartnerApplication.class, args);
	}

}
