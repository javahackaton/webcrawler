package org.webcrawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.webcrawler.constant.NotificationState;
import org.webcrawler.model.NotificationRequest;
import org.webcrawler.model.User;
import org.webcrawler.repository.NotificationRequestRepository;
import org.webcrawler.repository.UserRepository;

import javax.validation.Valid;

@Controller
public class NotificationRequestController {

    private static final Logger log = LoggerFactory.getLogger(NotificationRequestController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRequestRepository notificationRequestRepository;

    @GetMapping("/notification")
    public String index(Model model) {

        model.addAttribute("name", "test 23");

        model.addAttribute("user", new User());
        model.addAttribute("notificationRequest", new NotificationRequest());
        model.addAttribute("created", false);

        return "index";
    }

    @PostMapping("/notification")
    public String create(
            Model model,
            @Valid @ModelAttribute NotificationRequest notificationRequest,
            BindingResult notificationRequestValidation, // note, this must come right after the object that is being validated!
            @Valid @ModelAttribute User user,
            BindingResult userValidation // note, this must come right after the object that is being validated!
    ) {

        if (userValidation.hasErrors() || notificationRequestValidation.hasErrors()) {
            model.addAttribute("created", false);

            return "index";
        }

        User existingUser = userRepository.findFirstByEmail(user.getEmail());

        int userId;
        if (existingUser != null) {
            userId = existingUser.getId();

            model.addAttribute("user", existingUser);
        } else {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            User savedUser = userRepository.save(newUser);
            userId = savedUser.getId();

            model.addAttribute("user", savedUser);
        }

        NotificationRequest nr = new NotificationRequest();
        nr.setUrl(notificationRequest.getUrl());
        nr.setCssSelector(notificationRequest.getCssSelector());
        nr.setContent(notificationRequest.getContent());
        nr.setStatus(NotificationState.ACTIVE.name());
        nr.setUserId(userId);
        notificationRequestRepository.save(nr);

        log.info("New notification request was created");

        model.addAttribute("notificationRequest", nr);
        model.addAttribute("created", true);

        return "index";
    }
}
