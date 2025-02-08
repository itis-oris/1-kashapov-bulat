package ru.itis.semesterwork.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.semesterwork.entity.Rate;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.RateService;
import static ru.itis.semesterwork.servlet.HomePageServlet.getColorBasedOnRating;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/users/skills/review")
public class RatePageServlet extends HttpServlet {
    private RateService rateService;
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        rateService = (RateService) context.getAttribute("rateService");
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("skill") == null || req.getParameter("user") == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            String username = req.getParameter("user");
            Integer skillId = Integer.parseInt(req.getParameter("skill"));
            List<Rate> rates = rateService.getBySkill(Skill.builder().id(skillId).build());
            if (rates != null && !rates.isEmpty()) {
                for (Rate rate : rates) {
                    Optional<User> user = userService.getById(rate.getUser().getId());
                    req.setAttribute("color" + rate.getId(), getColorBasedOnRating(rate.getRate()));
                    if (user.isPresent()) {
                        rate.setUser(user.get());
                    } else {
                        rate.setUser(null);
                    }
                }
            }
            req.setAttribute("rates", rates);
            req.setAttribute("user", username);
            req.setAttribute("skill", skillId);
            req.getRequestDispatcher("/jsp/rate.jsp").forward(req, resp);
        }
    }
}
