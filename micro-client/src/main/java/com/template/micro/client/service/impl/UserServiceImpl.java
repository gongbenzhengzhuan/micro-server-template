package com.template.micro.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.template.micro.client.dto.UserDTO;
import com.template.micro.client.entity.User;
import com.template.micro.client.mapper.UserMapper;
import com.template.micro.client.service.IUserService;
import com.template.micro.client.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2023-05-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public IPage<List<UserVO>> getUser(UserDTO userDTO) {
        Page<UserVO> Page = new Page<>(userDTO.getPageIndex(), userDTO.getPageSize());
        Page.setOptimizeJoinOfCountSql(false);

        IPage<List<UserVO>> listIPage = userMapper.getUser(Page, userDTO);
        return listIPage;
    }
}
