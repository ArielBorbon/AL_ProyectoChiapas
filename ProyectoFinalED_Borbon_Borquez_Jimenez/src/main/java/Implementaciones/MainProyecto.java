package Implementaciones;

import Excepciones.GraphException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author EQUIPO:
 * Sebastian Borquez Huerta 252115
 * Alberto Jimenez Garcia 252595
 * Ariel Eduardo Borbon Izaguirre 252116
 */
public class MainProyecto {
     public static void main(String[] args) throws GraphException {
         
         JFrame frameloco = new JFrame();
         frameloco.setTitle("Proyecto PUEBLA");
         frameloco.setSize(800, 800);
         frameloco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frameloco.setLayout(new BorderLayout());
         frameloco.setLocationRelativeTo(null); 
         JPanel panelBotones = new JPanel();
                 
         ImageIcon imagen = new ImageIcon("Portada_Puebla.png");
         Image imagenEscalada = imagen.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
         ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
         JLabel etiquetaImagen = new JLabel(imagenRedimensionada);
         
         
         panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
         JButton boton = new JButton("Mostrar los vertices");
         boton.setAlignmentX(Component.CENTER_ALIGNMENT);
         JButton boton2 = new JButton("Mostrar matriz de adyacencias");
         boton2.setAlignmentX(Component.CENTER_ALIGNMENT);
         JButton boton3 = new JButton("Mostrar la ruta mas corta entre dos localidades");
         boton3.setAlignmentX(Component.CENTER_ALIGNMENT);
         JButton boton4 = new JButton("Mostrar arbol de esparcimiento minimo");
         boton4.setAlignmentX(Component.CENTER_ALIGNMENT);
         JButton boton5 = new JButton("Salir");
         boton5.setAlignmentX(Component.CENTER_ALIGNMENT);

         
         
     
         panelBotones.add(Box.createVerticalStrut(10));
         panelBotones.add(boton);
         panelBotones.add(Box.createVerticalStrut(10));
         panelBotones.add(boton2);
         panelBotones.add(Box.createVerticalStrut(10));
         panelBotones.add(boton3);
         panelBotones.add(Box.createVerticalStrut(10));
         panelBotones.add(boton4);
         panelBotones.add(Box.createVerticalStrut(10)); 
         panelBotones.add(boton5);
         panelBotones.add(Box.createVerticalStrut(30)); 

         
         frameloco.add(panelBotones, BorderLayout.SOUTH);
         frameloco.add(etiquetaImagen, BorderLayout.NORTH);
         frameloco.setVisible(true);

         
         
         
        try { 
            NoDiGraph<String, Integer> grafo = new NoDiGraph<>(100);
            String[] vertices = {
                "Heroica Puebla de Zaragoza", 
                "Tehuacán", 
                "Cholula de Rivadavia", 
                "San Bernardino Tlaxcalancingo", 
                "Amozoc de Mota", 
                "Atlixco", 
                "San Martín Texmelucan de Labastida", 
                "Teziutlán",
                "Huauchinango", 
                "Izúcar de Matamoros",
                "San Andrés Cholula",
                "Xicotepec de Juárez", 
                "Ciudad de Zacatlán",
                "San Juan Cuautlancingo",
                "Sanctórum",
                "Ciudad de Ajalpan", 
                "Acatzingo de Hidalgo", 
                "Tecamachalco",  
                "Huejotzingo", 
                "Santa María Moyotzingo", 
                "Tepeaca de Negrete",
                "Ciudad Serdán", 
                "Ciudad de Chignahuapan", 
                "Altepexi", 
                "Santiago Momoxpan", 
                "San Pedro Cholula", 
                "San Nicolás de los Ranchos", 
                "San Salvador el Seco", 
                "Tlachichuca" 
            };

            for (String vertex : vertices) {
                grafo.addVertex(vertex);
            }
            
    grafo.addEdge("Heroica Puebla de Zaragoza", "Cholula de Rivadavia", 12);
    grafo.addEdge("Heroica Puebla de Zaragoza", "San Bernardino Tlaxcalancingo", 5);
    grafo.addEdge("Heroica Puebla de Zaragoza", "Amozoc de Mota", 24);
    grafo.addEdge("Heroica Puebla de Zaragoza", "Atlixco", 30);
    grafo.addEdge("Heroica Puebla de Zaragoza", "San Andrés Cholula", 12);
    grafo.addEdge("Heroica Puebla de Zaragoza", "San Juan Cuautlancingo", 9);
    grafo.addEdge("Heroica Puebla de Zaragoza", "San Nicolás de los Ranchos", 21);
    grafo.addEdge("Heroica Puebla de Zaragoza", "Huejotzingo", 37);
    grafo.addEdge("Heroica Puebla de Zaragoza", "Santa María Moyotzingo", 42);
    grafo.addEdge("Heroica Puebla de Zaragoza", "San Pedro Cholula", 14);
    
    grafo.addEdge("Tehuacán", "San Salvador el Seco", 113);
    grafo.addEdge("Tehuacán", "Ciudad de Ajalpan", 21);
    
    grafo.addEdge("Cholula de Rivadavia", "San Pedro Cholula", 2);
    grafo.addEdge("Cholula de Rivadavia", "San Andrés Cholula", 3);
    grafo.addEdge("Cholula de Rivadavia", "San Bernardino Tlaxcalancingo", 7);
    grafo.addEdge("Cholula de Rivadavia", "Huejotzingo", 15);
    grafo.addEdge("Cholula de Rivadavia", "Santiago Momoxpan", 8);
    
    grafo.addEdge("San Bernardino Tlaxcalancingo", "Cholula de Rivadavia", 10);
    grafo.addEdge("San Bernardino Tlaxcalancingo", "San Pedro Cholula", 10);
    grafo.addEdge("San Bernardino Tlaxcalancingo", "Huejotzingo", 37);

    grafo.addEdge("Amozoc de Mota", "Heroica Puebla de Zaragoza", 25);
    grafo.addEdge("Amozoc de Mota", "San Martín Texmelucan de Labastida", 55);
    grafo.addEdge("Amozoc de Mota", "Tecamachalco", 50);
    

    grafo.addEdge("Atlixco", "Huejotzingo", 31);
    grafo.addEdge("Atlixco", "San Nicolás de los Ranchos", 26);
    
    grafo.addEdge("San Martín Texmelucan de Labastida", "Amozoc de Mota", 58);
    grafo.addEdge("San Martín Texmelucan de Labastida", "Huejotzingo", 28);
    grafo.addEdge("San Martín Texmelucan de Labastida", "San Pedro Cholula", 38);
    grafo.addEdge("San Martín Texmelucan de Labastida", "San Salvador el Seco", 110);

    grafo.addEdge("Teziutlán", "Huauchinango", 226);
    grafo.addEdge("Teziutlán", "Izúcar de Matamoros", 218);
    grafo.addEdge("Teziutlán", "Ciudad de Zacatlán", 197);

    grafo.addEdge("Huauchinango", "Teziutlán", 228);
    grafo.addEdge("Huauchinango", "Xicotepec de Juárez", 21);
    grafo.addEdge("Huauchinango", "Ciudad de Zacatlán", 57);

    grafo.addEdge("Izúcar de Matamoros", "Tehuacán", 206);
    grafo.addEdge("Izúcar de Matamoros", "San Andrés Cholula", 73);

    grafo.addEdge("San Andrés Cholula", "San Pedro Cholula", 5);
    grafo.addEdge("San Andrés Cholula", "San Juan Cuautlancingo",  8);
    grafo.addEdge("San Andrés Cholula", "Heroica Puebla de Zaragoza", 11);
    grafo.addEdge("San Andrés Cholula", "San Bernardino Tlaxcalancingo", 6);

    grafo.addEdge("Xicotepec de Juárez", "Teziutlán", 250);
    grafo.addEdge("Xicotepec de Juárez", "Huauchinango",21);

    grafo.addEdge("Ciudad de Zacatlán", "Xicotepec de Juárez", 78);

    grafo.addEdge("San Juan Cuautlancingo", "Heroica Puebla de Zaragoza", 10);
    grafo.addEdge("San Juan Cuautlancingo", "San Pedro Cholula", 7);
    grafo.addEdge("San Juan Cuautlancingo", "San Andrés Cholula", 6);


    grafo.addEdge("Sanctórum", "Heroica Puebla de Zaragoza", 75);
    grafo.addEdge("Sanctórum", "San Bernardino Tlaxcalancingo", 71);
    grafo.addEdge("Sanctórum", "San Pedro Cholula", 67);
    


    grafo.addEdge("Ciudad de Ajalpan", "Tehuacán", 21);
    grafo.addEdge("Ciudad de Ajalpan", "San Salvador el Seco", 136);
    
    grafo.addEdge("Acatzingo de Hidalgo", "Heroica Puebla de Zaragoza", 50);
    grafo.addEdge("Acatzingo de Hidalgo", "Tecamachalco", 16);
    
    grafo.addEdge("Tecamachalco", "Acatzingo de Hidalgo", 26);
    grafo.addEdge("Tecamachalco", "San Salvador el Seco", 41);
    grafo.addEdge("Tecamachalco", "Heroica Puebla de Zaragoza", 71);
    grafo.addEdge("Tecamachalco", "Tehuacán", 80);
    
    grafo.addEdge("Huejotzingo", "San Martín Texmelucan de Labastida", 15);
    grafo.addEdge("Huejotzingo", "Acatzingo de Hidalgo", 78);
    grafo.addEdge("Huejotzingo", "Heroica Puebla de Zaragoza", 39);
    grafo.addEdge("Huejotzingo", "Tepeaca de Negrete", 70);
    
    grafo.addEdge("Santa María Moyotzingo", "Huejotzingo", 14);
    grafo.addEdge("Santa María Moyotzingo", "San Martín Texmelucan de Labastida", 5);
    grafo.addEdge("Santa María Moyotzingo", "Heroica Puebla de Zaragoza", 46);
    
    grafo.addEdge("Tepeaca de Negrete", "Acatzingo de Hidalgo", 19);
    grafo.addEdge("Tepeaca de Negrete", "Huejotzingo", 70);
    grafo.addEdge("Tepeaca de Negrete", "San Martín Texmelucan de Labastida", 72);
    
    
    
    grafo.addEdge("Ciudad Serdán", "Tehuacán", 67);
    grafo.addEdge("Ciudad Serdán", "Acatzingo de Hidalgo", 26);
    grafo.addEdge("Ciudad Serdán", "Tecamachalco", 55);
    grafo.addEdge("Ciudad Serdán", "San Salvador el Seco", 29);
   
    grafo.addEdge("Ciudad de Chignahuapan", "Teziutlán", 187);
    grafo.addEdge("Ciudad de Chignahuapan", "Xicotepec de Juárez", 72);
    grafo.addEdge("Ciudad de Chignahuapan", "Huauchinango", 52);
    
    grafo.addEdge("Altepexi", "Acatzingo de Hidalgo", 106);
    grafo.addEdge("Altepexi", "Tepeaca de Negrete", 122);
    grafo.addEdge("Altepexi", "San Salvador el Seco", 132);
    
    grafo.addEdge("Santiago Momoxpan", "San Pedro Cholula", 6);
    grafo.addEdge("Santiago Momoxpan", "San Andrés Cholula", 5);
    grafo.addEdge("Santiago Momoxpan", "Heroica Puebla de Zaragoza", 10);
    
    
    grafo.addEdge("San Pedro Cholula", "San Andrés Cholula", 3);
    grafo.addEdge("San Pedro Cholula", "Cholula de Rivadavia", 7);
    grafo.addEdge("San Pedro Cholula", "Santiago Momoxpan", 7);
    grafo.addEdge("San Pedro Cholula", "Heroica Puebla de Zaragoza", 14);
    
    grafo.addEdge("San Nicolás de los Ranchos", "Huejotzingo", 20);
    grafo.addEdge("San Nicolás de los Ranchos", "San Pedro Cholula", 21);
    grafo.addEdge("San Nicolás de los Ranchos", "Santiago Momoxpan", 31);
    
    grafo.addEdge("San Salvador el Seco", "San Martín Texmelucan de Labastida", 106);
    grafo.addEdge("San Salvador el Seco", "Tlachichuca", 37);
    
    grafo.addEdge("Tlachichuca", "San Martín Texmelucan de Labastida", 134);
    grafo.addEdge("Tlachichuca", "San Salvador el Seco", 31);
    grafo.addEdge("Tlachichuca", "Huejotzingo", 132);
    grafo.addEdge("Tlachichuca", "Tepeaca de Negrete", 75);
    
    
    boton.addActionListener(e -> {
        JFrame arbolFrame = new JFrame("Municipios y Conexiones");
        arbolFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        arbolFrame.setSize(800, 800); 
        arbolFrame.getContentPane().setBackground(Color.GRAY);
        
        JPanel panelVertices= new JPanel();
        panelVertices.setLayout(new BorderLayout()); 
        JPanel panelBotones2 = new JPanel();
        panelBotones2.setLayout(new FlowLayout());
        ImageIcon imagen1 = new ImageIcon("Mapa_Puebla_Puntos.png"); 
        ImageIcon imagen2 = new ImageIcon("Mapa_Puebla_Vertices.png"); 
        JLabel etiquetaImg = new JLabel();
        
        
        etiquetaImg.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaImg.setVerticalAlignment(SwingConstants.CENTER);
        etiquetaImg.setIcon(imagen1);
        
        arbolFrame.add(etiquetaImg, BorderLayout.CENTER);
        
        JButton botonVertices = new JButton("Mostrar Vertices");
        JButton botonAristas = new JButton("Mostrar Aristas");
        JButton botonLista = new JButton("Mostrar lista de Localidades (Vertices)");
        
        botonVertices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                etiquetaImg.setIcon(imagen1);
            }
        });
        botonAristas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                etiquetaImg.setIcon(imagen2);
            }
        });
        botonLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame listaFrame = new JFrame("Lista de localidades");
                listaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listaFrame.setSize(500, 500);

                JPanel panelLista= new JPanel();
                panelLista.setLayout(new BorderLayout()); 
                StringBuilder output = new StringBuilder();
                
                
                
                
                for (int i = 0; i < grafo.getNumberVertices(); i++) {
                    
                    try {
                        
                        
                        output.append(grafo.getVertex(i));
                        output.append("\n");
                        
                    } catch (GraphException ex) {
                    }
                }
                
                JTextArea textA = new JTextArea(output.toString());
                textA.setEditable(false); 
                JScrollPane scrollP = new JScrollPane(textA); 
                listaFrame.add(scrollP, BorderLayout.CENTER);

                listaFrame.setVisible(true); 
            }
        });
        
        
        panelBotones2.add(botonVertices);
        panelBotones2.add(botonAristas);
        panelBotones2.add(botonLista);
        arbolFrame.add(panelBotones2, BorderLayout.SOUTH);
        arbolFrame.setVisible(true);
        
        
        });

    
    boton2.addActionListener(e -> {
        
            try {
                
            JFrame matrizFrame = new JFrame("Matriz de Adyacencia");
            matrizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            matrizFrame.setSize(1500, 600);

            JPanel panelMatrix = new JPanel();
            panelMatrix.setLayout(new BorderLayout()); 

            JTextArea textAreaM = new JTextArea(grafo.printAdjacencyMatrix().toString());
            textAreaM.setLineWrap(false); 
            textAreaM.setWrapStyleWord(false);
            textAreaM.setEditable(false);
            textAreaM.setFont(new Font("Monospaced", Font.PLAIN, 12));

            
            JScrollPane barraloca = new JScrollPane(textAreaM, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            
            panelMatrix.add(barraloca, BorderLayout.CENTER);

            matrizFrame.add(panelMatrix);
            matrizFrame.setVisible(true);
            
        } catch (GraphException ex) {
            JTextArea textArea = new JTextArea("Error al obtener la matriz");
          
            
            }
        });
    
    
    boton4.addActionListener(e -> {
        try {
            JFrame arbolFrame = new JFrame("Arbol de esparcimiento minimo");
            arbolFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            arbolFrame.setSize(650, 600);

            JPanel panelArbolE= new JPanel();
            panelArbolE.setLayout(new BorderLayout());
            
            JTextArea TextArbol = new JTextArea(grafo.primMST().toString());
            TextArbol.setLineWrap(false); 
            
            TextArbol.setWrapStyleWord(false); 
            TextArbol.setEditable(false); 
            TextArbol.setFont(new Font("Monospaced", Font.PLAIN, 12));

            
            JScrollPane barraloca = new JScrollPane(TextArbol, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            panelArbolE.add(barraloca, BorderLayout.CENTER); 
            arbolFrame.add(panelArbolE);
            arbolFrame.setVisible(true);
            
        } catch (GraphException ex) {
            JTextArea textArea = new JTextArea("Error al obtener la matriz");
            }
        });
    
    boton3.addActionListener(e -> {
        try {
            JFrame frameRuta = new JFrame("Ruta más corta entre municipios");
            frameRuta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameRuta.setSize(600, 300);
        frameRuta.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        JComboBox<String> cajitaInicio = new JComboBox<>(vertices);
        JComboBox<String> cajitaFin = new JComboBox<>(vertices);
        
        
        panel.add(new JLabel("Selecciona el municipio de inicio:"));
        panel.add(cajitaInicio);
        panel.add(new JLabel("Selecciona el municipio de destino:"));
        panel.add(cajitaFin);
        JButton calculateButton = new JButton("Calcular ruta más corta");
        
        frameRuta.add(panel, BorderLayout.CENTER);
        frameRuta.add(calculateButton, BorderLayout.SOUTH);
        frameRuta.setVisible(true);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inicio = (String) cajitaInicio.getSelectedItem();
                String fin = (String) cajitaFin.getSelectedItem();

                if (inicio.equals(fin)) {
                    JOptionPane.showMessageDialog(frameRuta, "El municipio de inicio y destino no pueden ser el mismo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    
                    int PESOTOTAL = grafo.shortestWEIGHT(inicio, fin);
                    LinkedList<String> ruta = grafo.shortestPath(inicio, fin);
                    
                    StringBuilder resultado = new StringBuilder("Ruta más corta:\n");

                    for (String vertex : ruta) {
                        resultado.append(vertex).append(" -> ");
                    }
                    if (ruta.isEmpty() ) {
                        JOptionPane.showMessageDialog(frameRuta, String.join("\n",inicio ,  fin   , "No existe la Ruta Mas Corta " , "Favor de Realizar Una Busqueda Diferente"), "Resultado", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    resultado.delete(resultado.length() - 4, resultado.length());
                    resultado.append("\nDistancia: " + PESOTOTAL + " Kilometros");


                    JOptionPane.showMessageDialog(frameRuta, resultado.toString(), "Resultado", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frameRuta, "Error calculando la ruta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        } catch (Exception ex) {
                    JTextArea textArea = new JTextArea("Error al obtener la matriz");
        }
        });
    
    boton5.addActionListener(e -> {
         System.exit(0);
    });
        } catch (GraphException e) {
            System.err.println("Error en el grafo: " + e.getMessage());
        }
    }
}


