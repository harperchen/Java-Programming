package server.user;

import java.util.Date;

public class YS {
    public String YSBH;
    public String KSBH;
    public String YSMC;
    public String PYZS;
    public String DLKL;
    public Boolean SFZJ;
    public Date DLRQ;

    public YS(String ysbh, String ksbh, String ysmc, String pyzs, String dlkl, Boolean sfzj, Date dlrq) {
        YSBH = ysbh;
        KSBH = ksbh;
        YSMC = ysmc;
        PYZS = pyzs;
        DLKL = dlkl;
        SFZJ = sfzj;
        DLRQ = dlrq;
    }
}


class YSVerify{
    public Boolean isOK;
    String token;
    public int reason;
    public YSVerify(){
        isOK = false;
        token = "";
        reason = 0;
    }
    public YSVerify(Boolean isOK, String token, int reason){
        this.isOK = isOK;
        this.token = token;
        this.reason = reason;
    }
}

class YS_Table{
    public String KSMC;
    public String YSBH;
    public String YSMC;
    public String HZMC;
    public Boolean HZLB;
    public Integer GHRC;
    public Integer SRHJ;

    public YS_Table(String KSMC, String YSBH,
            String YSMC, String HZMC, Boolean HZLB,
            Integer GHRC, Integer SRHJ){
        this.GHRC = GHRC;
        this.HZLB = HZLB;
        this.KSMC = KSMC;
        this.HZMC = HZMC;
        this.YSBH = YSBH;
        this.YSMC = YSMC;
        this.SRHJ = SRHJ;
    }

}