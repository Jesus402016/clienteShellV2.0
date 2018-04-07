/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.cliente;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;
import ufps.cliente.DTO.usuario;
import ufps.controlador.Controlador;

/**
 *
 * @author Jesus
 */
public class chat extends Thread {
    
    private usuario usuario;
    private String host = "http://";
    private int puerto = 3000;
    private boolean activo;
    private int conectados;
    private Controlador controlador;
    
    private interface MessagesTypes {
        
        public static String CHAT = "chat";
        public static String CONNECTION = "connection_user";
        public static String SELECT_ROOM = "select_room";
    };
    
    private interface MessageKeys {
        
        public static String ORIGIN = "origin";
        public static String DESTINATION = "destination";
        public static String MESSAGE = "message";
    };
    
    public chat(String nombre, Controlador con,String host) {
        this.usuario = new usuario(nombre);
        this.host+=host+":"+puerto;
        this.controlador = con;
        this.start();
    }
    
    @Override
    public void run() {
        try {
            Socket socket = IO.socket(host);
            socket.on(Socket.EVENT_CONNECT, (Object... args) -> {
                socket.emit(MessagesTypes.CONNECTION, getUserJson());
            }).on(MessagesTypes.CONNECTION, (Object... args) -> {
                System.out.print("usuario connectados:" + args[0].toString().concat("\n"));
                //controlador.requestMessage();
            }).on(MessagesTypes.CHAT, (Object... args) -> {
                JSONObject obj = (JSONObject) args[0];
                String origin = obj.getString(MessageKeys.ORIGIN);
                String destination = obj.getString(MessageKeys.DESTINATION);
                String message = obj.getString(MessageKeys.MESSAGE);
                if (origin.equals(usuario.getNombre())) {
                    System.out.println("Mensaje tuyo a ".concat(destination).concat(" :").concat(message));
                } else if(destination.equals("Global")){
                    System.out.println("Mensaje global de ".concat(origin).concat(" :").concat(message));
                } else if(destination.equals(usuario.getNombre())){
                    System.out.println("Mensaje privado de ".concat(origin).concat(" :").concat(message));
                }
                //controlador.requestMessage();
            });
            usuario.setSocket(socket);
            this.usuario.getSocket().connect();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }
    
    private JSONObject getUserJson() {
        JSONObject userJson = new JSONObject();
        userJson.put("userName", this.usuario.getNombre());
        return userJson;
    }
    
    private JSONObject getMessageJson(String destination, String message) {
        JSONObject userJson = new JSONObject();
        userJson.put(MessageKeys.ORIGIN, this.usuario.getNombre());
        userJson.put(MessageKeys.DESTINATION, destination);
        userJson.put(MessageKeys.MESSAGE, message);
        return userJson;
    }

    
    public void enviarMensaje(String mensaje, String destino) throws IOException {
        usuario.getSocket().emit(MessagesTypes.CHAT, getMessageJson(destino, mensaje));
    }
    
   
    

    //Metodo que retorna todos los conectados
    public void conectados(String[] list) {
        String cad = "Conectados:" + "(";
        for (int i = 1; i < list.length; i++) {
            cad += list[i] + "" + ",";
        }
        cad += ")";
        System.out.println(cad);
        this.setConectados(list.length);
        //System.out.print(getConectados());
    }
    
    public boolean getContectados() {
        if (this.getConectados() > 2) {
            return true;
        } else {
            return false;
        }
    }
    
    public void responder(String nombre) throws IOException {
        
        System.out.print("\n" + "Nuevo Mensaje:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String mensaje = br.readLine();
        this.enviarMensaje(mensaje, nombre);
        
    }

    /**
     * @return the conectados
     */
    public int getConectados() {
        return conectados;
    }

    /**
     * @param conectados the conectados to set
     */
    public void setConectados(int conectados) {
        this.conectados = conectados;
    }

    public usuario getUsuario() {
        return usuario;
    }
    
    
}
