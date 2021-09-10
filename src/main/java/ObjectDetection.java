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

public class ObjectDetection {



    public static void main(String[] args)  {
        //Create Directory to video frames.
        File file=new File("src/main/java/frames/");
        // Store picture names in  array
        String [] frameFiles=file.list();


        //Configure deep java library for object detection
        Criteria<BufferedImage, DetectedObjects> criteria =
                Criteria.builder()
                        .optApplication(Application.CV.OBJECT_DETECTION)
                        .setTypes(BufferedImage.class, DetectedObjects.class)
                        .optFilter("backbone", "resnet50")
                        .optProgress(new ProgressBar())
                        .build();

        //Load pre-trained model.
        ZooModel<BufferedImage, DetectedObjects> model = null;
        try {
            model = ModelZoo.loadModel(criteria);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create predictor object
        Predictor<BufferedImage, DetectedObjects> predictor = model.newPredictor();
        DetectedObjects detection=null;
        BufferedImage img = null;

        //Loop through each picture and use model to detect the objects present in each
        for (String frame:frameFiles) {
            try {
                //load image
                img = BufferedImageUtils.fromFile(Paths.get("src/main/java/frames/"+frame));

                detection = predictor.predict(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(detection);
        }
    }
}


