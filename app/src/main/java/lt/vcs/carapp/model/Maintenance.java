package lt.vcs.carapp.model;

import lt.vcs.carapp.DateObject;

public class Maintenance implements DateObject {
    public int id;
    public int autoId;
    public String date;
    public String jobId;
    public String comment;
    public String title;
    public int mileage;

    public Maintenance() {
    }

    public String getTitle() {
        return title;
    }

    public int getMileage() {
        return mileage;
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
