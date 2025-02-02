package com.linsir.core.mybatis.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.linsir.core.auth.LinsirUser;
import com.linsir.core.auth.utils.AuthUtil;
import com.linsir.core.constant.CommonConstant;
import com.linsir.core.constant.LinsirConstant;
import com.linsir.core.mybatis.util.S;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * description：自动填充插件
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/1 20:02
 */
@Component
@Slf4j
public class FillMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";
    private static final String CREATE_BY = "createBy";
    private static final String UPDATE_BY = "updateBy";






    @Override
    public void insertFill(MetaObject metaObject) {
        LinsirUser user =new LinsirUser();
        user.setUserName("默认用户名");
        user.setTenantId("默认租户名");
        log.info("当前执行插入操作，默认填入字段createdTime：{}和字段updatedTime:{}", new Date(),new Date());
        //时间是默认需要插入进去的
        this.strictInsertFill(metaObject, CREATE_TIME, Date.class, new Date());
        this.strictInsertFill(metaObject, UPDATE_TIME, Date.class, new Date());
        log.info("当前执行插入操作，默认填入字段createdBy：{}和字段updatedBy:{}",user.getUserName(),user.getUserName());
        this.strictInsertFill(metaObject, CREATE_BY, String.class, user.getUserName());
        this.strictInsertFill(metaObject, UPDATE_BY, String.class, user.getUserName());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LinsirUser user =new LinsirUser();
        user.setUserName("默认用户名");
        user.setTenantId("默认租户名");
        log.info("当前更新插入操作，默认填入字段createdBy：{}和字段updatedBy:{}",user.getUserName(),user.getUserName());
        this.strictInsertFill(metaObject, "updatedTime", Date.class, new Date());
        this.strictInsertFill(metaObject,"updatedBy",String.class, user.getUserName());
    }
}
