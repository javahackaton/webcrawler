package org.webcrawler.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CrawlerWorker {
    private static final Logger log = LoggerFactory.getLogger(CrawlerWorker.class);


    @Scheduled(fixedRate = 60000)
    public void crawlWebsites() {
        log.info("Web sites will be crawled");




        }
}