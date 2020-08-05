package com.weijie.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.weijie.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信的方法
     * @param map
     * @param phone
     * @return
     */
    @Override
    public boolean send(Map<String, Object> map, String phone) {

        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4GKHmoGSrhX8iYr1vv9S", "3ZtZswl3i5YKtkUlzXO6XHnMmd7q9U");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);//电话号码
        request.putQueryParameter("SignName", "维学堂在线教育网站");//标签名
        request.putQueryParameter("TemplateCode", "SMS_197430026");//模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));//验证码，json格式

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
