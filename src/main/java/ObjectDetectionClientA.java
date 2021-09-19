
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;

public class ObjectDetectionClientA {
    private static int counter = 1;

    public static void main(String[] args) {
        //FaultLogger faultLogger = new FaultLogger();

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
                    System.out.println(local.getHostName());
                    socket = new Socket(local.getHostName(), 6355);
                    System.out.println(local.getHostName() + " : " + socket.getLocalPort());
                    //Create output stream to send information to the game server
                    OutputStream outputStream = socket.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    //Create print writer to send information line by line to server
                    printWriter = new PrintWriter(outputStreamWriter, true);

                    //Create output stream to receive information from the game server
                    InputStream inputStream = socket.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    reader = new BufferedReader(inputStreamReader);

                    int threadNumber = counter;
                    counter++;
                    int maxInterationCount = 10;
                    int iterationCount = 0;
                    while (true)
                    {
                        iterationCount++;
                        printWriter.println("1 Alive");

                        try {
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        if(ObjectDetectionSystem.error){
                            if(iterationCount == maxInterationCount) {
                                printWriter.println("ERROR: " + threadNumber);
                                break;
                            }
                        }

                    }
                    System.out.println("Client TERMINATED: " + threadNumber);
                    System.exit(0);


                } catch (Exception e) {
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
        thread.start();
//
//        //Create Directory to video frames.
        File file=new File("src/main/java/frames/");
        // Store picture names in  array
        String [] frameFiles=file.list();
        ObjectDetectionSystem detectionSystem=new ObjectDetectionSystem();

        detectionSystem.configure();
        //
        for (String frame:frameFiles)
        {
            detectionSystem.detect(Paths.get("src/main/java/frames/"+frame));
        }

    }
}
