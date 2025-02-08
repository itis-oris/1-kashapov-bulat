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
import ru.itis.semesterwork.service.LessonService;
import ru.itis.semesterwork.service.RateService;
import ru.itis.semesterwork.service.SkillService;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.itis.semesterwork.servlet.HomePageServlet.getColorBasedOnRating;

@WebServlet("/users/skills")
public class SkillsPageServlet extends HttpServlet {
    private UserService userService;
    private SkillService skillService;
    private LessonService lessonService;
    private RateService rateService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        skillService = (SkillService) context.getAttribute("skillService");
        lessonService = (LessonService) context.getAttribute("lessonService");
        rateService = (RateService) context.getAttribute("rateService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("user");
        Optional<User> user = Optional.empty();
        User authUser = (User) req.getSession().getAttribute("user");
        if (username != null) {
            user = userService.getByUsername(username);
        }
        if (user.isPresent()) {
            if (req.getParameter("skill") != null) {
                Optional<Skill> skill = skillService.getById(Integer.parseInt(req.getParameter("skill")));
                if (skill.isPresent()) {
                    if (authUser != null && !lessonService.getBySkillAndStudent(skill.get(), authUser).isEmpty()&&
                    rateService.getByUserAndSkill(authUser, skill.get()).isEmpty()) {
                        req.setAttribute("rateable", true);
                    }
                    req.setAttribute("user", user.get());
                    req.setAttribute("skill", skill.get());
                    req.setAttribute("color", getColorBasedOnRating(skill.get().getRating()));
                    req.getRequestDispatcher("/jsp/skill.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("/SemesterWork/users/skills?user=" + username);
                }

            }
            String page = req.getParameter("page");
            int pageNum = page == null ? 1 : Integer.parseInt(page);
            int pageSize = 5;
            List<Skill> skills = skillService.getByUser(user.get(), (pageNum - 1) * pageSize, pageSize);
            if (!(skills == null || skills.isEmpty())) {
                for (Skill skill : skills) {
                    req.setAttribute("color-" + skill.getId(), getColorBasedOnRating(skill.getRating()));
                    req.setAttribute("description-" + skill.getId(), skill.getDescription().length() > 50 ? skill.getDescription().substring(0, 50) + "..." : skill.getDescription());

                }
            }
            Integer numOfPages = skillService.countByUser(user.get()) / pageSize + 1;
            req.setAttribute("skills", skills);
            req.setAttribute("user", user.get());
            req.setAttribute("pageNum", pageNum);
            req.setAttribute("countOfPages", numOfPages);
            req.getRequestDispatcher("/jsp/skills.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/SemesterWork/users");
        }
    }
}
