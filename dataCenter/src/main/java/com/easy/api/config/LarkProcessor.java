package com.easy.api.config;


import com.alibaba.fastjson2.JSON;
import com.easy.api.bean.constants.AppConstants;
import com.easy.utils.http.OkHttpUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author muchi
 */
@Component
public class LarkProcessor {


    private static final Logger log = LoggerFactory.getLogger(LarkProcessor.class);

    @Value("${lark.is_switch}")
    private Boolean isSwitchInstance;

    @Value("${lark.web_hook_url}")
    private String webHookUrlInstance;

    private static Boolean isSwitch;
    private static String webHookUrl;

    @PostConstruct
    public void init() {
        isSwitch = isSwitchInstance;
        webHookUrl = webHookUrlInstance;
    }

    public static void sendLarkMessage(Integer code, String... msg) {
        // 飞书消息推送功能开关
        if (Boolean.FALSE.equals(isSwitch)) {
            return;
        }
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> text = new HashMap<>();
        json.put("msg_type", "text");
        StringBuilder messageBuilder = new StringBuilder();
        if (Objects.equals(code, AppConstants.SUCCESS_CODE)) {
            messageBuilder.append("CODE : 1 , 数据同步成功通知 : ");
        } else if (Objects.equals(code, AppConstants.ERROR_CODE)) {
            messageBuilder.append("CODE : 0 , 数据同步失败通知 : ");
        }
        for (String message : msg) {
            messageBuilder.append(message).append(" ");
        }
        text.put("text", messageBuilder.toString().trim());
        json.put("content", text);
        OkHttpUtil okHttpUtil = OkHttpUtil.builder()
                .url(webHookUrl)
                .postJson(JSON.toJSONString(json));
        String result = okHttpUtil.sync();
        log.info(result);
    }
}