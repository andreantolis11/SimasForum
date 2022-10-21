package com.simasforum.SimasForum;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@Generated
public class SimasForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimasForumApplication.class, args);
	}

}
