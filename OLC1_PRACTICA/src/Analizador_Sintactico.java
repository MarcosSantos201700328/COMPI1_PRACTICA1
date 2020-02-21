
import Reestructurado_expresiones.Expresion_Lexema;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marco
 */
public class Analizador_Sintactico {

    int numero_preanalisis;//sirve para saber que elemento de la lista buscar
    token_del_lenguaje simbolo_preanalisis;//seteamos el simbolo actual de analisis
    LinkedList<token_del_lenguaje> Lista_Tokens;//lista que se obtiene del scanner
    boolean analisisCorrecto = false;//verifica si fue satisfactorio el analisis

    String id_auxiliar = "";//nos va servir para almacenar en la lista el id ya que no se encuentran en el mismo estado

    public LinkedList<Expresion_Lexema> Lexemas = new LinkedList<>();
    public LinkedList<Expresion_Lexema> Macros = new LinkedList<>();
    public LinkedList<Expresion_Lexema> ExpresionesRegulares = new LinkedList<>();

    public Analizador_Sintactico(LinkedList<token_del_lenguaje> Lista_tokens) {
        this.Lista_Tokens = Lista_tokens;
    }

    public boolean parser() {
        analisisCorrecto = true;
        numero_preanalisis = 0;
        simbolo_preanalisis = Lista_Tokens.get(0);

        INICIO();

        return analisisCorrecto;
    }

    private void INICIO() {
        // INICIO -> { CUERPO 
        match(token_del_lenguaje.TOKEN.LLAVE_A);
        CUERPO();

        match(token_del_lenguaje.TOKEN.ACEPTACION);

    }

    int contador = 0;

    private void CUERPO() {

        // CUERPO -> CUERPO SENTENCIA }
        // |SENTENCIA;
        if (simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.LLAVE_C || simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.ACEPTACION) {
            match(token_del_lenguaje.TOKEN.LLAVE_C);
        } else {
            SENTENCIA();

        }
    }

    private void SENTENCIA() {

        //sentencia -> CONJ  CONJUNTOS
        if (simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.CONJ) {
            match(token_del_lenguaje.TOKEN.CONJ);
            CONJUNTOS();
        } else if (simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.ID) {
            //sentencia -> ID  EXPRESIONES_LEXEMAS
             id_auxiliar = simbolo_preanalisis.getLexema().toString();
            match(token_del_lenguaje.TOKEN.ID);
            EXPRESIONES_LEXEMAS();
        } else if (simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.PORCENTAJES) {
            //sentencia -> %%
            match(token_del_lenguaje.TOKEN.PORCENTAJES);
        }
        CUERPO();

    }

    private void CONJUNTOS() {

        //CONJUNTOS ->: id flecha TIPO_C
        match(token_del_lenguaje.TOKEN.DOSPUNTOS);
        id_auxiliar = simbolo_preanalisis.getLexema().toString();
        match(token_del_lenguaje.TOKEN.ID);

        match(token_del_lenguaje.TOKEN.FLECHA);
        TIPO_C();

    }

    private void EXPRESIONES_LEXEMAS() {

        if (simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.FLECHA) {
            //EXPRESIONES_LEXEMAS -> flecha EXPRESION
            match(token_del_lenguaje.TOKEN.FLECHA);
            EXPRESION();
        } else if (simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.DOSPUNTOS) {
            //EXPRESIONES_LEXEMAS -> flecha EXPRESION
            match(token_del_lenguaje.TOKEN.DOSPUNTOS);
            LEXEMA();
        } else {
            JOptionPane.showMessageDialog(null, "Error sintactico, se esperaba una -> o un :");
            Error_fatal();

        }
    }

    private void EXPRESION() {
        ExpresionesRegulares.add(new Expresion_Lexema(id_auxiliar,simbolo_preanalisis.getLexema().toString()));
        match(token_del_lenguaje.TOKEN.EXPRESION);
        match(token_del_lenguaje.TOKEN.PTOCOMA);
    }

    private void LEXEMA() {
        Lexemas.add(new Expresion_Lexema(id_auxiliar,simbolo_preanalisis.getLexema().toString()));
        match(token_del_lenguaje.TOKEN.CADENA);

    }

    private void TIPO_C() {

        if (simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.CONJUNTOFINITIO) {
            //EXPRESIONES_LEXEMAS -> flecha EXPRESION
            String contenido = simbolo_preanalisis.getLexema().toString();
            Macros.add(new Expresion_Lexema(id_auxiliar, contenido));

            match(token_del_lenguaje.TOKEN.CONJUNTOFINITIO);
            match(token_del_lenguaje.TOKEN.PTOCOMA);

        } else if (simbolo_preanalisis.getTipo() == token_del_lenguaje.TOKEN.MACROS) {
            //EXPRESIONES_LEXEMAS -> flecha EXPRESION
            String contenido = simbolo_preanalisis.getLexema().toString();
            Macros.add(new Expresion_Lexema(id_auxiliar, contenido));

            match(token_del_lenguaje.TOKEN.MACROS);

            match(token_del_lenguaje.TOKEN.PTOCOMA);

        } else {
            match(token_del_lenguaje.TOKEN.MACROS);
            Error_fatal();
        }
        
        id_auxiliar="";
    }

    //--------------------------------------------------------------------
    private void Error_fatal() {

        JOptionPane.showMessageDialog(null, "Error faltal de sintaxis");
    }

    //------------------------------------------------------
    private void match(token_del_lenguaje.TOKEN token_a_verificar) {
        try {

            //si hace match y no es el ultimo token ,pasa al siguiente
            if (token_a_verificar == simbolo_preanalisis.getTipo() && simbolo_preanalisis.getTipo() != Lista_Tokens.getLast().getTipo()) {

                numero_preanalisis++;
                simbolo_preanalisis = Lista_Tokens.get(numero_preanalisis);

            } else if (simbolo_preanalisis.getTipo() == Lista_Tokens.getLast().getTipo()) {

                System.out.println("Analisis Sintactico Concluido");
            } else {
                System.err.println("Analisis Sintactico Incorrecto Se esperaba : " + especificar_error(token_a_verificar));
                JOptionPane.showMessageDialog(null, "Analisis Sintactico Incorrecto Se esperaba : " + especificar_error(token_a_verificar));
                analisisCorrecto = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Fatal");

        }
    }

    //----------------------------------------------------------------
    private String especificar_error(token_del_lenguaje.TOKEN tipo) {
        switch (tipo) {

            case LLAVE_A:
                return "Llave de apertura";
            case LLAVE_C:
                return "Llave de cierre";
            case COMILLA_DOBLE:
                return "Comilla doble";
            case SIMBOLO_OR:
                return "Simbolo OR |";
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
                return "#";
            default:
                return "error!";
        }

    }

}
