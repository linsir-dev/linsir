/*
 * Copyright (c) 2015-2021, www.dibo.ltd (service@dibo.ltd).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.linsir.core.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.linsir.core.mybatis.config.Cons;
import com.linsir.core.mybatis.util.BeanUtils;
import com.linsir.core.mybatis.util.ContextHolder;
import com.linsir.core.mybatis.util.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * Entity抽象父类
 * @author Yangzhao@dibo.ltd
 * @version v2.0
 * Copyright © diboot.com
 */
@Getter @Setter @Accessors(chain = true)
public abstract class AbstractEntity<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 10202L;

    /**
     * 默认主键id，类型为String型雪花算法ID
     */
    @TableId(type = IdType.ASSIGN_ID)
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
        if(Cons.FieldName.id.name().equals(pk)){
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
}
