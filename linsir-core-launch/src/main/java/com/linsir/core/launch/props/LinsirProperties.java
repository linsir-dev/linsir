package com.linsir.core.launch.props;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName : LinsirProperties
 * @Description :
 * @Author : Linsir
 * @Date: 2023-12-19 17:38
 */

@ConfigurationProperties("linsir")
public class LinsirProperties implements EnvironmentAware, EnvironmentCapable {
    @Nullable
    private Environment environment;

    /**
     * 装载自定义配置blade.prop.xxx
     */
    @Getter
    private final Map<String, String> prop = new HashMap<>();

    /**
     * 获取配置
     *
     * @param key key
     * @return value
     */
    @Nullable
    public String get(String key) {
        return get(key, null);
    }

    /**
     * 获取配置
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value
     */
    @Nullable
    public String get(String key, @Nullable String defaultValue) {
        String value = prop.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 获取配置
     *
     * @param key key
     * @return int value
     */
    @Nullable
    public Integer getInt(String key) {
        return getInt(key, null);
    }

    /**
     * 获取配置
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return int value
     */
    @Nullable
    public Integer getInt(String key, @Nullable Integer defaultValue) {
        String value = prop.get(key);
        if (value != null) {
            return Integer.valueOf(value.trim());
        }
        return defaultValue;
    }

    /**
     * 获取配置
     *
     * @param key key
     * @return long value
     */
    @Nullable
    public Long getLong(String key) {
        return getLong(key, null);
    }

    /**
     * 获取配置
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return long value
     */
    @Nullable
    public Long getLong(String key, @Nullable Long defaultValue) {
        String value = prop.get(key);
        if (value != null) {
            return Long.valueOf(value.trim());
        }
        return defaultValue;
    }

    /**
     * 获取配置
     *
     * @param key key
     * @return Boolean value
     */
    @Nullable
    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    /**
     * 获取配置
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return Boolean value
     */
    @Nullable
    public Boolean getBoolean(String key, @Nullable Boolean defaultValue) {
        String value = prop.get(key);
        if (value != null) {
            value = value.toLowerCase().trim();
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }

    /**
     * 获取配置
     *
     * @param key key
     * @return double value
     */
    @Nullable
    public Double getDouble(String key) {
        return getDouble(key, null);
    }

    /**
     * 获取配置
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return double value
     */
    @Nullable
    public Double getDouble(String key, @Nullable Double defaultValue) {
        String value = prop.get(key);
        if (value != null) {
            return Double.parseDouble(value.trim());
        }
        return defaultValue;
    }

    /**
     * 判断是否存在key
     *
     * @param key prop key
     * @return boolean
     */
    public boolean containsKey(String key) {
        return prop.containsKey(key);
    }


    /**
     * 环境，方便在代码中获取
     *
     * @return 环境 env
     */
    public String getEnv() {
        Objects.requireNonNull(environment, "Spring boot 环境下 Environment 不可能为null");
        String env = environment.getProperty("linsir.env");
        Assert.notNull(env, "请使用 LinsirApplication 启动...");
        return env;
    }

    /**
     * 应用名称${spring.application.name}
     *
     * @return 应用名
     */
    public String getName() {
        Objects.requireNonNull(environment, "Spring boot 环境下 Environment 不可能为null");
        return environment.getProperty("spring.application.name", environment.getProperty("blade.name", ""));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        Objects.requireNonNull(environment, "Spring boot 环境下 Environment 不可能为null");
        return this.environment;
    }
}
