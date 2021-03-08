package javaprojects;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login implements WindowListener, ActionListener
{
	Frame ventanaLogin = new Frame("Login");
	Dialog dialogoLogin = new Dialog(ventanaLogin,"Error", true);
	Label lblUsuario = new Label("Usuario: ");
	Label lblClave = new Label("Clave: ");
	Label lblError = new Label("Credenciales incorrectas!");
	TextField txtUsuario = new TextField(20);
	TextField txtClave = new TextField(20);
	Button btnAcceder = new Button("Acceder");
	Button btnLimpiar = new Button("Limpiar");
	
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatos bd = new BaseDatos();
	
	public Login() {
		ventanaLogin.setLayout(new FlowLayout());
		ventanaLogin.add(lblUsuario);
		ventanaLogin.add(txtUsuario);
		ventanaLogin.add(lblClave);
		txtClave.setEchoChar('*');
		ventanaLogin.add(txtClave);
		btnAcceder.addActionListener(this);
		ventanaLogin.add(btnAcceder);
		btnLimpiar.addActionListener(this);
		ventanaLogin.add(btnLimpiar);
		
		ventanaLogin.addWindowListener(this);
		ventanaLogin.setSize(280,150);
		ventanaLogin.setResizable(false);
		ventanaLogin.setLocationRelativeTo(null);
		ventanaLogin.setVisible(true);
		
	}
	public static void main(String[] args)
	{
		new Login();
	}
	
	// ACTION LISTENER METHOD
	public void actionPerformed(ActionEvent evento)
	{
		if (evento.getSource().equals(btnLimpiar))
		{
			txtUsuario.setText("");
			txtClave.setText("");
			txtUsuario.requestFocus();
		}else if(evento.getSource().equals(btnAcceder)){
			//Conectar con la base de datos.
			connection = bd.conectar();
			sentencia = "SELECT * FROM usuarios WHERE nombreUsuario = '" + txtUsuario.getText()+"' AND claveUsuario = SHA2('" + txtClave.getText() + "',256)";
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				rs = statement.executeQuery(sentencia);
				if(rs.next()) {
					int tipo = rs.getInt("tipoUsuario");
					new MiAgencia(tipo);
				}else {
					dialogoLogin.setLayout(new FlowLayout());
					dialogoLogin.add(lblError);
					dialogoLogin.addWindowListener(this);
					dialogoLogin.setSize(150,150);
					dialogoLogin.setLocationRelativeTo(null);
					dialogoLogin.setResizable(false);;
					dialogoLogin.setVisible(true);
					
				}
			} catch (SQLException sqle)
			{
				
			}
		}
	}
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){
		if (dialogoLogin.isActive()) {
			dialogoLogin.setVisible(false);
		}else {
			System.exit(0);			
		}
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
	
}
