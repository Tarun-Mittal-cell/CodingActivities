import ai.djl.*;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.util.*;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class MainSystem {



    public static void main(String[] args)  {
        //Create Directory to video frames.
        File file=new File("src/main/java/frames/");
        // Store picture names in  array
        String [] frameFiles=file.list();
        ObjectDetectionSystem detectionSystem=new ObjectDetectionSystem();
        detectionSystem.configure();

        for (String frame:frameFiles) {

            detectionSystem.detect(Paths.get("src/main/java/frames/"+frame));

        }
    }
}


