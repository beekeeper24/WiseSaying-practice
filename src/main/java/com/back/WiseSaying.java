package com.back;

public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public WiseSaying(int id, String content, String author) {
      this.id=id;
        this.content = content;
        this.author = author;
  }
}
