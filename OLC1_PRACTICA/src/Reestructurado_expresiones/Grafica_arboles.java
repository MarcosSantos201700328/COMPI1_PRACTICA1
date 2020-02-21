package Reestructurado_expresiones;

import Generacion_siguientes_tablas_automatas.Nodo;
import Generacion_siguientes_tablas_automatas.Tabla_raices;
import Generacion_siguientes_tablas_automatas.Siguientes;
import Generacion_siguientes_tablas_automatas.Tabla_siguientes;
import Generacion_siguientes_tablas_automatas.Tabla_transiciones;
import Generacion_siguientes_tablas_automatas.Transicion;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marco
 */
public class Grafica_arboles {
//La encargada de reestructuras las expresiones regulares y poder crear los arboles de cada una

    LinkedList<Expresion_Lexema> lista_expresiones;

    //estructuramos por nodos cada elemento de la expresion puesto que inicialmente
    //se toma como una cadena de caracteres
    //se vuelve a ingresar a una tabla hash para poder tener el control al momento de analizar con una lexema
    //y para manipular mejor la graficada
    LinkedList<Tabla_expresiones> listaprevia_raices = new LinkedList<>();
    LinkedList<Generacion_siguientes_tablas_automatas.Tabla_raices> Tabla_raices = new LinkedList<>();
   public LinkedList<Generacion_siguientes_tablas_automatas.Tabla_siguientes> Tabla_siguientes = new LinkedList<>();
   public LinkedList<Generacion_siguientes_tablas_automatas.Tabla_transiciones> Tabla_transiciones=new LinkedList<>();
    
   public LinkedList<String> rutas_arboles=new LinkedList<>();
   public LinkedList<String> rutas_siguientes=new LinkedList<>();
   public LinkedList<String> rutas_transiciones=new LinkedList<>();
   public LinkedList<String> rutas_automatas=new LinkedList<>();
    

    int numhoja = 1;//contador de nodos hoja
    int indice = 1;

    public Grafica_arboles(LinkedList<Expresion_Lexema> lista_expresiones) {
        this.lista_expresiones = lista_expresiones;
    }

    public void procesar_expresiones() {

        for (Expresion_Lexema expresion : lista_expresiones) {
            //por cada expresion 
            //lo almacenamos en una lista de expresiones estructuradas
            LinkedList<String> expresion_estructurada = new LinkedList<>();
            String exp = expresion.getContenido();
            char[] caracteres_expresion = exp.toCharArray();
            String cadena_nueva = "";
            int cont_verificador = 0;
            //primero vamos a definir los nodos
            int contAux = 0;

            for (char caracter : caracteres_expresion) {

                if (caracter == '"' && cont_verificador == 0) {
                    cadena_nueva += caracter;
                    cont_verificador = 1;

                } else if (caracter == '"' && cont_verificador == 1) {
                    cadena_nueva += caracter;
                    expresion_estructurada.add(cadena_nueva.replace("\"", "´"));
                    cadena_nueva = "";
                    cont_verificador = 0;

                } else if (caracter == '{' && exp.charAt(contAux + 1) != '}') {
                    cont_verificador = 1;

                } else if (caracter == '}' && cont_verificador == 1) {

                    expresion_estructurada.add(cadena_nueva);
                    cadena_nueva = "";
                    cont_verificador = 0;

                } else if (cont_verificador == 1) {
                    cadena_nueva += caracter;

                } else {
                    switch (caracter) {

                        case '.':
                            cadena_nueva += caracter;
                            expresion_estructurada.add(cadena_nueva);
                            cadena_nueva = "";

                            break;
                        case '|':
                            cadena_nueva += caracter;
                            expresion_estructurada.add(cadena_nueva.replace(" ", ""));
                            cadena_nueva = "";
                            break;
                        case '*':
                            cadena_nueva += caracter;
                            expresion_estructurada.add(cadena_nueva);
                            cadena_nueva = "";
                            break;
                        case '+':
                            cadena_nueva += caracter;
                            expresion_estructurada.add(cadena_nueva);
                            cadena_nueva = "";
                            break;
                        case '?':
                            cadena_nueva += caracter;
                            expresion_estructurada.add(cadena_nueva);
                            cadena_nueva = "";
                            break;

                    }
                }

                //_------------------------------------
                contAux++;//auxiliar de preanalisis de caracter 
            }
            System.out.println("antes_ " + expresion.getIdentiicador());
            for (String f : expresion_estructurada) {
                System.out.print(f + " ");
            }
            System.out.println("");
            listaprevia_raices.add(new Tabla_expresiones(expresion.getIdentiicador(), expresion_estructurada));
        }
    }
    Graficadora graficar = new Graficadora();
    int i = 0;
    String arbol_dot = "";

    public void recorrer_para_graficar_arbol() {
//por cada expresion se grafica un arbol
        for (Tabla_expresiones expression : listaprevia_raices) {

//si solo viene un dato 
            if (expression.getExpresion_estructurada().size() == 1) {
                Nodo padre = new Nodo("", expression.getExpresion_estructurada().get(i), String.valueOf(numhoja), String.valueOf(numhoja), String.valueOf(numhoja));
                formarArbol(padre, expression.getExpresion_estructurada());

                 // analisis de anulabilidad hojas
                anulabilidadHojas(padre);

                // analisis de anulabilidad arbol completo
                anulabilidad(padre);

                //analisis de primeros y ultimos
                primerosYultimos(padre);
                //despues de terminar agregamos la raiz a mi lista
                Tabla_raices.add(new Tabla_raices(expression.getIdentificador(), padre));

                //analisis de siguientes
                LinkedList<Siguientes> nueva_tabla = new LinkedList<>();
                agregar_simbolos_tabla_siguientes(padre, nueva_tabla);
                Siguientes(padre, nueva_tabla);

               

               //analisis de transiciones
               LinkedList<Transicion> nueva_t_transiciones= new LinkedList<>();
                System.out.println("inicio----");
              Tabla_transiciones(padre, nueva_t_transiciones, nueva_tabla);
                System.out.println("fin----\n\n");
               
               graficar.escribir_fichero_tabla_siguientes(graficar.hacer_tabla_siguientes(nueva_tabla) + "\n label=\"" + expression.getIdentificador() + "\";\n", "", expression.getIdentificador() + "_tablaS");
               graficar.generar_Dot_tabla_siguientes(expression.getIdentificador() + "_tablaS");
               rutas_siguientes.add("C:\\siguientes\\"+expression.getIdentificador() + "_tablaS.jpg");
               Tabla_siguientes.add(new Tabla_siguientes(expression.getIdentificador(), nueva_tabla)); 
               
                              
               
               graficar.escribir_fichero_tabla_transiciones(graficar.hacer_table_transiciones(nueva_t_transiciones)+ "\n label=\"" + expression.getIdentificador()+"\";\n","", expression.getIdentificador()+"TT");
               graficar.generar_Dot_tabla_transiciones(expression.getIdentificador()+"TT");
               rutas_transiciones.add("C:\\transiciones\\"+expression.getIdentificador()+"TT.jpg");
               Tabla_transiciones.add(new Tabla_transiciones(expression.getIdentificador(), nueva_t_transiciones));
                
                   

               
               graficar.escribir_fichero_automata(graficar.hacer_automata(nueva_t_transiciones, nueva_tabla)+ "\n label=\"" + expression.getIdentificador()+"\";\n", "", expression.getIdentificador()+"AFD");
               graficar.generar_Dot_automata(expression.getIdentificador()+"AFD");
               rutas_automatas.add("C:\\automatas\\"+expression.getIdentificador()+"AFD.jpg");
               
                
               //se procede a graficar
               graficar.escribir_fichero_grafo(graficar.recorrer_arbolito(padre) + "\n label=\"" + expression.getIdentificador() + "\";\n", "", expression.getIdentificador());
               graficar.generar_Dot_grafo(expression.getIdentificador());
               rutas_arboles.add("C:\\arboles\\"+expression.getIdentificador() + ".jpg");


            } else {// si vienen mas 
                Nodo padre = new Nodo("", ".", "", "", "");
                Nodo hijo1 = new Nodo("", expression.getExpresion_estructurada().get(i), "", "", "");
                Nodo hijo2 = new Nodo("", "#", "", "", "");
                padre.agregarHijo(hijo1);
                padre.agregarHijo(hijo2);
                formarArbol(hijo1, expression.getExpresion_estructurada());
                // analisis de anulabilidad hojas
                anulabilidadHojas(padre);

                // analisis de anulabilidad arbol completo
                anulabilidad(padre);

                //analisis de primeros y ultimos
                primerosYultimos(padre);
                //despues de terminar agregamos la raiz a mi lista
                Tabla_raices.add(new Tabla_raices(expression.getIdentificador(), padre));

                //analisis de siguientes
                LinkedList<Siguientes> nueva_tabla = new LinkedList<>();
                agregar_simbolos_tabla_siguientes(padre, nueva_tabla);
                Siguientes(padre, nueva_tabla);

               

               //analisis de transiciones
               LinkedList<Transicion> nueva_t_transiciones= new LinkedList<>();
                System.out.println("inicio----");
              Tabla_transiciones(padre, nueva_t_transiciones, nueva_tabla);
                System.out.println("fin----\n\n");
               
             
               
               
               graficar.escribir_fichero_tabla_transiciones(graficar.hacer_table_transiciones(nueva_t_transiciones)+ "\n label=\"" + expression.getIdentificador()+"\";\n","", expression.getIdentificador()+"TT");
               graficar.generar_Dot_tabla_transiciones(expression.getIdentificador()+"TT");
               rutas_transiciones.add("C:\\transiciones\\"+expression.getIdentificador()+"TT.jpg");
               Tabla_transiciones.add(new Tabla_transiciones(expression.getIdentificador(), nueva_t_transiciones));
                
                   
               graficar.escribir_fichero_tabla_siguientes(graficar.hacer_tabla_siguientes(nueva_tabla) + "\n label=\"" + expression.getIdentificador() + "\";\n", "", expression.getIdentificador() + "_tablaS");
               graficar.generar_Dot_tabla_siguientes(expression.getIdentificador() + "_tablaS");
               rutas_siguientes.add("C:\\siguientes\\"+expression.getIdentificador() + "_tablaS.jpg");
               Tabla_siguientes.add(new Tabla_siguientes(expression.getIdentificador(), nueva_tabla)); 
               
               
               graficar.escribir_fichero_automata(graficar.hacer_automata(nueva_t_transiciones, nueva_tabla)+ "\n label=\"" + expression.getIdentificador()+"\";\n", "", expression.getIdentificador()+"AFD");
               graficar.generar_Dot_automata(expression.getIdentificador()+"AFD");
               rutas_automatas.add("C:\\automatas\\"+expression.getIdentificador()+"AFD.jpg");
               
                
               //se procede a graficar
               graficar.escribir_fichero_grafo(graficar.recorrer_arbolito(padre) + "\n label=\"" + expression.getIdentificador() + "\";\n", "", expression.getIdentificador());
               graficar.generar_Dot_grafo(expression.getIdentificador());
               rutas_arboles.add("C:\\arboles\\"+expression.getIdentificador() + ".jpg");

            }

            //limpiamos nuestros operadores
            i = 0;
            arbol_dot = "";
            numhoja = 1;
            indice = 1;
            index=0;
            estado=0;
        }

    }

    /*
    @formarArbol
    
    este metodo permite formar un arbol de lo que se obtiene de la cadena
    de la expresion regular
    
     */
    public void formarArbol(Nodo padre, LinkedList<String> elemento) {

        if (i < elemento.size()) {

            if (elemento.get(i).equals(".") || elemento.get(i).equals("|")) {

                i++;
                String simbolo = elemento.get(i).replace("|", "(or)");
                simbolo = simbolo.replace("<", "menorque");
                simbolo = simbolo.replace(">", "mayorque");
                Nodo hijo1 = new Nodo("", elemento.get(i).replace("|", "(or)"), "", "", "");
                padre.agregarHijo(hijo1);
                formarArbol(hijo1, elemento);
                i++;
                simbolo = elemento.get(i).replace("|", "(or)");
                simbolo = simbolo.replace("<", "menorque");
                simbolo = simbolo.replace(">", "mayorque");
                Nodo hijo2 = new Nodo("", simbolo, "", "", "");
                padre.agregarHijo(hijo2);
                formarArbol(hijo2, elemento);

            } else if (elemento.get(i).equals("*") || elemento.get(i).equals("?") || elemento.get(i).equals("+")) {

                i++;
                String simbolo = elemento.get(i).replace("|", "(or)");
                simbolo = simbolo.replace("<", "menorque");
                simbolo = simbolo.replace(">", "mayorque");

                Nodo hijo1 = new Nodo("", simbolo, "", "", "");

                padre.agregarHijo(hijo1);

                formarArbol(hijo1, elemento);

            } else {

                //  Nodo hijo1 = new Nodo("", elemento.get(i).replace("|", "(or)"), "", "", "");
                // padre.agregarHijo(hijo1);
                return;
            }

        } else {
            return;
        }

    }

    public void anulabilidadHojas(Nodo raiz) {

        if (raiz.hijos.isEmpty()) {//si son nodos hoja
            raiz.anulable = "F";
            raiz.primeros = String.valueOf(numhoja);
            raiz.ultimos = String.valueOf(numhoja);
            raiz.numero_hoja = String.valueOf(numhoja);

            numhoja++;

        } else {

            for (Nodo hijo : raiz.hijos) {

                //siempre toma primero al hijo 1 y despues al hijo2
                anulabilidadHojas(hijo);
            }
        }
    }

    int hijoizquierdo = 0;
    int hijoderecho = 1;
    String datoIzquierdo = "";
    String datoDerecho = "";

    public String anulabilidad(Nodo raiz) {

        if (raiz.valor.equals(".")) {

            //el caso que sea un . y los lados izquierdo y derecho no tenga datos
            if (raiz.hijos.get(hijoizquierdo).anulable.equals("") && raiz.hijos.get(hijoderecho).anulable.equals("")) {

                datoIzquierdo = anulabilidad(raiz.hijos.get(hijoizquierdo));
                datoDerecho = anulabilidad(raiz.hijos.get(hijoderecho));

                if (datoIzquierdo.equals("F") || datoDerecho.equals("F")) {
                    raiz.anulable = "F";
                    return "F";
                } else {
                    raiz.anulable = "V";
                    return "V";
                }

            } else if (raiz.hijos.get(hijoizquierdo).anulable.equals("") && !raiz.hijos.get(hijoderecho).anulable.equals("")) {
                //el caso en que venga un hijo izquierdo sin valor y un hijo derecho con valor
                datoIzquierdo = anulabilidad(raiz.hijos.get(hijoizquierdo));
                datoDerecho = raiz.hijos.get(hijoderecho).anulable;
                anulabilidad(raiz.hijos.get(hijoderecho));

                if (datoIzquierdo.equals("F") || datoDerecho.equals("F")) {
                    raiz.anulable = "F";
                    return "F";
                } else {
                    raiz.anulable = "V";
                    return "V";
                }

            } else if (!raiz.hijos.get(hijoizquierdo).anulable.equals("") && raiz.hijos.get(hijoderecho).anulable.equals("")) {
                //el caso en que venga un hijo izquierdo con valor y un hijo derecho sin valor
                datoIzquierdo = raiz.hijos.get(hijoizquierdo).anulable;
                anulabilidad(raiz.hijos.get(hijoizquierdo));
                datoDerecho = anulabilidad(raiz.hijos.get(hijoderecho));

                if (datoIzquierdo.equals("F") || datoDerecho.equals("F")) {
                    raiz.anulable = "F";
                    return "F";
                } else {
                    raiz.anulable = "V";
                    return "V";
                }

            } else if (!raiz.hijos.get(hijoizquierdo).anulable.equals("") && !raiz.hijos.get(hijoderecho).anulable.equals("")) {
                //el caso en que venga un hijo izquierdo con valor y un hijo derecho sin valor
                datoIzquierdo = raiz.hijos.get(hijoizquierdo).anulable;
                anulabilidad(raiz.hijos.get(hijoizquierdo));
                datoDerecho = raiz.hijos.get(hijoderecho).anulable;
                anulabilidad(raiz.hijos.get(hijoderecho));
                if (datoIzquierdo.equals("F") || datoDerecho.equals("F")) {
                    raiz.anulable = "F";
                    return "F";
                } else {
                    raiz.anulable = "V";
                    return "V";
                }

            }

        } else if (raiz.valor.equals("(or)")) {

            //el caso que sea un . y los lados izquierdo y derecho no tenga datos
            if (raiz.hijos.get(hijoizquierdo).anulable.equals("") && raiz.hijos.get(hijoderecho).anulable.equals("")) {

                datoIzquierdo = anulabilidad(raiz.hijos.get(hijoizquierdo));
                datoDerecho = anulabilidad(raiz.hijos.get(hijoderecho));

                if (datoIzquierdo.equals("F") && datoDerecho.equals("F")) {
                    raiz.anulable = "F";
                    return "F";
                } else {
                    raiz.anulable = "V";
                    return "V";
                }

            } else if (raiz.hijos.get(hijoizquierdo).anulable.equals("") && !raiz.hijos.get(hijoderecho).anulable.equals("")) {
                //el caso en que venga un hijo izquierdo sin valor y un hijo derecho con valor
                datoIzquierdo = anulabilidad(raiz.hijos.get(hijoizquierdo));
                datoDerecho = raiz.hijos.get(hijoderecho).anulable;
                anulabilidad(raiz.hijos.get(hijoderecho));
                if (datoIzquierdo.equals("F") && datoDerecho.equals("F")) {
                    raiz.anulable = "F";
                    return "F";
                } else {
                    raiz.anulable = "V";
                    return "V";
                }

            } else if (!raiz.hijos.get(hijoizquierdo).anulable.equals("") && raiz.hijos.get(hijoderecho).anulable.equals("")) {
                //el caso en que venga un hijo izquierdo con valor y un hijo derecho sin valor
                datoIzquierdo = raiz.hijos.get(hijoizquierdo).anulable;
                anulabilidad(raiz.hijos.get(hijoizquierdo));
                datoDerecho = anulabilidad(raiz.hijos.get(hijoderecho));

                if (datoIzquierdo.equals("F") && datoDerecho.equals("F")) {
                    raiz.anulable = "F";
                    return "F";
                } else {
                    raiz.anulable = "V";
                    return "V";
                }

            } else if (!raiz.hijos.get(hijoizquierdo).anulable.equals("") && !raiz.hijos.get(hijoderecho).anulable.equals("")) {
                //el caso en que venga un hijo izquierdo con valor y un hijo derecho sin valor
                datoIzquierdo = raiz.hijos.get(hijoizquierdo).anulable;
                anulabilidad(raiz.hijos.get(hijoizquierdo));
                datoDerecho = raiz.hijos.get(hijoderecho).anulable;
                anulabilidad(raiz.hijos.get(hijoderecho));
                if (datoIzquierdo.equals("F") && datoDerecho.equals("F")) {
                    raiz.anulable = "F";
                    return "F";
                } else {
                    raiz.anulable = "V";
                    return "V";
                }

            }

        } else if (raiz.valor.equals("*")) {

            anulabilidad(raiz.hijos.get(hijoizquierdo));
            raiz.anulable = "V";
            return "V";

        } else if (raiz.valor.equals("+")) {
            anulabilidad(raiz.hijos.get(hijoizquierdo));
            raiz.anulable = "F";
            return "F";

        } else if (raiz.valor.equals("?")) {
            anulabilidad(raiz.hijos.get(hijoizquierdo));
            raiz.anulable = "V";
            return "V";

        } else {

            return raiz.anulable;
        }
        return "";
    }

    String primerosyultimosA = "";
    String primerosyultimosB = "";

    public void primerosYultimos(Nodo raiz) {

        if (raiz.valor.equals(".")) {

            //el caso que sea un . y los lados izquierdo y derecho no tenga datos
            if (raiz.hijos.get(hijoizquierdo).primeros.equals("") && raiz.hijos.get(hijoderecho).primeros.equals("")) {

                primerosYultimos(raiz.hijos.get(hijoizquierdo));
                primerosYultimos(raiz.hijos.get(hijoderecho));

                if (raiz.hijos.get(hijoizquierdo).anulable.equals("V")) {

                    raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros + "," + raiz.hijos.get(hijoderecho).primeros;

                } else {

                    raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros;

                }

                if (raiz.hijos.get(hijoderecho).anulable.equals("V")) {

                    raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos + "," + raiz.hijos.get(hijoderecho).ultimos;

                } else {
                    raiz.ultimos = raiz.hijos.get(hijoderecho).ultimos;

                }

            } else if (raiz.hijos.get(hijoizquierdo).primeros.equals("") && !raiz.hijos.get(hijoderecho).primeros.equals("")) {
                //el caso en que venga un hijo izquierdo sin valor y un hijo derecho con valor

                primerosYultimos(raiz.hijos.get(hijoizquierdo));
                primerosYultimos(raiz.hijos.get(hijoderecho));

                if (raiz.hijos.get(hijoizquierdo).anulable.equals("V")) {

                    raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros + "," + raiz.hijos.get(hijoderecho).primeros;

                } else {
                    raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros;

                }

                if (raiz.hijos.get(hijoderecho).anulable.equals("V")) {

                    raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos + "," + raiz.hijos.get(hijoderecho).ultimos;

                } else {
                    raiz.ultimos = raiz.hijos.get(hijoderecho).ultimos;

                }

            } else if (!raiz.hijos.get(hijoizquierdo).primeros.equals("") && raiz.hijos.get(hijoderecho).primeros.equals("")) {
                //el caso en que venga un hijo izquierdo con valor y un hijo derecho sin valor
                primerosYultimos(raiz.hijos.get(hijoizquierdo));
                primerosYultimos(raiz.hijos.get(hijoderecho));

                if (raiz.hijos.get(hijoizquierdo).anulable.equals("V")) {

                    raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros + "," + raiz.hijos.get(hijoderecho).primeros;

                } else {
                    raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros;

                }

                if (raiz.hijos.get(hijoderecho).anulable.equals("V")) {

                    raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos + "," + raiz.hijos.get(hijoderecho).ultimos;

                } else {
                    raiz.ultimos = raiz.hijos.get(hijoderecho).ultimos;

                }

            } else if (!raiz.hijos.get(hijoizquierdo).primeros.equals("") && !raiz.hijos.get(hijoderecho).primeros.equals("")) {
                //el caso en que venga un hijo izquierdo con valor y un hijo derecho sin valor
                primerosYultimos(raiz.hijos.get(hijoizquierdo));
                primerosYultimos(raiz.hijos.get(hijoderecho));

                if (raiz.hijos.get(hijoizquierdo).anulable.equals("V")) {

                    raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros + "," + raiz.hijos.get(hijoderecho).primeros;

                } else {
                    raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros;

                }

                if (raiz.hijos.get(hijoderecho).anulable.equals("V")) {

                    raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos + "," + raiz.hijos.get(hijoderecho).ultimos;

                } else {
                    raiz.ultimos = raiz.hijos.get(hijoderecho).ultimos;

                }
            }

        } else if (raiz.valor.equals("(or)")) {

            //el caso que sea un . y los lados izquierdo y derecho no tenga datos
            if (raiz.hijos.get(hijoizquierdo).primeros.equals("") && raiz.hijos.get(hijoderecho).primeros.equals("")) {

                primerosYultimos(raiz.hijos.get(hijoizquierdo));
                primerosYultimos(raiz.hijos.get(hijoderecho));

                raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros + "," + raiz.hijos.get(hijoderecho).primeros;

                raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos + "," + raiz.hijos.get(hijoderecho).ultimos;

            } else if (raiz.hijos.get(hijoizquierdo).primeros.equals("") && !raiz.hijos.get(hijoderecho).primeros.equals("")) {
                //el caso en que venga un hijo izquierdo sin valor y un hijo derecho con valor
                primerosYultimos(raiz.hijos.get(hijoizquierdo));
                primerosYultimos(raiz.hijos.get(hijoderecho));

                raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros + "," + raiz.hijos.get(hijoderecho).primeros;

                raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos + "," + raiz.hijos.get(hijoderecho).ultimos;

            } else if (!raiz.hijos.get(hijoizquierdo).primeros.equals("") && raiz.hijos.get(hijoderecho).primeros.equals("")) {
                primerosYultimos(raiz.hijos.get(hijoizquierdo));
                primerosYultimos(raiz.hijos.get(hijoderecho));

                raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros + "," + raiz.hijos.get(hijoderecho).primeros;

                raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos + "," + raiz.hijos.get(hijoderecho).ultimos;

            } else if (!raiz.hijos.get(hijoizquierdo).primeros.equals("") && !raiz.hijos.get(hijoderecho).primeros.equals("")) {
                //el caso en que venga un hijo izquierdo con valor y un hijo derecho sin valor
                primerosYultimos(raiz.hijos.get(hijoizquierdo));
                primerosYultimos(raiz.hijos.get(hijoderecho));

                raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros + "," + raiz.hijos.get(hijoderecho).primeros;

                raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos + "," + raiz.hijos.get(hijoderecho).ultimos;

            }

        } else if (raiz.valor.equals("*")) {

            primerosYultimos(raiz.hijos.get(hijoizquierdo));

            raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros;

            raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos;

        } else if (raiz.valor.equals("+")) {

            primerosYultimos(raiz.hijos.get(hijoizquierdo));

            raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros;

            raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos;

        } else if (raiz.valor.equals("?")) {

            primerosYultimos(raiz.hijos.get(hijoizquierdo));

            raiz.primeros = raiz.hijos.get(hijoizquierdo).primeros;

            raiz.ultimos = raiz.hijos.get(hijoizquierdo).ultimos;

        } else {

            return;
        }
        return;

    }

    public void agregar_simbolos_tabla_siguientes(Nodo raiz, LinkedList<Siguientes> lista) {

        if (raiz.hijos.isEmpty()) {//si son nodos hoja

            lista.add(new Siguientes(raiz.valor, String.valueOf(indice)));
            indice++;

        } else {

            for (Nodo hijo : raiz.hijos) {

                //siempre toma primero al hijo 1 y despues al hijo2
                agregar_simbolos_tabla_siguientes(hijo, lista);
            }
        }
    }

    public void Siguientes(Nodo raiz, LinkedList<Siguientes> lista) {

        if (raiz.valor.equals(".")) {

            Siguientes(raiz.hijos.get(hijoizquierdo), lista);
            Siguientes(raiz.hijos.get(hijoderecho), lista);

            String[] ultimos;
            ultimos = raiz.hijos.get(hijoizquierdo).ultimos.split(",");

            for (String ultimo : ultimos) {

                for (Siguientes subi : lista) {

                    if (ultimo.equals(subi.numHoja)) {

                        String[] primeros;
                        primeros = raiz.hijos.get(hijoderecho).primeros.split(",");

                        for (String primero : primeros) {

                            subi.siguientes.add(primero);
                        }

                    }

                }

            }

        } else if (raiz.valor.equals("*")) {

            Siguientes(raiz.hijos.get(hijoizquierdo), lista);

            String[] ultimos;
            ultimos = raiz.ultimos.split(",");

            for (String ultimo : ultimos) {

                for (Siguientes subi : lista) {

                    if (ultimo.equals(subi.numHoja)) {

                        String[] primeros;
                        primeros = raiz.primeros.split(",");

                        for (String primero : primeros) {

                            subi.siguientes.add(primero);
                        }

                    }

                }

            }

        } else if (raiz.valor.equals("+")) {

            Siguientes(raiz.hijos.get(hijoizquierdo), lista);

            String[] ultimos;
            ultimos = raiz.ultimos.split(",");

            for (String ultimo : ultimos) {

                for (Siguientes subi : lista) {

                    if (ultimo.equals(subi.numHoja)) {

                        String[] primeros;
                        primeros = raiz.primeros.split(",");

                        for (String primero : primeros) {

                            subi.siguientes.add(primero);
                        }

                    }

                }

            }

        } else if (raiz.valor.equals("?")) {

            Siguientes(raiz.hijos.get(hijoizquierdo), lista);

            String[] ultimos;
            ultimos = raiz.ultimos.split(",");

            for (String ultimo : ultimos) {

                for (Siguientes subi : lista) {

                    if (ultimo.equals(subi.numHoja)) {

                        String[] primeros;
                        primeros = raiz.primeros.split(",");

                        for (String primero : primeros) {

                            subi.siguientes.add(primero);
                        }

                    }

                }

            }

        
        
        } else {
            //es una hoja y no interesa
            return;
        }

    }
    
    int index=0;
    int estado=0;
    
    public void Tabla_transiciones(Nodo raiz,LinkedList<Transicion> lista_transicion,LinkedList<Siguientes> lista_siguientes){
        
        
        int cantidad_de_Estados_nuevos=0;
       //tomamos la raiz inicial , como primer conjunto
        lista_transicion.add(new Transicion("S0", raiz.primeros));
        
        System.out.println(lista_transicion.size());
        //tomamos los elementos del conjunto inicial
        System.out.println("imprimiendo conjuntos:"+lista_transicion.get(index).conjunto);
        
        String [] elementosDelConjunto= lista_transicion.get(index).conjunto.split(",");
        //verificamos que no haya union de conjunto y si hay los detectamos 
        //la tabla hash nos permite verificar esta informacion
        LinkedList<String> taux=new LinkedList<>();
        LinkedList<String> tnum=new LinkedList<>();
        String simbolo="";
        for(String datoActual:elementosDelConjunto){
           System.out.println("DATO ACTUAL: "+ datoActual);
           if(datoActual.equals("#")){
           simbolo="#";
          
           System.out.println("SIMBOLO:"+ simbolo);
           
           }else{
            simbolo=lista_siguientes.get(Integer.valueOf(datoActual)-1).simbolo;
           System.out.println("SIMBOLO:"+ simbolo);
           }
         
        if(taux.contains(simbolo)){
            
         int pos= taux.indexOf(simbolo);
         String datoanterior= tnum.get(pos);
         tnum.set(pos, datoanterior+"¬"+datoActual);
        
        }else{
            
              if(simbolo.equals("#")){
            taux.add(simbolo);
            tnum.add(String.valueOf(lista_siguientes.size()));
              }else{
            taux.add(simbolo);
            tnum.add(datoActual);
              }
          
                   
        }
        }
        //entonces esta tabla ya esta clasificada y procedemos a buscar los nuevos estados
        System.out.println("inicia analisis del elemento");
            for (String con : tnum) {
               System.out.println("dato analisis: "+ con);
           
               String concatenaconjunto="";
              if(con.contains("¬")){
              String[] numsiguientes=con.split("¬");
             // sim=lista_siguientes.get(Integer.valueOf(numsiguientes[0])-1).simbolo;
              for(String e:numsiguientes){
              //vamos a buscar los siguientes de cada uno y los concatenamos
                 LinkedList<String> sig=  lista_siguientes.get(Integer.valueOf(e)-1).siguientes;
                  for (String dato : sig) {
                      if(concatenaconjunto.contains(dato)){
                      //no lo agrega
                      }else{
                      if(sig.getLast().equals(dato)){
                          concatenaconjunto+=dato;
                      }else{
                      concatenaconjunto+=dato+",";
                      }
                      }
                 }                            
              }             
              }else{
                
                  LinkedList<String> sig=  lista_siguientes.get(Integer.valueOf(con)-1).siguientes;
               
              for (String dato : sig) {
                     
                      if(sig.getLast().equals(dato)){
                          concatenaconjunto+=dato;
                      }else{
                      concatenaconjunto+=dato+",";
                      }
                      
                 }
                  
              }
              
       
      
                   System.out.println("datos del nuevo conjunto: "+ concatenaconjunto+"\n");
        
           //aqui analizo el conjunto nuevo
               int tamanio=lista_transicion.size();
                System.out.println("verificando tamaño: "+ tamanio);
                boolean encontrado=false;
                for (int j = 0; j < tamanio; j++) {
                    
                  System.out.println("Concatena_conjunto_:"+concatenaconjunto+" <-> "+" listaTrasnsicion: "+lista_transicion.get(j).conjunto);
                  if(concatenaconjunto.equals(lista_transicion.get(j).conjunto)){
                  lista_transicion.get(index).estados_siguientes.add("S"+String.valueOf(j)+"°"+simbolo);
                   encontrado=true;
                   }            
                  }
                
                if(encontrado){
                //ya fue insertado
                }else{
               
                    if(!con.equals(lista_siguientes.getLast().numHoja)){
                  estado++;
                  lista_transicion.get(index).estados_siguientes.add("S"+String.valueOf(estado)+"°"+simbolo);
                  lista_transicion.add(new Transicion("S"+String.valueOf(estado), concatenaconjunto));
                  cantidad_de_Estados_nuevos++;
                  }else{
                        
                  estado++;
                  lista_transicion.get(index).estados_siguientes.add("S"+String.valueOf(estado)+"%"+simbolo);
                  lista_transicion.add(new Transicion("S"+String.valueOf(estado), concatenaconjunto));
                 
                  return;
                    }
                }
                
                
                System.out.println("Cantidad estados nuevos:"+cantidad_de_Estados_nuevos);
                System.out.println(" vuelvo a retornar");
            }
            
                           //analizar siguientes de esos datos
       
       index++;
       obtener_estados(cantidad_de_Estados_nuevos, lista_transicion, lista_siguientes);
          
     }
  
    public void obtener_estados(int cantidad_elemetos,LinkedList<Transicion> lista_transicion,LinkedList<Siguientes> lista_siguientes){
    if(cantidad_elemetos==0){
    return;
    }else{
     
           int cantidad_de_Estados_nuevos=0;
       //tomamos la raiz inicial , como primer conjunto
        
        
        System.out.println(lista_transicion.size());
        //tomamos los elementos del conjunto inicial
        System.out.println("imprimiendo conjuntos:"+lista_transicion.get(index).conjunto);
        
        String [] elementosDelConjunto= lista_transicion.get(index).conjunto.split(",");
        //verificamos que no haya union de conjunto y si hay los detectamos 
        //la tabla hash nos permite verificar esta informacion
        LinkedList<String> taux=new LinkedList<>();
        LinkedList<String> tnum=new LinkedList<>();
        String simbolo="";
        for(String datoActual:elementosDelConjunto){
           System.out.println("DATO ACTUAL: "+ datoActual);
        
     
            
      if(datoActual.equals("#")){
           simbolo="#";
          
           System.out.println("SIMBOLO:"+ simbolo);
           
           }else{
            simbolo=lista_siguientes.get(Integer.valueOf(datoActual)-1).simbolo;
           System.out.println("SIMBOLO:"+ simbolo);
           }
         
        if(taux.contains(simbolo)){
            
         int pos= taux.indexOf(simbolo);
         String datoanterior= tnum.get(pos);
         tnum.set(pos, datoanterior+"¬"+datoActual);
        
        }else{
            
              if(simbolo.equals("#")){
            taux.add(simbolo);
            tnum.add(String.valueOf(lista_siguientes.size()));
              }else{
            taux.add(simbolo);
            tnum.add(datoActual);
              }
          
                   
        }
        
        
       
        }
        //entonces esta tabla ya esta clasificada y procedemos a buscar los nuevos estados
        System.out.println("inicia analisis del elemento");
            for (String con : tnum) {
               System.out.println("dato analisis: "+ con);
           
               String concatenaconjunto="";
              if(con.contains("¬")){
              String[] numsiguientes=con.split("¬");
             // sim=lista_siguientes.get(Integer.valueOf(numsiguientes[0])-1).simbolo;
              for(String e:numsiguientes){
              //vamos a buscar los siguientes de cada uno y los concatenamos
                 LinkedList<String> sig=  lista_siguientes.get(Integer.valueOf(e)-1).siguientes;
                  for (String dato : sig) {
                      if(concatenaconjunto.contains(dato)){
                      //no lo agrega
                      }else{
                      if(sig.getLast().equals(dato)){
                          concatenaconjunto+=dato;
                      }else{
                      concatenaconjunto+=dato+",";
                      }
                      }
                 }                            
              }             
              }else{
                
                  LinkedList<String> sig=  lista_siguientes.get(Integer.valueOf(con)-1).siguientes;
               
              for (String dato : sig) {
                     
                      if(sig.getLast().equals(dato)){
                          concatenaconjunto+=dato;
                      }else{
                      concatenaconjunto+=dato+",";
                      }
                      
                 }
                  
              }
              
       
      
            System.out.println("datos del nuevo conjunto: "+ concatenaconjunto+"\n");
           simbolo=lista_siguientes.get(Integer.valueOf(con)-1).simbolo;
           //aqui analizo el conjunto nuevo
               int tamanio=lista_transicion.size();
                System.out.println("verificando tamaño: "+ tamanio);
                boolean encontrado=false;
                for (int j = 0; j < tamanio; j++) {
                    
                  System.out.println("Concatena_conjunto_:"+concatenaconjunto+" <-> "+" listaTrasnsicion: "+lista_transicion.get(j).conjunto);
                  if(concatenaconjunto.equals(lista_transicion.get(j).conjunto)){
                  lista_transicion.get(index).estados_siguientes.add("S"+String.valueOf(j)+"°"+simbolo);
                   encontrado=true;
                   }else if(concatenaconjunto.equals(" ") || concatenaconjunto.equals("")){
                   encontrado=true;
                   }         
                  }
                
                if(encontrado){
                //ya fue insertado
                }else{
               
                    if(!con.equals(lista_siguientes.getLast().numHoja)){
                  estado++;
                  lista_transicion.get(index).estados_siguientes.add("S"+String.valueOf(estado)+"°"+simbolo);
                  lista_transicion.add(new Transicion("S"+String.valueOf(estado), concatenaconjunto));
                  cantidad_de_Estados_nuevos++;
                  }else{
                        
                  estado++;
                  lista_transicion.get(index).estados_siguientes.add("S"+String.valueOf(estado)+"°"+simbolo);
                  lista_transicion.add(new Transicion("S"+String.valueOf(estado), concatenaconjunto));
                 cantidad_de_Estados_nuevos++;
                  
                    }
                }
                
                
                System.out.println("Cantidad estados nuevos:"+cantidad_de_Estados_nuevos);
                System.out.println(" vuelvo a retornar");
            }
            
                           //analizar siguientes de esos datos
       
       index++;
       obtener_estados(cantidad_de_Estados_nuevos, lista_transicion, lista_siguientes);
        
        
    }
    }
          
 }

    