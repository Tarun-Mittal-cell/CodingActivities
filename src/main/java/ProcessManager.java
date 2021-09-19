import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {
    private Process[] processes;

    public ProcessManager(){
        processes = new Process[]{ null, null};
    }

    public void initProcessA(int index){
        if(processes[index] != null){
            if(processes[index].isAlive()){
                processes[index].destroyForcibly();
                processes[index] = null;
            }
        }
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
        if(processes[index] != null){
            if(processes[index].isAlive()){
                processes[index].destroyForcibly();
                processes[index] = null;
            }
        }
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

    public void endAllProcesses(){
        System.out.println("ending things");
        for(int index = 0; index < processes.length; index++){
            if(processes[index] != null){
                if(processes[index].isAlive()){
                    processes[index].destroyForcibly();
                    processes[index] = null;
                }
            }
        }
    }
}
