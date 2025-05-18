package GUI;

import Algoritmos.RutaMasCorta.BellmanFord;
import Algoritmos.RutaMasCorta.Dijkstra;
import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;

public class PanelRutaMasCorta extends JPanel {                                             // T(n) = 511n"2 + 1626

    public static final int OPCION_UNA_CIUDAD = 0;
    public static final int OPCION_DOS_CIUDADES = 1;

    private int opcion;

    public PanelRutaMasCorta() {                                                                                                                                    // 265n"2 + 414n + 1005
        initComponents();   //246n" + 396n + 977
        mostrarGrafo();     // 19n"2 + 18n + 28
    }

    private void initComponents() {                                                 //                                      246n" + 396n + 977
        //configuracion del panel
        setLayout(new BorderLayout());                     //2
        //Crear botones
        JButton btnBF = Estilos.crearBoton("Usar algoritmo de Bellman-Ford");                     //23
        JButton btnDJK = Estilos.crearBoton("Usar algoritmo de Dijkstra");                     //23
        JButton btnVolver = Estilos.crearBoton("Volver");                     //23
        //panel botones
        JPanel panelBotones = new JPanel();                     //2
        panelBotones.setLayout(new GridLayout(1, 3, 10, 10));                     //2

        panelBotones.add(btnBF, BorderLayout.CENTER);                     //2
        panelBotones.add(btnDJK, BorderLayout.CENTER);                     //2
        panelBotones.add(btnVolver, BorderLayout.CENTER);                     //2
        add(panelBotones, BorderLayout.SOUTH);                     //2

        //Metodo para regresar con el boton volver
        btnVolver.addActionListener(e -> {
            // Obtener el JFrame (ventana) que contiene este panel
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);                     //3

            if (ventana instanceof MenuPrincipal) {                     //1
                MenuPrincipal menu = (MenuPrincipal) ventana;                     //2

                // Limpiar todo lo que hay actualmente en la ventana
                menu.getContentPane().removeAll();                     //2

                // Volver a agregar el panel del menú lateral
                menu.add(menu.getPanelMenuLateral(), BorderLayout.WEST);                     //2

                // Agregar algún panel de inicio o bienvenida, si lo usas
                menu.add(new PanelDefault(), BorderLayout.CENTER);                     //3

                // Actualizar la interfaz gráfica
                menu.revalidate();                     //1
                menu.repaint();                     //1
            }
        });

        btnDJK.addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);                     //3

            if (ventana instanceof MenuPrincipal) {                     //1
                MenuPrincipal menu = (MenuPrincipal) ventana;                     //2

                ModalRutaMasCorta modalOpciones = new ModalRutaMasCorta(menu, true);                     //2
                modalOpciones.mostrarModal();                     //1
                this.opcion = modalOpciones.getOpcion();                     //2

                String ciudadOrigen = "";                     //1
                String ciudadDestino = "";                     //1
                if (this.opcion == OPCION_DOS_CIUDADES) {                     //2
                    boolean seleccionValida = false;                     //1
                    while (!seleccionValida) {                     //1
                        ModalSeleccionarFuente modalSeleccionarFuente = new ModalSeleccionarFuente(menu, true, this.opcion);                     //2
                        modalSeleccionarFuente.mostrar();                               //1n
                        ciudadOrigen = modalSeleccionarFuente.getCiudadOrigen();                     //2n
                        ciudadDestino = modalSeleccionarFuente.getCiudadDestino();                     //2n
                        if (ciudadDestino.equals(ciudadOrigen)) {                     //1n
                            JOptionPane.showMessageDialog(this,
                                    "Por favor, selecciona 2 localidades distintas.",
                                    "Misma ubicación",
                                    JOptionPane.ERROR_MESSAGE);                     //2n
                        } else {
                            seleccionValida = true;                     //1n
                        }
                    }
                    pintarRutaDijkstra(ciudadOrigen, ciudadDestino);        //         46n"2 + 97n + 99
                } else if (this.opcion == OPCION_UNA_CIUDAD) {                       //1
                    ModalSeleccionarFuente modalSeleccionarFuente = new ModalSeleccionarFuente(menu, true, this.opcion);                     //2
                    modalSeleccionarFuente.mostrar();                     //1
                    ciudadOrigen = modalSeleccionarFuente.getCiudadOrigen();                     //2
                    pintarRutaDijkstraTodas(ciudadOrigen);                     // 86n"2 + 73n + 125
                }
            }
        });

        btnBF.addActionListener(e
                -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);                     //3
            if (!(ventana instanceof MenuPrincipal)) {                                           //2
                return;                     //1
            }
            MenuPrincipal menu = (MenuPrincipal) ventana;                     //2

            ModalRutaMasCorta modalOpc = new ModalRutaMasCorta(menu, true);                     //2
            modalOpc.mostrarModal();                     //1
            this.opcion = modalOpc.getOpcion();                     //2

            String ciudadOrigen = "";                     //1
            String ciudadDestino = "";                     //1
            if (opcion == OPCION_DOS_CIUDADES) {                     //1
                boolean ok = false;                     //1
                while (!ok) {                     //1
                    ModalSeleccionarFuente msf = new ModalSeleccionarFuente(menu, true, opcion);                     //2
                    msf.mostrar();                     //1
                    ciudadOrigen = msf.getCiudadOrigen();                     //2
                    ciudadDestino = msf.getCiudadDestino();                     //2
                    if (ciudadOrigen.equals(ciudadDestino)) {                     //1
                        JOptionPane.showMessageDialog(this,
                                "Selecciona dos localidades distintas",
                                "Error de selección",
                                JOptionPane.ERROR_MESSAGE);                     //2
                    } else {
                        ok = true;                     //1
                    }
                }
                pintarRutaBellmanFord(ciudadOrigen, ciudadDestino);                     //34n"2 + 93n + 276
            } else {
                ModalSeleccionarFuente msf = new ModalSeleccionarFuente(menu, true, opcion);                     //2
                msf.mostrar();                     //1
                ciudadOrigen = msf.getCiudadOrigen();                     //2
                pintarRutaBellmanFordTodas(ciudadOrigen);                     //80n"2 + 124n + 311
            }
        });

    }

    ;
    
    

    private void mostrarGrafo() {                          //                                                                                       19n"2 + 18 + 28
        JPanel panelGrafo = PanelGrafo.obtenerPanelGrafo();                     //19n"2 + 18n + 18

        Component panelCentral = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);                     //4
        if (panelCentral != null) {                     //1
            remove(panelCentral);                     //1
        }
        add(panelGrafo, BorderLayout.CENTER);                       //2

        revalidate();                     //1
        repaint();                     //1

    }

    private void pintarRutaDijkstra(String origenNombre, String destinoNombre) {        //                                                                      46n"2 + 97n + 99
        new Thread(() -> {                          //1
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();                          //3
                Vertice origen = findVertice(base, origenNombre);                          //2
                Vertice destino = findVertice(base, destinoNombre);                          //2

                Dijkstra.ResultadoPrevia previa = Dijkstra.ejecutarPrevia(base, origen);                          //  15n"2  + 12n  + 21
                List<Arista> camino = Dijkstra.caminoMasCortoListaAristas(base, origen, destino);                          // //        16n"2  +  24n + 17

                System.setProperty("org.graphstream.ui", "swing");                         //2
                Graph graph = PanelGrafo.crearGrafoChiapas();                       //                  19n"2    +   18n   + 9
                graph.setAttribute("ui.stylesheet", """
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px; text-alignment: center; text-size: 12px;
                }
                node.gris  { fill-color: orange; }
                node.negro { fill-color: gray;   }
                edge { fill-color: rgb(130,130,130); size: 2px; }
                edge.highlighted { fill-color: red; size: 4px; }
            """);                                                                                //1

                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);                         //4
                viewer.disableAutoLayout();                         //1
                ViewPanel panel = (ViewPanel) viewer.addDefaultView(false);                         //3

                SwingUtilities.invokeLater(() -> {                         //1
                    removeCurrentGrafoPanel();                         //1
                    add(panel, BorderLayout.CENTER);                         //2
                    revalidate();                         //1
                    repaint();                         //1
                });

                Vertice anterior = null;                         //1
                for (Arista ar : camino) {                         //2n+1
                    graph.getNode(ar.getDestino().getNombre())                         //3n
                            .setAttribute("ui.class", "gris");                         //n

                    String id1 = ar.getOrigen().getNombre() + "-" + ar.getDestino().getNombre();                         //7n
                    String id2 = ar.getDestino().getNombre() + "-" + ar.getOrigen().getNombre();                         //7n
                    Edge e = graph.getEdge(id1);                                                                 //2n
                    if (e == null) {                                                                                      //n
                        e = graph.getEdge(id2);                                                                 //2n
                    }

                    if (e != null) {                                                         //n
                        e.setAttribute("ui.class", "highlighted");                                   //n
                        e.setAttribute("ui.label", ar.getDistancia());                         //2n
                    } else {
                        System.err.println("Arista no encontrada: " + id1 + "/" + id2);                         //3n
                    }

                    Thread.sleep(1200);                                                          //n

                    if (anterior != null) {                                                      //n
                        graph.getNode(anterior.getNombre())                         //2n
                                .setAttribute("ui.class", "negro");                         //n
                    }
                    anterior = ar.getDestino();                                                  //2n
                }

                if (anterior != null) {                                                      //1
                    graph.getNode(anterior.getNombre())                         //2
                            .setAttribute("ui.class", "negro");                         //1
                }

                Map<Vertice, Double> distAcum = previa.distancias;                               //2
                List<Vertice> secuencia = new ArrayList<>();                                         //2
                for (Vertice v = destino; v != null; v = previa.previos.get(v)) {                                 //3n+2
                    secuencia.add(0, v);                                                  //n
                }

                String texto = secuencia.stream()                         //2
                        .map(v -> v.getNombre() + " (" + (int) Math.round(distAcum.getOrDefault(v, 0.0)) + ")")                         //6
                        .collect(Collectors.joining(" → "));                         //2

                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(                         //2
                        this,
                        "Ruta " + origenNombre + " → " + destinoNombre + ":\n" + texto,                         //4
                        "Dijkstra",
                        JOptionPane.INFORMATION_MESSAGE                         //1
                ));

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();                         //2
            }
        }).start();                         //1
    }

    /**
     * Quita el panel central actual antes de añadir el nuevo
     */
    private void removeCurrentGrafoPanel() {                                                                                                                        //7
        Component actual = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);                         //5
        if (actual != null) {                         //1
            remove(actual);                         //1
        }
    }

    private Vertice findVertice(GrafoTDA grafo, String nombre) {                                                                                                    //8                       
        return grafo.obtenerVertices().stream()                         //2
                .filter(v -> v.getNombre().equals(nombre))                         //3
                .findFirst()                                                    //1
                .orElseThrow(() -> new IllegalArgumentException(                         //2
                "No se encontró el vértice con nombre: " + nombre));                         
    }

    private void pintarRutaDijkstraTodas(String origenNombre) {     //                                                                                                        86n"2 + 73n + 125
        new Thread(() -> {                         //1
            try {                         
                GrafoTDA base = new GrafoChiapas().getGrafo();                         //3
                Vertice origen = findVertice(base, origenNombre);                               //9

                Dijkstra.ResultadoPrevia res = Dijkstra.ejecutarPrevia(base, origen);                         //  15n"2  + 12n  + 21
                GrafoTDA subGrafoCompleto = Dijkstra.caminoMasCortoTodas(base, origen);         // 15n"2 + 31n + 36
                Map<Vertice, Double> distAcum = res.distancias;                                 //2

                System.setProperty("org.graphstream.ui", "swing");                                 //1
                Graph graph = PanelGrafo.crearGrafoChiapas();                                 //19n"2    +   18n   + 10
                graph.setAttribute("ui.stylesheet", """                                 
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px; text-alignment: center; text-size: 12px;
                }
                node.gris  { fill-color: orange; }
                node.negro { fill-color: gray;   }
                edge { fill-color: rgb(130,130,130); size: 2px; }
                edge.highlighted { fill-color: red; size: 4px; }
            """);                                 //1
                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);                                 //4
                viewer.disableAutoLayout();                                                                                                                      //1
                ViewPanel panel = (ViewPanel) viewer.addDefaultView(false);                                                                      //3
                SwingUtilities.invokeLater(() -> {                                                                                                             //1
                    removeCurrentGrafoPanel();                                                                                                   //7
                    add(panel, BorderLayout.CENTER);                                                                             //2
                    revalidate();                                                                                                                            //1
                    repaint();                                                                                                                               //1
                });

                Set<String> vistas = new HashSet<>();                                                                                                    //2
                for (Vertice u : subGrafoCompleto.obtenerVertices()) {                                                                   //2n +1 
                    for (Arista a : subGrafoCompleto.obtenerAdyacentes(u)) {                                                         //2n +1
                        String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();                                               //7n*n
                        String id2 = a.getDestino().getNombre() + "-" + a.getOrigen().getNombre();                                               //7n*n
                        if (vistas.contains(id1) || vistas.contains(id2)) {                                                                      //2n*n
                            continue;
                        }
                        vistas.add(id1);                                                                                                              //n*n

                        graph.getNode(a.getDestino().getNombre())                                                                       //3n*n
                                .setAttribute("ui.class", "gris");                                                              //n*n
                        Edge e = graph.getEdge(id1);                                                                         //2n*n
                        if (e == null) {                                                                                                       //n*n
                            e = graph.getEdge(id2);                                                                                  //2n*n
                        }
                        if (e != null) {                                                                                         //n*n
                            e.setAttribute("ui.class", "highlighted");                                                   //n*n
                        }

                        Thread.sleep(1200);                                                                          //n*n
                    }
                }

                StringBuilder sb = new StringBuilder("Caminos mínimos desde " + origenNombre + ":\n");                                 //4
                List<Vertice> vs = new ArrayList<>(base.obtenerVertices());                                                      //3
                vs.sort(Comparator.comparing(Vertice::getNombre));                                                               //3
                for (Vertice destino : vs) {                                                                 //2n +1
                    if (destino.equals(origen)) {                                                    //n
                        continue;                                 //n
                    }
                    List<String> pasos = new ArrayList<>();                                 //2n
                    for (Vertice v = destino; v != null; v = res.previos.get(v)) {                                 //(4n + 1)*n
                        pasos.add(0, v.getNombre() + " (" + (int) Math.round(distAcum.getOrDefault(v, 0.0)) + ")");                                 //6n*n
                    }
                    sb.append("• ").append(String.join(" → ", pasos)).append("\n");                                 //4n
                }
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(                                 //2
                        this, sb.toString(),                                 //1
                        "Dijkstra — todas", JOptionPane.INFORMATION_MESSAGE                                 //1
                ));

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();                                 //2
            }
        }).start();                                 //1
    }

    private void pintarRutaBellmanFord(String origenNombre, String destinoNombre) {                                 //                                                        34n"2 + 93n + 276
        new Thread(() -> {                                                                               //1
            try {   
                GrafoTDA base = new GrafoChiapas().getGrafo();                                                   //3n + 157
                Vertice origen = findVertice(base, origenNombre);                                                   //2
                Vertice destino = findVertice(base, destinoNombre);                                                   //2

                BellmanFord.Resultado res = BellmanFord.ejecutar(base, origen);                             // 15n"2  +  15n + 48
                List<Arista> camino = BellmanFord.reconstruirCamino(res, destino);                  // 11n + 10

                System.setProperty("org.graphstream.ui", "swing");                             //1
                Graph graph = PanelGrafo.crearGrafoChiapas();                               //19n"2    +   18n   + 10
                graph.setAttribute("ui.stylesheet", """
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px;
                  text-alignment: center;
                  text-size: 12px;
                }
                node.gris  { fill-color: orange; }
                node.negro { fill-color: gray;   }
                edge {
                  fill-color: rgb(130,130,130);
                  size: 2px;
                }
                edge.highlighted {
                  fill-color: red;
                  size: 4px;
                }
            """);                               //1

                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);                               //4
                viewer.disableAutoLayout();                                                                                        //1
                ViewPanel panel = (ViewPanel) viewer.addDefaultView(false);                                         //3

                SwingUtilities.invokeLater(() -> {                               //1
                    removeCurrentGrafoPanel();                               //7
                    add(panel, BorderLayout.CENTER);                               //2
                    revalidate();                               //1
                    repaint();                               //1
                });

                Vertice anterior = null;                               //1

                for (Arista ar : camino) {                                         //2n+1

                    Node nodoDestino = graph.getNode(ar.getDestino().getNombre());                               //5n
                    nodoDestino.setAttribute("ui.class", "gris");                               //n

                    String id1 = ar.getOrigen().getNombre() + "-" + ar.getDestino().getNombre();                               //7n
                    String id2 = ar.getDestino().getNombre() + "-" + ar.getOrigen().getNombre();                               //7n
                    Edge e = graph.getEdge(id1);                               //2n
                    if (e == null) {                               //n
                        e = graph.getEdge(id2);                               //2n
                    }
                    if (e == null) {                               //n
                        e = graph.addEdge(id1,                               //2n
                                ar.getOrigen().getNombre(),                               //2n
                                ar.getDestino().getNombre(),                               //2n
                                false);
                    }

                    e.setAttribute("ui.class", "highlighted");                               //n
                    e.setAttribute("ui.label", ar.getDistancia());                               //2n

                    Thread.sleep(1200);                               //n

                    if (anterior != null) {                               //n
                        graph.getNode(anterior.getNombre()).setAttribute("ui.class", "negro");                               //3n
                    }
                    anterior = ar.getDestino();                                     //2n
                }

                if (anterior != null) {                               //1
                    graph.getNode(anterior.getNombre()).setAttribute("ui.class", "negro");                               //4
                }

                List<Vertice> secuencia = new ArrayList<>();                               //2
                for (Vertice v = destino; v != null; v = res.previos.get(v)) {                               //3n+1
                    secuencia.add(0, v);                                                   //n
                }
                String texto = secuencia.stream()                                       //2
                        .map(v -> v.getNombre() + " (" + (int) Math.round(res.distancias.get(v)) + ")")                               //8
                        .collect(Collectors.joining(" → "));                               //2

                SwingUtilities.invokeLater(()                               //1
                        -> JOptionPane.showMessageDialog(this,                               //1
                                "Ruta " + origenNombre + " → " + destinoNombre + ":\n"                               //2
                                + texto,                               //1
                                "Bellman–Ford",
                                JOptionPane.INFORMATION_MESSAGE)                               //1
                );

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();                               //2
            }
        }).start();                               //1
    }

    private void pintarRutaBellmanFordTodas(String origenNombre) {  //                                                                                                        80n"2 + 124n + 311
        new Thread(() -> {                               //1
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();                               //3n + 157
                Vertice origen = findVertice(base, origenNombre);                           //9

                BellmanFord.Resultado res = BellmanFord.ejecutar(base, origen);                             // 15n"2  +  15n + 41

                GrafoTDA subGrafoCompleto = BellmanFord.caminoMasCortoTodas(base, origen);          // 15n"2 + 30n + 49
                Map<Vertice, Double> distAcum = res.distancias;                                       //2                 

                System.setProperty("org.graphstream.ui", "swing");                               //1
                Graph graph = PanelGrafo.crearGrafoChiapas();                                          //19n"2    +   18n   + 10

                graph.setAttribute("ui.stylesheet", """
                node {
                  fill-color: rgb(134,192,160);
                  size: 30px;
                  text-alignment: center;
                  text-size: 12px;
                }
                node.gris  { fill-color: orange; }
                node.negro { fill-color: gray;   }
                edge {
                  fill-color: rgb(130,130,130);
                  size: 2px;
                }
                edge.highlighted {
                  fill-color: red;
                  size: 4px;
                }
            """);                                          //1

                SwingViewer viewer = new SwingViewer(graph, SwingViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);                                          //4
                viewer.disableAutoLayout();                                          //1
                ViewPanel panel = (ViewPanel) viewer.addDefaultView(false);                                          //2

                SwingUtilities.invokeLater(() -> {                                          //1
                    removeCurrentGrafoPanel();                                          //1
                    add(panel, BorderLayout.CENTER);                                          //2
                    revalidate();                                                          //1
                    repaint();                                                       //1
                });

                Set<String> aristasVistas = new HashSet<>();                                          //2
                List<Arista> todasAristas = new ArrayList<>();                                          //2
                for (Vertice v : subGrafoCompleto.obtenerVertices()) {                                          //2n+1
                    for (Arista a : subGrafoCompleto.obtenerAdyacentes(v)) {                                          //(2n+1)*n
                        String clave1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();                                          //7n*n
                        String clave2 = a.getDestino().getNombre() + "-" + a.getOrigen().getNombre();                                          //7n*n
                        if (!aristasVistas.contains(clave1) && !aristasVistas.contains(clave2)) {                                          //3n*n
                            aristasVistas.add(clave1);                                          //n*n
                            todasAristas.add(a);                                          //n*n
                        }
                    }
                }

                Set<Vertice> visitados = new HashSet<>();                                          //2

                for (Arista a : todasAristas) {                                          //2n + 1
                   
                    if (!visitados.contains(a.getOrigen())) {                                          //2n
                        graph.getNode(a.getOrigen().getNombre()).setAttribute("ui.class", "gris");                                          //4n
                        visitados.add(a.getOrigen());                                          //2n
                    }
                    if (!visitados.contains(a.getDestino())) {                                          //2n
                        graph.getNode(a.getDestino().getNombre()).setAttribute("ui.class", "gris");                                          //4n
                        visitados.add(a.getDestino());                                          //2n
                    }

                 
                    String id1 = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();                                          //7n
                    String id2 = a.getDestino().getNombre() + "-" + a.getOrigen().getNombre();                                          //7n
                    Edge e = graph.getEdge(id1);                                                                                      //2n
                    if (e == null) {                                                                                                              //n
                        e = graph.getEdge(id2);                                                                                                   //2n
                    }
                    if (e == null) {                                                                                                          //n
                        e = graph.addEdge(id1,                                                                                    //2n
                                a.getOrigen().getNombre(),                                          //2n
                                a.getDestino().getNombre(),                                          //2n
                                false);
                    }

                    e.setAttribute("ui.class", "highlighted");                                          //n
                    e.setAttribute("ui.label", a.getDistancia());                                          //n

                    Thread.sleep(1200);                                          //n
                }

                for (Vertice v : visitados) {                                          //2n +1
                    graph.getNode(v.getNombre()).setAttribute("ui.class", "negro");                                          //3n
                }

                
                StringBuilder sb = new StringBuilder("Caminos mínimos desde " + origenNombre + ":\n");                                          //2
                List<Vertice> vs = new ArrayList<>(base.obtenerVertices());                                          //3
                vs.sort(Comparator.comparing(Vertice::getNombre));                                          //4

                for (Vertice destino : vs) {                                          //2n+1
                    if (destino.equals(origen)) {                                          //n
                        continue;                                          //n
                    }
                    List<String> pasos = new ArrayList<>();                                          //2n+1
                    Vertice v = destino;                                          //n
                    while (v != null) {                                          //(n+1)*n
                        pasos.add(0, v.getNombre() + " (" + (int) Math.round(distAcum.get(v)) + ")");                                          //6n*n
                        v = res.previos.get(v);                                          //3n*n
                    }
                    sb.append("• ").append(String.join(" → ", pasos)).append("\n");                                          //4n
                }

                SwingUtilities.invokeLater(()                                          //1
                        -> JOptionPane.showMessageDialog(this,                                          //1
                                sb.toString(),                                          //1
                                "Bellman–Ford — todas",
                                JOptionPane.INFORMATION_MESSAGE)                                          //1
                );

            } catch (Exception ex) {
                SwingUtilities.invokeLater(()                                          //1
                        -> JOptionPane.showMessageDialog(this,                                          //1
                                ex.getMessage(),                                          //1
                                "Error",
                                JOptionPane.ERROR_MESSAGE)                                          //1
                );
            }
        }).start();                                          //1
    }

}
