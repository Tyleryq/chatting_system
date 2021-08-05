package edu.ncu.chattingsys.commonsapi.model.responseData;

import edu.ncu.chattingsys.commonsapi.model.domain.Article;

import java.util.Date;

public class ArticleData {

    private String title;
    private String content;
    private String keyword;
    private Date p_date;
    private String name;//发布者名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getP_date() {
        return p_date;
    }

    public void setP_date(Date p_date) {
        this.p_date = p_date;
    }
}
