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
import ru.itis.semesterwork.service.LessonService;
import ru.itis.semesterwork.service.NotificationService;
import ru.itis.semesterwork.service.SkillService;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;

@WebServlet("/skill_sign_up")
public class SkillSignUpServlet extends HttpServlet {
    private NotificationService notificationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        notificationService = (NotificationService) context.getAttribute("notificationService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String skillIdStr = req.getParameter("skill");
        String teacherIdStr = req.getParameter("teacher");
        String type = req.getParameter("type");
        User user = (User) req.getSession().getAttribute("user");
        if (skillIdStr == null || teacherIdStr == null || skillIdStr.equals("") || teacherIdStr.equals("")) {
            resp.sendRedirect("/SemesterWork/home");
        } else {
            Integer skillId = Integer.parseInt(skillIdStr);
            Integer teacherId = Integer.parseInt(teacherIdStr);
            User teacher = User.builder().id(teacherId).build();
            Skill skill = Skill.builder().id(skillId).build();
            notificationService.add(Notification.builder()
                    .from(user)
                    .to(teacher)
                    .type(type)
                    .skill(skill).build());
            req.getRequestDispatcher("/jsp/skill_sign_up.jsp").forward(req, resp);
        }

    }
}
