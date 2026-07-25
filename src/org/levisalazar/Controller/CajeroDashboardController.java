package org.levisalazar.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.levisalazar.model.Usuario;


public class CajeroDashboardController extends DashboardController implements Initializable  {
    @FXML Label lblBienvenida; 
    private Usuario usuarioActual;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //TODO 
    }    
    
     @Override
     public void IniciarUsuario(Usuario usuario){
         this.usuarioActual = usuario; 
         lblBienvenida.setText("Bienvenido cajero: " + usuario.getUsername());
                 
     }
    
}
