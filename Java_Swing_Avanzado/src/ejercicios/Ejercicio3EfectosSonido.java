package ejercicios;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Ejercicio 3 - Reproductor de efectos de sonido.
 * Cada botón reproduce un archivo WAV distinto con la clase Clip.
 * Los archivos están en la carpeta "sonidos" del proyecto.
 */
public class Ejercicio3EfectosSonido extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JLabel lblEstado = new JLabel("Elige un efecto de sonido", SwingConstants.CENTER);

    public Ejercicio3EfectosSonido() {
        super("Ejercicio 3 - Efectos de sonido");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel botones = new JPanel(new GridLayout(1, 3, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        // nombre del botón y archivo asociado
        String[][] efectos = {{"Aplausos", "sonidos/aplausos.wav"},
                              {"Campana", "sonidos/campana.wav"},
                              {"Explosión", "sonidos/explosion.wav"}};
        for (String[] ef : efectos) {
            JButton b = new JButton(ef[0]);
            b.setFont(b.getFont().deriveFont(Font.BOLD, 15f));
            b.setPreferredSize(new Dimension(130, 60));
            b.addActionListener(e -> reproducir(ef[0], ef[1])); // cada botón conoce su archivo
            botones.add(b);
        }

        add(botones, BorderLayout.CENTER);
        add(lblEstado, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    /** Crea un Clip nuevo por cada clic, así se pueden superponer sonidos. */
    private void reproducir(String nombre, String ruta) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(ruta));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            // Cuando el sonido termina (evento STOP) se libera el recurso con close()
            clip.addLineListener(ev -> { if (ev.getType() == LineEvent.Type.STOP) clip.close(); });
            clip.start();
            lblEstado.setText("Reproduciendo: " + nombre);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            lblEstado.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio3EfectosSonido().setVisible(true));
    }
}
