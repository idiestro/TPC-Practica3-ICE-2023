package server1;

import Ice.Current;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

public class FunctionsI extends ServerFunctions._Server1Disp{
	
	private HashMap<String, String> clientData;
	private Map<String, String> txtFileData;
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
			//Save all dataInputs value directly into clientData
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
        	int id = 0;
        	//While data (file) exist read info
        	while((line = reader.readLine()) != null) {
    			//Save txt info into txtFileData - with format: "Client X": "txt line"
    			txtFileData.put(("Client " + id), line);
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
			boolean result = false;
			//Instance HashMap class
			
			for(HashMap.Entry<String, String> entry : clientData.entrySet()) {
				Object value = entry.getValue();
				
				if(txtFileData.containsValue(value)) {
					result = true;
					break;
				}
			}
			return result;
			
		}catch(Exception e) {
    		System.err.println("Error al comparar HashMap de cliente y csv:" + e.getMessage());
    		return false;

		}
		
	}

}
