/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.main;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ufps.controlador.Controlador;
import ufps.controlador.Controlador2;

/**
 *
 * @author Jesus
 */
public class main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static private Controlador con;
    static private Controlador2 con2;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String cad = 
                  "################################################################" + "\n"
                + "################### BIENVENIDO CHAT 2.0  V######################" + "\n"
                + "###################### Politicas de Uso ########################" + "\n"
                + "################## Ingrese nombre del usuario ##################" + "\n"
                + "################# si desea un mensaje privado:  ################" + "\n"
                + "########### nombre del usuario + la letra ','+ mensaje #########" + "\n"
                + "####### para activar el reconocimiento de voz oprime la tecla R  ##"+"\n"
                + "################ Y despues la tecla ENTER     #####################"+"\n"
                + "###########  Para parar presione la tecla P y enter################"+"\n"
                + "############     Para Salir S y despues ENTER#####################"+"\n"
                + "################################################################";
        
        System.out.print(cad);
        System.out.print("\n" + "Ingrese la direccion del Servidor:");
        String servidor = br.readLine();        
        System.out.print("\n" + "Ingrese su Nombre de Usuario:");
        String nombre = br.readLine();
        con = new Controlador(nombre,servidor);
        con2 = new Controlador2(con.chat, con);
        con.start();
        con2.start();
       
   
    }

}
