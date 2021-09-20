import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final InputStream inputStream  ;
    private final OutputStream outputStream;
    private final Socket client;
    private boolean end;

    public ClientHandler(Socket client, InputStream inputStream , OutputStream outputStream)
    {
        this.client=client;
        this.outputStream=outputStream;
        this.inputStream=inputStream;
        end=false;
    }

    @Override
    public void run() {

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        //Output stream to  send information to client
        PrintWriter printWriter = new PrintWriter(outputStream, true);

        String line = null;
        String index=null;
        try {
            /*line = reader.readLine();
            String index=line.split(" ")[1];
            if(index.equals("1"))
            {
                Control.lastMessage1 = System.currentTimeMillis();
            }
            else
            {
                Control.lastMessage2 = System.currentTimeMillis();
            }*/

            while (!end)
            {
                line = reader.readLine();
                System.out.println("Client Sent: " + line);
                index = line.split(" ")[1];
                if (index.equals("1")) {
                    Control.lastMessage1 = System.currentTimeMillis();
                } else if (index.equals("2")) {
                    Control.lastMessage2 = System.currentTimeMillis();
                }

            }
        } catch (Exception e)
        {
            //e.printStackTrace();
            System.out.println("Connection Lost...");
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
    public void stopThread()
    {
        end = true;
        System.out.println(Thread.currentThread().getName()+ " Ended");
    }
}
