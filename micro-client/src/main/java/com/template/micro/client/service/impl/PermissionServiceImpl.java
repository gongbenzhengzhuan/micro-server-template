package com.template.micro.client.service.impl;

import com.template.micro.client.entity.Permission;
import com.template.micro.client.mapper.PermissionMapper;
import com.template.micro.client.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2023-05-07
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
