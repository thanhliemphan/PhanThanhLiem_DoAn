package com.example.PhanThanhLiem_DoAn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.repository")
public class PhanThanhLiemDoAnApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PhanThanhLiemDoAnApplication.class, args);
	}

}
