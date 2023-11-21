package server1;

import Ice.BooleanHolder;
import Ice.Current;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FunctionsI extends ServerFunctions._Server1Disp{
	
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> clientData;
	private String [] txtFileData;
	private String txtFilePath = (System.getProperty("user.dir") + "/Resources/DB/PretendingBeDB.txt");
	
			
	/*
	 * Get client data and save into clientData
	 */
	@Override
	public void getClientData(Map<String, String> dataInput, Current current) {
		// TODO Auto-generated method stub
		try {
			//Instance HashMap class
			clientData = new HashMap<>();
			//Save client info in each hashmap key
			clientData.putAll(dataInput);
		//Exception control
		} catch (Exception e) {
			System.err.println("Error al guardar datos del cliente: " + e.getMessage());
		}
		
	}
	
	/*
	 * Read csv data and save into csvData
	 */
	@Override
	public String txtReader(Current current) {
		// TODO Auto-generated method stub

        try {
        	//Create reader for file
        	BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
        	//Create variables to save data
        	String line;
        	//While data (file) exist read info
        	while((line = reader.readLine()) != null) {
    			//Save txt info into txtFileData - with format: "Client X": "txt line"
    			txtFileData = line.split(" ");
        	}
        	reader.close();
        //Input/Output exception control
        } catch (IOException e) {
        		System.err.println("Error al trabajar con archivo .txt: " + e.getMessage());
        }
		return null;
	}
	
	/*
	 * Compare values between clientData and csvData
	 */

	public void compareInputWithSaveData(BooleanHolder result, Current current) {
		// TODO Auto-generated method stub
		try {
			//Declare boolean for search result
			result.value =
				//Search name, firstSurname and secondSurname coincidence
				(txtFileData[0].toLowerCase().contains(clientData.get("name").toLowerCase())) && 
				(txtFileData[1].toLowerCase().contains(clientData.get("firstSurname").toLowerCase())) &&
				(txtFileData[2].toLowerCase().contains(clientData.get("secondSurname").toLowerCase()));

			
		}catch(Exception e) {
    		System.err.println("Error al comparar datos de cliente y PretendingBeDB.txt:" + e.getMessage());
    		result.value = false;
		}
		
	}


}
