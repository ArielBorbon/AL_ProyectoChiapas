package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class PanelReportes extends JPanel {

    private JPanel panelOpciones;
    private JPanel panelOpcionesBusqueda;
    private JPanel panelOpcionesMST;
    private JPanel panelOpcionesRuta;
    private JTextArea textAreaReportes;
    private JPanel panelActual; // NUEVO: para volver al panel anterior

    public PanelReportes() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JButton btnReportes = Estilos.crearBoton("Generar reportes de complejidad temporal/ T(n)");
        JButton btnVolver = Estilos.crearBoton("Volver");

        // Panel de botones principales
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        JButton btnRecorrer = Estilos.crearBoton("Reportes de algoritmos de recorrido");
        JButton btnMST = Estilos.crearBoton("Reportes de algoritmo de MST");
        JButton btnRutaMasCorta = Estilos.crearBoton("Reportes de algoritmo de ruta más corta");
        panelOpciones.add(btnRecorrer);
        panelOpciones.add(Box.createVerticalStrut(10));
        panelOpciones.add(btnMST);
        panelOpciones.add(Box.createVerticalStrut(10));
        panelOpciones.add(btnRutaMasCorta);
        panelOpciones.setVisible(false);

        // Subpaneles
        panelOpcionesBusqueda = new JPanel();
        panelOpcionesBusqueda.setLayout(new BoxLayout(panelOpcionesBusqueda, BoxLayout.Y_AXIS));
        JButton btnBFS = Estilos.crearBoton("Reportes de algoritmo BFS");
        JButton btnDFS = Estilos.crearBoton("Reportes de algoritmo DFS");
        panelOpcionesBusqueda.add(btnBFS);
        panelOpcionesBusqueda.add(Box.createVerticalStrut(10));
        panelOpcionesBusqueda.add(btnDFS);
        panelOpcionesBusqueda.setVisible(false);

        panelOpcionesMST = new JPanel();
        panelOpcionesMST.setLayout(new BoxLayout(panelOpcionesMST, BoxLayout.Y_AXIS));
        JButton btnPrim = Estilos.crearBoton("Reportes de algoritmo Prim");
        JButton btnKruskal = Estilos.crearBoton("Reportes de algoritmo Kruskal");
        JButton btnBoruvka = Estilos.crearBoton("Reportes de algoritmo Boruvka");
        panelOpcionesMST.add(btnPrim);
        panelOpcionesMST.add(Box.createVerticalStrut(10));
        panelOpcionesMST.add(btnKruskal);
        panelOpcionesMST.add(Box.createVerticalStrut(10));
        panelOpcionesMST.add(btnBoruvka);
        panelOpcionesMST.setVisible(false);

        panelOpcionesRuta = new JPanel();
        panelOpcionesRuta.setLayout(new BoxLayout(panelOpcionesRuta, BoxLayout.Y_AXIS));
        JButton btnDijkstra = Estilos.crearBoton("Reportes de algoritmo Dijkstra");
        JButton btnBellman = Estilos.crearBoton("Reportes de algoritmo Bellman-Ford");
        panelOpcionesRuta.add(btnDijkstra);
        panelOpcionesRuta.add(Box.createVerticalStrut(10));
        panelOpcionesRuta.add(btnBellman);
        panelOpcionesRuta.setVisible(false);

        // Tamaño uniforme de botones
        Dimension tamBoton = new Dimension(350, 45);
        JButton[] todosLosBotones = {
            btnRecorrer, btnMST, btnRutaMasCorta,
            btnBFS, btnDFS,
            btnPrim, btnKruskal, btnBoruvka,
            btnDijkstra, btnBellman,
            btnReportes, btnVolver
        };
        for (JButton boton : todosLosBotones) {
            boton.setPreferredSize(tamBoton);
            boton.setMaximumSize(tamBoton);
            boton.setMinimumSize(tamBoton);
            boton.setAlignmentX(CENTER_ALIGNMENT);
        }

        // Área de texto
        textAreaReportes = new JTextArea();
        textAreaReportes.setLineWrap(true);
        textAreaReportes.setWrapStyleWord(true);
        textAreaReportes.setEditable(false);
        textAreaReportes.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(textAreaReportes);
        scrollPane.setPreferredSize(new Dimension(700, 400));

        // Panel inferior
        JPanel panelBotonesAbajo = new JPanel();
        panelBotonesAbajo.setLayout(new BoxLayout(panelBotonesAbajo, BoxLayout.Y_AXIS));
        panelBotonesAbajo.add(btnReportes);
        panelBotonesAbajo.add(Box.createVerticalStrut(10));
        panelBotonesAbajo.add(panelOpciones);
        panelBotonesAbajo.add(panelOpcionesBusqueda);
        panelBotonesAbajo.add(panelOpcionesMST);
        panelBotonesAbajo.add(panelOpcionesRuta);
        panelBotonesAbajo.add(Box.createVerticalStrut(10));
        panelBotonesAbajo.add(btnVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotonesAbajo, BorderLayout.SOUTH);

        // Acción volver
        btnVolver.addActionListener(e -> {
            if (panelOpcionesBusqueda.isVisible() || panelOpcionesMST.isVisible() || panelOpcionesRuta.isVisible()) {
                if (panelActual != null) {
                    panelOpcionesBusqueda.setVisible(false);
                    panelOpcionesMST.setVisible(false);
                    panelOpcionesRuta.setVisible(false);
                    panelActual.setVisible(true);
                    textAreaReportes.setText("");
                    panelActual = null; // ya regresamos
                }
            } else {
                // Volver al menú principal
                JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (ventana instanceof MenuPrincipal) {
                    MenuPrincipal menu = (MenuPrincipal) ventana;
                    menu.getContentPane().removeAll();
                    menu.add(menu.getPanelMenuLateral(), BorderLayout.WEST);
                    menu.add(new PanelDefault(), BorderLayout.CENTER);
                    menu.revalidate();
                    menu.repaint();
                }
            }
        });

        // Acción reportes
        btnReportes.addActionListener(e -> {
            btnReportes.setVisible(false);
            panelOpciones.setVisible(true);
            revalidate();
            repaint();
        });

        // Opciones principales
        btnRecorrer.addActionListener(e -> {
            panelActual = panelOpciones;
            panelOpciones.setVisible(false);
            panelOpcionesBusqueda.setVisible(true);
            textAreaReportes.setText("");
        });

        btnMST.addActionListener(e -> {
            panelActual = panelOpciones;
            panelOpciones.setVisible(false);
            panelOpcionesMST.setVisible(true);
            textAreaReportes.setText("");
        });

        btnRutaMasCorta.addActionListener(e -> {
            panelActual = panelOpciones;
            panelOpciones.setVisible(false);
            panelOpcionesRuta.setVisible(true);
            textAreaReportes.setText("");
        });

        // Reportes
        btnBFS.addActionListener(e -> {
            textAreaReportes.setText(
            """
                        -------------------------------- Reporte BFS --------------------------------
            Este algoritmo recorre el grafo nivel por nivel utilizando una cola.
            Complejidad: T(n) = 28n´2 + 20n + 20       O(V + E)     /      O(n´2)
            
            
            Metodo ejecutarBFS
            Este metodo es el encargado de ejecutar el algoritmo BFS desde una semilla Proporcionada, Donde en caso de que exista un vertice en blanco, comenzara el BFS desde este de nuevo
            T(n) = 28n´2 + 20n + 20   /   O(V + E)   O(n´2)
            
         
            ------  Metodos Auxiliares: ( Se ejecuta el BFS y se retorna algo de este ) ------
            
               
            Metodo ObtenerRecorrido
            Este metodo recupera la lista de aristas que el BFS va tomando para completar su operacion, retornando la lista de estas para conocer el camino que tomo durante todo su ciclo
            T(n) = 28n´2 + 20n + 21   /   O(V + E)   O(n´2)
            

             
            Metodo ObtenerArbol
            Este metodo realiza la misma operacion del BFS sin embargo este nos retorna el arbol que retorna el BFS
            T(n) = 28n´2 + 20n + 21   /   O(V + E)   O(n´2)
            
            
            
            Metodo obtenerNiveles
            Este metodo retorna los niveles que se encontraron durante toda la operacion del BFS desde determinada semilla
            T(n) = 28n´2 + 20n + 21   /   O(V + E)   O(n´2)
            
            
            
            Metodo agruparPorNivel
            Este metodo agrupa todas las vertices con su respectivo nivel de profundidad en el cual se encontraron en el BFS
            T(n) = 28n´2 + 28n + 24   /   O(V + E)   O(n´2)
            
            
            
            
            --------- T(n) Total de la Clase ---------
              T(n) = 140n"2 + 108n + 108             O(n"2)     /      O(V + E)
            
            """
            );
        });

        btnDFS.addActionListener(e -> {
            textAreaReportes.setText("""
                                     --- Reporte DFS ---
                                     Este algoritmo recorre el grafo en profundidad utilizando recursión o una pila.
                                     Complejidad: O(V + E)
                                     
                                     Método ejecutarDFS:
                                     Este método es el núcleo del algoritmo DFS. Inicializa las estructuras de datos (colores de vértices, lista de orden de descubrimiento y árbol DFS)
                                     y luego llama a una función de visita recursiva (dfsVisit) comenzando desde el vértice semilla.
                                     También maneja componentes desconectados iterando sobre todos los vértices para asegurar que todos sean visitados.
                                     T(n) = O(V+E) (donde V es el número de vértices y E el número de aristas)
                                     
                                     Método recorridoOrden:
                                     Este método es una utilidad que llama a ejecutarDFS y retorna únicamente la lista de vértices en el orden en que fueron descubiertos.
                                     T(n) = O(V+E) (dominado por la llamada a ejecutarDFS)
                                     
                                     Método obtenerArbol:
                                     Similar a recorridoOrden, este método llama a ejecutarDFS pero retorna el árbol DFS construido durante el recorrido (representado como un mapa de adyacencia).
                                     T(n) = O(V+E) (dominado por la llamada a ejecutarDFS)
                                     
                                     Clase Interna Resultado:
                                     Esta clase simple se utiliza para empaquetar el orden de descubrimiento y el árbol DFS. Su constructor tiene una complejidad constante.
                                     T(n) = 2, O(1)
                                     
                                     Método setListener:
                                     Permite registrar un listener para observar el progreso del algoritmo (por ejemplo, para animaciones). Es una operación de asignación simple.
                                     T(n) = 1, O(1)
                                     
                                     --------- T(n) Total de la Clase (considerando el método principal ejecutarDFS) ---------
                                     T(n) = O(V+E)
                                     (La complejidad de la clase está dominada por la ejecución del algoritmo DFS principal.)
                                     """);
        });

        btnPrim.addActionListener(e -> {
            textAreaReportes.setText("""
                                     --- Reporte Prim ---
                                     Árbol de expansión mínima usando cola de prioridad.
                                     Complejidad: O(E log V)
                                     
                                     
                                     Metodo Run: Ejecuta el algoritmo MST Prim, NO es el metodo Prim, es el metodo que controla y llama su ejecucion
                                     T(n) = 50n"2 + 27n + 8        O(n´2)       /      O(E log V)
                                     
                                     Metodo algoritmoPrim: Este ejecuta el algoritmo Prim obteniendo el Arbol de esparcimiento Minimo de un determinado Grafo:
                                     T(n) = 50n"2 + 27n + 6        O(n´2)       /      O(E log V)
                                     
                                     
                                             --------- Metodos Auxiliares -----------
                                     
                                     Metodo obtenerMinimo:  este obtiene la distancia minima hacia un vertice
                                     T(n) = 6n + 3               O(n)
                                     
                                     Metodos Getter (getMST y isEjecutando):
                                     T(n) = 2                    O(1)
                                     
                                     
                                     --------- T(n) Total de la clase: ---------
                                     //T(n) = 100n"2 + 60n + 19              O(n"2)     /    O(E log V)
                                     
                                     
                                     
                                     
                                     
                                     """);
        });

        btnKruskal.addActionListener(e -> {
            textAreaReportes.setText("""
                                     
                                     --- Reporte Kruskal ---
                                     Ordena aristas y une componentes con conjuntos disjuntos.
                                     Complejidad: O(E log E)
                                     
                                     
                                     Metodo Kruskal: Este metodo ejecuta el algoritmo Kruskal utilizando un determinado grafo y retornando su MST:
                                     T(n) =  8n´2 + 34n + 11        O(n´2)       /       O(E log E)
                                     
                                     
                                     ---------  Metodos Auxiliares  -----------
                                     
                                     Metodo makeSet: Este metodo Realiza un nuevo conjunto de vertices donde se añade a el vertice seleccionado
                                     (Un arreglo de vertices con solamente un vertice adentro)
                                     T(n) = 4                   O(1)
                                     
                                     Metodo findSet: Este metodo busca si cierto arreglo de vertices contiene adentro un vertice, esto para validar si 
                                     se requiere la union o no de estos
                                     T(n) = 4n +6               O(n)
                                     
                                     Metodo union: Este metodo une 2 arreglos de vertices en uno solo 
                                     T(n) = 9                    O(1)
                                      
                                     Metodos Getter (getMST y isEjecutando):
                                     T(n) = 2                    O(1)
                                     
                                     
                                     --------- T(n) Total de la clase: ---------
                                     T(n) = 8n"2 + 38n + 37     O(n´2)       /       O(E log E)
                                     
                                     
                                     """);
        });

        btnBoruvka.addActionListener(e -> {
            textAreaReportes.setText("""
                                     --- Reporte Boruvka ---
                                     Une componentes con aristas más baratas en paralelo.
                                     Complejidad: O(E log V)
                                     
                                     
                                     Metodo Boruvka: Este metodo obtiene el MST de un grafo mediante unir las aristas mas cortas por cada iteracion hasta encontrarlo
                                     T(n) = 38n"3 + 80n"2 + 22n + 35   O(n´2)      /     O(E log V)
                                     
                                     --------- Metodos Auxiliares ---------
                                     
                                     Metodo generarClave: este metodo genera un nombre clave para las aristas y evitar duplicaciones ya que esta no dirigido
                                     T(n) = 12         O(1)
                                     
                                     
                                     Metodos Getter (getMST y detener):
                                     T(n) = 2                        
                                     
                                     --------- T(n) Total de la clase: ---------
                                     T(n) = 38n"3 + 80n"2 + 22n + 52      O(n"3)       /    O(E log V)
                                     
                                     
                                     """);
        });

        btnDijkstra.addActionListener(e -> {
            textAreaReportes.setText("""
                                     --- Reporte Dijkstra ---
                                     Rutas más cortas sin pesos negativos.
                                     Complejidad: O((V + E) log V)
                                     
                                     Metodo rutaMasCorta: 
                                     Este metodo obtiene la lista de vertice y aristas  para llegar de un vertice a otro de la manera mas corta posible
                                     T(n) = 15n"2  + 30n  +  21                 O(n"2)   /   O((V + E) log V)
                                     
                                     Metodo caminoMasCortoTodas:
                                     Este metodo obtiene todos los caminos mas cortos hacia una arista, donde como parametros tiene el grafo y la arista origen
                                     y retorna un grafo con los caminos mas cortos
                                     T(n) = 15n"2 + 31n + 33                 O(n"2)   /   O((V + E) log V)
                                     
                                     Metodo EjecutarPrevia
                                     Este metodo retorna un objeto resultadoPrevia (descripcion abajo) se usa para conocer esta mediante un Grafo y un vertice origen
                                     T(n) =  15n"2  + 30n  +  21            O(n"2)   /   O((V + E) log V)
                                     
                                     Clase DistanciaNodo:
                                     esta clase tiene 2 atributos, un vertice nodo y la distancia hacia este (lo usa el dijkstra para la mayoria de retornos y saber la distancia)
                                     T(n) = 4              O(1)   (t(n) de la clase)
                                     
                                     Metodo CaminoMasCortoListaAristas:
                                     Este metodo retorna un subgrafo  con los vertices del grafo y las aristas de estos caminos
                                     T(n) = 16n´2 + 31n + 33   O(n´2    )
                                     
                                     
                                     Metodo CaminoMasTexto:
                                     Este metodo retorna en un String el camino mas corto, con un formato de vertice -> vertice (peso) -> Vertice (Sumatoria final)
                                     T(n) = 5n + 10        O(n)  
                                     
                                     
                                     
                                     Metodo CaminoMasCortoPrevia: Este verifica todas las distancias mas cortas previas, usado como complemento para el metodo principal
                                     T(n) =  15n"2 + 13n + 13            O(n´2)
                                     
                                     Clase resultadoPrevia:
                                     Esta clase tiene una lista con todas las distancias hacia este vertice y los vertices que toca con estas distancias,
                                     Esta clase su T(n) es de 4 y O(1)
                                     
                                     
                                     
                                     ---------- T(n) Total de la clase: --------
                                     T(n) = 76n"2 + 115n + 115                O(n´2)        /      O((V + E) log V)
                                     
                                     
                                     
                                     """);
        });

        btnBellman.addActionListener(e -> {
            textAreaReportes.setText("""
                                     
                                     --- Reporte Bellman-Ford ---
                                     Rutas con pesos negativos permitidos.
                                     Complejidad: O(V * E)
                                     
                                     
                                     Metodo Ejecutar:
                                     Este metodo ejecuta el algoritmo BellmanFord sobre un grafo y retorna un objeto clase Resultado (descripcion abajo)
                                     T(n) = 15n´2  +  15n + 41      O(n´2)       /        O(V * E)
                                     
                                     Metodo reconstruirCamino: 
                                     Este metodo nos muestra la lista de aristas necesarias para reconstruir del camino del vertice seleccionado al vertice destino
                                     T(n) = 11n + 9     O(n)
                                     
                                     Metodo CaminoMasTexto:
                                     Este metodo nos retorna un String con el nombre del vertice del camino mas corto (Nombre (peso) -> Nombre2 (peso2) -> )
                                     T(n) = 4n + 9      O(n)
                                     
                                     
                                     Metodo CaminoMasCortoTodas:
                                     Este metodo retorna un subgrafo de todos los vertices y las aristas con sus caminos mas cortos 
                                     T(n) = 15n´2 + 30n + 47        O(n´2)      /        O(V * E)
                                     
                                     
                                     
                                     ---------- T(n) Total de la clase: --------
                                     T(n) = 30n"2 + 60 +  112                              O(n"2)      /    O(V * E)
                                     """);
        });
    }
}
