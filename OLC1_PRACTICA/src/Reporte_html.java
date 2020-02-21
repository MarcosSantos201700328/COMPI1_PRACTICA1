/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Reestructurado_expresiones.Expresion_Lexema;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.LinkedList;

/**
 *
 * @author marco
 */
public class Reporte_html {

    public void escribir_fichero_tokens(LinkedList<token_del_lenguaje> datos) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("Tokens.html");
            pw = new PrintWriter(fichero);

            //pw.println(info);
            pw.println("<!DOCTYPE html>");
            pw.println("<head>");
            pw.println("<title>R e p o r t e</title>");
            pw.println("<style>");
            pw.println("table {");
            pw.println("font-family: arial, sans-serif;");
            pw.println("border: 1px solid #dddddd;");
            pw.println("width: 100%;");
            pw.println("}");
            pw.println("td, th {");
            pw.println("border: 1px solid #dddddd;");
            pw.println("text-align: left;");
            pw.println("padding: 8px;");
            pw.println("}");
            pw.println("th{");
            pw.println("background-color:green;");
            pw.println("color: white;");
            pw.println("}");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h2>TABLA DE TOKENS</h2>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>NO.</th>");
            pw.println("<th>LEXEMA</th>");
            pw.println("<th>TIPO</th>");
            pw.println("<th>FILA</th>");
            pw.println("<th>COLUMNA</th>");
            pw.println("</tr>");
            pw.println("<tr>");

            int i = 1;
            for (token_del_lenguaje dato : datos) {
                pw.println("<td>" + i + "</td>");
                pw.println("<td>" + dato.getLexema() + "</td>");
                pw.println("<td>" + dato.tipo_detallado(dato.getTipo()) + "</td>");
                pw.println("<td>" + dato.getFila() + "</td>");
                pw.println("<td>" + dato.getColumna() + "</td>");

                pw.println("</tr>");

                i++;
            }

            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");

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

    public void escribir_fichero_erroresTokens(LinkedList<token_del_lenguaje> datos) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("Errores_lexicos.html");
            pw = new PrintWriter(fichero);

            //pw.println(info);
            pw.println("<!DOCTYPE html>");
            pw.println("<head>");
            pw.println("<title>R e p o r t e</title>");
            pw.println("<style>");
            pw.println("table {");
            pw.println("font-family: arial, sans-serif;");
            pw.println("border: 1px solid #dddddd;");
            pw.println("width: 100%;");
            pw.println("}");
            pw.println("td, th {");
            pw.println("border: 1px solid #dddddd;");
            pw.println("text-align: left;");
            pw.println("padding: 8px;");
            pw.println("}");
            pw.println("th{");
            pw.println("background-color:red;");
            pw.println("color: white;");
            pw.println("}");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h2>ERRORES LEXICOS</h2>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>NO.</th>");
            pw.println("<th>LEXEMA</th>");
            pw.println("<th>TIPO</th>");
            pw.println("<th>FILA</th>");
            pw.println("<th>COLUMNA</th>");
            pw.println("</tr>");
            pw.println("<tr>");

            int i = 1;
            for (token_del_lenguaje dato : datos) {
                pw.println("<td>" + i + "</td>");
                pw.println("<td>" + dato.getLexema() + "</td>");
                pw.println("<td>" + dato.tipo_detallado(dato.getTipo()) + "</td>");
                pw.println("<td>" + dato.getFila() + "</td>");
                pw.println("<td>" + dato.getColumna() + "</td>");

                pw.println("</tr>");

                i++;
            }

            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");

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

    public void escribir_fichero_macros(LinkedList<Expresion_Lexema> datos) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("macros.html");
            pw = new PrintWriter(fichero);

            //pw.println(info);
            pw.println("<!DOCTYPE html>");
            pw.println("<head>");
            pw.println("<title>R e p o r t e</title>");
            pw.println("<style>");
            pw.println("table {");
            pw.println("font-family: arial, sans-serif;");
            pw.println("border: 1px solid #dddddd;");
            pw.println("width: 100%;");
            pw.println("}");
            pw.println("td, th {");
            pw.println("border: 1px solid #dddddd;");
            pw.println("text-align: left;");
            pw.println("padding: 8px;");
            pw.println("}");
            pw.println("th{");
            pw.println("background-color:blue;");
            pw.println("color: white;");
            pw.println("}");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h2>MACROS DEL ARCHIVO</h2>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>NO.</th>");
            pw.println("<th>ID</th>");
            pw.println("<th>MACRO</th>");

            pw.println("</tr>");
            pw.println("<tr>");

            int i = 1;
            for (Expresion_Lexema dato : datos) {
                pw.println("<td>" + i + "</td>");
                pw.println("<td>" + dato.getIdentiicador() + "</td>");
                pw.println("<td>" + dato.getContenido() + "</td>");

                pw.println("</tr>");

                i++;
            }

            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");

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

    public void escribir_fichero_lexemas(LinkedList<Expresion_Lexema> datos) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("lexemas.html");
            pw = new PrintWriter(fichero);

            //pw.println(info);
            pw.println("<!DOCTYPE html>");
            pw.println("<head>");
            pw.println("<title>R e p o r t e</title>");
            pw.println("<style>");
            pw.println("table {");
            pw.println("font-family: arial, sans-serif;");
            pw.println("border: 1px solid #dddddd;");
            pw.println("width: 100%;");
            pw.println("}");
            pw.println("td, th {");
            pw.println("border: 1px solid #dddddd;");
            pw.println("text-align: left;");
            pw.println("padding: 8px;");
            pw.println("}");
            pw.println("th{");
            pw.println("background-color:blue;");
            pw.println("color: white;");
            pw.println("}");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h2>LEXEMAS DEL ARCHIVO</h2>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>NO.</th>");
            pw.println("<th>ID</th>");
            pw.println("<th>LEXEMA</th>");

            pw.println("</tr>");
            pw.println("<tr>");

            int i = 1;
            for (Expresion_Lexema dato : datos) {
                pw.println("<td>" + i + "</td>");
                pw.println("<td>" + dato.getIdentiicador() + "</td>");
                pw.println("<td>" + dato.getContenido() + "</td>");

                pw.println("</tr>");

                i++;
            }

            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");

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

        public void escribir_fichero_expresiones(LinkedList<Expresion_Lexema> datos) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("expresiones.html");
            pw = new PrintWriter(fichero);

            //pw.println(info);
            pw.println("<!DOCTYPE html>");
            pw.println("<head>");
            pw.println("<title>R e p o r t e</title>");
            pw.println("<style>");
            pw.println("table {");
            pw.println("font-family: arial, sans-serif;");
            pw.println("border: 1px solid #dddddd;");
            pw.println("width: 100%;");
            pw.println("}");
            pw.println("td, th {");
            pw.println("border: 1px solid #dddddd;");
            pw.println("text-align: left;");
            pw.println("padding: 8px;");
            pw.println("}");
            pw.println("th{");
            pw.println("background-color:yellow;");
            pw.println("color: white;");
            pw.println("}");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h2>EXPRESIONES REGULARES DEL ARCHIVO</h2>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>NO.</th>");
            pw.println("<th>ID</th>");
            pw.println("<th>EXPREISON REGULAR</th>");

            pw.println("</tr>");
            pw.println("<tr>");

            int i = 1;
            for (Expresion_Lexema dato : datos) {
                pw.println("<td>" + i + "</td>");
                pw.println("<td>" + dato.getIdentiicador() + "</td>");
                pw.println("<td>" + dato.getContenido() + "</td>");

                pw.println("</tr>");

                i++;
            }

            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");

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
  
        public void mostrar_reporte_tokens() {
        try {

            File objetofile = new File("Tokens.html");
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }
    }

    public void mostrar_reporte_errores_lexicos() {
        try {

            File objetofile = new File("Errores_lexicos.html");
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }
    }

    public void mostrar_reporte_macros() {
        try {

            File objetofile = new File("macros.html");
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }
    }

    public void mostrar_reporte_lexemas() {
        try {

            File objetofile = new File("lexemas.html");
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }
    }
    
     public void mostrar_reporte_expresiones() {
        try {

            File objetofile = new File("expresiones.html");
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }
    }
}
