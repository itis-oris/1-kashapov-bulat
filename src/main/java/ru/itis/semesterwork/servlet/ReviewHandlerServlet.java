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
import ru.itis.semesterwork.service.SkillService;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/review")
public class ReviewHandlerServlet extends HttpServlet {
    private RateService rateService;
    private SkillService skillService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        rateService = (RateService) context.getAttribute("rateService");
        skillService = (SkillService) context.getAttribute("skillService");
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("rating") != null && req.getParameter("skill") != null
        && req.getParameter("message") != null && req.getSession().getAttribute("user") != null
        && req.getParameter("user") != null) {
            int rating = Integer.parseInt(req.getParameter("rating"));
            Integer skillId = Integer.valueOf(req.getParameter("skill"));
            Optional<Skill> skill = skillService.getById(skillId);
            skillService.update(Skill.builder().id(skillId).build(), "rating", calculateNewRating(skill.get(), rating));
            String username = req.getParameter("user");
            Optional<User> reviewReceiver = userService.getByUsername(username);
            userService.updateAttribute(reviewReceiver.get(), "avg_rating", calculateUserRating(reviewReceiver.get()));
            String message = req.getParameter("message");
            User user = (User) req.getSession().getAttribute("user");
            rateService.add(Rate.builder()
                    .skill(Skill.builder().id(skillId).build())
                    .rate(rating)
                    .user(user)
                    .message(message).build());
            resp.sendRedirect("/SemesterWork/users/skills?user=" + username + "&skill=" + skillId);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private Float calculateNewRating(Skill skill, int newRating) {
        Float skillRating = skill.getRating();
        Integer numOfRatings = rateService.getCountBySkill(skill);
        return (int) (((skillRating * numOfRatings + newRating) / (numOfRatings + 1)) * 10) / 10.0f;
    }
    private Float calculateUserRating(User user) {
        List<Skill> skills = skillService.getByUser(user);
        Float skillRating = (float) 0;
        for (Skill skill : skills) {
            skillRating += skill.getRating();
        }
        skillRating /= skills.size();
        return (int) (skillRating * 10) / 10.0f;
    }
}
