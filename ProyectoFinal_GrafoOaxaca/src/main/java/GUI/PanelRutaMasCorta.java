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
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanelRutaMasCorta extends JPanel {

    public static final int OPCION_UNA_CIUDAD = 0;
    public static final int OPCION_DOS_CIUDADES = 1;

    private int opcion;

    public PanelRutaMasCorta() {
        initComponents();
        mostrarGrafo();
    }

    private void initComponents() {
        //configuracion del panel
        setLayout(new BorderLayout());
        //Crear botones
        JButton btnBF = Estilos.crearBoton("Usar algoritmo de Bellman-Ford");
        JButton btnDJK = Estilos.crearBoton("Usar algoritmo de Dijkstra");
        JButton btnVolver = Estilos.crearBoton("Volver");
        //panel botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3, 10, 10));

        panelBotones.add(btnBF, BorderLayout.CENTER);
        panelBotones.add(btnDJK, BorderLayout.CENTER);
        panelBotones.add(btnVolver, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        //Metodo para regresar con el boton volver
        btnVolver.addActionListener(e -> {
            // Obtener el JFrame (ventana) que contiene este panel
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);

            if (ventana instanceof MenuPrincipal) {
                MenuPrincipal menu = (MenuPrincipal) ventana;

                // Limpiar todo lo que hay actualmente en la ventana
                menu.getContentPane().removeAll();

                // Volver a agregar el panel del menú lateral
                menu.add(menu.getPanelMenuLateral(), BorderLayout.WEST);

                // Agregar algún panel de inicio o bienvenida, si lo usas
                menu.add(new PanelDefault(), BorderLayout.CENTER);

                // Actualizar la interfaz gráfica
                menu.revalidate();
                menu.repaint();
            }
        });

        btnDJK.addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);

            if (ventana instanceof MenuPrincipal) {
                MenuPrincipal menu = (MenuPrincipal) ventana;

                ModalRutaMasCorta modalOpciones = new ModalRutaMasCorta(menu, true);
                modalOpciones.mostrarModal();
                this.opcion = modalOpciones.getOpcion();

                String ciudadOrigen = "";
                String ciudadDestino = "";
                if (this.opcion == OPCION_DOS_CIUDADES) {
                    boolean seleccionValida = false;
                    while (!seleccionValida) {
                        ModalSeleccionarFuente modalSeleccionarFuente = new ModalSeleccionarFuente(menu, true, this.opcion);
                        modalSeleccionarFuente.mostrar();
                        ciudadOrigen = modalSeleccionarFuente.getCiudadOrigen();
                        ciudadDestino = modalSeleccionarFuente.getCiudadDestino();
                        if (ciudadDestino.equals(ciudadOrigen)) {
                            JOptionPane.showMessageDialog(this,
                                    "Por favor, selecciona 2 localidades distintas.",
                                    "Misma ubicación",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            seleccionValida = true;
                        }
                    }
                    pintarRutaDijkstra(ciudadOrigen, ciudadDestino);
                } else if (this.opcion == OPCION_UNA_CIUDAD) {
                    ModalSeleccionarFuente modalSeleccionarFuente = new ModalSeleccionarFuente(menu, true, this.opcion);
                    modalSeleccionarFuente.mostrar();
                    ciudadOrigen = modalSeleccionarFuente.getCiudadOrigen();
                    pintarRutaDijkstraTodas(ciudadOrigen);
                }
            }
        });

        btnBF.addActionListener(e
                -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (!(ventana instanceof MenuPrincipal)) {
                return;
            }
            MenuPrincipal menu = (MenuPrincipal) ventana;

            ModalRutaMasCorta modalOpc = new ModalRutaMasCorta(menu, true);
            modalOpc.mostrarModal();
            this.opcion = modalOpc.getOpcion();

            String ciudadOrigen = "";
            String ciudadDestino = "";
            if (opcion == OPCION_DOS_CIUDADES) {
                boolean ok = false;
                while (!ok) {
                    ModalSeleccionarFuente msf = new ModalSeleccionarFuente(menu, true, opcion);
                    msf.mostrar();
                    ciudadOrigen = msf.getCiudadOrigen();
                    ciudadDestino = msf.getCiudadDestino();
                    if (ciudadOrigen.equals(ciudadDestino)) {
                        JOptionPane.showMessageDialog(this,
                                "Selecciona dos localidades distintas",
                                "Error de selección",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        ok = true;
                    }
                }
                pintarRutaBellmanFord(ciudadOrigen, ciudadDestino);
            } else {
                ModalSeleccionarFuente msf = new ModalSeleccionarFuente(menu, true, opcion);
                msf.mostrar();
                ciudadOrigen = msf.getCiudadOrigen();
                pintarRutaBellmanFordTodas(ciudadOrigen);
            }
        });

    }

    ;
    
    

    private void mostrarGrafo() {
        JPanel panelGrafo = PanelGrafo.obtenerPanelGrafo();

        Component panelCentral = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (panelCentral != null) {
            remove(panelCentral);
        }
        add(panelGrafo, BorderLayout.CENTER);

        revalidate();
        repaint();

    }

    private void pintarRutaDijkstra(String origenNombre, String destinoNombre) {
        new Thread(() -> {
            try {
                GrafoTDA grafoBase = new GrafoChiapas().getGrafo();

                Vertice origen = grafoBase.obtenerVertices().stream()
                        .filter(v -> v.getNombre().equals(origenNombre))
                        .findFirst()
                        .orElseThrow();
                Vertice destino = grafoBase.obtenerVertices().stream()
                        .filter(v -> v.getNombre().equals(destinoNombre))
                        .findFirst()
                        .orElseThrow();

                List<Arista> camino = Dijkstra.caminoMasCortoListaAristas(grafoBase, origen, destino);

                GrafoTDA subGrafo = new GrafoTDA();

                List<Vertice> ciudades = new ArrayList<>(grafoBase.obtenerVertices());
                ciudades.sort(Comparator.comparing(Vertice::getNombre));
                for (Vertice v : ciudades) {
                    subGrafo.agregarVertice(v);

                }

                double acumulado = 0;
                List<String> rutaConPesos = new ArrayList<>();
                rutaConPesos.add(origenNombre + " (0)");

                List<String> nombresRuta = new ArrayList<>();
                for (Arista ar : camino) {
                    acumulado += ar.getDistancia();
                    rutaConPesos.add(ar.getDestino().getNombre() + " (" + (int) acumulado + ")");
                    subGrafo.agregarArista(ar.getOrigen(), ar.getDestino(), ar.getDistancia());

                    if (nombresRuta.isEmpty()) {
                        nombresRuta.add(ar.getOrigen().getNombre());
                    }
                    nombresRuta.add(ar.getDestino().getNombre());
                    subGrafo.agregarArista(ar.getOrigen(), ar.getDestino(), ar.getDistancia());

                    SwingUtilities.invokeLater(() -> {
                        JPanel panel = PanelGrafo.obtenerGrafoPintado(subGrafo);
                        removeCurrentGrafoPanel();
                        add(panel, BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    });

                    Thread.sleep(1000);
                }

                String resumenRuta = String.join(" -> ", rutaConPesos);

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            "Ruta final de " + origenNombre + " a " + destinoNombre + ":\n" + resumenRuta,
                            "Camino más corto (Dijkstra)",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Quita el panel central actual antes de añadir el nuevo
     */
    private void removeCurrentGrafoPanel() {
        Component actual = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (actual != null) {
            remove(actual);
        }
    }

    private void pintarRutaDijkstraTodas(String origenNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice origen = base.obtenerVertices().stream()
                        .filter(v -> v.getNombre().equals(origenNombre))
                        .findFirst().orElseThrow();

                Dijkstra.ResultadoPrevia resultadoPrevia = Dijkstra.ejecutarPrevia(base, origen);
                GrafoTDA caminos = Dijkstra.caminoMasCortoTodas(base, origen);

                List<Arista> todas = new ArrayList<>();

                List<Vertice> ordenados = new ArrayList<>(caminos.obtenerVertices());
                ordenados.sort(Comparator.comparing(Vertice::getNombre));

                for (Vertice u : ordenados) {
                    todas.addAll(caminos.obtenerAdyacentes(u));
                    List<Arista> ady = new ArrayList<>(caminos.obtenerAdyacentes(u));
                    ady.sort(Comparator
                            .comparing((Arista a) -> a.getDestino().getNombre())
                    );
                }

                Set<String> vistos = new HashSet<>();
                for (Arista a : todas) {
                    String key = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();
                    String rev = a.getDestino().getNombre() + "-" + a.getOrigen().getNombre();
                    if (vistos.contains(key) || vistos.contains(rev)) {
                        continue;
                    }
                    vistos.add(key);

                    GrafoTDA parcial = new GrafoTDA();
                    List<Vertice> ciudades = new ArrayList<>(base.obtenerVertices());
                    ciudades.sort(Comparator.comparing(Vertice::getNombre));
                    for (Vertice v : ciudades) {
                        parcial.agregarVertice(v);
                    }

                    for (String agregado : vistos) {
                        String[] parts = agregado.split("-");
                        Vertice x = base.obtenerVertices().stream()
                                .filter(v -> v.getNombre().equals(parts[0])).findFirst().get();
                        Vertice y = base.obtenerVertices().stream()
                                .filter(v -> v.getNombre().equals(parts[1])).findFirst().get();
                        double w = caminos.obtenerAdyacentes(x).stream()
                                .filter(ar -> ar.getDestino().equals(y))
                                .findFirst().get().getDistancia();
                        parcial.agregarArista(x, y, w);
                    }

                    SwingUtilities.invokeLater(() -> {
                        removeCurrentGrafoPanel();
                        add(PanelGrafo.obtenerGrafoPintado(parcial), BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    });

                    Thread.sleep(1000);
                }

                StringBuilder sb = new StringBuilder("Rutas más cortas desde " + origenNombre + ":\n");
                ordenados = new ArrayList<>(base.obtenerVertices());
                ordenados.sort(Comparator.comparing(Vertice::getNombre));

                for (Vertice destino : ordenados) {
                    if (destino.equals(origen)) {
                        continue;
                    }

                    List<Vertice> caminoV = new ArrayList<>();
                    Vertice paso = destino;
                    while (paso != null) {
                        caminoV.add(0, paso);
                        paso = resultadoPrevia.previos.get(paso);
                    }

                    List<String> partes = new ArrayList<>();
                    for (Vertice v : caminoV) {
                        double pesoAcum = resultadoPrevia.distancias.get(v);
                        partes.add(v.getNombre() + " (" + (int) Math.round(pesoAcum) + ")");
                    }
                    sb.append("- ")
                            .append(String.join(" → ", partes))
                            .append("\n");
                }

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            sb.toString(),
                            "Caminos más cortos desde " + origenNombre,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void pintarRutaBellmanFord(String origenNombre, String destinoNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice origen = findVertice(base, origenNombre);
                Vertice destino = findVertice(base, destinoNombre);

                BellmanFord.Resultado res = BellmanFord.ejecutar(base, origen);
                List<Arista> camino = BellmanFord.reconstruirCamino(res, destino);

                GrafoTDA sub = new GrafoTDA();
                base.obtenerVertices().forEach(sub::agregarVertice);

                for (Arista ar : camino) {
                    sub.agregarArista(ar.getOrigen(), ar.getDestino(), ar.getDistancia());
                    SwingUtilities.invokeLater(() -> repaintSubGrafo(sub));
                    Thread.sleep(800);
                }
                List<String> rutaConPesos = new ArrayList<>();
                
                List<Vertice> verticesCamino = new ArrayList<>();
                Vertice paso = destino;
                while (paso != null) {
                    verticesCamino.add(0, paso);
                    paso = res.previos.get(paso);
                }
                for (Vertice v : verticesCamino) {
                    double pesoAcum = res.distancias.get(v);
                    rutaConPesos.add(v.getNombre() + " (" + (int) pesoAcum + ")");
                }
                String resumen = String.join(" → ", rutaConPesos);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            "Ruta final de " + origenNombre + " a " + destinoNombre + ":\n" + resumen,
                            "Camino más corto (Bellman–Ford)",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });

            } catch (InterruptedException | IllegalArgumentException ex) {
                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this,
                                ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE)
                );
            }

        }).start();
    }

    private void pintarRutaBellmanFordTodas(String origenNombre) {
        new Thread(() -> {
            try {
                GrafoTDA base = new GrafoChiapas().getGrafo();
                Vertice origen = findVertice(base, origenNombre);

                BellmanFord.Resultado res = BellmanFord.ejecutar(base, origen);

                GrafoTDA caminos = BellmanFord.caminoMasCortoTodas(base, origen);
                Set<String> vistos = new HashSet<>();

                List<Vertice> ordenadosBF = new ArrayList<>(caminos.obtenerVertices());
                ordenadosBF.sort(Comparator.comparing(Vertice::getNombre));

                for (Vertice u : ordenadosBF) {
                    List<Arista> ady = new ArrayList<>(caminos.obtenerAdyacentes(u));
                    ady.sort(Comparator
                            .comparing((Arista a) -> a.getDestino().getNombre())
                    );
                    for (Arista a : caminos.obtenerAdyacentes(u)) {
                        String key = a.getOrigen().getNombre() + "-" + a.getDestino().getNombre();
                        String rev = a.getDestino().getNombre() + "-" + a.getOrigen().getNombre();
                        if (vistos.contains(key) || vistos.contains(rev)) {
                            continue;
                        }
                        vistos.add(key);

                        GrafoTDA parcial = new GrafoTDA();
                        base.obtenerVertices().forEach(parcial::agregarVertice);
                        vistos.forEach(k -> {
                            String[] p = k.split("-");
                            Vertice x = findVertice(base, p[0]);
                            Vertice y = findVertice(base, p[1]);
                            double w = caminos.obtenerAdyacentes(x).stream()
                                    .filter(ar -> ar.getDestino().equals(y))
                                    .findFirst().get().getDistancia();
                            parcial.agregarArista(x, y, w);
                        });

                        SwingUtilities.invokeLater(() -> repaintSubGrafo(parcial));
                        Thread.sleep(800);
                    }

                }
                StringBuilder sb = new StringBuilder("Rutas más cortas desde " + origenNombre + ":\n");
                List<Vertice> todos = new ArrayList<>(base.obtenerVertices());
                todos.sort(Comparator.comparing(Vertice::getNombre));
                for (Vertice destino : todos) {
                    if (destino.equals(origen)) {
                        continue;
                    }

                    List<Vertice> sec = new ArrayList<>();
                    Vertice paso = destino;
                    while (paso != null) {
                        sec.add(0, paso);
                        paso = res.previos.get(paso);
                    }
                    List<String> partes = sec.stream()
                            .map(v -> v.getNombre() + " (" + (int) Math.round(res.distancias.get(v)) + ")")
                            .collect(Collectors.toList());

                    sb.append("- ").append(String.join(" → ", partes)).append("\n");
                }

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            sb.toString(),
                            "Caminos más cortos desde " + origenNombre,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private Vertice findVertice(GrafoTDA g, String nombre) {
        return g.obtenerVertices().stream()
                .filter(v -> v.getNombre().equals(nombre))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(nombre));
    }

    private void repaintSubGrafo(GrafoTDA sub) {
        removeCurrentGrafoPanel();
        add(PanelGrafo.obtenerGrafoPintado(sub), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}
