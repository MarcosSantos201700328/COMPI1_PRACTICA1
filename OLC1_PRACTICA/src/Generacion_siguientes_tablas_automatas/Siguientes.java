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
public class Siguientes {
    

  public  String simbolo;
  public  String numHoja;
  public  LinkedList<String> siguientes;

    public Siguientes(String simbolo, String numHoja) {
      
        this.simbolo = simbolo;
        this.numHoja = numHoja;
        this.siguientes = new LinkedList<>();
    }
    
    
    
}
