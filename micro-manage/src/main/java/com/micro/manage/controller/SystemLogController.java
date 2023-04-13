package com.micro.manage.controller;


import api.entity.SystemLogInsertDTO;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micro.manage.dto.SearchLogDTO;
import com.micro.manage.entity.SearchLog;
import com.micro.manage.entity.SystemLog;
import com.micro.manage.service.ISystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utils.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author fangxin
 * @since 2023-01-12
 */
@RestController
@Api(value = "[系统审计日志]", tags = "[系统审计日志]", description = "[系统审计日志]")
@RequestMapping("/${api-prefix}systemLog")
public class SystemLogController {

    @Autowired
    private ISystemLogService systemLogService;

    /**
     * 内部调用，不对外暴露
     *
     * @param dto 入参
     * @return 是否成功
     */
    @ApiOperation(value = "新增日志只远程调用，不对外提供", hidden = true)
    @PostMapping(value = "/insert")
    public Result<Boolean> insert(@RequestBody SystemLogInsertDTO dto) {
        Result<Boolean> result = new Result<>();
        SystemLog systemLog = new SystemLog();
        BeanCopier.create(SystemLogInsertDTO.class, SystemLog.class, false)
                .copy(dto, systemLog, null);
        return systemLogService.save(systemLog) ? result.success(true) : result.failure("日志新增失败");
    }

}
