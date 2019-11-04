import java.util.Random;


public class Food {
	/*
	Random randomGeneratorX;							    // Declare a variable called randomGenerator of type Random
	randomGeneratorX = new Random();						// make a random number generator and store it in the variable called randomGenerator
	int randomIntegerX = randomGeneratorX.nextInt(10);	// make a random number from 0 to 10 and store it in the variable randomInteger
	
	Random randomGeneratorY;
	randomGeneratorY = new Random();
	int randomIntegerY = randomGeneratorY.nextInt(10);
	
	int x = randomIntegerX;
	int y = randomIntegerY;

	
	 */
	public void FoodFunction(String filename){
		
		Random randomGeneratorX;							    // Declare a variable called randomGenerator of type Random
		randomGeneratorX = new Random();						// make a random number generator and store it in the variable called randomGenerator
		int randomIntegerX = randomGeneratorX.nextInt(950)+50;	// make a random number from 0 to 10 and store it in the variable randomInteger
		
		Random randomGeneratorY;
		randomGeneratorY = new Random();
		int randomIntegerY = randomGeneratorY.nextInt(468)+200;
		
		int x = randomIntegerX;
		int y = randomIntegerY;
		
		EZImage[] food = new EZImage[5];
			for(int i = 0; i < 5; i++) {
				food[i] = EZ.addImage(filename, x, y);
		}
	}
}

