package org.webcrawler.worker;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.webcrawler.model.NotificationRequest;
import org.webcrawler.parser.HttpCrawler;
import org.webcrawler.repository.NotificationRequestRepository;
import org.webcrawler.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@Component
public class CrawlerWorker {
    private static final Logger log = LoggerFactory.getLogger(CrawlerWorker.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRequestRepository notificationRequestRepository;

    @Scheduled(fixedRate = 10000)
    public void crawlWebsites() throws IOException {
        log.info("Web sites will be crawled");

        List<NotificationRequest> list = notificationRequestRepository.findByStatus("active");
        for (NotificationRequest request : list) {
            boolean found = crawlUrl(request);
            if(found) {
                request.setStatus("found");
                notificationRequestRepository.save(request);
            }
        }



    }
    boolean crawlUrl(NotificationRequest request) throws IOException {
        HttpCrawler crawler = new HttpCrawler();
        Document doc = crawler.getUrlContent(request.getUrl());
        List<String>list = crawler.getSelectors(doc, request.getCssSelector());
        return  crawler.ifContentFound(list, request.getContent());
    }
}