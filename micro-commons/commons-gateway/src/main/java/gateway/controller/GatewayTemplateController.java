package gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import log.annotation.AuditLog;
import log.enumeration.OperationTypeEnum;
import log.enumeration.SubSystemEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.vo.Result;

import java.util.List;


/**
 * @author dzl
 * @since 2023-3-05
 */
@Slf4j
@Api(tags = {"[管理端-标签管理], tags = [标签管理], description = [标签管理]"})
@RestController
@RequestMapping("/${api-prefix}labelManage/gateway/")
public class GatewayTemplateController {

    /**
     * 查看标签
     *
     * @param id
     * @return
     */
    @AuditLog(subSystemName = SubSystemEnum.USER_SIDE_DATA_STORAGE_MANAGE_PLATFORM, moduleName = "工作台-特征词", operationType = OperationTypeEnum.SELECT_OPERATION, operationContent = "特征词")
    @ApiOperation(value = "特征词")
    @GetMapping(value = "/getLabelFeature")
    public Result getLabelFeature(@RequestParam("id") List<Integer> id) {
        System.out.println("success");
        return new Result().success("success");
    }
}
