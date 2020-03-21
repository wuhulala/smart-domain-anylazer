package com.wuhulala.sda;


import com.wuhulala.rest.actuator.EndpointConfig;
import com.wuhulala.rest.springboot.SrspConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import({SrspConfig.class, EndpointConfig.class})
@MapperScan("com.wuhulala.sda.mapper")
@SpringBootApplication
public class SdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdaApplication.class, args);
    }
}
