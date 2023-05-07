package com.template.micro.client.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.template.micro.client.dto.RoleDTO;
import com.template.micro.client.dto.UserDTO;
import com.template.micro.client.entity.Role;
import com.template.micro.client.entity.User;
import com.template.micro.client.service.IRoleService;
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
 * @since 2023-05-07
 */
@Api(tags = {"[管理端-角色], tags = [标签管理], description = [标签管理]"})
@RestController
@RequestMapping("/role")
public class RoleController {
    //增删改查
    @Autowired
    private IRoleService iRoleService;

    @ApiOperation(value = "角色新增")
    @PostMapping(value = "/roleInsert")
    public Result<String> insert(@RequestBody Role role) {
//        Role role = new Role();
//        BeanCopier.create(RoleDTO.class, Role.class, false)
//                .copy(roleDTO, role, null);
        iRoleService.saveOrUpdate(role);
        return new Result<String>().success("角色新增成功");
    }

    @ApiOperation(value = "角色删除")
    @GetMapping(value = "/roleDelete")
    public Result<String> delete(@RequestParam("id") List<Integer> id) {
        iRoleService.removeBatchByIds(id);
        return new Result<String>().success("角色删除");
    }

    @ApiOperation(value = "角色分页查询")
    @PostMapping(value = "/pageList")
    public Result<List<Role>> findListByPage(@RequestBody RoleDTO roleDTO) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("role", roleDTO.getRole());
        IPage<Role> page = iRoleService.page(new Page<>(roleDTO.getPageIndex(), roleDTO.getPageSize()), queryWrapper);
        return new Result<List<Role>>().success(page.getRecords()).total((int) page.getTotal());
    }
}

