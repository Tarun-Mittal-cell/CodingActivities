import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FaultLogger {
    public FaultLogger(){

    }

    //create a timestamped file, and write the provided log content to it
    //returns true if successful, false if a file or IO error occurs
    public boolean logFault(String input){
        File logFile;
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String fileName = timeStamp + "_memoryFault.txt";

            logFile = new File(fileName);
            if (logFile.createNewFile()) {
                //System.out.println("File created: " + logFile.getName());
            } else {
                //System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
        try {
            FileWriter logWriter = new FileWriter(logFile);
            logWriter.write(input);
            logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
