package org.geektimes.projects.user.web.controller;

import org.apache.commons.lang.StringUtils;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 输出 “Hello,World” Controller
 */
@Path("/register")
public class RegisterController implements PageController {

    UserServiceImpl userService = new UserServiceImpl();

    @Override
    @POST
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(password)) {
            return "register.jsp";
        }else{
            if(StringUtils.equals(password,repeatPassword)) return "密码不一致请重新输入";
        }
        try {
            if (userService.register(new User(userId, password, email, phoneNumber))) {
                return "login-form.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail-page.jsp";
    }
}
