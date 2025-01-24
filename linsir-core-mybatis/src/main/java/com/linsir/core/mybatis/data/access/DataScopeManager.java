package com.linsir.core.mybatis.data.access;


import java.io.Serializable;
import java.util.List;

/**
 * description：数据权限校验扩展接口
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:18
 */
public interface DataScopeManager {

    /**
     * <h3>可访问的对象范围ID</h3>
     * <br/>
     * <table border="10">
     * <caption>添加条件规则</caption>
     * <tr>
     * <th>返回值</th>
     * <th>SQL</th>
     * <th>说明</th>
     * </tr>
     * <tr>
     * <td>null</td>
     * <td>-</td>
     * <td>为null不加入条件</td>
     * </tr>
     * <td>[]</td>
     * <td>IS NULL</td>
     * <td></td>
     * </tr>
     * <tr>
     * <td>[10001]</td>
     * <td>= '10001'</td>
     * <td>长度等于1的列表</td>
     * </tr>
     * <tr>
     * <td>[10001, 10002] &nbsp</td>
     * <td>IN ('10001', '10002') &nbsp</td>
     * <td>长度大于1的列表</td>
     * </tr>
     * </table>
     */
    List<? extends Serializable> getAccessibleIds(String fieldName);

    /**
     * 基于entityClass和字段名，返回可访问的范围ids
     * @param entityClass 实体类
     * @param fieldName 字段名
     * @return
     */
    default List<? extends Serializable> getAccessibleIds(Class<?> entityClass, String fieldName) {
        return getAccessibleIds(fieldName);
    }

    /**
     * 该数据权限涉及的实体类，基于代码的数据权限必须指定
     * @return
     */
    List<Class<?>> getEntityClasses();

    /**
     * 显示标题
     * @return
     */
    default String getTitle() {
        return this.getClass().getSimpleName();
    }
}
