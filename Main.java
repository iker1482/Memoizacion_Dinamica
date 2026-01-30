import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File f=new File("arboles.txt");
        String[] dias={"LUN","MART","MIERC","JUE","VIER","SAB","DOM"};
        int i=0;
        try(Scanner n=new Scanner(f);) {
            while (n.hasNext()) {
                System.out.println("EL dia: "+dias[i] +" lo que sucedio fue con la sol recursiva fue: ");
                Arbol diaArbol = new Arbol("A",n.nextLine());
                Arbol carbol = new Arbol(diaArbol.getCentroDistribucion(), diaArbol.getCadena());
                SolucionRecursiva sr = new SolucionRecursiva(diaArbol);
                System.out.println(diaArbol);
                System.out.println(sr.solucion());
                System.out.println("\n");
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
      

    }
}