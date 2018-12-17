package server.user;

import server.ServerDB;
import server.json.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

class Msg{
    public List<String> object;
    String token;
    int msgType;

    public Msg(int msgType){
        object = new ArrayList<String>();
        this.msgType = msgType;
    }

    public Msg(String token, int msgType){
        object = new ArrayList<String>();
        this.token = token;
        this.msgType = msgType;
    }

}

public class MsgType {
    private final static int PatientLogin = 1;
    private final static int PatientLoginVerify = 2;
    private final static int WrongBRBH = 3;
    private final static int WrongDLKL = 4;
    public final static int QueryKSXX = 5;
    public final static int QueryYSXX = 6;
    public final static int QueryHZXX = 7;
    public final static int QueryYCJE = 8;
    public final static int PatientLogout = 9;
    public final static int PatientAlreadyOn = 10;
    public final static int CutPatientMonty = 11;
    public final static int NumberQuerry = 12;
    public final static int NumberGiveup = 13;


    public final static int DoctorLogin = 21;
    public final static int DoctorLoginVerify = 22;
    public final static int DoctorWrongBRBH = 23;
    public final static int DoctorWrongDLKL = 24;
    public final static int DoctorAlreadyOn = 25;
    public final static int QueryDoctor = 26;
    public final static int DoctorLogout = 27;
    public final static int PatientList = 28;
    public final static int SalaryList = 29;

    public static String MsgHandler(String msg) {
        Msg ObjMsg = (Msg) JsonToObject.jsonToObject(msg, Msg.class);
            switch (ObjMsg.msgType) {
                case PatientLogin: {
                    try {
                        BRVerify brVerify = new BRVerify();
                        BR br = (BR) JsonToObject.jsonToObject(ObjMsg.object.get(0), BR.class);
                        String sql = "SELECT DLKL FROM T_BRXX WHERE BRBH = '" + br.BRBH + "'";
                        ResultSet resultSet = ServerDB.sqlExecute(sql);
                        String Correct_BLKL = "";
                        while (resultSet.next()) {
                            Correct_BLKL = resultSet.getString("DLKL");
                        }
                        ServerDB.sqlExit();
                        if (!Correct_BLKL.isEmpty()) {
                            Boolean compare_res = Correct_BLKL.equals(br.DLKL);
                            if (compare_res) {
                                User already = Token.hasUser(br.BRBH, Person.Patient);
                                if (already != null) {
                                    if (already.state == State.ON) {
                                        brVerify.reason = PatientAlreadyOn;
                                    } else {
                                        Date DLRQ = new Date();
                                        sql = "UPDATE T_BRXX SET DLRQ = '" + DLRQ.toString() + "' WHERE BRBH = '" + br.BRBH + "'";
                                        ServerDB.sqlExecute(sql);
                                        ServerDB.sqlExit();
                                        brVerify.token = already.getToken();
                                        brVerify.isOK = true;
                                    }
                                } else {
                                    Date DLRQ = new Date();
                                    sql = "UPDATE T_BRXX SET DLRQ = '" + DLRQ.toString() + "' WHERE BRBH = '" + br.BRBH + "'";
                                    ServerDB.sqlExecute(sql);
                                    ServerDB.sqlExit();
                                    User user = new User(Person.Patient, br.BRBH, State.ON);
                                    String token = user.getToken();
                                    Token.putData(token, user);
                                    brVerify.token = token;
                                    brVerify.isOK = true;
                                }
                            } else {
                                brVerify.reason = WrongDLKL;
                            }
                        } else {
                            brVerify.reason = WrongBRBH;
                        }
                        Msg retMsg = new Msg(PatientLoginVerify);
                        retMsg.object.add(ObjectToJsonUtil.objectToJson(brVerify));
                        return ObjectToJsonUtil.objectToJson(retMsg);
                    } catch (Exception e) {

                    }
                }
                case QueryKSXX: {
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            Msg sendmsg = new Msg(QueryKSXX);
                            String sql = "SELECT * FROM T_KSXX";
                            ResultSet resultSet = ServerDB.sqlExecute(sql);
                            while (resultSet.next()) {
                                KS ks = new KS(resultSet.getString("KSBH"), resultSet.getString("KSMC"),
                                        resultSet.getString("PYZS"));
                                sendmsg.object.add(ObjectToJsonUtil.objectToJson(ks));
                            }
                            ServerDB.sqlExit();
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            return sendstr;
                        } else {
                            return "";
                        }
                    }catch (Exception e) {

                    }
                }
                case QueryYSXX: {
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            String ks = ObjMsg.object.get(0);
                            Msg sendmsg = new Msg(QueryYSXX);
                            String sql = "SELECT * FROM T_KSYS WHERE KSBH = '" + ks + "'";
                            ResultSet resultSet = ServerDB.sqlExecute(sql);
                            while (resultSet.next()) {
                                YS ys = new YS(resultSet.getString("YSBH"), resultSet.getString("KSBH"),
                                        resultSet.getString("YSMC"), resultSet.getString("PYZS"),
                                        "", resultSet.getBoolean("SFZJ"), null);
                                sendmsg.object.add(ObjectToJsonUtil.objectToJson(ys));
                            }
                            ServerDB.sqlExit();
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            return sendstr;
                        } else {
                            return "";
                        }
                    } catch (Exception e) {

                    }
                }
                case QueryHZXX: {
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            HZ hzsample = (HZ) JsonToObject.jsonToObject(ObjMsg.object.get(0), HZ.class);
                            Msg sendmsg = new Msg(QueryHZXX);
                            String sql = "SELECT * FROM T_HZXX WHERE KSBH = '" + hzsample.KSBH + "' AND SFZJ = " + (hzsample.SFZJ ? 1 : 0);
                            ResultSet resultSet = ServerDB.sqlExecute(sql);
                            while (resultSet.next()) {
                                HZ hz = new HZ(resultSet.getString("HZBH"), resultSet.getString("HZMC"),
                                        resultSet.getString("PYZS"), hzsample.KSBH, hzsample.SFZJ,
                                        resultSet.getDouble("GHFY"));
                                sendmsg.object.add(ObjectToJsonUtil.objectToJson(hz));
                            }
                            ServerDB.sqlExit();
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            
                            return sendstr;
                        } else {
                            return "";
                        }
                    } catch (Exception e) {

                    }
                }
                case QueryYCJE: {
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            BR br = (BR) JsonToObject.jsonToObject(ObjMsg.object.get(0), BR.class);
                            Msg sendmsg = new Msg(QueryYCJE);
                            String sql = "SELECT YCJE FROM T_BRXX WHERE BRBH = '" + br.BRBH + "'";
                            ResultSet resultSet = ServerDB.sqlExecute(sql);
                            while (resultSet.next()) {
                                br.YCJE = resultSet.getDouble("YCJE");
                                sendmsg.object.add(ObjectToJsonUtil.objectToJson(br));
                            }
                            ServerDB.sqlExit();
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            return sendstr;
                        } else {
                            return "";
                        }
                    } catch (Exception e) {

                    }
                }
                case PatientLogout: {
                    if (Token.isValid(ObjMsg.token)) {
                        BR br = (BR) JsonToObject.jsonToObject(ObjMsg.object.get(0), BR.class);
                        if (Token.hasUser(ObjMsg.token, br.BRBH)) {
                            User user = Token.getUser(ObjMsg.token);
                            user.state = State.OFF;
                            Msg retMsg = new Msg(PatientLogout);
                            retMsg.object.add(ObjectToJsonUtil.objectToJson(true));
                            return ObjectToJsonUtil.objectToJson(retMsg);
                        }
                    } else {
                        return "";
                    }
                }
                case CutPatientMonty: {
                    //缴费成功，返回编号
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            Msg sendmsg = new Msg(CutPatientMonty);
                            Double cutMoney = Double.valueOf(ObjMsg.object.get(0));
                            User user = Token.getUser(ObjMsg.token);
                            patientMoneyHandler(user.bh, cutMoney);
                            sendmsg.object.add(ObjectToJsonUtil.objectToJson(true));
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            return sendstr;
                        } else {
                            return "";
                        }

                    } catch (Exception e) {

                    }
                }
                case NumberQuerry: {
                    try {

                        if (Token.isValid(ObjMsg.token)) {
                            //TODO 加锁

                            GH gh = (GH) JsonToObject.jsonToObject(ObjMsg.object.get(0), GH.class);
                            Msg sendmsg = new Msg(NumberQuerry);

                            sendmsg.object.add(handleNumberQuerry(gh));
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            
                            return sendstr;
                        } else {
                            return "";
                        }
                    } catch (Exception e) {

                    }
                }
                case NumberGiveup: {
                    //退号逻辑
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            GH gh = (GH) JsonToObject.jsonToObject(ObjMsg.object.get(0), GH.class);
                            Msg sendmsg = new Msg(NumberGiveup);
                            String sql = "UPDATE T_GHXX SET THBZ = " + (gh.THBZ ? 1 : 0) + " WHERE GHBH = '" + gh.GHBH + "'";
                            ServerDB.sqlExecute(sql);
                            ServerDB.sqlExit();
                            sendmsg.object.add(ObjectToJsonUtil.objectToJson(true));
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            return sendstr;
                        } else {
                            return "";
                        }
                    } catch (Exception e) {

                    }
                }
                case DoctorLogin: {
                    try {
                        YSVerify ysVerify = new YSVerify();
                        YS ys = (YS) JsonToObject.jsonToObject(ObjMsg.object.get(0), YS.class);
                        String sql = "SELECT DLKL FROM T_KSYS WHERE YSBH = '" + ys.YSBH + "'";
                        ResultSet resultSet = ServerDB.sqlExecute(sql);
                        String Correct_BLKL = "";
                        while (resultSet.next()) {
                            Correct_BLKL = resultSet.getString("DLKL");
                        }
                        ServerDB.sqlExit();
                        if (!Correct_BLKL.isEmpty()) {
                            Boolean compare_res = Correct_BLKL.equals(ys.DLKL);
                            if (compare_res) {
                                User already = Token.hasUser(ys.YSBH, Person.Doctor);
                                if (already != null) {
                                    if (already.state == State.ON) {
                                        ysVerify.reason = DoctorAlreadyOn;
                                    } else {
                                        Date DLRQ = new Date();
                                        sql = "UPDATE T_KSYS SET DLRQ = '" + DLRQ.toString() + "' WHERE YSBH = '" + ys.YSBH + "'";
                                        ServerDB.sqlExecute(sql);
                                        ServerDB.sqlExit();
                                        ysVerify.token = already.getToken();
                                        ysVerify.isOK = true;
                                    }
                                } else {
                                    Date DLRQ = new Date();
                                    sql = "UPDATE T_KSYS SET DLRQ = '" + DLRQ.toString() + "' WHERE YSBH = '" + ys.YSBH + "'";
                                    ServerDB.sqlExecute(sql);
                                    ServerDB.sqlExit();
                                    User user = new User(Person.Doctor, ys.YSBH, State.ON);
                                    String token = user.getToken();
                                    Token.putData(token, user);
                                    ysVerify.token = token;
                                    ysVerify.isOK = true;
                                }
                            } else {
                                ysVerify.reason = DoctorWrongDLKL;
                            }
                        } else {
                            ysVerify.reason = DoctorWrongBRBH;
                        }
                        Msg retMsg = new Msg(DoctorLoginVerify);
                        retMsg.object.add(ObjectToJsonUtil.objectToJson(ysVerify));
                        
                        return ObjectToJsonUtil.objectToJson(retMsg);
                    } catch (Exception e) {

                    }
                }
                case QueryDoctor:{
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            Msg sendmsg = new Msg(QueryDoctor);
                            YS ys = (YS) JsonToObject.jsonToObject(ObjMsg.object.get(0), YS.class);
                            String sql = "SELECT KSBH, YSMC, SFZJ, DLRQ FROM T_KSYS WHERE YSBH = '" + ys.YSBH + "'";
                            ResultSet resultSet = ServerDB.sqlExecute(sql);
                            YS newys = new YS(ys.YSBH, resultSet.getString("KSBH"),
                                        resultSet.getString("YSMC"), "", "",
                                        resultSet.getBoolean("SFZJ"), new Date(resultSet.getString("DLRQ")));
                            ServerDB.sqlExit();
                            newys.DLRQ.setHours(newys.DLRQ.getHours() - 14);
                            sendmsg.object.add(ObjectToJsonUtil.objectToJson(newys));
                            sql = "SELECT KSBH, KSMC FROM T_KSXX WHERE KSBH = '" + newys.KSBH + "'";
                            resultSet = ServerDB.sqlExecute(sql);
                            while (resultSet.next()){
                                KS ks = new KS(resultSet.getString("KSBH"), resultSet.getString("KSMC"), null);
                                sendmsg.object.add(ObjectToJsonUtil.objectToJson(ks));
                            }
                            ServerDB.sqlExit();
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            return sendstr;
                        } else {
                            return "";
                        }
                    }catch (Exception e) {

                    }
                }
                case PatientList:{
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            Msg sendmsg = new Msg(PatientList);
                            YS ys = (YS) JsonToObject.jsonToObject(ObjMsg.object.get(0), YS.class);

                            String sql = "SELECT T_GHXX.GHBH, T_BRXX.BRMC, T_HZXX.HZMC, T_HZXX.SFZJ, T_GHXX.RQSJ " +
                                    "FROM T_GHXX, T_BRXX, T_HZXX " +
                                    "WHERE YSBH = '" + ys.YSBH + "' AND THBZ = 0 " +
                                    "AND T_GHXX.BRBH = T_BRXX.BRBH AND T_HZXX.HZBH = T_GHXX.HZBH ";
                            ResultSet resultSet = ServerDB.sqlExecute(sql);
                            while(resultSet.next()) {
                                GH_Table gh_table = new GH_Table(resultSet.getString("GHBH"),
                                        resultSet.getString("BRMC"), resultSet.getString("HZMC"),
                                        resultSet.getBoolean("SFZJ"),  new Date(resultSet.getString("RQSJ")));
                                gh_table.GHRQ.setHours(gh_table.GHRQ.getHours() - 14);
                                sendmsg.object.add(ObjectToJsonUtil.objectToJson(gh_table));
                            }
                            ServerDB.sqlExit();
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            return sendstr;
                        } else {
                            return "";
                        }
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case SalaryList:{
                    try {
                        if (Token.isValid(ObjMsg.token)) {
                            Msg sendmsg = new Msg(SalaryList);
                            Date start_time = (Date) JsonToObject.jsonToObject(ObjMsg.object.get(0), Date.class);
                            Date end_time = (Date) JsonToObject.jsonToObject(ObjMsg.object.get(1), Date.class);
                            String sql = "SELECT T_KSXX.KSMC, T_GHXX.YSBH, T_KSYS.YSMC, T_HZXX.HZMC, T_HZXX.SFZJ HZLB, COUNT(*) GHRC," +
                                    " SUM(T_GHXX.GHFY) SRHJ FROM T_GHXX, T_KSXX, T_HZXX, T_KSYS " +
                                    "WHERE T_GHXX.YSBH = T_KSYS.YSBH AND T_GHXX.HZBH = T_HZXX.HZBH AND T_KSYS.KSBH = T_KSXX.KSBH " +
                                    "AND T_GHXX.THBZ = 0 " +
                                    "AND T_GHXX.RQSJ BETWEEN '" + start_time.toString() + "' AND '" + end_time.toString() + "'" +
                                    "GROUP BY T_GHXX.YSBH, T_GHXX.HZBH";
                            ResultSet resultSet = ServerDB.sqlExecute(sql);
                            while(resultSet.next()) {
                                YS_Table ys_table = new YS_Table(resultSet.getString("KSMC"), resultSet.getString("YSBH"),
                                        resultSet.getString("YSMC"), resultSet.getString("HZMC"),
                                        resultSet.getBoolean("HZLB"), resultSet.getInt("GHRC"),
                                        resultSet.getInt("SRHJ"));
                                sendmsg.object.add(ObjectToJsonUtil.objectToJson(ys_table));
                            }
                            ServerDB.sqlExit();
                            String sendstr = ObjectToJsonUtil.objectToJson(sendmsg);
                            
                            return sendstr;
                        } else {
                            return "";
                        }
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case DoctorLogout: {
                    if (Token.isValid(ObjMsg.token)) {
                        YS ys = (YS) JsonToObject.jsonToObject(ObjMsg.object.get(0), YS.class);
                        if (Token.hasUser(ObjMsg.token, ys.YSBH)) {
                            User user = Token.getUser(ObjMsg.token);
                            user.state = State.OFF;
                            Msg retMsg = new Msg(DoctorLogout);
                            retMsg.object.add(ObjectToJsonUtil.objectToJson(true));
                            return ObjectToJsonUtil.objectToJson(retMsg);
                        }
                    } else {
                        return "";
                    }
                }
            }
        return "";
    }
    public static synchronized void patientMoneyHandler(String patientNo, Double cutMoney) throws Exception{
        Double extra = 0.0;
        String sql = "SELECT YCJE FROM T_BRXX WHERE BRBH = '" + patientNo + "'";
        ResultSet resultSet = ServerDB.sqlExecute(sql);
        while (resultSet.next()) {
            Double ycje = resultSet.getDouble("YCJE");
            extra = ycje - cutMoney;
        }
        ServerDB.sqlExit();
        sql = "BEGIN TRANSACTION";
        ServerDB.sqlExecute(sql);
        ServerDB.sqlExit();
        sql = "UPDATE T_BRXX SET YCJE = " + extra.toString() + " WHERE BRBH = '" + patientNo + "'";
        ServerDB.sqlExecute(sql);
        ServerDB.sqlExit();
        sql = "END TRANSACTION";
        ServerDB.sqlExecute(sql);
        ServerDB.sqlExit();
    }

    public static synchronized String handleNumberQuerry(GH gh) throws Exception{
        Integer num = null;
        Date date = new Date();
        Date start = (Date) date.clone();
        start.setHours(0);
        start.setMinutes(0);
        start.setSeconds(0);
        Date end = (Date) start.clone();
        end.setHours(23);
        end.setMinutes(59);
        end.setSeconds(59);
        String sql = "SELECT COUNT(*) FROM T_GHXX";
        ResultSet resultSet = ServerDB.sqlExecute(sql);
        while (resultSet.next()) {
            num = resultSet.getInt("COUNT(*)");
        }
        ServerDB.sqlExit();
        num++;
        String number = num.toString();
        num = 6 - number.length();
        String a = "000000";
        a = a.substring(0, num);
        number = a.concat(number);
        sql = "SELECT COUNT(*) FROM T_GHXX WHERE HZBH = '" + gh.HZBH + "' AND THBZ = 0 AND RQSJ BETWEEN '" + start.toString()
                + "' AND '" + end.toString() +"'";
        resultSet = ServerDB.sqlExecute(sql);
        while (resultSet.next()) {
            num = resultSet.getInt("COUNT(*)");
        }
        ServerDB.sqlExit();
        sql = "SELECT GHRS FROM T_HZXX WHERE HZBH = '" + gh.HZBH + "'";
        resultSet = ServerDB.sqlExecute(sql);
        int allnum = 0;
        while (resultSet.next()) {
            allnum = resultSet.getInt("GHRS");
        }
        ServerDB.sqlExit();
        if(num >= allnum){
            return "Numberisfull";
        }
        num++;
        Date RQSJ = new Date();
        sql = "INSERT INTO T_GHXX (GHBH, HZBH, YSBH, BRBH, GHRC, THBZ, GHFY, RQSJ) " +
                "VALUES ('" + number + "', '" + gh.HZBH + "', '" + gh.YSBH + "', '" + gh.BRBH + "', " +
                num + ", " + (gh.THBZ ? 1 : 0) + ", " + gh.GHFY + ", '" + RQSJ.toString() + "');";
        ServerDB.sqlExecute(sql);
        ServerDB.sqlExit();
        return number;
    }
}
