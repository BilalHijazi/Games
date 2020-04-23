package com.example.games;

public class Article {
    private String Title,ArticleURL,CoverId;

    public Article(String title, String articleURL,String coverId) {
        Title = title;
        ArticleURL = articleURL;
        CoverId=coverId;

    }

    public Article() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArticleURL() {
        return ArticleURL;
    }

    public void setArticleURL(String articleURL) {
        ArticleURL = articleURL;
    }

    public String getCoverId() {
        return CoverId;
    }

    public void setCoverId(String coverId) {
        CoverId = coverId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "Title='" + Title + '\'' +
                ", ArticleURL='" + ArticleURL + '\'' +
                ", CoverId='" + CoverId + '\'' +
                '}';
    }
}
