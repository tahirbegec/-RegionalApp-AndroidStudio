package de.uni_marburg.sp21.Model;

public class Message {

    private String content;
    private String date;

    public Message(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }
    public String getDate() {
        return date;
    }
}
