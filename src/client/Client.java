package client;

import Utils.*;
import java.util.HashMap;


public class Client implements Runnable{
	
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
		//Instace user interface
		new TextIO4GUI("Consulta de Usuarios");
	
	}
	
	public void run() {
		//Init Ice communicator
				//status hold program status
				int status = 0;
				//Init cmmunicator
				Ice.Communicator ic = null;
				
				try {
					//Init ICE communication
					ic = Ice.Util.initialize();
					System.out.println("------Client connected-----");
					//Get server proxy
					Ice.ObjectPrx base = ic.stringToProxy("SimpleFunctions:default -p 10000");
					//Make secure cast of server1
					ServerFunctions.Server1Prx server1 = ServerFunctions.Server1PrxHelper.checkedCast(base);
							//Demo.CalculadoraPrx calculadora = Demo.CalculadoraPrxHelper.checkedCast(base);
					//Validate calculadora != null
					if (server1 == null)
						//Error message
						throw new Error("Invalid proxy");
					

					//Declare data String
					String clientData;
					TextIO4GUI.putln("Bienvenido al sistema de consulta de usuarios");
					TextIO4GUI.putln("Escribe, en el orden indicado y de uno en uno:");
					TextIO4GUI.putln("Nombre, primer apellido, segundo apellido, email y teléfono");
					//Send user data to server
					for(int i=0; i<5; i++) {
						clientData = TextIO4GUI.getlnString();
						server1.getClientData(clientData);

					}
					server1.txtReader();
					System.out.println(server1.compareInputWithSaveData());
					
					
							//Ice.IntHolder coc=new Ice.IntHolder(), res=new Ice.IntHolder(); // Inicicialización de los datos Holder (parámetros de entrada y salida)
							//calculadora.cociente(6,5, coc, res);
							//System.out.println("cociente: "+coc.value+" resto: "+res.value);		
				//Local exception				
				} catch (Ice.LocalException e) {
					e.printStackTrace();
					status = 1;
					System.out.println("------Client disconnected-----");
				//General exception
				} catch (Exception e) {
					System.err.println(e.getMessage());
					status = 1;
					System.out.println("------Client disconnected-----");
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
						System.out.println("------Client disconnected-----");
					}
				}
				//Close system if status==1
				System.exit(status);
			}
		
	
	
	public static void main(String[] args) {
		try {
			Client client = new Client();
			client.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(("El cliente no ha podido ser iniciado: " + e.getMessage()));;
		}
	}
}
