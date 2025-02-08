package ru.itis.semesterwork.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.semesterwork.entity.Lesson;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.service.LessonService;
import ru.itis.semesterwork.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.itis.semesterwork.servlet.HomePageServlet.getColorBasedOnRating;

@WebServlet("/home/lessons")
public class MyLessonsPageServlet extends HttpServlet {
    LessonService lessonService;
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        lessonService = (LessonService) servletContext.getAttribute("lessonService");
        userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (req.getParameter("lesson") != null) {
            Optional<Lesson> lesson = lessonService.getById(Integer.parseInt(req.getParameter("lesson")));
            if (lesson.isPresent()) {
                Optional<User> teacher = userService.getById(lesson.get().getTeacher().getId());
                Optional<User> student = userService.getById(lesson.get().getStudent().getId());
                lesson.get().setTeacher(teacher.get());
                lesson.get().setStudent(student.get());
                req.setAttribute("lesson", lesson.get());
                req.getRequestDispatcher("/jsp/lesson.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("/SemesterWork/home/lessons");
            }
        } else {
            String page = req.getParameter("page");
            int pageNum = page == null ? 1 : Integer.parseInt(page);
            int pageSize = 5;
            List<Lesson> lessons;
            Integer numOfPages;
            String mode;
            if (req.getParameter("teacher") != null) {
                lessons = lessonService.getByTeacher(user, (pageNum - 1) * pageSize, pageSize);
                numOfPages = lessonService.countByTeacher(user) / pageSize + 1;
                mode = "teacher";
            } else if (req.getParameter("student") != null) {
                lessons = lessonService.getByStudent(user, (pageNum - 1) * pageSize, pageSize);
                numOfPages = lessonService.countByStudent(user) / pageSize + 1;
                mode = "student";
            } else {
                lessons = lessonService.getByUser(user, (pageNum - 1) * pageSize, pageSize);
                numOfPages = lessonService.countByUser(user) / pageSize + 1;
                mode = "user";
            }
            for (Lesson lesson : lessons) {
                Optional<User> optional = userService.getById(lesson.getTeacher().getId());
                lesson.getTeacher().setUsername((optional.isPresent()) ? optional.get().getUsername() : "not exist");
                optional = userService.getById(lesson.getStudent().getId());
                lesson.getStudent().setUsername((optional.isPresent()) ? optional.get().getUsername() : "not exist");
            }
            req.setAttribute("lessons", lessons);
            req.setAttribute("user", user);
            req.setAttribute("page", pageNum);
            req.setAttribute("numOfPages", numOfPages);
            req.setAttribute("mode", mode);
            req.getRequestDispatcher("/jsp/my_lessons.jsp").forward(req, resp);
        }
    }
}
