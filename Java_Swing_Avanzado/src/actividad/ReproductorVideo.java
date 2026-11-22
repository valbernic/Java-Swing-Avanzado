package actividad;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Actividad - Multimedia: reproductor de VIDEO con controles Swing.
 * Usa la librería externa JavaFX (JFXPanel + MediaPlayer + MediaView) dentro de
 * un JFrame, con botones Swing para Reproducir, Pausar y Detener.
 * Requiere JavaFX configurado y el archivo video/video.mp4.
 */
public class ReproductorVideo extends JFrame {

    private static final long serialVersionUID = 1L;

    private MediaPlayer mediaPlayer; // se crea en el hilo de JavaFX

    public ReproductorVideo() {
        super("Multimedia - Reproductor de video (JavaFX en Swing)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 560);
        setLayout(new BorderLayout());

        JFXPanel jfxPanel = new JFXPanel(); // contenedor Swing para contenido JavaFX
        add(jfxPanel, BorderLayout.CENTER);

        JButton btnPlay = new JButton("Reproducir");
        JButton btnPause = new JButton("Pausar");
        JButton btnStop = new JButton("Detener");
        JPanel controles = new JPanel();
        controles.add(btnPlay);
        controles.add(btnPause);
        controles.add(btnStop);
        add(controles, BorderLayout.SOUTH);

        // Cada orden al MediaPlayer se envía al hilo de JavaFX con Platform.runLater
        btnPlay.addActionListener(e -> Platform.runLater(() -> { if (mediaPlayer != null) mediaPlayer.play(); }));
        btnPause.addActionListener(e -> Platform.runLater(() -> { if (mediaPlayer != null) mediaPlayer.pause(); }));
        btnStop.addActionListener(e -> Platform.runLater(() -> { if (mediaPlayer != null) mediaPlayer.stop(); }));

        Platform.runLater(() -> {
            Media media = new Media(new File("video/videoplayback.mp4").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            MediaView vista = new MediaView(mediaPlayer);
            vista.setFitWidth(780);         // ajusta el video al ancho de la ventana
            vista.setPreserveRatio(true);   // mantiene la proporción
            jfxPanel.setScene(new Scene(new Group(vista)));
        });

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReproductorVideo().setVisible(true));
    }
}
