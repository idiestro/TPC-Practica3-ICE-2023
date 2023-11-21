package client;

import Utils.*;
import java.util.HashMap;

import Ice.HeaderDictHolder;


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
					
					//Get server1 proxy
					Ice.ObjectPrx baseServer1 = ic.stringToProxy(("SimpleFunctions:default -p " + serverPorts.get("server1")));
					//Make secure cast of server1
					ServerFunctions.Server1Prx server1 = ServerFunctions.Server1PrxHelper.checkedCast(baseServer1);
					//Validate server1 != null
					if (server1 == null)
						//Error message
						throw new Error("Invalid proxy");
					
					//Get server2 proxy
					//Ice.ObjectPrx baseServer2 = ic.stringToProxy(("SimpleFunctions:default -p " + serverPorts.get("server2")));
					//Make secure cast of server2
					//ServerFunctions.Server1Prx server2 = ServerFunctions.Server1PrxHelper.checkedCast(baseServer2);
					//Validate server2 != null
					//if (server2 == null)
						//Error message
						//throw new Error("Invalid proxy");
					
					
					//Server 1 functions
					
					System.out.println("Server 1 search is working");
					//Declare data String
					HashMap <String,String> clientData = new HashMap<>();
					//Show info throws interface
					TextIO4GUI.putln("Bienvenido al sistema de consulta de usuarios");
					TextIO4GUI.putln("Por favor, introduce tu información:");
					//Get client info throws user interface
					TextIO4GUI.putln("Nombre:");
					clientData.put("name", TextIO4GUI.getlnString());
					TextIO4GUI.putln("Primer apellido:");
					clientData.put("firstSurname", TextIO4GUI.getlnString());
					TextIO4GUI.putln("Segundo apellido:");
					clientData.put("secondSurname", TextIO4GUI.getlnString());
					TextIO4GUI.putln("Email:");
					clientData.put("email", TextIO4GUI.getlnString());
					TextIO4GUI.putln("Telécono:");
					clientData.put("phone", TextIO4GUI.getlnString());
					System.out.println("User data:" + clientData);
					//Send user data to server
					server1.getClientData(clientData);
					//Read server db values
					server1.txtReader();
					//Declare Ice Holder to save search result
					Ice.BooleanHolder searchResult = new Ice.BooleanHolder();
					//Compare if user is on db
					server1.compareInputWithSaveData(searchResult);
					System.out.println("Server 1 search done");
					System.out.println("Search Result = " + searchResult.value);
					
					//Create result message
					String searchResultMessage = searchResult.value? 
					"El cliente ya se encuentra en la base de datos":
					"El cliente no se encuentra en la base de datos, llamamos a Server 2 para almacenarlo";
					TextIO4GUI.putln(searchResultMessage);
					
					
					//Server 2 functions


					
					
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
