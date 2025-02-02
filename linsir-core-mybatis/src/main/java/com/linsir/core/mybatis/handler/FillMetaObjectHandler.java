package com.linsir.core.mybatis.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.linsir.core.auth.LinsirUser;
import com.linsir.core.constant.FieldConstant;
import com.linsir.core.constant.LinsirConstant;
import com.linsir.core.constant.RoleConstant;
import com.linsir.core.convert.LinsirConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.linsir.core.constant.FieldConstant.*;

/**
 * description：自动填充插件
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/1 20:02
 */
@Component
@Slf4j
public class FillMetaObjectHandler implements MetaObjectHandler {



    @Override
    public void insertFill(MetaObject metaObject) {
        LinsirUser user =new LinsirUser();
        user.setUserName("默认用户名");
        user.setTenantId("默认租户名");
        user.setType(RoleConstant.USER);
        user.setTenantId("000000");
        log.info("当前执行插入操作，默认填入字段createdTime：{}和字段updatedTime:{}", new Date(),new Date());
        //时间是默认需要插入进去的
        this.strictInsertFill(metaObject, CREATE_TIME, Date.class, new Date());
        this.strictInsertFill(metaObject, UPDATE_TIME, Date.class, new Date());
        log.info("当前执行插入操作，默认填入字段createdBy：{}和字段updatedBy:{}",user.getUserName(),user.getUserName());
        this.strictInsertFill(metaObject, CREATE_BY, String.class, user.getUserName());
        this.strictInsertFill(metaObject, UPDATE_BY, String.class, user.getUserName());
        if (user.getTenantId()!=null)
        {
            if (user.getType()==RoleConstant.USER)
            {
                log.info("当前执行插入操作，默认填入字段{}:{}", TENANT_CODE,user.getTenantId());
                this.strictInsertFill(metaObject,TENANT_CODE,String.class, user.getTenantId());
            }
            else{
                log.info("000000");
                this.strictInsertFill(metaObject,TENANT_CODE,String.class, RoleConstant.ADMIN_TENANT_ID);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LinsirUser user =new LinsirUser();
        user.setUserName("默认用户名");
        user.setTenantId("默认租户名");
        log.info("当前更新插入操作，默认填入字段createdBy：{}和字段updatedBy:{}",user.getUserName(),user.getUserName());
        this.strictUpdateFill(metaObject, UPDATE_TIME, Date.class, new Date());
        this.strictUpdateFill(metaObject,UPDATE_BY,String.class, user.getUserName());
    }
}
