package com.easy.utils.http;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求数据解析工具
 * </p>
 *
 * @author muchi
 */
public class RequestUtil {

    public static byte[] getRequestBodyBytes(HttpServletRequest request) {
        try (InputStream inputStream = request.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int n;
            while ((n = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, n);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把request转换成json数据
     *
     * @param request 请求
     * @return String
     */
    public static String getRequestBodyString(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 把request转换成xml数据
     *
     * @param request 请求
     * @return String
     */
    public static String getRequestBodyXml(HttpServletRequest request) {
        String inputLine;
        StringBuilder notifyXml = new StringBuilder();
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notifyXml.append(inputLine);
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return notifyXml.toString();

    }


    /**
     * 把request转换成map数据
     *
     * @param request 请求
     * @return Map<String, String>
     */
    public static Map<String, String> getRequestBodyMap(HttpServletRequest request) {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<>(requestParams.size());
        for (String o : requestParams.keySet()) {
            String[] values = requestParams.get(o);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(o, valueStr);
        }
        return params;
    }
}