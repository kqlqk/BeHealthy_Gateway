package me.kqlqk.behealthy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BeHealthyGateway {

    public static void main(String[] args) {
        SpringApplication.run(BeHealthyGateway.class, args);
    }

}
