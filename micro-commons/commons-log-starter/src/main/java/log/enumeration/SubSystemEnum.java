package log.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author sgz
 * @date 2023/3/31 17:33
 * @Deception 子系统枚举值：对应各个子系统，还有第三方的系统
 */
@ToString
@AllArgsConstructor
public enum SubSystemEnum {

    // 未知系统
    UNKNOWN_PLATFORM(0, "未知系统"),
    // 数据存储与管理平台-管理端
    MANAGE_SIDE_DATA_STORAGE_MANAGE_PLATFORM(1, "数据存储与管理平台-管理端"),
    // 数据存储与管理平台-用户端
    USER_SIDE_DATA_STORAGE_MANAGE_PLATFORM(2, "数据存储与管理平台-用户端");

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
