import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainSystem {
    private static long thresholdMemoryUsage = 0;
    private static  ScheduledExecutorService meomryCheckScheduler = Executors.newSingleThreadScheduledExecutor();
    private static FaultLogger faultLogger = null;

    private static void setMemoryThresholdForNotification(int threshold){
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean() ;
        MemoryUsage heapMemoryUsage = memBean.getHeapMemoryUsage();
        long maxMemory =  heapMemoryUsage.getMax();
        thresholdMemoryUsage = (threshold*maxMemory)/100;
    }

    public static void printMemory() {
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memBean.getHeapMemoryUsage();
        long currentUsedMemory = heapMemoryUsage.getUsed();
        if(currentUsedMemory>thresholdMemoryUsage){
            int percentage = (int)((100*heapMemoryUsage.getUsed())/heapMemoryUsage.getMax());
            String output = "Heartbeat >> " + currentUsedMemory + " >> " + percentage + "%";
            System.out.println(output);
            faultLogger.logFault(output);
        }
    }

    public static void runMemoryLogger(){
        meomryCheckScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                printMemory();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args)  {
        faultLogger = new FaultLogger();
        setMemoryThresholdForNotification(50);
        runMemoryLogger();
        //Create Directory to video frames.
        File file=new File("src/main/java/frames/");
        // Store picture names in  array
        String [] frameFiles=file.list();
        ObjectDetectionSystem detectionSystem=new ObjectDetectionSystem();
        detectionSystem.configure();
        //
        for (String frame:frameFiles) {

            detectionSystem.detect(Paths.get("src/main/java/frames/"+frame));
            
        }
        meomryCheckScheduler.shutdownNow();
    }
}


