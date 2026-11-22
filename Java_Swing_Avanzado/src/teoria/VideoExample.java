package teoria;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.io.File;

/**
 * Ejemplo de la guía: VIDEO MP4 en Swing usando JavaFX (librería externa).
 * Requiere JavaFX configurado en el proyecto (módulos javafx.swing y javafx.media).
 */
public class VideoExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Reproducción de Video");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JFXPanel jfxPanel = new JFXPanel(); // panel Swing que puede contener contenido JavaFX
        frame.add(jfxPanel);
        frame.setVisible(true);

        // El código JavaFX debe ejecutarse en el hilo de JavaFX
        Platform.runLater(() -> {
            String videoPath = new File("video/videoplayback.mp4").toURI().toString(); // ruta como URI
            Media media = new Media(videoPath);                 // el archivo de video
            MediaPlayer mediaPlayer = new MediaPlayer(media);   // controla la reproducción
            MediaView mediaView = new MediaView(mediaPlayer);   // componente que muestra el video

            jfxPanel.setScene(new Scene(new javafx.scene.Group(mediaView)));
            mediaPlayer.play();
        });
    }
}
