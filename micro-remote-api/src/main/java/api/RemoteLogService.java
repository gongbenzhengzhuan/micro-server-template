package api;

import api.entity.SearchLogDTO;
import api.entity.SystemLogInsertDTO;
import api.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import utils.vo.Result;

import java.util.List;


/**
 * @author sgz
 * @date 2023/3/28 10:22
 * @Deception 日志服务
 */
@SuppressWarnings("all")
@FeignClient(url ="http://101.37.117.226:8089/",value = "micro-manage", path = "/datasystem-manage", fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 11:03:03
     * @Description 存储搜索日志（每行）
     * @Param[0] com.zyc.datasystem.log.dto.SearchLogDTO dto
     * @Return void
     */
    @PostMapping("/workbenchSearchForLog/storeSearchLog")
    public void storeSearchLog(@RequestBody SearchLogDTO dto);

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 11:03:03
     * @Description 存储搜索日志（批量）
     * @Param[0] java.util.List<com.zyc.datasystem.api.entity.SearchLogDTO> dtoList
     * @Return void
     */
    @PostMapping("/workbenchSearchForLog/storeSearchLogList")
    public void storeSearchLogList(@RequestBody List<SearchLogDTO> dtoList);

    /**
     * 新增日志
     *
     * @param dto 日志入参
     * @return 是否成功
     */
    @PostMapping(value = "/systemLog/insert")
    Result<Boolean> insert(@RequestBody SystemLogInsertDTO dto);
}
