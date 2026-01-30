public class Arbol {

    private String cadena;
    private String centroDistribucion;
    private NodoArbol <String> raiz;

    // Constructor del árbol a partir de la cadena
    public Arbol(String centroDistribucion, String cadena) {
        this.cadena = cadena;
        this.centroDistribucion = centroDistribucion;
        if (estructuraArbolValida(cadena))
            this.raiz = constructorArbol(cadena, 0, new NodoArbol<String> (centroDistribucion));
        else 
            this.raiz = null;
    }

    public String getCadena() {
        return cadena;
    }

    public String getCentroDistribucion() {
        return centroDistribucion;
    }

    public NodoArbol<String> getRaiz() {
        return raiz;
    }

    // Checa si la cadena solo contiene dígitos y paréntesis
    private static boolean soloDigitosYParentesis(String s){
        if(s==null)return false;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(!(c=='('||c==')'||Character.isDigit(c)))return false;
        }
        return true;
    }

    // Checa si los paréntesis están balanceados
    private static boolean parentesisBalanceados(String s){
        if(s==null)return false;
        int bal=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c=='(')bal++;
            else if(c==')'){
                if(bal==0)return false;
                bal--;
            }
        }
        return bal==0;
    }

    // Checa si los números naturales en la cadena tienen máximo dos dígitos
    private static boolean naturalesMaxDosDigitos(String s){
        if(s==null)return false;
        int n=s.length(),i=0;
        while(i<n){
            char c=s.charAt(i);
            if(Character.isDigit(c)){
                int start=i;
                while(i<n&&Character.isDigit(s.charAt(i)))i++;
                if(i-start>2)return false;
                continue;
            }
            i++;
        }
        return true;
    }

    // Checa si la estructura de la cadena es válida para construir un árbol
    public static boolean estructuraArbolValida(String s){
        if(s==null)return false;
        if(!soloDigitosYParentesis(s))return false;
        if(!parentesisBalanceados(s))return false;
        if(!naturalesMaxDosDigitos(s))return false;
        int[] i={0};
        int top=0;
        while(i[0]<s.length()){
            if(s.charAt(i[0])!='(')return false;
            if(!parseNodo(s,i))return false;
            top++;
            if(top>2)return false;
        }
        return top>=1&&top<=2;
    }

    // Checa que no hayan más de dos hijos por "padre"
    private static boolean parseNodo(String s,int[] iref){
        int n=s.length();
        int i=iref[0];
        if(i>=n||s.charAt(i)!='(')return false;
        i++;
        if(i<n&&Character.isDigit(s.charAt(i))){
            int start=i;
            while(i<n&&Character.isDigit(s.charAt(i)))i++;
            if(start==i)return false;
            if(i>=n||s.charAt(i)!=')')return false;
            i++;
            iref[0]=i;
            return true;
        }else{
            iref[0]=i;
            if(!parseNodo(s,iref))return false;
            i=iref[0];
            if(i>=n)return false;
            if(s.charAt(i)!='('){
                if(s.charAt(i)==')')return false;
                return false;
            }
            if(!parseNodo(s,iref))return false;
            i=iref[0];
            if(i>=n||s.charAt(i)!=')')return false;
            i++;
            iref[0]=i;
            return true;
        }
    }

    // Construye el árbol recursivamente
    public NodoArbol<String> constructorArbol(String cadena, int i, NodoArbol<String> nodo) {
        if (i == cadena.length()){
            while (nodo.getPadre() != null)
                nodo = nodo.getPadre();
            return nodo;
        }
        char c = cadena.charAt(i);
        if (c == '('){
            NodoArbol<String> nuevo = new NodoArbol<String>("("+i+")");
            if (Character.isDigit(cadena.charAt(i+1)))
                return constructorArbol(cadena, i + 1, nodo);
            if (nodo.getIzquierdo() == null)
                nodo.setIzquierdo(nuevo);
            else
                if (nodo.getDerecho() == null)
                    nodo.setDerecho(nuevo);
                else 
                    return constructorArbol(cadena, i + 1, nodo.getPadre());
            return constructorArbol(cadena, i + 1, nuevo);
        }
        if (Character.isDigit(c)){
            int n = cadena.length();
            int start = i;
            while (i < n && Character.isDigit(cadena.charAt(i))) {
                i++;
            }
            String numero = cadena.substring(start, i);
            NodoArbol<String> nuevo = new NodoArbol<String>(numero);
            if (nodo.getIzquierdo() == null)
                nodo.setIzquierdo(nuevo);
            else
                if (nodo.getDerecho() == null)
                    nodo.setDerecho(nuevo);
                else 
                    return constructorArbol(cadena, i + 1, nodo.getPadre());
            return constructorArbol(cadena, i + 1, nodo);
        }
        if (c == ')')
            return constructorArbol(cadena, i + 1, nodo.getPadre());
        return null;
    }

    // Imprime el árbol en formato texto
    @Override
    public String toString() {
        if (estructuraArbolValida(cadena) == false)
            return "Cadena inválida para construir un árbol.";
        return dibujar(raiz, "", true);
    }

    // Dibuja el árbol en formato texto
    private String dibujar(NodoArbol nodo, String prefijo, boolean esUltimo) {
        if (nodo == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(prefijo);
        sb.append(esUltimo ? "└── " : "├── ");
        sb.append(nodo.valor).append("\n");
        String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
        if (nodo.izquierdo != null || nodo.derecho != null) {
            if (nodo.izquierdo != null) {
                sb.append(dibujar(nodo.izquierdo, nuevoPrefijo, nodo.derecho == null));
            }
            if (nodo.derecho != null) {
                sb.append(dibujar(nodo.derecho, nuevoPrefijo, true));
            }
        }
        return sb.toString();
    }

}