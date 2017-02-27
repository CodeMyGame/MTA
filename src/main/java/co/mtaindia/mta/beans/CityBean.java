package co.mtaindia.mta.beans;

/**
 * Created by Kapil Gehlot on 1/29/2017.
 */

public class CityBean {
    private String coursename;
    private String duration;
    private String fee;
    private String registered;
    private String dpurl;
    private String batches;

    public CityBean(String coursename, String duration, String fee, String registered, String batches, String dpurl) {

        this.coursename = coursename;
        this.duration = duration;
        this.fee = fee;
        this.registered = registered;
        this.batches = batches;
        this.dpurl = dpurl;
    }

    public String getCoursename() {
        return this.coursename;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFee() {
        return this.fee;
    }

    public String getRegistered() {
        return this.registered;
    }

    public String getBatches() {
        return this.batches;
    }

    public String getDpurl() {
        return this.dpurl;
    }

}
