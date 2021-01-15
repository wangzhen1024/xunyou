package com.example.xunyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.example.xunyou.mapper")
@SpringBootApplication
public class XunyouApplication {

	public static void main(String[] args) {
		SpringApplication.run(XunyouApplication.class, args);
	}

}
