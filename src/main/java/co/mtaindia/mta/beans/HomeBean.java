package co.mtaindia.mta.beans;

/**
 * Created by Kapil Gehlot on 1/16/2017.
 */

public class HomeBean {
    public String url;
    public String heading;
    public String description;

    public HomeBean(String url, String heading, String description) {
        this.url = url;
        this.heading = heading;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String name) {
        this.url = name;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

}
