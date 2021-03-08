package javaprojects;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConsultaEmpleado implements WindowListener
{
	//Ventana Consulta de Clientes
		Frame frmConsultaEmpleados = new Frame("Consulta Empleados");
		TextArea listadoEmpleados = new TextArea(5, 60);
		Button btnPdfEmpleados = new Button("PDF");

		String sentencia = "";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		BaseDatos bd = new BaseDatos();
		
	public ConsultaEmpleado() {
		frmConsultaEmpleados.setLayout(new FlowLayout());
		// Conectar
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM empleados";
		// La información está en ResultSet
		// Recorrer el RS y por cada registro,
		// meter una línea en el TextArea
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			listadoEmpleados.selectAll();
			listadoEmpleados.setText("");
			listadoEmpleados.append("Id\tDni\t\tNombre\tApellidos\t\tDomicilio\t\t\tFecha\sIngreso\n");
			while(rs.next())
			{
				listadoEmpleados.append(rs.getInt("idEmpleado")
						+"\t"+rs.getString("dniEmpleado")
						+"\t"+rs.getString("nombreEmpleado")
						+"\t"+rs.getString("apellidoEmpleado")
						+"\t\t"+rs.getString("domicilioEmpleado")
						+"\t"+rs.getString("fechaIngresoEmpleado")
						+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoEmpleados.setText("Se ha producido un error en la consulta");
		}
		finally
		{
			listadoEmpleados.setEditable(false);
			frmConsultaEmpleados.add(listadoEmpleados);
			frmConsultaEmpleados.add(btnPdfEmpleados);

			frmConsultaEmpleados.setSize(550,160);
			frmConsultaEmpleados.setResizable(false);
			frmConsultaEmpleados.setLocationRelativeTo(null);
			frmConsultaEmpleados.addWindowListener(this);
			frmConsultaEmpleados.setVisible(true);
			bd.desconectar(connection);
		}
	}

	//WINDOW LISTENER METHODS
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){
		if(frmConsultaEmpleados.isActive())
		{
			frmConsultaEmpleados.setVisible(false);
		}
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}

}
