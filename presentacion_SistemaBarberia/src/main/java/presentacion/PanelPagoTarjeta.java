/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.CitaDTO;
import exceptions.BarberiaNoEncontradaException;
import exceptions.CitaConflictoClienteException;
import exceptions.ClienteNoEncontradoException;
import exceptions.HorarioNoDisponibleException;
import exceptions.ServicioNoEncontradoException;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import presentacion.mediadores.CitaMediator;
import presentacion.mediadores.ICitaMediator;
import presentacion.controles.ControlVistas;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelPagoTarjeta extends JPanel {
    
    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO     = new Color(212, 160, 23);
    private static final Color ERROR_COLOR = new Color(239, 68, 68);
    private static final Font  FONT_LABEL  = new Font("Comic Sans MS", Font.BOLD, 13);
    
    private final ICitaMediator mediadorCita = new CitaMediator();
    
    private JTextField txtNumero, txtNombre, txtFecha, txtCvv;
    private JLabel     lblError;
    private CitaDTO    citaEnProceso;

    private final ControlVistas control;

    public PanelPagoTarjeta(ControlVistas control) {
        this.control = control;
        initUI();
    }

    public void cargarCita(CitaDTO cita) {
        this.citaEnProceso = cita;
        limpiar();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new GridBagLayout());

        // Card redondeada — igual que PanelRegistro
        JPanel card = new presentacion.utilerias.PanelRedondeado(16, CARD, BORDE);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(480, 470));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Pago con Tarjeta");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        lblTitulo.setForeground(TEXTO);

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblLogo.setForeground(TEXTO_MUTED);

        JLabel lblSub = new JLabel("Ingresa los datos de tu tarjeta para confirmar");
        lblSub.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblSub.setForeground(TEXTO_MUTED);

        JPanel headerTop = new JPanel(new BorderLayout());
        headerTop.setBackground(CARD);
        headerTop.add(lblTitulo, BorderLayout.WEST);
        headerTop.add(lblLogo,   BorderLayout.EAST);

        header.add(headerTop, BorderLayout.NORTH);
        header.add(lblSub,    BorderLayout.SOUTH);

        // Form
        txtNumero = campo();
        txtNombre = campo();
        txtFecha  = campo();
        txtCvv    = campo();

        ((AbstractDocument) txtNumero.getDocument()).setDocumentFilter(new SoloNumeros(16));
        ((AbstractDocument) txtCvv.getDocument()).setDocumentFilter(new SoloNumeros(4));
        ((AbstractDocument) txtFecha.getDocument()).setDocumentFilter(new FiltroFecha());
        ((AbstractDocument) txtNombre.getDocument()).setDocumentFilter(new SoloLetras());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD);
        form.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        GridBagConstraints g = new GridBagConstraints();
        g.fill    = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;

        // Nombre — fila completa
        agregarFila(form, g, 0, "Nombre del titular", txtNombre);

        // Numero — fila completa
        agregarFila(form, g, 2, "Numero de tarjeta (16 digitos)", txtNumero);

        // Fecha + CVV — misma fila con dos columnas
        JLabel lblFecha = new JLabel("Vencimiento (MM/AA)");
        lblFecha.setFont(FONT_LABEL);
        lblFecha.setForeground(TEXTO);

        JLabel lblCvv = new JLabel("CVV");
        lblCvv.setFont(FONT_LABEL);
        lblCvv.setForeground(TEXTO);

        JPanel filaCols = new JPanel(new GridLayout(1, 2, 16, 0));
        filaCols.setBackground(CARD);

        JPanel colFecha = new JPanel(new GridBagLayout());
        colFecha.setBackground(CARD);
        GridBagConstraints gf = new GridBagConstraints();
        gf.fill = GridBagConstraints.HORIZONTAL; gf.weightx = 1.0;
        gf.gridy = 0; gf.insets = new Insets(6, 0, 1, 0); colFecha.add(lblFecha, gf);
        gf.gridy = 1; gf.insets = new Insets(1, 0, 6, 0); colFecha.add(txtFecha, gf);

        JPanel colCvv = new JPanel(new GridBagLayout());
        colCvv.setBackground(CARD);
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = GridBagConstraints.HORIZONTAL; gc2.weightx = 1.0;
        gc2.gridy = 0; gc2.insets = new Insets(6, 0, 1, 0); colCvv.add(lblCvv, gc2);
        gc2.gridy = 1; gc2.insets = new Insets(1, 0, 6, 0); colCvv.add(txtCvv, gc2);

        filaCols.add(colFecha);
        filaCols.add(colCvv);

        g.gridy = 4; g.insets = new Insets(6, 0, 6, 0);
        form.add(filaCols, g);

        // Error label
        lblError = new JLabel(" ");
        lblError.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        lblError.setForeground(ERROR_COLOR);
        g.gridy = 5; g.insets = new Insets(4, 0, 0, 0);
        form.add(lblError, g);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(55, 55, 55)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));

        JButton btnPagar = new JButton("Pagar y Confirmar");
        btnPagar.setBackground(BTN_ORO);
        btnPagar.setForeground(new Color(15, 15, 15));
        btnPagar.setOpaque(true);
        btnPagar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnPagar.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnPagar.setFocusPainted(false);
        btnPagar.setBorderPainted(false);
        btnPagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPagar.setAlignmentX(CENTER_ALIGNMENT);
        btnPagar.setMaximumSize(new Dimension(430, 42));

        JButton btnVolver = new JButton("Regresar");
        btnVolver.setBackground(CARD);
        btnVolver.setForeground(TEXTO_MUTED);
        btnVolver.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);

        footer.add(btnPagar);
        footer.add(Box.createVerticalStrut(6));
        footer.add(btnVolver);

        card.add(header, BorderLayout.NORTH);
        card.add(form,   BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);
        add(card);
        
        txtNombre.addActionListener(e -> txtNumero.requestFocusInWindow());
        txtNumero.addActionListener(e -> txtFecha.requestFocusInWindow());
        txtFecha.addActionListener(e -> txtCvv.requestFocusInWindow());
        txtCvv.addActionListener(e -> procesarPago());
        btnPagar.addActionListener(e -> procesarPago());
        btnVolver.addActionListener(e -> {
            // Pasar el numero enmascarado al resumen si ya fue escrito
            String num = txtNumero.getText().trim();
            if (num.length() == 16) {
                control.<PanelConfirmarCita>getPanel(ControlVistas.pantallaConfirmarCita)
                        .setNumeroTarjeta(num);
            }
            control.mostrar(ControlVistas.pantallaConfirmarCita);
        });

        // Actualizar el resumen en tiempo real mientras se escribe el numero
        txtNumero.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void actualizar() {
                String num = txtNumero.getText().trim();
                if (num.length() == 16) {
                    control.<PanelConfirmarCita>getPanel(ControlVistas.pantallaConfirmarCita)
                            .setNumeroTarjeta(num);
                }
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { actualizar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { actualizar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
        });
    }

    private void agregarFila(JPanel form, GridBagConstraints g,
                              int fila, String etiqueta, JTextField campo) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXTO);
        g.gridy = fila;     g.insets = new Insets(6, 0, 1, 0); form.add(lbl,   g);
        g.gridy = fila + 1; g.insets = new Insets(1, 0, 6, 0); form.add(campo, g);
    }

    private void procesarPago() {
        lblError.setText(" ");

        String numero = txtNumero.getText().trim();
        String nombre = txtNombre.getText().trim();
        String fecha  = txtFecha.getText().trim();
        String cvv    = txtCvv.getText().trim();

        if (numero.length() != 16) {
            lblError.setText("El numero de tarjeta debe tener exactamente 16 digitos.");
            return;
        }
        if (nombre.isBlank()) {
            lblError.setText("Ingresa el nombre del titular.");
            return;
        }
        if (!fecha.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            lblError.setText("Fecha invalida. Formato MM/AA  (ej: 08/27).");
            return;
        }
        // Verificar que la tarjeta no este expirada
        try {
            int mesTarjeta = Integer.parseInt(fecha.substring(0, 2));
            int anioTarjeta = 2000 + Integer.parseInt(fecha.substring(3, 5));
            java.time.LocalDate hoy = java.time.LocalDate.now();
            java.time.LocalDate vencimiento = java.time.LocalDate.of(anioTarjeta, mesTarjeta, 1)
                    .plusMonths(1).minusDays(1);
            if (vencimiento.isBefore(hoy)) {
                lblError.setText("La tarjeta esta expirada.");
                return;
            }
        } catch (Exception ex) {
            lblError.setText("Fecha invalida.");
            return;
        }
        if (cvv.length() < 3) {
            lblError.setText("El CVV debe tener 3 o 4 digitos.");
            return;
        }

        try {
            CitaDTO confirmada = mediadorCita.agendarCita(citaEnProceso);
            PanelConfirmacion panelConf = control.getPanel(ControlVistas.pantallaConfirmacion);
            panelConf.setNumeroTarjeta(txtNumero.getText().trim());
            panelConf.mostrarCita(confirmada);
            control.mostrar(ControlVistas.pantallaConfirmacion);
        } catch (HorarioNoDisponibleException ex) {
            JOptionPane.showMessageDialog(this,
                    "El horario seleccionado ya no esta disponible.\nElige otro horario.",
                    "Horario No Disponible", JOptionPane.WARNING_MESSAGE);
            control.mostrar(ControlVistas.pantallaFechaHora);
        } catch (CitaConflictoClienteException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ya tienes una cita agendada a esa hora en otra barberia.",
                    "Conflicto de cita", JOptionPane.WARNING_MESSAGE);
            control.mostrar(ControlVistas.pantallaFechaHora);
        } catch (ClienteNoEncontradoException | BarberiaNoEncontradaException
                | ServicioNoEncontradoException ex) {
            lblError.setText("Error: " + ex.getMessage());
        }
    }

    private void limpiar() {
        txtNumero.setText("");
        txtNombre.setText("");
        txtFecha.setText("");
        txtCvv.setText("");
        if (lblError != null) lblError.setText(" ");
    }

    private JTextField campo() {
        JTextField c = new JTextField();
        c.setBackground(CARD);
        c.setForeground(TEXTO_MUTED);
        c.setCaretColor(TEXTO);
        c.setBorder(BorderFactory.createLineBorder(BORDE));
        c.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        c.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        return c;
    }

    // ── DocumentFilters ─────────────────────────────────────────────────────

    private static class SoloNumeros extends DocumentFilter {
        private final int maxLen;
        SoloNumeros(int maxLen) { this.maxLen = maxLen; }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("[0-9]+")
                    && (fb.getDocument().getLength() + string.length()) <= maxLen) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("[0-9]*")
                    && (fb.getDocument().getLength() - length + text.length()) <= maxLen) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    private static class FiltroFecha extends DocumentFilter {
        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            String actual = fb.getDocument().getText(0, fb.getDocument().getLength());
            StringBuilder sb = new StringBuilder(actual);
            sb.replace(offset, offset + length, text);
            String nuevo = sb.toString().replaceAll("[^0-9/]", "");
            if (nuevo.length() == 2 && !actual.contains("/")
                    && !text.equals("/") && length == 0) {
                nuevo = nuevo + "/";
            }
            if (nuevo.length() <= 5) {
                fb.replace(0, fb.getDocument().getLength(), nuevo, attrs);
            }
        }
    }

    private static class SoloLetras extends DocumentFilter {
        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
                super.insertString(fb, offset, string, attr);
            }
        }
        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

}
