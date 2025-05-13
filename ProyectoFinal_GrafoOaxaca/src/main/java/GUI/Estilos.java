package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Estilos {

    public static JButton crearBoton(String nombre) {
        JButton boton = new JButton(nombre);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        boton.setBackground(new Color(134, 192, 160));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        boton.setPreferredSize(new Dimension(250, 80));

        Color colorBase = new Color(134, 192, 160);
        Color colorHover = new Color(107, 163, 133);

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(colorHover);
            }

            public void mouseExited(MouseEvent evt) {
                boton.setBackground(colorBase);
            }
        });

        return boton;
    }
}
