package GUI;

import javax.swing.*;
import java.awt.*;

public class PanelDefault extends JPanel {
    public PanelDefault() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Selecciona una opción del menú", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(label, BorderLayout.CENTER);
    }
}
