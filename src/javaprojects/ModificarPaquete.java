package javaprojects;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ModificarPaquete implements WindowListener, ActionListener
{
	// Ventana Modificación Cliente
	Frame ventanaModificar = new Frame("Editar Paquete");
	Label lblEditarPaquete = new Label("Seleccionar el paquete:");
	Choice choPaquetesEditar = new Choice();
	Button btnEditarPaquete = new Button("Editar");
	
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatos bd = new BaseDatos();
	
	public ModificarPaquete() 
	{
		ventanaModificar.setLayout(new FlowLayout());
		ventanaModificar.add(lblEditarPaquete);
		// Rellenar el Choice
		// Conectar
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM paquetesviaje";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choPaquetesEditar.removeAll();
			while(rs.next())
			{
				choPaquetesEditar.add(rs.getInt("idPaquete")
						+"-"+rs.getString("nombrePaquete")
						+"-"+rs.getInt("cantidadDestinosPaquete")
						+"-"+rs.getInt("plazasPaquete")
						+"-"+rs.getDouble("precioPaquete"));
			}
		}
		catch (SQLException sqle)
		{
			
		}
		ventanaModificar.add(choPaquetesEditar);
		btnEditarPaquete.addActionListener(this);
		ventanaModificar.add(btnEditarPaquete);

		ventanaModificar.setSize(270,140);
		ventanaModificar.setResizable(false);
		ventanaModificar.setLocationRelativeTo(null);
		ventanaModificar.addWindowListener(this);
		ventanaModificar.setVisible(true);
		bd.desconectar(connection);
	}

	//ACTION LISTENER METHOD
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnEditarPaquete))
		{
			new EditarPaquete(choPaquetesEditar.getSelectedItem());
		}
	}
	
	//WINDOW LISTENER METHOD
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){
		if(ventanaModificar.isActive())
		{
			ventanaModificar.setVisible(false);
		}
		else
		{
			System.exit(0);
		}
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}

}
