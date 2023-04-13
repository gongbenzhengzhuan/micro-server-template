package utils.utils;

/**
 * 常量定义类
 *
 * @author lizhongpeng  2020/06/16 19:25
 */
public class ConstantUtils {

    public static final String TOKEN = "Token";
    /**
     * 正序排序
     */
    public static final String ORDER_BY_ASC = " ASC ";
    /**
     * 倒序排序
     */
    public static final String ORDER_BY_DESC = " DESC ";

    /**
     * 默认错误提示信息
     */
    public static final String ERROR_MSG = "当前服务繁忙，请稍后重试";
    /**
     * 参数错误提示
     */
    public static final String MSG_INVALID_PARAMS = "参数错误";

    public static final String SUCCESSED = "操作成功";

    public static final String FT_TIME = "yyyy-MM-dd HH:mm:ss";

    /*系统通用KEY定义*/
    //用户ID定义
    public static final String KEY_USER_ID = "userId";

    //es索引名称
    public static final String INDEX_ARTICLE="open_source_article";

    public static final String ARTICLE_SUMMARY="article_summary";

    /**
     * 数据总量 redis 缓存KEY
     */
    public static final String KEY_TOTAL_COUNT = "key_total_count";
    /**
     * 采集数据同步缓存KEY
     */
    public static final String KEY_SYNC_STARTTIME = "keySysStartTime";

    public static final String PRETAGS="<span style='color:red'>";
    public static final String POSTTAGS="</span>";
}
