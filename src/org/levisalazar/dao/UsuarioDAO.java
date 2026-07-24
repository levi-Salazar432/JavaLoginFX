package org.levisalazar.dao;

// Data Acces Object, objeto de acceso a los datos --> MYSQL usando conexion 

import org.levisalazar.model.Usuario;
import java.sql.SQLException; 
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.CallableStatement; 
import org.levisalazar.util.Conexion;

public class UsuarioDAO {
    
    //InicioSesion
    public Usuario iniciarSesion(String username, String passwordHash){
        Usuario usuario = null; 
        String sql = "{call sp_iniciar_sesion(?,?)}"; 
              
        // try -with-resources -- al final el try, los recursos se cierran auto
        // recursos: connction, ResultSet 
        try (Connection conexion = Conexion.getInstancia().conectar();
                   CallableStatement consulta = conexion.prepareCall(sql)) {
            
            consulta.setString(1, username);
            consulta.setString(2,passwordHash);
            
            try(ResultSet tablaResultado = consulta.executeQuery()){
                //comprobar que haya algo en el resultado
                if (tablaResultado.next()) {
                    //verdadero hay algo(datos) Mapear 
                    usuario = new Usuario(); 
                    usuario.setId(tablaResultado.getInt(1));
                    usuario.setUsername(tablaResultado.getString(2));
                    usuario.setRol(tablaResultado.getString(3));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR en iniciar sesion: " + e.getMessage());
        }
        
        return usuario; 
    }
    
    //RegistrarUsuario
    public boolean registrarUsuario(String username, String password, String rol){
        
        return false; 
    }
}
