package org.java.financial;

import org.springframework.boot.SpringApplication;

public class TestFinancialApplication {

    public static void main(String[] args) {
        SpringApplication.from(FinancialApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
