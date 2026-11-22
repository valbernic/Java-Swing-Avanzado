package actividad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;

/**
 * Actividad - Gráfico AVANZADO con Graphics2D.
 * Gráfico circular (pastel) de lenguajes favoritos con antialiasing, degradado
 * (GradientPaint), grosor de línea (BasicStroke) y figuras Arc2D.
 * Es interactivo: al hacer clic sobre una porción, esta se resalta (MouseListener).
 */
public class GraficoPastel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String[] lenguajes = {"Java", "Python", "C++", "JavaScript"};
    private final int[] votos = {40, 30, 15, 15};
    private final Color[] colores = {new Color(231, 111, 81), new Color(42, 157, 143),
            new Color(233, 196, 106), new Color(38, 70, 83)};
    private final Arc2D[] porciones = new Arc2D[lenguajes.length]; // se guardan para detectar clics
    private int seleccionada = -1; // índice de la porción resaltada

    public GraficoPastel() {
        // Interactividad: se detecta en qué porción se hizo clic con Arc2D.contains()
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionada = -1;
                for (int i = 0; i < porciones.length; i++) {
                    if (porciones[i] != null && porciones[i].contains(e.getPoint())) seleccionada = i;
                }
                repaint(); // pide a Swing que vuelva a llamar a paintComponent
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo con degradado vertical
        g2.setPaint(new GradientPaint(0, 0, new Color(245, 247, 250), 0, getHeight(), new Color(215, 225, 240)));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Título
        g2.setColor(new Color(30, 40, 60));
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString("Lenguaje favorito (encuesta)", 20, 30);

        int total = 0;
        for (int v : votos) total += v;

        double inicio = 90; // se empieza arriba (90°) y se avanza en sentido horario
        int x = 40, y = 55, d = 220;
        g2.setStroke(new BasicStroke(2f)); // grosor de borde
        for (int i = 0; i < votos.length; i++) {
            double angulo = 360.0 * votos[i] / total; // cada porción es proporcional a sus votos
            int desp = (i == seleccionada) ? 12 : 0;  // la porción seleccionada se "separa"
            double medio = Math.toRadians(inicio - angulo / 2);
            int dx = (int) (desp * Math.cos(medio)), dy = (int) (-desp * Math.sin(medio));

            porciones[i] = new Arc2D.Double(x + dx, y + dy, d, d, inicio, -angulo, Arc2D.PIE);
            g2.setColor(colores[i]);
            g2.fill(porciones[i]);          // porción rellena
            g2.setColor(Color.WHITE);
            g2.draw(porciones[i]);          // borde blanco
            inicio -= angulo;
        }

        // Leyenda con porcentajes
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        for (int i = 0; i < lenguajes.length; i++) {
            int ly = 90 + i * 30;
            g2.setColor(colores[i]);
            g2.fillRoundRect(290, ly - 12, 16, 16, 5, 5);
            g2.setColor(i == seleccionada ? Color.RED : Color.DARK_GRAY);
            g2.drawString(lenguajes[i] + "  " + (votos[i] * 100 / total) + "%", 315, ly + 1);
        }
        g2.setColor(Color.GRAY);
        g2.drawString("Haz clic en una porción para resaltarla", 20, getHeight() - 15);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Gráfico avanzado - Graphics2D");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(460, 340);
            f.add(new GraficoPastel());
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
