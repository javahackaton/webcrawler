package org.webcrawler.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.webcrawler.constant.NotificationState;
import org.webcrawler.mail.Mailer;
import org.webcrawler.model.NotificationRequest;
import org.webcrawler.model.User;
import org.webcrawler.repository.NotificationRequestRepository;
import org.webcrawler.repository.UserRepository;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class MailerWorker {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRequestRepository notificationRequestRepository;

    @Autowired
    private Mailer mailer;

    private static final Logger log = LoggerFactory.getLogger(CrawlerWorker.class);

    @Scheduled(fixedRate = 10000)
    public void notifyUsers() throws MessagingException {
        log.info("Notification will be sent to users");
        List<NotificationRequest> list = notificationRequestRepository.findByStatus(NotificationState.FOUND.name());
        for (NotificationRequest request : list) {
            User user = userRepository.findOneById(request.getUserId());
            boolean sent = sendEmail(user, request);
            if(sent) {
                request.setStatus(NotificationState.NOTIFIED.name());
                notificationRequestRepository.save(request);
                log.info(" User of RequestId " + request.getId() + " was notified");
            } else {
                log.error ("Cannot send notification for request Id" + request.getId());
            }
        }
    }

    private boolean sendEmail(User user, NotificationRequest request) throws MessagingException {
        String content = "<p>Dear User,<br/> you request for the text: '<b>"
                + request.getContent() + "</b>' in css selector '<b>"
                + request.getCssSelector()  + "</b>' was found in the following url: '<b>"
                + request.getUrl()  + "</b>'.  " +
                "<br/>Best regards, <br/> Hackaton team";
        String title = "Notification. You request was processed";

        mailer.mail(user.getEmail(), title, content);
        return true;
    }

}
