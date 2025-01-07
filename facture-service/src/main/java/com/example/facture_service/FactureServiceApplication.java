package com.example.facture_service;

import com.example.operators_api.web.MarocTelecomSoapService;
import com.example.operators_api.web.MarocTelecomWS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FactureServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactureServiceApplication.class, args);
	}

	@Bean
	MarocTelecomSoapService customerSoapService(){
		return new MarocTelecomWS().getMarocTelecomSoapServicePort();
	}

}
