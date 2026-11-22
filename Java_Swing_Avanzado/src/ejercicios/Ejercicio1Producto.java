package ejercicios;

import javax.swing.*;
import java.awt.*;

/**
 * Ejercicio 1 - Gestión de un producto con binding de datos manual.
 * Al pulsar "Actualizar Producto" los datos de los campos pasan al modelo
 * Producto (setters) y una etiqueta muestra el modelo de forma estructurada (getters).
 */
public class Ejercicio1Producto extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Producto producto = new Producto("Teclado", 45.90, 20, "Tecnología"); // modelo

    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtPrecio = new JTextField(15);
    private final JTextField txtStock = new JTextField(15);
    private final JComboBox<String> cmbCategoria =
            new JComboBox<>(new String[]{"Tecnología", "Abarrotes", "Limpieza", "Ropa", "Hogar"});
    private final JLabel lblInfo = new JLabel();

    public Ejercicio1Producto() {
        super("Ejercicio 1 - Gestión de producto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Datos del producto"));
        form.add(new JLabel("Nombre:"));            form.add(txtNombre);
        form.add(new JLabel("Precio (S/):"));       form.add(txtPrecio);
        form.add(new JLabel("Cantidad en stock:")); form.add(txtStock);
        form.add(new JLabel("Categoría:"));         form.add(cmbCategoria);

        JButton btnActualizar = new JButton("Actualizar Producto");
        btnActualizar.addActionListener(e -> actualizarProducto()); // interfaz de escucha

        lblInfo.setBorder(BorderFactory.createTitledBorder("Información actualizada (modelo)"));
        lblInfo.setPreferredSize(new Dimension(330, 120));

        JPanel centro = new JPanel();
        centro.add(btnActualizar);

        add(form, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(lblInfo, BorderLayout.SOUTH);

        cargarVista();      // modelo -> vista al iniciar
        mostrarModelo();
        pack();
        setLocationRelativeTo(null);
    }

    /** Vista -> Modelo, con validación de los datos numéricos. */
    private void actualizarProducto() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del producto.");
            return;
        }
        double precio;
        int stock;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
            stock = Integer.parseInt(txtStock.getText().trim());
            if (precio < 0 || stock < 0) throw new NumberFormatException(); // no se aceptan negativos
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser números positivos.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return; // el modelo queda como estaba
        }
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCantidadStock(stock);
        producto.setCategoria((String) cmbCategoria.getSelectedItem());
        mostrarModelo(); // la etiqueta refleja el modelo ya actualizado
    }

    /** Modelo -> Vista: llena los campos con los valores del modelo. */
    private void cargarVista() {
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getCantidadStock()));
        cmbCategoria.setSelectedItem(producto.getCategoria());
    }

    /** Muestra el modelo en la etiqueta usando HTML para varias líneas. */
    private void mostrarModelo() {
        lblInfo.setText(String.format("<html><b>Producto:</b> %s<br><b>Precio:</b> S/ %.2f<br>"
                        + "<b>Stock:</b> %d unidades<br><b>Categoría:</b> %s<br><b>Valor en inventario:</b> S/ %.2f</html>",
                producto.getNombre(), producto.getPrecio(), producto.getCantidadStock(),
                producto.getCategoria(), producto.getPrecio() * producto.getCantidadStock()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio1Producto().setVisible(true));
    }
}

/**
 * Modelo de datos: Producto con nombre, precio, cantidadStock y categoria.
 */
class Producto {
    private String nombre;
    private double precio;
    private int cantidadStock;
    private String categoria;

    public Producto(String nombre, double precio, int cantidadStock, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
