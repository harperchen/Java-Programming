package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerNet {
    private int port = 8000;
    private Socket server;
    private ServerSocket serverSocket;

    private int corePoolSize = 10;
    private int maximumPoolSize = 20;
    private long keepAlizeTime = 200;
    private ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);
    private ThreadPoolExecutor threadPoolExecutor;

    public ServerNet(){
        try {
            //用于监听
            serverSocket = new ServerSocket(port);
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAlizeTime, TimeUnit.MINUTES, workQueue);
        }catch(Exception e){

        }
    }

    public void start() {
        while (true) {
            try {
                System.out.println("等待远程连接，端口号：" + serverSocket.getLocalPort() + "...");
                server = serverSocket.accept();
                MyRunnable myRunnable = new MyRunnable(server);
                threadPoolExecutor.execute(myRunnable);
                System.out.println("线程池中现在的线程数目是：" + threadPoolExecutor.getPoolSize() + ",  队列中正在等待执行的任务数量为：" +
                        threadPoolExecutor.getQueue().size());

            } catch (SocketTimeoutException s) {

            } catch (IOException e) {

            }
        }

    }

    public void PoolClosed() {
        threadPoolExecutor.shutdown();
    }
}
