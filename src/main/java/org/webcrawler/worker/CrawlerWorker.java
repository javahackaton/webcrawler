package org.webcrawler.worker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CrawlerWorker {

    @Scheduled(fixedRate = 60000)
    public void crawlWebsites() {
        System.out.println("Web sites will be crawled");

    }
}