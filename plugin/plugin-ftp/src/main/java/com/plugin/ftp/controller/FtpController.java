package com.plugin.ftp.controller;

import com.plugin.common.CommonUtils;
import com.plugin.ftp.server.FtpInterface;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
public class FtpController {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.projectRoot}")
    private String projectRoot;

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("/insert")
    public Object insert(MultipartFile file) throws Exception {
        try {
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String name = CommonUtils.getRandom(4) + "_" + System.currentTimeMillis() + "." + suffix;
            boolean b = FtpInterface.uploadFile(host, port, username, password, projectRoot, name, file.getInputStream());
            if (b) {
                return "上传成功！文件名：" + name;
            } else {
                return "上传失败";
            }
        } catch (Exception e) {
            return ExceptionUtils.getStackTrace(e);
        }
    }

    /**
     * 下载回显页面
     *
     * @param fileName
     * @return
     */
    @RequestMapping("/show")
    public void show(HttpServletResponse response, @RequestParam String fileName) {
        OutputStream out = null;
        InputStream in = null;
        try {
            in = FtpInterface.downloadFile(host, port, username, password, projectRoot, fileName);
            String picType = fileName.split("\\.")[1];

            BufferedImage bufImg = null;
            bufImg = ImageIO.read(in);
            out = response.getOutputStream();
            ImageIO.write(bufImg, picType, out);

            in.close();
        } catch (Exception e) {
            System.out.println("异常：" + ExceptionUtils.getStackTrace(e));
            return;
        } finally {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除
     *
     * @param fileName
     * @return
     */
    @RequestMapping("/delete")
    public Object delete(@RequestParam String fileName) {
        try {
            boolean b = FtpInterface.deleteFile(host, port, username, password, projectRoot, fileName);
            if (b) {
                return "删除成功";
            } else {
                return "删除失败";
            }
        } catch (Exception e) {
            return ExceptionUtils.getStackTrace(e);
        }
    }
}
