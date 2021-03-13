package org.geektimes.projects.user.web.controller;

import org.apache.commons.lang.StringUtils;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 输出 “Hello,World” Controller
 */
@Path("/register")
public class RegisterController implements PageController {

    @Resource(name = "bean/UserServiceImpl")
    private UserService userService;

    @POST
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String email = request.getParameter("email");
        String phoneNum = request.getParameter("phoneNum");
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(password)) {
            return "register.jsp";
        }else{
            if(StringUtils.equals(password,repeatPassword)) return "密码不一致请重新输入";
        }
        boolean register = false;
        try {
            register = userService.register(new User(userId, password, email, phoneNum));
        } catch (Exception e) {
            request.setAttribute("fail_reason", e.getMessage());
            return "fail-page.jsp";
        }
        if (register) {
            return "login-form.jsp";
        }
        return "注册失败";
    }

}
