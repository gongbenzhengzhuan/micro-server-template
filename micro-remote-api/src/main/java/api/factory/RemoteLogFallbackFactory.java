package api.factory;

import api.RemoteLogService;
import api.entity.SearchLogDTO;
import api.entity.SystemLogInsertDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import utils.vo.Result;

import java.util.List;


/**
 * @author sgz
 * @date 2023/3/28 10:22
 * @Deception 日志服务降级处理
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {

    @Override
    public RemoteLogService create(Throwable throwable) {
        return new RemoteLogService() {
            @Override
            public void storeSearchLog(@RequestBody SearchLogDTO dto) {
                //出现异常，自定义返回内容，保证接口安全
                String message = throwable.getMessage();
                log.info(message);
            }
            @Override
            public void storeSearchLogList(@RequestBody List<SearchLogDTO> dto) {
                //出现异常，自定义返回内容，保证接口安全
                String message = throwable.getMessage();
                log.info(message);
            }

            @Override
            public Result<Boolean> insert(@RequestBody SystemLogInsertDTO dto) {
                log.error("日志服务调用失败，请查看原因:P{", throwable.getCause());
                return new Result().success(Boolean.FALSE);
            }
        };
    }
}
