package com.micro.manage;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;

public class CodeGenerator {
    public static void main(String[] args) {

        final String sqlUrl = "jdbc:mysql://101.37.117.226:3306/xjd_039_zyc_service?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8";
        final String sqlUserName = "root";
        final String sqlPass = "123456";
        final String packageName = "com.micro.manage";
        final String outPut = "/micro-manage/src/main/java";
        final String tables = "system_log";//"表

        // 数据源配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig
                .Builder(
                sqlUrl,
                sqlUserName,
                sqlPass);//内部会自行判断数据库类型
        // 全局配置
        GlobalConfig.Builder globalConfigBuilder = new GlobalConfig.Builder();
        // 代码生成目录
        String projectPath = System.getProperty("user.dir");
        globalConfigBuilder.outputDir(projectPath + outPut);

        // 作者
//        globalConfigBuilder.author("");
        // 结束时是否打开文件夹
//        globalConfigBuilder.openDir(false);
        globalConfigBuilder.disableOpenDir();
        // 是否覆盖旧的文件
        //globalConfigBuilder.fileOverride();
        // 实体属性Swagger2注解
        globalConfigBuilder.enableSwagger();
        globalConfigBuilder.dateType(DateType.ONLY_DATE);
        // 包配置，如模块名、实体、mapper、service、controller等
        PackageConfig.Builder packageConfigBuilder = new PackageConfig.Builder();
        packageConfigBuilder.parent(packageName);
        packageConfigBuilder.entity("entity");
        packageConfigBuilder.mapper("mapper");
        packageConfigBuilder.service("service");
        packageConfigBuilder.serviceImpl("service.impl");
        //packageConfigBuilder.serviceImpl("serviceImpl");
        packageConfigBuilder.controller("controller");

        //生成dto时候放开
//        packageConfigBuilder.other("dto");
//        InjectionConfig.Builder injectionConfigBuilder = new InjectionConfig.Builder();
//        injectionConfigBuilder.beforeOutputFile((tableInfo, objectMap) -> {
//            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
//        })
//        .customFile(
//                Collections.singletonMap(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tables)+"DTO.java", "/templates/dto.vm")
//        );
        //生成dto时候放开

        // 策略配置
        StrategyConfig.Builder strategyConfigBuilder = new StrategyConfig.Builder();
        // 设置需要映射的表名
        strategyConfigBuilder.addInclude(tables);//字符串数组，可以生成多个表

        // 下划线转驼峰
        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.entityBuilder().columnNaming(NamingStrategy.underline_to_camel);
        // 去除前缀"t_"
        //strategyConfigBuilder.addTablePrefix("t_");

        // entity的Lombok
        strategyConfigBuilder.entityBuilder().enableLombok();

        // 逻辑删除
        strategyConfigBuilder.entityBuilder().logicDeleteColumnName("deleted");
        strategyConfigBuilder.entityBuilder().logicDeletePropertyName("deleted");

        // 创建时间
        IFill gmtCreate = new Column("create_time", FieldFill.INSERT);
        // 更新时间
        IFill gmtModified = new Column("update_time", FieldFill.INSERT_UPDATE);
        strategyConfigBuilder.entityBuilder().addTableFills(gmtCreate, gmtModified);

        // 乐观锁
        //strategyConfigBuilder.entityBuilder().enableSerialVersionUID();
        //strategyConfigBuilder.entityBuilder().versionColumnName("version");
        //strategyConfigBuilder.entityBuilder().versionPropertyName("version");

        // 使用Restful风格的Controller
        strategyConfigBuilder.controllerBuilder().enableRestStyle();

        // 将请求地址转换为驼峰命名，如 http://localhost:8080/hello_id_2
        //strategyConfigBuilder.controllerBuilder().enableHyphenStyle();

        // 创建代码生成器对象，加载配置
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfigBuilder.build());
        autoGenerator.global(globalConfigBuilder.build());
        autoGenerator.packageInfo(packageConfigBuilder.build());
        autoGenerator.strategy(strategyConfigBuilder.build());
        //autoGenerator.injection(injectionConfigBuilder.build());
        // 执行
        autoGenerator.execute();
    }
}
