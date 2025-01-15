
package com.linsir.core.extension;

import com.linsir.core.util.IdGenerator;
import com.linsir.core.vo.LabelValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 序列号生成器的雪花id字符串生成实现
 * @author mazc@dibo.ltd
 * @version v3.1.1
 * @date 2023/10/07
 */
@Slf4j
@Component
public class SnowflakeIdGenerator implements AutoFillHandler {

    @Override
    public LabelValue definition() {
        return new LabelValue("雪花ID生成器", "SnowflakeID");
    }

    @Override
    public String buildFillValue(Map<String, Object> entityDataMap) {
        return IdGenerator.nextIdStr();
    }

}
