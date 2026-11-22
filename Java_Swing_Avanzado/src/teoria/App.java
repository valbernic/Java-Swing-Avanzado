package teoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ejemplo de la guía: BINDING DE DATOS MANUAL.
 * La interfaz muestra los datos de un objeto Persona (modelo) y, al pulsar
 * "Actualizar Modelo", copia lo escrito en los campos hacia el modelo.
 */
public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo de Binding de Datos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        // Modelo de datos: el objeto cuyos valores se sincronizan con la interfaz
        Persona persona = new Persona("John Doe", 30, "Masculino");

        // Componentes de interfaz para cada atributo (modelo -> vista: se llenan con los getters)
        JTextField nombreField = new JTextField(persona.getNombre(), 15);
        JTextField edadField = new JTextField(String.valueOf(persona.getEdad()), 15); // int -> String
        JTextField sexoField = new JTextField(persona.getSexo(), 15);
        JButton button = new JButton("Actualizar Modelo");

        // Listener: al presionar el botón se hace la sincronización vista -> modelo
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                persona.setNombre(nombreField.getText()); // getText() lee el campo, set... guarda en el modelo
                try {
                    int edad = Integer.parseInt(edadField.getText()); // convierte el texto a número
                    persona.setEdad(edad);
                } catch (NumberFormatException ex) {
                    // validación: si la edad no es un número se avisa y no se modifica
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido para la edad.");
                }
                persona.setSexo(sexoField.getText());

                // Mostrar los valores actualizados en consola para comprobar el binding
                System.out.println("Modelo actualizado:");
                System.out.println("Nombre: " + persona.getNombre());
                System.out.println("Edad: " + persona.getEdad());
                System.out.println("Sexo: " + persona.getSexo());
            }
        });

        frame.setLayout(new java.awt.FlowLayout()); // los componentes se acomodan en fila
        frame.add(new JLabel("Nombre:"));
        frame.add(nombreField);
        frame.add(new JLabel("Edad:"));
        frame.add(edadField);
        frame.add(new JLabel("Sexo:"));
        frame.add(sexoField);
        frame.add(button);

        frame.setVisible(true);
    }
}

/**
 * Modelo de datos con nombre, edad y sexo (clase JavaBean: atributos privados + get/set).
 */
class Persona {
    private String nombre;
    private int edad;
    private String sexo;

    public Persona(String nombre, int edad, String sexo) {
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}
