module ServerFunctions{

	dictionary <string,string> Map;
	
	interface Server1{
		void getClientData(string dataInput);
		string txtReader();
		bool compareInputWithSaveData();
	
	};
};
