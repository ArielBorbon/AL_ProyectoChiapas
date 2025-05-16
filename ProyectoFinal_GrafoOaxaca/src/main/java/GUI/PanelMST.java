package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Algoritmos.MST.Boruvka;
import Algoritmos.MST.BoruvkaListener;
import Algoritmos.MST.Kruskal;
import Algoritmos.MST.Prim;
import Implementacion.Arista;
import Implementacion.GrafoTDA;
import Implementacion.Vertice;

public class PanelMST extends JPanel {

    public PanelMST() {
        initComponents();
        mostrarGrafoOriginal();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JButton btnKruskal = Estilos.crearBoton("Usar algoritmo de Kruskal");
        JButton btnPrim = Estilos.crearBoton("Usar algoritmo de Prim");
        JButton btnBoruvka = Estilos.crearBoton("Usar algoritmo de Boruvka");
        JButton btnVolver = Estilos.crearBoton("Volver");

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4, 10, 10));

        panelBotones.add(btnKruskal);
        panelBotones.add(btnPrim);
        panelBotones.add(btnBoruvka);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof MenuPrincipal menu) {
                menu.getContentPane().removeAll();
                menu.add(menu.getPanelMenuLateral(), BorderLayout.WEST);
                menu.add(new PanelDefault(), BorderLayout.CENTER);
                menu.revalidate();
                menu.repaint();
            }
        });

        btnKruskal.addActionListener(e -> pintarMSTKruskal());
        btnBoruvka.addActionListener(e -> pintarMSTBoruvka());
        btnPrim.addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
            ModalSeleccionarFuente modal = new ModalSeleccionarFuente(ventana, true, ModalSeleccionarFuente.FUENTE);
            modal.mostrar();
            Vertice fuente = new Vertice(modal.getCiudadOrigen());
            pintarMSTPrim(fuente);
        });
    }

    private void pintarMSTKruskal() {
        new Thread(() -> {
            try {
                Kruskal kruskal = new Kruskal(new GrafoChiapas().getGrafo());
                kruskal.start();
                while (kruskal.isEjecutando()) {
                    Thread.sleep(1000);
                    GrafoTDA mst = kruskal.getMst();
                    SwingUtilities.invokeLater(() -> mostrarGrafoPintado(mst));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void pintarMSTPrim(Vertice fuente) {
        new Thread(() -> {
            try {
                Prim prim = new Prim(new GrafoChiapas().getGrafo(), fuente);
                prim.start();
                while (prim.isEjecutando()) {
                    Thread.sleep(1000);
                    GrafoTDA mst = prim.getMST();
                    SwingUtilities.invokeLater(() -> mostrarGrafoPintado(mst));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void pintarMSTBoruvka() {
        Boruvka boruvka = new Boruvka(new GrafoChiapas().getGrafo());

        boruvka.setListener(new BoruvkaListener() {
            @Override
            public void onSeleccionAristas(Collection<Arista> seleccionadas) {
                // Opcional: destacar visualmente las aristas seleccionadas antes de agregarlas
            }

            @Override
            public void onActualizaMST(GrafoTDA mstActual) {
                SwingUtilities.invokeLater(() -> mostrarGrafoPintado(mstActual));
            }
        });

        boruvka.start(); // iniciar el algoritmo en un hilo propio
    }

    private void mostrarGrafoPintado(GrafoTDA grafoPintado) {
        JPanel panelGrafo = PanelGrafo.obtenerGrafoPintado(grafoPintado);
        mostrarGrafo(panelGrafo);
    }

    private void mostrarGrafoOriginal() {
        JPanel panelGrafo = PanelGrafo.obtenerPanelGrafo();
        mostrarGrafo(panelGrafo);
    }

    private void mostrarGrafo(JPanel panelGrafo) {
        Component panelCentral = ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (panelCentral != null) {
            remove(panelCentral);
        }

        add(panelGrafo, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
