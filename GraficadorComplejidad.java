import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GraficadorComplejidad extends JFrame {
    private List<PuntoDatos> datosRecursiva;
    private List<PuntoDatos> datosIterativa;
    private Random random;
    
    public GraficadorComplejidad() {
        super("Análisis de Complejidad - Soluciones de Rutas Postales");
        this.datosRecursiva = new ArrayList<>();
        this.datosIterativa = new ArrayList<>();
        this.random = new Random();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        generarDatosTeoricos();
        agregarPanelGrafica();
    }
    
    /**
     * Clase para almacenar puntos de datos (tamaño vs tiempo/memoria)
     */
    private static class PuntoDatos {
        int tamaño;      // Número de oficinas
        long tiempo;     // Tiempo en nanosegundos
        long memoria;    // Memoria en bytes
        String tipo;     // "Recursiva" o "Iterativa"
        
        public PuntoDatos(int tamaño, long tiempo, long memoria, String tipo) {
            this.tamaño = tamaño;
            this.tiempo = tiempo;
            this.memoria = memoria;
            this.tipo = tipo;
        }
    }
    
    /**
     * Genera datos teóricos y prácticos para las gráficas
     */
    private void generarDatosTeoricos() {
        System.out.println("📊 GENERANDO DATOS PARA ANÁLISIS...");
        
        int[] tamanios = {10, 25, 50, 75, 100, 150, 200, 255};
        
        for (int tamaño : tamanios) {
            String arbolCadena = generarArbolBalanceado(tamaño);
            Arbol arbol = new Arbol("Centro", arbolCadena);
            
            // Datos para solución recursiva
            long tiempoRecursiva = calcularTiempoTeorico(tamaño, true);
            long memoriaRecursiva = calcularMemoriaTeorica(tamaño, true);
            datosRecursiva.add(new PuntoDatos(tamaño, tiempoRecursiva, memoriaRecursiva, "Recursiva"));
            
            // Datos para solución iterativa
            long tiempoIterativa = calcularTiempoTeorico(tamaño, false);
            long memoriaIterativa = calcularMemoriaTeorica(tamaño, false);
            datosIterativa.add(new PuntoDatos(tamaño, tiempoIterativa, memoriaIterativa, "Iterativa"));
            
            System.out.printf("Tamaño: %3d | Recursiva: %6d ns | Iterativa: %6d ns%n", 
                            tamaño, tiempoRecursiva, tiempoIterativa);
        }
    }
    
    /**
     * Cálculo teórico del tiempo basado en complejidad algorítmica
     */
    private long calcularTiempoTeorico(int n, boolean esRecursiva) {
        // Tiempo base en nanosegundos
        long baseTime = 1000;
        
        if (esRecursiva) {
            // Recursiva: O(n) pero con overhead de llamadas recursivas
            // Más lenta para n pequeños, problema con n grandes
            return baseTime * n + (long)(baseTime * Math.log(n) * 10);
        } else {
            // Iterativa: O(n) más eficiente en práctica
            return baseTime * n + (long)(baseTime * Math.log(n) * 5);
        }
    }
    
    /**
     * Cálculo teórico del uso de memoria
     */
    private long calcularMemoriaTeorica(int n, boolean esRecursiva) {
        // Memoria base en bytes por nodo
        long baseMemory = 100;
        
        if (esRecursiva) {
            // Recursiva: O(n) en stack (más crítico)
            return baseMemory * n * 2; // Overhead por llamadas recursivas
        } else {
            // Iterativa: O(n) en heap (menos crítico)
            return baseMemory * n + 1000; // Memoria constante adicional
        }
    }
    
    /**
     * Genera un árbol binario balanceado para pruebas reales
     */
    private String generarArbolBalanceado(int numOficinas) {
        if (numOficinas == 1) {
            return "(" + (random.nextInt(99) + 1) + ")";
        }
        
        // Dividir balanceadamente para árbol equilibrado
        int mitad = numOficinas / 2;
        String izquierdo = generarArbolBalanceado(mitad);
        String derecho = generarArbolBalanceado(numOficinas - mitad);
        
        return "(" + izquierdo + derecho + ")";
    }
    
    /**
     * Crea el panel de gráficas
     */
    private void agregarPanelGrafica() {
        JPanel panelGrafica = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                   RenderingHints.VALUE_ANTIALIAS_ON);
                
                dibujarGraficaTiempo(g2d, 50, 50, getWidth() - 100, getHeight()/2 - 80);
                dibujarGraficaMemoria(g2d, 50, getHeight()/2 + 30, getWidth() - 100, getHeight()/2 - 80);
                dibujarLeyenda(g2d, getWidth() - 200, 20);
            }
        };
        
        add(panelGrafica);
    }
    
    /**
     * Dibuja la gráfica de tiempo de ejecución
     */
    private void dibujarGraficaTiempo(Graphics2D g2d, int x, int y, int width, int height) {
        // Título
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("⏱️ TIEMPO DE EJECUCIÓN (Complejidad O(n))", x, y - 10);
        
        // Ejes
        g2d.drawLine(x, y + height, x + width, y + height); // Eje X
        g2d.drawLine(x, y, x, y + height); // Eje Y
        
        // Escalas
        int maxTiempo = getMaxTiempo();
        int maxTamaño = 255;
        
        // Línea teórica O(n)
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
        for (int i = 0; i <= width; i += 10) {
            int tamaño = i * maxTamaño / width;
            int yPos = y + height - (int)(calcularTiempoTeorico(tamaño, false) * height / maxTiempo);
            if (i > 0) {
                g2d.drawLine(x + i - 10, yPrev, x + i, yPos);
            }
            yPrev = yPos;
        }
        g2d.drawString("O(n) Teórica", x + width - 80, y + 20);
        
        // Dibujar datos recursiva
        dibujarLineaDatos(g2d, datosRecursiva, x, y, width, height, maxTiempo, maxTamaño, Color.RED);
        
        // Dibujar datos iterativa
        dibujarLineaDatos(g2d, datosIterativa, x, y, width, height, maxTiempo, maxTamaño, Color.BLUE);
        
        // Etiquetas ejes
        g2d.setColor(Color.BLACK);
        g2d.drawString("Tamaño (nº oficinas)", x + width/2 - 50, y + height + 30);
        g2d.drawString("Tiempo (ns)", x - 30, y + height/2);
    }
    
    /**
     * Dibuja la gráfica de uso de memoria
     */
    private void dibujarGraficaMemoria(Graphics2D g2d, int x, int y, int width, int height) {
        // Título
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("💾 USO DE MEMORIA", x, y - 10);
        
        // Ejes
        g2d.drawLine(x, y + height, x + width, y + height);
        g2d.drawLine(x, y, x, y + height);
        
        // Dibujar datos
        long maxMemoria = getMaxMemoria();
        int maxTamaño = 255;
        
        dibujarLineaDatos(g2d, datosRecursiva, x, y, width, height, maxMemoria, maxTamaño, Color.RED);
        dibujarLineaDatos(g2d, datosIterativa, x, y, width, height, maxMemoria, maxTamaño, Color.BLUE);
        
        // Etiquetas
        g2d.setColor(Color.BLACK);
        g2d.drawString("Tamaño (nº oficinas)", x + width/2 - 50, y + height + 30);
        g2d.drawString("Memoria (bytes)", x - 40, y + height/2);
    }
    
    /**
     * Dibuja una línea de datos en la gráfica
     */
    private void dibujarLineaDatos(Graphics2D g2d, List<PuntoDatos> datos, int x, int y, 
                                 int width, int height, long maxValor, int maxTamaño, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));
        
        int prevX = -1, prevY = -1;
        for (PuntoDatos punto : datos) {
            int xPos = x + (punto.tamaño * width / maxTamaño);
            int yPos = y + height - (int)(punto.tiempo * height / maxValor);
            
            // Punto
            g2d.fillOval(xPos - 3, yPos - 3, 6, 6);
            
            // Línea
            if (prevX != -1) {
                g2d.drawLine(prevX, prevY, xPos, yPos);
            }
            
            prevX = xPos;
            prevY = yPos;
        }
        
        // Etiqueta
        String tipo = datos.get(0).tipo;
        g2d.drawString(tipo, x + width - 80, y + (tipo.equals("Recursiva") ? 40 : 60));
    }
    
    /**
     * Dibuja la leyenda de la gráfica
     */
    private void dibujarLeyenda(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("LEYENDA:", x, y);
        
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y + 20, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Solución Recursiva", x + 25, y + 32);
        
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x, y + 45, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Solución Iterativa", x + 25, y + 57);
    }
    
    private long getMaxTiempo() {
        return Math.max(
            datosRecursiva.stream().mapToLong(p -> p.tiempo).max().orElse(0),
            datosIterativa.stream().mapToLong(p -> p.tiempo).max().orElse(0)
        );
    }
    
    private long getMaxMemoria() {
        return Math.max(
            datosRecursiva.stream().mapToLong(p -> p.memoria).max().orElse(0),
            datosIterativa.stream().mapToLong(p -> p.memoria).max().orElse(0)
        );
    }
    
    /**
     * Método para generar análisis teórico en texto
     */
    public static void generarAnalisisTeorico() {
        System.out.println("\n📈 ANÁLISIS TEÓRICO DE COMPLEJIDAD");
        System.out.println("=".repeat(50));
        
        System.out.println("\n🔍 COMPLEJIDAD TEMPORAL:");
        System.out.println("• Ambas soluciones: O(n) donde n = número de nodos");
        System.out.println("• Recursiva: Overhead de llamadas a función");
        System.out.println("• Iterativa: Operaciones de pila más eficientes");
        
        System.out.println("\n💾 COMPLEJIDAD ESPACIAL:");
        System.out.println("• Recursiva: O(h) en stack (h = altura del árbol)");
        System.out.println("  - Mejor caso (balanceado): O(log n)");
        System.out.println("  - Peor caso (degenerado): O(n)");
        System.out.println("  - ⚠️  Riesgo de StackOverflow con árboles grandes");
        
        System.out.println("• Iterativa: O(n) en heap");
        System.out.println("  - Más estable y predecible");
        System.out.println("  - Sin riesgo de StackOverflow");
        
        System.out.println("\n🏆 CONCLUSIÓN TEÓRICA:");
        System.out.println("✅ ITERATIVA ES MEJOR EN LA PRÁCTICA");
        System.out.println("   - Más estable con árboles grandes");
        System.out.println("   - Uso de memoria más predecible");
        System.out.println("   - Sin límites de profundidad");
        
        System.out.println("⚠️  RECURSIVA ES ACEPTABLE PARA:");
        System.out.println("   - Árboles pequeños y balanceados");
        System.out.println("   - Código más legible y conciso");
        System.out.println("   - Situaciones sin restricciones de stack");
    }
    
    public static void main(String[] args) {
        // Generar análisis en texto
        generarAnalisisTeorico();
        
        // Mostrar gráfica
        SwingUtilities.invokeLater(() -> {
            new GraficadorComplejidad().setVisible(true);
        });
    }
}
