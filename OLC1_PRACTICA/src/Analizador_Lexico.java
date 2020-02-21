
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
public class Analizador_Lexico {
    
    public boolean AnalisisCorrecto = true;
    
    public String contenidoArchivo;
    public LinkedList<token_del_lenguaje> ListaParser = new LinkedList<>();//lista que utilizara el parser
    public LinkedList<token_del_lenguaje> ListaErroresLexicos = new LinkedList<>();//lista que reporta errores lexicos
    private int estado;
    private String lexema;
    
    public Analizador_Lexico(String contenido) {
        this.contenidoArchivo = contenido + "#";//se inicializa el contenido del archivo
    }

    /*
    @scanner
    realiza el analisis lexico
     */
    public void scanner() {
        //inicializamos las variables
        estado = 0;
        lexema = "";
        int cuenta_llave_apertura=0;
        int fila = 1;
        int columna = 1;
        char[] cadenaCaracteres = contenidoArchivo.toCharArray();
        
        int auxcaracter = 0;//me va a servir en los casos donde se tenga que tener un caracter anticipado a su analisis

        for (char charActual : cadenaCaracteres) {

            //solo se verifica el salto de linea y sigue con el curso del analisis
            if (charActual == '\n') {
                fila++;
                columna = 0;
            }

            //aqui inician los casos de reconocimiento de las expresiones regulares
            switch (estado) {
                
                case 0:
                    
                    if (charActual == 'C') {
                        lexema += charActual;
                        estado = 9;
                        
                    } else if (charActual == '/') {// inicio de comentario simplre
                        lexema += charActual;
                        estado = 1;
                        
                    } else if (charActual == '<') {//inicio de comentario multi
                        lexema += charActual;
                        estado = 3;
                        
                    } else if (charActual == '{' && cuenta_llave_apertura==0) { //llave de apertura
                        lexema += charActual;
                        cuenta_llave_apertura++;
                        
                        aceptarToken(token_del_lenguaje.TOKEN.LLAVE_A, lexema, fila, columna);
                    } else if (charActual == '}') { //llave de cierre
                        lexema += charActual;
                        aceptarToken(token_del_lenguaje.TOKEN.LLAVE_C, lexema, fila, columna);
                    } else if (charActual == '"') { //llave de cierre
                        lexema += charActual;
                        estado = 5;
                    } else if (charActual == '-') {
                        lexema += charActual;
                        estado = 6;
                    }else if(charActual=='%'){
                    
                    lexema+=charActual;
                    estado=12;
                    
                    }else if(charActual==':'){
                     lexema+=charActual;
                        aceptarToken(token_del_lenguaje.TOKEN.DOSPUNTOS, lexema, fila, columna);
                        
                    }else if (charActual >= 65 && charActual <= 90 || charActual >= 97 && charActual <= 122) {//una letra
                     
                        
                        lexema += charActual;
                        estado = 8;
                    } else if (charActual == '#') {//indicador de fin del archivo
                        aceptarToken(token_del_lenguaje.TOKEN.ACEPTACION, charActual, fila, columna);
                        System.out.println("Analisis concluido");
                        
                    } else if (charActual == '\n' || charActual == '\t' || charActual == ' ') {//soporte de espacio , tabulaciones , salto de linea

                        //no se hace nada se ignoran
                    } else {
                        //de venir otra vez no se toma en cuenta
                        if(charActual=='{'){
                        //no importa soportamos el error;
                        }else{
                        lexema += charActual;
                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                        }
                    }
                    break;
                //------------------------------------------------------------------------------------------------------
                case 1://se espera la siguiene // para comenzar el comentario multilinea

                    if (charActual == '/') {
                        lexema += charActual;
                        estado = 2;
                        
                    } else {
                        lexema += charActual;
                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                    }
                    break;
                //------------------------------------------------------------------------------------------------------
                case 2:// se acepta el comentario simple hasta encontrar un salto de linea

                    if (charActual == '\n') {
                        //ya no se concatena el simbolo solo se acepta lo anterior a estos caracteres
                        // aceptarToken(token_del_lenguaje.TOKEN.COMENSIMPLE, lexema, fila, columna);
                        estado = 0;
                        lexema = "";
                    } else {
                        lexema += charActual;
                    }
                    
                    break;
                //------------------------------------------------------------------------------------------------------
                case 3://se espera la apertura correcta de <!

                    if (charActual == '!') {
                        lexema += charActual;
                        estado = 4;
                    } else {
                        lexema += charActual;
                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                    }
                    break;

                //-------------------------------------------------------------------------------------------------------
                case 4://se concatena todo hasta que se encuentre el !> para el cierre anticipado de comentario multilinea
                    if (lexema.startsWith("<!") && lexema.endsWith("!>")) {
                        estado = 0;
                        lexema = "";
                        
                    } else {
                        lexema += charActual;
                    }
                    break;
                //---------------------------------------------------------------------------------------------------------
                case 5://concatena la cadena

                    if (charActual == '"') {
                        lexema += charActual;
                        aceptarToken(token_del_lenguaje.TOKEN.CADENA, lexema, fila, columna);
                    } else {
                        lexema += charActual;
                    }
                    break;
                //----------------------------------------------------------------------------------------------------
                case 6://aceptacion del la flecha y calisificacion de macros

                    if (charActual == '>') {
                        lexema += charActual;
                        aceptarToken(token_del_lenguaje.TOKEN.FLECHA, lexema, fila, columna);
                        estado = 7;
                    } else {
                        lexema += charActual;
                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                    }
                    break;
                case 7://caso para saber si es una expresion regular , una macro de rango o un conjunto finito

                    if (charActual == ';' || charActual=='\n' ) {//de encontrar el punto y coma

                        if (lexema.length() == 3) {//se trata de un rango 

                            String cadena_aux = "";
                            
                            for (int i = 0; i < lexema.length(); i++) {
                                
                                if (lexema.charAt(i) >= 32 && lexema.charAt(i) <= 125) {
                                    cadena_aux += lexema.codePointAt(i);
                                } else if (lexema.charAt(i) == '~') {
                                    cadena_aux += lexema.charAt(i);
                                } else {
                                    reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                                    AnalisisCorrecto = false;
                                }
                                
                            }
                            
                            if (AnalisisCorrecto) {
                                aceptarToken(token_del_lenguaje.TOKEN.MACROS, cadena_aux, fila, columna);
                            }
                        } else {// se trata de un conjunto especifico

                            if (lexema.startsWith(",", 1)) {//es un conjunto
                                String cadena_aux = "";
                                for (int i = 0; i < lexema.length(); i++) {
                                    if (lexema.charAt(i) >= 32 && lexema.charAt(i) >= 43 && lexema.charAt(i) >= 45 && lexema.charAt(i) <= 125) {
                                        cadena_aux += lexema.codePointAt(i);
                                    } else if (lexema.charAt(i) == ',') {
                                        cadena_aux += lexema.charAt(i);
                                    } else {
                                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                                        AnalisisCorrecto = false;
                                    }
                                }
                                
                                if (AnalisisCorrecto) {
                                    aceptarToken(token_del_lenguaje.TOKEN.CONJUNTOFINITIO, cadena_aux, fila, columna);
                                }
                                
                            } else { // es una expresion

                                String cadena_aux = "";
                                
                                for (int i = 0; i < lexema.length(); i++) {
                                    
                                    if (lexema.charAt(i) >= 32 && lexema.charAt(i) <= 125 || lexema.charAt(i) == '|') {
                                        cadena_aux += lexema.codePointAt(i);
                                    } else {
                                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                                        AnalisisCorrecto = false;
                                    }
                                    
                                }
                                
                                if (AnalisisCorrecto) {
                                    aceptarToken(token_del_lenguaje.TOKEN.EXPRESION, lexema, fila, columna);
                                }
                                
                            }
                            
                        }
                        //finalmente se concatena el punto y coma 
                        if(charActual==';'){
                        aceptarToken(token_del_lenguaje.TOKEN.PTOCOMA, charActual, fila, columna);
                        }
                        
                    } else if (charActual == '\n' || charActual == '\t' || charActual == ' ') {//soporte de espacio , tabulaciones , salto de linea

                        //no se hace nada se ignoran
                    } else {
                        lexema += charActual;
                    }
                    
                    break;
                case 8:
                    if (contenidoArchivo.startsWith("-", auxcaracter + 1) || contenidoArchivo.startsWith(" ", auxcaracter + 1) || contenidoArchivo.startsWith(":", auxcaracter + 1)) {
                        lexema += charActual;
                        aceptarToken(token_del_lenguaje.TOKEN.ID, lexema, fila, columna);
                    } else if (charActual >= 65 && charActual <= 90 || charActual >= 97 && charActual <= 122) {//una letra
                        lexema += charActual;
                        estado = 8;
                    } else if (charActual >= 48 && charActual <= 57) {//un numero
                        lexema += charActual;
                        estado = 8;
                        
                    } else if (charActual == '_') {
                        lexema += charActual;
                        estado = 8;
                    } else {
                        
                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                        
                    }
                    
                    break;
                
                case 9:
                    if (charActual == 'O') {
                        lexema += charActual;
                        estado = 10;
                        
                    } else {
                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                        
                    }
                    break;
                
                case 10:
                    
                    if (charActual == 'N') {
                        lexema += charActual;
                        estado = 11;
                        
                    } else {
                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                        
                    }
                    break;
                case 11:
                    
                    if (charActual == 'J') {
                        lexema += charActual;
                        aceptarToken(token_del_lenguaje.TOKEN.CONJ, lexema, fila, columna);
                        
                    } else {
                        reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                        
                    }
                    break;
                case 12:
                    if(charActual=='%'){
                    lexema+=charActual;
                      aceptarToken(token_del_lenguaje.TOKEN.PORCENTAJES, lexema, fila, columna);
                    
                    }else{
                    
                      reportarToken(token_del_lenguaje.TOKEN.ERROR, lexema, fila, columna);
                        AnalisisCorrecto = false;
                        
                    }
                
                
            }
            columna++;//aumenta la columna cada vez que lee el siguiente caracter
            auxcaracter++;
        }
        
    }

    /*
    @aceptarToken
    acepta el token si cumple alguna expreison del arbol de analisis lexico
    
     */
    private void aceptarToken(token_del_lenguaje.TOKEN tipo, Object Lexema, int fila, int columna) {
        ListaParser.add(new token_del_lenguaje(tipo, Lexema, fila, columna));// se agrega el token a mi lista 
        lexema = "";//limpiamos el lexema
        estado = 0; // el estado regresa al estado inicial

    }

    /*
    @reportarToken
    niega el token si no cumple alguna expreison del arbol de analisis lexico
    
     */
    private void reportarToken(token_del_lenguaje.TOKEN tipo, Object Lexema, int fila, int columna) {
        ListaErroresLexicos.add(new token_del_lenguaje(tipo, Lexema, fila, columna));// se agrega el token a mi lista 
        lexema = "";//limpiamos el lexema
        estado = 0; // el estado regresa al estado inicial
    }

    /*
    @Lista de tokens aceptados
    retorna los tokens acpetados en el analisis
     */
    public LinkedList<token_del_lenguaje> returnListaTokens() {
        return ListaParser;
    }

    /*
    @Lista de errores Lexicos
    retorna los tokens no acpetados en el analisis
     */
    public LinkedList<token_del_lenguaje> returnListaErroresLexicos() {
        return ListaErroresLexicos;
    }
    
}
