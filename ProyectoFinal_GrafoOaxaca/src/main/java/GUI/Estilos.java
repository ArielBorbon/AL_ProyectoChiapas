package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Estilos {                      //    T(n) = 23      O(1)

    public static JButton crearBoton(String nombre) {                                                   // 23
        JButton boton = new JButton(nombre);            //2
        boton.setFont(new Font("SansSerif", Font.PLAIN, 16));           //3
        boton.setBackground(new Color(134, 192, 160));                          //2
        boton.setForeground(Color.BLACK);                                           //2
        boton.setFocusPainted(false);                                               //1
        boton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); //3
        boton.setPreferredSize(new Dimension(250, 80));                     //2

        Color colorBase = new Color(134, 192, 160);                             //2
        Color colorHover = new Color(107, 163, 133);                            //2

        boton.addMouseListener(new MouseAdapter() {                                 //1
            public void mouseEntered(MouseEvent evt) {  
                boton.setBackground(colorHover);                                    //1
            }

            public void mouseExited(MouseEvent evt) {
                boton.setBackground(colorBase);                                     //1
            }
        });

        return boton;                                                                   //1
    }
}
