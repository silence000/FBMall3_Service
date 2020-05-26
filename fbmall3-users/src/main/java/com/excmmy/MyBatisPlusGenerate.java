package com.excmmy;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class MyBatisPlusGenerate {

    public static void main(String[] args) {
        // 1. 创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2. 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir"); // 获取当前项目路径
        gc.setOutputDir(projectPath + "/fbmall3-users/src/main/java");
        gc.setAuthor("Excmmy"); // 设置作者
        gc.setOpen(false); // 自动生成后代码后是否自动打开资源资源管理器
        gc.setFileOverride(false); // 重新生成文件时是否覆盖
        gc.setIdType(IdType.AUTO); // 主键生成策略
        gc.setServiceName("%sService"); // 去掉自动生成接口的首字母I
        gc.setDateType(DateType.ONLY_DATE); // 设置日期类型
        gc.setSwagger2(true); // 自动生成Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 3. 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/fbmall3?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL); // 设置数据库类型
        mpg.setDataSource(dsc);

        // 4. 生成的包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(""); // 设置模块名
        pc.setParent("com.excmmy"); // 设置父模块名
        pc.setEntity("bean");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 5. 策略配置
        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude("edu" + "_\\w*"); // 映射表名, 所有edu_开头的表名都会被映射
//        strategy.setInclude("users","cart","dictionary");
        strategy.setInclude("cart");
        strategy.setNaming(NamingStrategy.underline_to_camel); // 数据映射到实体类的策略, 使用驼峰命名法
//        strategy.setTablePrefix("edu_"); // 不生成表的前缀
        strategy.setEntityLombokModel(true); // 自动添加Lombok的注解

        // 逻辑删除
        strategy.setLogicDeleteFieldName("isDelete"); // 逻辑删除字段名
//        strategy.setEntityBooleanColumnRemoveIsPrefix(true); // 去除布尔值的is_前缀

        // 自动填充
        TableFill create_time = new TableFill("gmtCreate", FieldFill.INSERT); // 自动填写创建时间
        TableFill modified_time = new TableFill("gmtModified", FieldFill.UPDATE); // 修改时的时间戳自动填充
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(create_time);
        tableFills.add(modified_time);
        strategy.setTableFillList(tableFills);

        // 乐观锁列(版本标识)
//        strategy.setVersionFieldName("version");

        // RestFUL API
        strategy.setRestControllerStyle(true);
        // URL驼峰命名 转化为下划线 _
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);

        // 6. 执行
        mpg.execute();
    }
}
