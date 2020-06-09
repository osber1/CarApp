package lt.vcs.carapp.model;

import lt.vcs.carapp.DateObject;

public class Service implements DateObject {
    public int id;
    public int autoId;
    public String date;
    public String title;
    public String comment;

    public Service(int autoId, String date, String title, String comment) {
        this.autoId = autoId;
        this.date = date;
        this.title = title;
        this.comment = comment;
    }

    public Service(String date, String title) {
        this.date = date;
        this.title = title;
    }

    public Service() {
    }

    public int getId() {
        return id;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
