package ru.itis.semesterwork.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.SkillService;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomePageServlet extends HttpServlet {
    private SkillService skillService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        skillService = (SkillService) context.getAttribute("skillService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        req.setAttribute("user", session.getAttribute("user"));
        User user = (User) session.getAttribute("user");
        List<Skill> skills = skillService.getByUser(user, 0, 3);
        if (skills != null && !skills.isEmpty()) {
            req.setAttribute("skills", skills);
            for (int i = 1; i <= skills.size(); i++) {
                req.setAttribute("color-" + i, getColorBasedOnRating(skills.get(i - 1).getRating()));
                req.setAttribute("description-" + i, skills.get(i - 1).getDescription().length() > 50 ? skills.get(i - 1).getDescription().substring(0, 50) + "..." : skills.get(i - 1).getDescription());
            }
        }
        req.setAttribute("color", getColorBasedOnRating(user.getAvg_rating()));

        req.getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    public static String getColorBasedOnRating(float ratingValue) {
        if (ratingValue <= 1) return "#ff0000";    // Красный
        if (ratingValue <= 2) return "#ff4500";    // Оранжево-красный
        if (ratingValue <= 3) return "#ffa500";    // Оранжевый
        if (ratingValue <= 4) return "#9acd32";    // Желтовато-зелёный
        return "#32cd32";                          // Зелёный
    }
}
