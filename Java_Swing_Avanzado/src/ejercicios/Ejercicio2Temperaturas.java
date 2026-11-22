package ejercicios;

import javax.swing.*;
import java.awt.*;

/**
 * Ejercicio 2 - Temperaturas de la semana en un gráfico de líneas.
 * Siete campos de texto (lunes a domingo), un botón "Mostrar Gráfico" y un
 * panel personalizado que dibuja los puntos y las líneas que los unen.
 */
public class Ejercicio2Temperaturas extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String[] DIAS = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};

    private final JTextField[] campos = new JTextField[7];
    private final PanelGrafico panelGrafico = new PanelGrafico();

    public Ejercicio2Temperaturas() {
        super("Ejercicio 2 - Temperatura semanal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // Fila de entrada: etiqueta + campo por cada día
        JPanel entrada = new JPanel(new GridLayout(2, 7, 4, 2));
        entrada.setBorder(BorderFactory.createTitledBorder("Temperatura diaria (°C)"));
        for (String d : DIAS) entrada.add(new JLabel(d, SwingConstants.CENTER));
        double[] ejemplo = {18, 20, 22, 19, 24, 26, 23}; // valores iniciales de ejemplo
        for (int i = 0; i < 7; i++) {
            campos[i] = new JTextField(String.valueOf(ejemplo[i]), 4);
            campos[i].setHorizontalAlignment(JTextField.CENTER);
            entrada.add(campos[i]);
        }

        JButton btnMostrar = new JButton("Mostrar Gráfico");
        btnMostrar.addActionListener(e -> mostrarGrafico());
        JPanel pBoton = new JPanel();
        pBoton.add(btnMostrar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(entrada, BorderLayout.CENTER);
        norte.add(pBoton, BorderLayout.SOUTH);

        add(norte, BorderLayout.NORTH);
        add(panelGrafico, BorderLayout.CENTER);

        setSize(620, 480);
        setLocationRelativeTo(null);
    }

    /** Lee y valida los 7 valores; si son correctos, se los pasa al panel y lo redibuja. */
    private void mostrarGrafico() {
        double[] temps = new double[7];
        for (int i = 0; i < 7; i++) {
            try {
                temps[i] = Double.parseDouble(campos[i].getText().trim().replace(',', '.'));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Temperatura inválida el día " + DIAS[i] + ".");
                campos[i].requestFocus();
                return;
            }
        }
        panelGrafico.setTemperaturas(temps);
    }

    /**
     * Panel que dibuja el gráfico de líneas con Graphics2D.
     */
    static class PanelGrafico extends JPanel {
        private static final long serialVersionUID = 1L;
        private double[] temps; // null hasta que se pulse el botón

        PanelGrafico() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder("Gráfico de líneas"));
        }

        void setTemperaturas(double[] temps) {
            this.temps = temps;
            repaint(); // vuelve a dibujar con los nuevos datos
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int izq = 55, der = 25, arriba = 35, abajo = 45;
            int ancho = getWidth() - izq - der;
            int alto = getHeight() - arriba - abajo;

            // Escala del eje Y: de min a max (redondeado a múltiplos de 5)
            double min = 0, max = 40;
            if (temps != null) {
                min = temps[0]; max = temps[0];
                for (double t : temps) { min = Math.min(min, t); max = Math.max(max, t); }
                min = Math.floor((min - 2) / 5) * 5;
                max = Math.ceil((max + 2) / 5) * 5;
            }

            // Ejes y líneas guía con sus valores
            g2.setColor(new Color(225, 225, 225));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            for (double v = min; v <= max; v += 5) {
                int y = arriba + (int) ((max - v) / (max - min) * alto);
                g2.setColor(new Color(225, 225, 225));
                g2.drawLine(izq, y, izq + ancho, y);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(String.format("%.0f°", v), izq - 35, y + 4);
            }
            g2.setColor(Color.BLACK);
            g2.drawLine(izq, arriba, izq, arriba + alto);                // eje Y
            g2.drawLine(izq, arriba + alto, izq + ancho, arriba + alto); // eje X

            // Etiquetas de los días en el eje X
            int paso = ancho / 6;
            for (int i = 0; i < 7; i++) g2.drawString(DIAS[i], izq + i * paso - 10, arriba + alto + 18);

            if (temps == null) {
                g2.drawString("Ingrese las temperaturas y pulse \"Mostrar Gráfico\"", izq + 40, arriba + alto / 2);
                return;
            }

            // Coordenadas de cada punto (valor -> píxel)
            int[] xs = new int[7], ys = new int[7];
            for (int i = 0; i < 7; i++) {
                xs[i] = izq + i * paso;
                ys[i] = arriba + (int) ((max - temps[i]) / (max - min) * alto);
            }

            // Línea que une los puntos
            g2.setColor(new Color(220, 80, 60));
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawPolyline(xs, ys, 7);

            // Puntos y valores
            for (int i = 0; i < 7; i++) {
                g2.setColor(new Color(40, 90, 160));
                g2.fillOval(xs[i] - 5, ys[i] - 5, 10, 10);
                g2.setColor(Color.BLACK);
                g2.drawString(String.format("%.1f", temps[i]), xs[i] - 10, ys[i] - 9);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio2Temperaturas().setVisible(true));
    }
}
