package org.webcrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.webcrawler.model.NotificationRequest;
import org.webcrawler.model.User;
import org.webcrawler.repository.NotificationRequestRepository;
import org.webcrawler.repository.UserRepository;

@Controller
public class NotificationRequestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRequestRepository notificationRequestRepository;

    @RequestMapping("/")
    public String greeting(Model model) {
        User n = new User();
        n.setEmail("testuser-1@aaa.bbb.ddd");
        userRepository.save(n);

        NotificationRequest nr = new NotificationRequest();
        nr.setContent("some content");
        nr.setCssSelector("#main");
        nr.setStatus("active");
        nr.setUserId(1);
        notificationRequestRepository.save(nr);

        model.addAttribute("name", "test 17");
        return "index";
    }

}
