package log.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author sgz
 * @date 2023/3/28 11:30
 * @Deception 审计类型枚举
 */
@ToString
@AllArgsConstructor
public enum OperationTypeEnum {

    // 未知类型
    UNKNOWN_OPERATION(0, "未知类型"),
    // 新增
    INSERT_OPERATION(1, "新增"),
    // 修改
    UPDATE_OPERATION(2, "修改"),
    // 删除
    DELETE_OPERATION(3, "删除"),
    // 导出
    EXPORT_OPERATION(4, "导出"),
    // 登陆
    LOGIN_OPERATION(5, "登录"),
    // 搜索
    SELECT_OPERATION(6, "查询"),
    // 审批
    APPROVAL_OPERATION(7, "审批");


    /**
     * 码值
     */
    @Getter
    private final Integer code;

    /**
     * 描述
     */
    @Getter
    private final String description;
}
