package server;

public class Main{
    public static void main(String [] args) {
  //      ServerDB.InitDB();
        ServerNet t = new ServerNet();
        t.start();
    }
}
