package server;

import server.user.MsgType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class MyRunnable implements Runnable{
    private Socket server;
    public MyRunnable(Socket server){
        this.server = server;
    }
    public void run(){
        System.out.println("远程主机地址： " + server.getRemoteSocketAddress());
        //应该开启一个新的线程
        String in_string = recvMsg();
        String out_string = MsgType.MsgHandler(in_string);
        sendMsg(out_string);
        closeNet();
        System.out.println("线程执行完毕");
    }

    private void sendMsg(String out_string){
        try{
            System.out.println(out_string);
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(out_string);
        }catch(Exception e){

        }

    }

    private String recvMsg(){
        try {
            DataInputStream in = new DataInputStream(server.getInputStream());
            String in_string = in.readUTF();
            System.out.println(in_string);
            return in_string;
        }catch(Exception e){

        }
        return "";
    }

    private void closeNet(){
        try{
            server.close();
        }catch(Exception e){

        }
    }

}
