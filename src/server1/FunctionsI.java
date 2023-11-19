package server1;

import Ice.Current;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

public class FunctionsI extends ServerFunctions._Server1Disp{
	
	private HashMap<String, String> clientData;
	private String [] txtFileData;
	private String txtFilePath = (System.getProperty("user.dir") + "/Resources/DB/PretendingBeDB.txt");
	
	private static int counter = 0;
			
	/*
	 * Get client data and save into clientData
	 */
	@Override
	public void getClientData(String dataInput, Current current) {
		// TODO Auto-generated method stub
		try {
			//Instance HashMap class
			clientData = new HashMap<>();
			
			switch(counter) {
				case 0: 
					clientData.put("name", dataInput);
					break;
				case 1: 
					clientData.put("firstSurname", dataInput);
					break;
				case 2: 
					clientData.put("secondSurname", dataInput);
					break;
				case 3: 
					clientData.put("email", dataInput);
					break;
				case 4: 
					clientData.put("phone", dataInput);
					break;
			}
			counter++;
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
        	int id = 0;
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

	@Override
	public boolean compareInputWithSaveData(Current current) {
		// TODO Auto-generated method stub
		try {
			//Declare boolean for search result
			boolean result = false;
			//Search name, firstSurname and secondSurname coincidence
			if(	(txtFileData[0].toLowerCase().contains(clientData.get("name").toLowerCase())) && 
				(txtFileData[1].toLowerCase().contains(clientData.get("firstSurname").toLowerCase())) &&
				(txtFileData[2].toLowerCase().contains(clientData.get("secondSurname").toLowerCase()))) {
				//If client is on txt file, search result is true
				result = true;
			}
			return result;
			
		}catch(Exception e) {
    		System.err.println("Error al comparar datos de cliente y PretendingBeDB.txt:" + e.getMessage());
    		return false;

		}
		
	}

}
