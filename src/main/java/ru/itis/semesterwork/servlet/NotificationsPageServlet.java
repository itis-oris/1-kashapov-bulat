package ru.itis.semesterwork.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.semesterwork.entity.Lesson;
import ru.itis.semesterwork.entity.Notification;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.NotificationService;
import ru.itis.semesterwork.service.SkillService;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.itis.semesterwork.servlet.HomePageServlet.getColorBasedOnRating;

//TODO
@WebServlet("/home/notifications")
public class NotificationsPageServlet extends HttpServlet {
    private NotificationService notificationService;
    private UserService userService;
    private SkillService skillService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        notificationService = (NotificationService) servletContext.getAttribute("notificationService");
        userService = (UserService) servletContext.getAttribute("userService");
        skillService = (SkillService) servletContext.getAttribute("skillService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String page = req.getParameter("page");
        int pageNum = page == null ? 1 : Integer.parseInt(page);
        int pageSize = 5;
        List<Notification> notifications = notificationService.getByUser(user, (pageNum - 1) * pageSize, pageSize);
        for (Notification notification : notifications) {
            Optional<User> optionalTo = userService.getById(notification.getTo().getId());
            Optional<User> optionalFrom = userService.getById(notification.getFrom().getId());
            Optional<Skill> optionalSkill = skillService.getById(notification.getSkill().getId());
            notification.getTo().setUsername((optionalTo.isPresent()) ? optionalTo.get().getUsername() : "not exist");
            notification.getFrom().setUsername((optionalFrom.isPresent()) ? optionalFrom.get().getUsername() : "not exist");
            notification.setSkill(optionalSkill.get());
        }
        Integer numOfPages = notificationService.countByUser(user) / pageSize + 1;
        req.setAttribute("notifications", notifications);
        req.setAttribute("user", user);
        req.setAttribute("pageNum", pageNum);
        req.setAttribute("numOfPages", numOfPages);
        req.getRequestDispatcher("/jsp/notifications.jsp").forward(req, resp);
    }
}
