
package com.linsir.core.vo;

import com.linsir.core.binding.annotation.BindDict;
import com.linsir.core.entity.I18nConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 国际化配置 VO
 *
 * @author wind
 * @version v3.0.0
 * @date 2022-10-12
 */
@Getter
@Setter
@Accessors(chain = true)
public class I18nConfigVO extends I18nConfig {
    private static final long serialVersionUID = 5679642618572762054L;

    /**
     * 国际化类型label
     */
    @BindDict(field = "type", type = DICT_I18N_TYPE)
    private LabelValue typeLabel;
}
