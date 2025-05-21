package GUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PanelDefault extends JPanel {
    private Image backgroundImage;
    private String errorMessage = null;

    public PanelDefault() { // Constructor
        setLayout(new BorderLayout()); // 2

        URL imageUrl = getClass().getResource("/mapa_chiapas.jpg"); // 1
        if (imageUrl != null) { // 1
            ImageIcon icon = new ImageIcon(imageUrl); // 1
            backgroundImage = icon.getImage(); // 1
            // Establecer un tamaño preferido basado en la imagen original.
            // Esto es una sugerencia para el layout manager del contenedor.
            setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight())); // 2
        } else {
            errorMessage = "Imagen no encontrada: /mapa_chiapas.jpg"; // 1
            System.err.println(errorMessage);
            setPreferredSize(new Dimension(600, 400)); // Un tamaño por defecto si la imagen falla // 2
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 1 (importante para pintar el fondo base y bordes)
        Graphics2D g2d = (Graphics2D) g.create(); // 1

        // Solo intentar dibujar la imagen si existe y el panel tiene un tamaño visible.
        if (backgroundImage != null && getWidth() > 0 && getHeight() > 0) { // 1
            // Dibujar la imagen escalada para que cubra todo el área del panel
            // getWidth() y getHeight() devuelven el tamaño actual del panel.
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // 1 (operación de renderizado)
        } else if (errorMessage != null) { // 1
            // Si la imagen no se cargó, mostrar un mensaje de error en el panel
            g2d.setColor(Color.RED); // 1
            g2d.setFont(new Font("SansSerif", Font.BOLD, 16)); // 3
            FontMetrics fm = g2d.getFontMetrics(); // 1
            int stringWidth = fm.stringWidth(errorMessage); // 1
            // Centrar el texto del error
            int x = (getWidth() - stringWidth) / 2; // 3
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent(); // 4
            // Asegurarse de que el texto del error sea visible incluso si el panel es muy pequeño
            if (x < 5) x = 5;
            if (y < fm.getAscent() + 5) y = fm.getAscent() + 5;
            g2d.drawString(errorMessage, x, y); // 1
        }
        g2d.dispose(); // 1 (liberar recursos del contexto gráfico copiado)
    }
}