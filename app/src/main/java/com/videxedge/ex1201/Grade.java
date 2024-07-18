package com.videxedge.ex1201;

public class Grade {
    private int key_id;
    private String subject;
    private int grade;

    public Grade(int key_id, String subject, int grade) {
        this.key_id = key_id;
        this.subject = subject;
        this.grade = grade;
    }

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
