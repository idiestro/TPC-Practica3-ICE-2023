module ServerFunctions{

	dictionary <string,string> Map;
	
	interface Server1{
		void getClientData(Map dataInput);
		string csvReader(string csvFilePath);
		bool compareInputWithSaveData();
	
	};
};
