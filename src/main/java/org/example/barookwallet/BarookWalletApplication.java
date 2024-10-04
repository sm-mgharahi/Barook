package org.example.barookwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BarookWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarookWalletApplication.class, args);
    }

}
