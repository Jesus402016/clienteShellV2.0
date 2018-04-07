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
import ufps.cliente.chat;

/**
 *
 * @author Jesus
 */
public class Controlador extends Thread {

    public chat chat;
    private String nombre = "";
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private boolean activo = true;

    public Controlador(String nombre,String host) {
        this.chat = new chat(nombre, this,host);
    }

    @Override
    public void run() {

        while (activo) {
            System.out.print("");
            if (this.chat.getContectados()) {
                try {
                    System.out.print("\n" + "Nuevo Mensaje:" + "");
                    String mensaje = br.readLine();
                    if(mensaje.equals("R")){
                      
                    }else{
                    enviarMensaje(mensaje, getNombre());
                    }
                } catch (IOException ex) {
                    setNombre("");
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//Metodo para enviar Mensaje al destino 
    public void enviarMensaje(String mensaje, String nombre) {
        try {
            this.chat.enviarMensaje(mensaje, nombre);
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
            System.out.print("\n" + "Nuevo Mensaje:" + "");
            String mensaje = br.readLine();
            if (mensaje.contains(",")) {
                String[] dataMessage = mensaje.split(",");
                enviarMensaje(dataMessage[1], dataMessage[0]);
            } else {
                System.out.println("Formato de chat incorrecto.");
                this.requestMessage();
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
