package es.noegrarciaramirez.spaceinvaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {
    final short SCENE = 500;
    
    short posicionFondo = 0;
    short posicionFondo2 = -SCENE;

    short xAlasNave = 8;
    short yAlasNave = 40;
    
    int posicionNave1 = -50;//centro
    int posicionNave2 = -120;//izquierda
    int posicionNave3 = -190;//derecha
    //Nave Jugador
    int posicionNaveJugador;//Posición nave jugador en la X
    short velocidadMovNave = 5;
    
    //Disparo Nave
    short posicionDisparoFuera = 600;//Para la X e Y, que no aparezca el disparo en pantalla
    short posicionDisparoX = posicionDisparoFuera;//248
    short posicionDisparoY = posicionDisparoFuera;//430

    
    int groupPrimeraNaveY = -50;
    int groupSegundaNaveY = -120;
    int groupTerceraNaveY = -190;

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();
        var scene = new Scene(root, SCENE, SCENE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        //Primera y segunda imagen del fondo para bucle
        Image image1 = new Image(getClass().getResourceAsStream("/images/fondo.png"));
        ImageView imageView1 = new ImageView(image1);
        
        Image image2 = new Image(getClass().getResourceAsStream("/images/fondo2.png"));
        ImageView imageView2 = new ImageView(image2);
        
        //Imagen Boss Final
        Image image3 = new Image(getClass().getResourceAsStream("/images/bossFinal.png"));
        ImageView bossF = new ImageView(image3);

         //Imagen Mi nave
        Image image4 = new Image(getClass().getResourceAsStream("/images/naveUser.png"));
        ImageView naveUser = new ImageView(image4);
        
        //Disparo de la nave verde 1
        //Disparo de la nave rojo 2
        Image image5 = new Image(getClass().getResourceAsStream("/images/disparoDeNave.png"));
        ImageView disparoRojo = new ImageView(image5);
        
        
        
        
        //Disparo rojo
        
        Rectangle hitBoxDisparo = new Rectangle (5, 13);//Rectangulo del disparo
        Group groupDisparo = new Group();
        
        groupDisparo.getChildren().add(hitBoxDisparo);
        groupDisparo.getChildren().add(disparoRojo);
        
        groupDisparo.setLayoutX(posicionDisparoX);
        groupDisparo.setLayoutY(posicionDisparoY);
        


        //Cuerpo Nave 1 centro Verde
        Rectangle cuerpoNave1 = new Rectangle(xAlasNave, yAlasNave);//ala izquierda
        cuerpoNave1.setFill(Color.GREEN);
        cuerpoNave1.setX (0);
        cuerpoNave1.setY (0);
        
        Circle nave1 = new Circle(10, Color.GREEN);//centro de nave
        nave1.setCenterX(18);
        nave1.setCenterY(20);

        Rectangle cuerpoNave11 = new Rectangle(xAlasNave, yAlasNave);//ala derecha
        cuerpoNave11.setFill(Color.GREEN);
        cuerpoNave11.setX (28);
        cuerpoNave11.setY (0);
        
        Group groupPrimeraNave = new Group();
        groupPrimeraNave.getChildren().add(cuerpoNave1);
        groupPrimeraNave.getChildren().add(nave1);
        groupPrimeraNave.getChildren().add(cuerpoNave11);
        
        //Posicion del grupo completo
        groupPrimeraNave.setLayoutX(250);
        groupPrimeraNave.setLayoutY(posicionNave1);

        //Cuerpo Nave 2 izquierda
        Rectangle cuerpoNave2 = new Rectangle(xAlasNave, yAlasNave);//ala izquierda
        cuerpoNave2.setFill(Color.RED);
        cuerpoNave2.setX (0);
        cuerpoNave2.setY (0);
        
        Circle nave2 = new Circle(10, Color.RED);//centro de nave
        nave2.setCenterX(18);
        nave2.setCenterY(20);

        Rectangle cuerpoNave22 = new Rectangle(xAlasNave, yAlasNave);//ala derecha
        cuerpoNave22.setFill(Color.RED);
        cuerpoNave22.setX (28);
        cuerpoNave22.setY (0);
        
        Group groupSegundaNave = new Group();
        groupSegundaNave.getChildren().add(cuerpoNave2);
        groupSegundaNave.getChildren().add(nave2);
        groupSegundaNave.getChildren().add(cuerpoNave22);
        
        //Posicion del grupo completo
        groupSegundaNave.setLayoutX(100);
        groupPrimeraNave.setLayoutY(250);
        
        //Cuerpo Nave 3 derecha
        Rectangle cuerpoNave3 = new Rectangle(xAlasNave, yAlasNave);//ala izquierda
        cuerpoNave3.setFill(Color.BLUE);
        cuerpoNave3.setX (0);
        cuerpoNave3.setY (0);
        
        Circle nave3 = new Circle(10, Color.BLUE);//centro de nave
        nave3.setCenterX(18);
        nave3.setCenterY(20);
        
        Rectangle cuerpoNave33 = new Rectangle(xAlasNave, yAlasNave);//ala derecha
        cuerpoNave33.setFill(Color.BLUE);
        cuerpoNave33.setX (28);
        cuerpoNave33.setY (0);
        
        Group groupTerceraNave = new Group();
        groupTerceraNave.getChildren().add(cuerpoNave3);
        groupTerceraNave.getChildren().add(nave3);
        groupTerceraNave.getChildren().add(cuerpoNave33);
        
        //Posicion del grupo completo
        groupTerceraNave.setLayoutX(400);
        groupTerceraNave.setLayoutY(400);
        
        //Nave del jugador
        naveUser.setFitHeight(60);
        naveUser.setFitWidth(60);
        posicionNaveJugador = (int) ((SCENE/2)-(naveUser.getFitWidth()/2));
        naveUser.setX(posicionNaveJugador);
        naveUser.setY(SCENE-65);
        

        //Tamaño autoajustado al de la ventana
        imageView1.setFitHeight(SCENE);
        imageView1.setFitWidth(SCENE);
        imageView2.setFitHeight(SCENE);
        imageView2.setFitWidth(SCENE);
        
        imageView1.setX(0);
        imageView1.setY(0);
        imageView2.setX(0);
        imageView2.setY(-SCENE);
        
        
        //Poner las cosas en pantalla
        root.getChildren().add(imageView1);
        root.getChildren().add(imageView2);
        root.getChildren().add(naveUser);
        root.getChildren().add(bossF);
        root.getChildren().add(groupPrimeraNave);
        root.getChildren().add(groupSegundaNave);
        root.getChildren().add(groupTerceraNave);
        root.getChildren().add(groupDisparo);


        //Movimiento de la naveJugador
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle (final KeyEvent keyEvent){
                switch(keyEvent.getCode()){
                    case LEFT:
                        posicionNaveJugador-=velocidadMovNave;
                        naveUser.setX(posicionNaveJugador);
                        break;
                    case RIGHT:
                        posicionNaveJugador+=velocidadMovNave;
                        naveUser.setX(posicionNaveJugador);
                        break;     
                }
            }
        });

        //Bucle de fondo y naves enemigas
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    
                    
                    posicionFondo ++;
                    posicionFondo2 ++;

                    imageView1.setY(posicionFondo);
                    imageView2.setY(posicionFondo2);
                    
                    //Mover las 3 naves
                    groupPrimeraNaveY ++;
                    groupPrimeraNave.setLayoutY (groupPrimeraNaveY);
                    groupSegundaNaveY ++;
                    groupSegundaNave.setLayoutY (groupSegundaNaveY);
                    groupTerceraNaveY ++;
                    groupTerceraNave.setLayoutY (groupTerceraNaveY);
                    
                    //bucle de imagen
                    if (posicionFondo == SCENE){
                        posicionFondo = 0;
                        posicionFondo2 = -SCENE;
                        imageView1.setY(posicionFondo);
                        imageView2.setY(posicionFondo2);   
                    }
                    //Devolver naves arriba si se pasan o si impactan
                    if (groupPrimeraNaveY == SCENE){
                        groupPrimeraNaveY = -50;
                        groupPrimeraNave.setLayoutY(-50);
                    }
                    if (groupSegundaNaveY == SCENE){
                        groupSegundaNaveY = -50;
                        groupSegundaNave.setLayoutY(-120);
                    }
                    if (groupTerceraNaveY == SCENE){
                        groupTerceraNaveY = -50;
                        groupTerceraNave.setLayoutY(-190);
                    }
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        Timeline timeline2 = new Timeline(
            new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {
                    
                    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        public void handle (final KeyEvent keyEvent){
                            switch(keyEvent.getCode()){
                                case SPACE:
                                    //groupDisparo.setLayoutX();
                                break;     
                            }
                        }
                    });
                }
            })
        );
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
    }

    public static void main(String[] args) {
        launch();
    }

}