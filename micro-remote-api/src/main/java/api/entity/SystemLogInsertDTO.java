package api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author sgz
 * @date 2023/3/28 10:50
 * @Deception 日志服务远程入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemLogInsertDTO {

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("所属角色名称")
    private String roleName;

    @ApiModelProperty("所属部门名称")
    private String department;

    @ApiModelProperty("子系统")
    private String subsystem;

    @ApiModelProperty("所属模块")
    private String module;

    @ApiModelProperty("操作类型")
    private String operationType;

    @ApiModelProperty("操作结果")
    private String operationResult;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("key号")
    private String keyNumber;

    @ApiModelProperty("操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//入参
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//出参
    private Date operationTime;

    @ApiModelProperty("操作日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")//入参
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//出参
    private Date operationDate;

    @ApiModelProperty("操作内容")
    private String operationContent;

}
