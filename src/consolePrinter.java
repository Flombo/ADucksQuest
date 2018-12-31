class consolePrinter {

	//Prints all Fields after everything is set
	void printAllFields(int yDimension, int xDimension, Field[][] fields){
		for(int i = 0; i < yDimension; i++){
			for(int j = 0; j < xDimension; j++){
				switch(fields[j][i].getName()){
					case "Field":
						System.out.print("[]");
						break;
					case "Player":
						System.out.print("[P]");
						break;
					case "Target":
						System.out.print("[T]");
						break;
				}
			}
			System.out.println(" ");
		}
	}
}
