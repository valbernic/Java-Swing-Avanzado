package actividad;

import javax.swing.*;
import java.awt.*;

/**
 * Actividad - Binding de datos manual (aplicación similar a la de la guía).
 * Sincroniza en ambos sentidos un objeto Alumno con la interfaz:
 *  - Vista -> Modelo: botón "Guardar en modelo" (con validación).
 *  - Modelo -> Vista: botón "Cargar desde modelo" y "Subir nota +1".
 */
public class BindingAlumno extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Alumno alumno = new Alumno("Adriano Peralta", "2025001206", 14); // modelo inicial

    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtCodigo = new JTextField(15);
    private final JTextField txtNota = new JTextField(15);
    private final JLabel lblModelo = new JLabel(); // muestra el estado actual del modelo

    public BindingAlumno() {
        super("Binding manual - Alumno");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // Formulario: etiquetas y campos en una cuadrícula de 3 filas x 2 columnas
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Datos del alumno (vista)"));
        form.add(new JLabel("Nombre:"));       form.add(txtNombre);
        form.add(new JLabel("Código:"));       form.add(txtCodigo);
        form.add(new JLabel("Nota (0-20):"));  form.add(txtNota);

        JButton btnGuardar = new JButton("Guardar en modelo");
        JButton btnCargar = new JButton("Cargar desde modelo");
        JButton btnSubir = new JButton("Subir nota +1");
        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnGuardar); botones.add(btnCargar); botones.add(btnSubir);

        lblModelo.setBorder(BorderFactory.createTitledBorder("Estado del modelo"));

        add(form, BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
        add(lblModelo, BorderLayout.SOUTH);

        // Interfaces de escucha: cada botón ejecuta una dirección del binding
        btnGuardar.addActionListener(e -> vistaAModelo());
        btnCargar.addActionListener(e -> modeloAVista());
        btnSubir.addActionListener(e -> {
            alumno.setNota(Math.min(20, alumno.getNota() + 1)); // se cambia el MODELO...
            modeloAVista();                                     // ...y la vista se actualiza
        });

        modeloAVista(); // al iniciar, la vista muestra los datos del modelo
        pack();
        setLocationRelativeTo(null);
    }

    /** Vista -> Modelo: lee los campos (getText), valida y llama a los setters. */
    private void vistaAModelo() {
        String nombre = txtNombre.getText().trim();
        String codigo = txtCodigo.getText().trim();
        if (nombre.isEmpty() || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y código son obligatorios.");
            return; // si no es válido, el modelo NO se modifica
        }
        int nota;
        try {
            nota = Integer.parseInt(txtNota.getText().trim());
            if (nota < 0 || nota > 20) throw new NumberFormatException(); // fuera de rango
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La nota debe ser un número entero entre 0 y 20.");
            txtNota.requestFocus();
            return;
        }
        alumno.setNombre(nombre);
        alumno.setCodigo(codigo);
        alumno.setNota(nota);
        actualizarEtiqueta();
        System.out.println("Modelo actualizado: " + alumno); // comprobación en consola
    }

    /** Modelo -> Vista: lee el modelo (getters) y escribe en los campos (setText). */
    private void modeloAVista() {
        txtNombre.setText(alumno.getNombre());
        txtCodigo.setText(alumno.getCodigo());
        txtNota.setText(String.valueOf(alumno.getNota()));
        actualizarEtiqueta();
    }

    private void actualizarEtiqueta() {
        // JLabel acepta HTML básico para mostrar varias líneas
        lblModelo.setText("<html>Nombre: " + alumno.getNombre() + "<br>Código: " + alumno.getCodigo()
                + "<br>Nota: " + alumno.getNota() + " (" + alumno.getEstado() + ")</html>");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BindingAlumno().setVisible(true)); // crea la GUI en el hilo de Swing
    }
}

/**
 * Modelo de datos del alumno.
 */
class Alumno {
    private String nombre;
    private String codigo;
    private int nota;

    public Alumno(String nombre, String codigo, int nota) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.nota = nota;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }

    public String getEstado() { return nota >= 11 ? "Aprobado" : "Desaprobado"; } // dato calculado

    @Override
    public String toString() { return nombre + " | " + codigo + " | nota " + nota; }
}
