package com.template.micro.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.template.micro.client.dto.UserDTO;
import com.template.micro.client.entity.User;
import com.template.micro.client.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.*;
import utils.vo.Result;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2023-05-06
 */
@Api(tags = {"[管理端-用户], tags = [标签管理], description = [标签管理]"})
@RestController
@RequestMapping("/user")
public class UserController {
    //增删改查
    @Autowired
    private IUserService iUserService;

    @ApiOperation(value = "用户新增")
    @PostMapping(value = "/userInsert")
    public Result<String> insert(@RequestBody User user) {
//        User user = new User();
//        BeanCopier.create(UserDTO.class, User.class, false)
//                .copy(userDTO, user, null);
        iUserService.saveOrUpdate(user);
        return new Result<String>().success("用户新增成功");
    }

    @ApiOperation(value = "用户删除")
    @GetMapping(value = "/userDelete")
    public Result<String> delete(@RequestParam("id") List<Integer> id) {
        iUserService.removeBatchByIds(id);
        return new Result<String>().success("用户删除");
    }

    @ApiOperation(value = "用户分页查询")
    @PostMapping(value = "/pageList")
    public Result<List<User>> findListByPage(@RequestBody UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
       // queryWrapper.eq("login", userDTO.getLogin());
        IPage<User> page = iUserService.page(new Page<>(userDTO.getPageIndex(), userDTO.getPageSize()), queryWrapper);
        return new Result<List<User>>().success(page.getRecords()).total((int) page.getTotal());
    }

}

