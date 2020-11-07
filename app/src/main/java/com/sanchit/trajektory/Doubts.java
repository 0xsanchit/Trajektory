package com.sanchit.trajektory;

import java.lang.reflect.Array;

public class Doubts {
private String Description,Status,Title,Subject,AskedBy,doubtId,SolvedBy;

    public Doubts() {
    }

    public Doubts(String description, String status, String title, String subject, String askedBy, String doubtId, String solvedBy) {
        Description = description;
        Status = status;
        Title = title;
        Subject = subject;
        AskedBy = askedBy;
        this.doubtId = doubtId;
        SolvedBy = solvedBy;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getAskedBy() {
        return AskedBy;
    }

    public void setAskedBy(String askedBy) {
        AskedBy = askedBy;
    }

    public String getDoubtId() {
        return doubtId;
    }

    public void setDoubtId(String doubtId) {
        this.doubtId = doubtId;
    }

    public String getSolvedBy() {
        return SolvedBy;
    }

    public void setSolvedBy(String solvedBy) {
        SolvedBy = solvedBy;
    }
}
