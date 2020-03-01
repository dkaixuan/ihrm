package com.ihrm;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.JwtUtil;
import com.ihrm.domain.system.Permission;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 15:15
 * @Version 1.0
 */
//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = "com.ihrm")
//2.配置jpa注解的扫描
@EntityScan(value="com.ihrm.domain.system")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
    @Bean
    public Permission permission(){
        return new Permission();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
