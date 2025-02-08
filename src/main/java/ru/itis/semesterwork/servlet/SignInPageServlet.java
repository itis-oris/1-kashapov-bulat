package ru.itis.semesterwork.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/sign_in")
public class SignInPageServlet extends HttpServlet {
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                resp.sendRedirect("/SemesterWork/home");
            } else {
                req.getRequestDispatcher("/jsp/signin.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/jsp/signin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = "";
        Optional<User> optionalUser = userService.getByNameAndPass(username, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = req.getSession();
            user.setSession_id(session.getId());
            userService.update(user);
            session.setAttribute("user", user);
            if (req.getParameter("remember_me") != null) {
                remember = UUID.randomUUID().toString();
                Cookie rememberMeCookie = new Cookie("rememberMeToken", remember);
                rememberMeCookie.setMaxAge(60 * 60 * 24 * 7);
                resp.addCookie(rememberMeCookie);
                user.setRemember_id(remember);
                resp.addCookie(rememberMeCookie);
            }
            try {
                resp.sendRedirect("/SemesterWork/home");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                resp.sendRedirect("/SemesterWork/sign_in?not_found_error=1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
