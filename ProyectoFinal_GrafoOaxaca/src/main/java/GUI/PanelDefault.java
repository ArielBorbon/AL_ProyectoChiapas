package GUI;

import javax.swing.*;
import java.awt.*;

public class PanelDefault extends JPanel {                  // T(n)= 8              O(1)
    public PanelDefault() {             //8
        setLayout(new BorderLayout());      //2
        JLabel label = new JLabel("Selecciona una opción del menú", JLabel.CENTER); //2
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));       //3
        add(label, BorderLayout.CENTER);        //2
    }
}       
