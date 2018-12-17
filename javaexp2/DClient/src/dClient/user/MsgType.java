package dClient.user;

import dClient.DClientNet;
import dClient.json.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private static String token = "";
    public final static int DoctorLogin = 21;
    public final static int DoctorLoginVerify = 22;
    public final static int DoctorWrongYSBH = 23;
    public final static int DoctorWrongDLKL = 24;
    public final static int DoctorAlreadyOn = 25;
    public final static int QueryDoctor = 26;
    public final static int DoctorLogout = 27;
    public final static int PatientList = 28;
    public final static int SalaryList = 29;

    public static Object MsgHandler(String msg, int msgType) {
        switch (msgType) {
            case DoctorLogin: {
                Msg sendmsg = new Msg(DoctorLogin);
                sendmsg.object.add(msg);
                String out = ObjectToJsonUtil.objectToJson(sendmsg);
                DClientNet dClientNet = new DClientNet();
                dClientNet.sendMsg(out);
                String in = dClientNet.recMsg();
                dClientNet.closeNet();
                Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                if (recvMsg.msgType == DoctorLoginVerify) {
                    YSVerify verify = (YSVerify) JsonToObject.jsonToObject(recvMsg.object.get(0), YSVerify.class);
                    if (verify.isOK) {
                        token = verify.token;
                        return verify.isOK;
                    } else {
                        return verify.reason;
                    }
                }
            }
            case QueryDoctor: {
                try {
                    //带token
                    Msg sendmsg = new Msg(token, QueryDoctor);
                    sendmsg.object.add(msg);
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    DClientNet dClientNet = new DClientNet();
                    dClientNet.sendMsg(out);
                    String in = dClientNet.recMsg();
                    dClientNet.closeNet();
                    if (in.equals("")) {
                        System.out.println("Wrong Token");
                        System.exit(0);
                    }
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    return recvMsg.object;
                } catch (Exception e) {

                }
            }
            case DoctorLogout:{
                Msg sendmsg = new Msg(token, DoctorLogout);
                sendmsg.object.add(msg);
                String out = ObjectToJsonUtil.objectToJson(sendmsg);
                DClientNet dClientNet = new DClientNet();
                dClientNet.sendMsg(out);
                String in = dClientNet.recMsg();
                dClientNet.closeNet();
                if(in.equals("")){
                    System.out.println("Wrong Token");
                    System.exit(0);
                }
                Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                Boolean bool = (Boolean) JsonToObject.jsonToObject(recvMsg.object.get(0), Boolean.class);
                return bool;
            }
            case PatientList:{
                Msg sendmsg = new Msg(token, PatientList);
                sendmsg.object.add(msg);
                Date date = new Date();
                sendmsg.object.add(date.toString());
                String out = ObjectToJsonUtil.objectToJson(sendmsg);
                DClientNet dClientNet = new DClientNet();
                dClientNet.sendMsg(out);
                String in = dClientNet.recMsg();
                dClientNet.closeNet();
                if(in.equals("")){
                    System.out.println("Wrong Token");
                    System.exit(0);
                }
                Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);

                ObservableList<GH_Table> gh_tables = FXCollections.observableArrayList();
                for(int i = 0; i < recvMsg.object.size(); i++) {
                    GH_Table gh_table = (GH_Table) JsonToObject.jsonToObject(recvMsg.object.get(i), GH_Table.class);
                    gh_tables.add(gh_table);
                }
                return gh_tables;
            }
            case SalaryList:{
                Msg sendmsg = new Msg(token, SalaryList);
                String[] dates = msg.split("_");
                sendmsg.object.add(dates[0]);
                sendmsg.object.add(dates[1]);

                String out = ObjectToJsonUtil.objectToJson(sendmsg);
                DClientNet dClientNet = new DClientNet();
                dClientNet.sendMsg(out);
                String in = dClientNet.recMsg();
                dClientNet.closeNet();
                if(in.equals("")){
                    System.out.println("Wrong Token");
                    System.exit(0);
                }
                Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);

                ObservableList<YS_Table> ys_tables = FXCollections.observableArrayList();
                for(int i = 0; i < recvMsg.object.size(); i++) {
                    YS_Table ys_table = (YS_Table) JsonToObject.jsonToObject(recvMsg.object.get(i), YS_Table.class);
                    ys_tables.add(ys_table);
                }
                return ys_tables;
            }
        }
        return null;
    }
}
