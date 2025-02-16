package com.linsir.core.mybatis.data.access;


import com.linsir.core.auth.LinsirUser;
import com.linsir.core.auth.utils.AuthUtil;
import com.linsir.core.code.ResultCode;
import com.linsir.core.constant.FieldConstant;
import com.linsir.core.constant.TypeConstant;
import com.linsir.core.mybatis.entity.Organization;
import com.linsir.core.mybatis.exception.InvalidUsageException;
import com.linsir.core.mybatis.props.MybatisPlusProperties;
import com.linsir.core.mybatis.service.OrganizationService;
import com.linsir.core.mybatis.util.ContextHolder;
import com.linsir.core.mybatis.util.V;
import com.linsir.core.utils.Func;
import com.linsir.core.vo.ExtLabelValue;
import com.linsir.core.vo.PositionDataScope;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

/**
 * description：UserOrgDataAccessScopeManager
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/16 1:19
 */
@Slf4j
public abstract class UserOrgDataAccessScopeManager implements DataScopeManager{

    /**
     * 用户 类型的字段名
     */
    private static final List<String> USER_FIELD_NAMES = Arrays.asList(FieldConstant.FieldName.userId.name(), FieldConstant.FieldName.createdBy.name(), "user");

    /**
     * 部门 类型的字段名
     */
    private static final List<String> ORG_FIELD_NAMES = Arrays.asList(FieldConstant.FieldName.orgId.name(), "org", "department");

    @Resource
    private MybatisPlusProperties properties;

    @Override
    public String getTitle() {
        return Func.toStr(properties.getScopeManagerTitle(),"基于用户组织的数据权限控制");
    }

    /**
     * 教育子类实现
     * @return
     */
    @Override
    public abstract List<Class<?>> getEntityClasses();

    @Override
    public List<? extends Serializable> getAccessibleIds(String fieldName) {
        //当前用户
        LinsirUser currentUser;
        try {
            /*默认设置linsir*/
            currentUser = new LinsirUser();
            currentUser.setId(1L);
        }catch (Exception ex){
            log.warn("获取数据权限可访问ids异常",ex);
            return Collections.emptyList();
        }
        if (currentUser == null) {
            log.warn("无法获取当前用户");
            return Collections.emptyList();
        }
        //获取用户岗位对应的数据权限
        ExtLabelValue extensionObj = currentUser.getExtensionObj();
        if (extensionObj == null || extensionObj.getExt() == null) {
            //提取可访问ids
            if (isOrgFieldName(fieldName)) {
                return  buildOrgIdsScope(currentUser);
            }
                else if (isUserFieldName(fieldName))
                {
                return  buildUserIdsScope(currentUser);
                }
                else {
                throw new InvalidUsageException(ResultCode.INVALID_OPERATION,"默认数据权限: UserOrgDataAccessScopeManager 未能识别该字段类型: {}，检查是否需要重写isUserFieldName()/isOrgFieldName()", fieldName);
            }
        }

        // 处理岗位对应的数据范围权限
        PositionDataScope positionDataScope = (PositionDataScope)extensionObj.getExt();
        // 可看全部数据，不拦截
        if(TypeConstant.DICTCODE_DATA_PERMISSION_TYPE.ALL.name().equalsIgnoreCase(positionDataScope.getDataPermissionType())){
            return null;
        }
        // 本人数据
        else if(TypeConstant.DICTCODE_DATA_PERMISSION_TYPE.SELF.name().equalsIgnoreCase(positionDataScope.getDataPermissionType())){
            if(isUserFieldName(fieldName)){
                return buildUserIdsScope(currentUser);
            }
            else{// 忽略无关字段
                return null;
            }
        }
        // 按user过滤，本人及下属
        else if(TypeConstant.DICTCODE_DATA_PERMISSION_TYPE.SELF_AND_SUB.name().equalsIgnoreCase(positionDataScope.getDataPermissionType())){
            if(isUserFieldName(fieldName)){
                return positionDataScope.getAccessibleUserIds();
            }
            else{// 忽略无关字段
                return null;
            }
        }
        // 按部门过滤，本部门
        else if(TypeConstant.DICTCODE_DATA_PERMISSION_TYPE.DEPT.name().equalsIgnoreCase(positionDataScope.getDataPermissionType())){
            if(isOrgFieldName(fieldName)){
                return Collections.singletonList(positionDataScope.getOrgId());
            }
            else{// 忽略无关字段
                return null;
            }
        }
        // 按部门过滤，本部门及下属部门
        else if(TypeConstant.DICTCODE_DATA_PERMISSION_TYPE.DEPT_AND_SUB.name().equalsIgnoreCase(positionDataScope.getDataPermissionType())){
            if(isOrgFieldName(fieldName)){
                return positionDataScope.getAccessibleOrgIds();
            }
            else{// 忽略无关字段
                return null;
            }
        }
        else{
            log.warn("未知的数据权限类型: {}", positionDataScope.getDataPermissionType());
            return Collections.emptyList();
        }
    }

    /**
     * 未配置数据权限时的默认可见自己的
     * @param currentUser
     * @return
     */
    protected List<? extends Serializable> buildUserIdsScope(LinsirUser currentUser){
        List<Serializable> accessibleIds = new ArrayList<>(1);
        accessibleIds.add(currentUser.getId());
        return accessibleIds;
    }

    /**
     * 未配置数据权限时的默认本部门
     * @param currentUser
     * @return
     */
    protected List<? extends Serializable> buildOrgIdsScope(LinsirUser currentUser){
        List<Serializable> accessibleIds = new ArrayList<>();
        accessibleIds.add(currentUser.getDeptId());
        List<Long> childOrgIds = ContextHolder.getBean(OrganizationService.class).getChildOrgIds(currentUser.getDeptId());
        if(V.notEmpty(childOrgIds)){
            accessibleIds.addAll(childOrgIds);
        }
        return accessibleIds;
    }

    /**
     * 是否为可支持的用户字段
     * @param fieldName
     * @return
     */
    protected boolean isUserFieldName(String fieldName){
        return USER_FIELD_NAMES.contains(fieldName);
    }

    /**
     * 是否为可支持的部门字段
     * @param fieldName
     * @return
     */
    protected boolean isOrgFieldName(String fieldName){
        return ORG_FIELD_NAMES.contains(fieldName);
    }
}
