module ServerFunctions{

	dictionary <string,string> Map;
	
	interface Server1{
		void getClientData(Map dataInput);
		string txtReader();
		bool compareInputWithSaveData();
	
	};
};
