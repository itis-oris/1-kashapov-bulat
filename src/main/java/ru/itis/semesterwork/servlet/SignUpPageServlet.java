package ru.itis.semesterwork.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/sign_up")
public class SignUpPageServlet extends HttpServlet {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        passwordEncoder = (PasswordEncoder) context.getAttribute("encoder");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_password");
        String encodedPassword = passwordEncoder.encode(req.getParameter("password"));
        String email = req.getParameter("email");
        String aboutMe = req.getParameter("aboutme");

        if (userService.getByUsername(username).isPresent()) {

            try {
                resp.sendRedirect("/SemesterWork/sign_up?exists_error=1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            if (!Objects.equals(password, confirmPassword)) {
                try {
                    resp.sendRedirect("/SemesterWork/sign_up?match_error=1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (!passwordIsValid(password)) {
                try {
                    resp.sendRedirect("/SemesterWork/sign_up?valid_error=1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                User userToAdd = User.builder()
                        .username(username)
                        .password(encodedPassword)
                        .email(email)
                        .build();
                if (aboutMe != null) {
                    userToAdd.setDescription(aboutMe);
                }
                boolean res = userService.add(userToAdd);
                try {
                    String parameter = (res) ? "create_success=1" : "create_error=1";
                    resp.sendRedirect(req.getContextPath() + "/sign_in?" + parameter);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private boolean passwordIsValid(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
