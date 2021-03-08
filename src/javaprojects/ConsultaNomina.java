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


public class ConsultaNomina implements WindowListener
{
	//Ventana Consulta de Nóminas
	Frame frmConsultaNominas = new Frame("Consulta Nóminas");
	TextArea listadoNominas = new TextArea(5, 50);
	Button btnPdfNominas = new Button("PDF");
		
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	//Realizamos una sub-consulta para mostrar los datos de los empleados al hacer la consulta
	String subSentencia = "";
	Statement statementEmpleados = null;
	ResultSet rsEmpleados = null;
	BaseDatos bd = new BaseDatos();
			
	public ConsultaNomina() {
		frmConsultaNominas.setLayout(new FlowLayout());
		// Conectar
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM nominas";
		
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
			
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			listadoNominas.selectAll();
			listadoNominas.setText("");
			listadoNominas.append("Inicio\t\tFin\t\tImporte\t\tEmpleado\n");
			while(rs.next())
			{
				subSentencia = "select * from empleados where idEmpleado=" + rs.getInt("idEmpleadoFK");
				rsEmpleados = statementEmpleados.executeQuery(subSentencia);
				rsEmpleados.next();
				
				listadoNominas.append(rs.getString("fechaInicioNomina")
						+"\t"+rs.getString("fechaFinNomina")
						+"\t"+rs.getDouble("importeNomina")
						+"\t\t"+rsEmpleados.getString("nombreEmpleado")+ "\s" +rsEmpleados.getString("apellidoEmpleado")+"\s(#" + rsEmpleados.getInt("idEmpleado")+")"
						+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoNominas.setText("Se ha producido un error en la consulta");
		}
		finally
		{
			listadoNominas.setEditable(false);
			frmConsultaNominas.add(listadoNominas);
			frmConsultaNominas.add(btnPdfNominas);
			frmConsultaNominas.setSize(450,160);
			frmConsultaNominas.setResizable(false);
			frmConsultaNominas.setLocationRelativeTo(null);
			frmConsultaNominas.addWindowListener(this);
			frmConsultaNominas.setVisible(true);
			bd.desconectar(connection);
		}
	}
	
	//WINDOW LISTENER METHODS
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0){
		if(frmConsultaNominas.isActive())
		{
			frmConsultaNominas.setVisible(false);
		}
		}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
}
