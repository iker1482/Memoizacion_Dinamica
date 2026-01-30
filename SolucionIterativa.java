public class SolucionIterativa {


    public String solucion(Arbol arbol) {
        if (arbol.getRaiz() == null) return "Estructura inválida";
        
        PilaA<NodoArbol<String>> pila = new PilaA<>();
        // Pila para recordar el camino de regreso
        PilaA<String> caminoRegreso = new PilaA<>(); 
        StringBuilder ruta = new StringBuilder();
        int pesoTotal = 0;
        int callesVisitadas = 0;
        
        NodoArbol<String> actual = arbol.getRaiz();
        ruta.append(actual.getValor()); // Empezar desde el centro
        
        // Recorrido DFS iterativo completo
        while (actual != null || !pila.isEmpty()) {
            // Avanzar todo a la izquierda primero
            while (actual != null) {
                if (actual != arbol.getRaiz()) {
                    ruta.append(" -> ").append(actual.getValor());
                    callesVisitadas++;
                }
                
                if (esOficina(actual)) {
                    pesoTotal += Integer.parseInt(actual.getValor());
                }
                
                pila.push(actual);
                caminoRegreso.push(actual.getValor());
                actual = actual.getIzquierdo(); // Priorizar izquierda
            }
            
            // Retroceder y tomar derecha
            if (!pila.isEmpty()) {
                actual = pila.pop();
                String nodoRegreso = caminoRegreso.pop();
                
                // Solo agregar regreso si no es la raíz
                if (actual != arbol.getRaiz() && !caminoRegreso.isEmpty()) {
                    ruta.append(" -> ").append(caminoRegreso.peek());
                    callesVisitadas++;
                }
                
                actual = actual.getDerecho();
            }
        }
        
        return "Ruta: " + ruta.toString() + 
               "\nNúmero de calles: " + callesVisitadas + 
               "\nPeso: " + pesoTotal;
    }

    private boolean esOficina(NodoArbol<String> nodo) {
        if (nodo == null) return false;
        String valor = nodo.getValor();
        // Una oficina es un nodo hoja con valor numérico
        return nodo.getIzquierdo() == null && 
               nodo.getDerecho() == null && 
               valor.matches("\\d+");
    }
}
