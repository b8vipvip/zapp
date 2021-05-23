/*
 * Copyright (C) 2020 The zfoo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.app.zapp.web.login.controller;

import com.zfoo.app.zapp.common.constant.AppConstant;
import com.zfoo.app.zapp.common.protocol.user.SignInAnswer;
import com.zfoo.app.zapp.common.protocol.user.SignInByPhoneAsk;
import com.zfoo.app.zapp.common.protocol.user.wechat.WeChatBindAsk;
import com.zfoo.app.zapp.common.protocol.user.wechat.WeChatSignInAnswer;
import com.zfoo.app.zapp.common.protocol.user.wechat.WeChatSignInAsk;
import com.zfoo.app.zapp.common.result.BaseResponse;
import com.zfoo.app.zapp.common.result.CodeEnum;
import com.zfoo.app.zapp.common.util.TokenUtils;
import com.zfoo.app.zapp.web.login.model.SignInResponse;
import com.zfoo.app.zapp.web.login.model.wechat.WeChatAccessTokenResponse;
import com.zfoo.app.zapp.web.login.model.wechat.WeChatRegisterRequest;
import com.zfoo.app.zapp.web.login.model.wechat.WeChatUserInfoResponse;
import com.zfoo.app.zapp.web.login.service.LoginService;
import com.zfoo.app.zapp.web.util.HttpUtils;
import com.zfoo.event.manager.EventBus;
import com.zfoo.net.NetContext;
import com.zfoo.net.packet.common.Message;
import com.zfoo.protocol.exception.ExceptionUtils;
import com.zfoo.protocol.util.JsonUtils;
import com.zfoo.protocol.util.StringUtils;
import com.zfoo.util.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 微信公众号相关接口
 * 微信公众号登录
 *
 * @author jaysunxiao
 * @version 1.0
 * @module WeChatService模块
 * @since 2019-11-07 20:40
 */
@Controller
public class WeChatServiceController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatServiceController.class);

    @Value("${weChat.service.accessToken.url}")
    private String weChatServiceAccessTokenUrl;

    @Value("${weChat.service.userInfo.url}")
    private String weChatServiceUserInfoUrl;

    @Value("${weChat.service.appid}")
    private String weChatServiceAppid;

    @Value("${weChat.service.secret}")
    private String weChatServiceSecret;

    @Autowired
    private LoginService loginService;

    /**
     * 微信公众号登录
     *
     * @param code  微信平台的返回code码
     * @param state 状态
     * @return 地址重定向
     */
    @RequestMapping(value = "/api/weChat/service/signIn")
    public void serviceSignIn(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) throws Exception {
        logger.info("微信公众号用户登录请求[code:{}][state:{}]", code, state);

        if (StringUtils.isBlank(code)) {
            return;
        }

        var client = HttpClient.newBuilder()
                .executor(EventBus.asyncExecute())
                .build();

        var responseBodyHandler = HttpResponse.BodyHandlers.ofString();

        var accessTokenRequest = HttpRequest
                .newBuilder(URI.create(StringUtils.format(weChatServiceAccessTokenUrl, weChatServiceAppid, weChatServiceSecret, code)))
                .GET()
                .build();


        var weChatAccessTokenResponse = JsonUtils.string2Object(client.send(accessTokenRequest, responseBodyHandler).body(), WeChatAccessTokenResponse.class);

        logger.info(JsonUtils.object2String(weChatAccessTokenResponse));

        var openid = weChatAccessTokenResponse.getOpenid();
        var unionid = weChatAccessTokenResponse.getUnionid();
        var weChatAccessToken = weChatAccessTokenResponse.getAccessToken();

        var weChatSignInAnswer = NetContext.getConsumer().syncAsk(WeChatSignInAsk.valueOf(unionid), WeChatSignInAnswer.class, unionid).packet();


        // 如果是新用户再绑定手机号码
        if (weChatSignInAnswer.isNewUser()) {
            var pair = loginService.createWeChatUnion(openid, unionid, weChatAccessToken);
            response.sendRedirect(StringUtils.format("/register?type=weChatService&verifyKey={}", pair.getKey()));
        } else {
            var token = weChatSignInAnswer.getToken();

            var cookie = new Cookie(AppConstant.ZFOO_COOKIE_TOKEN, token);
            cookie.setPath("/");
            cookie.setMaxAge(AppConstant.ZFOO_COOKIE_TOKEN_EXPIRE_TIME);
            response.addCookie(cookie);

            response.sendRedirect("/");
        }
    }

    @PostMapping(value = "/api/weChatService/register", consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public BaseResponse register(@RequestBody WeChatRegisterRequest cm) throws Exception {
        var verifyKey = cm.getVerifyKey();
        var phoneNumberStr = cm.getPhoneNumber();
        var phoneCode = cm.getPhoneCode();

        logger.info("微信用户注册请求[verifyKey:{}][phoneNumber:{}][phoneCode:{}]", verifyKey, phoneNumberStr, phoneCode);

        if (StringUtils.isBlank(phoneNumberStr) || StringUtils.isBlank(phoneCode)) {
            return BaseResponse.valueOf(CodeEnum.PARAMETER_EMPTY);
        }

        if (!NumberUtils.isLong(phoneNumberStr)) {
            return BaseResponse.valueOf(CodeEnum.PARAMETER_NOT_MATCH);
        }

        var phoneNumber = Long.parseLong(phoneNumberStr);

        // 验证码校验
        var verifyCodeCache = loginService.phoneCaches.getIfPresent(phoneNumber);
        if (verifyCodeCache == null) {
            return BaseResponse.valueOf(CodeEnum.SIGN_IN_PHONE_CODE_NOT_EXIST);
        }

        if (!NumberUtils.isInteger(phoneCode)) {
            return BaseResponse.valueOf(CodeEnum.SIGN_IN_PHONE_CODE_ERROR);
        }

        if (Integer.parseInt(phoneCode) != verifyCodeCache) {
            return BaseResponse.valueOf(CodeEnum.SIGN_IN_PHONE_CODE_ERROR);
        }

        // verifyKey校验
        var triple = loginService.weChatUnionCaches.getIfPresent(verifyKey);
        if (triple == null) {
            return BaseResponse.valueOf(CodeEnum.SIGN_IN_REGISTER_CODE_NOT_EXIST);
        }

        // 用户登录请求
        var signInAnswer = NetContext.getConsumer().syncAsk(SignInByPhoneAsk.valueOf(phoneNumber), SignInAnswer.class, phoneNumberStr).packet();
        var token = signInAnswer.getToken();
        if (StringUtils.isBlank(token)) {
            return BaseResponse.valueOf(CodeEnum.SIGN_IN_FAIL);
        }

        var userId = TokenUtils.get(token).getLeft();
        var openid = triple.getLeft();
        var unionid = triple.getMiddle();
        var weChatAccessToken = triple.getRight();

        // 如果是新用户再请求微信基本的用户信息
        if (signInAnswer.isNewUser()) {
            try {
                var client = HttpClient.newBuilder()
                        .executor(EventBus.asyncExecute())
                        .build();
                var responseBodyHandler = HttpResponse.BodyHandlers.ofString();

                var userInfoRequest = HttpRequest
                        .newBuilder(URI.create(StringUtils.format(weChatServiceUserInfoUrl, weChatAccessToken, openid)))
                        .GET()
                        .build();

                var weChatUserInfoResponse = JsonUtils.string2Object(client.send(userInfoRequest, responseBodyHandler).body(), WeChatUserInfoResponse.class);
                // 将微信的http图片替换成https
                var avatarUrl = weChatUserInfoResponse.getHeadImgUrl();
                if (!StringUtils.isBlank(avatarUrl) && avatarUrl.startsWith(HttpUtils.HTTP_PREFIX)) {
                    weChatUserInfoResponse.setHeadImgUrl(avatarUrl.replaceFirst(HttpUtils.HTTP_PREFIX, HttpUtils.HTTPS_PREFIX));
                }
                NetContext.getConsumer().syncAsk(weChatUserInfoResponse.toWeChatUserInfoAsk(userId), Message.class, userId);
            } catch (Exception e) {
                logger.error(ExceptionUtils.getMessage(e));
            }
        }

        var message = NetContext.getConsumer().syncAsk(WeChatBindAsk.valueOf(unionid, userId), Message.class, userId).packet();
        if (!message.success()) {
            return BaseResponse.valueOf(message.getCode());
        }

        return BaseResponse.valueOf(CodeEnum.OK_QUIETLY, SignInResponse.valueOf(signInAnswer.getToken()));
    }
}
