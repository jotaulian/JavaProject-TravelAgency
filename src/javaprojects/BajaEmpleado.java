package javaprojects;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
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

public class BajaEmpleado implements WindowListener, ActionListener
{
	// Ventana de Borrado de Cliente
		Frame frmBajaEmpleado = new Frame("Baja de Empleado");
		Label lblMensajeBajaEmpleado = new Label("Seleccionar el empleado:");
		Choice choEmpleados = new Choice();
		Button btnBorrarEmpleado = new Button("Borrar");
		Dialog dlgSeguroEmpleado = new Dialog(frmBajaEmpleado, "¿Seguro?", true);
		Label lblSeguroEmpleado = new Label("¿Está seguro de borrar?");
		Button btnSiSeguroEmpleado = new Button("Sí");
		Button btnNoSeguroEmpleado = new Button("No");
		
		Dialog dlgConfirmacionBajaEmpleado = new Dialog(frmBajaEmpleado, "Baja Empleado", true);
		Label lblConfirmacionBajaEmpleado = new Label("Baja de empleado correcta");
		
		String sentencia = "";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		BaseDatos bd = new BaseDatos();
	
	public BajaEmpleado() {
		frmBajaEmpleado.setLayout(new FlowLayout());
		frmBajaEmpleado.add(lblMensajeBajaEmpleado);
		// Rellenar el Choice
		// Conectar
		connection = bd.conectar();
		// Hacer un SELECT * FROM empleados
		sentencia = "SELECT * FROM empleados";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choEmpleados.removeAll();
			while(rs.next())
			{
				choEmpleados.add(rs.getInt("idEmpleado")
						+"~"+rs.getString("dniEmpleado")
						+"~"+rs.getString("nombreEmpleado")
						+"~"+rs.getString("apellidoEmpleado")
						+"~"+rs.getString("domicilioEmpleado")
						+"~"+rs.getString("fechaIngresoEmpleado"));
			}
		}
		catch (SQLException sqle){}
		
		frmBajaEmpleado.add(choEmpleados);
		btnBorrarEmpleado.addActionListener(this);
		frmBajaEmpleado.add(btnBorrarEmpleado);

		frmBajaEmpleado.setSize(470,140);
		frmBajaEmpleado.setResizable(false);
		frmBajaEmpleado.setLocationRelativeTo(null);
		frmBajaEmpleado.addWindowListener(this);
		frmBajaEmpleado.setVisible(true);
	}

	//ACTION LISTENER METHOD
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnBorrarEmpleado))
		{
			dlgSeguroEmpleado.setLayout(new FlowLayout());
			dlgSeguroEmpleado.addWindowListener(this);
			dlgSeguroEmpleado.setSize(150,100);
			dlgSeguroEmpleado.setResizable(false);
			dlgSeguroEmpleado.setLocationRelativeTo(null);
			dlgSeguroEmpleado.add(lblSeguroEmpleado);
			btnSiSeguroEmpleado.addActionListener(this);
			dlgSeguroEmpleado.add(btnSiSeguroEmpleado);
			btnNoSeguroEmpleado.addActionListener(this);
			dlgSeguroEmpleado.add(btnNoSeguroEmpleado);
			dlgSeguroEmpleado.setVisible(true);
		}
		else if(evento.getSource().equals(btnNoSeguroEmpleado))
		{
			dlgSeguroEmpleado.setVisible(false);
		}
		else if(evento.getSource().equals(btnSiSeguroEmpleado))
		{
			// Conectar
			connection = bd.conectar();
			// Hacer un DELETE FROM empleados WHERE idEmpleado = X
			String[] elegido = choEmpleados.getSelectedItem().split("~");
			sentencia = "DELETE FROM empleados WHERE idEmpleado = "+elegido[0];
			
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(sentencia);
				lblConfirmacionBajaEmpleado.setText("Baja de Empleado Correcta");
			}
			catch (SQLException sqle)
			{
				lblConfirmacionBajaEmpleado.setText("Error en Baja");
			}
			finally
			{
				dlgConfirmacionBajaEmpleado.setLayout(new FlowLayout());
				dlgConfirmacionBajaEmpleado.addWindowListener(this);
				dlgConfirmacionBajaEmpleado.setSize(160,100);
				dlgConfirmacionBajaEmpleado.setResizable(false);
				dlgConfirmacionBajaEmpleado.setLocationRelativeTo(null);
				dlgConfirmacionBajaEmpleado.add(lblConfirmacionBajaEmpleado);
				dlgConfirmacionBajaEmpleado.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}
	
	//WINDOW LISTENER METHOD
	public void windowActivated(WindowEvent arg0){}
		public void windowClosed(WindowEvent arg0){}
		public void windowClosing(WindowEvent arg0){
			if(frmBajaEmpleado.isActive())
			{
				frmBajaEmpleado.setVisible(false);
			}
			else if(dlgSeguroEmpleado.isActive())
			{
				dlgSeguroEmpleado.setVisible(false);
			}
			else if(dlgConfirmacionBajaEmpleado.isActive())
			{
				dlgConfirmacionBajaEmpleado.setVisible(false);
				dlgSeguroEmpleado.setVisible(false);
				frmBajaEmpleado.setVisible(false);
			}
		}
		public void windowDeactivated(WindowEvent arg0){}
		public void windowDeiconified(WindowEvent arg0){}
		public void windowIconified(WindowEvent arg0){}
		public void windowOpened(WindowEvent arg0){}
}
