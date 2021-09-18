import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

public class MainServer {

    public static void main(String[] args) {
        String line;
        BufferedReader reader=null;
        PrintWriter printWriter=null;
        Socket client=null;
        Thread thread   = new Thread(new Runnable() {
            @Override
            public void run() {
                long  timeElapsed1=0;
                long  timeElapsed2=0;
                while (true)
                {
                    timeElapsed1=System.currentTimeMillis()- Control.lastMessage1;
                    // System.out.println(Thread.currentThread().getName() + " Elapsed Time System 1: " + Control.lastMessage1);
                    if (timeElapsed1>5000&&Control.lastMessage1!=0 )
                    {
                        System.out.println("System 1 Offline.");
                    }

                    timeElapsed2=System.currentTimeMillis()- Control.lastMessage2;
                    //System.out.println(Thread.currentThread().getName() + " Elapsed Time System 2: " + Control.lastMessage2);
                    if (timeElapsed2>5000&&Control.lastMessage2!=0 )
                    {
                        System.out.println("System 2 Offline.");
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

        //Create server socket for game.
        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(6355);

            System.out.println("Listening for connections.....");
            //Accept communication on socket
            while (true) {
                client = serverSocket.accept();
                System.out.println("Connection established....");

                //Input stream to read information from client
                InputStream inputStream = client.getInputStream();

                //Output stream to  send information to client
                OutputStream outputStream = client.getOutputStream();
                ClientHandler clientHandler = new ClientHandler(client, inputStream, outputStream);
                clientHandler.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
