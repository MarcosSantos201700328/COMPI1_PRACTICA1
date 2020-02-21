/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco
 */
public class token_del_lenguaje {
    
    private TOKEN tipo;
    private Object Lexema;
    private int fila;
    private int columna;

    public token_del_lenguaje(TOKEN tipo, Object Lexema, int fila, int columna) {
        this.tipo = tipo;
        this.Lexema = Lexema;
        this.fila = fila;
        this.columna = columna;
    } 
       
    public enum TOKEN{
        /*SIMBOLOS SIMPLES*/
        
        LLAVE_A,//{
        LLAVE_C,//}
        COMILLA_DOBLE,//"
        SIMBOLO_OR,//|
        SIMBOLO_AND,//.
        SIMBOLO_0_1vez,//?
        SIMBOLO_0_MAS,//*
        SIMBOLO_1_MAS,//+
        SIMBOLO_RANGO,//~
        PTOCOMA,//;
        DOSPUNTOS,//:
        ASCIII,//CUALQUIER SIMBOLO ASCII COMPRENDIDO DEL 32-47 , 58-64 , 91-96 , 123-125
        COMA,// ,
        
        
        /*SIMBOLOS COMPUESTOS*/
        
        FLECHA,//->
        ID,//L(L|N|_)*
        DIGITO,//D+(. D+)?
        COMENAMUL, //<!
        COMENCMUL, //!>
        COMENSIMPLE,// //
        PORCENTAJES, // %%%%
        CADENA, // HOLA , MUNDO , ETC...
        MACROS, // A~z etc
        EXPRESION,//expresion regular
        CONJUNTOFINITIO,
        
        
        /*SIMBOLOS RESERVADOS O PALABRAS RESERVADAS DEL LENGUAJE*/
        CONJ,//CONJ
        
        
        /*Error*/
        ERROR,
           
        /*simbolo de aceptacion*/
        ACEPTACION
    }

    /**
     * @return the tipo
     */
    public TOKEN getTipo() {
        return tipo;
    }

    /**
     * @return the Lexema
     */
    public Object getLexema() {
        return Lexema;
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }
    
    
    /*GET QUE PERMITE SABER QUE ES CADA SIMBOLO MEDIANTE STRINGS*/
    
    public String tipo_detallado(TOKEN tipo){
        
    switch(tipo){
    
        case LLAVE_A:
            return "Llave de apertura";
        case LLAVE_C:
            return "Llave de cierre";
        case COMILLA_DOBLE:
            return "Comilla doble";
        case SIMBOLO_OR:
            return  "Simbolo OR |";
        case SIMBOLO_0_1vez:
            return "Simbolo cerradura ?";
        case SIMBOLO_0_MAS:
            return "Simbolo cerradura *";
        case SIMBOLO_1_MAS:
            return "Simbolo cerradura +";
        case SIMBOLO_RANGO:
            return "Simbolo de rango de una macro ~";
        case PTOCOMA:
            return "Punto y coma";
        case DOSPUNTOS:
            return "Dos puntos";
        case SIMBOLO_AND:
            return "Simbolo AND .";
        case PORCENTAJES:
            return "Simbolos de porcentaje doble";
        case FLECHA:
            return "Simbolo de asignacion de expresion regular";
        case COMENAMUL:
            return "Comentario multilinea apertura";
        case COMENSIMPLE:
            return "Comentario simple";
        case CONJ:
            return "Palabra reservada CONJ";
        case ASCIII:
            return "Simbolos ASCCI comprendidos del 32-47, 58-64, 91-96, 123-125";
        case COMENCMUL:
             return "Comentario multulinea cierre";
        case ID:
             return "Identificador";
        case DIGITO:
            return "Digito";
        case CADENA:
            return "Cadena";
        case EXPRESION:
            return "expresion regular";
        case MACROS:
            return "Macro de rangos de caracteres ASCII 32 al 125";
        case ERROR:
             return "Error Lexico, simbolo no admitido";
        case COMA:
            return "Coma ,";
        case CONJUNTOFINITIO:
            return "Conjunto finito de caracteres ASCCI 32 al 125";
        case ACEPTACION:
            return "Simbolo de fin del archivo";
        default:
            return "error!";
    }
    }
    
}
