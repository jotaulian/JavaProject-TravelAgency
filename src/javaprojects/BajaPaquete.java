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

public class BajaPaquete implements WindowListener, ActionListener
{
	// Ventana de Borrado de Cliente
		Frame frmBajaPaquete = new Frame("Baja de Paquete");
		Label lblMensajeBajaPaquete = new Label("Seleccionar el paquete:");
		Choice choPaquetes = new Choice();
		Button btnBorrarPaquete = new Button("Borrar");
		Dialog dlgSeguroPaquete = new Dialog(frmBajaPaquete, "¿Seguro?", true);
		Label lblSeguroPaquete = new Label("¿Está seguro de borrar?");
		Button btnSiSeguroPaquete = new Button("Sí");
		Button btnNoSeguroPaquete = new Button("No");
		
		Dialog dlgConfirmacionBajaPaquete = new Dialog(frmBajaPaquete, "Baja Paquete", true);
		Label lblConfirmacionBajaPaquete = new Label("Baja de paquete correcta");
		
		String sentencia = "";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		BaseDatos bd = new BaseDatos();
	
	public BajaPaquete() {
		frmBajaPaquete.setLayout(new FlowLayout());
		frmBajaPaquete.add(lblMensajeBajaPaquete);
		// Rellenar el Choice
		// Conectar
		connection = bd.conectar();
		// Hacer un SELECT * FROM paquetesviaje
		sentencia = "SELECT * FROM paquetesviaje";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choPaquetes.removeAll();
			while(rs.next())
			{
				choPaquetes.add(rs.getInt("idPaquete")
						+"-"+rs.getString("nombrePaquete")
						+"-"+rs.getInt("cantidadDestinosPaquete")
						+"-"+rs.getInt("plazasPaquete")
						+"-"+rs.getDouble("precioPaquete"));
			}
		}
		catch (SQLException sqle){}
		
		frmBajaPaquete.add(choPaquetes);
		btnBorrarPaquete.addActionListener(this);
		frmBajaPaquete.add(btnBorrarPaquete);

		frmBajaPaquete.setSize(270,140);
		frmBajaPaquete.setResizable(false);
		frmBajaPaquete.setLocationRelativeTo(null);
		frmBajaPaquete.addWindowListener(this);
		frmBajaPaquete.setVisible(true);
	}

	//ACTION LISTENER METHOD
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnBorrarPaquete))
		{
			dlgSeguroPaquete.setLayout(new FlowLayout());
			dlgSeguroPaquete.addWindowListener(this);
			dlgSeguroPaquete.setSize(150,100);
			dlgSeguroPaquete.setResizable(false);
			dlgSeguroPaquete.setLocationRelativeTo(null);
			dlgSeguroPaquete.add(lblSeguroPaquete);
			btnSiSeguroPaquete.addActionListener(this);
			dlgSeguroPaquete.add(btnSiSeguroPaquete);
			btnNoSeguroPaquete.addActionListener(this);
			dlgSeguroPaquete.add(btnNoSeguroPaquete);
			dlgSeguroPaquete.setVisible(true);
		}
		else if(evento.getSource().equals(btnNoSeguroPaquete))
		{
			dlgSeguroPaquete.setVisible(false);
		}
		else if(evento.getSource().equals(btnSiSeguroPaquete))
		{
			// Conectar
			connection = bd.conectar();
			// Hacer un DELETE FROM paquetesviaje WHERE idPaquete = X
			String[] elegido = choPaquetes.getSelectedItem().split("-");
			sentencia = "DELETE FROM paquetesviaje WHERE idPaquete = "+elegido[0];
			
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(sentencia);
				lblConfirmacionBajaPaquete.setText("Baja de Paquete Correcta");
			}
			catch (SQLException sqle)
			{
				lblConfirmacionBajaPaquete.setText("Error en Baja");
			}
			finally
			{
				dlgConfirmacionBajaPaquete.setLayout(new FlowLayout());
				dlgConfirmacionBajaPaquete.addWindowListener(this);
				dlgConfirmacionBajaPaquete.setSize(150,100);
				dlgConfirmacionBajaPaquete.setResizable(false);
				dlgConfirmacionBajaPaquete.setLocationRelativeTo(null);
				dlgConfirmacionBajaPaquete.add(lblConfirmacionBajaPaquete);
				dlgConfirmacionBajaPaquete.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}
	
	//WINDOW LISTENER METHOD
	public void windowActivated(WindowEvent arg0){}
		public void windowClosed(WindowEvent arg0){}
		public void windowClosing(WindowEvent arg0){
			if(frmBajaPaquete.isActive())
			{
				frmBajaPaquete.setVisible(false);
			}
			else if(dlgSeguroPaquete.isActive())
			{
				dlgSeguroPaquete.setVisible(false);
			}
			else if(dlgConfirmacionBajaPaquete.isActive())
			{
				dlgConfirmacionBajaPaquete.setVisible(false);
				dlgSeguroPaquete.setVisible(false);
				frmBajaPaquete.setVisible(false);
			}
		}
		public void windowDeactivated(WindowEvent arg0){}
		public void windowDeiconified(WindowEvent arg0){}
		public void windowIconified(WindowEvent arg0){}
		public void windowOpened(WindowEvent arg0){}
}
