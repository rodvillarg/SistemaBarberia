/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.ServicioDTO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import presentacion.controles.ControlVistas;
import presentacion.utilerias.GestorSesion;
import itson.negocios_gestorbarberias.fachada.IBarberiasFacade;
import itson.negocios_gestorbarberias.fachada.BarberiasFacade;
import itson.negocios_gestorservicios.fachada.IServiciosFacade;
import itson.negocios_gestorservicios.fachada.ServiciosFacade;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class PanelGestionarServicios extends JPanel {

    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO     = new Color(212, 160, 23);
    private static final Color ROJO        = new Color(220, 60, 60);

    private final ControlVistas      control;
    private final IServiciosFacade  facadeServicio;
    private final IBarberiasFacade  facadeBarberia;

    private JPanel    panelLista;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JSpinner   spinnerDuracion;

    public PanelGestionarServicios(ControlVistas control) {
        this.control          = control;
        this.facadeServicio = new ServiciosFacade();
        this.facadeBarberia = new BarberiasFacade();
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());
        add(crearTopBar(),    BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearFooter(),    BorderLayout.SOUTH);
    }

    private JPanel crearTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));

        JLabel lblTitulo = new JLabel("Gestionar Servicios");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTitulo.setForeground(TEXTO);

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);

        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(lblLogo,   BorderLayout.EAST);
        return topBar;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel();
        contenido.setBackground(FONDO);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ── Lista de servicios ──
        JLabel lblServicios = new JLabel("Mis servicios");
        lblServicios.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblServicios.setForeground(TEXTO_MUTED);
        lblServicios.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(lblServicios);
        contenido.add(Box.createVerticalStrut(10));

        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(FONDO);
        panelLista.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(panelLista);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(30, 30, 30));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(sep);
        contenido.add(Box.createVerticalStrut(14));

        // ── Formulario agregar ──
        JLabel lblAgregar = new JLabel("Agregar nuevo servicio");
        lblAgregar.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblAgregar.setForeground(TEXTO_MUTED);
        lblAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(lblAgregar);
        contenido.add(Box.createVerticalStrut(10));

        JPanel formRow = new JPanel(new GridLayout(1, 3, 12, 0));
        formRow.setBackground(FONDO);
        formRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        formRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNombre = campo();
        txtPrecio = campo();

        SpinnerNumberModel modeloDuracion = new SpinnerNumberModel(30, 10, 30, 5);
        spinnerDuracion = new JSpinner(modeloDuracion);
        spinnerDuracion.setBackground(CARD);
        spinnerDuracion.setForeground(TEXTO);
        spinnerDuracion.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        spinnerDuracion.setBorder(BorderFactory.createLineBorder(BORDE));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerDuracion, "##' min'");
        spinnerDuracion.setEditor(editor);
        editor.getTextField().setBackground(CARD);
        editor.getTextField().setForeground(TEXTO);
        editor.getTextField().setCaretColor(TEXTO);
        editor.getTextField().setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JPanel colNombre = colForm("Nombre", txtNombre);
        JPanel colPrecio = colForm("Precio ($)", txtPrecio);
        JPanel colDuracion = new JPanel();
        colDuracion.setLayout(new BoxLayout(colDuracion, BoxLayout.Y_AXIS));
        colDuracion.setBackground(FONDO);
        JLabel lblDur = new JLabel("Duración");
        lblDur.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        lblDur.setForeground(TEXTO_MUTED);
        colDuracion.add(lblDur);
        colDuracion.add(Box.createVerticalStrut(4));
        colDuracion.add(spinnerDuracion);

        formRow.add(colNombre);
        formRow.add(colPrecio);
        formRow.add(colDuracion);
        contenido.add(formRow);
        contenido.add(Box.createVerticalStrut(10));

        JButton btnAgregar = new JButton("+ Agregar servicio");
        btnAgregar.setBackground(BTN_ORO);
        btnAgregar.setForeground(new Color(15, 15, 15));
        btnAgregar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnAgregar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setOpaque(true);
        btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAgregar.addActionListener(e -> agregarServicio());
        contenido.add(btnAgregar);

        return contenido;
    }

    private JPanel colForm(String etiqueta, JTextField campo) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(FONDO);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        lbl.setForeground(TEXTO_MUTED);
        col.add(lbl);
        col.add(Box.createVerticalStrut(4));
        col.add(campo);
        return col;
    }
    

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDE),
                BorderFactory.createEmptyBorder(10, 24, 10, 24)));

        JButton btnVolver = new JButton("Regresar");
        btnVolver.setBackground(CARD);
        btnVolver.setForeground(TEXTO_MUTED);
        btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setOpaque(true);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaMenuAdmin));

        footer.add(btnVolver, BorderLayout.WEST);
        return footer;
    }
    
    public void refrescar() {
        cargarServicios();
    }

    private void cargarServicios() {
        panelLista.removeAll();
        String idBarberia = obtenerIdBarberia();
        if (idBarberia == null) {
            JLabel lblVacio = new JLabel("No tienes servicios registrados aún.");
            lblVacio.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
            lblVacio.setForeground(TEXTO_MUTED);
            panelLista.add(lblVacio);
            panelLista.revalidate();
            panelLista.repaint();
            return;
        }

        List<ServicioDTO> servicios = facadeServicio.obtenerServiciosPorBarberia(idBarberia);
        if (servicios.isEmpty()) {
            JLabel lblVacio = new JLabel("No tienes servicios registrados aún.");
            lblVacio.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
            lblVacio.setForeground(TEXTO_MUTED);
            panelLista.add(lblVacio);
        } else {
            for (ServicioDTO s : servicios) {
                panelLista.add(crearFilaServicio(s));
            }
        }
        panelLista.revalidate();
        panelLista.repaint();
    }

    private JPanel crearFilaServicio(ServicioDTO s) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(FONDO);
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(20, 20, 20)),
                BorderFactory.createEmptyBorder(8, 0, 8, 0)));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblIcono = new JLabel("✂");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        lblIcono.setForeground(BTN_ORO);
        lblIcono.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 12));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(FONDO);

        JLabel lblNombre = new JLabel(s.getNombre());
        lblNombre.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        lblNombre.setForeground(TEXTO);

        JLabel lblMeta = new JLabel(s.getDuracionMinutos() + " min");
        lblMeta.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        lblMeta.setForeground(TEXTO_MUTED);

        info.add(lblNombre);
        info.add(lblMeta);

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        der.setBackground(FONDO);

        JLabel lblPrecio = new JLabel("$" + String.format("%.0f", s.getPrecio()));
        lblPrecio.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblPrecio.setForeground(BTN_ORO);

        JButton btnEliminar = new JButton("X");
        btnEliminar.setBackground(FONDO);
        btnEliminar.setForeground(new Color(100, 100, 100));
        btnEliminar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnEliminar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btnEliminar.setForeground(ROJO);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btnEliminar.setForeground(new Color(100, 100, 100));
            }
        });
        btnEliminar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar el servicio \"" + s.getNombre() + "\"?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                facadeServicio.eliminar(s.getId());
                cargarServicios();
            }
        });

        der.add(lblPrecio);
        der.add(btnEliminar);

        JPanel izq = new JPanel(new BorderLayout());
        izq.setBackground(FONDO);
        izq.add(lblIcono, BorderLayout.WEST);
        izq.add(info,     BorderLayout.CENTER);

        fila.add(izq,  BorderLayout.WEST);
        fila.add(der,  BorderLayout.EAST);
        return fila;
    }

    private void agregarServicio() {
        String nombre = txtNombre.getText().trim();
        String precio = txtPrecio.getText().trim();
        int duracion  = (int) spinnerDuracion.getValue();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre del servicio es obligatorio.",
                    "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (precio.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El precio es obligatorio.",
                    "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precioDouble;
        try {
            precioDouble = Double.parseDouble(precio);
            if (precioDouble <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El precio debe ser un número mayor a 0.",
                    "Precio inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idBarberia = obtenerIdBarberia();
        if (idBarberia == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró tu barbería.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ServicioDTO dto = new ServicioDTO();
        dto.setNombre(nombre);
        dto.setPrecio(precioDouble);
        dto.setDuracionMinutos(duracion);
        dto.setIdBarberia(idBarberia);

        facadeServicio.registrar(dto);

        txtNombre.setText("");
        txtPrecio.setText("");
        spinnerDuracion.setValue(30);

        cargarServicios();
    }

    private String obtenerIdBarberia() {
        if (!GestorSesion.haySesion()) return null;
        try {
            return facadeBarberia
                    .obtenerPorBarbero(GestorSesion.getClienteActivo().getId())
                    .getId();
        } catch (Exception e) {
            return null;
        }
    }

    private JTextField campo() {
        JTextField f = new JTextField();
        f.setBackground(CARD);
        f.setForeground(TEXTO_MUTED);
        f.setCaretColor(TEXTO);
        f.setBorder(BorderFactory.createLineBorder(BORDE));
        f.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        f.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        return f;
    }
} 
