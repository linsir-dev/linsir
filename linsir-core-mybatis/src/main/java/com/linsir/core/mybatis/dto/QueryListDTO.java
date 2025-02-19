package com.linsir.core.mybatis.dto;


import lombok.Data;

/**
 * description：QueryListDTO
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/18 18:54
 */
@Data
public class QueryListDTO<DTO> {

    private DTO dto;

    private int pageIndex;

    private int pageSize;
}
