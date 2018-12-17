package dClient.user;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

class GH {
    private String GHBH;
    private String HZBH;
    private String YSBH;
    private String BRBH;
    private Integer GHRC;
    private Boolean THBZ;
    private Double GHFY;
    private Date RQSJ;

    public GH(String ghbh, String hzbh, String ysbh, String brbh,
    Integer ghrc, Boolean thbz, Double ghfy, Date rqsj){
        GHBH = ghbh;
        HZBH = hzbh;
        YSBH = ysbh;
        BRBH = brbh;
        GHRC = ghrc;
        THBZ = thbz;
        GHFY = ghfy;
        RQSJ = rqsj;
    }

}


public class GH_Table {
    private Date GHRQ;
    private String GHBH;
    private String BRMC;
    private String HZMC;
    private Boolean SFZJ;


    public GH_Table(String ghbh, String brmc, String hzmc, Boolean sfzj, Date ghrq){
        GHBH = ghbh;
        BRMC = brmc;
        HZMC = hzmc;
        SFZJ = sfzj;
        GHRQ = ghrq;
    }

    public Date getGHRQ() {
        return GHRQ;
    }

    public String getGHBH() {
        return GHBH;
    }

    public String getSFZJ() {
        return SFZJ ? "专家号" : "普通号";
    }

    public String getBRMC() {
        return BRMC;
    }

    public String getHZMC() {
        return HZMC;
    }
}

