import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Main2Testing {

	static final int DOG_RUNNING = 1;
	static final int DOG_DIES = 2;
	static final int MAX_SCREEN_X = 1023;
	static final int MAX_SCREEN_Y = 768;
	static final int MAX_BONES = 5;
	static final int MAX_WATER = 5;

	public static void main(String[] args) {

		EZ.initialize(MAX_SCREEN_X, MAX_SCREEN_Y);

		int start = DOG_DIES;

		// DECLARING BACKGROUND ITEMS
		EZImage sky1 = EZ.addImage("Sky.png", 1023 / 2, 768 / 2);
		EZImage clouds1 = EZ.addImage("clouds.png", 1023 / 2, 768 / 2);
		EZImage clouds2 = EZ.addImage("clouds.png", 511 * 3, 768 / 2);
		EZImage island1 = EZ.addImage("islands.png", 1023 / 2, 768 / 2);
		EZImage ocean1 = EZ.addImage("ocean.png", 1023 / 2, 768 / 2);
		EZImage ocean2 = EZ.addImage("ocean1.png", 1535, 768 / 2);
		EZImage CloudReflection1 = EZ.addImage("CloudReflection1.png", 1023 / 2, 768 / 2);
		EZImage CloudReflection2 = EZ.addImage("CloudReflection1.png", 511 * 3, 768 / 2);
		EZImage WaterTexture = EZ.addImage("WaterTexture.png", 1023 / 2, 768 / 2);
		EZImage WaterTexture2 = EZ.addImage("WaterTexture2.png", 1535, 768 / 2);
		EZImage IslandReflection = EZ.addImage("IslandsReflection.png", 1023 / 2, 768 / 2);

		// sound effect
		EZSound WaveSound = EZ.addSound("WaveSound.wav");
		EZSound DogWhine = EZ.addSound("Dog_Whine.wav");
		EZSound Ding2 = EZ.addSound("PickUpItem.wav");
		EZSound SharkChomp = EZ.addSound("ChompSound.wav");
		EZSound BackgroundMusic = EZ.addSound("Shipwreck_Shore.wav");
		EZSound GameMenuBling = EZ.addSound("Game_Menu.wav");
		EZSound GameMenuBackgroundMusic = EZ.addSound("Island_Night.wav");
		EZSound GameOver = EZ.addSound("Game_over.wav");

		// shark
		Shark sharkPicture[] = new Shark[3];
		for (int i = 0; i < 3; i++) {
			sharkPicture[i] = new Shark();
		}

		// food
		FoodNew bones[] = new FoodNew[MAX_BONES];
		for (int i = 0; i < MAX_BONES; i++) {
			bones[i] = new FoodNew();
		}
		int FoodBar = 100;
		double FoodCount = 0;

		// water
		Water bottle[] = new Water[MAX_WATER];
		for (int i = 0; i < MAX_WATER; i++) {
			bottle[i] = new Water();
		}
		int WaterBar = 100;
		double WaterCount = 0;

		// creating dog from Dog (class)
		Dog myDog = new Dog();

		// text box for food and water count
		EZ.addImage("healthbox.png", 1023 / 2, 768 / 2);
		int LivesLeftfontsize = 22;
		EZText FoodLeft = EZ.addText(868, 680, "Food Left: " + FoodBar + "%", new Color(255, 255, 255),
				LivesLeftfontsize);
		EZText WaterLeft = EZ.addText(870, 725, "Water Left: " + WaterBar + "%", new Color(255, 255, 255),
				LivesLeftfontsize);

		// Game start image!
		EZImage Start = EZ.addImage("Game_Start_Test.png", 1023 / 2, 768 / 2);

		// background music while start menu is there!
		GameMenuBackgroundMusic.loop();

		// Game will only start AFTER you press the space bar!
		while (start == DOG_DIES) {

			if (EZInteraction.wasKeyReleased(KeyEvent.VK_SPACE)) {
				Start.hide();
				GameMenuBling.play();
				GameMenuBackgroundMusic.stop();
				start = DOG_RUNNING;
			}
			System.out.println("Start Screen!");
		}

		// loop wave sound!
		BackgroundMusic.loop();
		WaveSound.loop();

		// Actual coding for game
		while (start == DOG_RUNNING) {

			// coding to move backgrounds
			moveBackgrounds(clouds1, clouds2, ocean1, ocean2, WaterTexture, WaterTexture2, CloudReflection1,
					CloudReflection2);
			repeatBackgrounds(clouds1, clouds2, ocean1, ocean2, WaterTexture, WaterTexture2, CloudReflection1,
					CloudReflection2);

			// code to move myDog
			myDog.move();

			// if dog moves, water and food count goes down!!
			if (EZInteraction.isKeyDown('w') || (EZInteraction.isKeyDown('s')
					|| (EZInteraction.isKeyDown('a') || (EZInteraction.isKeyDown('d'))))) {
				FoodCount = FoodCount + 0.05;
				FoodBar = FoodBar - (int) FoodCount;
				WaterCount = WaterCount + 0.05;
				WaterBar = WaterBar - (int) WaterCount;

				if (FoodCount > 1) {
					FoodCount = 0;
				}
				if (WaterCount > 1) {
					WaterCount = 0;
				}
			}

			// food
			for (int i = 0; i < MAX_BONES; i++) {

				// bones moving to the left of the screen
				bones[i].move();

				// if bone hits dog, then +HP, sound effect play
				if (bones[i].bonePicture.isPointInElement(myDog.x + 30, myDog.y + 30)
						|| (bones[i].bonePicture.isPointInElement(myDog.x - 30, myDog.y - 20))
						|| (bones[i].bonePicture.isPointInElement(myDog.x + 50, myDog.y))
						|| (bones[i].bonePicture.isPointInElement(myDog.x - 50, myDog.y + 20))
						|| (bones[i].bonePicture.isPointInElement(myDog.x, myDog.y + 30))) {
					Ding2.play();
					if (FoodBar < 100) {
						FoodBar += 2;
						// System.out.println(FoodBar);
					}
					// bone randomly spawns somewhere else
					bones[i].init();

				}
			}
			// water
			for (int i = 0; i < MAX_WATER; i++) {

				// bottles moving to left
				bottle[i].move();

				// if bottle hits dog, water + 2, sound effect plays!
				if (bottle[i].waterPicture.isPointInElement(myDog.x + 30, myDog.y + 30)
						|| (bottle[i].waterPicture.isPointInElement(myDog.x - 30, myDog.y - 20))
						|| (bottle[i].waterPicture.isPointInElement(myDog.x + 50, myDog.y))
						|| (bottle[i].waterPicture.isPointInElement(myDog.x - 50, myDog.y + 20))
						|| (bottle[i].waterPicture.isPointInElement(myDog.x, myDog.y + 30))) {
					Ding2.play();
					if (WaterBar < 100) {
						WaterBar += 2;
						// System.out.println(WaterBar);
					}

					// randomly spawn bottles
					bottle[i].init();

				}
			}

			// shark
			for (int a = 0; a < 3; a++) {

				// shark movement
				sharkPicture[a].move();

				// if shark touches dog, food - 1, water -1; dog whine plays
				if (sharkPicture[a].sharkPicture.isPointInElement(myDog.x, myDog.y)) {
					sharkPicture[a].init();
					FoodBar -= 1;
					WaterBar -= 1;
					DogWhine.play();
				}
			}

			// if shark touches any of the bones, bones randomly spawn
			for (int a = 0; a < 3; a++) {

				for (int i = 0; i < MAX_WATER; i++) {
					if (sharkPicture[a].sharkPicture.isPointInElement((int) bones[i].x, (int) bones[i].y + 1)
							|| (sharkPicture[a].sharkPicture.isPointInElement((int) bones[i].x, (int) bones[i].y - 1))
							|| (sharkPicture[a].sharkPicture.isPointInElement((int) bones[i].x - 1, (int) bones[i].y))
							|| (sharkPicture[a].sharkPicture.isPointInElement((int) bones[i].x + 1, (int) bones[i].y))
							|| (sharkPicture[a].sharkPicture.isPointInElement((int) bones[i].x, (int) bones[i].y))) {
						bones[i].init();
						SharkChomp.play();
					}
				}
			}

			// if shark touches any of the bottle, bottle randomly spawn
			for (int a = 0; a < 3; a++) {

				for (int i = 0; i < MAX_WATER; i++) {
					if (sharkPicture[a].sharkPicture.isPointInElement((int) bottle[i].x, (int) bottle[i].y + 1)
							|| (sharkPicture[a].sharkPicture.isPointInElement((int) bottle[i].x, (int) bottle[i].y - 1))
							|| (sharkPicture[a].sharkPicture.isPointInElement((int) bottle[i].x - 1, (int) bottle[i].y))
							|| (sharkPicture[a].sharkPicture.isPointInElement((int) bottle[i].x + 1, (int) bottle[i].y))
							|| (sharkPicture[a].sharkPicture.isPointInElement((int) bottle[i].x, (int) bottle[i].y))) {
						bottle[i].init();
						SharkChomp.play();
					}
				}
			}

			FoodLeft.setMsg("Food Left: " + FoodBar + "%");
			WaterLeft.setMsg("Water Left: " + WaterBar + "%");
			
			// if food count = 0, game over
			if (FoodBar == 0) {
				start = DOG_DIES;
			}
			
			//if water count = 0; game over
			if (WaterBar == 0) {
				start = DOG_DIES;
			}

			EZ.refreshScreen();
		}

		// Game Over screen!
		if (start == DOG_DIES) {
			EZ.addImage("GameOver.png", 1023 / 2, 768 / 2);
			BackgroundMusic.stop();
			WaveSound.stop();
			GameOver.play();

		}

		EZ.refreshScreen();
	}

	public static void moveBackgrounds(EZImage clouds1, EZImage clouds2, EZImage ocean1, EZImage ocean2,
			EZImage WaterTexture, EZImage WaterTexture2, EZImage CloudReflection2, EZImage CloudReflection1) {
		clouds1.moveForward(-0.2);
		clouds2.moveForward(-0.2);
		ocean1.moveForward(-0.4);
		ocean2.moveForward(-0.4);
		CloudReflection1.moveForward(-0.2);
		CloudReflection2.moveForward(-0.2);
		WaterTexture.moveForward(-0.4);
		WaterTexture2.moveForward(-0.4);
	}

	public static void repeatBackgrounds(EZImage clouds1, EZImage clouds2, EZImage ocean1, EZImage ocean2,
			EZImage WaterTexture, EZImage WaterTexture2, EZImage CloudReflection1, EZImage CloudReflection2) {

		if (clouds1.getXCenter() < -500) {
			clouds1.moveForward(1023 * 2);
		}
		if (clouds2.getXCenter() < -500) {
			clouds2.moveForward(1023 * 2);
		}
		if (ocean1.getXCenter() < -511) {
			ocean1.moveForward(1023 * 2);
		}
		if (ocean2.getXCenter() < -511) {
			ocean2.moveForward(1023 * 2);
		}
		if (WaterTexture.getXCenter() < -511) {
			WaterTexture.moveForward(1023 * 2);
		}
		if (WaterTexture2.getXCenter() < -511) {
			WaterTexture2.moveForward(1023 * 2);
		}

		if (CloudReflection1.getXCenter() < -500) {
			CloudReflection1.moveForward(1023 * 2);
		}
		if (CloudReflection2.getXCenter() < -500) {
			CloudReflection2.moveForward(1023 * 2);
		}
	}

}
