/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.utilerias;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelDeBuscador extends JPanel {
    
     private static final Color FONDO  = new Color(22, 22, 22);
    private static final Color BORDE  = new Color(55, 55, 55);
    private static final Color TEXTO  = new Color(241, 245, 249);
    private static final Color MUTED  = new Color(148, 163, 184);

    private static final String TEXTO_DEFAULT = "BUSCAR...";

    private final JTextField txtBuscador;
    private Consumer<String> onBusquedaChange;

    public PanelDeBuscador() {
        setBackground(FONDO);
        setBorder(BorderFactory.createLineBorder(BORDE, 2, true));
        setLayout(new BorderLayout(8, 0));
        setPreferredSize(new Dimension(320, 36));

        // Ícono lupa (texto en lugar de imagen para evitar dependencia de recurso)
        JLabel lblLupa = new JLabel("🔍");
        lblLupa.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        lblLupa.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 4));

        txtBuscador = new JTextField(TEXTO_DEFAULT);
        txtBuscador.setBackground(FONDO);
        txtBuscador.setForeground(MUTED);
        txtBuscador.setCaretColor(TEXTO);
        txtBuscador.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 8));
        txtBuscador.setFont(new Font("Comic Sans MS", Font.BOLD, 14));

        add(lblLupa, BorderLayout.WEST);
        add(txtBuscador, BorderLayout.CENTER);

        inicializarEventos();
    }

    /**
     * Registra el observador que será notificado en tiempo real
     * cada vez que el texto del buscador cambie.
     *
     * @param listener Consumer que recibe el texto actual del buscador.
     */
    public void setOnBusquedaChange(Consumer<String> listener) {
        this.onBusquedaChange = listener;
    }

    /**
     * Retorna el texto actual del buscador, sin espacios.
     *
     * @return Texto de búsqueda.
     */
    public String getTextoBusqueda() {
        String txt = txtBuscador.getText().trim();
        return txt.equals(TEXTO_DEFAULT) ? "" : txt;
    }

    // ── Eventos ──────────────────────────────────────────────────────────────

    private void inicializarEventos() {
        // Placeholder: limpiar al ganar foco
        txtBuscador.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtBuscador.getText().equals(TEXTO_DEFAULT)) {
                    txtBuscador.setText("");
                    txtBuscador.setForeground(TEXTO);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtBuscador.getText().isBlank()) {
                    txtBuscador.setText(TEXTO_DEFAULT);
                    txtBuscador.setForeground(MUTED);
                }
            }
        });

        // Observer: DocumentListener notifica en cada cambio de texto
        txtBuscador.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { notificarCambio(); }
            @Override public void removeUpdate(DocumentEvent e)  { notificarCambio(); }
            @Override public void changedUpdate(DocumentEvent e) { notificarCambio(); }
        });
    }

    /**
     * Notifica al observador registrado con el texto actual.
     */
    private void notificarCambio() {
        if (onBusquedaChange != null) {
            onBusquedaChange.accept(getTextoBusqueda());
        }
    }
}
