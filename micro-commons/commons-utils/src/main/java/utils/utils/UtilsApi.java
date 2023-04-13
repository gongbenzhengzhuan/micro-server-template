package utils.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 *
 * @author lizhongpeng
 */
@Slf4j
public class UtilsApi {
    private static final String REGEX_MOBILE = "^((1[3-9][0-9]))\\d{8}$";
    private static ObjectMapper mapper = new CustomObjectMapper();
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    //    private static long workId = getServerWorkId(null, true);
//    private static long centerId = getServerWorkId(null, false);
    private static long workId = 3L;
    private static long centerId = 3L;
    private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(workId, centerId);

    /**
     * 功能简述
     * 获取配置文件key参数
     *
     * @param fileName 配置文件名
     * @param key      配置文件中的键
     * @return
     * @author lizhongpeng
     * @date 2015年5月23日 上午10:18:56
     */
    public static String getValueByKey(final String fileName, final String key) {
        final PropertyResourceBundle pb = (PropertyResourceBundle) ResourceBundle.getBundle(fileName);
        return pb.getString(key);
    }

    /**
     * 获取随机UUID
     *
     * @return
     */
    public static String getUUIDStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 功能简述
     * 正则手机号码匹配
     *
     * @param
     * @author lizhongpeng
     * @date 2018/4/2 18:58
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 判断格式为邮箱
     *
     * @param email
     * @return
     * @author lizhongpeng
     */
    public static boolean isEmail(String email) {
        if (isNull(email)) {
            return false;
        } else {
            String reg = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
            if (email.matches(reg)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 验证字符串是否为null或空字符串
     * 如果字符串中包含字符（包括空格），返回false
     *
     * @param str
     * @return
     * @author lizhongpeng
     * @author lizhongpeng 2018-08-13 上午10:18
     */
    public static boolean isNull(String str) {
        if (null == str) {
            return true;
        }
        if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串是否为null或空字符串
     * 如果字符串中包含字符（包括空格），返回true
     *
     * @param str
     * @return
     * @author lizhongpeng
     * @author lizhongpeng 2018-08-13 上午10:18
     */
    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * 验证任意的对象为null或为空字符（仅支持基本数据类型）
     *
     * @param obj
     * @return
     * @author lizhongpeng
     * @author lizhongpeng 2018-08-13 上午10:19
     */
    public static boolean isNull(Object obj) {
        if (null == obj || "".equals(obj))
            return true;
        return false;
    }

    /**
     * 验证任意的对象不为null并且不为空字符（仅支持基本数据类型）
     *
     * @param obj
     * @return
     * @author lizhongpeng
     * @author lizhongpeng 2018-08-13 上午10:19
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 判断map集合是否为null或empty
     * null或empty返回true，其他返回false
     *
     * @param map
     * @return
     * @author lizhongpeng
     */
    public static boolean isMapNull(Map<?, ?> map) {
        if (map == null || map.isEmpty())
            return true;
        return false;
    }

    /**
     * 判断map集合不为null或empty
     *
     * @param map
     * @return
     * @author lizhongpeng
     */
    public static boolean isNotNullForMap(Map<?, ?> map) {
        return !isMapNull(map);
    }

    /**
     * 判断集合是否为null或empty
     * null或empty返回true，其他返回false
     *
     * @param list
     * @return
     * @author lizhongpeng
     */
    public static boolean isListNull(List<?> list) {
        if (list == null || list.isEmpty() || list.size() == 0)
            return true;
        return false;
    }

    /**
     * 判断list集合不为null或empty
     *
     * @param list
     * @return
     * @author lizhongpeng
     */
    public static boolean isNotNullForList(List<?> list) {
        return !isListNull(list);
    }

    /**
     * 将对象转变成Long类型，如果obj为空则返回Long类型的最小值-9223372036854775808
     *
     * @param obj
     * @return
     * @author lizhongpeng
     * @author lizhongpeng 2018-08-14 上午10:47
     */
    public static Long toLong(Object obj) {
        if (obj == null || obj.toString().trim().equals("null") || obj.toString().trim().length() == 0) {
            return Long.parseLong("0");
        } else if (obj instanceof Long) {
            return (Long) obj;
        } else {
            return Long.parseLong(obj.toString());
        }
    }

    /**
     * 将对象转换为Double
     * 将对象转换为double, NULL返回0.0
     *
     * @param obj 对象
     * @return double
     * @author lizhongpeng 2018-08-14 上午10:47
     */
    public static double toDouble(Object obj) {
        double i = 0.0;
        if (obj == null || "".equals(obj.toString().trim())) {
            return i;
        }
        String str = obj.toString();
        try {
            i = Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 保留指定个数num位的小数点
     *
     * @param d
     * @param num
     * @return
     * @author lizhongpeng
     */
    public static double toDouble(double d, int num) {
        BigDecimal b = new BigDecimal(d);
        return b.setScale(num, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 将对象类型转成字符串类型
     *
     * @param obj
     * @return
     * @author lizhongpeng 2018-08-14 14:46
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        } else if (obj instanceof String) {
            return (String) obj;
        } else {
            return obj.toString();
        }
    }

    /**
     * 将对象转换为int
     * 将对象转换为int, NULL返回0
     *
     * @param obj 对象
     * @return int
     * @author lizhongpeng 2018-08-16 11:06
     */
    public static int toInt(Object obj) {
        int i = 0;
        if (obj == null || "".equals(obj.toString().trim())) {
            return i;
        }
        if (obj instanceof Integer) {
            i = (Integer) obj;
        } else if (obj instanceof Long) {
            i = Integer.parseInt(String.valueOf(((Long) obj)));
        } else if (obj instanceof Double) {
            i = (int) ((Double) obj).doubleValue();
        } else {
            String str = obj.toString();
            // 判断是否是浮点数格式，如果是去除小数部分
            if (str.indexOf(".") > -1) {
                str = str.substring(0, str.indexOf("."));
            }
            try {
                i = Integer.valueOf(str);
            } catch (Exception e) {
                // 类型转换异常
                e.printStackTrace();
            }
        }
        return i;
    }

    /**
     * 转换成BigInteger类型
     *
     * @param content
     * @return
     */
    public static BigInteger toBigInteger(String content) {
        try {
            return new BigInteger(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger("0");
    }


    /**
     * 判断是否为中文字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 任意对象转换成JSON格式字符串
     *
     * @param o
     * @param <T>
     * @return
     * @author lizhongpeng 2018-07-10 下午2:58
     */
    public static <T> String toJson(T o) {
        if (o == null) {
            return null;
        }
        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            log.error("toJson Error", e);
            return null;
        }
    }

    /**
     * 获得当前日期的方法yyyy-MM-dd格式
     *
     * @return
     */
    public static String getCurrDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 取格式化的系统当前时间，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantUtils.FT_TIME);
        return sdf.format(new Date());
    }

    /**
     * 将字符串类型日期转换成日期（格式：yyyy-MM-dd)
     *
     * @param dateString
     * @return
     */
    public static Date getDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 将字符串类型日期转换成日期（格式：yyyy-MM-dd hh:mm:ss)
     *
     * @param dateString
     * @return
     */
    public static Date getDatetime(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantUtils.FT_TIME);
        Date date;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线, 效率比上面高
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr
     * @return
     */
    public static String formatJson(String jsonStr) {
        int level = 0;
        StringBuffer jsonFormatStr = new StringBuffer();
        jsonFormatStr.append("\n");
        for (int index = 0; index < jsonStr.length(); index++) {
            char c = jsonStr.charAt(index);
            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonFormatStr.charAt(jsonFormatStr.length() - 1)) {
                jsonFormatStr.append(getLevelStr(level));
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonFormatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonFormatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonFormatStr.append("\n");
                    level--;
                    jsonFormatStr.append(getLevelStr(level));
                    jsonFormatStr.append(c);
                    break;
                default:
                    jsonFormatStr.append(c);
                    break;
            }
        }
        return jsonFormatStr.toString();
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    /**
     * 计算SQL查询起始索引值
     *
     * @param total
     * @param pageSize
     * @param currentPageNum
     * @return
     */
    public static int countListStart(int total, int pageSize, int currentPageNum) {
        if (pageSize < 1) {
            pageSize = 10;
        }
        if (currentPageNum < 1) {
            currentPageNum = 1;
        }
        int pageCount = total / pageSize;
        if (total % pageSize > 0) {
            pageCount++;
        }
        if (pageCount == 0) {
            pageCount = 1;
        }
        if (currentPageNum >= pageCount) {
            currentPageNum = pageCount;
        }
        return (currentPageNum - 1) * pageSize;
    }

    /**
     * 打印服务启动信息
     *
     * @param environment
     * @author lizhongpeng 2022/05/19 10:05
     */
    public static void printBannerInfo(Environment environment) {
        String banner = "-----------------------------------------\n" +
                ":: 服务启动成功:: " + getCurrDatetime() + "\n" +
                ":: 服务名称:: " + environment.getProperty("spring.application.name") + "\n" +
                ":: 端口号:: " + environment.getProperty("server.port") + "\n" +
                "-----------------------------------------";
        System.out.println(banner);
    }

    /**
     * 计算总页数
     *
     * @author lizhongpeng 2022/07/25 08:07
     */
    public static int countTotalPage(int totalCount, int pageSize) {
        if (totalCount < 0) {
            return 0;
        }
        int count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        if (count == 0) {
            count = 1;
        }
        return count;
    }

    /**
     * 计算时间区间
     *
     * @param domain 0:本周 1:本月
     * @return {@link List<String>}
     * @author lizhongpeng 2022/09/26 18:09
     */
    public static List<String> getDateList(Integer domain) {
        LocalDate localDate = LocalDate.now();
        LocalDate lv = null;
        int val = 0;
        List<String> list = new ArrayList<>();
        switch (domain) {
            case 0://本周
            {
                val = localDate.getDayOfWeek().getValue();
                break;
            }
            case 1://本月
            {
                val = localDate.getDayOfMonth();
                break;
            }
        }
        for (int i = val; i > 0; i--) {
            lv = localDate.minusDays(i - 1);
            list.add(lv.toString());
        }
        return list;
    }

    /**
     * 创建线程池
     *
     * @param name
     * @param corePoolSize
     * @param maxPoolSize
     * @return
     */
    public static Executor createExecutor(String name, int corePoolSize, int maxPoolSize) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix(name);
        executor.setKeepAliveSeconds(20);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 取默认路径
     *
     * @return java.lang.StringBuilder
     * @author lizhongpeng
     * @date 2021/08/11 15:08
     * @params []
     */
    public static String getTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 获取本机ID
     *
     * @return
     */
//    public static long getServerWorkId(String addressConfig, boolean forWorkId) {
//        try {
//            String address = isNull(addressConfig) ? Inet4Address.getLocalHost().getHostAddress() : addressConfig;
//            if (UtilsApi.isNotNull(address)) {
//                int[] ints = org.apache.commons.lang3.StringUtils.toCodePoints(address);
//                int sums = 0;
//                for (int b : ints) {
//                    sums += b;
//                }
//                long vid = (long) (sums % 32);
//                if (forWorkId) {
//                    return vid;
//                }
//                if (vid > 0 || vid <= 31) {
//                    vid = vid - 1;
//                }
//                if (vid == 0) {
//                    vid = vid + RandomUtils.nextLong(2, 30);
//                }
//                return vid;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        long workIdv = RandomUtils.nextLong(0, 31);
//        if (forWorkId) {
//            return workIdv;
//        }
//        if (workIdv > 0 || workIdv <= 31) {
//            workIdv = workIdv - 1;
//        }
//        if (workIdv == 0) {
//            workIdv = workIdv + RandomUtils.nextLong(2, 30);
//        }
//        return workIdv;
//    }

    /**
     * 生产Long类型唯一ID
     *
     * @return
     */
    public static long createUniqueId() {
        return idWorker.nextId();
    }

    public static void main(String[] args) {
        //生产Long类型唯一ID
        System.out.println(createUniqueId());
        //取默认路径
        System.out.println(getTempDir());
//计算时间区间
        System.out.println(getDateList(1));
        //时间格式转换
        System.out.println(getDatetime("2022-10-10 10:10:10"));
        //判断是否是中文
        System.out.println(isChinese('A'));
        //判断链表是否是空
        List<String> resultList = Lists.newArrayList();
        System.out.println(isNotNullForList(resultList));
        System.out.println(isListNull(resultList));
        //生成uuid
        System.out.println(getUUIDStr());
        //判断映射表是否未空
        Map<String,String> maps = new HashMap<>();
        System.out.println(isNotNullForMap(maps));
        System.out.println(isMapNull(maps));

    }
}
