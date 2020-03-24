package cn.gson.oasys;

import cn.gson.oasys.Utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)   //不添加会报错
@ImportResource(locations = {"classpath:activiti.cfg.xml"})  //引入xml配置文件
@Import(SpringUtils.class)
public class OasysApplication {

	public static void main(String[] args) {
		SpringApplication.run(OasysApplication.class, args);
	}

/*	@Bean
     public EmbeddedServletContainerCustomizer containerCustomizer(){
		        return container -> {
		        	container.setSessionTimeout(2147483647);*//*单位为S*//*
				 };
		     }*/
}

