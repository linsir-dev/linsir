package com.linsir.core.binding.query.dynamic;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linsir.core.binding.QueryBuilder;
import com.linsir.core.binding.cache.BindingCacheManager;
import com.linsir.core.binding.parser.ParserCache;
import com.linsir.core.binding.parser.PropInfo;
import com.linsir.core.binding.query.BindQuery;
import com.linsir.core.config.BaseConfig;
import com.linsir.core.tool.constant.CommonConstant;
import com.linsir.core.util.S;
import com.linsir.core.util.V;
import com.linsir.core.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description：动态SQL构建Provider
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:19
 */
@Slf4j
public class DynamicSqlProvider {

    /**
     * select中的占位列前缀标识
     */
    public static final String PLACEHOLDER_COLUMN_FLAG = "__";

    /**
     * 构建动态SQL
     * @param ew
     * @return
     */
    public String buildSqlForList(QueryWrapper ew){
        return buildDynamicSql(null, ew);
    }

    /**
     * 构建动态SQL
     * @param page 分页参数，用于MP分页插件AOP，不可删除
     * @param ew
     * @return
     */
    public <DTO> String buildSqlForListWithPage(Page<?> page, QueryWrapper<DTO> ew){
        return buildDynamicSql(page, ew);
    }

    /**
     * 构建动态SQL
     * @param page 分页参数，用于MP分页插件AOP，不可删除
     * @param ew
     * @return
     */
    private <DTO> String buildDynamicSql(Page<?> page, QueryWrapper<DTO> ew){
        DynamicJoinQueryWrapper wrapper = (DynamicJoinQueryWrapper)ew;
        return new SQL() {{
            SELECT_DISTINCT(formatSqlSelect(ew.getSqlSelect(), wrapper.getMainEntityClass(), page));
            FROM(wrapper.getEntityTable()+" self");
            //提取字段，根据查询条件中涉及的表，动态join
            List<AnnoJoiner> annoJoinerList = wrapper.getAnnoJoiners();
            if(V.notEmpty(annoJoinerList)){
                Set<String> tempSet = new HashSet<>();
                StringBuilder sb = new StringBuilder();
                for(AnnoJoiner joiner : annoJoinerList){
                    if(V.notEmpty(joiner.getJoin()) && V.notEmpty(joiner.getOnSegment())){
                        if(joiner.getMiddleTable() != null){
                            sb.setLength(0);
                            sb.append(joiner.getMiddleTable()).append(" ").append(joiner.getMiddleTableAlias()).append(" ON ").append(joiner.getMiddleTableOnSegment());
                            String deletedCol = ParserCache.getDeletedColumn(joiner.getMiddleTable());
                            if(deletedCol != null && !S.containsIgnoreCase(joiner.getMiddleTable(), " " + deletedCol)){
                                sb.append(" AND ").append(joiner.getMiddleTableAlias()).append(".").append(deletedCol).append(" = ").append(BaseConfig.getActiveFlagValue());
                            }
                            String joinSegment = sb.toString();
                            if(!tempSet.contains(joinSegment)){
                                LEFT_OUTER_JOIN(joinSegment);
                                tempSet.add(joinSegment);
                            }
                        }
                        sb.setLength(0);
                        sb.append(joiner.getJoin()).append(" ").append(joiner.getAlias()).append(" ON ").append(joiner.getOnSegment());
                        String deletedCol = ParserCache.getDeletedColumn(joiner.getJoin());
                        if(deletedCol != null && !S.containsIgnoreCase(joiner.getOnSegment(), " " + deletedCol)){
                            sb.append(" AND ").append(joiner.getAlias()).append(".").append(deletedCol).append(" = ").append(BaseConfig.getActiveFlagValue());
                        }
                        String joinSegment = sb.toString();
                        if(!tempSet.contains(joinSegment)){
                            LEFT_OUTER_JOIN(joinSegment);
                            tempSet.add(joinSegment);
                        }
                    }
                }
                tempSet = null;
            }
            MergeSegments segments = ew.getExpression();
            if(segments != null){
                String normalSql = segments.getNormal().getSqlSegment();
                if(V.notEmpty(normalSql)){
                    WHERE(formatNormalSql(normalSql));
                    // 动态为主表添加is_deleted=0
                    String isDeletedCol = ParserCache.getDeletedColumn(wrapper.getEntityTable());
                    String isDeletedSection = "self."+ isDeletedCol;
                    if(isDeletedCol != null && !QueryBuilder.checkHasColumn(segments.getNormal(), isDeletedSection)){
                        WHERE(isDeletedSection+ " = " +BaseConfig.getActiveFlagValue());
                    }
                }
                // 存在联表且无where条件，
                else if(V.notEmpty(annoJoinerList)){
                    // 动态为主表添加is_deleted=0
                    String isDeletedCol = ParserCache.getDeletedColumn(wrapper.getEntityTable());
                    String isDeletedSection = "self."+ isDeletedCol;
                    if(isDeletedCol != null && !QueryBuilder.checkHasColumn(segments.getNormal(), isDeletedSection)){
                        WHERE(isDeletedSection+ " = " +BaseConfig.getActiveFlagValue());
                    }
                }
            }
        }}.toString();
    }

    /**
     * 格式化sql select列语句
     * @param sqlSelect
     * @return
     */
    private String formatSqlSelect(String sqlSelect, Class<?> entityClass, Page<?> page){
        List<String> columnList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        PropInfo propInfo = BindingCacheManager.getPropInfoByClass(entityClass);
        if(V.isEmpty(sqlSelect)) {
            for(Field field : BeanUtils.extractAllFields(entityClass)){
                String column = propInfo.getColumnByField(field.getName());
                if(V.isEmpty(column)){
                    continue;
                }
                BindQuery bindQuery = field.getAnnotation(BindQuery.class);
                if((bindQuery == null || !bindQuery.ignoreSelect()) && !V.equals(column, propInfo.getDeletedColumn())) {
                    columnList.add("self." + column);
                }
            }
        }
        else {
            for(String column : S.split(sqlSelect)){
                column = S.removeDuplicateBlank(column).trim();
                String fieldName = propInfo.getFieldByColumn(column);
                Field field = BeanUtils.extractField(entityClass, fieldName);
                BindQuery bindQuery = field.getAnnotation(BindQuery.class);
                if(bindQuery == null || !bindQuery.ignoreSelect()) {
                    columnList.add("self." + column);
                }
            }
        }
        sb.append(S.join(columnList));
        if(page != null && page.orders() != null) {
            for(OrderItem orderItem : page.orders()){
                if(!columnList.contains(orderItem.getColumn())) {
                    sb.append(CommonConstant.SEPARATOR_COMMA).append(orderItem.getColumn()).append(" AS ").append(S.replace(orderItem.getColumn(), ".", "_")).append(PLACEHOLDER_COLUMN_FLAG);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 格式化where条件的sql
     * @param normalSql
     * @return
     */
    private String formatNormalSql(String normalSql){
        if(normalSql.startsWith("(") && normalSql.endsWith(")")){
            return S.substring(normalSql,1,normalSql.length()-1);
        }
        return normalSql;
    }

}

