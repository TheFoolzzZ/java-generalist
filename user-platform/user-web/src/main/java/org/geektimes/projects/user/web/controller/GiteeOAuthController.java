package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.gitee.service.OauthTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @description
 * @Author chengde.tan
 * @Date 2021/4/21 22:43
 */
@RestController
public class GiteeOAuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiteeOAuthController.class);
    private static final String REGEX = "=";
    private static final String COMMA = ",";

    @Resource
    private OauthTokenService oauthTokenService;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/callback")
    public String callback(String code){
        LOGGER.info("code:"+code);
        ResponseEntity<Object> entity = restTemplate.postForEntity(oauthTokenService.oauthToken(code), httpEntity(), Object.class);
        Object body = entity.getBody();
        assert body != null;
        LOGGER.info("token_body:"+body.toString());
        String[] split = body.toString().split(REGEX);
        String accessToken = split[1].split(COMMA)[0];
        LOGGER.info("accessToken:"+accessToken);
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(oauthTokenService.oauthUserInfo(accessToken),  Object.class);
        return forEntity.toString();
    }

    private HttpEntity httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HttpHeaders> request = new HttpEntity<HttpHeaders>(headers);
        return request;
    }
}
