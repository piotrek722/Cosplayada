package pl.edu.agh.tai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tai.model.LoginInfo;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RestController
public class CosplayadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosplayadaApplication.class, args);
	}
}
