package org.geektimes.projects.user.gitee.service;

/**
 * @description
 * @Author chengde.tan
 * @Date 2021/4/21 22:50
 */
public interface OauthTokenService {

    String oauthUri();


    String oauthToken(String code);


    String oauthUserInfo(String accessToken);
}
