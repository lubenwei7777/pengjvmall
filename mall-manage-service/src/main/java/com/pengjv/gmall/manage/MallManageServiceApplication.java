package com.pengjv.gmall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.pengjv.gmall.manage.mapper")
@EnableTransactionManagement
public class MallManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallManageServiceApplication.class, args);
	}

}
