package com.example.operators_api.web;

import com.example.operators_api.services.RechargeService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@WebService(serviceName = "MarocTelecomWS")
public class MarocTelecomSoapService {
    private final RechargeService rechargeService;
    @WebMethod
    public String recharge(@WebParam(name = "collaboratorId") Long collaboratorId,
                           @WebParam(name = "phoneNumber") String phoneNumber,
                           @WebParam(name = "amount") double amount){
        return rechargeService.recharge(collaboratorId,phoneNumber,amount);
    }
}