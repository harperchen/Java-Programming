package pClient.user;

import pClient.PClientNet;
import pClient.json.*;

import java.util.ArrayList;
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
    public final static int PatientLogin = 1;
    public final static int PatientLoginVerify = 2;
    public final static int WrongBRBH = 3;
    public final static int WrongDLKL = 4;
    public final static int QueryKSXX = 5;
    public final static int QueryYSXX = 6;
    public final static int QueryHZXX = 7;
    public final static int QueryYCJE = 8;
    public final static int PatientLogout = 9;
    public final static int PatientAlreadyOn = 10;
    public final static int CutPatientMonty = 11;
    public final static int NumberQuerry = 12;
    public final static int NumberGiveup = 13;

    public static Object MsgHandler(String msg, int msgType){
        try {
            switch (msgType) {
                case PatientLogin: {
                    Msg sendmsg = new Msg(PatientLogin);
                    sendmsg.object.add(msg);
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    PClientNet pClientNet = new PClientNet();
                    pClientNet.sendMsg(out);
                    String in = pClientNet.recMsg();
                    pClientNet.closeNet();
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    if(recvMsg.msgType == PatientLoginVerify) {
                        BRVerify verify = (BRVerify) JsonToObject.jsonToObject(recvMsg.object.get(0), BRVerify.class);
                        if (verify.isOK) {
                            token = verify.token;
                            return verify.isOK;
                        } else {
                            return verify.reason;
                        }
                    }
                }
                case QueryKSXX: {
                    try {
                        //带token
                        Msg sendmsg = new Msg(token, QueryKSXX);
                        String out = ObjectToJsonUtil.objectToJson(sendmsg);
                        PClientNet pClientNet = new PClientNet();
                        pClientNet.sendMsg(out);
                        String in = pClientNet.recMsg();
                        pClientNet.closeNet();
                        if(in.equals("")){
                            System.out.println("Wrong Token");
                            System.exit(0);
                        }
                        Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                        List<KS> ksxx = new ArrayList<KS>();
                        for (String str_ks : recvMsg.object) {
                            ksxx.add((KS) JsonToObject.jsonToObject(str_ks, KS.class));
                        }
                        return ksxx;
                    } catch (Exception e) {

                    }
                }
                case QueryYSXX:{
                    Msg sendmsg = new Msg(token, QueryYSXX);
                    sendmsg.object.add(msg.substring(0, 6));
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    PClientNet pClientNet = new PClientNet();
                    pClientNet.sendMsg(out);
                    String in = pClientNet.recMsg();
                    pClientNet.closeNet();
                    if(in.equals("")){
                        System.out.println("Wrong Token");
                        System.exit(0);
                    }
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    List<YS> ysxx = new ArrayList<YS>();
                    for (String str_ys : recvMsg.object) {
                        ysxx.add((YS) JsonToObject.jsonToObject(str_ys, YS.class));
                    }
                    return ysxx;
                }
                case QueryHZXX:{
                    Msg sendmsg = new Msg(token, QueryHZXX);
                    sendmsg.object.add(msg);
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    PClientNet pClientNet = new PClientNet();
                    pClientNet.sendMsg(out);
                    String in = pClientNet.recMsg();
                    pClientNet.closeNet();
                    if(in.equals("")){
                        System.out.println("Wrong Token");
                        System.exit(0);
                    }
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    List<HZ> hzxx = new ArrayList<HZ>();
                    for (String str_hz : recvMsg.object) {
                        hzxx.add((HZ) JsonToObject.jsonToObject(str_hz, HZ.class));
                    }
                    return hzxx;
                }
                case QueryYCJE:{
                    Msg sendmsg = new Msg(token, QueryYCJE);
                    sendmsg.object.add(msg);
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    PClientNet pClientNet = new PClientNet();
                    pClientNet.sendMsg(out);
                    String in = pClientNet.recMsg();
                    pClientNet.closeNet();
                    if(in.equals("")){
                        System.out.println("Wrong Token");
                        System.exit(0);
                    }
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    BR br = (BR) JsonToObject.jsonToObject(recvMsg.object.get(0), BR.class);
                    return br.YCJE;
                }
                case CutPatientMonty:{
                    Msg sendmsg = new Msg(token, CutPatientMonty);
                    sendmsg.object.add(msg);
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    PClientNet pClientNet = new PClientNet();
                    pClientNet.sendMsg(out);
                    String in = pClientNet.recMsg();
                    pClientNet.closeNet();
                    if(in.equals("")){
                        System.out.println("Wrong Token");
                        System.exit(0);
                    }
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    Boolean bool = (Boolean) JsonToObject.jsonToObject(recvMsg.object.get(0), Boolean.class);
                    return bool;
                }
                case PatientLogout:{
                    Msg sendmsg = new Msg(token, PatientLogout);
                    sendmsg.object.add(msg);
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    PClientNet pClientNet = new PClientNet();
                    pClientNet.sendMsg(out);
                    String in = pClientNet.recMsg();
                    pClientNet.closeNet();
                    if(in.equals("")){
                        System.out.println("Wrong Token");
                        System.exit(0);
                    }
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    Boolean bool = (Boolean) JsonToObject.jsonToObject(recvMsg.object.get(0), Boolean.class);
                    return bool;
                }
                case NumberQuerry:{
                    Msg sendmsg = new Msg(token, NumberQuerry);
                    sendmsg.object.add(msg);
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    PClientNet pClientNet = new PClientNet();
                    pClientNet.sendMsg(out);
                    String in = pClientNet.recMsg();
                    pClientNet.closeNet();
                    if(in.equals("")){
                        System.out.println("Wrong Token");
                        System.exit(0);
                    }
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    if(recvMsg.object.get(0).equals("Numberisfull")){
                        return "Numberisfull";
                    }
                    String ghbh = (String) JsonToObject.jsonToObject(recvMsg.object.get(0), String.class);
                    return ghbh;
                }
                case NumberGiveup:{
                    Msg sendmsg = new Msg(token, NumberGiveup);
                    sendmsg.object.add(msg);
                    String out = ObjectToJsonUtil.objectToJson(sendmsg);
                    PClientNet pClientNet = new PClientNet();
                    pClientNet.sendMsg(out);
                    String in = pClientNet.recMsg();
                    pClientNet.closeNet();
                    if(in.equals("")){
                        System.out.println("Wrong Token");
                        System.exit(0);
                    }
                    Msg recvMsg = (Msg) JsonToObject.jsonToObject(in, Msg.class);
                    Boolean result = (Boolean) JsonToObject.jsonToObject(recvMsg.object.get(0), Boolean.class);
                    return result;
                }
            }
        }catch (Exception e){

        }
        return null;
    }

}
