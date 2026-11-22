package teoria;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Ejemplo de la guía: TRANSFORMACIONES con Graphics2D
 * (traslación, rotación, escalado y sesgado) y antialiasing.
 */
public class AppGraficos2D extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g; // Graphics2D amplía Graphics con más funciones

        // Antialiasing: suaviza los bordes de las figuras
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rectángulo original (gris) como referencia
        g2d.setColor(Color.GRAY);
        int rectWidth = 100;
        int rectHeight = 50;
        g2d.fillRect(50, 50, rectWidth, rectHeight);

        // Se guarda la transformación original para poder volver a ella
        AffineTransform originalTransform = g2d.getTransform();

        // Traslación: mueve el origen 200 px a la derecha
        g2d.translate(200, 0);
        g2d.setColor(Color.BLUE);
        g2d.fillRect(50, 50, rectWidth, rectHeight);
        g2d.setTransform(originalTransform); // restaura

        // Rotación: 45° alrededor del punto (100, 75)
        g2d.translate(0, 150);
        g2d.rotate(Math.toRadians(45), 100, 75);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(50, 50, rectWidth, rectHeight);
        g2d.setTransform(originalTransform);

        // Escalado: 1.5 veces en X y 0.5 veces en Y
        g2d.translate(200, 150);
        g2d.scale(1.5, 0.5);
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(50, 50, rectWidth, rectHeight);
        g2d.setTransform(originalTransform);

        // Sesgado (shear): inclina la figura horizontalmente
        g2d.translate(0, 300);
        g2d.shear(0.5, 0);
        g2d.setColor(Color.MAGENTA);
        g2d.fillRect(50, 50, rectWidth, rectHeight);
        g2d.setTransform(originalTransform); // deja todo como al inicio
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo de Transformaciones con Graphics2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new AppGraficos2D());
        frame.setVisible(true);
    }
}
