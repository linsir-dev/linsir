package com.linsir.core.mybatis.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.linsir.core.auth.LinsirUser;
import com.linsir.core.constant.FieldConstant;
import com.linsir.core.constant.RoleConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;


/**
 * description：FillMetaObjectHandler
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/6 5:01
 */
@Component
@Slf4j
public class FillMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {

        LinsirUser user = new LinsirUser();
        user.setUserName("linsir");
        user.setTenantId("t-0000");
        user.setType(RoleConstant.ADMIN);
        log.info("执行插入操作，设置默认用户信息，为租户，其中name{},tenantId{},type{}",user.getUserName(),user.getTenantId(),user.getType());
        if (!user.getUserName().isEmpty()){
           this.strictInsertFill(metaObject, FieldConstant.FieldName.createdBy.name(), String.class,user.getUserName());
           this.strictInsertFill(metaObject, FieldConstant.FieldName.updatedBy.name(), String.class,user.getUserName());
        }
        /*if (!user.getType().isEmpty()){
            if (user.getType().equals(RoleConstant.USER)){
                log.info("该用户为用户type为:{}.是租户",user.getType());
                this.strictInsertFill(metaObject,FieldConstant.FieldName.tenantId.name(),String.class,user.getTenantId());
            }
            else {
                log.info("该用户为用户type为:{}，是系统用户",user.getType());
            }
        }*/
        this.strictInsertFill(metaObject, FieldConstant.FieldName.createdTime.name(), Date.class, new Date());
        this.strictInsertFill(metaObject, FieldConstant.FieldName.updatedTime.name(), Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LinsirUser user = new LinsirUser();
        user.setUserName("linsir");
        user.setTenantId("t-0000");
        user.setType(RoleConstant.ADMIN);
        log.info("执行更新操作，设置默认用户信息，为租户，其中name{},tenantId{},type{}",user.getUserName(),user.getTenantId(),user.getType());
        if (!user.getUserName().isEmpty()){
            this.strictInsertFill(metaObject, FieldConstant.FieldName.updatedBy.name(), String.class,user.getUserName());
        }
        this.strictInsertFill(metaObject, FieldConstant.FieldName.updatedTime.name(), LocalDate.class, LocalDate.now());
    }
}
