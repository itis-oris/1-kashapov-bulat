package ru.itis.semesterwork.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.semesterwork.entity.Lesson;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.LessonService;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/home/lesson_done")
public class FinishLessonServlet extends HttpServlet {
    private LessonService lessonService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        lessonService = (LessonService) context.getAttribute("lessonService");
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("lesson") != null) {
            Integer lessonId = Integer.parseInt(req.getParameter("lesson"));
            Optional<Lesson> lesson = lessonService.getById(lessonId);
            if (lesson.isPresent()) {
                Optional<User> teacher = userService.getById(lesson.get().getTeacher().getId());
                Optional<User> student = userService.getById(lesson.get().getStudent().getId());
                boolean res = userService.addSkillPoint(teacher.get(), teacher.get().getSkillpoints() + 1);
                res &= userService.addSkillPoint(student.get(), student.get().getSkillpoints() - 1);
                res &= lessonService.updateAttribute(lessonId, "status", "done");
                if (res) {
                    resp.sendRedirect("/SemesterWork/home/lessons");
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }

            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
