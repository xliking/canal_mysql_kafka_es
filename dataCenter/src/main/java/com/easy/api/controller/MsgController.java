package com.easy.api.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.easy.framework.bean.base.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 16655054153
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/msg")
public class MsgController {

    @GetMapping(value = "/list")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public R<Object> getMsg() {
        JSONObject msgObj = new JSONObject();
        JSONArray okArray = new JSONArray();
        JSONArray errArray = new JSONArray();
        JSONObject o1 = new JSONObject();
        o1.put("msg", "hello world");
        o1.put("status", "0");
        okArray.add(o1);
        JSONObject o2 = new JSONObject();
        o2.put("msg", "hello world");
        o2.put("status", "0");
        okArray.add(o2);
        JSONObject o3 = new JSONObject();
        o3.put("msg", "hello world");
        o3.put("status", "0");
        okArray.add(o3);

        msgObj.put("success", okArray);

        JSONObject o4 = new JSONObject();
        o4.put("msg", "hello world");
        o4.put("status", "1");
        errArray.add(o4);
        JSONObject o5 = new JSONObject();
        o5.put("msg", "hello world");
        o5.put("status", "1");
        errArray.add(o5);
        JSONObject o6 = new JSONObject();
        o6.put("msg", "hello world");
        o6.put("status", "1");
        errArray.add(o6);
        JSONObject o7 = new JSONObject();
        o7.put("msg", "hello world");
        o7.put("status", "1");
        errArray.add(o7);
        msgObj.put("error", errArray);
        return R.success(msgObj);
    }


}