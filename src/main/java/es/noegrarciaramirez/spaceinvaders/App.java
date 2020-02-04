package es.noegrarciaramirez.spaceinvaders;

import static java.lang.Math.random;
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
import java.util.Random;

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
    final short velocidadMovNave = 5;
    
    
    //Disparo Nave
    final short posicionDisparoFuera = 600;//Para la X e Y, que no aparezca el disparo en pantalla
    short posicionDisparoDentroX = 248;
    short posicionDisparoDentroY = 430;
    boolean disparo = false;

    
    int groupPrimeraNaveY = -50;
    int groupSegundaNaveY = -120;
    int groupTerceraNaveY = -190;
    //Velocidad naves enemigas
    short velocidadNavesEnemigas = 2;
    //Contador de impactos a cada nave
    short numImpactos1 = 0;
    short numImpactos2 = 0;
    short numImpactos3 = 0;
    
    //Contador de puntucación
    int score = 0;

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
        
        //Boss
        Rectangle hbBossF = new Rectangle(100, 100);
        Color c = new Color(0,0,0,0.0);
        hbBossF.setFill(c);

        Group groupBoss1 = new Group();
        groupBoss1.getChildren().add(hbBossF);
        groupBoss1.getChildren().add(bossF);
        groupBoss1.setLayoutX(SCENE);
        
        //Disparo rojo
        
        Rectangle hitBoxDisparo = new Rectangle (5, 13);//Rectangulo del disparo
        Group groupDisparo = new Group();
        
        groupDisparo.getChildren().add(hitBoxDisparo);
        groupDisparo.getChildren().add(disparoRojo);
        
        groupDisparo.setLayoutX(posicionDisparoFuera);
        groupDisparo.setLayoutY(posicionDisparoFuera);
        
        //Crear número aleatorio para la X de las naves enemigas
        Random random = new Random();
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
        
        Rectangle hB1 = new Rectangle (28+xAlasNave, yAlasNave);//Rectangulo que engloba la nave, para detectar la colison
        hB1.setFill(c);
        
        Group groupPrimeraNave = new Group();
        groupPrimeraNave.getChildren().add(cuerpoNave1);
        groupPrimeraNave.getChildren().add(nave1);
        groupPrimeraNave.getChildren().add(cuerpoNave11);
        groupPrimeraNave.getChildren().add(hB1);
        
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
        
        Rectangle hB2 = new Rectangle (28+xAlasNave, yAlasNave);
        hB2.setFill(c);
        
        Group groupSegundaNave = new Group();
        groupSegundaNave.getChildren().add(cuerpoNave2);
        groupSegundaNave.getChildren().add(nave2);
        groupSegundaNave.getChildren().add(cuerpoNave22);
        groupSegundaNave.getChildren().add(hB2);
        
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
        
        Rectangle hB3 = new Rectangle (28+xAlasNave, yAlasNave);
        hB3.setFill(c);
        
        Group groupTerceraNave = new Group();
        groupTerceraNave.getChildren().add(cuerpoNave3);
        groupTerceraNave.getChildren().add(nave3);
        groupTerceraNave.getChildren().add(cuerpoNave33);
        groupTerceraNave.getChildren().add(hB3);
        
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
        root.getChildren().add(groupBoss1);
        


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
                    
                    case SPACE:
                        disparo = true;
                        groupDisparo.setLayoutX(posicionNaveJugador+28);
                        groupDisparo.setLayoutY(posicionDisparoDentroY);
                        random.nextInt(SCENE-40);//genera num aleatorio para darselo a la X de las naves enemigas
                        System.out.println("Score "+score);
                    break;
                }
            }
        });

        //Bucle de fondo y naves enemigas
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
                @SuppressWarnings("empty-statement")
                public void handle(ActionEvent ae) {
                    
                    //impacto de disparo con nave 1
                    Shape shapeCollision = Shape.intersect(hB1, hitBoxDisparo);
                    boolean colisionVacia = shapeCollision.getBoundsInLocal().isEmpty();
                    if (colisionVacia == false) {
                        disparo = false;
                        groupDisparo.setLayoutX(posicionDisparoFuera);
                        groupDisparo.setLayoutY(posicionDisparoFuera);
                        posicionDisparoDentroY = 430;
                        groupPrimeraNave.setLayoutX(600);
                        numImpactos1+=1;
                        score+=50;
                    }

                    //impacto de disparo con nave 2
                    Shape shapeCollision2 = Shape.intersect(hB2, hitBoxDisparo);
                    boolean colisionVacia2 = shapeCollision2.getBoundsInLocal().isEmpty();
                    if (colisionVacia2 == false) {
                        disparo = false;
                        groupDisparo.setLayoutX(posicionDisparoFuera);
                        groupDisparo.setLayoutY(posicionDisparoFuera);
                        posicionDisparoDentroY = 430;
                        groupSegundaNave.setLayoutX(600);
                        numImpactos2+=1;
                        score+=50;
                    }                    

                    //impacto de disparo con nave 3
                    Shape shapeCollision3 = Shape.intersect(hB3, hitBoxDisparo);
                    boolean colisionVacia3 = shapeCollision3.getBoundsInLocal().isEmpty();
                    if (colisionVacia3 == false) {
                        disparo = false;
                        groupDisparo.setLayoutX(posicionDisparoFuera);
                        groupDisparo.setLayoutY(posicionDisparoFuera);
                        posicionDisparoDentroY = 430;
                        groupTerceraNave.setLayoutX(600);
                        numImpactos3+=1;
                        score+=50;
                    }
                    
                    //Impacto con Boss1
                    Shape shapeCollision4 = Shape.intersect(hbBossF, hitBoxDisparo);
                    boolean colisionVacia4 = shapeCollision4.getBoundsInLocal().isEmpty();
                    if (colisionVacia4 == false) {
                        disparo = false;
                        groupDisparo.setLayoutX(posicionDisparoFuera);
                        groupDisparo.setLayoutY(posicionDisparoFuera);
                        posicionDisparoDentroY = 430;
                        groupPrimeraNave.setLayoutX(600);
                        score+=150;
                    }
                    
                    
                    //Salidad el disparo desde la nave
                    if (disparo == true){
                    posicionDisparoDentroY-=6;
                    groupDisparo.setLayoutY(posicionDisparoDentroY);
                    }
                    //Impedir que la nave se salga de los lados
                    if(posicionNaveJugador<=8){
                        posicionNaveJugador=8;
                    };
                    if(posicionNaveJugador>=SCENE - 65){
                        posicionNaveJugador=SCENE - 65;
                    };
                    
                    //Si el disparo falla que salga en la nave otra vez
                    if(posicionDisparoDentroY <= 0){;
                        disparo = false;
                        groupDisparo.setLayoutX(posicionDisparoFuera);
                        groupDisparo.setLayoutY(posicionDisparoFuera);
                        posicionDisparoDentroY = 430;                        
                    }
                    
                    //Movimiento del fondo
                    posicionFondo ++;
                    posicionFondo2 ++;
                    
                    imageView1.setY(posicionFondo);
                    imageView2.setY(posicionFondo2);
                    
                    //Mover las 3 naves
                    groupPrimeraNaveY +=velocidadNavesEnemigas;
                    groupPrimeraNave.setLayoutY (groupPrimeraNaveY);
                    groupSegundaNaveY +=velocidadNavesEnemigas;
                    groupSegundaNave.setLayoutY (groupSegundaNaveY);
                    groupTerceraNaveY +=velocidadNavesEnemigas;
                    groupTerceraNave.setLayoutY (groupTerceraNaveY);
                    
                    //bucle de imagen
                    if (posicionFondo == SCENE){
                        posicionFondo = 0;
                        posicionFondo2 = -SCENE;
                        imageView1.setY(posicionFondo);
                        imageView2.setY(posicionFondo2);   
                    }
                    //Devolver naves arriba si se pasan o si impactan
                    if (groupPrimeraNaveY >= SCENE){
                        groupPrimeraNaveY = -50;
                        groupPrimeraNave.setLayoutY(-50);
                        groupPrimeraNave.setLayoutX (random.nextInt(SCENE-40));
                    }
                    if (groupSegundaNaveY >= SCENE){
                        groupSegundaNaveY = -50;
                        groupSegundaNave.setLayoutY(-120);
                        groupSegundaNave.setLayoutX (random.nextInt(SCENE-40));
                    }
                    if (groupTerceraNaveY >= SCENE){
                        groupTerceraNaveY = -50;
                        groupTerceraNave.setLayoutY(-190);
                        groupTerceraNave.setLayoutX (random.nextInt(SCENE-40));
                    }
                    
                    //Si impacta 3 veces en una nave, esa nave va más rápido
                    //porque luego salen 3 naves y boss
                    if(numImpactos1>=3){
                        velocidadNavesEnemigas=3;
                    }
                    if(numImpactos2>=3){
                        velocidadNavesEnemigas=3;
                    }
                    if(numImpactos3>=3){
                        velocidadNavesEnemigas=3;
                    }
                    //Sale el boss si impacta con cualquier nave 6 veces
                    if((numImpactos1>=6) || (numImpactos2>=6) || (numImpactos3>=6)){
                        bossF.setX((SCENE/2)-(50));
                    }
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
/*
    private void crearNaves(){
        
    }
*/
    public static void main(String[] args) {
        launch();
    }

}