import ai.djl.Application;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.util.BufferedImageUtils;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ObjectDetectionSystem {

    private Predictor<BufferedImage, DetectedObjects> predictor;
    public static Boolean error = false;
    private List<Thread> threadList = new ArrayList<>();

    private static int currentIteration = 1;


    public void configure() {
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
        this.predictor = model.newPredictor();
    }

    public void detect(Path path) {
        DetectedObjects detection = null;
        BufferedImage img = null;
        try {

            img = BufferedImageUtils.fromFile(path);
            detection = predictor.predict(img);
        } catch (IOException | TranslateException e) {
            e.printStackTrace();
        }
       // System.out.println(detection);
    }



}

    
    