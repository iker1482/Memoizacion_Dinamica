

/**
 
 * Se define el comportamiento esperado de una pila a través de una interface.
 */
public interface PilaADT <T>{
    public T pop(); // Debe quitar el elemento que está en el tope y regresarlo.
    public void push(T dato); // Agrega el dato en el tope de la pila.
    public T peek(); // Regresa el elemento que está en el tope, sin quitarlo.
    public boolean isEmpty(); // Regresa true si la pila no tiene elementos.  
}
