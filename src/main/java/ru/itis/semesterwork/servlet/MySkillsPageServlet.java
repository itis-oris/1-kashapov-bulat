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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.itis.semesterwork.servlet.HomePageServlet.getColorBasedOnRating;

@WebServlet("/home/skills")
public class MySkillsPageServlet extends HttpServlet {
    private SkillService skillService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        skillService = (SkillService) servletContext.getAttribute("skillService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (req.getParameter("skill") != null) {
            Optional<Skill> skill = skillService.getById(Integer.parseInt(req.getParameter("skill")));
            if (skill.isPresent()) {
                req.setAttribute("skill", skill.get());
                req.setAttribute("color", getColorBasedOnRating(skill.get().getRating()));
                req.getRequestDispatcher("/jsp/my_skill.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("/SemesterWork/home/skills");
            }

        } else {
            String page = req.getParameter("page");
            int pageNum = page == null ? 1 : Integer.parseInt(page);
            int pageSize = 5;
            List<Skill> skills = skillService.getByUser(user, (pageNum - 1) * pageSize, pageSize);
            if (!(skills == null || skills.size() == 0)) {
                for (Skill skill : skills) {
                    req.setAttribute("color-" + skill.getId(), getColorBasedOnRating(skill.getRating()));
                    req.setAttribute("description-" + skill.getId(), skill.getDescription().length() > 50 ? skill.getDescription().substring(0, 50) + "..." : skill.getDescription());
                }
            }
            Integer numOfPages = skillService.countByUser(user) / pageSize + 1;
            req.setAttribute("skills", skills);
            req.setAttribute("user", user);
            req.setAttribute("pageNum", pageNum);
            req.setAttribute("countOfPages", numOfPages);
            req.getRequestDispatcher("/jsp/my_skills.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Integer userId = Integer.parseInt(req.getParameter("user_id"));
        if (skillService.getByUserAndName(User.builder().id(userId).build(), name).isPresent()) {
            try {
                resp.sendRedirect("/SemesterWork/home/my_skills?error=already_exists");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            skillService.addSkill(Skill.builder()
                    .user(User.builder().id(userId).build())
                    .name(name)
                    .description(description).build());
        }
    }
}
