package dClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DClientNet {

    Socket client;
    int port = 8000;
    String serverName = "127.0.0.1";

    public DClientNet(){
        try{
            System.out.println("连接到主机： " + serverName + ", 端口号： " + port);
            client = new Socket(serverName, port);
            System.out.println("远程主机地址： " + client.getRemoteSocketAddress());
        }catch(Exception e){

        }
    }

    public void sendMsg(String out){
        try {
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream outputStream = new DataOutputStream(outToServer);
            outputStream.writeUTF(out);
        }catch(Exception e){

        }
    }

    public String recMsg(){
        try {
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            String in_str = in.readUTF();
            System.out.println("服务器响应： " + in_str);
            return in_str;
        }catch(Exception e){

        }
        return "";
    }

    public void closeNet(){
        try {
            client.close();
        }catch(Exception e){

        }
    }
}
