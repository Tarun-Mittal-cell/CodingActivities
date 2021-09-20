import com.google.gson.JsonIOException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Random;

public class ObjectDetectionClientA {
    private static int counter = 1;
    private static String[] buffer;
    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InetAddress local = null;
                PrintWriter printWriter = null;
                BufferedReader reader = null;
                Socket socket = null;

                try {
                    local = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    //Create client socket
                    socket = new Socket(local.getHostName(), 6355);
                    //Create output stream to send information to the game server
                    OutputStream outputStream = socket.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    //Create print writer to send information line by line to server
                    printWriter = new PrintWriter(outputStreamWriter, true);

                    //Create output stream to receive information from the game server
                    InputStream inputStream = socket.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    reader = new BufferedReader(inputStreamReader);

                     while (true)
                    {
                        printWriter.println("System 1 Alive");

                        try {
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                } finally {
                    try {

                        if (socket != null) {
                            socket.close();
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
        });

        thread.setDaemon(true);
        thread.start();

        Random random=new Random();
        int max=12;
        int min= 8;
        int bufferSize=random.nextInt(max - min + 1) + min;
        buffer=new String[bufferSize];

       //Create Directory to video frames.
        File file=new File("src/main/java/frames/");
        // Store picture names in  array
        String [] frameFiles=file.list();
        ObjectDetectionSystem detectionSystem=new ObjectDetectionSystem();

        detectionSystem.configure();
        //
        int index=0;
        for (String frame:frameFiles)
        {
            buffer[index]=frame;
            index++;
            detectionSystem.detect(Paths.get("src/main/java/frames/"+frame));

        }

    }
}
