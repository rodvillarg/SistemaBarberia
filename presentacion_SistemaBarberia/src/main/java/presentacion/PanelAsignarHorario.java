/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.HorarioDTO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import presentacion.controles.ControlVistas;
import presentacion.mediadores.HorarioMediator;
import presentacion.mediadores.IHorarioMediator;
import presentacion.mediadores.BarberiaMediator;
import presentacion.mediadores.IBarberiaMediator;
import presentacion.utilerias.GestorSesion;


/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class PanelAsignarHorario extends JPanel {

    private static final Color FONDO = new Color(10, 10, 10);
    private static final Color CARD = new Color(22, 22, 22);
    private static final Color BORDE = new Color(55, 55, 55);
    private static final Color TEXTO = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO = new Color(212, 160, 23);

    private static final String[] DIAS = {
        "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"
    };

    private final ControlVistas control;
    private final IHorarioMediator mediadorHorario;
    private final IBarberiaMediator mediadorBarberia;
    

    private JCheckBox[] checks;
    private JComboBox<String>[] combosApertura;
    private JComboBox<String>[] combosCierre;
    private JLabel[] lblNoAtiende;

    private String idBarberia;
    private String pantallaOrigen;

    public PanelAsignarHorario(ControlVistas control) {
        this.control = control;
        this.mediadorHorario = new HorarioMediator();
        this.mediadorBarberia = new BarberiaMediator();
        initUI();
    }

    public void setIdBarberia(String idBarberia) {
        this.idBarberia = idBarberia;
    }
    
    public void setPantallaOrigen(String pantallaOrigen) {
        this.pantallaOrigen = pantallaOrigen;
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());
        add(crearTopBar(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);
    }

    private JPanel crearTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));

        JLabel lblTitulo = new JLabel("Asignar Horario");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTitulo.setForeground(TEXTO);

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);

        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(lblLogo, BorderLayout.EAST);
        return topBar;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel();
        contenido.setBackground(FONDO);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JLabel lblSub = new JLabel("Selecciona los días y horas de atención");
        lblSub.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblSub.setForeground(TEXTO_MUTED);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(lblSub);
        contenido.add(Box.createVerticalStrut(16));

        checks = new JCheckBox[7];
        combosApertura = new JComboBox[7];
        combosCierre = new JComboBox[7];
        lblNoAtiende = new JLabel[7];

        for (int i = 0; i < DIAS.length; i++) {
            final int idx = i;

            checks[i] = new JCheckBox();
            checks[i].setBackground(FONDO);
            checks[i].setFocusPainted(false);
            checks[i].setIcon(iconCheck(false));
            checks[i].setSelectedIcon(iconCheck(true));

            combosApertura[i] = crearCombo();
            combosCierre[i] = crearCombo();
            combosCierre[i].setSelectedIndex(Math.min(
                    combosApertura[i].getSelectedIndex() + 18,
                    combosCierre[i].getItemCount() - 1));

            combosApertura[i].setEnabled(false);
            combosCierre[i].setEnabled(false);

            lblNoAtiende[i] = new JLabel("No atiende");
            lblNoAtiende[i].setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
            lblNoAtiende[i].setForeground(new Color(50, 50, 50));

            checks[i].addActionListener(e -> {
                boolean sel = checks[idx].isSelected();
                combosApertura[idx].setEnabled(sel);
                combosCierre[idx].setEnabled(sel);
                lblNoAtiende[idx].setVisible(!sel);
                combosApertura[idx].setVisible(sel);
                combosCierre[idx].setVisible(sel);
            });

            JLabel lblDia = new JLabel(DIAS[i]);
            lblDia.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
            lblDia.setForeground(TEXTO_MUTED);
            lblDia.setPreferredSize(new Dimension(100, 30));

            JLabel lblApertura = new JLabel("APERTURA");
            lblApertura.setFont(new Font("Comic Sans MS", Font.PLAIN, 9));
            lblApertura.setForeground(TEXTO_MUTED);

            JLabel lblCierre = new JLabel("CIERRE");
            lblCierre.setFont(new Font("Comic Sans MS", Font.PLAIN, 9));
            lblCierre.setForeground(TEXTO_MUTED);

            JPanel colApertura = new JPanel();
            colApertura.setLayout(new BoxLayout(colApertura, BoxLayout.Y_AXIS));
            colApertura.setBackground(FONDO);
            colApertura.add(lblApertura);
            colApertura.add(combosApertura[i]);
            combosApertura[i].setVisible(false);
            lblApertura.setVisible(false);

            JPanel colCierre = new JPanel();
            colCierre.setLayout(new BoxLayout(colCierre, BoxLayout.Y_AXIS));
            colCierre.setBackground(FONDO);
            colCierre.add(lblCierre);
            colCierre.add(combosCierre[i]);
            combosCierre[i].setVisible(false);
            lblCierre.setVisible(false);

            checks[i].addActionListener(e -> {
                boolean sel = checks[idx].isSelected();
                lblApertura.setVisible(sel);
                lblCierre.setVisible(sel);
                lblDia.setForeground(sel ? TEXTO : TEXTO_MUTED);
            });

            JLabel lblSep = new JLabel("—");
            lblSep.setForeground(new Color(55, 55, 55));
            lblSep.setBorder(BorderFactory.createEmptyBorder(14, 4, 0, 4));

            JPanel fila = new JPanel();
            fila.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 4));
            fila.setBackground(FONDO);
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
            fila.setAlignmentX(Component.LEFT_ALIGNMENT);
            fila.add(checks[i]);
            fila.add(lblDia);
            fila.add(colApertura);
            fila.add(lblSep);
            fila.add(colCierre);
            fila.add(lblNoAtiende[i]);

            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(20, 20, 20));
            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            sep.setAlignmentX(Component.LEFT_ALIGNMENT);

            contenido.add(fila);
            if (i < DIAS.length - 1) {
                contenido.add(sep);
            }
        }

        return contenido;
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
        btnVolver.addActionListener(e -> control.mostrar(
                pantallaOrigen != null ? pantallaOrigen : ControlVistas.pantallaRegBarberia));

        JButton btnGuardar = new JButton("Guardar horario");
        btnGuardar.setBackground(BTN_ORO);
        btnGuardar.setForeground(new Color(15, 15, 15));
        btnGuardar.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardar());

        footer.add(btnVolver, BorderLayout.WEST);
        footer.add(btnGuardar, BorderLayout.EAST);
        return footer;
    }

    private JComboBox<String> crearCombo() {
        JComboBox<String> combo = new JComboBox<>();
        for (int h = 7; h <= 22; h++) {
            combo.addItem(String.format("%02d:00", h));
            if (h < 22) {
                combo.addItem(String.format("%02d:30", h));
            }
        }
        combo.setBackground(CARD);
        combo.setForeground(TEXTO);
        combo.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        combo.setPreferredSize(new Dimension(90, 28));
        combo.setFocusable(false);
        return combo;
    }

    private Icon iconCheck(boolean seleccionado) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                if (seleccionado) {
                    g2.setColor(BTN_ORO);
                    g2.fillRoundRect(x, y, 20, 20, 5, 5);
                    g2.setColor(new Color(15, 15, 15));
                    g2.setStroke(new BasicStroke(2.5f,
                            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(x + 4, y + 10, x + 8, y + 14);
                    g2.drawLine(x + 8, y + 14, x + 16, y + 6);
                } else {
                    g2.setColor(FONDO);
                    g2.fillRoundRect(x, y, 20, 20, 5, 5);
                    g2.setColor(BORDE);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(x, y, 20, 20, 5, 5);
                }
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 20;
            }

            @Override
            public int getIconHeight() {
                return 20;
            }
        };
    }
    
    public void refrescar() {
        if (!GestorSesion.haySesion()) {
            return;
        }
        try {
            this.idBarberia = mediadorBarberia
                    .obtenerPorBarbero(GestorSesion.getClienteActivo().getId())
                    .getId();
        } catch (Exception e) {
            return;
        }

        List<HorarioDTO> horarios = mediadorHorario.obtenerHorariosPorBarberia(idBarberia);

        for (int i = 0; i < DIAS.length; i++) {
            checks[i].setSelected(false);
            combosApertura[i].setEnabled(false);
            combosCierre[i].setEnabled(false);
            combosApertura[i].setVisible(false);
            combosCierre[i].setVisible(false);
            lblNoAtiende[i].setVisible(true);
        }

        for (HorarioDTO h : horarios) {
            for (int i = 0; i < DIAS.length; i++) {
                if (DIAS[i].toUpperCase().equals(h.getDiaSemana())) {
                    checks[i].setSelected(true);
                    combosApertura[i].setEnabled(true);
                    combosCierre[i].setEnabled(true);
                    combosApertura[i].setVisible(true);
                    combosCierre[i].setVisible(true);
                    lblNoAtiende[i].setVisible(false);
                    combosApertura[i].setSelectedItem(h.getHoraApertura());
                    combosCierre[i].setSelectedItem(h.getHoraCierre());
                    break;
                }
            }
        }
        revalidate();
        repaint();
    }

    private void guardar() {
        boolean seleccionoAlguno = false;
        for (JCheckBox check : checks) {
            if (check.isSelected()) {
                seleccionoAlguno = true;
                break;
            }
        }

        if (!seleccionoAlguno) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona al menos un día de atención.",
                    "Sin días seleccionados", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (idBarberia == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró la barbería registrada.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<HorarioDTO> horarios = new ArrayList<>();
        for (int i = 0; i < DIAS.length; i++) {
            if (checks[i].isSelected()) {
                String horaApertura = (String) combosApertura[i].getSelectedItem();
                String horaCierre = (String) combosCierre[i].getSelectedItem();

                if (horaCierre.compareTo(horaApertura) <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "El horario de cierre debe ser después del de apertura en "
                            + DIAS[i] + ".",
                            "Horario inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                HorarioDTO dto = new HorarioDTO();
                dto.setIdBarberia(idBarberia);
                dto.setDiaSemana(DIAS[i].toUpperCase());
                dto.setHoraApertura(horaApertura);
                dto.setHoraCierre(horaCierre);
                horarios.add(dto);
            }
        }

        for (HorarioDTO h : horarios) {
            mediadorHorario.registrar(h);
        }

        JOptionPane.showMessageDialog(this,
                "Horarios guardados correctamente.",
                "Listo", JOptionPane.INFORMATION_MESSAGE);

        control.mostrar(pantallaOrigen != null ? pantallaOrigen : ControlVistas.pantallaGestionarServicios);
    }
}
