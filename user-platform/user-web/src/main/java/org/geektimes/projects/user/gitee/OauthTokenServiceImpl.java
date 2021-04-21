package org.geektimes.projects.user.gitee;

import org.geektimes.projects.user.gitee.service.OauthTokenService;
import org.springframework.stereotype.Component;

/**
 * @description
 * @Author theFoolzzZ
 * @Date 2021/4/21 22:55
 */
@Component("giteeOauth")
public class OauthTokenServiceImpl implements OauthTokenService {

    public static final String CLIENT_ID = "d90344aa5501204823b71231388830b490d674bcb2341e43a0461e0a93948aab";
    public static final String CLIENT_SECRET = "7a41cba022cb2d346de1b0c1ac2df811b7cef7afc5e0d924a7e8df00f5ff1097";
    public static final String REDIRECT_URI = "127.0.0.1/callback";

    @Override
    public String oauthUri() {
        return String.format("https://gitee.com/oauth/authorize?client_id={%s}&redirect_uri={%s}&response_type=code",CLIENT_ID,REDIRECT_URI);

    }

    @Override
    public String oauthToken(String code) {
        return String.format("https://gitee.com/oauth/token?grant_type=authorization_code&code={%s}&" +
                "client_id={%s}&redirect_uri={%s}&client_secret={%S}",code,CLIENT_ID, REDIRECT_URI,CLIENT_SECRET);
    }

    @Override
    public String oauthUserInfo(String accessToken) {
        return "https://gitee.com/api/v5/user?access_token=" + accessToken;
    }
}
