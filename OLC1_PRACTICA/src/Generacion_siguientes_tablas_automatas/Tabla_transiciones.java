/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generacion_siguientes_tablas_automatas;

import java.util.LinkedList;

/**
 *
 * @author marco
 */
public class Tabla_transiciones {
    
   public String id;
   public LinkedList<Transicion> Tabla_transicion;

    public Tabla_transiciones(String id, LinkedList<Transicion> Tabla_transicion) {
        this.id = id;
        this.Tabla_transicion = Tabla_transicion;
    }
   
    
    
}
