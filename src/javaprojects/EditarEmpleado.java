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

public class EditarEmpleado implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Edición Empleado");
	Label lblId = new Label("\tID:\t\t\t\t");
	Label lblDni = new Label("\t\sDNI:\t\t\t\s\s");
	Label lblNombre = new Label("Nombre:\t");
	Label lblApellidos = new Label("Apellidos:\t");
	Label lblDomicilio = new Label("Domicilio:\t");
	Label lblFechaIngreso = new Label("Fecha de Ingreso:");
	TextField txtId = new TextField(20);
	TextField txtDni = new TextField(20);
	TextField txtNombre = new TextField(20);
	TextField txtApellidos = new TextField(20);
	TextField txtDomicilio = new TextField(20);
	TextField txtFechaIngreso = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");
	
	Dialog dlgEditarEmpleado = new Dialog(ventana, "Editar", true);
	Label lblMensajeEditarEmpleado = new Label("Modificación correcta");
	
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	BaseDatos bd = new BaseDatos();
	
	public EditarEmpleado(String elementoElegido)
	{
		String[] elemento = elementoElegido.split("~");
		ventana.setLayout(new FlowLayout());
		ventana.add(lblId);
		txtId.setEditable(false);
		txtId.setText(elemento[0]);
		ventana.add(txtId);
		ventana.add(lblDni);
		txtDni.setText(elemento[1]);
		ventana.add(txtDni);
		ventana.add(lblNombre);
		txtNombre.setText(elemento[2]);
		ventana.add(txtNombre);
		ventana.add(lblApellidos);
		txtApellidos.setText(elemento[3]);
		ventana.add(txtApellidos);
		ventana.add(lblDomicilio);
		txtDomicilio.setText(elemento[4]);
		ventana.add(txtDomicilio);
		ventana.add(lblFechaIngreso);
		txtFechaIngreso.setText(elemento[5]);
		ventana.add(txtFechaIngreso);
		
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventana.add(btnAceptar);
		ventana.add(btnCancelar);
		
		ventana.setSize(320,250);
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
				if(((txtDni.getText().length())!=0)
						&&((txtNombre.getText().length())!=0) 
						&&((txtApellidos.getText().length())!=0) 
						&& ((txtDomicilio.getText().length())!=0) 
						&& ((txtFechaIngreso.getText().length())!=0))
				{
					sentencia = "UPDATE empleados SET dniEmpleado='"
							+ txtDni.getText()
							+ "', nombreEmpleado='" +txtNombre.getText()
							+ "', apellidoEmpleado='" +txtApellidos.getText()
							+ "', domicilioEmpleado='" +txtDomicilio.getText()
							+ "', fechaIngresoEmpleado='" +txtFechaIngreso.getText()
							+ "' WHERE idEmpleado = "+txtId.getText();
					statement.executeUpdate(sentencia);
					lblMensajeEditarEmpleado.setText("Modificación del Empleado Correcta");
				}
				else
				{
					lblMensajeEditarEmpleado.setText("Por favor, revisa que no hayan campos vacíos.");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeEditarEmpleado.setText("Error en la Modificación");
			}
			finally
			{
				dlgEditarEmpleado.setLayout(new FlowLayout());
				dlgEditarEmpleado.addWindowListener(this);
				dlgEditarEmpleado.setSize(270,100);
				dlgEditarEmpleado.setResizable(false);
				dlgEditarEmpleado.setLocationRelativeTo(null);
				dlgEditarEmpleado.add(lblMensajeEditarEmpleado);
				dlgEditarEmpleado.setVisible(true);
				bd.desconectar(connection);
			}
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
