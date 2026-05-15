/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.BarberiaDTO;
import dto.ResenaDTO;
import itson.negocios_gestorresenas.fachada.IResenasFacade;
import itson.negocios_gestorresenas.fachada.ResenasFacade;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import presentacion.controles.ControlVistas;
import presentacion.utilerias.GestorSesion;
import itson.negocios_gestorclientes.fachada.ClientesFacade;
import itson.negocios_gestorclientes.fachada.IClientesFacade;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class PanelResenas extends JPanel {

    private static final Color FONDO = new Color(10, 10, 10);
    private static final Color CARD = new Color(22, 22, 22);
    private static final Color BORDE = new Color(55, 55, 55);
    private static final Color TEXTO = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO = new Color(212, 160, 23);
    private static final Color VERDE = new Color(34, 197, 94);
    private static final Color ROJO = new Color(239, 68, 68);
    private static final Color AMARILLO = new Color(234, 179, 8);

    private final ControlVistas control;
    private final IResenasFacade facadeResena;
    private final IClientesFacade  facadeCliente;

    private BarberiaDTO barberiaActual;
    private JPanel panelLista;
    private JLabel lblTitulo;
    private JLabel lblPromedio;
    private JButton[] btnEstrellas;
    private JTextArea txtComentario;
    private int calificacionSeleccionada = 0;

    private String pantallaOrigen = ControlVistas.pantallaInfoBarberia;

    public PanelResenas(ControlVistas control) {
        this.control = control;
        this.facadeResena = new ResenasFacade();
        this.facadeCliente = new ClientesFacade();
        initUI();
    }

    public void setPantallaOrigen(String pantallaOrigen) {
        this.pantallaOrigen = pantallaOrigen;
    }

    public void setBarberia(BarberiaDTO barberia) {
        this.barberiaActual = barberia;
        lblTitulo.setText("RESEÑAS — " + barberia.getNombre());
        limpiarFormulario();
        cargarResenas();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());
        add(crearTopBar(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);
    }
    
    private void limpiarFormulario() {
        txtComentario.setText("");
        calificacionSeleccionada = 0;
        actualizarEstrellas();
    }

    private JPanel crearTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblTitulo = new JLabel("RESEÑAS");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);

        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(lblLogo, BorderLayout.EAST);
        return topBar;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(FONDO);

        // ── Columna izquierda: lista de reseñas ──
        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(FONDO);
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JScrollPane scroll = new JScrollPane(panelLista,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(FONDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // ── Columna derecha: resumen + formulario ──
        JPanel colDer = new JPanel(new BorderLayout());
        colDer.setPreferredSize(new Dimension(220, 0));
        colDer.setBackground(new Color(13, 13, 13));
        colDer.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, BORDE));

        // resumen promedio
        JPanel resumen = new JPanel(new BorderLayout());
        resumen.setBackground(new Color(15, 15, 15));
        resumen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));

        lblPromedio = new JLabel("★ 0.0", SwingConstants.LEFT);
        lblPromedio.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        lblPromedio.setForeground(BTN_ORO);
        resumen.add(lblPromedio, BorderLayout.CENTER);

        // formulario
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(new Color(13, 13, 13));
        form.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        JLabel lblFormTitulo = new JLabel("DEJA TU RESEÑA");
        lblFormTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        lblFormTitulo.setForeground(TEXTO_MUTED);
        lblFormTitulo.setAlignmentX(LEFT_ALIGNMENT);
        form.add(lblFormTitulo);
        form.add(Box.createVerticalStrut(10));

        JLabel lblCal = new JLabel("CALIFICACIÓN");
        lblCal.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        lblCal.setForeground(TEXTO_MUTED);
        lblCal.setAlignmentX(LEFT_ALIGNMENT);
        form.add(lblCal);
        form.add(Box.createVerticalStrut(6));

        // estrellas clicables
        JPanel panelEstrellas = new JPanel(new GridLayout(1, 5, 3, 0));
        panelEstrellas.setBackground(new Color(13, 13, 13));
        panelEstrellas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        panelEstrellas.setAlignmentX(LEFT_ALIGNMENT);
        btnEstrellas = new JButton[5];
        for (int i = 0; i < 5; i++) {
            final int valor = i + 1;
            btnEstrellas[i] = new JButton("★");
            btnEstrellas[i].setFont(new Font("Dialog", Font.PLAIN, 18));
            btnEstrellas[i].setForeground(BTN_ORO);
            btnEstrellas[i].setBackground(new Color(13, 13, 13));
            btnEstrellas[i].setBorder(BorderFactory.createEmptyBorder());
            btnEstrellas[i].setFocusPainted(false);
            btnEstrellas[i].setBorderPainted(false);
            btnEstrellas[i].setOpaque(true);
            btnEstrellas[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnEstrellas[i].addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    calificacionSeleccionada = valor;
                    actualizarEstrellas();
                }
            });
            panelEstrellas.add(btnEstrellas[i]);
        }
        form.add(panelEstrellas);
        form.add(Box.createVerticalStrut(10));

        JLabel lblCom = new JLabel("COMENTARIO");
        lblCom.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        lblCom.setForeground(TEXTO_MUTED);
        lblCom.setAlignmentX(LEFT_ALIGNMENT);
        form.add(lblCom);
        form.add(Box.createVerticalStrut(6));

        txtComentario = new JTextArea(4, 1);
        txtComentario.setBackground(FONDO);
        txtComentario.setForeground(TEXTO_MUTED);
        txtComentario.setCaretColor(TEXTO);
        txtComentario.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        txtComentario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        JScrollPane scrollCom = new JScrollPane(txtComentario,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCom.setBorder(BorderFactory.createEmptyBorder());
        scrollCom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        scrollCom.setAlignmentX(LEFT_ALIGNMENT);
        form.add(scrollCom);
        form.add(Box.createVerticalStrut(10));

        JButton btnPublicar = new JButton("Publicar reseña");
        btnPublicar.setBackground(BTN_ORO);
        btnPublicar.setForeground(new Color(15, 15, 15));
        btnPublicar.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        btnPublicar.setBorder(BorderFactory.createEmptyBorder(7, 14, 7, 14));
        btnPublicar.setFocusPainted(false);
        btnPublicar.setBorderPainted(false);
        btnPublicar.setOpaque(true);
        btnPublicar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPublicar.setAlignmentX(LEFT_ALIGNMENT);
        btnPublicar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                publicarResena();
            }
        });
        form.add(btnPublicar);

        colDer.add(resumen, BorderLayout.NORTH);
        colDer.add(form, BorderLayout.CENTER);

        contenido.add(scroll, BorderLayout.CENTER);
        contenido.add(colDer, BorderLayout.EAST);
        return contenido;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDE),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JButton btnVolver = new JButton("Regresar");
        btnVolver.setBackground(CARD);
        btnVolver.setForeground(TEXTO);
        btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setOpaque(true);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                control.mostrar(pantallaOrigen);
            }
        });

        footer.add(btnVolver, BorderLayout.WEST);
        return footer;
    }

    private void cargarResenas() {
        panelLista.removeAll();

        if (barberiaActual == null) {
            panelLista.revalidate();
            panelLista.repaint();
            return;
        }

        double promedio = facadeResena.calcularPromedio(barberiaActual.getId());
        lblPromedio.setText(String.format("★ %.1f", promedio));

        List<ResenaDTO> resenas = facadeResena.obtenerPorBarberia(barberiaActual.getId());

        if (resenas.isEmpty()) {
            JLabel lbl = new JLabel("Aún no hay reseñas para esta barbería.");
            lbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
            lbl.setForeground(TEXTO_MUTED);
            panelLista.add(lbl);
        } else {
            for (int i = resenas.size() - 1; i >= 0; i--) {
                panelLista.add(crearCard(resenas.get(i)));
                panelLista.add(Box.createVerticalStrut(8));
            }
        }

        panelLista.revalidate();
        panelLista.repaint();
    }

    private JPanel crearCard(ResenaDTO resena) {
        presentacion.utilerias.PanelRedondeado card
                = new presentacion.utilerias.PanelRedondeado(8, CARD, BORDE);
        card.setLayout(new BorderLayout(0, 4));
        card.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // cabecera: cliente + calificación
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);

        String nombreCliente = "Cliente";
        try {
            nombreCliente = facadeCliente.obtenerPorId(resena.getIdCliente()).getNombre();
        } catch (Exception ex) {
            nombreCliente = "Cliente";
        }

        boolean esDuenio = barberiaActual != null
                && resena.getIdCliente().equals(barberiaActual.getIdBarbero());

        JLabel lblCliente;
        if (esDuenio) {
            lblCliente = new JLabel(nombreCliente + " · Dueño");
            lblCliente.setForeground(BTN_ORO);
        } else {
            lblCliente = new JLabel(nombreCliente);
            lblCliente.setForeground(TEXTO);
        }
        lblCliente.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        lblCliente.setForeground(TEXTO);

        String estrellas = construirEstrellas(resena.getCalificacion());
        Color colorCal = colorPorCalificacion(resena.getCalificacion());
        JLabel lblCal = new JLabel(estrellas);
        lblCal.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        lblCal.setForeground(colorCal);

        cabecera.add(lblCliente, BorderLayout.WEST);
        cabecera.add(lblCal, BorderLayout.EAST);

        // comentario
        JLabel lblComentario = new JLabel("<html><p style='width:300px'>"
                + resena.getComentario() + "</p></html>");
        lblComentario.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblComentario.setForeground(TEXTO_MUTED);

        // fecha
        JLabel lblFecha = new JLabel(resena.getFecha());
        lblFecha.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        lblFecha.setForeground(new Color(80, 80, 80));

        card.add(cabecera, BorderLayout.NORTH);
        card.add(lblComentario, BorderLayout.CENTER);
        card.add(lblFecha, BorderLayout.SOUTH);
        return card;
    }

    private void publicarResena() {
        if (!GestorSesion.haySesion()) {
            JOptionPane.showMessageDialog(this,
                    "Debes iniciar sesión para dejar una reseña.",
                    "Sin sesión", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (calificacionSeleccionada == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una calificación antes de publicar.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (barberiaActual == null) {
            return;
        }

        String comentario = txtComentario.getText().trim();
        if (comentario.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El comentario no puede estar vacío.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fecha = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy",
                        new java.util.Locale("es", "MX")));

        ResenaDTO dto = new ResenaDTO(
                GestorSesion.getClienteActivo().getId(),
                barberiaActual.getId(),
                calificacionSeleccionada,
                comentario,
                fecha);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Deseas publicar tu reseña?",
                "Confirmar reseña", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        facadeResena.agregar(dto);
        txtComentario.setText("");
        calificacionSeleccionada = 5;
        actualizarEstrellas();

        cargarResenas();

        JOptionPane.showMessageDialog(this,
                "¡Reseña publicada correctamente!",
                "Listo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarEstrellas() {
        for (int i = 0; i < btnEstrellas.length; i++) {
            if (i < calificacionSeleccionada) {
                btnEstrellas[i].setForeground(BTN_ORO);
            } else {
                btnEstrellas[i].setForeground(BORDE);
            }
        }
    }

    private String construirEstrellas(int calificacion) {
        StringBuilder sb = new StringBuilder("<html>");
        for (int i = 1; i <= 5; i++) {
            if (i <= calificacion) {
                sb.append("&#9733;");
            } else {
                sb.append("&#9734;");
            }
        }
        sb.append("</html>");
        return sb.toString();
    }

    private Color colorPorCalificacion(int calificacion) {
        if (calificacion >= 5) {
            return VERDE;
        }
        if (calificacion >= 4) {
            return BTN_ORO;
        }
        if (calificacion >= 3) {
            return AMARILLO;
        }
        return ROJO;
    }
}
