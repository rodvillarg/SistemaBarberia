/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.utilerias;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class VentanaPrincipal extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel contenedor;
    private final Map<String, JPanel> paneles;

    public VentanaPrincipal() {
        setTitle("Sistema de Barberias");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        paneles = new HashMap<>();
        add(contenedor, BorderLayout.CENTER);
    }

    public void agregarPanel(JPanel panel, String nombre) {
        paneles.put(nombre, panel);
        contenedor.add(panel, nombre);
    }

    public void mostrar(String nombre) {
        cardLayout.show(contenedor, nombre);
    }

    @SuppressWarnings("unchecked")
    public <T extends JPanel> T obtenerPanel(String nombre) {
        return (T) paneles.get(nombre);
    }
}