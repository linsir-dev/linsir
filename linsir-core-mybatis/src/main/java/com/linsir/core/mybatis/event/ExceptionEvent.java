/*
 * Copyright (c) 2015-2099, www.dibo.ltd (service@dibo.ltd).
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
package com.linsir.core.mybatis.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 系统异常事件
 * @author : JerryMa
 * @version : v3.6.0
 * @date 2024/12/16
 */
public class ExceptionEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1676095351766485923L;

    private Map<String, Object> msgMap;

    public ExceptionEvent(Map<String, Object> msgMap, Exception e) {
        super(e);
        this.msgMap = msgMap;
    }

    public Map<String, Object> getMsgMap() {
        return msgMap;
    }

}