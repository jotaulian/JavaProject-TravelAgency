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

public class AltaEmpleado implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Alta Empleado");
	Label lblDniEmpleado = new Label("Dni:\s\s\s\t");
	Label lblNombreEmpleado = new Label("Nombre:");
	Label lblApellidosEmpleado = new Label("Apellidos:");
	Label lblDomicilioEmpleado = new Label("Domicilio:");
	Label lblFechaIngresoEmpleado = new Label("Fecha Ingreso:");
	TextField txtDni = new TextField(20);
	TextField txtNombre = new TextField(20);
	TextField txtApellidos = new TextField(20);
	TextField txtDomicilio = new TextField(20);
	TextField txtFechaIngreso = new TextField(20);
	Button btnAceptarAltaEmpleado = new Button("Aceptar");
	Button btnCancelarAltaEmpleado = new Button("Cancelar");

	Dialog dlgConfirmarAltaEmpleado = new Dialog(ventana, "Alta Empleado", true);
	Label lblMensajeAltaEmpleado = new Label("Alta de Empleado Correcta");

	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatos bd = new BaseDatos();
	
	
	public AltaEmpleado() {
		ventana.setLayout(new FlowLayout());
		ventana.add(lblDniEmpleado);
		ventana.add(txtDni);
		ventana.add(lblNombreEmpleado);
		ventana.add(txtNombre);
		ventana.add(lblApellidosEmpleado);
		ventana.add(txtApellidos);
		ventana.add(lblDomicilioEmpleado);
		ventana.add(txtDomicilio);
		ventana.add(lblFechaIngresoEmpleado);
		ventana.add(txtFechaIngreso);
		btnAceptarAltaEmpleado.addActionListener(this);
		ventana.add(btnAceptarAltaEmpleado);
		btnCancelarAltaEmpleado.addActionListener(this);
		ventana.add(btnCancelarAltaEmpleado);

		ventana.setSize(310,230);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}
	
	//ACTION LISTENER METHOD
		@Override
		public void actionPerformed(ActionEvent evento)
		{
			if(evento.getSource().equals(btnAceptarAltaEmpleado))
			{	
				//Conectar con la base de datos
				connection = bd.conectar();
				try
				{
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					if(((txtNombre.getText().length())!=0)
							&&((txtDni.getText().length())!=0)
							&&((txtApellidos.getText().length())!=0)
							&&((txtDomicilio.getText().length())!=0)
							&&((txtFechaIngreso.getText().length())!=0))
					{
						sentencia = "INSERT INTO empleados VALUES(null, '"
								+txtDni.getText()
								+"', '"+txtNombre.getText()
								+"', '"+txtApellidos.getText()
								+"', '"+txtDomicilio.getText()
								+"', '"+txtFechaIngreso.getText()
								+"')";
						statement.executeUpdate(sentencia);
						lblMensajeAltaEmpleado.setText("Alta de Empleado Correcta");
					}
					else
					{
						lblMensajeAltaEmpleado.setText("Faltan datos");
					}
				}
				catch (SQLException sqle)
				{
					lblMensajeAltaEmpleado.setText("Error en ALTA");
				}
				finally
				{
					dlgConfirmarAltaEmpleado.setLayout(new FlowLayout());
					dlgConfirmarAltaEmpleado.addWindowListener(this);
					dlgConfirmarAltaEmpleado.setSize(150,100);
					dlgConfirmarAltaEmpleado.setResizable(false);
					dlgConfirmarAltaEmpleado.setLocationRelativeTo(null);
					dlgConfirmarAltaEmpleado.add(lblMensajeAltaEmpleado);
					dlgConfirmarAltaEmpleado.setVisible(true);
					bd.desconectar(connection);
				}
			}else {
				ventana.setVisible(false);
			}
		}
	
	//WINDOW LISTENER METHODS
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){
		ventana.setVisible(false);
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
	
}
