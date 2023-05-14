package com.template.micro.client.controller;

import com.template.micro.client.entity.SystemLog;
import com.template.micro.client.service.ISystemLogService;
import gateway.entity.UserInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import log.enumeration.OperationTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author dzl
 * @since 2023-3-05
 */
@Slf4j
@Api(tags = {"[管理端-标签管理], tags = [标签管理], description = [标签管理]"})
@RestController
@RequestMapping("/${api-prefix}labelManage")
public class TemplateController {
    @Autowired
    ISystemLogService iSystemLogService;

    /**
     * 查看标签
     *
     * @param id
     * @return
     */
  //  @AuditLog(subSystemName = SubSystemEnum.USER_SIDE_DATA_STORAGE_MANAGE_PLATFORM, moduleName = "工作台-特征词client", operationType = OperationTypeEnum.SELECT_OPERATION, operationContent = "特征词client")
    @ApiOperation(value = "特征词")
    @GetMapping(value = "/getLabelFeature")
    public Result<List<SystemLog>> getLabelFeature(HttpServletRequest request,@RequestParam("id") List<Integer> id) {

        //模拟认证
//        HttpSession session = request.getSession();
//        String token = (String) session.getAttribute("token");
//        System.out.println("token:"+token);
//        if (!"adminadmin".equals(token)) {
//            System.out.println("用户没有登录，请重新登录");
//            return new Result<List<SystemLog>>().code(204);
//        }

      //  System.out.println("用户已经登录，可以正常访问");
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setKeyNum("go222");
        System.out.println(userInfoDTO.getKeyNum());
        System.out.println(OperationTypeEnum.SELECT_OPERATION);
        //        int a =1/0;
        System.out.println("success");
        return new Result<List<SystemLog>>().success(iSystemLogService.getSystemLog(id));
    }
}
