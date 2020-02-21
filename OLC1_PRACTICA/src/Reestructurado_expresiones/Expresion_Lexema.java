package Reestructurado_expresiones;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco
 */
public class Expresion_Lexema {
    //almacena id y la expresion pero en forma de cadena
    private String Identiicador;
    private String Contenido;

    public Expresion_Lexema(String Identiicador, String Contenido) {
        this.Identiicador = Identiicador;
        this.Contenido = Contenido;
    }

    /**
     * @return the Identiicador
     */
    public String getIdentiicador() {
        return Identiicador;
    }

    /**
     * @param Identiicador the Identiicador to set
     */
    public void setIdentiicador(String Identiicador) {
        this.Identiicador = Identiicador;
    }

    /**
     * @return the Contenido
     */
    public String getContenido() {
        return Contenido;
    }

    /**
     * @param Contenido the Contenido to set
     */
    public void setContenido(String Contenido) {
        this.Contenido = Contenido;
    }
    
    
    
}
