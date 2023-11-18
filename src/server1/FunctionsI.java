package server1;

import Ice.Current;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

public class FunctionsI extends ServerFunctions._Server1Disp{
	
	private HashMap<String, String> clientData;
	private HashMap<String, String> csvData;

	/*
	 * Get client data and save into clientData
	 */
	@Override
	public void getClientData(Map<String, String> dataInput, Current current) {
		// TODO Auto-generated method stub
		try {
			for(HashMap.Entry<String, String> entry : dataInput.entrySet()) {
				clientData.put(entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			System.err.println("Error al guardar datos del cliente: " + e.getMessage());
		}
		
	}
	
	/*
	 * Read csv data and save into csvData
	 */
	@Override
	public String csvReader(String csvFilePath, Current current) {
		// TODO Auto-generated method stub

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> records = csvReader.readAll();

            //First file of csv contains keys
            String[] headers = records.get(0);

            //Create HashMap to storage csv values
            csvData = new HashMap<>();

            //Iterating upon csv files
            for (int i = 1; i < records.size(); i++) {
                String[] values = records.get(i);

                //Iterating upon columns and save data into HashMap
                for (int j = 0; j < headers.length; j++) {
                    String header = headers[j];
                    String value = values[j];
                    //Save Key-Value inside HashMap
                    csvData.put(header, value);
                }
            }

            //Print in consolde resulted HashMap
            System.out.println("Lectura de csv resultante: " + csvData);

        } catch (Exception e) {
        		System.err.println("Error al trabajar con archivo csv:" + e.getMessage());
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
			for(HashMap.Entry<String, String> entry : clientData.entrySet()) {
				Object value = entry.getValue();
				
				if(csvData.containsValue(value)) {
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
