package com.micro.manage.controller;

import com.micro.manage.dto.SearchLogDTO;
import com.micro.manage.service.ISearchLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import log.annotation.AuditLog;
import log.enumeration.OperationTypeEnum;
import log.enumeration.SubSystemEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-24 17:31 星期五
 * @ClassName com.zyc.datasystem.manage.controller.SearchLogController
 * @Description: 搜索日志控制器
 */
@SuppressWarnings("all")
@RestController
@Api(value = "[搜索日志]", tags = "[搜索日志]", description = "[搜索日志]")
@RequestMapping("/${api-prefix}workbenchSearchForLog")
public class SearchLogController {

    @Autowired
    public ISearchLogService searchLogService;

    @AuditLog(subSystemName = SubSystemEnum.MANAGE_SIDE_DATA_STORAGE_MANAGE_PLATFORM, moduleName = "工作台-搜索日志", operationType = OperationTypeEnum.SELECT_OPERATION, operationContent = "分页查询搜索日志统计列表")
    @ApiOperation(value = "搜索日志分页查询统计列表")
    @PostMapping(value = "/pageList")
    public Result pageList(@RequestBody SearchLogDTO searchLogDTO) throws Exception {
        return searchLogService.pageList(searchLogDTO);
    }

    @AuditLog(subSystemName = SubSystemEnum.MANAGE_SIDE_DATA_STORAGE_MANAGE_PLATFORM, moduleName = "工作台-搜索日志", operationType = OperationTypeEnum.EXPORT_OPERATION, operationContent = "搜索日志批量导出")
    @ApiOperation(value = "搜索日志批量导出")
    @PostMapping(value = "/batchExport")
    public void batchExport(HttpServletResponse httpServletResponse, @RequestBody List<Integer> searchLogIds) throws Exception {
        searchLogService.batchExport(httpServletResponse, searchLogIds);
    }

    @PostMapping(value = "/storeSearchLog")
    public void storeSearchLog(@RequestBody SearchLogDTO searchLogDTO) throws Exception {
        searchLogService.storeSearchLog(searchLogDTO);
    }

    @PostMapping(value = "/storeSearchLogList")
    public void storeSearchLogList(@RequestBody List<SearchLogDTO> searchLogDTOs) throws Exception {
        searchLogService.storeSearchLogList(searchLogDTOs);
    }
}
