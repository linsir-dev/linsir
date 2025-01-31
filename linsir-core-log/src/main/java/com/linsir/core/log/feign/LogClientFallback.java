
package com.linsir.core.log.feign;

import com.linsir.core.log.model.LogApi;
import com.linsir.core.log.model.LogError;
import com.linsir.core.log.model.LogUsual;
import com.linsir.core.log.vo.LogResult;
import com.linsir.core.results.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 日志fallback
 *
 * @author jiang
 */
@Slf4j
@Component
public class LogClientFallback implements ILogClient {

	@Override
	public R saveUsualLog(LogUsual log) {
		return LogResult.FAIL_EXCEPTION();
	}

	@Override
	public  R saveApiLog(LogApi log) {
		return LogResult.FAIL_EXCEPTION("usual log send fail");
	}

	@Override
	public   R saveErrorLog(LogError log) {
		return  LogResult.FAIL_EXCEPTION("usual log send fail");
	}
}
