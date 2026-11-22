package actividad;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Actividad - Multimedia: imagen + audio en una ventana Swing.
 * Muestra una portada (ImageIcon) y reproduce/detiene una pista WAV con Clip.
 * Un javax.swing.Timer actualiza la barra de progreso mientras suena.
 */
public class ReproductorAudioImagen extends JFrame {

    private static final long serialVersionUID = 1L;

    private Clip clip;                                   // audio cargado en memoria
    private final JProgressBar barra = new JProgressBar(0, 100);
    private final JLabel lblEstado = new JLabel("Detenido", SwingConstants.CENTER);
    private final Timer timer;                           // refresca la barra cada 200 ms

    public ReproductorAudioImagen() {
        super("Multimedia - Imagen y audio");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // Imagen: se carga desde archivo con ImageIcon y se muestra en un JLabel
        JLabel lblImagen = new JLabel(new ImageIcon("imagenes/portada.png"));
        add(lblImagen, BorderLayout.NORTH);

        JButton btnPlay = new JButton("Reproducir");
        JButton btnStop = new JButton("Detener");
        JPanel botones = new JPanel();
        botones.add(btnPlay);
        botones.add(btnStop);

        JPanel sur = new JPanel(new BorderLayout(5, 5));
        barra.setStringPainted(true); // muestra el porcentaje
        sur.add(barra, BorderLayout.NORTH);
        sur.add(lblEstado, BorderLayout.CENTER);
        sur.add(botones, BorderLayout.SOUTH);
        add(sur, BorderLayout.SOUTH);

        timer = new Timer(200, e -> actualizarProgreso());

        btnPlay.addActionListener(e -> reproducir("sonidos/musica.wav"));
        btnStop.addActionListener(e -> detener());

        pack();
        setLocationRelativeTo(null);
    }

    /** Carga el WAV (solo la primera vez) y lo reproduce desde el inicio. */
    private void reproducir(String ruta) {
        try {
            if (clip == null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(ruta));
                clip = AudioSystem.getClip();
                clip.open(ais);
            }
            clip.setFramePosition(0); // vuelve al inicio
            clip.start();
            timer.start();
            lblEstado.setText("Reproduciendo: " + new File(ruta).getName());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo reproducir: " + ex.getMessage());
        }
    }

    private void detener() {
        if (clip != null) clip.stop();
        timer.stop();
        lblEstado.setText("Detenido");
    }

    /** Calcula el % reproducido: posición actual / duración total. */
    private void actualizarProgreso() {
        if (clip == null) return;
        int pct = (int) (100.0 * clip.getMicrosecondPosition() / clip.getMicrosecondLength());
        barra.setValue(pct);
        if (!clip.isRunning() && pct >= 99) detener(); // terminó la pista
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReproductorAudioImagen().setVisible(true));
    }
}
