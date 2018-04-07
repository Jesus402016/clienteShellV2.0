/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.controlador;
import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import net.sourceforge.javaflacencoder.FLACFileWriter;
/**
 *
 * @author Jesus
 */
public class  escuchar {
    Controlador con1;
    Controlador2 con2;
    Microphone mic = new Microphone(FLACFileWriter.FLAC);
    GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
    GSpeechResponseListener escuchar=null;
    String output="";
    String old_text="";
    
    /**
     * constructor controlador 1
     * @param con1 
     */
    public escuchar(Controlador con1){
        this.duplex.setLanguage("es");
        this.con1=con1;
        
    }
    /**
     * constructor controlador dos
     * @param con2 
     */
    public escuchar(Controlador2 con2){
        this.duplex.setLanguage("es");
        this.con2=con2;
    }
    
    public void reconocimiento(){
        try {
           
            duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());

            System.out.println("no se pudo");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       
          escuchar=new GSpeechResponseListener() {	
			public void onResponse(GoogleResponse gr) {
                                if(gr.getResponse()!=null)                    
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					old_text = output;
					if (old_text.contains("(")) {
						old_text = old_text.substring(0, old_text.indexOf('('));
					}
                                        
					System.out.println("Mensaje Enviado");
					//this.old_text = ( response.getText() + "\n" );
				        old_text = old_text.replace(")", "").replace("( ", "");
					//response.setText(this.old_text);
                                        if(con1 instanceof Controlador){
                                        con1.enviarMensaje(old_text, "Global");
                                        }else{                                        
                                        con2.enviarMensaje("Global",old_text);
                                        }
                                        System.out.println(old_text);                                        
                                        old_text="";
                                        output="";                                        
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				
				
			}
                        
                };
          duplex.addResponseListener(escuchar);
          
         
    }
    
   
    
    public void parar(){
                                mic.close();
                                duplex.removeResponseListener(escuchar);
                                this.escuchar=null;
                                System.out.println("microfono parado");
				
    }
}

