package mcraft;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
public class WorldGenerator {
	static int X;
	static int Y;
	public static int biomecode = -1; 
	private static boolean GenerateTreeToMany = false;
	private static boolean GenerateWater = true;
	private static boolean GenerateCave = false;
	static Random ran = new Random();
	public static GameObject[][] random_arrays = new GameObject[999][50]; //청크 시스템,한 청크에 최대 50개의 블럭이 들어있음. MAX CHUNK = 999
	public static void Build(JPanel panel) { //맵을 생성함.
		int BX = -10000;
		int BY = 600;
		int i = 0;
		for (int a = 0; a < 12; a++) {
			if (biomecode == -1)
				biomecode = ran.nextInt(5); //5개의 바이옴 중에 하나를 뽑음.
			if (ran.nextInt(3) == 2)
				GenerateCave = true;
			for (int i2 = 0; i2 < 50 + 1;i2++) {  //50개의 작은 청크를 생성하면서, 바이옴을 정함.
				i++;
				//if (ran.nextInt(800) == 2 && BX < 35000) {
				//}
				int ranint = ran.nextInt(5);
				if (biomecode < 1) { //Default Forest
					BuildForest(panel,i, BX, BY, Color.GREEN,true);
					if (ran.nextBoolean())
						BY -= 50;
					else
						BY += 50;
				}
				else if (biomecode == 1) { //산
					BuildForest(panel, i, BX, BY, Color.GREEN,true);
					if (ran.nextBoolean())
						BY += 50;
					else
						BY -= 100;
				}
				else if (biomecode == 2) { // Desert (사막)
					GenerateWater = false;
					BuildForest(panel,i, BX, BY, new Color(255,213,0),false);
					GenerateWater = true;
					if (ran.nextBoolean())
						BY -= 50;
					else
						BY += 50;
				}
				else if (biomecode == 3) { //늪
					BuildForest(panel,i, BX, BY, new Color(136,233,39),true);
					if (ran.nextBoolean())
						BY -= 50;
					else
						BY += 50;
				}
				else if (biomecode == 4) { //Ocean
					random_arrays[i][1] = new GameObject(BX,BY - 200,300,1000,ObjectType.Cube,panel,new Color(0,0,255,100));
					random_arrays[i][1].IsGround = true;
					random_arrays[i][1].Y_FIXING = true;
					random_arrays[i][1].collisionable = false;
					random_arrays[i][1].tag = "Chunk_Water";
					BuildForest(panel,i, BX, BY + 100, new Color(0,255,0,100),false);
					if (ran.nextBoolean())
						BY -= 50;
					else
						BY += 50;
				}
				
				BX += 200;
				}
			GenerateCave = false;
			biomecode = ran.nextInt(5);
				
			}
			BY += 50 * 51;
			BX += 200 * 51;
		}
	private static void BuildTree(JPanel panel, int i,int BX,int BY) {
		random_arrays[i][2] = new GameObject(BX + 25,BY - 300,50,300,ObjectType.Cube,panel,new Color(144,35,2));
		random_arrays[i][2].IsGround = true;
		random_arrays[i][2].Y_FIXING = true;
		random_arrays[i][2].collisionable = false;
		random_arrays[i][2].tag = "Chunk_Tree";
		random_arrays[i][3] = new GameObject(BX,BY - 500,100,200,ObjectType.Cube,panel,Color.GREEN);
		random_arrays[i][3].IsGround = true;
		random_arrays[i][3].Y_FIXING = true;
		random_arrays[i][3].collisionable = false;
		random_arrays[i][3].tag = "Chunk_Leaves";
	}
	private static void BuildForest(JPanel panel, int i,int BX,int BY,Color color,boolean generatetree) {
		//[log]
		System.out.println("Block Successfuly generated on " + BX + ", " + BY);
		int ranint = ran.nextInt(5);
			if (ranint < 4) { //Ground
				random_arrays[i][0] = new GameObject(BX,BY,200,1000,ObjectType.Cube,panel,color);
				random_arrays[i][0].IsGround = true;
				random_arrays[i][0].Y_FIXING = true;
				random_arrays[i][0].tag = "Chunk_Ground";
				if (GenerateTreeToMany && generatetree) {
					if (ran.nextBoolean())
						BuildTree(panel,i,BX,BY);
				}
				else if (!GenerateTreeToMany && generatetree) {
					if (ran.nextInt(3) == 2)
						BuildTree(panel,i,BX,BY);
				}
			}
			else if  (ranint == 4 && GenerateWater) { //Water
				random_arrays[i][0] = new GameObject(BX,BY + 300,200,1000,ObjectType.Cube,panel,color);
				random_arrays[i][0].IsGround = true;
				random_arrays[i][0].Y_FIXING = true;
				random_arrays[i][0].collisionable = true;
				random_arrays[i][0].tag = "Chunk_Ground";
				random_arrays[i][1] = new GameObject(BX,BY,200,300,ObjectType.Cube,panel,new Color(0,0,255,100));
				random_arrays[i][1].IsGround = true;
				random_arrays[i][1].Y_FIXING = true;
				random_arrays[i][1].collisionable = false;
				random_arrays[i][1].tag = "Chunk_Water";
			}
			else if (ranint == 4) {
				random_arrays[i][0] = new GameObject(BX,BY,200,100,ObjectType.Cube,panel,color);
				random_arrays[i][0].IsGround = true;
				random_arrays[i][0].Y_FIXING = true;
				random_arrays[i][0].tag = "Chunk_Ground";
			}
				if (GenerateCave) {
					random_arrays[i][4] = new GameObject(BX,BY + 1000,200,300,ObjectType.Cube,panel,Color.GRAY);
					random_arrays[i][4].IsGround = true;
					random_arrays[i][4].Y_FIXING = true;
					random_arrays[i][4].tag = "Chunk_Stone";
					if (ran.nextInt(5) == 4) {
						random_arrays[i][7] = new GameObject(BX,BY + 1650,200,800,ObjectType.Cube,panel,new Color(255,0,0,150));
						random_arrays[i][7].IsGround = true;
						random_arrays[i][7].Y_FIXING = true;
						random_arrays[i][7].collisionable = false;
						random_arrays[i][7].tag = "Chunk_Lava";
					}
					else {
						random_arrays[i][6] = new GameObject(BX,BY + 1650,200,800,ObjectType.Cube,panel,Color.GRAY);
						random_arrays[i][6].IsGround = true;
						random_arrays[i][6].Y_FIXING = true;
						random_arrays[i][6].tag = "Chunk_Stone";
					}
				}
				else {
					random_arrays[i][4] = new GameObject(BX,BY + 900,200,1000,ObjectType.Cube,panel,Color.GRAY);
					random_arrays[i][4].IsGround = true;
					random_arrays[i][4].Y_FIXING = true;
					random_arrays[i][4].tag = "Chunk_Stone";
				}
			
	}
	public static void ShowBuilded(Graphics g) { //생성한 맵의 그래픽을 불러옴.
		for (GameObject[] obj : random_arrays) {
			if (obj != null) {
				for (GameObject OBJIN : obj)
					if (OBJIN != null)
						OBJIN.Show(g);
			}
		}
	}
}
