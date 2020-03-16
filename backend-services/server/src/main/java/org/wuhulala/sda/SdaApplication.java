package org.wuhulala.sda;


import com.wuhulala.rest.actuator.EndpointConfig;
import com.wuhulala.rest.springboot.SrspConfig;
import com.wuhulala.sda.service.domain.DomainServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import({SrspConfig.class, EndpointConfig.class, DomainServiceImpl.class})
@SpringBootApplication
public class SdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdaApplication.class, args);
    }
}
