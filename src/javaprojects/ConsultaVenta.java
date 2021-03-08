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

public class ConsultaVenta implements WindowListener
{
	//Ventana Consulta de Nóminas
	Frame frmConsultaVentas = new Frame("Consulta Ventas");
	TextArea listadoVentas = new TextArea(5, 50);
	Button btnPdfVentas = new Button("PDF");
			
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
		
	//Realizamos una sub-consulta para mostrar los datos de los empleados al hacer la consulta
	String subSentenciaEmpleados = "";
	Statement statementEmpleados = null;
	ResultSet rsEmpleados = null;
	//Realizamos una sub-consulta para mostrar los datos del paquete al hacer la consulta
	String subSentenciaPaquete = "";
	Statement statementPaquete = null;
	ResultSet rsPaquete = null;
	BaseDatos bd = new BaseDatos();
	
	public ConsultaVenta() {
		frmConsultaVentas.setLayout(new FlowLayout());
		// Conectar
		connection = bd.conectar();
		// Hacer un SELECT * FROM ventaspaquetes
		sentencia = "SELECT * FROM ventaspaquetes";
		
		// La información está en ResultSet
		// Recorrer el RS y por cada registro,
		// meter una línea en el TextArea
		try
		{
			//Crear las sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			statementEmpleados = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			statementPaquete = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			listadoVentas.selectAll();
			listadoVentas.setText("");
			listadoVentas.append("idventa\tEmpleado\t\tPaquete\t\t\tCantidad\n");
			while(rs.next())
			{
				subSentenciaEmpleados = "select * from empleados where idEmpleado=" + rs.getInt("idEmpleadoFK");
				rsEmpleados = statementEmpleados.executeQuery(subSentenciaEmpleados);
				rsEmpleados.next();
				
				subSentenciaPaquete = "select * from paquetesviaje where idPaquete=" + rs.getInt("idPaqueteFK");
				rsPaquete = statementPaquete.executeQuery(subSentenciaPaquete);
				rsPaquete.next();
				
				listadoVentas.append(rs.getInt("idVentaPaquete")
						+"\t"+rsEmpleados.getString("nombreEmpleado")+ "\s" +rsEmpleados.getString("apellidoEmpleado")+"\s(#" + rsEmpleados.getInt("idEmpleado")+")"
						+"\t\t"+rsPaquete.getString("nombrePaquete")+ "\s(#" +rsPaquete.getInt("idPaquete")+")"
						+"\t\t"+rs.getInt("cantidadVentaPaquete")
						+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoVentas.setText("Se ha producido un error en la consulta");
		}
		finally
		{
			listadoVentas.setEditable(false);
			frmConsultaVentas.add(listadoVentas);
			frmConsultaVentas.add(btnPdfVentas);
			frmConsultaVentas.setSize(480,160);
			frmConsultaVentas.setResizable(false);
			frmConsultaVentas.setLocationRelativeTo(null);
			frmConsultaVentas.addWindowListener(this);
			frmConsultaVentas.setVisible(true);
			bd.desconectar(connection);
		}
	}

	//WINDOW LISTENER METHODS
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){
			System.exit(0);
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
}
