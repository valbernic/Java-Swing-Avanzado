package actividad;

import javax.swing.*;
import java.awt.*;

/**
 * Actividad - Gráfico SIMPLE con la clase Graphics.
 * Dibuja un paisaje (casa, sol, árbol y nubes) usando solo métodos de Graphics:
 * fillRect, drawRect, fillPolygon, fillOval, drawLine, drawArc y drawString.
 */
public class DibujoCasa extends JPanel {

    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cielo y pasto
        g.setColor(new Color(200, 230, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(110, 190, 90));
        g.fillRect(0, 260, getWidth(), getHeight() - 260);

        // Sol: círculo relleno + rayos con líneas
        g.setColor(Color.ORANGE);
        g.fillOval(370, 30, 60, 60);
        for (int i = 0; i < 360; i += 45) {
            double rad = Math.toRadians(i);
            int x1 = 400 + (int) (38 * Math.cos(rad)), y1 = 60 + (int) (38 * Math.sin(rad));
            int x2 = 400 + (int) (55 * Math.cos(rad)), y2 = 60 + (int) (55 * Math.sin(rad));
            g.drawLine(x1, y1, x2, y2);
        }

        // Nubes: varios óvalos blancos superpuestos
        g.setColor(Color.WHITE);
        g.fillOval(60, 40, 60, 35);
        g.fillOval(90, 30, 60, 45);
        g.fillOval(125, 42, 55, 33);

        // Casa: pared, techo (polígono), puerta, ventana
        g.setColor(new Color(240, 200, 140));
        g.fillRect(120, 160, 160, 110);
        g.setColor(Color.BLACK);
        g.drawRect(120, 160, 160, 110);

        int[] xs = {105, 200, 295};   // vértices del techo (triángulo)
        int[] ys = {160, 90, 160};
        g.setColor(new Color(170, 60, 50));
        g.fillPolygon(xs, ys, 3);

        g.setColor(new Color(120, 70, 30));
        g.fillRect(185, 210, 35, 60);                 // puerta
        g.setColor(Color.YELLOW);
        g.fillOval(212, 240, 5, 5);                   // manija

        g.setColor(new Color(150, 210, 255));
        g.fillRect(135, 180, 35, 30);                 // ventana
        g.setColor(Color.BLACK);
        g.drawRect(135, 180, 35, 30);
        g.drawLine(152, 180, 152, 210);
        g.drawLine(135, 195, 170, 195);

        // Árbol: tronco y copa
        g.setColor(new Color(110, 70, 40));
        g.fillRect(345, 200, 20, 70);
        g.setColor(new Color(40, 140, 60));
        g.fillOval(315, 140, 80, 80);

        // Sonrisa con un arco (drawArc) y texto
        g.setColor(Color.DARK_GRAY);
        g.drawArc(385, 45, 30, 30, 200, 140);
        g.drawString("Dibujo con Graphics - LP3", 10, 300);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Gráfico simple - Paisaje");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(470, 350);
            f.add(new DibujoCasa());
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
