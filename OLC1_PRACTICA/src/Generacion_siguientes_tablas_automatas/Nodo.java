package Generacion_siguientes_tablas_automatas;

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
public class Nodo {

    public String anulable;
    public String valor;
    public LinkedList<Nodo> hijos;
    public String numero_hoja;
    public String primeros;
    public String ultimos;

    public Nodo(String anulable, String valor, String numeero_hoja, String primeros, String ultimos) {
        this.anulable = anulable;
        this.valor = valor;
        this.numero_hoja = numeero_hoja;
        this.primeros = primeros;
        this.ultimos = ultimos;
        this.hijos = new LinkedList<>();
    }

    public void agregarHijo(Nodo hijo) {
        this.hijos.add(hijo);
    }

}
