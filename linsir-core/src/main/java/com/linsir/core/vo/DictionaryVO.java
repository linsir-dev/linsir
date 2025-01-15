package com.linsir.core.vo;

import com.linsir.core.entity.Dictionary;
import lombok.Data;

import java.util.List;

/**
 * @author ：linsir
 * @date ：Created in 2022/3/23 15:20
 * @description：数据字典的VO，附带子项定义children
 * @modified By：
 * @version: 0.0.1
 */
@Data
public class DictionaryVO extends Dictionary {

    private List<Dictionary> children;
}
