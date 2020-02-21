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
public class Transicion {
    
    public String estado;
    public String conjunto;
    public LinkedList<String> estados_siguientes;
    

    public Transicion(String estado, String conjunto) {
        this.estado = estado;
        this.conjunto = conjunto;
        this.estados_siguientes = new LinkedList<>();
    }
    
}
