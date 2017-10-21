package org.webcrawler.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;

@Entity
public class NotificationRequest {

//- id
//- userId
//- url
//- cssSelector
//- content
//- created_at
//- updated_at
//- status: active, found, notified




    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    @URL
    private String url;

    private Integer userId;

    private String cssSelector;

    private String content;

    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCssSelector() {
        return cssSelector;
    }

    public void setCssSelector(String cssSelector) {
        this.cssSelector = cssSelector;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This is a way to check that at least one of the fields is present (content or cssSelector)
     */
    @AssertTrue(message="css selector or content (or both) must be provided")
    public boolean isContentOrCssSelectorPresent() {
        if(content != null && !content.isEmpty()) {
            return true;
        }
        if(cssSelector != null && !cssSelector.isEmpty()) {
            return true;
        }
        return false;
    }
}
