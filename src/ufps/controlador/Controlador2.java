/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufps.cliente.DTO.usuario;
import ufps.cliente.chat;

/**
 *
 * @author Jesus
 */
public class Controlador2 extends Thread {

    private chat chat;
    private String nombre = "";
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private boolean activo = true;
    private Controlador controllador;
    private escuchar mic;
    public Controlador2(chat chat, Controlador controlador) {
        this.chat = chat;
        this.controllador = controlador;
        mic=new escuchar(this);
    }

    @Override
    public void run() {
        while (activo) {
            requestMessage();
        }
    }

//Metodo para enviar Mensaje al destino 
    public void enviarMensaje(String mensaje, String nombre) {
        try {
            this.chat.enviarMensaje(nombre, mensaje);
        } catch (IOException ex) {
            System.out.println("Error al enviar mensaje");
        }

    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void requestMessage() {
        try {
            String mensaje = br.readLine();
            if(mensaje.equals("P")){
             mic.parar();
            }else if(mensaje.equals("R")){             
              mic.reconocimiento();
            }else{
              
            if(!mensaje.contains(",")){
                enviarMensaje("Global", mensaje);
            }else
            if (mensaje.contains(",")) {
                String[] dataMessage = mensaje.split(",");
                enviarMensaje(dataMessage[0], dataMessage[1]);
            }
            }//else {
                //System.out.println("Formato de chat incorrecto.");
                //this.requestMessage();
            //}
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
