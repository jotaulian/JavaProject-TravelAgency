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

public class AltaPaquete implements WindowListener, ActionListener
{
	Frame frmAltaPaquete = new Frame("Alta de Paquete");
	Label lblNombrePaquete = new Label("Nombre");
	TextField txtNombrePaquete = new TextField(20);
	Label lblCantidadDestinos = new Label("Nº Destinos:");
	TextField txtCantidadDestinos = new TextField(20);
	Label lblPrecioPaquete = new Label("Precio:");
	TextField txtPrecioPaquete = new TextField(20);
	Label lblPlazasPaquete = new Label("Plazas:");
	TextField txtPlazasPaquete = new TextField(20);
	Button btnAceptarAltaPaquete = new Button("Aceptar");
	Button btnCancelarAltaPaquete = new Button("Cancelar");

	Dialog dlgConfirmarAltaPaquete = new Dialog(frmAltaPaquete, "Alta Paquete", true);
	Label lblMensajeAltaPaquete = new Label("Alta de Paquete Correcta");
	
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatos bd = new BaseDatos();
	
	public AltaPaquete()
	{
		frmAltaPaquete.setLayout(new FlowLayout());
		frmAltaPaquete.add(lblNombrePaquete);
		txtNombrePaquete.setText("");
		frmAltaPaquete.add(txtNombrePaquete);
		frmAltaPaquete.add(lblCantidadDestinos);
		txtCantidadDestinos.setText("");
		frmAltaPaquete.add(txtCantidadDestinos);
		frmAltaPaquete.add(lblPlazasPaquete);
		txtPlazasPaquete.setText("");
		frmAltaPaquete.add(txtPlazasPaquete);
		frmAltaPaquete.add(lblPrecioPaquete);
		txtPrecioPaquete.setText("");
		frmAltaPaquete.add(txtPrecioPaquete);
		btnAceptarAltaPaquete.addActionListener(this);
		frmAltaPaquete.add(btnAceptarAltaPaquete);
		btnCancelarAltaPaquete.addActionListener(this);
		frmAltaPaquete.add(btnCancelarAltaPaquete);

		frmAltaPaquete.setSize(300,180);
		frmAltaPaquete.setResizable(false);
		frmAltaPaquete.setLocationRelativeTo(null);
		frmAltaPaquete.addWindowListener(this);
		txtPrecioPaquete.requestFocus();
		frmAltaPaquete.setVisible(true);
	}

	//ACTION LISTENER METHOD
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAceptarAltaPaquete))
		{
			connection = bd.conectar();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Ejecutar la sentencia SQL
				sentencia = "INSERT INTO paquetesviaje VALUES (null, '"
						+ txtNombrePaquete.getText()
						+ "', '"+ txtCantidadDestinos.getText()
						+ "', '" + txtPlazasPaquete.getText()
							+ "', '" +txtPrecioPaquete.getText() + "')";
				statement.executeUpdate(sentencia);
				lblMensajeAltaPaquete.setText("Alta de Paquete Correcta");
				
			}
			catch (SQLException sqle)
			{
				lblMensajeAltaPaquete.setText("Error en ALTA");
				System.out.println(sqle.getMessage());
			}
			finally
			{
				dlgConfirmarAltaPaquete.setLayout(new FlowLayout());
				dlgConfirmarAltaPaquete.addWindowListener(this);
				dlgConfirmarAltaPaquete.setSize(150,100);
				dlgConfirmarAltaPaquete.setResizable(false);
				dlgConfirmarAltaPaquete.setLocationRelativeTo(null);
				dlgConfirmarAltaPaquete.add(lblMensajeAltaPaquete);
				dlgConfirmarAltaPaquete.setVisible(true);
				bd.desconectar(connection);
			}
		}else {
			frmAltaPaquete.setVisible(false);
		}
		
	}

	//WINDOW LISTENER METHODS
	@Override
	public void windowActivated(WindowEvent arg0){}
	@Override
	public void windowClosed(WindowEvent arg0){}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		if(frmAltaPaquete.isActive())
		{
			frmAltaPaquete.setVisible(false);
		}
		else if(dlgConfirmarAltaPaquete.isActive())
		{
			txtNombrePaquete.setText("");
			txtCantidadDestinos.setText("");
			txtPrecioPaquete.setText("");
			txtPlazasPaquete.setText("");
			txtPrecioPaquete.requestFocus();
			dlgConfirmarAltaPaquete.setVisible(false);
		}
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0){}
	@Override
	public void windowDeiconified(WindowEvent arg0){}
	@Override
	public void windowIconified(WindowEvent arg0){}
	@Override
	public void windowOpened(WindowEvent arg0){}
}