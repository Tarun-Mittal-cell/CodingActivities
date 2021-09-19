package main.java;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final InputStream inputStream  ;
    private final OutputStream outputStream;
    private final Socket client;

    public ClientHandler(Socket client, InputStream inputStream , OutputStream outputStream)
    {
        this.client=client;
        this.outputStream=outputStream;
        this.inputStream=inputStream;
    }

    @Override
    public void run() {

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        //Output stream to  send information to client
        PrintWriter printWriter = new PrintWriter(outputStream, true);

        String line = null;
        try {
            line = reader.readLine();
            String index=line.split(" ")[0];
            if(index.equals("1"))
            {
                Control.lastMessage1 = System.currentTimeMillis();
            }
            else
            {
                Control.lastMessage2 = System.currentTimeMillis();
            }

            boolean finished = false;
            while (!finished) {

                if (line.equals("finished")) {
                    finished = true;
                }
                System.out.println("Client Sent: " + line);
                if (!finished) {
                    line = reader.readLine();
                    index=line.split(" ")[0];
                    if(index.equals("1"))
                    {
                        Control.lastMessage1 = System.currentTimeMillis();
                    }
                    else if(index.equals("2"))
                    {
                        Control.lastMessage2 = System.currentTimeMillis();
                    }
                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {

                if (client != null) {
                    client.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing resources.");
            }
        }
    }



}
