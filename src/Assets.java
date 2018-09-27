import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {

	private static final int width = 16, height = 16;
	
	//Stand-alone
	public static BufferedImage dirt, grass, stone, tree, rock, bush, wood, coin, chestClosed, chestOpen, displayBoxRed, speechBubble;
	//Player walk
	public static BufferedImage[] player_down, player_up, player_right, player_left, player_idle;
	//Player attack
	public static BufferedImage[] player_attack_down, player_attack_up, player_attack_right, player_attack_left;
	//Archer Female
	public static BufferedImage[] archF_down, archF_up, archF_right, archF_left, archF_idle;
	public static BufferedImage[] mageM_down, mageM_up, mageM_right, mageM_left, mageM_idle;
	public static BufferedImage[] player_attack_downB, player_attack_rightB,  player_attack_upB,  player_attack_leftB;
	//Start Menu UI
	public static BufferedImage[] btn_start, btn_blank, btn_inc_pos;
	//Inventory UI
	public static BufferedImage inventoryScreen, itemHighlighter, nothing, heartFull, heartHalf, heartFourth, heartThreeFourths, heartEmpty;
	public static Font font28, font28nBold, fontPlaceHolder;
	public static BufferedImage parchment, tlCorner, trCorner, blCorner, brCorner, vertLine, horzLine;
	public static BufferedImage swordStarter, test, chestPlate, swordBlue, swordRed, swordGreen, swordBlack;
	public static BufferedImage tabHighlightSword, tabHighlightShield, tabHighlightPotion, tabHighlightQuest;
	
	
	public static void init() {
		
		//149, 0, 171, 16
		
		
		
		//Load Sprite Sheets
		SpriteSheet sheetWorld = new SpriteSheet(ImageLoader.loadImage("/textures/Overworld.png"));
		SpriteSheet sheetCharacter = new SpriteSheet(ImageLoader.loadImage("/textures/character.png"));
		SpriteSheet overworldSpecial = new SpriteSheet(ImageLoader.loadImage("/textures/overworldSpecial.png"));
		SpriteSheet cave = new SpriteSheet(ImageLoader.loadImage("/textures/cave.png"));
		SpriteSheet menuUI = new SpriteSheet(ImageLoader.loadImage("/textures/uiMenu.png"));
		SpriteSheet invUI = new SpriteSheet(ImageLoader.loadImage("/textures/invMenu.png"));
		SpriteSheet objectSheet = new SpriteSheet(ImageLoader.loadImage("/textures/objects.png"));
		SpriteSheet parchmentSheet = new SpriteSheet(ImageLoader.loadImage("/textures/parchment.png"));
		SpriteSheet weaponsSheet = new SpriteSheet(ImageLoader.loadImage("/textures/weapons.png"));
		SpriteSheet sw = new SpriteSheet(ImageLoader.loadImage("/textures/sword.png"));
		SpriteSheet overlayItems = new SpriteSheet(ImageLoader.loadImage("/textures/objects.png"));
		SpriteSheet blueSwordSwing = new SpriteSheet(ImageLoader.loadImage("/textures/swordSwingBlue.png"));
		SpriteSheet rangerFemale = new SpriteSheet(ImageLoader.loadImage("/textures/ranger_f.png"));
		SpriteSheet mageMale = new SpriteSheet(ImageLoader.loadImage("/textures/mage_m.png"));
		
		//Speech Bubble
		speechBubble = objectSheet.crop(309, 96, 52, 47);
		
		//Chest
		chestClosed = objectSheet.crop(149, 0, 22, 17);
		chestOpen = objectSheet.crop(229, 23, 22, 17);
		
		//Display text box
		displayBoxRed = invUI.crop(6, 13, 76, 37);
		
		//Highlighters for tabs
		tabHighlightSword = invUI.crop(144, 138, 13, 17);
		tabHighlightShield = invUI.crop(144, 156, 13, 17);
		tabHighlightPotion = invUI.crop(144, 174, 13, 17);
		tabHighlightQuest = invUI.crop(144, 192, 13, 17);
		
		//Font
		font28 = FontLoader.highTower;
		font28nBold = FontLoader.highTowernBold;
		fontPlaceHolder = new Font("Times New Roman", Font.BOLD, 28);
		
		//Mage Male
		mageM_up = new BufferedImage[3];
		mageM_down = new BufferedImage[3];
		mageM_left = new BufferedImage[3];
		mageM_right = new BufferedImage[3];
		mageM_idle = new BufferedImage[4];
		
		mageM_up[0] = mageMale.crop(2, 2, 27, 33);
		mageM_up[1] = mageMale.crop(34, 0, 27, 33);
		mageM_up[2] = mageMale.crop(68, 2, 27, 33);
		
		mageM_right[0] = mageMale.crop(2, 38, 27, 33);
		mageM_right[1] = mageMale.crop(34, 36, 27, 33);
		mageM_right[2] = mageMale.crop(68, 38, 27, 33);
		
		mageM_down[0] = mageMale.crop(2, 74, 27, 33);
		mageM_down[1] = mageMale.crop(34, 72, 27, 33);
		mageM_down[2] = mageMale.crop(68, 74, 27, 33);
		
		mageM_left[0] = mageMale.crop(2, 110, 27, 33);
		mageM_left[1] = mageMale.crop(34, 108, 27, 33);
		mageM_left[2] = mageMale.crop(68, 110, 27, 33);
		
		mageM_idle[2] = mageMale.crop(34, 0, 27, 33);
		mageM_idle[0] = mageMale.crop(34, 36, 27, 33);
		mageM_idle[3] = mageMale.crop(34, 72, 27, 33);
		mageM_idle[1] = mageMale.crop(34, 108, 27, 33);
		
		//Ranger Female
		archF_up = new BufferedImage[3];
		archF_down = new BufferedImage[3];
		archF_left = new BufferedImage[3];
		archF_right = new BufferedImage[3];
		archF_idle = new BufferedImage[4];
		
		archF_up[0] = rangerFemale.crop(2, 2, 27, 33);
		archF_up[1] = rangerFemale.crop(34, 0, 27, 33);
		archF_up[2] = rangerFemale.crop(68, 2, 27, 33);
		
		archF_right[0] = rangerFemale.crop(2, 38, 27, 33);
		archF_right[1] = rangerFemale.crop(34, 36, 27, 33);
		archF_right[2] = rangerFemale.crop(68, 38, 27, 33);
		
		archF_down[0] = rangerFemale.crop(2, 74, 27, 33);
		archF_down[1] = rangerFemale.crop(34, 72, 27, 33);
		archF_down[2] = rangerFemale.crop(68, 74, 27, 33);
		
		archF_left[0] = rangerFemale.crop(2, 110, 27, 33);
		archF_left[1] = rangerFemale.crop(34, 108, 27, 33);
		archF_left[2] = rangerFemale.crop(68, 110, 27, 33);
		
		archF_idle[2] = rangerFemale.crop(34, 0, 27, 33);
		archF_idle[0] = rangerFemale.crop(34, 36, 27, 33);
		archF_idle[3] = rangerFemale.crop(34, 72, 27, 33);
		archF_idle[1] = rangerFemale.crop(34, 108, 27, 33);
		
		//Overlay Images
		heartFull = overlayItems.crop(65, 3, 12, 11);
		heartThreeFourths = overlayItems.crop(81, 3, 12, 11);
		heartHalf = overlayItems.crop(97, 3, 12, 11);
		heartFourth = overlayItems.crop(113, 3, 11, 11);
		heartEmpty = overlayItems.crop(129, 3, 11, 11);
		
		//Weapons
		swordStarter = sw.crop(65, 0, width, height);
		swordBlue = sw.crop(0, 0, width, height);
		swordRed = sw.crop(17, 0, width, height);
		swordGreen = sw.crop(33, 0, width, height);
		swordBlack = sw.crop(49, 0, width, height);
		test = weaponsSheet.crop(1, 1, 48, 48);
		chestPlate = sheetCharacter.crop(100, 50, width, height);
		
		//Parchment
		parchment = parchmentSheet.crop(0, 0, 512, 512);
		
		//Inventory Screen
		inventoryScreen = invUI.crop(5, 99, 119, 172);	
		itemHighlighter = invUI.crop(127, 141, 14, 14);
		nothing = cave.crop(200, 200, 2, 2);
		//Introduction Menu Assets
			
		//Arrays
		btn_start = new BufferedImage[2];
		btn_blank = new BufferedImage[2];
		btn_inc_pos = new BufferedImage[2];
		
		//Blank Button
		btn_blank[0] = menuUI.crop(16, 126, 278, 54);
		btn_blank[1] = menuUI.crop(16, 203, 278, 54);
		
		//Vertical incremental buttons
		btn_inc_pos[0] = menuUI.crop(461, 18, 68, 30);
		btn_inc_pos[1] = menuUI.crop(461, 71, 68, 30);
		
		//433, 320, 14, 22
		
		//Player Movement Animations
		
		//Arrays
		player_down = new BufferedImage[2];
		player_up = new BufferedImage[2];
		player_left = new BufferedImage[3];
		player_right = new BufferedImage[3];
		player_idle = new BufferedImage[4];
		player_attack_down = new BufferedImage[4];
		player_attack_up = new BufferedImage[4];
		player_attack_left = new BufferedImage[4];
		player_attack_right = new BufferedImage[4];
		
		player_attack_downB = new BufferedImage[4];
		player_attack_leftB = new BufferedImage[4];
		player_attack_rightB = new BufferedImage[4];
		player_attack_upB = new BufferedImage[4];
		
		//Walking
		player_down[0] = sheetCharacter.crop(17, 7, 15, 20);
		player_down[1] = sheetCharacter.crop(49, 7, 15, 20);
		player_up[0] = sheetCharacter.crop(16, 70, 15, 21);
		player_up[1] = sheetCharacter.crop(48, 70, 15, 21);
		player_right[0] = sheetCharacter.crop(18, 39, 15, 21);
		player_right[1] = sheetCharacter.crop(34, 38, 15, 21);
		player_right[2] = sheetCharacter.crop(50, 39, 15, 21);
		player_left[0] = sheetCharacter.crop(17, 103, 15, 21);
		player_left[1] = sheetCharacter.crop(33, 102, 15, 21);
		player_left[2] = sheetCharacter.crop(49, 103, 15, 21);
		//Idle
		player_idle[0] = sheetCharacter.crop(33, 6, 15, 21);
		player_idle[1] = sheetCharacter.crop(32, 69, 15, 22);
		player_idle[2] = sheetCharacter.crop(34, 38, 15, 21);
		player_idle[3] = sheetCharacter.crop(33, 102, 15, 21);
		//Swing Sword
		player_attack_down[0] = sheetCharacter.crop(7, 134, 16, 26);
		player_attack_down[1] = sheetCharacter.crop(41, 134, 16, 26);
		player_attack_down[2] = sheetCharacter.crop(72, 134, 16, 26);
		player_attack_down[3] = sheetCharacter.crop(104, 134, 16, 26);		
		player_attack_up[0] = sheetCharacter.crop(7, 166, 16, 26);
		player_attack_up[1] = sheetCharacter.crop(41, 166, 16, 26);
		player_attack_up[2] = sheetCharacter.crop(72, 166, 16, 26);
		player_attack_up[3] = sheetCharacter.crop(104, 166, 16, 26);	
		player_attack_right[0] = sheetCharacter.crop(8, 199, 20, 26);
		player_attack_right[1] = sheetCharacter.crop(41, 199, 20, 26);
		player_attack_right[2] = sheetCharacter.crop(73, 199, 20, 26);
		player_attack_right[3] = sheetCharacter.crop(105, 199, 20, 26);
		player_attack_left[0] = sheetCharacter.crop(8, 230, 20, 26);
		player_attack_left[1] = sheetCharacter.crop(33, 230, 20, 26);
		player_attack_left[2] = sheetCharacter.crop(68, 230, 20, 26);
		player_attack_left[3] = sheetCharacter.crop(101, 230, 20, 26);	
		
		player_attack_downB[0] = blueSwordSwing.crop(7, 134, 16, 26);
		player_attack_downB[1] = blueSwordSwing.crop(41, 134, 16, 26);
		player_attack_downB[2] = blueSwordSwing.crop(72, 134, 16, 26);
		player_attack_downB[3] = blueSwordSwing.crop(104, 134, 16, 26);		
		player_attack_upB[0] = blueSwordSwing.crop(7, 166, 16, 26);
		player_attack_upB[1] = blueSwordSwing.crop(41, 166, 16, 26);
		player_attack_upB[2] = blueSwordSwing.crop(72, 166, 16, 26);
		player_attack_upB[3] = blueSwordSwing.crop(104, 166, 16, 26);	
		player_attack_rightB[0] = blueSwordSwing.crop(8, 199, 20, 26);
		player_attack_rightB[1] = blueSwordSwing.crop(41, 199, 20, 26);
		player_attack_rightB[2] = blueSwordSwing.crop(73, 199, 20, 26);
		player_attack_rightB[3] = blueSwordSwing.crop(105, 199, 20, 26);
		player_attack_leftB[0] = blueSwordSwing.crop(8, 230, 20, 26);
		player_attack_leftB[1] = blueSwordSwing.crop(33, 230, 20, 26);
		player_attack_leftB[2] = blueSwordSwing.crop(68, 230, 20, 26);
		player_attack_leftB[3] = blueSwordSwing.crop(101, 230, 20, 26);
		
		
		//Tiles
		grass = sheetWorld.crop(0, 0, width, height);
		dirt = sheetWorld.crop(32, 512, width, height);
		stone = cave.crop(112, 48, 15, 15);
		
		//Stand alone Entities (Probably will re-do)
		rock = overworldSpecial.crop(0, 0, width, height);
		bush = cave.crop(173, 39, 15, 14);
		tree = sheetWorld.crop(512, 18, 15, 30);
		
		//Items
		wood = sheetWorld.crop(433, 320, 14, 22);
		coin = objectSheet.crop(2, 66, 10, 10);
	}

}
