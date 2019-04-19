package com.example.booklogger;

public class Book {
    private String title = "Title";
    private String author = "Author";
    private int pages = 420;
    private int days = 69;
    private int currentPage = 0;
    private String coverPath = null;

    public Book() {
    }

    public Book(String inputTitle, String inputAuthor, int inputPages, int inputDays ) {
        title = inputTitle;
        author = inputAuthor;
        pages = inputPages;
        days = inputDays;

    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public int getDays() {
        return days;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }
}
