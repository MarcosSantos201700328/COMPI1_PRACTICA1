/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generacion_siguientes_tablas_automatas;

/**
 *
 * @author marco
 */
public class Tabla_raices {
    
    private String id;
    private Nodo raiz;

    public Tabla_raices(String id, Nodo raiz) {
        this.id = id;
        this.raiz = raiz;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the raiz
     */
    public Nodo getRaiz() {
        return raiz;
    }
        
}
