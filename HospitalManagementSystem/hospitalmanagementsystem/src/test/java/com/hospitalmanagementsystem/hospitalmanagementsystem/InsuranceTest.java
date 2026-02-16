package com.hospitalmanagementsystem.hospitalmanagementsystem;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Insurance;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    InsuranceService insuraceService;

    @Test
    void createInsuranceTest(){
        //Provider type
        //Policy Number
        //VaLid Until
     Insurance insurance =  Insurance.builder()
                            .policyNumber("123456789")
                            .provider("Test Provider")
                            .validUntil(LocalDate.now().plusYears(10))
                            .build();

        insuraceService.createInsurance(insurance,1L);

    }

    @Test
    void deleteInsuranceTest(){
        insuraceService.deleteInsurance(1L);
    }

    @Test
    void getInsuranceTest(){
        System.out.println(insuraceService.getInsurance(1L));
    }
}
