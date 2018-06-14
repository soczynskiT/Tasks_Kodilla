package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyInfoConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyInfoConfig companyInfoConfig;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("company_info", companyInfoConfig.createCompanyInfoView());
        context.setVariable("goodbye_message", "Have a nice " + StringUtils.capitalize(LocalDate.now().getDayOfWeek().name().toLowerCase()));
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    String buildSchedulerEmail(String message) {
        List<Task> tasks = taskRepository.findAll();

        Context context = new Context();
        context.setVariable("admin_config", adminConfig);
        context.setVariable("message", message);
        context.setVariable("button_text", "Show TaskApp");
        context.setVariable("button_url", "http://localhost:8888/tasks");
        context.setVariable("trello_button", "Launch Trello");
        context.setVariable("trello_url", "https://trello.com/tomaszsoczynski/boards");
        context.setVariable("show_tasks_content", taskRepository.count() > 0);
        context.setVariable("tasks_list_header", "Tasks in database:");
        context.setVariable("tasks_list", tasks);
        context.setVariable("goodbye_message", "Have a nice " + StringUtils.capitalize(LocalDate.now().getDayOfWeek().name().toLowerCase()) + "!");
        context.setVariable("company_info", companyInfoConfig);

        return templateEngine.process("mail/task-scheduler-mail", context);
    }
}
