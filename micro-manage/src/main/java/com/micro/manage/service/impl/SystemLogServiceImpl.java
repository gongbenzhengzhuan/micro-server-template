package com.micro.manage.service.impl;

import com.micro.manage.entity.SystemLog;
import com.micro.manage.mapper.SystemLogMapper;
import com.micro.manage.service.ISystemLogService;
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
