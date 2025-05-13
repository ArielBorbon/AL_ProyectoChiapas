package GUI;

import javax.swing.*;
import java.awt.*;
import GUI.Estilos;
public class PanelRecorrer extends JPanel {

    public PanelRecorrer(){
        //configuracion del panel
        setLayout(new BorderLayout());
        //creacion de los botones
        JButton btnBFS = Estilos.crearBoton("Usar busqueda en anchura-BFS");
        JButton btnDFS = Estilos.crearBoton("Usar busqueda en profundidad-DFS");
        //creacion del panel de los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnBFS);
        panelBotones.add(btnDFS);

    }

}
