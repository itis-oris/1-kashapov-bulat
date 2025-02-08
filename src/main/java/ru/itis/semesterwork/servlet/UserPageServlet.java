package ru.itis.semesterwork.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.SkillService;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.itis.semesterwork.servlet.HomePageServlet.getColorBasedOnRating;

@WebServlet("/users")
public class UserPageServlet extends HttpServlet {
    private UserService userService;
    private SkillService skillService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        skillService = (SkillService) context.getAttribute("skillService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        Optional<User> user = Optional.empty();
        if (username != null) {
            user = userService.getByUsername(username);
        }
        if (user.isPresent()) {
            User authUser = (User) req.getSession().getAttribute("user");
            if (authUser != null && authUser.getUsername().equals(username)) {
                resp.sendRedirect("/SemesterWork/home");
            }
            else {
                req.setAttribute("user", user.get());
                String color = getColorBasedOnRating(user.get().getAvg_rating());
                req.setAttribute("color", color);
                List<Skill> skills = skillService.getByUser(user.get(), 0, 3);
                if (skills != null && !skills.isEmpty()) {
                    req.setAttribute("skills", skills);
                    for (int i = 1; i <= skills.size(); i++) {
                        req.setAttribute("color-" + i, getColorBasedOnRating(skills.get(i - 1).getRating()));
                        req.setAttribute("description-" + i, skills.get(i - 1).getDescription().length() > 50 ? skills.get(i - 1).getDescription().substring(0, 50) + "..." : skills.get(i - 1).getDescription());
                    }
                }
                req.getRequestDispatcher("/jsp/user.jsp").forward(req, resp);
            }
        } else {
            String page = req.getParameter("page");
            int pageNum = page == null ? 1 : Integer.parseInt(page);
            int pageSize = 10;
            List<User> users = userService.getAll((pageNum - 1) * pageSize, pageSize);
            Integer numOfPages = userService.countAll() / pageSize + 1;
            req.setAttribute("users", users);
            req.setAttribute("page", pageNum);
            req.setAttribute("numOfPages", numOfPages);
            req.getRequestDispatcher("/jsp/users.jsp").forward(req, resp);
        }
    }
}
