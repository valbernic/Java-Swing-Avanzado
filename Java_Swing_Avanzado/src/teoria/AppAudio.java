package teoria;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Ejemplo de la guía: REPRODUCCIÓN DE AUDIO WAV con javax.sound.sampled.
 * El archivo sample-3s.wav debe estar en la carpeta raíz del proyecto.
 */
public class AppAudio {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Reproducción de Audio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JButton playButton = new JButton("Reproducir Audio");
        playButton.addActionListener(e -> playAudio("sample-3s.wav")); // expresión lambda como listener

        frame.add(playButton);
        frame.setVisible(true);
    }

    /** Abre el archivo WAV en un Clip y lo reproduce. */
    public static void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); // flujo de audio
            Clip clip = AudioSystem.getClip(); // Clip: audio cargado en memoria
            clip.open(audioStream);            // carga el sonido
            clip.start();                      // reproduce (en otro hilo, no bloquea la ventana)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // formato no soportado, archivo inexistente o sin dispositivo de audio
        }
    }
}
