package org.levisalazar.Controller;



import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent; 
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.levisalazar.dao.UsuarioDAO;
import org.levisalazar.model.Usuario;
import org.levisalazar.util.SecurityUtil;



public class InicioSesionController implements Initializable {
    
   @FXML  private TextField txtUsuario; 
    @FXML  private PasswordField txtPassword; 
    @FXML  private Button btnIniciarSesion; 
    @FXML  private Label lblMensaje; 
    @FXML private UsuarioDAO usuarioDAO; 
    

  @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAO();
        lblMensaje.setText("");
    }    
    @FXML
    public void eventoIniciarSesion(ActionEvent evento) {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText(); 
        
       
        if (usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Por favor, Complete todos sus datos.");
            return; 
        }
        
       
        String passwordHash = SecurityUtil.hashSHA256Password(password);
        Usuario usuarioIniciado = usuarioDAO.iniciarSesion(usuario, passwordHash);
          
        if (usuarioIniciado != null) {
            lblMensaje.setText("Inicio correcto");
            abrirDashboard(usuarioIniciado);
        } else { 
            lblMensaje.setText("Usuario o contraseña incorrectos");
        }
    } 
    
    private void abrirDashboard(Usuario usuario){
        String rutaFXML = ""; 
        String tituloDashboard = ""; 
        
        switch (usuario.getRol().toLowerCase()) {
            case "admin":
                rutaFXML = "/org/levisalazar/View/AdminDashboradView.fxml";
                tituloDashboard = "Panel de Administracion";
                break;
                
            case "empleado":
                 rutaFXML = "/org/levisalazar/View/EmpleadoDashboardView.fxml";
                 tituloDashboard = "Panel de Empleado";  
                    break;  
            case "cajero": 
                rutaFXML = "/org/levisalazar/View/CajeroDashboardView.fxml";
                 tituloDashboard = "Panel de Cajero"; 
                 break; 
                
        }
        try {
           FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent raiz = cargadorFXML.load();
           switch (usuario.getRol().toLowerCase()) {
               case "admin":
                   AdminDashboradController adminController = cargadorFXML.getController();
                   adminController.IniciarUsuario(usuario);
                   break;
               case "empleado":
                   EmpleadoDashboardController empleadoController = cargadorFXML.getController();
                   empleadoController.IniciarUsuario(usuario);
                   break;
               case "cajero":
                   CajeroDashboardController cajeroController = cargadorFXML.getController();
                   cajeroController.IniciarUsuario(usuario);
                   break;
           }
            Stage escenario = new Stage(); 
           escenario.setScene(new Scene(raiz));
           escenario.setTitle(tituloDashboard);
           escenario.show();
           
           // 2. Cerrar la ventana de login DESPUÉS
           Stage escenaActual = (Stage) btnIniciarSesion.getScene().getWindow(); 
           escenaActual.close();
            
        } catch (IOException e) {
            System.err.println("ERROR al cargar la vista :" + rutaFXML+ e.getMessage());
            lblMensaje.setText("ERROR interno");
        }
         }
    
}


