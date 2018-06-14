package com.crud.tasks.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CompanyInfoConfig {

    @Value("${info.company.name}")
    private String name;

    @Value("${info.company.goal}")
    private String goal;

    @Value("${info.company.email}")
    private String email;

    @Value("${info.company.phone}")
    private String phone;

    public String createCompanyInfoView() {
        return name + ", " + email + ", " + phone + ", " + goal;
    }
}
