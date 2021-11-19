package nreynas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Anima
 */
public class Nreynas {

    /**
     * @param args the command line arguments
     */
     static int numGeneracion = 0;
     static int n=0;
     static int probabilidadDeMutacion =0;
     static ArrayList<Cromosoma> nuevaPoblacion;
     static Cromosoma cromosomaCorrecto = new Cromosoma();

    public static void main(String[] args) {
        int poblacionIncial;
      
        System.out.println("");
        Scanner tc = new Scanner(System.in);
       
        System.out.println("Tamaño del tablero: ");
        n =  tc.nextInt();
        
        System.out.println("Poblacion inicial: ");
        poblacionIncial =  tc.nextInt();
        int poblacion[][] = poblacion(poblacionIncial,n);
        
        System.out.println("Probabilidad de mutacion 1  -  100");
        probabilidadDeMutacion = tc.nextInt();
        
        
        System.out.println("Poblacion Inicial");
        imprimirMatriz(poblacion);
        
       
        fitness(poblacion);
    }
    
    
    public static int  getColisiones(int[] cromosoma, int n){
        int contenido, colisiones=0, columnaInterior=0 ;
         for (int i = 0; i < n; i++) { // columnas1
             contenido = cromosoma[i];
             for (int j = i+1; j < n; j++) {  // columnas2
                 columnaInterior++;
                 if(contenido+columnaInterior == cromosoma[j]){
                     colisiones++;
                 }
                 if(contenido-columnaInterior == cromosoma[j]){
                     colisiones++;
                 }
                 if(contenido == cromosoma[j]){
                    colisiones++;   
                 }
             }
             columnaInterior=0;
        }
        return colisiones;
    }
       
     
    
    public static void fitness(int[][] poblacion){ //calculara el fitness del cromosoma
        // para eso primero checamos la cantidad de colisiones que tienen las reynas
        boolean solucion;
        int  primeraVez= 0;
        int filas = poblacion.length;
        int columnas = poblacion[0].length;
        boolean hijoCorrecto = false;
        int [] arrayCromosoma ;
        int numParesQueNoSeAtacan = 0;
        int max = (columnas * (columnas-1)/2 ) ,min= 0;
        float porcentaje = 0;
        
        int numColisiones = 0;

  
         System.out.println("Tamaño columnas "+ columnas + " filas " + filas);
         ArrayList<Cromosoma> cromosomas= new ArrayList<>();

         
         for (int i = 0; i < filas; i++) {
                 arrayCromosoma  = new int[columnas];
             for (int j = 0; j < columnas; j++) {
                 arrayCromosoma[j]= poblacion[i][j]; // sacamos los cromosomas de la poblacion    
             }

                 numColisiones = getColisiones(arrayCromosoma, n); // calculamos las coliciones
                 if(numColisiones == 0){
                     System.out.println("Se encontro una solucion : "  );
                     hijoCorrecto = true;
                     solucion = true;
                     break;
                 }
                 System.out.println("Array del cromosoma: "+ Arrays.toString(arrayCromosoma));
                 numParesQueNoSeAtacan = max - numColisiones;
                 System.out.println("numero de pares que no se atacan "+ numParesQueNoSeAtacan);
                 Cromosoma cromosoma = new Cromosoma();  // creamos el cromosoma que contendra la info para el fitness;
                 
                 cromosoma.setNumColiciones(numColisiones);
                 cromosoma.setNumParesNoAtacantes(numParesQueNoSeAtacan);
                 cromosoma.setCromosoma(arrayCromosoma);
                 
                 cromosomas.add(cromosoma);

                 porcentaje+= numParesQueNoSeAtacan;         
        }
         System.out.println("");
         if(hijoCorrecto == false){

        
        // se calcula el fitness
        for (Cromosoma cromosoma : cromosomas) {
            cromosoma.setPorcentaje((cromosoma.getNumParesNoAtacantes()/porcentaje) * 100);
             System.out.println("Porcentaje: "+ cromosoma.getPorcentaje());
         }

        System.out.println("\n");
       // se hace el proceso de seleccion
       solucion = seleccion(cromosomas);
       
      
      }else{
             solucion = true;
             System.out.println("Se encontro la solucion x2");
         }
         
         primeraVez = 1;
     while((solucion == false && primeraVez>0)){

         porcentaje= 0;
         for(Cromosoma cromosoma: nuevaPoblacion){
              // se calcula el fitness
             numColisiones = getColisiones(cromosoma.getCromosoma(), n); // calculamos las coliciones
             cromosoma.setNumColiciones(numColisiones);
             numParesQueNoSeAtacan = max - numColisiones;
             cromosoma.setNumParesNoAtacantes(numParesQueNoSeAtacan);
             System.out.println("numero de pares que no se atacan: "+ numParesQueNoSeAtacan);
             porcentaje+= numParesQueNoSeAtacan;
         }
         
         // se agrega el porcentaje
         for (Cromosoma cromosoma : nuevaPoblacion) {
             cromosoma.setPorcentaje((cromosoma.getNumParesNoAtacantes()/porcentaje) * 100);
             System.out.println("Porcentaje: "+ cromosoma.getPorcentaje());
         }
         
         solucion = seleccion(nuevaPoblacion);
         System.out.println("-");
         if(solucion == true){
             System.out.println("Se encontro la solucion");
             System.out.println(Arrays.toString(cromosomaCorrecto.getCromosoma()));
             System.out.println("Numero de coliciones: "+ cromosomaCorrecto.getNumParesNoAtacantes());
             System.out.println("Numero de pares que no se atacan "+ cromosomaCorrecto.getNumParesNoAtacantes());
         }
         }
        
    }
    
    public static int [][] poblacion(int poblacionInicial, int tamañoTablero){
        int poblacion [][] = new int [poblacionInicial][tamañoTablero];
        int locus;  //elemento del vector
        for (int i = 0; i < poblacionInicial; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                 locus = (int)(Math.random()*tamañoTablero+1);
                 poblacion[i][j] = locus;
            }    
        }
        return poblacion;
        
    }
  
      public static int[][] poblacionNueva(ArrayList<Cromosoma> hijos){
          int columnas =  hijos.get(0).getCromosoma().length;
          int filas = hijos.size();
         int poblacion[][] = new int[filas][columnas];
         int[] cromosoma;
          System.out.println("filas: "+ filas + " columnas : "+ columnas);
          for (int i = 0; i < filas; i++) {
              cromosoma = hijos.get(i).getCromosoma();
               for (int j = 0; j < columnas; j++) {
                  poblacion[i][j] = cromosoma[j];
              }
          }
         return poblacion;
        
    }

      
    public static boolean seleccion(ArrayList<Cromosoma> poblacion){

          int poblacionActual = poblacion.size(); // necesitamos saber la poblacion para escojer el fitness
          float probabilidad , probabilidadAcumulada=0;
          int numeroHijosEscojidos=0;
          int[] auxiliarCromosomaX={0,0,0,0,0,0,0,0};
          int[] auxiliarCromosomaY={0,0,0,0,0,0,0,0};
          ArrayList<Cromosoma> padres = new ArrayList();
          Cromosoma padreX = new Cromosoma();
          Cromosoma padreY = new Cromosoma();
          ArrayList<Cromosoma> hijos = new ArrayList();
          Cromosoma cromosomaH1, cromosomaH2;
           int i=0;
        int[] cromosomaHijo1, cromosomaHijo2;
        int tamañoTablero = poblacion.get(0).getCromosoma().length;
        int numAleatorio;
        int probabilidadMutacion ; 
        int numColisionesH1;
        int numColisionesH2;
        Cromosoma hijoMasFuerte = new Cromosoma();
        int numeroAeloMutacion; // para elegir la posicion de la mutacion
          
          
          System.out.println("poblacionActual:" + poblacionActual);
         while(numeroHijosEscojidos < poblacionActual){  // si por ejemplo tenemos una poblacion de 4, entonces necesitamos tener 4 padres elegidos para la reproduccion

               probabilidad = (float) Math.random()*100; // escogemos un numero del 1 al 100      
              
               for (Cromosoma cromosoma : poblacion) {
                   probabilidadAcumulada += cromosoma.getPorcentaje();
                    
                    if(probabilidad <= probabilidadAcumulada && !Arrays.equals(auxiliarCromosomaX, cromosoma.getCromosoma())){ // si el numero aleatoria es menor o igual a el procentaje del cromosoma, entonces escogelo como padre   
                     padres.add(cromosoma);
                      padreX = cromosoma;
                      auxiliarCromosomaX= cromosoma.getCromosoma();

                      break;
                    }
                     
              }
               probabilidadAcumulada = 0;
                probabilidad = (float) Math.random()*100; // escogemos un numero del 1 al 100   
               for (Cromosoma cromosoma : poblacion) {
                   probabilidadAcumulada += cromosoma.getPorcentaje();
                    
                    if(probabilidad <= probabilidadAcumulada && !Arrays.equals(auxiliarCromosomaY, cromosoma.getCromosoma())){ // si el numero aleatoria es menor o igual a el procentaje del cromosoma, entonces escogelo como padre   
                      padres.add(cromosoma);
                      padreY = cromosoma;
                      auxiliarCromosomaY= cromosoma.getCromosoma();

                      break;
                    }    
              }
               
             cromosomaHijo1 = new int[tamañoTablero];
             cromosomaHijo2 = new int[tamañoTablero];
             System.out.println("");

                 numAleatorio = (int) (Math.random()*(tamañoTablero-1)+1); // numero del 1 a el tamaño del tablero para la seleccion
                 if(numAleatorio == tamañoTablero-1){
                     numAleatorio-=1;
                 }
                 System.out.println("Numero de corte: "+ numAleatorio);
                 int  desde, hasta;
                 desde= 0; hasta=numAleatorio;
                 //desde 0 hasta el numero aleatorio
                 for (int k = desde; k < hasta; k++) {
                    cromosomaHijo1[k]= padreX.getCromosoma()[k];
                    cromosomaHijo2[k]= padreY.getCromosoma()[k];
                  }
                 //desde numero aleatorio hasta donde termine el arreglo
                  desde= numAleatorio; hasta=tamañoTablero;
                 for (int k = desde; k < hasta; k++) {
                     cromosomaHijo1[k]= padreY.getCromosoma()[k];
                     cromosomaHijo2[k]= padreX.getCromosoma()[k];
                   }
                 
              
                  cromosomaH1 = new Cromosoma();
                  cromosomaH2 = new Cromosoma();
                 System.out.println("");
                 
                 numColisionesH1= getColisiones(cromosomaHijo1, n);  // veo el numero de coliciones
                 numColisionesH2 = getColisiones(cromosomaHijo2, n);  // veo el numero de coliciones
                 
                 if(numColisionesH1 == 0){
                            System.out.println("Hijo1 de seleccion");
                            cromosomaCorrecto.setCromosoma(cromosomaHijo1);
                            cromosomaCorrecto.setNumColiciones(numColisionesH1);
                            return true;
                    }   
                 if(numColisionesH2 == 0){
                     System.out.println("Hijo2 de seleccion");
                     cromosomaCorrecto.setCromosoma(cromosomaHijo2);
                     cromosomaCorrecto.setNumColiciones(numColisionesH2);
                     return true;
                 }
                 
                 if(numColisionesH1 > numColisionesH2){
                     cromosomaH2.setCromosoma(cromosomaHijo2);
                     cromosomaH1.setCromosoma(cromosomaHijo1);
                        System.out.println("Hijo mas fuerte: " + Arrays.toString(cromosomaH2.getCromosoma())   + " Num Coliciones: " + numColisionesH2);
                        System.out.println("Hijo mas debil: " + Arrays.toString(cromosomaH1.getCromosoma())   + " Num Coliciones: " + numColisionesH1);
                        
                        hijoMasFuerte = cromosomaH2;
                        numeroHijosEscojidos++;
                        
                 }else{
                     cromosomaH2.setCromosoma(cromosomaHijo2);
                     cromosomaH1.setCromosoma(cromosomaHijo1);
                     System.out.println("Hijo mas fuerte: " + Arrays.toString(cromosomaH1.getCromosoma()) + " Num Coliciones: "+ numColisionesH1);
                     System.out.println("Hijo mas debil: " + Arrays.toString(cromosomaH2.getCromosoma())   + " Num Coliciones: " + numColisionesH2);
                     hijoMasFuerte = cromosomaH1;
                      numeroHijosEscojidos++;
                 }
                     
                probabilidadMutacion  = (int)(Math.random()*100+1); // 4% porciento de probabilidad de mutacion

                    if(probabilidadMutacion <= probabilidadDeMutacion){
                        
                           numeroAeloMutacion = (int)(Math.random()*(tamañoTablero-1));
                          
                           System.out.println("Numero de mutacion: ----------------------------------------------------------------------- "+ numeroAeloMutacion);
                           hijoMasFuerte.getCromosoma()[numeroAeloMutacion] = (int)(Math.random()*tamañoTablero+1);
                           
                           System.out.println("Se muto el hijo: " + Arrays.toString(hijoMasFuerte.getCromosoma()));
                           hijos.add(hijoMasFuerte);
                           
                    }else{
                      hijos.add(hijoMasFuerte);
                    }                
               
              probabilidadAcumulada = 0;
               
         } // Fin del while
         System.out.println("");
         System.out.println("Padres escogidos: ");
         for(Cromosoma cromosoma: padres){
             System.out.println(Arrays.toString(cromosoma.getCromosoma()));
         }
         System.out.println("");
         
         System.out.println("Hijos");
         for(Cromosoma hijo: hijos){
             System.out.println(" ---- "+ Arrays.toString(hijo.getCromosoma()) + " ----" );        
         }
         System.out.println("");
         
         System.out.println("");
         System.out.println("---------  Poblacion nueva  -----------");
         numGeneracion++;
         System.out.println("Generacion numero: " + numGeneracion);
         System.out.println("");
         imprimirMatriz(poblacionNueva(hijos));
         nuevaPoblacion = hijos;
         
         return false;
    }
    
    
       public static void imprimirMatriz(int[][] matriz){
        for (int x=0; x < matriz.length; x++) {
            System.out.print("|");
            for (int y=0; y < matriz[x].length; y++) {
              System.out.print (matriz[x][y]);
              if (y!=matriz[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
        }
    }
    
    public static int[][] generarMatriz(int tamaño){
        int matriz [][] = new int [tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                 matriz[i][j] = 0;
            }
        }
       return matriz;
    }
}
