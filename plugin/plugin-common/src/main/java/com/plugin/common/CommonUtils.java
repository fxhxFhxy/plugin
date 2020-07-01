package com.plugin.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用工具类(UUID,MD5,Token)
 */
public class CommonUtils {

    /**
     * md5加密
     *
     * @param text
     * @param salt
     * @return
     */
    public static String encodeMD5(String text, String salt) {
        return StringUtils.upperCase(DigestUtils.md5Hex(text + salt));
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String genToken() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return uuid;
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String genUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return uuid;
    }

    /**
     * InputStream转byte数组
     *
     * @param inputStream
     */
    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 随机数，区分大小写
     *
     * @param length 需要的字符长度
     * @return 返回随机字母数字组合字符串
     */
    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            //随机数由0-9，a-z,A-Z组成，数字占10个，字母占52个，数字、字母占比1:5（标准的应该是10:52）
            //random.nextInt(6) 0-5中6个数取一个
            String charOrNum = (random.nextInt(6) + 6) % 6 >= 1 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母，输出比例为1:1
                int temp = random.nextInt(2) % 2 == 0 ? 97 : 65;
                //char（65）-char(90) 为大写字母A-Z；char(97)-char(122)为小写字母a-z
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
