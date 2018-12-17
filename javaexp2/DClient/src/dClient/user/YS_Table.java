package dClient.user;

public class YS_Table{
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

    public String getKSMC() {
        return KSMC;
    }

    public String getHZMC() {
        return HZMC;
    }

    public String getHZLB() {
        return HZLB ? "专家号" : "普通号";
    }

    public Integer getGHRC() {
        return GHRC;
    }

    public String getYSBH() {
        return YSBH;
    }

    public Integer getSRHJ() {
        return SRHJ;
    }

    public String getYSMC() {
        return YSMC;
    }
}