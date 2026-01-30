public class NodoArbol<T> {
    T valor;
    NodoArbol<T> izquierdo;
    NodoArbol<T> derecho;
    NodoArbol<T> padre;  // 🔹 referencia al padre

    // Constructor
    public NodoArbol(T valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
        this.padre = null;
    }

    // Getters y setters
    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public NodoArbol<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol<T> izquierdo) {
        this.izquierdo = izquierdo;
        if (izquierdo != null) {
            izquierdo.setPadre(this); // asigna este como padre
        }
    }

    public NodoArbol<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol<T> derecho) {
        this.derecho = derecho;
        if (derecho != null) {
            derecho.setPadre(this); // asigna este como padre
        }
    }

    public NodoArbol<T> getPadre() {
        return padre;
    }

    public void setPadre(NodoArbol<T> padre) {
        this.padre = padre;
    }

    @Override
    public boolean equals(Object o){
        return this==o;
    }

}