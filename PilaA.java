import java.util.NoSuchElementException;

/** 
 
 * Clase que implementa una pila genérica usando un arreglo genérico.
 */
public class PilaA <T> implements PilaADT <T>{
    private T colec[];
    private int tope;
    private final int MAX = 10;

    /* Se construye un arreglo de objetos y se lo convierte explícitamente a tipo T.
     * Inicialmente la pila está vacía, lo que se indica con tope igual a -1.
     */
    public PilaA() {
        colec = (T[]) (new Object[MAX]);
        tope = -1;
    }
    
    /* Se construye un arreglo de objetos y se lo convierte explícitamente a tipo T.
     * El espacio máximo reservado queda determinado por el parámetro max.
     * Inicialmente la pila está vacía, lo que se indica con tope igual a -1.
     */
    public PilaA(int max) {
        colec = (T[]) (new Object[max]);
        tope = -1;
    }
    
    /* Agrega el dato en el tope, redefiniendo el valor de éste. Si la pila está llena, 
     * se construye un arreglo de mayor capacidad y se copian los elementos de la pila 
     * a éste.
     */
    public void push(T dato) {
        if (tope == colec.length - 1) 
            aumentaCapacidad();
        tope++;
        colec[tope] = dato;
    }

    /* Elimina y regresa el elemento que está en el tope de la pila, redefiniendo el valor 
     * del tope. Si la pila está vacía lanza una excepción.
     */
    public T pop() {
        if (isEmpty())
            throw new NoSuchElementException("Pila vacía");
        else{
            T dato = colec[tope];
            tope--;
            return dato;
        }
    }
    
    /* Regresa el elemento que está en el tope.
     * Si la pila está vacía lanza una excepción.
     */
    public T peek() { 
        if (isEmpty())
            throw new NoSuchElementException("Pila vacía");
        else
            return colec[tope];
    }
    
    // Regresa true si la pila está vacía.
    public boolean isEmpty() {
        return tope == -1;
    }
    
    /* Método auxiliar que construye un arreglo de mayor tamaño y copia en él todos
     * los elementos de la pila, asignando al arreglo colec la referencia del nuevo
     * arreglo.
     */
    private void aumentaCapacidad(){
        T nuevo[] = (T[]) (new Object[colec.length * 2]);
        int i;
        
        for (i= 0; i <= tope; i++)
            nuevo[i] = colec[i];        
        colec = nuevo;
    }
}
