package com.example.vmo1;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Vmo1Application {

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", "dukdwp2nd");
        config.put("api_key", "827523261874871");
        config.put("api_secret", "I-iOdCGrQ3RYVYO1g0MIbnkMNDo");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
    public static void main(String[] args) {

        SpringApplication.run(Vmo1Application.class, args);
    }

}
