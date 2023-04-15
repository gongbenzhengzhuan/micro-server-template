package com.template.micro.client.service;

import com.template.micro.client.entity.SystemLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author 作者
 * @since 2023-04-13
 */
public interface ISystemLogService extends IService<SystemLog> {
    public List<SystemLog> getSystemLog(List<Integer> ids);
}
