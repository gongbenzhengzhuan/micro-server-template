package gateway;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import swagger.annotation.EnableBaseSwagger2;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
//@EnableSwagger2
@EnableBaseSwagger2
@EnableScheduling
@EnableFeignClients(basePackages = { "api"})
public class CommonsGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonsGatewayApplication.class,args);
    }

}
