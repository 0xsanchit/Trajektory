package com.sanchit.trajektory;

public class Subtopics {
    String Name;
    String url;

    public Subtopics() {
    }

    public Subtopics(String name, String url) {
        Name = name;
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
