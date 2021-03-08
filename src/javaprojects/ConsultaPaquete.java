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


public class ConsultaPaquete implements WindowListener
{
	//Ventana Consulta de Clientes
		Frame frmConsultaPaquetes = new Frame("Consulta Paquetes");
		TextArea listadoClientes = new TextArea(5, 40);
		Button btnPdfPaquetes = new Button("PDF");
	
		String sentencia = "";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		BaseDatos bd = new BaseDatos();


public ConsultaPaquete() {
	frmConsultaPaquetes.setLayout(new FlowLayout());
	// Conectar
	connection = bd.conectar();
	// Hacer un SELECT * FROM clientes
	sentencia = "SELECT * FROM paquetesviaje";
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
		listadoClientes.selectAll();
		listadoClientes.setText("");
		listadoClientes.append("#ID\tNombre\t\tDestinos\tPlazas\tPrecio\n");
		while(rs.next())
		{
			listadoClientes.append(rs.getInt("idPaquete")
					+"\t"+rs.getString("nombrePaquete")
					+"\t"+rs.getInt("cantidadDestinosPaquete")
					+"\t"+rs.getInt("plazasPaquete")
					+"\t"+rs.getDouble("precioPaquete")
					+"\n");
		}
	}
	catch (SQLException sqle)
	{
		listadoClientes.setText("Se ha producido un error en la consulta");
	}
	finally
	{
		listadoClientes.setEditable(false);
		frmConsultaPaquetes.add(listadoClientes);
		frmConsultaPaquetes.add(btnPdfPaquetes);
		frmConsultaPaquetes.setSize(400,160);
		frmConsultaPaquetes.setResizable(false);
		frmConsultaPaquetes.setLocationRelativeTo(null);
		frmConsultaPaquetes.addWindowListener(this);
		frmConsultaPaquetes.setVisible(true);
		bd.desconectar(connection);
	}
}


	//WINDOW LISTENER METHODS
	public void windowActivated(WindowEvent arg0){}
	public void windowClosed(WindowEvent arg0){}
	public void windowClosing(WindowEvent arg0)
	{
		if(frmConsultaPaquetes.isActive())
		{
			frmConsultaPaquetes.setVisible(false);
		}
	}
	public void windowDeactivated(WindowEvent arg0){}
	public void windowDeiconified(WindowEvent arg0){}
	public void windowIconified(WindowEvent arg0){}
	public void windowOpened(WindowEvent arg0){}
		
}

