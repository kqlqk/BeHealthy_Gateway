package me.kqlqk.BeHealthy.main_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BeHealthyMainRestApi {

    public static void main(String[] args) {
        SpringApplication.run(BeHealthyMainRestApi.class, args);
    }

}
