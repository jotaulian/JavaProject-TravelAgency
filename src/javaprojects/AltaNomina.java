package javaprojects;

import java.awt.Button;
import java.awt.Choice;
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

public class AltaNomina implements WindowListener, ActionListener
{
	// Atributos o Componentes
		Frame ventanaAltaNomina = new Frame("Alta de Nómina");
		Label lblFechaInicio = new Label("Fecha Inicio:");
		Label lblFechaFin = new Label("Fecha Fin:");
		Label lblImporte = new Label("Importe:");
		Label lblEmpleado = new Label("Empleado:");
		TextField txtFechaInicio = new TextField(20);
		TextField txtFechaFin = new TextField(20);
		TextField txtImporte = new TextField(20);
		Choice choEmpleados = new Choice();
		Button btnAceptar = new Button("Aceptar");
		Button btnCancelar = new Button("Cancelar");

		Dialog dlgMensajeAltaNomina = new Dialog(ventanaAltaNomina, 
				"Confirmación", true);
		Label lblMensaje = new Label("Alta de Nómina Correcta");

		BaseDatos bd;
		String sentencia = "";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
	//Constructor
	public AltaNomina() {
		ventanaAltaNomina.setLayout(new FlowLayout());
		ventanaAltaNomina.add(lblFechaInicio);
		ventanaAltaNomina.add(txtFechaInicio);
		ventanaAltaNomina.add(lblFechaFin);
		ventanaAltaNomina.add(txtFechaFin);
		ventanaAltaNomina.add(lblImporte);
		ventanaAltaNomina.add(txtImporte);
		ventanaAltaNomina.add(lblEmpleado);
		// Rellenar el Choice
		// Conectar
		bd = new BaseDatos();
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
			choEmpleados.add("Seleccionar un empleado");
			while(rs.next())
			{
				choEmpleados.add(rs.getInt("idEmpleado")
						+"-"+rs.getString("dniEmpleado")
						+"-"+rs.getString("nombreEmpleado")
						+"-"+rs.getString("apellidoEmpleado"));
			}
		}
		catch (SQLException sqle)
		{}
		ventanaAltaNomina.add(choEmpleados);
		btnAceptar.addActionListener(this);
		ventanaAltaNomina.add(btnAceptar);
		btnCancelar.addActionListener(this);
		ventanaAltaNomina.add(btnCancelar);
		ventanaAltaNomina.setSize(300,180);
		ventanaAltaNomina.setResizable(false);
		ventanaAltaNomina.setLocationRelativeTo(null);
		ventanaAltaNomina.addWindowListener(this);
		ventanaAltaNomina.setVisible(true);
	}
	
	
	//ACTION LISTENER METHOD
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnCancelar))
		{
			ventanaAltaNomina.setVisible(false);
		}
		else
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			try
			{
				sentencia = "INSERT INTO nominas VALUES(null, '"
						+txtFechaInicio.getText()+"','"
						+txtFechaFin.getText()+"','"
						+txtImporte.getText()+"',"
						+choEmpleados.getSelectedItem().split("-")[0]
						+")";
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				statement.executeUpdate(sentencia);
			}
			catch (SQLException sqle)
			{
				lblMensaje.setText("Error en Alta");
			}
			finally
			{
				dlgMensajeAltaNomina.setLayout(new FlowLayout());
				dlgMensajeAltaNomina.addWindowListener(this);
				dlgMensajeAltaNomina.setSize(150,100);
				dlgMensajeAltaNomina.setResizable(false);
				dlgMensajeAltaNomina.setLocationRelativeTo(null);
				dlgMensajeAltaNomina.add(lblMensaje);
				dlgMensajeAltaNomina.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}

	//WINDOW LISTENER METHODS
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){
		if(dlgMensajeAltaNomina.isActive()) {
			dlgMensajeAltaNomina.setVisible(false);
		}
		else if(ventanaAltaNomina.isActive()) {
			ventanaAltaNomina.setVisible(false);
		}else {
			System.exit(0);			
		}
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}

}
