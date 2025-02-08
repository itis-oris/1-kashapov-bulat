package ru.itis.semesterwork.listener;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.semesterwork.config.DbConfig;
import ru.itis.semesterwork.repository.*;
import ru.itis.semesterwork.repository.mapper.*;
import ru.itis.semesterwork.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(ContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        DbConfig configuration =
                new DbConfig(properties);
        DataSource dataSource = configuration.hikariDataSource();
        JdbcTemplate template = new JdbcTemplate(dataSource);
        UserRepositoryImpl userRepository =
                new UserRepositoryImpl(template, new UserRowMapper());
        SkillRepository skillRepository =
                new SkillRepository(template, new SkillRowMapper(), new CategoryMapper());

        LessonRepository lessonRepository =
                new LessonRepository(template, new LessonRowMapper());

        NotificationRepository notificationRepository =
                new NotificationRepository(template, new NotificationRowMapper());

        RateRepository rateRepository =
                new RateRepository(template, new RateRowMapper());


        UserService userService = new UserService(userRepository, bCryptPasswordEncoder);
        SkillService skillService = new SkillService(skillRepository);
        LessonService lessonService = new LessonService(lessonRepository);
        NotificationService notificationService = new NotificationService(notificationRepository);
        RateService rateService = new RateService(rateRepository);

        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("skillService", skillService);
        servletContext.setAttribute("lessonService", lessonService);
        servletContext.setAttribute("notificationService", notificationService);
        servletContext.setAttribute("rateService", rateService);
        servletContext.setAttribute("dataSource", dataSource);
        servletContext.setAttribute("encoder", bCryptPasswordEncoder);
        logger.info("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        HikariDataSource dataSource = (HikariDataSource) servletContext.getAttribute("dataSource");
        dataSource.close();
        logger.info("contextDestroyed");
    }

}
