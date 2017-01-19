package co.mtaindia.mta.RecyclerViewPack;

/**
 * Created by Kapil Gehlot on 1/14/2017.
 */

public class CertificationBean {
    public String url;
    public String heading;
    public String description;
    public String code;

    public CertificationBean(){};
    public CertificationBean(String url,String heading,String description,String code) {
        this.url = url;
        this.heading = heading;
        this.description = description;
        this.code = code;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String name) {
        this.url = name;
    }
    public String getHeading(){
        return heading;
    }
    public void setHeading(String name) {
        this.heading = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String name) {
        this.description = name;
    }
    public String getCode(){
        return code;
    }
    public void setCode(String name) {
        this.code = name;
    }
}
