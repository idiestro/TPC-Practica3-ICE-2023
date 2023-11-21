package server2;

import Ice.BooleanHolder;
import Ice.Current;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FunctionsII extends ServerFunctions._Server2Disp{
	
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> clientData;
	private String txtFileData;
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
	 * Write client info into txt file
	 */
	@Override
	public void txtWriter(Current current) {
		// TODO Auto-generated method stub

		//Convert hashmap to string
		hashMapToString();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath, true))) {
			//Set ln
			writer.newLine();
			//Write new info
			writer.write(txtFileData);


		}catch (IOException e) {
			System.out.println("Error al escribir en archivo txt: " + e.getMessage());
		}
	}
	
	/*
	 * Validate if info is saved into txt
	 */
	@Override
	public void validateSavedInfo(BooleanHolder result, Current current) {
		// TODO Auto-generated method stub
		try {
			//Create reader for file
			BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
			//Create variables to save data
			String line;
			//While data (file) exist read info
			while((line = reader.readLine()) != null) {
				//Validate if client name is writed in txt
				result = line.contains(clientData.get("name")) ? true:false;
			}
			reader.close();
			//Input/Output exception control
		} catch (IOException e) {
			System.err.println("Error al leer en archivo .txt: " + e.getMessage());
		}
	}

	/*
	 * Not ICE method, util for convert hashmap to string
	 */
	private void hashMapToString() {
		//Get from HashMap and build string: values separated by space
		txtFileData = clientData.get("name") + " " +
				clientData.get("firstSurname") + " " +
				clientData.get("secondSurname") + " " +
				clientData.get("email") + " " +
				clientData.get("phone");
	}


}
