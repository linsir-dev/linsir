package com.linsir.core.tool.config;

import com.linsir.core.tool.convert.EnumToStringConverter;
import com.linsir.core.tool.convert.StringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * blade enum 《-》 String 转换配置
 *
 * @author L.cm
 */
@Configuration
public class LinsirConverterConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new EnumToStringConverter());
		registry.addConverter(new StringToEnumConverter());
	}

}
