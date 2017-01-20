package co.mtaindia.mta.beans;

/**
 * Created by Kapil Gehlot on 1/19/2017.
 */

public class BatchBean {
    public String url;
    public String heading;

    public BatchBean(String url, String heading) {
        this.url = url;
        this.heading = heading;
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

    public void setHeading(String name) {
        this.heading = name;
    }
}
