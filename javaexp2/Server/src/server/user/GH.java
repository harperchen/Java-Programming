package server.user;

import java.util.Date;

public class GH {
    public String GHBH;
    public String HZBH;
    public String YSBH;
    public String BRBH;
    public Integer GHRC;
    public Boolean THBZ;
    public Double GHFY;
    public Date RQSJ;

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

class GH_Table {
    public Date GHRQ;
    public String GHBH;
    public String BRMC;
    public String HZMC;
    public Boolean SFZJ;


    public GH_Table(String ghbh, String brmc, String hzmc, Boolean sfzj, Date ghrq){
        GHBH = ghbh;
        BRMC = brmc;
        HZMC = hzmc;
        SFZJ = sfzj;
        GHRQ = ghrq;
    }

}
