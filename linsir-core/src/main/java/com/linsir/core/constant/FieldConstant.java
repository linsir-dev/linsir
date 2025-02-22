package com.linsir.core.constant;

/**
 * description：字段相关
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/2 14:53
 */
public interface FieldConstant {

    /*------------------删除标志DEL---------------------*/
    /*删除标志*/
    public static final Integer DEL_FLAG_1 = 1;

    /*删除*/
    public static final Integer DEL_FLAG_0 = 0;


    /*------------------排序---------------------*/
    /*排序 - 降序标记*/
    public static final String ORDER_DESC = "DESC";

    /*-------------------Field--------------------*/
    public enum FieldName{
        /**
         * 主键属性名
         */
        id,
        /**
         * 租户Id
         */
        tenantId,
        /**
         * 默认的上级ID属性名
         */
        parentId,
        /**
         * 子节点属性名
         */
        children,
        /**
         * 逻辑删除标记字段
         */
        deleted,
        /**
         * 创建时间字段
         */
        createdTime,
        /**
         * 更新时间
         */
        updatedTime,
        /**
         * 创建人
         */
        createdBy,
        /**
         * 更新人
         */
        updatedBy,
        /**
         * 组织id
         */
        orgId,
        /**
         * 用户id
         */
        userId,
        /**
         * 父ids路径
         */
        parentIdsPath
    }


    /*------------------字段&列名---------------------*/

    /*逻辑删除列名*/
    public static final String COLUMN_IS_DELETED = "is_deleted";

    /*逻辑删除字段名称*/
    public static final String FIELD_DELETED = "deleted";

    public static final String COLUMN_CREATED_TIME = "created_time";
    public static final String COLUMN_UPDATE_TIME = "updated_time";
    public static final String COLUMN_CREATE_BY = "created_by";
    public static final String COLUMN_UPDATE_BY = "updated_by";
    public static final String COLUMN_PARENT_ID="parent_id";

    /**
     * 租户数据库的字段名称
     * */
    public static final String COLUMN_TENANT_CODE = "tenant_code";
    public static final String COLUMN_ID = "id";


    /**
     * 字典Entity相关属性名定义
     */
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_ITEM_VALUE = "itemValue";
    public static final String COLUMN_ITEM_VALUE = "item_value";
    public static final String FIELD_TYPE = "type";
}
