package com.linsir.core.mybatis.data.query;


import com.linsir.core.binding.query.Comparison;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * description：查询条件条目
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:36
 */
@Getter
@Setter
@Accessors(chain = true)
public class CriteriaItem extends BaseCriteria {
    private static final long serialVersionUID = -2342876399137671211L;

    private String joinTable;

    private String onLink;

    private String onWhere;

    private CriteriaItem or;

    public CriteriaItem() {
    }

    public CriteriaItem(BaseCriteria baseCriteria) {
        this.field = baseCriteria.field;
        this.comparison = baseCriteria.comparison;
        this.value = baseCriteria.value;
    }

    public CriteriaItem(String field, Object value) {
        super(field, value);
    }

    public CriteriaItem(String field, Comparison comparison, Object value) {
        super(field, comparison, value);
    }

    public CriteriaItem update(Comparison comparison, Object value) {
        this.comparison = comparison.name();
        this.value = value;
        return this;
    }

    public CriteriaItem joinOn(String joinTable, String onLink, String onWhere) {
        this.joinTable = joinTable;
        this.onLink = onLink;
        this.onWhere = onWhere;
        return this;
    }

    /**
     * 追加or条件
     * @param field
     * @param value
     * @return
     */
    public CriteriaItem or(String field, Object value) {
        this.or = new CriteriaItem(field, value);
        return this;
    }

    /**
     * 追加or条件
     * @param field
     * @param comparison
     * @param value
     * @return
     */
    public CriteriaItem or(String field, Comparison comparison, Object value) {
        this.or = new CriteriaItem(field, comparison, value);
        return this;
    }

}
