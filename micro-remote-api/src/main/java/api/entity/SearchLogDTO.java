package api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-25 10:04 星期六
 * @ClassName com.zyc.datasystem.manage.entity.SearchLog
 * @Description: 搜索日志数据传输对象
 */
@SuppressWarnings("all")
@ApiModel(value = "SearchLogDTO对象", description = "搜索日志对象")
@Data
public class SearchLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "当前页")
    public long pageIndex;

    @ApiModelProperty(value = "页数据量")
    public long pageSize;

    @ApiModelProperty("搜索人")
    private String userName;

    @ApiModelProperty("搜索关键字")
    private String searchKeywords;

    @ApiModelProperty("命中数量")
    private Integer searchNum;

    @ApiModelProperty("所属角色")
    private String roles;

    @ApiModelProperty("所属组织")
    private String department;

    @ApiModelProperty("发起应用")
    private String application;

    @ApiModelProperty("搜索分类")
    private String searchCategory;

    @ApiModelProperty("终端标识")
    private String terminalMarking;

    @ApiModelProperty("终端设备名")
    private String terminalEquipment;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("mac地址")
    private String macAddress;

    @ApiModelProperty("key号")
    private String keyNumber;

    @ApiModelProperty("操作时间")
    /** 入参 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 出参 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    @ApiModelProperty("操作内容")
    private String operationContent;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("插入有时间")
    /** 入参 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 出参 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改时间")
    /** 入参 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 出参 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
