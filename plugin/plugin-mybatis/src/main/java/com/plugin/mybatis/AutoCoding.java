package com.plugin.mybatis;

public class AutoCoding {

    public static void main(String[] args) {
        final String mainDir = "D:\\plugin\\plugin\\plugin-mybatis\\src\\main\\";

        CodeGenerator.ConfigBuilder builder = new CodeGenerator.ConfigBuilder();

        CodeGenerator codeGenerator = builder
//                数据库连接
                .dbUrl("jdbc:mysql://192.168.0.127:3337/cb_clinic")
//                账户
                .userName("root")
//                密码
                .password("123456")
                // 生成类位置
                .dir(mainDir + "java")
                // 生成xml 位置
                .xmlDir(mainDir + "resources")
                // 包引用路径
                .packageName("com.cb")
                .build();

        //根据表生成后台代码
        codeGenerator.code("clinic");
    }

}
