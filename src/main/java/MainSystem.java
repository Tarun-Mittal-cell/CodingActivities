
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainSystem {

    public static ProcessManager processManager;

    public static void main(String[] args) {

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
              //  while (System.currentTimeMillis() - startTime < runTime)
                while (true)
                {
                    timeElapsed1=System.currentTimeMillis() - Control.lastMessage1;
                    if (timeElapsed1>5000&&Control.lastMessage1!=0 )
                    {
                        System.out.println("System 1 Offline.");
                        System.out.println("Restarting System 1...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        processManager.initProcessA(0);
                    }

                    timeElapsed2=System.currentTimeMillis() - Control.lastMessage2;
                    if (timeElapsed2>5000&&Control.lastMessage2!=0 )
                    {
                        System.out.println("System 2 Offline.");
                        System.out.println("Restarting System 2...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
        thread.setDaemon(true);
        thread.start();

        processManager.initProcessA(0);
        processManager.initProcessB(1);

        ServerSocket serverSocket= null;

        try {
            serverSocket = new ServerSocket(6355);

            System.out.println("Listening for connections.....");

            int count=0;
            while (count!=5)
            {
                //Accept communication on socket
                client = serverSocket.accept();
                System.out.println("Connection established....");
                count++;

                //Input stream to read information from client
                InputStream inputStream = client.getInputStream();

                //Output stream to send information to client
                OutputStream outputStream = client.getOutputStream();
                ClientHandler clientHandler = new ClientHandler(client, inputStream, outputStream);
                clientHandler.start();
            }
            processManager.endAllProcesses();
            System.out.println("Ending Server...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            try{
               serverSocket.close();

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
