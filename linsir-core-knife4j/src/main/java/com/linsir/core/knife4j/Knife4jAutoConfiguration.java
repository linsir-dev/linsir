package com.linsir.core.knife4j;


import com.linsir.core.launch.props.LinsirPropertySource;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * @author ：linsir
 * @description：配置
 * @date ：2025/1/5 19:43
 */
@Profile({"dev","test"})
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@LinsirPropertySource(value = "classpath:/linsir-swagger.yml")
public class Knife4jAutoConfiguration {

    private static final String DEFAULT_EXCLUDE_PATH = "/error";
    private static final String BASE_PATH = "/**";

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                // 分组名称
                .group("app-api")
                // 接口请求路径规则
                .pathsToMatch("/**")
                .build();
    }

    /**
     * 配置基本信息
     */
    @Bean
    public OpenAPI openAPI(SwaggerProperties swaggerProperties) {
        return new OpenAPI()
                .info(new Info()
                        // 标题
                        .title(swaggerProperties.getTitle())
                        // 描述Api接口文档的基本信息
                        .description("后端服务接口")
                        // 版本
                        .version("v1.0.0")
                        // 设置OpenAPI文档的联系信息，姓名，邮箱。
                        .contact(new Contact().name("Thomas").email("2907205361@qq.com"))
                        // 设置OpenAPI文档的许可证信息，包括许可证名称为"Apache 2.0"，许可证URL为"http://springdoc.org"。
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }

}
