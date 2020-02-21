package Reestructurado_expresiones;


import Generacion_siguientes_tablas_automatas.Nodo;
import Generacion_siguientes_tablas_automatas.Siguientes;
import Generacion_siguientes_tablas_automatas.Tabla_siguientes;
import Generacion_siguientes_tablas_automatas.Transicion;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
public class Graficadora {
//grafica lo que sea en lenguaje dot 
    private int pos = 0;

    public void generar_Dot_grafo(String nombre) {
        try {

            String dotPath = "C:\\release\\bin\\dot.exe";

            String fileInputPath = nombre + ".txt";
            String fileOutputPath ="C:\\arboles\\"+ nombre + ".jpg";

            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }

    public void escribir_fichero_grafo(String info, String ruta, String nombre) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(nombre + ".txt");
            pw = new PrintWriter(fichero);

            pw.println("digraph grafo{");

            pw.println(info);

            pw.println("}");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public void abrir_imagen_grafo(String nombre) {
        try {

            File objetofile = new File(nombre + ".jpg");
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }
    }

    public String recorrer_arbolito(Nodo nodo) {

        pos = pos + 1;
        String padre = "nodo" + pos;
        String concatena = "";

        //  concatena+= padre + "[label=\""+pos+") valor: "+nodo.valor+"\"];\n";
        concatena += padre + "[ shape=Mrecord,style=filled, fillcolor=slategray4 , color=lightblue4 , label=<<TABLE border=\"0\" cellborder=\"0\" bgcolor=\"lightskyblue4\">\n";
        concatena += "<tr>\n"
                + "<td ></td>\n"
                + "<td border=\"1\">" + nodo.anulable + "</td>\n"
                + "</tr>\n"
                + "\n"
                + "<TR>\n"
                + "<TD  border=\"1\" >" + nodo.primeros + "</TD>\n"
                + "<td border=\"1\" >" + nodo.valor + " </td>\n"
                + "<TD  border=\"1\" >" + nodo.ultimos + "</TD>\n"
                + " </TR>\n"
                + "<tr>\n"
                + "<td></td>\n"
                + "<td border=\"1\">" + nodo.numero_hoja + "</td>\n"
                + "</tr>\n"
                + "</TABLE>>]";
        for (Nodo hijo : nodo.hijos) {

            concatena += padre + "->" + "nodo" + (1 + pos) + ";\n";
            concatena += recorrer_arbolito(hijo);
        }

        return concatena;

    }
    
    
    //_____________________________________________________________________________________
   public void generar_Dot_tabla_siguientes(String nombre) {
        try {

            String dotPath = "C:\\release\\bin\\dot.exe";

            String fileInputPath = nombre + ".txt";
            String fileOutputPath ="C:\\siguientes\\"+ nombre + ".jpg";

            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }

    public void escribir_fichero_tabla_siguientes(String info, String ruta, String nombre) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(nombre + ".txt");
            pw = new PrintWriter(fichero);

            pw.println("digraph grafo{");

            pw.println(info);

            pw.println("}");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public String hacer_tabla_siguientes(LinkedList<Generacion_siguientes_tablas_automatas.Siguientes> siguientes){
    
        String info="";
        
        info+="node [shape=box];\n";
        
        info+="tabla[shape=box, style=filled, fillcolor=white, color=blue, label=<<TABLE border=\"0\" cellborder=\"1\">\n";
        
        info+="<TR>";
        info+="<TD>Simbolo</TD>";
        info+="<TD>i</TD>";
        info+="<TD>Siguientes(i)</TD>";        
        info+="</TR>";
        
        
        for (Siguientes siguiente : siguientes) {
            
            info+="<TR>\n";
            info+="<TD>"+siguiente.simbolo+"</TD>\n";
            info+="<TD>"+siguiente.numHoja+"</TD>\n";
            info+="<TD>";
            
            if(siguiente.siguientes.isEmpty()){
            info+="no data";
            }else{
            
            for (String s : siguiente.siguientes) {
            
                if(siguiente.siguientes.getLast().equals(s)){
                info+=s;
                }else{
                info+=s+",";
                }                
            }
            }
            info+="</TD>\n";
           
            info+="</TR>\n";
                    
        }
        
        
        info+="</TABLE>>]\n" +
        "\n";
        
    return info;
    }

   
    //_________________________________________________________________________________________________________________________--
    
    
    
    public void generar_Dot_tabla_transiciones(String nombre) {
        try {

            String dotPath = "C:\\release\\bin\\dot.exe";

            String fileInputPath = nombre + ".txt";
            String fileOutputPath ="C:\\transiciones\\"+ nombre + ".jpg";

            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }
    
    public void escribir_fichero_tabla_transiciones(String info, String ruta, String nombre) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(nombre + ".txt");
            pw = new PrintWriter(fichero);

            pw.println("digraph grafo{");

            pw.println(info);

            pw.println("}");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
    
     public String hacer_table_transiciones(LinkedList<Generacion_siguientes_tablas_automatas.Transicion> transiciones){
     
          String info="";
        
        info+="node [shape=box];\n";
        
        info+="tabla[shape=box, style=filled, fillcolor=white, color=blue, label=<<TABLE border=\"0\" cellborder=\"1\">\n";
        
        info+="<TR>";
        info+="<TD>Simbolo</TD>";
        info+="<TD>Transiciones</TD>";
               
        info+="</TR>\n";
        
        
         for (Transicion transicion : transiciones) {
             
             info+="<TR>\n";
             
             info+="<TD>"+transicion.estado+"</TD>\n";
             
             info+="<TD>";
               
             if(!transicion.estados_siguientes.isEmpty()){
                
                 if(transicion.estados_siguientes.size()>=2){
                 if(transicion.estados_siguientes.getFirst().equals(transicion.estados_siguientes.get(1))){
                 
                     transicion.estados_siguientes.pop();
                 }                 
                 }
                 
             for (String trans : transicion.estados_siguientes) {
                 
                 if(!transicion.estados_siguientes.getLast().equals(trans)){
                 info+="   "+trans.replace("°", " - ")+"   ,   ";
                 }else{
                 info+="   "+trans.replace("°", " - ");
                 }
             }
             }else{
             info+="estado final de aceptacion";
             }
             info+="</TD>";
             
             
             info+="</TR>\n";
             
         }
        
        
        info+="</TABLE>>]\n" +
        "\n";
         return info;
     }


//___________________________________________________________________________________________________________________________________
     
       public void generar_Dot_automata(String nombre) {
        try {

            String dotPath = "C:\\release\\bin\\dot.exe";

            String fileInputPath = nombre + ".txt";
            String fileOutputPath ="C:\\automatas\\"+ nombre + ".jpg";

            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }
    
      public void escribir_fichero_automata(String info, String ruta, String nombre) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(nombre + ".txt");
            pw = new PrintWriter(fichero);

            pw.println("digraph grafo{");

            pw.println(info);

            pw.println("}");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
    
     public String hacer_automata(LinkedList<Generacion_siguientes_tablas_automatas.Transicion> transiciones,LinkedList<Generacion_siguientes_tablas_automatas.Siguientes> siguiente){
     
         String info="";
           info+="rankdir=LR;\n";
           info+="size=\"8,5\"";
        
        info+="\n";
        
        info+="node [shape = doublecircle]; ";
        //vamos a buscar los estados que contengan en sus conjuntos al simbolo de aceptacion
         for (Transicion transicion : transiciones) {
             
             //verifica que los estados sean de aceptacion
                  if(transicion.conjunto.contains(siguiente.getLast().numHoja)){
                  info+=transicion.estado+" ";
                  }        
         }
        
         info+="\n node [shape = circle];\n";
         
         //ahora hacemos enlaces
          for (Transicion transicion : transiciones) {
             
             
              
              if(transicion.estados_siguientes.isEmpty()){
                  //no hace nada solamente declara el nodo otra vez              
              info+="\n";
              }else{
             
                  for (String trans : transicion.estados_siguientes) {
                      info+=transicion.estado;
                      String [] datos= trans.split("°");
                      //hace el enlace con cada estado
                      info+="->"+datos[0]+"[label=\""+datos[1]+"\"];\n";
                                            
                  }
                  
                  
              }
                  
         }
         
         
        
       
         return info;
     }

///________________________________________________________________________________________________________________________________________
     
}
