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

public class EditarPaquete implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Edición Paquete");
	Label lblId = new Label("ID:");
	Label lblNombre = new Label("Nombre:");
	Label lblDestinos = new Label("Nº Destinos:");
	Label lblPlazas = new Label("Plazas:");
	Label lblPrecio = new Label("Precio:");
	TextField txtId = new TextField(20);
	TextField txtNombre = new TextField(20);
	TextField txtDestinos = new TextField(20);
	TextField txtPlazas = new TextField(20);
	TextField txtPrecio = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");
	
	Dialog dlgEditarPaquete = new Dialog(ventana, "Editar", true);
	Label lblMensajeEditarPaquete = new Label("Modificación correcta");
	
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	BaseDatos bd = new BaseDatos();
	
	public EditarPaquete(String elementoElegido)
	{
		String[] elemento = elementoElegido.split("-");
		ventana.setLayout(new FlowLayout());
		ventana.add(lblId);
		txtId.setEditable(false);
		txtId.setText(elemento[0]);
		ventana.add(txtId);
		ventana.add(lblNombre);
		txtNombre.setText(elemento[1]);
		ventana.add(txtNombre);
		ventana.add(lblDestinos);
		txtDestinos.setText(elemento[2]);
		ventana.add(txtDestinos);
		ventana.add(lblPlazas);
		txtPlazas.setText(elemento[3]);
		ventana.add(txtPlazas);
		ventana.add(lblPrecio);
		txtPrecio.setText(elemento[4]);
		ventana.add(txtPrecio);
		
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventana.add(btnAceptar);
		ventana.add(btnCancelar);
		
		ventana.setSize(290,210);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}

	//ACTION LISTENER METHOD
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAceptar))
		{
			connection = bd.conectar();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				if(((txtNombre.getText().length())!=0)
						&&((txtDestinos.getText().length())!=0) 
						&& ((txtPlazas.getText().length())!=0) 
						&& ((txtPrecio.getText().length())!=0))
				{
					sentencia = "UPDATE paquetesviaje SET nombrePaquete='"
							+ txtNombre.getText()
							+ "', cantidadDestinosPaquete='" +txtDestinos.getText()
							+ "', plazasPaquete='" +txtPlazas.getText()
							+ "', precioPaquete='" +txtPrecio.getText()
							+ "' WHERE idPaquete = "+txtId.getText();
					statement.executeUpdate(sentencia);
					lblMensajeEditarPaquete.setText("Modificación de Paquete Correcta");
				}
				else
				{
					lblMensajeEditarPaquete.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeEditarPaquete.setText("Error en Modificación");
			}
			finally
			{
				dlgEditarPaquete.setLayout(new FlowLayout());
				dlgEditarPaquete.addWindowListener(this);
				dlgEditarPaquete.setSize(200,100);
				dlgEditarPaquete.setResizable(false);
				dlgEditarPaquete.setLocationRelativeTo(null);
				dlgEditarPaquete.add(lblMensajeEditarPaquete);
				dlgEditarPaquete.setVisible(true);
				bd.desconectar(connection);
			}
		}
		else
		{
			ventana.setVisible(false);
		}
	}

	//WINDOW LISTENER METHODS
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0)
		{
			ventana.setVisible(false);
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
}
