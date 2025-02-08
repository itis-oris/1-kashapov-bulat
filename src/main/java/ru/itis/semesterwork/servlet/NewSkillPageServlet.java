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
import java.util.Optional;

@WebServlet("/home/new_skill")
public class NewSkillPageServlet extends HttpServlet {
    private SkillService skillService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        skillService = (SkillService) context.getAttribute("skillService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("/jsp/new_skill.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String description = req.getParameter("description");
        Optional<Skill> skill = skillService.getByUserAndName(user, name);

        if (skill.isPresent()) {
            try {
                resp.sendRedirect("/SemesterWork/home/new_skill?exists_error=1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            skillService.addSkill(Skill.builder()
                    .name(name)
                    .category(category)
                    .user(user)
                    .description(description).build());
            resp.sendRedirect("/SemesterWork/home");
        }
    }
}
