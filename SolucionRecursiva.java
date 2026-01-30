public class SolucionRecursiva {

    private Arbol arbol;

    public SolucionRecursiva(Arbol arbol) {
        this.arbol = arbol;
    }

    public String solucion() {
        if (arbol.getRaiz() == null)
            return "Estructura inválida";
        Arbol cArbol = new Arbol(arbol.getCentroDistribucion(), arbol.getCadena());
        return solucion(cArbol.getRaiz(), new StringBuilder(), 0, 0);
    }

    private String solucion(NodoArbol<String> nodo, StringBuilder r, int nc, int p){
        if (nodo == null)
            return "Ruta: "+r.toString()+"\nNúmero de calles: "+nc+"\nPeso: "+p;
        String valor = nodo.getValor();
        if (valor.equals(arbol.getRaiz().getValor())){
            if (nodo.getIzquierdo() != null){
                r.append(valor);
                return solucion(nodo.getIzquierdo(), r, nc, p);
            }
        }
        if (nodo.getIzquierdo() != null){
            r.append("-->").append(valor);
            nc++;
            return solucion(nodo.getIzquierdo(), r, nc, p);
        }
        if (nodo.getDerecho() != null){
            r.append("-->").append(valor);
            nc++;
            return solucion(nodo.getDerecho(), r, nc, p);
        }
        if (nodoEsNatural(nodo)){
            r.append("-->").append(valor);
            nc++;
            p += Integer.parseInt(valor);
            NodoArbol<String> padre = nodo.getPadre();
            if (padre.getIzquierdo() != null && padre.getIzquierdo().equals(nodo))
                padre.setIzquierdo(null);
            if (padre.getDerecho() != null && padre.getDerecho().equals(nodo))
                padre.setDerecho(null);
            return solucion(padre, r, nc, p);
        }
        if (nodo.getPadre() != null){
            r.append("-->").append(valor);
            nc++;
            NodoArbol<String> padre = nodo.getPadre();
            if (padre.getIzquierdo() != null && padre.getIzquierdo().equals(nodo))
                padre.setIzquierdo(null);
            if (padre.getDerecho() != null && padre.getDerecho().equals(nodo))
                padre.setDerecho(null);
            return solucion(padre, r, nc, p);
        }
        r.append("-->").append(valor);
        nc++;
        return "Ruta: "+r.toString()+"\nNúmero de calles: "+nc+"\nPeso: "+p;
    }

    private static boolean nodoEsNatural(NodoArbol<String> n){
        if(n==null)return false;
        String v=n.getValor();
        if(v==null||v.isEmpty())return false;
        for(int i=0;i<v.length();i++)if(!Character.isDigit(v.charAt(i)))return false;
        return true;
    }


}