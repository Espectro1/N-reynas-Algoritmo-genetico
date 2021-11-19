/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nreynas;

/**
 *
 * @author Anima
 */
public class Cromosoma {
    private int[] cromosoma;
    private int numColiciones;
    private int numParesNoAtacantes;
    private int posicionCorte;
    private float fitness;
    private float porcentaje;
    public int getPosicionCorte() {
        return posicionCorte;
    }

    public void setPosicionCorte(int posicionCorte) {
        this.posicionCorte = posicionCorte;
    }
   
    

    public int getNumParesNoAtacantes() {
        return numParesNoAtacantes;
    }

    public void setNumParesNoAtacantes(int numParesNoAtacantes) {
        this.numParesNoAtacantes = numParesNoAtacantes;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }
   
    public  Cromosoma(){
        
    }

    public int[] getCromosoma() {
        return cromosoma;
    }

    public void setCromosoma(int[] cromosoma) {
        this.cromosoma = cromosoma;
    }

    public int getNumColiciones() {
        return numColiciones;
    }

    public void setNumColiciones(int numColiciones) {
        this.numColiciones = numColiciones;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    
    
    
}
