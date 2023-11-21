module ServerFunctions{

	dictionary <string,string> Map;
	
	interface Server1{
		void getClientData(Map dataInput);
		string txtReader();
		void compareInputWithSaveData(out bool result);
	
	};
	interface Server2{
		void getClientData(Map dataInput);
		void txtWriter();
		void validateSavedInfo(out bool result);
	
	};
};
