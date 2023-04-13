package log.annotation;

import log.enumeration.SysDicDataEnum;

import java.lang.annotation.*;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-28 10:06 星期二
 * @ClassName com.zyc.datasystem.log.annotation.SearchLog
 * @Description: 搜索日志注解
 */
@SuppressWarnings("all")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchLog {

    /** 搜索发起的应用 */
    SysDicDataEnum.Application application() default SysDicDataEnum.Application.$000;

    /** 搜索分类，默认全文检索 */
    SysDicDataEnum.SearchCategory searchCategories() default SysDicDataEnum.SearchCategory.$201;

    /** 搜索关键词key */
    String searchKeywordsKey() default "searchKeywords";

    /** 搜索的操作内容 */
    String operationContent() default "未知操作内容";
}
