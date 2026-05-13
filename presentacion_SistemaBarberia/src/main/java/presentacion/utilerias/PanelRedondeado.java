/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.utilerias;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelRedondeado extends JPanel {
    private int radio;
    private Color colorFondo;
    private Color colorBorde;
    private int grosorBorde;

    public PanelRedondeado(int radio, Color colorFondo, Color colorBorde) {
        this(radio, colorFondo, colorBorde, 1);
    }

    public PanelRedondeado(int radio, Color colorFondo, Color colorBorde, int grosorBorde) {
        this.radio        = radio;
        this.colorFondo   = colorFondo;
        this.colorBorde   = colorBorde;
        this.grosorBorde  = grosorBorde;
        setOpaque(false);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(
                grosorBorde + 4, grosorBorde + 4,
                grosorBorde + 4, grosorBorde + 4));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Fondo redondeado
        g2.setColor(colorFondo);
        g2.fill(new RoundRectangle2D.Double(0, 0, w, h, radio, radio));

        // Borde redondeado
        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(grosorBorde));
        g2.draw(new RoundRectangle2D.Double(
                grosorBorde / 2.0, grosorBorde / 2.0,
                w - grosorBorde, h - grosorBorde,
                radio, radio));

        g2.dispose();
        super.paintComponent(g);
    }
}
