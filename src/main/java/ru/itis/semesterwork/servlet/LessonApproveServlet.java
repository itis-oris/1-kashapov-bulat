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
import ru.itis.semesterwork.service.LessonService;
import ru.itis.semesterwork.service.NotificationService;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/lesson_approve")
public class LessonApproveServlet extends HttpServlet {
    private LessonService lessonService;
    private NotificationService notificationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        lessonService = (LessonService) context.getAttribute("lessonService");
        notificationService = (NotificationService) context.getAttribute("notificationService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accept = req.getParameter("accept");
        String notificationId = req.getParameter("notification");
        if (accept == null || notificationId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            int nId = Integer.parseInt(notificationId);
            Optional<Notification> optionalNotification = notificationService.getById(nId);
            String type;
            if (optionalNotification.isPresent()) {
                Notification notification = Notification.builder()
                        .from(optionalNotification.get().getTo())
                        .to(optionalNotification.get().getFrom())
                        .skill(optionalNotification.get().getSkill())
                        .build();
                if (Objects.equals(accept, "1")) {
                    String message = req.getParameter("type");
                    notification.setMessage(message);
                    lessonService.addLesson(Lesson.builder()
                            .student(optionalNotification.get().getFrom())
                            .teacher(optionalNotification.get().getTo())
                            .teacherSkill(optionalNotification.get().getSkill()).build());
                    notificationService.update(nId, "status", "approved");
                    type = "accepted";
                } else {
                    type = "rejected";
                    notificationService.update(nId, "status", "rejected");
                }
                notification.setType(type);
                notificationService.add(notification);

            }
            resp.sendRedirect("/SemesterWork/home/notifications");
        }
    }
}
