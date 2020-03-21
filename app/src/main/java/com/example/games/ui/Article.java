package com.example.games.ui;

public class Article {
    private String Title,ArticleURL,RelatedGame;

    public Article(String title, String articleURL, String relatedGame) {
        Title = title;
        ArticleURL = articleURL;
        RelatedGame = relatedGame;
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

    public String getRelatedGame() {
        return RelatedGame;
    }

    public void setRelatedGame(String relatedGame) {
        RelatedGame = relatedGame;
    }

    @Override
    public String toString() {
        return "Article{" +
                "Title='" + Title + '\'' +
                ", ArticleURL='" + ArticleURL + '\'' +
                ", RelatedGame='" + RelatedGame + '\'' +
                '}';
    }
}
