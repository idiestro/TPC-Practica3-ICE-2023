package client;

import Utils.Utils;
import java.util.HashMap;
import java.util.Map;

public class Client implements Runnable{
	
	private final HashMap <String, Integer> serverPorts;
	private HashMap <String, String> testClientData;

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
		
		//TestData
		testClientData = new HashMap <>();
		testClientData.put("name", "Nacho");
		testClientData.put("firstSurname", "Diestro");
		testClientData.put("secondSurname", "Gil");
		testClientData.put("email", "test@test.com");
		testClientData.put("phone", "666666666");
		
		System.out.println("----TEST----");
		System.out.println("----Values for test:----");
		System.out.println(testClientData);
		
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
					
					//Use Calculadora functions
					server1.getClientData(testClientData);
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
