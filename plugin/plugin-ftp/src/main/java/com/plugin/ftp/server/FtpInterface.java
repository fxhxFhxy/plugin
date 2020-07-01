package com.plugin.ftp.server;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * ftp方法
 */
public class FtpInterface {

    private static Logger logger = LoggerFactory.getLogger(FtpInterface.class);

    /**
     * 上传
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param filePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param input    文件流
     * @return
     */
    public static boolean uploadFile(String host, int port, String username, String password, String filePath,
                                     String fileName, InputStream input) throws Exception {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            logger.info("连接ftp成功");
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
//            ftp.enterLocalPassiveMode();
            ftp.login(username, password);// 登录
            logger.info("登录ftp成功");
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(fileName, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
            logger.info("上传成功");
        } catch (IOException e) {
            logger.error("上传失败：" + ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    logger.error("上传失败：" + ExceptionUtils.getStackTrace(ioe));
                }
            }
        }
        return result;
    }

    /**
     * 下载文件
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @return
     */
    public static InputStream downloadFile(String host, int port, String username, String password, String remotePath, String fileName) {
        FTPClient ftp = new FTPClient();
        InputStream in = null;
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
//            ftp.enterLocalPassiveMode();
            ftp.login(username, password);// 登录
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return null;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            in = ftp.retrieveFileStream(fileName);

            ftp.logout();
        } catch (IOException e) {
            logger.error("下载失败：" + ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    logger.error("下载失败：" + ExceptionUtils.getStackTrace(ioe));
                }
            }
        }
        return in;
    }

    /**
     * 删除文件
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要删除的文件名
     * @return
     */
    public static boolean deleteFile(String host, int port, String username, String password, String remotePath, String fileName) {
        boolean flag = false;
        FTPClient ftp = new FTPClient();
        try {
            logger.info("开始删除文件");
            ftp.connect(host, port);
            ftp.login(username, password);// 登录
            //切换FTP目录
            ftp.changeWorkingDirectory(remotePath);
            ftp.dele(fileName);
            ftp.logout();
            flag = true;
            logger.info("删除文件成功");
        } catch (Exception e) {
            logger.error("删除文件失败" + ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
