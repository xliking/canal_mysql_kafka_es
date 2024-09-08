package com.easy.utils.file;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Base64FileUtil {

    private static final Pattern FILE_SUFFIX_PATTERN = Pattern.compile("(?<=/)(?<fileSuffix>\\w.*?)(?=;)");

    public static String fixBase64Image(String base64Image) {

        return fixBase64("data:image/jpeg;base64,", base64Image);
    }

    public static String fixBase64(String prefix, String base64Image) {

        if (StringUtils.isBlank(prefix)) {
            throw new RuntimeException("base64 前缀数据为空");
        }
        if (StringUtils.isBlank(base64Image)) {
            throw new RuntimeException("base64 数据为空");
        }
        if (!base64Image.startsWith("data:")) {
            base64Image = prefix + base64Image;
        }
        return base64Image;
    }

    public static String getRandomFileName(String fileSuffix) {

        if (StringUtils.isBlank(fileSuffix)) {
            throw new RuntimeException("文件后缀为空");
        }
        return UUID.randomUUID().toString().replace("-", "") + "." + fileSuffix;
    }

    public static byte[] decodeBase64Image(String base64Image) {

        String[] parts = base64Image.split(",");
        String imageString = parts[1];
        return Base64.getDecoder().decode(imageString);
    }

    /**
     * 获取base64图片的文件后缀
     *
     * @param base64Image base64
     * @return String jpeg
     */
    public static String getFileSuffix(String base64Image) {

        if (StringUtils.isBlank(base64Image)) {
            throw new RuntimeException("base64 图片数据为空");
        }
        Matcher fileSuffixMatcher = FILE_SUFFIX_PATTERN.matcher(base64Image);
        if (!fileSuffixMatcher.find()) {
            return "jpeg";
        }
        return fileSuffixMatcher.group("fileSuffix");
    }

    @SneakyThrows
    public static File base64ToFile(String base64Image) {

        base64Image = fixBase64Image(base64Image);
        File tempFile = File.createTempFile(String.valueOf(UUID.randomUUID()), getFileSuffix(base64Image));
        byte[] bytes = decodeBase64Image(base64Image);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        inputStreamToFile(inputStream, tempFile);
        return tempFile;
    }

    public static String byteToBase64(byte[] bytes) {

        return Base64.getEncoder().encodeToString(bytes);
    }

    private static void inputStreamToFile(InputStream ins, File file) throws IOException {

        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = ins.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }
}