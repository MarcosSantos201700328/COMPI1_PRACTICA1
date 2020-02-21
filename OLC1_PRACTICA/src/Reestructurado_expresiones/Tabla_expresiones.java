package Reestructurado_expresiones;


import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco
 */
public class Tabla_expresiones {
    
    private String identificador;
    private LinkedList<String> expresion_estructurada;

    public Tabla_expresiones(String identificador, LinkedList<String> expresion_estructurada) {
        this.identificador = identificador;
        this.expresion_estructurada = expresion_estructurada;
    }

    /**
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @return the expresion_estructurada
     */
    public LinkedList<String> getExpresion_estructurada() {
        return expresion_estructurada;
    }
    
    
    
}
