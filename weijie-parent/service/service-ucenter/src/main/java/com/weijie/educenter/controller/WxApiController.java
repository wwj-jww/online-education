package com.weijie.educenter.controller;

import com.google.gson.Gson;
import com.weijie.commonutils.JwtUtils;
import com.weijie.educenter.bean.UcenterMember;
import com.weijie.educenter.service.UcenterMemberService;
import com.weijie.educenter.utils.ConstantWxUtils;
import com.weijie.educenter.utils.HttpClientUtils;
import com.weijie.servicebase.exceptionhandler.WeiJieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller //重定向到微信二维码地址 不需要返回json字符串，所以用@controller
//@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 1 生成微信扫描二维码
     */

    @GetMapping("/login")
    public String getWxCode() {
        //固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch(Exception e) {
        }

        String url = String.format(
                    baseUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    redirectUrl,
                    "weijie"
            );


        //重定向到请求微信地址里面
        return "redirect:"+url;
        }





    //2 转跳首页面 （特殊需求 因为穷）
    @GetMapping("callback")
    public String callback(String code, String state) {

        try {
            //用code请求微信指定地址
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接参数
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            //用httpclient发送请求
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //因为返回的是json字符串类型，后端没有json类型，无法取得值，所以用gson把json转为map，再根据键取得值
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");
            //把扫描人信息添加数据库里面
            //判断数据表里面是否存在相同微信信息，根据openid判断
            UcenterMember member = memberService.getOpenIdMember(openid);
            if(member == null) {//memeber是空，表没有相同微信数据，进行添加

                //3 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                //获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userInfoMap.get("nickname");//昵称
                String headimgurl = (String)userInfoMap.get("headimgurl");//头像

                member = new UcenterMember();
                member.setId(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            //cookie无法实现跨域，所以用url重写
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;

        } catch (Exception e) {
            throw new WeiJieException(20001,"登录失败");
        }
    }


}

