package server1;

import Ice.ObjectPrx;
import Ice.Properties;
import Utils.*;

/*
 * Kill port process: 
 * sudo kill -9 `sudo lsof -t -i:10000`
 */

public class Server1 implements Runnable{
	
	private final int serverListenPort;
	
	public Server1() throws Exception {
		java.util.Properties prop =  Utils.getConfigProperties();
		serverListenPort = Integer.parseInt(prop.getProperty("SERVER1_PORT"));
	}
	
	public void run() {
		//Init Ice communicator
				//status hold program status
				int status = 0;
				//Init communicator
				Ice.Communicator ic = null;
				
				try {
					//Init ICE communication
					ic = Ice.Util.initialize();
					//Create ObjectAdapter to listen in specified port
					Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints(
							"SimpleCalculadoraAdapter", ("default -p " + serverListenPort));
					//Create Calculadora object
					Ice.Object object = new CalculadoraI();
					//Add Calculadora object to adapter
					ObjectPrx add = adapter.add(object, ic.stringToIdentity("SimpleCalculadora"));
					//Activate adapter to listen connections
					adapter.activate();
					//Wait until shutdown
					ic.waitForShutdown();
				//Ice local exceptions
				} catch (Ice.LocalException e) {
					//Print error message
					e.printStackTrace();
					//Change status 
					status = 1;
				//General exceptions
				} catch (Exception e) {
					//Print error message
					System.err.println(e.getMessage());
					//Change status 
					status = 1;
				}
				
				//Clean and exit
				if (ic != null) {
					// Clean up
					//
					try {
						//Close connection
						ic.close();
						//Destroy connection
						ic.destroy();
					//General exceptions
					} catch (Exception e) {
						//Print error message
						System.err.println(e.getMessage());
						//Change status
						status = 1;
					}
				}
				//Close system if status==1
				System.exit(status);
	}
	
	public static void main(String[] args) {
		try {
			//Implements multithread
			Server1 server1 = new Server1();
			server1.run();
		//Exception
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
