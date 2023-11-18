package client;

import Utils.Utils;
import java.util.HashMap;

public class Client {
	
	private final HashMap <String, Integer> serverPorts;

	/*
	 * Constructor
	 */
	public Client() throws Exception {
		//Call properties reader
		java.util.Properties prop =  Utils.getConfigProperties();
		//Init hashmap to save servers port
		serverPorts = new HashMap <String, Integer>();
		//Save ports from config into serverPorts HashMap
		serverPorts.put("server1", Integer.parseInt(prop.getProperty("SERVER1_PORT")));
		serverPorts.put("server2", Integer.parseInt(prop.getProperty("SERVER2_PORT")));
	}
	
	
	
	public static void main(String[] args) {
	//Init Ice communicator
		//status hold program status
		int status = 0;
		//Init cmmunicator
		Ice.Communicator ic = null;
		
		try {
			//Init ICE communication
			ic = Ice.Util.initialize(args);
			//Get server proxy
			Ice.ObjectPrx base = ic
					.stringToProxy("SimpleCalculadora:default -p 10000");
			//Make secure cast of Calculadora
			Demo.CalculadoraPrx calculadora = Demo.CalculadoraPrxHelper.checkedCast(base);
			//Validate calculadora != null
			if (calculadora == null)
				//Error message
				throw new Error("Invalid proxy");
			
			//Use Calculadora functions
			System.out.println(calculadora.sumar(5,6));
			System.out.println(calculadora.restar(5,6));
			System.out.println(calculadora.producto(5,6));
			
			Ice.IntHolder coc=new Ice.IntHolder(), res=new Ice.IntHolder(); // Inicicialización de los datos Holder (parámetros de entrada y salida)
			calculadora.cociente(6,5, coc, res);
			System.out.println("cociente: "+coc.value+" resto: "+res.value);		
		//Local exceptio				
		} catch (Ice.LocalException e) {
			e.printStackTrace();
			status = 1;
		//General exception
		} catch (Exception e) {
			System.err.println(e.getMessage());
			status = 1;
		}
		//Clean and Exit
		if (ic != null) {
			// Clean up
			try {
				ic.close();
				ic.destroy();
			//Exception
			} catch (Exception e) {
				System.err.println(e.getMessage());
				//Change program status
				status = 1;
			}
		}
		//Close system if status==1
		System.exit(status);
	}
}
