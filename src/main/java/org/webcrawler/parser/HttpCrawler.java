package org.webcrawler.parser;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class HttpCrawler
{
    public Document getUrlContent(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public boolean ifContentFound(List<String> selectors, String content)
    {

        boolean found = false;
        for (String text : selectors){
            if(text.contains(content)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public List<String> getSelectors(Document doc, String cssElement) {
        Elements elements = doc.select(cssElement);
        List<String> list = elements.eachText();
        return list;
    }
}