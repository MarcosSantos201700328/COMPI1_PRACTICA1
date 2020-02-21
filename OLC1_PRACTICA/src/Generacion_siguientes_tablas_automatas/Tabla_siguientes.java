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
public class Tabla_siguientes {
    
  public  String id;
  public  LinkedList<Siguientes> t_siguientes;

    public Tabla_siguientes(String id, LinkedList<Siguientes> t_siguientes) {
        this.id = id;
        this.t_siguientes = t_siguientes;
    }
    
    
}
