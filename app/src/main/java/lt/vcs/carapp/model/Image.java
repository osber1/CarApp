package lt.vcs.carapp.model;

public class Image {
    public int id;
    public int jobId;
    public byte[] image;

    public Image(int jobId, byte[] image) {
        this.jobId = jobId;
        this.image = image;
    }

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
