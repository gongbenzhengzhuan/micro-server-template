package com.template.micro.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.template.micro.client.dto.UserDTO;
import com.template.micro.client.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.template.micro.client.vo.UserVO;
import feign.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2023-05-06
 */
public interface IUserService extends IService<User> {
    IPage<List<UserVO>> getUser(UserDTO userDTO);
}
