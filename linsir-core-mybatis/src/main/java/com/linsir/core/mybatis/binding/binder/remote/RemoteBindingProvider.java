package com.linsir.core.mybatis.binding.binder.remote;

import com.linsir.core.vo.jsonResults.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * description：远程绑定Provider接口
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:02
 */
public interface RemoteBindingProvider {
    /**
     * 加载请求数据
     * @param remoteBindDTO
     * @return
     */
    @PostMapping("/common/remote-binding")
    JsonResult<String> loadBindingData(RemoteBindDTO remoteBindDTO);
}
