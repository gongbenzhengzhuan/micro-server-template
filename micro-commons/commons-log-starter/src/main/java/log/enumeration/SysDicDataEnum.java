package log.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-28 16:50 星期二
 * @ClassName com.zyc.commons.log.enumeration.SysDicDataEnum
 * @Description: 数据字典枚举类（枚举数据应与数据库 dict_code 保持一致）对应表 sys_dict_data
 */
@SuppressWarnings("all")
public enum SysDicDataEnum {

    ;

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-29 10:03:00
     * @Description 所属应用
     */
    @Getter
    @AllArgsConstructor
    public enum Application {
        $000(000, "未知系统"),
        $101(101, "资源库");
        private Integer code;
        private String name;

        @Override
        public String toString() {
            return new StringBuilder("Application{")
                    .append("[Integer]code=")
                    .append(this.code)
                    .append(", [String]name=")
                    .append(this.name)
                    .append("}").toString();
        }
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-29 10:03:14
     * @Description 搜索分类
     */
    @Getter
    @AllArgsConstructor
    public enum SearchCategory {
        $201(201, "全文检索"),
        $202(202, "知识图谱搜索"),
        $203(203, "事实核查"),
        $204(204, "事理图谱"),
        $205(205, "元知识分析"),
        $206(206, "知识众包问答"),
        $207(207, "自然语言搜索");

        private Integer code;
        private String name;

        @Override
        public String toString() {
            return new StringBuilder("SearchCategory{")
                    .append("[Integer]code=")
                    .append(this.code)
                    .append(", [String]name=")
                    .append(this.name)
                    .append("}").toString();
        }
    }

    public static void main(String[] args) {
        Application $000 = Application.$000;
        System.out.println($000.toString());
        SearchCategory $201 = SearchCategory.$201;
        System.out.println($201.toString());
    }
}
