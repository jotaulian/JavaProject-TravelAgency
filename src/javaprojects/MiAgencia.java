package javaprojects;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MiAgencia implements WindowListener, ActionListener
{
	// Ventana Principal
	Frame ventana = new Frame("Mi Agencia");

	MenuBar mnBar = new MenuBar();

	Menu mnuPaquetes = new Menu("Paquetes");
	MenuItem mniAltaPaquete = new MenuItem("Alta");
	MenuItem mniBajaPaquete = new MenuItem("Baja");
	MenuItem mniModificacionPaquete = new MenuItem("Modificación");
	MenuItem mniConsultaPaquete = new MenuItem("Consulta");

	Menu mnuEmpleados = new Menu("Empleados");
	MenuItem mniAltaEmpleado = new MenuItem("Alta");
	MenuItem mniBajaEmpleado = new MenuItem("Baja");
	MenuItem mniModificacionEmpleado = new MenuItem("Modificación");
	MenuItem mniConsultaEmpleado = new MenuItem("Consulta");
	
	Menu mnuVentas = new Menu("Ventas");
	MenuItem mniAltaVenta = new MenuItem("Alta");
	MenuItem mniBajaVenta = new MenuItem("Baja");
	MenuItem mniModificacionVenta = new MenuItem("Modificación");
	MenuItem mniConsultaVenta = new MenuItem("Consulta");

	Menu mnuNominas = new Menu("Nóminas");
	MenuItem mniAltaNomina = new MenuItem("Alta");
	MenuItem mniBajaNomina = new MenuItem("Baja");
	MenuItem mniModificacionNomina = new MenuItem("Modificación");
	MenuItem mniConsultaNomina= new MenuItem("Consulta");


	public MiAgencia( int tipo)
	{
		ventana.setLayout(new FlowLayout());
		mniAltaPaquete.addActionListener(this);
		mnuPaquetes.add(mniAltaPaquete);
		if(tipo==0) {			
			mniBajaPaquete.addActionListener(this);
			mnuPaquetes.add(mniBajaPaquete);
			mniModificacionPaquete.addActionListener(this);
			mnuPaquetes.add(mniModificacionPaquete);
			mniConsultaPaquete.addActionListener(this);
			mnuPaquetes.add(mniConsultaPaquete);
		}
		mnBar.add(mnuPaquetes);

		mniAltaEmpleado.addActionListener(this);
		mnuEmpleados.add(mniAltaEmpleado);
		if(tipo==0) {			
			mniBajaEmpleado.addActionListener(this);
			mnuEmpleados.add(mniBajaEmpleado);
			mniModificacionEmpleado.addActionListener(this);
			mnuEmpleados.add(mniModificacionEmpleado);
			mniConsultaEmpleado.addActionListener(this);
			mnuEmpleados.add(mniConsultaEmpleado);
		}
		mnBar.add(mnuEmpleados);

		mniAltaNomina.addActionListener(this);
		mnuNominas.add(mniAltaNomina);
		if(tipo==0) {			
//		mnuNominas.add(mniBajaNomina);
//		mnuNominas.add(mniModificacionNomina);
			mniConsultaNomina.addActionListener(this);
			mnuNominas.add(mniConsultaNomina);
		}
		mnBar.add(mnuNominas);

		mniAltaVenta.addActionListener(this);
		mnuVentas.add(mniAltaVenta);
		if(tipo==0) {			
//		mnuVentas.add(mniBajaVenta);
//		mnuVentas.add(mniModificacionVenta);
			mniConsultaVenta.addActionListener(this);
			mnuVentas.add(mniConsultaVenta);
		}
		mnBar.add(mnuVentas);

		ventana.setMenuBar(mnBar);

		ventana.setSize(320,160);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}

	
	//ACTION LISTENER METHOD
	public void actionPerformed(ActionEvent evento)
	{
		//Paquetes
		if(evento.getSource().equals(mniAltaPaquete))
		{
			new AltaPaquete();
		}
		else if(evento.getSource().equals(mniConsultaPaquete))
		{
			new ConsultaPaquete();
		}
		else if(evento.getSource().equals(mniBajaPaquete))
		{
			new BajaPaquete();
		} 
		else if(evento.getSource().equals(mniModificacionPaquete))
		{
			new ModificarPaquete();
		}
		//Empleados
		else if(evento.getSource().equals(mniAltaEmpleado))
		{
			new AltaEmpleado();
		}
		else if(evento.getSource().equals(mniConsultaEmpleado))
		{
			new ConsultaEmpleado();
		}
		else if(evento.getSource().equals(mniBajaEmpleado))
		{
			new BajaEmpleado();
		}
		else if(evento.getSource().equals(mniModificacionEmpleado)) 
		{
			new ModificarEmpleado();
		}
		//Nominas
		else if(evento.getSource().equals(mniAltaNomina)) 
		{
			new AltaNomina();
		}
		else if(evento.getSource().equals(mniConsultaNomina)) 
		{
			new ConsultaNomina();
		}
		//Ventas
		else if(evento.getSource().equals(mniAltaVenta)) 
		{
			new AltaVenta();
		}
		else if(evento.getSource().equals(mniConsultaVenta)) 
		{
			new ConsultaVenta();
		}
		
	}
	
	//WINDOW LISTENER METHODS
		public void windowActivated(WindowEvent we) {}
		public void windowClosed(WindowEvent we) {}
		public void windowClosing(WindowEvent we)
		{
				System.exit(0);
		}
		public void windowDeactivated(WindowEvent we) {}
		public void windowDeiconified(WindowEvent we) {}
		public void windowIconified(WindowEvent we) {}
		public void windowOpened(WindowEvent we) {}
}