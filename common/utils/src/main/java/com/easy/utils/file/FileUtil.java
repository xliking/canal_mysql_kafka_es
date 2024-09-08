package com.easy.utils.file;

import com.easy.utils.lang.DateUtil;
import com.easy.utils.lang.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 文件工具
 * </p>
 *
 * @author muchi
 */
public class FileUtil {

    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_JPG = "image/jpg";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_BMP = "image/bmp";
    public static final String IMAGE_GIF = "image/gif";
    public static final String[] IMAGE_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png"};
    public static final String[] FLASH_EXTENSION = {"swf", "flv"};
    public static final String[] OFFICE_EXTENSION = {"doc", "docx", "xls", "xlsx", "ppt", "pptx"};
    public static final String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb"};
    public static final String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb"};
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb",
            // pdf
            "pdf"};

    /**
     * 获取文件类型
     * <p>
     * 例如: .txt, 返回: txt
     *
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(File file) {
        if (null == file) {
            return StringUtils.EMPTY;
        }
        return getFileType(file.getName());
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: a.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(String fileName) {
        int separatorIndex = fileName.lastIndexOf(".");
        if (separatorIndex < 0) {
            return "";
        }
        return fileName.substring(separatorIndex + 1).toLowerCase();
    }

    /**
     * 按照时间格式 组装文件路径
     * eg: 2022/08/11/15151532222_测试图片.png
     *
     * @param file 文件
     * @return 文件路径+文件名全名
     */
    public static String getTimePathFileName(MultipartFile file) {
        // 获取文件名字
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            fileName = IdUtil.fastSimpleUUID() + "." + fileName.substring(i + 1);
        }
        // 拆分文件名字
        fileName = DateUtil.datePath() + "/" + DateUtil.timeNum() + "_" + fileName;
        return fileName;
    }

    /**
     * 获取文件扩展
     *
     * @param contentType 内容类型
     * @return String
     */
    public static String getExtension(String contentType) {
        return switch (contentType) {
            case IMAGE_PNG -> "png";
            case IMAGE_JPG -> "jpg";
            case IMAGE_JPEG -> "jpeg";
            case IMAGE_BMP -> "bmp";
            case IMAGE_GIF -> "gif";
            default -> "";
        };
    }

    /**
     * 获取文件流
     *
     * @param inStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 流转文件
     *
     * @param ins  文件流
     * @param file 文件
     */
    public static void inputStreamToFile(InputStream ins, File file) throws Exception {
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }
}