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

public class ModificarEmpleado implements WindowListener, ActionListener
{
	// Ventana Modificación Cliente
		Frame ventanaModificar = new Frame("Modificar Empleado");
		Label lblEditarEmpleado = new Label("Seleccionar el empleado:");
		Choice choEmpleadosEditar = new Choice();
		Button btnEditarEmpleado = new Button("Editar");
		
		String sentencia = "";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		BaseDatos bd = new BaseDatos();
	
		public ModificarEmpleado() 
		{
			ventanaModificar.setLayout(new FlowLayout());
			ventanaModificar.add(lblEditarEmpleado);
			// Rellenar el Choice
			// Conectar
			connection = bd.conectar();
			// Hacer un SELECT * FROM clientes
			sentencia = "SELECT * FROM empleados";
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				rs = statement.executeQuery(sentencia);
				choEmpleadosEditar.removeAll();
				while(rs.next())
				{
					choEmpleadosEditar.add(rs.getInt("idEmpleado")
							+"~"+rs.getString("dniEmpleado")
							+"~"+rs.getString("nombreEmpleado")
							+"~"+rs.getString("apellidoEmpleado")
							+"~"+rs.getString("domicilioEmpleado")
							+"~"+rs.getString("fechaIngresoEmpleado"));
				}
			}
			catch (SQLException sqle)
			{
				
			}
			ventanaModificar.add(choEmpleadosEditar);
			btnEditarEmpleado.addActionListener(this);
			ventanaModificar.add(btnEditarEmpleado);

			ventanaModificar.setSize(470,140);
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
		if(evento.getSource().equals(btnEditarEmpleado))
		{
			new EditarEmpleado(choEmpleadosEditar.getSelectedItem());
		}
	}

	//WINDOW LISTENER METHODS
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
