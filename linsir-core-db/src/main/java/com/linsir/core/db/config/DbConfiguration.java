package com.linsir.core.db.config;


import com.linsir.core.launch.props.LinsirPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * description：DbConfiguration
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/17 1:13
 */
@Configuration
@LinsirPropertySource(value = "classpath:/linsir-db.yml")
public class DbConfiguration {
}
