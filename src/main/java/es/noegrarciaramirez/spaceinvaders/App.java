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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;
import javafx.scene.text.Font;

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
    short velocidadMovNave = 0;
    short direccionNave=1;
    
    short contadorVidasJugador = 3;
    
    short corazon1PosicionX = 5;
    short corazon2PosicionX = 5;
    short corazon3PosicionX = 5;

    //Corazon 1
    Image image9 = new Image(getClass().getResourceAsStream("/images/corazon1.png"));
    ImageView corazon1 = new ImageView(image9);    


    //Corazon 2
    Image image10 = new Image(getClass().getResourceAsStream("/images/corazon2.png"));
    ImageView corazon2 = new ImageView(image10);

    //Corazon 3
    Image image11 = new Image(getClass().getResourceAsStream("/images/corazon3.png"));
    ImageView corazon3 = new ImageView(image11);
        
    //Disparo Nave
    final short posicionDisparoFuera = 600;//Para la X e Y, que no aparezca el disparo en pantalla
    short posicionDisparoDentroX = 248;
    short posicionDisparoDentroY = 430;
    boolean disparo = false;

    
    int groupPrimeraNaveY = -50;
    int groupSegundaNaveY = -120;
    int groupTerceraNaveY = -190;
    
    //Velocidad naves enemigas
    short velocidadNavesEnemigas = 3;
    
    //Contador de impactos a cada nave
    short numImpactos1 = 0;
    short numImpactos2 = 0;
    short numImpactos3 = 0;

    //Boss1
    int movimientoXBoss1 = 0;
    int velociadadBoss1 = 7;
    int direccionBoss1 = 1;
    short contadorImpactosJefe = 0;
    //Disparo del Boss 1
    int yDisparoBoss1 = 100;
    //Contador de puntucación y puntuacón max
    int score = 0;
    int highscore;
    //Etiquetas
    Label labelPuntuacion = new Label();
    Label labelHighscore = new Label();
    Label labelFinPartida = new Label();
    //Pantalla inicial del juego
    Button buttonEmpezar = new Button();
    
    Button buttonReiniciar = new Button();

    VBox vbox = new VBox();
    Timeline timeline;
    //Grupos
    Group groupBoss1 = new Group();
    Group grupoDisparoBoss1 = new Group();
    Group groupDisparo = new Group();
    Group groupPrimeraNave = new Group();
    Group groupSegundaNave = new Group();
    Group groupTerceraNave = new Group();
    Group grupoNaveJ = new Group();

    //Primera y segunda imagen del fondo para bucle
    Image image1 = new Image(getClass().getResourceAsStream("/images/fondo.png"));
    ImageView imageView1 = new ImageView(image1);

    Image image2 = new Image(getClass().getResourceAsStream("/images/fondo2.png"));
    ImageView imageView2 = new ImageView(image2);

    //Imagen Nave del Jugador
    Image image4 = new Image(getClass().getResourceAsStream("/images/naveUser.png"));
    ImageView naveUser = new ImageView(image4);
    
    //Imagen iconoPlay
    Image imagePlay = new Image(getClass().getResourceAsStream("/images/play.png"));
    ImageView iconoPlay = new ImageView(imagePlay);
    
    //Imagen iconoReplay
    Image imageReplay = new Image(getClass().getResourceAsStream("/images/replay.png"));
    ImageView iconoReplay = new ImageView(imageReplay);
    
    //Crear número aleatorio para la X de las naves enemigas
    Random random = new Random();
    @Override
    public void start(Stage stage) {
        
        Pane root = new Pane();
        var scene = new Scene(root, SCENE, SCENE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Space Invaders 0.9");
        
        //Etiqueta con puntuación
        labelPuntuacion.relocate(10, 10);
        labelPuntuacion.setTextFill(WHITE);
        
        //Etiqueta puntuación Max
        labelHighscore.relocate(100, 10);
        labelHighscore.setTextFill(RED);
        
        //Etiqueta fin de partida
        labelFinPartida.setVisible(false);
        labelFinPartida.relocate((SCENE/2)-15, (SCENE/2)-80);
        labelFinPartida.setTextFill(WHITE);
        labelFinPartida.setText("GAME OVER");
        labelFinPartida.setMinWidth(400);
        labelFinPartida.setMaxWidth(400);
        labelFinPartida.setFont(new Font("Aclonica", 30));
        
        //Elementos el vobx
        vbox.getChildren().add(buttonEmpezar);
        vbox.getChildren().add(buttonReiniciar);
        buttonReiniciar.setStyle("-fx-background-color: transparent;");//Poner fondo de botón en transparente
        buttonReiniciar.setVisible(false);
        buttonReiniciar.setTranslateX((SCENE/2)-75);
        buttonReiniciar.setTranslateY((SCENE/2));
        vbox.setVisible(true);
        buttonEmpezar.setStyle("-fx-background-color: transparent;");
        buttonEmpezar.setTranslateX((SCENE/2)-47);
        buttonEmpezar.setTranslateY((SCENE/2)-80);
        buttonEmpezar.setGraphic (new ImageView(imagePlay));
        iconoPlay.setFitHeight(30);
        iconoPlay.setFitWidth(30);
        buttonReiniciar.setGraphic (new ImageView(imageReplay));
        buttonReiniciar.setPrefWidth(30);
        buttonReiniciar.setPrefHeight(30);
        iconoReplay.setFitHeight(30);
        iconoReplay.setFitWidth(30);
        
        //Imagen Boss1
        Image image3 = new Image(getClass().getResourceAsStream("/images/bossFinal.png"));
        ImageView bossF = new ImageView(image3);

        //Disparo de la nave Jugador
        Image image5 = new Image(getClass().getResourceAsStream("/images/disparoDeNave.png"));
        ImageView disparoRojo = new ImageView(image5);
        
        //Disparo de boss1
        Image image6 = new Image(getClass().getResourceAsStream("/images/disparoBoss1.png"));
        ImageView disparoBoss1 = new ImageView(image6);  
        
        //Imagen Boss2
//        Image image7 = new Image(getClass().getResourceAsStream("/images/bossFinal2.png"));
//        ImageView boss2 = new ImageView(image7);
        
        
        //Disparo de boss2
        //Image image8 = new Image(getClass().getResourceAsStream("/images/disparoBoss2.png"));
        //ImageView disparoBoss2 = new ImageView(image8);

        //Vidas Jugador
        corazon3.setX(corazon1PosicionX);
        corazon3.setY(390);
        
        corazon2.setX(corazon2PosicionX);
        corazon2.setY(415);
        
        corazon1.setX(corazon3PosicionX);
        corazon1.setY(440);
        
        //Boss1
        Rectangle hbBossF = new Rectangle(100, 100);
        Color c = new Color(0,0,0,0.0);
        hbBossF.setFill(c);

        groupBoss1.getChildren().add(hbBossF);//rectangulo como hitbox
        groupBoss1.getChildren().add(bossF);//imagen
        groupBoss1.setLayoutX(SCENE);
        
        //Disparo del Boss 1
        disparoBoss1.setFitWidth(20);
        disparoBoss1.setFitHeight(33);
        Rectangle hbDisparoBoss1 = new Rectangle(20, 33);
        hbDisparoBoss1.setFill(c);
        
        grupoDisparoBoss1.getChildren().add(disparoBoss1);
        grupoDisparoBoss1.getChildren().add(hbDisparoBoss1);
        grupoDisparoBoss1.setLayoutX(SCENE);
        
        //Disparo del Boss 2
//        Circle hBdisparo1Boss1 = new Circle(9/*radio*/);
//        hBdisparo1Boss1.setCenterX(9);
//        hBdisparo1Boss1.setCenterY(9);
//        hBdisparo1Boss1.setFill(c);
//        Group grupoDisp1Boss1 = new Group();
//        grupoDisp1Boss1.getChildren().add(hBdisparo1Boss1);
//        grupoDisp1Boss1.getChildren().add(disparoBoss1);

        
        //Disparo Jugador
        
        Rectangle hitBoxDisparo = new Rectangle (5, 13);//Rectangulo del disparo
        
        groupDisparo.getChildren().add(hitBoxDisparo);
        groupDisparo.getChildren().add(disparoRojo);
        
        groupDisparo.setLayoutX(posicionDisparoFuera);
        groupDisparo.setLayoutY(posicionDisparoFuera);

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
        
        groupPrimeraNave.getChildren().add(cuerpoNave1);
        groupPrimeraNave.getChildren().add(nave1);
        groupPrimeraNave.getChildren().add(cuerpoNave11);
        groupPrimeraNave.getChildren().add(hB1);
        
        //Posicion del grupo completo
        int randomPosXNaveEnemiga1 = random.nextInt(SCENE-40);
        groupPrimeraNave.setLayoutX(randomPosXNaveEnemiga1);
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
        
        groupSegundaNave.getChildren().add(cuerpoNave2);
        groupSegundaNave.getChildren().add(nave2);
        groupSegundaNave.getChildren().add(cuerpoNave22);
        groupSegundaNave.getChildren().add(hB2);
        
        //Posicion del grupo completo
        int randomPosXNaveEnemiga2 = random.nextInt(SCENE-40);
        groupSegundaNave.setLayoutX(randomPosXNaveEnemiga2);
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
        
        groupTerceraNave.getChildren().add(cuerpoNave3);
        groupTerceraNave.getChildren().add(nave3);
        groupTerceraNave.getChildren().add(cuerpoNave33);
        groupTerceraNave.getChildren().add(hB3);
        
        //Posicion del grupo completo
        int randomPosXNaveEnemiga3 = random.nextInt(SCENE-40);
        groupTerceraNave.setLayoutX(randomPosXNaveEnemiga3);
        groupTerceraNave.setLayoutY(360);
        
        //Nave del jugador
        naveUser.setFitHeight(60);
        naveUser.setFitWidth(60);
        
        Rectangle hbNaveJ = new Rectangle(60, 60);
        hbNaveJ.setFill(c);
        
        grupoNaveJ.getChildren().add(hbNaveJ);
        grupoNaveJ.getChildren().add(naveUser);
        
        posicionNaveJugador = (int) ((SCENE/2)-(naveUser.getFitWidth()/2));
        
        grupoNaveJ.setLayoutX(posicionNaveJugador);
        grupoNaveJ.setLayoutY(SCENE-65);



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
        root.getChildren().add(groupPrimeraNave);//3 naves enemigas
        root.getChildren().add(groupSegundaNave);
        root.getChildren().add(groupTerceraNave);
        root.getChildren().add(groupBoss1);
        root.getChildren().add(grupoNaveJ);
        root.getChildren().add(groupDisparo);
        root.getChildren().add(corazon1);
        root.getChildren().add(corazon2);
        root.getChildren().add(corazon3);
        root.getChildren().add(grupoDisparoBoss1);
        root.getChildren().add(labelPuntuacion);
        root.getChildren().add(labelHighscore);
        root.getChildren().add(labelFinPartida);
        root.getChildren().add(vbox);

        
//        for (int i = 0; i < 3; i++) {
//            
//        }
        
        //Movimiento de la naveJugador
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle (final KeyEvent keyEvent){
                switch(keyEvent.getCode()){
                    case LEFT:
                        direccionNave = -1;
                        velocidadMovNave = 3;
                    break;
                    
                    case RIGHT:
                        direccionNave = 1;
                        velocidadMovNave = 3;
                    break;
                    
                    case SPACE:
                        disparo = true;
                        groupDisparo.setLayoutX(posicionNaveJugador+28);
                        groupDisparo.setLayoutY(posicionDisparoDentroY);
                        random.nextInt(SCENE-40);//genera num aleatorio para darselo a la X de las naves enemigas
                    break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                direccionNave = 0;
            }
        });

        //Bucle
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
                @SuppressWarnings("empty-statement")
                public void handle(ActionEvent ae) {
                 
                    //labelHighscore
                    labelPuntuacion.setText("Score: " + score);//Obtener puntuacion
                    //Comprobar si Highscore es mayor a la puntuación del jugador, y actualizarla en partida si es así
                    if(score>=highscore){
                        highscore=score;
                        labelHighscore.setText("HighScore: " + highscore);
                    }
                    
                    grupoNaveJ.setLayoutX(posicionNaveJugador);
                    posicionNaveJugador += velocidadMovNave * direccionNave;
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
                        score+=150;
                        
                        contadorImpactosJefe+=1;
                    }
                    //colision de NaveJugador con Nave1
                    Shape shapeCollision5 = Shape.intersect(hbNaveJ, hB1);
                    boolean colisionVacia5 = shapeCollision5.getBoundsInLocal().isEmpty();
                    if (colisionVacia5 == false) {
                        disparo = false;
                        groupDisparo.setLayoutX(posicionDisparoFuera);
                        groupDisparo.setLayoutY(posicionDisparoFuera);
                        posicionDisparoDentroY = 430;
                        groupPrimeraNave.setLayoutX(600);
                        score-=1500;
                        //Quitar vidas al jugador según el corazón que le falte
                        quitarVidasJugador();
                        contadorVidasJugador-=1;
                    }
                    //colision de NaveJugador con Nave2
                    Shape shapeCollision6 = Shape.intersect(hbNaveJ, hB2);
                    boolean colisionVacia6 = shapeCollision6.getBoundsInLocal().isEmpty();
                    if (colisionVacia6 == false) {
                        disparo = false;
                        groupDisparo.setLayoutX(posicionDisparoFuera);
                        groupDisparo.setLayoutY(posicionDisparoFuera);
                        posicionDisparoDentroY = 430;
                        groupSegundaNave.setLayoutX(600);
                        numImpactos3+=1;
                        score-=1500;
                        
                        quitarVidasJugador();
                        contadorVidasJugador-=1;
                    }
                    
                    //colision de NaveJugador con Nave3
                    Shape shapeCollision7 = Shape.intersect(hbNaveJ, hB3);
                    boolean colisionVacia7 = shapeCollision7.getBoundsInLocal().isEmpty();
                    if (colisionVacia7 == false) {
                        disparo = false;
                        groupDisparo.setLayoutX(posicionDisparoFuera);
                        groupDisparo.setLayoutY(posicionDisparoFuera);
                        posicionDisparoDentroY = 430;
                        groupTerceraNave.setLayoutX(600);
                        numImpactos3+=1;
                        score-=1500;
                        
                        quitarVidasJugador();
                        contadorVidasJugador-=1;
                    }
                    
                    //Salida del disparo desde la nave
                    if (disparo == true){
                    posicionDisparoDentroY-=9;
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
                    
                    //bucle de fondo
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
                    
                    //Parar partida si Jugador llega a 0 vidas
                    if(contadorVidasJugador==0){
                        vbox.setVisible(true);
                        buttonEmpezar.setVisible(false);
                        buttonReiniciar.setVisible(true);
                        labelFinPartida.relocate((SCENE/2)-85, (SCENE/2)-130);
                        labelHighscore.relocate((SCENE/2)-36, (SCENE/2)-70);
                        labelPuntuacion.relocate((SCENE/2)-36, (SCENE/2)-50);
                        labelHighscore.setVisible(true);
                        labelPuntuacion.setVisible(true);
                        labelFinPartida.setVisible(true);
                        timeline.stop();
                        buttonReiniciar.setOnAction((ActionEvent e)-> {//Qutia la vbox al presionar el botón
                            reiniciarPartida();
                        });
                    }
                    
                    //Si impacta 3 veces en una nave, aumenta su velocidad de 2 a 4
                    if(numImpactos1>=3){
                        velocidadNavesEnemigas=4;
                    }
                    if(numImpactos2>=3){
                        velocidadNavesEnemigas=4;
                    }
                    if(numImpactos3>=3){
                        velocidadNavesEnemigas=4;
                    }
                    //Sale el boss si impacta con cualquier nave 5 veces
                    if((numImpactos1>=5) || (numImpactos2>=5) || (numImpactos3>=5)){
                        movimientoXBoss1 += velociadadBoss1 * direccionBoss1;
                        groupBoss1.setLayoutX(movimientoXBoss1);
                        yDisparoBoss1+=6;
                        
                        grupoDisparoBoss1.setLayoutY(yDisparoBoss1);
                        
                        if(movimientoXBoss1<=0 || movimientoXBoss1>=(SCENE-90)){
                            direccionBoss1*= -1;
                        }
                        if (contadorImpactosJefe >= 3){
                            grupoDisparoBoss1.setLayoutX(SCENE);
                            grupoDisparoBoss1.setVisible(false);
                            groupBoss1.setLayoutX(SCENE);
                            groupBoss1.setVisible(false);
                    }
                        if(yDisparoBoss1 >= 900){
                            yDisparoBoss1 = 90;
                            grupoDisparoBoss1.setLayoutY(yDisparoBoss1);
                            grupoDisparoBoss1.setLayoutX(movimientoXBoss1+45);
                        }
                        
                        //Colisión del disparo del Boss 1 con el Jugador
                        Shape shapeCollision8 = Shape.intersect(hbNaveJ, hbDisparoBoss1);
                        boolean colisionVacia8 = shapeCollision8.getBoundsInLocal().isEmpty();
                        if (colisionVacia8 == false) {
                            yDisparoBoss1 = 90;
                            grupoDisparoBoss1.setLayoutY(yDisparoBoss1);
                            grupoDisparoBoss1.setLayoutX(movimientoXBoss1+45);
                        
                            score-=500;
                            quitarVidasJugador();
                            contadorVidasJugador-=1;
                        }
                    }
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.stop();

        
        buttonEmpezar.setOnAction((ActionEvent e)-> {//Qutia la vbox al presionar el botón
            vbox.setVisible(false);
            timeline.play();
        });
    }


    //Qutiar el corazón que corresponda
    private void quitarVidasJugador(){
        if(corazon3PosicionX == 5){
            corazon3PosicionX=SCENE;
            corazon3.setX(corazon3PosicionX);
            } else { if ((corazon2PosicionX == 5)){
                corazon2PosicionX=SCENE;
                corazon2.setX(corazon2PosicionX);
                } else { if ((corazon1PosicionX == 5)){
                    corazon1PosicionX=SCENE;
                    corazon1.setX(corazon2PosicionX);
                }
            }
        }
    }

    
    private void reiniciarPartida() {
        vbox.setVisible(false);
        buttonReiniciar.setVisible(false);
        
        contadorVidasJugador = 3;
        corazon1PosicionX = 5;
        corazon2PosicionX = 5;
        corazon3PosicionX = 5;
        corazon3.setX(corazon1PosicionX);
        corazon3.setY(390);
        corazon2.setX(corazon2PosicionX);
        corazon2.setY(415);
        corazon1.setX(corazon3PosicionX);
        corazon1.setY(440);
        velocidadNavesEnemigas = 3;
        numImpactos1 = 0;
        numImpactos2 = 0;
        numImpactos3 = 0;
        movimientoXBoss1 = 0;
        velociadadBoss1 = 7;
        direccionBoss1 = 1;
        contadorImpactosJefe = 0;
        yDisparoBoss1 = 100;
        score = 0;
        groupPrimeraNaveY = -50;
        groupSegundaNaveY = -120;
        groupTerceraNaveY = -190;
        labelPuntuacion.relocate(10, 10);
        labelHighscore.relocate(100, 10);
        labelFinPartida.setVisible(false);
        groupBoss1.setLayoutX(SCENE);
        grupoDisparoBoss1.setLayoutX(SCENE);
        groupDisparo.setLayoutX(posicionDisparoFuera);
        groupDisparo.setLayoutY(posicionDisparoFuera);
        groupPrimeraNave.setLayoutX(250);
        groupPrimeraNave.setLayoutY(posicionNave1);
        groupSegundaNave.setLayoutX(100);
        groupPrimeraNave.setLayoutY(250);
        groupTerceraNave.setLayoutX(400);
        groupTerceraNave.setLayoutY(400);
        posicionNaveJugador = (int) ((SCENE/2)-(naveUser.getFitWidth()/2));
        grupoNaveJ.setLayoutX(posicionNaveJugador);
        grupoNaveJ.setLayoutY(SCENE-65);
        imageView1.setX(0);
        imageView1.setY(0);
        imageView2.setX(0);
        imageView2.setY(-SCENE);
        
        timeline.play();
    }
    
    private Random randomNum() {
        System.out.println("a");
        return random;
    }



    public static void main(String[] args) {
        launch();
    }

}