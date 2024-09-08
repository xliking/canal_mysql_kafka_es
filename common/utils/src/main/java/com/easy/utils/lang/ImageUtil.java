package com.easy.utils.lang;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

/**
 * <p>图片工具类</p>
 *
 * @author muchi
 * @since 2023/7/5 17:27
 */
@Slf4j
public class ImageUtil {

    /**
     * 通过url地址获取byte
     *
     * @param imageUrl 图片地址
     * @return
     */
    public static byte[] getImageByte(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        byte[] bytes = toByteArray(inputStream);
        inputStream.close();
        return bytes;
    }

    /**
     * 获取图片转为Base64编码
     *
     * @param imageUrl 图片地址
     * @return Base64编码
     */
    public static String encodeToBase64(String imageUrl) throws Exception {
        return Base64.getEncoder().encodeToString(getImageByte(imageUrl));
    }


    private static byte[] toByteArray(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        }
    }

    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @param imageId     影像编号
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize, String imageId) {
        if (imageBytes == null || imageBytes.length == 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

}