package pClient.user;

import java.util.Date;

class BRVerify{
    public Boolean isOK;
    String token;
    public int reason;
    public BRVerify(){
        isOK = false;
        token = "";
        reason = 0;
    }
    public BRVerify(Boolean isOK, String token, int reason){
        this.isOK = isOK;
        this.token = token;
        this.reason = reason;
    }
}


public class BR {
    public String BRBH;
    public String BRMC;
    public String DLKL;
    public Date DLRQ;
    public Double YCJE;
    
    public BR(String brbh, String brmc, String dlkl, Date dlrq, Double ycje){
        BRBH = brbh;
        BRMC = brmc;
        DLKL = dlkl;
        DLRQ = dlrq;
        YCJE = ycje;
    }
}
