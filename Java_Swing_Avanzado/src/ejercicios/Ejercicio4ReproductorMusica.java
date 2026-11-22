package ejercicios;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Ejercicio 4 - Reproductor con Reproducir, Pausar y Reanudar.
 * Clip guarda la posición del audio: al pausar se guarda la posición en
 * microsegundos y al reanudar se continúa desde ese mismo punto.
 */
public class Ejercicio4ReproductorMusica extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String RUTA = "sonidos/musica.wav";

    private Clip clip;
    private long posicionPausa = 0;  // dónde se pausó (microsegundos)
    private final JLabel lblEstado = new JLabel("Listo", SwingConstants.CENTER);
    private final JButton btnReproducir = new JButton("Reproducir");
    private final JButton btnPausar = new JButton("Pausar");
    private final JButton btnReanudar = new JButton("Reanudar");

    public Ejercicio4ReproductorMusica() {
        super("Ejercicio 4 - Reproductor de música");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        botones.add(btnReproducir);
        botones.add(btnPausar);
        botones.add(btnReanudar);
        add(new JLabel("Pista: " + new File(RUTA).getName(), SwingConstants.CENTER), BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
        add(lblEstado, BorderLayout.SOUTH);

        btnReproducir.addActionListener(e -> reproducir());
        btnPausar.addActionListener(e -> pausar());
        btnReanudar.addActionListener(e -> reanudar());
        actualizarBotones(false, false);

        setSize(380, 160);
        setLocationRelativeTo(null);
    }

    /** Reproducir: carga la pista (una sola vez) y empieza desde el inicio. */
    private void reproducir() {
        try {
            if (clip == null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(RUTA));
                clip = AudioSystem.getClip();
                clip.open(ais);
            }
            clip.stop();
            clip.setMicrosecondPosition(0); // desde el inicio
            clip.start();
            lblEstado.setText("Reproduciendo desde el inicio");
            actualizarBotones(true, false);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            lblEstado.setText("Error: " + ex.getMessage());
        }
    }

    /** Pausar: guarda la posición actual y detiene el clip. */
    private void pausar() {
        if (clip != null && clip.isRunning()) {
            posicionPausa = clip.getMicrosecondPosition();
            clip.stop();
            lblEstado.setText(String.format("Pausado en %.1f s", posicionPausa / 1_000_000.0));
            actualizarBotones(false, true);
        }
    }

    /** Reanudar: vuelve a la posición guardada y continúa. */
    private void reanudar() {
        if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(posicionPausa);
            clip.start();
            lblEstado.setText("Reanudado");
            actualizarBotones(true, false);
        }
    }

    /** Habilita solo los botones que tienen sentido en cada estado. */
    private void actualizarBotones(boolean sonando, boolean pausado) {
        btnPausar.setEnabled(sonando);
        btnReanudar.setEnabled(pausado);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio4ReproductorMusica().setVisible(true));
    }
}
