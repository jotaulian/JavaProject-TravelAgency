	package javaprojects;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaVenta implements WindowListener, ActionListener
{
	/* Componentes */
	Frame frmAltaVentaPaso1 = new Frame("Alta Venta (1 de 2)");
	Frame frmAltaVentaPaso2 = new Frame("Alta Venta (2 de 2)");
	Label lblElegirPaquete = new Label("Elegir Paquete:");
	Label lblElegirEmpleado = new Label("Vendedor:");
	Label lblCantidad = new Label("Cantidad:");
	Label lblNombrePaquete = new Label("XXXXXXXXXXXXXXXX");
	Font font1 = new Font("SansSerif", Font.BOLD, 15);
	Choice choPaquetes = new Choice();
	Choice choEmpleados = new Choice();
	TextField txtCantidad = new TextField(3);
	Button btnSiguiente = new Button("Siguiente");
	Button btnAnadir = new Button("Añadir");
	TextArea txaListado = new TextArea(4,20);
	
	Dialog dlgMensaje1 = new Dialog(frmAltaVentaPaso1, "Error", true);
	Label lblMensaje1 = new Label("Debes seleccionar un paquete");

	Dialog dlgMensaje = new Dialog(frmAltaVentaPaso2, "Estado de la venta", true);
	Label lblMensaje = new Label("Venta Añadida");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	int idPaquete;
	int plazasPaquete;

	public AltaVenta() {
		frmAltaVentaPaso1.setLayout(new FlowLayout());
		frmAltaVentaPaso1.add(lblElegirPaquete);

		// Rellenar el Choice
		// Conectar
		bd = new BaseDatos();
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
			choPaquetes.add("Seleccionar un paquete");
			while(rs.next())
			{
				choPaquetes.add(rs.getInt("idPaquete")
						+"-"+rs.getString("nombrePaquete")
						+"-"+rs.getInt("cantidadDestinosPaquete")
						+"-"+rs.getInt("plazasPaquete")
						+"-"+rs.getDouble("precioPaquete"));
			}
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
		
		frmAltaVentaPaso1.add(choPaquetes);
		btnAnadir.addActionListener(this);
		btnSiguiente.addActionListener(this);
		frmAltaVentaPaso1.add(btnSiguiente);
		frmAltaVentaPaso1.setSize(300,140);
		frmAltaVentaPaso1.setResizable(false);
		frmAltaVentaPaso1.setLocationRelativeTo(null);
		frmAltaVentaPaso1.addWindowListener(this);
		frmAltaVentaPaso1.setVisible(true);
	}
	
	//ACTION LISTENER METHOD
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnSiguiente))
		{
			if(choPaquetes.getSelectedItem().equals("Seleccionar un paquete")) {
				dlgMensaje1.setLayout(new FlowLayout());
				dlgMensaje1.addWindowListener(this);
				dlgMensaje1.setSize(250,100);
				dlgMensaje1.setResizable(false);
				dlgMensaje1.setLocationRelativeTo(null);
				dlgMensaje1.add(lblMensaje1);
				dlgMensaje1.setVisible(true);
			}else {
				
				lblNombrePaquete.setText("Paquete: "+choPaquetes.getSelectedItem().split("-")[1]+"\t\t");
				idPaquete = Integer.parseInt(choPaquetes.getSelectedItem().split("-")[0]);
				plazasPaquete = Integer.parseInt(choPaquetes.getSelectedItem().split("-")[3]);
				frmAltaVentaPaso2.setLayout(new FlowLayout());
				lblNombrePaquete.setFont(font1);
				frmAltaVentaPaso2.add(lblNombrePaquete);
				frmAltaVentaPaso2.add(lblElegirEmpleado);
				
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
				{
					System.out.println(sqle.getMessage());
				}
				frmAltaVentaPaso2.add(choEmpleados);
				frmAltaVentaPaso2.add(lblCantidad);
				txtCantidad.setText("0");
				frmAltaVentaPaso2.add(txtCantidad);
				
				// Hacer un SELECT * FROM paquetesviajes para mostrar información del paquete seleccionado
				sentencia = "SELECT * FROM paquetesviaje WHERE idPaquete = "+idPaquete;
				try
				{
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					//Crear un objeto ResultSet para guardar lo obtenido
					//y ejecutar la sentencia SQL
					rs = statement.executeQuery(sentencia);
					txaListado.selectAll();
					txaListado.setText("");
					while(rs.next())
					{
						txaListado.append("PLAZAS DISPONIBLES: "+rs.getInt("plazasPaquete"));
					}
				}
				catch (SQLException sqle)
				{
					System.out.println(sqle.getMessage());
				}
				txaListado.setEditable(false);
				frmAltaVentaPaso2.add(txaListado);
				frmAltaVentaPaso2.add(btnAnadir);
				
				frmAltaVentaPaso2.setSize(290,260);
				frmAltaVentaPaso2.setResizable(false);
				frmAltaVentaPaso2.setLocationRelativeTo(null);
				frmAltaVentaPaso2.addWindowListener(this);
				frmAltaVentaPaso2.setVisible(true);
			}
		}
		else
		{
			// Si le dieron al botón Añadir:
			bd = new BaseDatos();
			connection = bd.conectar();
			
			//Solo añadimos la venta si la cantidad ingresada es mayor a 0 y si hay suficientes plazas disponibles en el paquete.
			if(Integer.parseInt(txtCantidad.getText())>0 && plazasPaquete>=Integer.parseInt(txtCantidad.getText())) 
			{
				try
				{
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					//Crear un objeto ResultSet para guardar lo obtenido
					//y ejecutar la sentencia SQL
					sentencia = "INSERT INTO ventaspaquetes VALUES (null, "
							+ choEmpleados.getSelectedItem().split("-")[0]
							+ ", " +idPaquete + ", "+txtCantidad.getText()+ ")";
					statement.executeUpdate(sentencia);
					lblMensaje.setText("Venta Añadida");
				}
				catch (SQLException sqle)
				{
					lblMensaje1.setText("Error al carga la venta");
					dlgMensaje1.setLayout(new FlowLayout());
					dlgMensaje1.addWindowListener(this);
					dlgMensaje1.setSize(250,100);
					dlgMensaje1.setResizable(false);
					dlgMensaje1.setLocationRelativeTo(null);
					dlgMensaje1.add(lblMensaje1);
					dlgMensaje1.setVisible(true);
				}
				
				//Si hay un empleado seleccionado, actualizamos las plazas del paquete. 
				if(!choEmpleados.getSelectedItem().equals("Seleccionar un empleado")) {
					try
					{
						//Crear una sentencia
						statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						//Crear un objeto ResultSet para guardar lo obtenido
						//y ejecutar la sentencia SQL
						sentencia = "UPDATE paquetesviaje SET plazasPaquete ="
								+ (plazasPaquete - Integer.parseInt(txtCantidad.getText()))
								+ " WHERE idPaquete = " + idPaquete;
						statement.executeUpdate(sentencia);
						lblMensaje.setText("Venta Añadida. Ahora quedan " + (plazasPaquete - Integer.parseInt(txtCantidad.getText())) + " plazas.");
						
					}
					catch (SQLException sqle)
					{
					}	
					dlgMensaje.setLayout(new FlowLayout());
					dlgMensaje.addWindowListener(this);
					dlgMensaje.setSize(250,100);
					dlgMensaje.setResizable(false);
					dlgMensaje.setLocationRelativeTo(null);
					dlgMensaje.add(lblMensaje);
					dlgMensaje.setVisible(true);
					bd.desconectar(connection);
				}
				
			} else {
				lblMensaje1.setText("Error en la cantidad ingresada");
				dlgMensaje1.setLayout(new FlowLayout());
				dlgMensaje1.addWindowListener(this);
				dlgMensaje1.setSize(190,100);
				dlgMensaje1.setResizable(false);
				dlgMensaje1.setLocationRelativeTo(null);
				dlgMensaje1.add(lblMensaje1);
				dlgMensaje1.setVisible(true);
			}
			
		}
	}

	//WINDOW LISTENER METHOD
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0)
		{
			if(frmAltaVentaPaso1.isActive())
			{
				frmAltaVentaPaso1.setVisible(false);
			}
			else if(frmAltaVentaPaso2.isActive())
			{
				frmAltaVentaPaso2.setVisible(false);
				frmAltaVentaPaso1.setVisible(false);
				
			}else if(dlgMensaje1.isActive()) {
				dlgMensaje1.setVisible(false);
			}
			else {
				dlgMensaje.setVisible(false);
				frmAltaVentaPaso2.setVisible(false);
				frmAltaVentaPaso1.setVisible(false);
			}
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
}
