package es.noegrarciaramirez.spaceinvaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    final short SCENE_HEIGHT = 520;
    final short SCENE_WIDTH = 680;
    
    

    @Override
    public void start(Stage stage) {
        
        Pane root = new Pane();
        var scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        
        Image image1 = new Image(getClass().getResourceAsStream("images/dfsgfbossFinal.png"));
//        ImageView imageView1 = new ImageView(image1);
        
//        root.getChildren().add(imageView1);
    }

    public static void main(String[] args) {
        launch();
    }

}