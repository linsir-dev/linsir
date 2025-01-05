### linsir-core-Knife4j  1.2.0
### 功能
1. api doc 文档

### 规划
1. 增强模式 
2. i18n国际化 
3. 接口添加作者 
4. 自定义文档 
5. 访问权限控制 
6. 接口排序 
7. 分组排序 
8. 请求参数缓存 
9. 动态请求参数 
10. 导出离线文档 
11. 过滤请求参数
   3.12 包含请求参数
   3.13 搜索API接口
   3.14 清除缓存
   3.15 动态请求参数添加文档注释
   3.16 动态响应参数添加文档注释
   3.17 自定义Host
   3.18 afterScript
   3.19 OAuth2
   3.20 导出Postman
   3.21 全局参数
   3.22 自定义Swagger Models名称
   3.23 自定义主页内容
   3.24 自定义Footer
   3.25 JSR303
   3.26 禁用调试
   3.27 禁用搜索框
   3.28 禁用OpenApi结构显示
   3.29 版本控制

### 参考
1. https://doc.xiaominfo.com/docs/quick-start


### 注意事项
1. 版本 
   2. Spring Boot 3 只支持OpenAPI3规范

   3. Knife4j提供的starter已经引用springdoc-openapi的jar，开发者需注意避免jar包冲突 
   4. JDK版本必须 >= 17 
   5. 详细Demo请参考knife4j-spring-boot3-demo


@Configuration 注解用于标记一个类为配置类，我们可以使用以下三种方法使其生效。
   1. 方法一： 配置类放在@ComponentScan扫描的路径下，Spring 会自动扫描该配置类，并将其中定义的 Bean 注册到应用上下文中，实现自动配置的效果。

   2. 方法二： 在启动配置类上使用@Import注解导入配置

   3. 方法三： 在resources/META-INF/目录下创建spring.factories文件，添加类似以下配置（@EnableAutoConfiguration注解开启了自动配置）


                            