package com.square.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PaymentsRsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsRsApplication.class, args);
	}

}
