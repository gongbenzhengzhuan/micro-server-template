package com.template.micro.client.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.template.micro.client.dto.UserDTO;
import com.template.micro.client.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.template.micro.client.vo.UserVO;
import feign.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2023-05-06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage<List<UserVO>> getUser(Page<UserVO> page, @Param("dto") UserDTO userDTO);
}
