package com.template.micro.client.service.impl;

import com.template.micro.client.entity.SystemLog;
import com.template.micro.client.mapper.SystemLogMapper;
import com.template.micro.client.service.ISystemLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2023-04-13
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements ISystemLogService {

}
