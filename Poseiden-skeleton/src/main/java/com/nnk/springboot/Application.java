package com.nnk.springboot;

import com.nnk.springboot.service.BidService;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.service.Impl.BidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {



	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
