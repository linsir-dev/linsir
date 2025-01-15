package com.linsir.core.log.launch;

import com.linsir.core.auto.service.AutoService;
import com.linsir.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

import java.util.Properties;

/**
 * LogLauncherServiceImpl
 *
 * @author Chill
 */
@AutoService(LauncherService.class)
public class LogLauncherServiceImpl implements LauncherService {
	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		props.setProperty("logging.config", "classpath:log/logback-" + profile + ".xml");
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
