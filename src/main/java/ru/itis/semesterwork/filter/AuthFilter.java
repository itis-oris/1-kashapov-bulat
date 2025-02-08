package ru.itis.semesterwork.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@WebFilter(urlPatterns = {"/sign_in", "/home/*", "/skill_sign_up"})
public class AuthFilter extends HttpFilter {
    UserService userService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            Optional<User> optionalUser = userService.getById(user.getId());
            if (optionalUser.isPresent()) {
                session.setAttribute("user", optionalUser.get());
                chain.doFilter(req, res);
            } else {
                response.sendRedirect("/SemesterWork/sign_in");
            }

        } else {
            String actualSessionId = "";
            String rememberId = "";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        actualSessionId = cookie.getValue();
                    } else if (cookie.getName().equals("rememberMeToken")) {
                        rememberId = cookie.getValue();
                    }
                }
            }
            Optional<User> user = AuthenticatedUser(rememberId, actualSessionId);
            if (user.isPresent()) {
                if (session == null) {
                    session = request.getSession();
                    user.get().setSession_id(session.getId());
                    userService.update(user.get());
                }
                session.setAttribute("user", user.get());
                chain.doFilter(request, response);
            } else if (Objects.equals(request.getServletPath(), "/sign_in")) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect("http://localhost:8080/SemesterWork/sign_in");
            }
        }
    }
    private Optional<User> AuthenticatedUser(String rememberId, String actualSessionId) {
        if (!rememberId.isEmpty()) {
            Optional<User> user = userService.getByRememberId(rememberId);
            if (user.isPresent()) {
                return user;
            }
        }
        if (!actualSessionId.isEmpty()) {

            return userService.getBySessionId(actualSessionId);
        }
        return Optional.empty();
    }
}
