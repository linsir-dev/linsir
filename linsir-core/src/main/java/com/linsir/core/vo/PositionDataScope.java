
package com.linsir.core.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位相关的数据范围
 * @author JerryMa
 * @version v2.6.0
 * @date 2022/5/9
 * Copyright © diboot.com
 */
@Getter @Setter
public class PositionDataScope implements Serializable {
    private static final long serialVersionUID = 3374139242516436636L;

    public PositionDataScope(){}

    public PositionDataScope(String positionId, String dataPermissionType, String userId, String orgId){
        this.positionId = positionId;
        this.dataPermissionType = dataPermissionType;
        this.userId = userId;
        this.orgId = orgId;
    }

    /**
     * 岗位id
     */
    private String positionId;

    /**
     * 数据权限范围
     */
    private String dataPermissionType;

    /**
     * 当前部门id
     */
    private String orgId;

    /**
     * 当前及子级别部门ids
     */
    private List<? extends Serializable> accessibleOrgIds;

    /**
     * 当前用户id
     */
    private String userId;

    /**
     * 当前及子级用户ids
     */
    private List<? extends Serializable> accessibleUserIds;
}
