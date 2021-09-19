import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {
    private Process[] processes;

    public ProcessManager(){
        processes = new Process[2];
    }

    public void initProcessA(int index){

        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            //ObjectDetectionClientA oa = new ObjectDetectionClientA();
            //System.out.println(oa.getClass().getName());
            //String className = ObjectDetectionClientA.getClass().getName();

            List<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-cp");
            command.add(classpath);
            command.add("ObjectDetectionClientA");

            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.inheritIO().start();
            processes[index] = process;
        } catch (IOException e){
            System.out.println(e.toString());
        }

    }

    public void initProcessB(int index){
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            //ObjectDetectionClientA oa = new ObjectDetectionClientA();
            //System.out.println(oa.getClass().getName());
            //String className = ObjectDetectionClientA.getClass().getName();

            List<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-cp");
            command.add(classpath);
            command.add("ObjectDetectionClientB");

            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.inheritIO().start();
            processes[index] = process;
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
