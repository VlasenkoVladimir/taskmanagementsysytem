package effectiveMobile.com.taskManagementSystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TaskManagementSystemApplication implements CommandLineRunner {

	@Value("${server.port}")
	private String serverPort;

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode("admin");
		System.out.println(hashedPassword);

		System.out.println("Swagger path: http://localhost:" + serverPort + "/swagger-ui/index.html");
		System.out.println("Application path: http://localhost:" + serverPort);
	}
}