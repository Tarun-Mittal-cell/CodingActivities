
import javax.management.ObjectName;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {

    public static ProcessManager processManager;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        long runTime = 50000; //number of seconds before a graceful shutdown of the whole system

        String line;
        BufferedReader reader=null;
        PrintWriter printWriter=null;
        Socket client=null;

        processManager = new ProcessManager();

        Thread thread   = new Thread(new Runnable() {
            @Override
            public void run() {
                long  timeElapsed1=0;
                long  timeElapsed2=0;
                while (System.currentTimeMillis() - startTime < runTime)
                {
                    timeElapsed1=System.currentTimeMillis() - Control.lastMessage1;
                    // System.out.println(Thread.currentThread().getName() + " Elapsed Time System 1: " + Control.lastMessage1);
                    if (timeElapsed1>5000&&Control.lastMessage1!=0 )
                    {
                        System.out.println("System 1 Offline.");
                        processManager.initProcessA(0);
                    }

                    timeElapsed2=System.currentTimeMillis() - Control.lastMessage2;
                    //System.out.println(Thread.currentThread().getName() + " Elapsed Time System 2: " + Control.lastMessage2);
                    if (timeElapsed2>5000&&Control.lastMessage2!=0 )
                    {
                        System.out.println("System 2 Offline.");
                        processManager.initProcessB(1);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
        //thread.interrupt();
        processManager.initProcessA(0);
        processManager.initProcessB(1);
        //processManager.endAllProcesses();
        //Create server socket for game.
        List<ClientHandler> handlers = new ArrayList<ClientHandler>();
        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(6355);

            System.out.println("Listening for connections.....");
            //Accept communication on socket
            while (System.currentTimeMillis() - startTime < runTime) {
                client = serverSocket.accept();
                System.out.println("Connection established....");

                //Input stream to read information from client
                InputStream inputStream = client.getInputStream();

                //Output stream to  send information to client
                OutputStream outputStream = client.getOutputStream();
                ClientHandler clientHandler = new ClientHandler(client, inputStream, outputStream);
                clientHandler.start();
                handlers.add(clientHandler);
            }
            System.out.println("Ending Server");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            try{
                serverSocket.close();
                for (ClientHandler c: handlers
                     ) {
                    c.interrupt();
                }

                processManager.endAllProcesses();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        //manage the processes for active redundancy
        //thread.interrupt();
        //processManager.endAllProcesses();

    }
}
