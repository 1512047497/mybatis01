package com.zking.shiro01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        //获取登入的账号密码并构建登入令牌toke
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UsernamePasswordToken token = new UsernamePasswordToken(
                username,
                password
        );

        try {
            //身份验证
            subject.login(token);
            req.getSession().setAttribute("username",username);
            resp.sendRedirect(req.getContextPath()+"/main.jsp");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            req.getSession().setAttribute("msg","账号或者密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }
}
