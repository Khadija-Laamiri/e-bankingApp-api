package com.example.operators_api.config;

import com.example.operators_api.web.MarocTelecomSoapService;
import lombok.AllArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;

@Configuration
@AllArgsConstructor
public class CxfConfig {
    private Bus bus;
    private MarocTelecomSoapService marocTelecomSoapService;

    @Bean
    public EndpointImpl endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, marocTelecomSoapService);
        endpoint.publish("/CustomerService");
        return endpoint;
    }
}