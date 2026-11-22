package teoria;

import javax.swing.*;
import java.awt.*;

/**
 * Ejemplo de la guía: GRÁFICOS BÁSICOS con la clase Graphics.
 * Se extiende JPanel y se sobrescribe paintComponent para dibujar figuras.
 */
public class AppGraficos extends JPanel {

    /** Swing llama a este método cada vez que el panel necesita dibujarse. */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // limpia el fondo antes de dibujar

        g.setColor(Color.BLUE);              // color con el que se dibujará
        g.drawLine(10, 10, 100, 100);        // línea de (10,10) a (100,100)

        g.setColor(Color.RED);
        g.drawRect(50, 50, 100, 50);         // rectángulo: x, y, ancho, alto (solo borde)

        g.setColor(Color.GREEN);
        g.drawOval(150, 50, 100, 50);        // óvalo dentro de un rectángulo imaginario

        g.setColor(Color.ORANGE);
        g.fillOval(200, 150, 50, 50);        // círculo relleno (fill = relleno)

        g.setColor(Color.BLACK);
        g.drawString("Ejemplo de Gráficos en Swing", 10, 200); // texto en (10,200)
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo de Gráficos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        AppGraficos panel = new AppGraficos(); // el panel actúa como "lienzo"
        frame.add(panel);

        frame.setVisible(true);
    }
}
