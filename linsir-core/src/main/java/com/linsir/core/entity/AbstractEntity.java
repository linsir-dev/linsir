package com.linsir.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linsir.core.tool.constant.CommonConstant;
import com.linsir.core.util.BeanUtils;
import com.linsir.core.util.ContextHolder;
import com.linsir.core.util.JSON;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author linsir
 * @title: AbstractEntity
 * @projectName linsir
 * @description: Entity抽象父类
 * @date 2022/3/19 23:05
 *
 * 下面介绍几个我常用的 lombok 注解：
 * @Data   ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 * @Setter：注解在属性上:为属性提供 setting 方法,       注解再类上表示当前类中所有属性都生成setter方法
 * @Getter：注解在属性上：为属性提供 getting 方法， 注解再类上表示当前类中所有属性都生成getter方法
 * @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
 * @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
 * @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
 *
 *
 */
@Data
public abstract class AbstractEntity<T extends Serializable>  implements Serializable{

    private static final long serialVersionUID = -3213747504298736681L;
    /** 主键  默认主键字段id，类型为Long型雪花，转json时转换为String*/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private T id;


    /**
     * 获取主键值
     * @return
     */
    @JsonIgnore
    public Object getPrimaryKeyVal(){
        String pk = ContextHolder.getIdFieldName(this.getClass());
        if(pk == null){
            return null;
        }
        if(CommonConstant.FieldName.id.name().equals(pk)){
            return getId();
        }
        return BeanUtils.getProperty(this, pk);
    }


    /**
     * Entity对象转为String
     * @return
     */
    @Override
    public String toString(){
        return this.getClass().getName()+ ":"+this.getId();
    }

    /**
     * Entity对象转为map
     * @return
     */
    public Map<String, Object> toMap(){
        String jsonStr = JSON.stringify(this);
        return JSON.toMap(jsonStr);
    }
    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy ;


    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime ;
    /** 更新人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy ;

    /** 更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime ;

    public AbstractEntity setId(T id){
        this.id = id;
        return this;
    }

    public T getId(){
        return this.id;
    }


}
