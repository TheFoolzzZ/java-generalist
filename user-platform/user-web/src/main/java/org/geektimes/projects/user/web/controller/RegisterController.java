package org.geektimes.projects.user.web.controller;

import org.apache.commons.lang.StringUtils;
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

    @Override
    @POST
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(password)) {
            return "register.jsp";
        }else{
            if(StringUtils.equals(password,repeatPassword)) return "密码不一致请重新输入";
        }
        return "login-form.jsp";
    }
}
