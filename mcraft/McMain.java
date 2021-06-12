package mcraft;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

import javax.swing.*;

import org.lwjgl.vulkan.VK;
public class McMain extends JFrame {
	public static setGUI gui;
static class setGUI extends JFrame implements KeyListener {
	public Entity character;
	JUMP JUMP_;
	GameObject easter_text,easter_text2,easter_text3;
	JPanel panel = new DefaultPanel();
        // �����ڸ� ���� GUI �ʱ� ���� ��
	public boolean INPUT_D = false;
	public boolean INPUT_A = false;
	public final static double Version = 0.1;
	public int XP = 0;
	Inventory inv;
	Slot slot1,slot2,slot3;
	public short time = 8000;
	public short DAY = 0;
	public final static String CREATIVE = "creative";
	public final static String SURVIVAL = "survival";
	public String GAMEMODE = SURVIVAL;
	public int SCORE = 0;
	public int LEVEL = 1;
	private boolean overed = false;
        setGUI(){
        	System.out.println("ENVIRONMENT SETTING\n");
        	System.out.print("BIOME (swamp,forest,desert,ocean,mountain,random): ");
        	String value = new Scanner(System.in).nextLine();
        	if (value.toLowerCase().equals("swamp")) {
        		WorldGenerator.biomecode = 3;
        	}
        	else if (value.toLowerCase().equals("desert")) {
        		WorldGenerator.biomecode = 2;
        	}
        	else if (value.toLowerCase().equals("ocean")) {
        		WorldGenerator.biomecode = 4;
        	}
        	else if (value.toLowerCase().equals("forest")) {
        		WorldGenerator.biomecode = 0;
        	}
        	else if (value.toLowerCase().equals("mountain")) {
        		WorldGenerator.biomecode = 1;
        	}
        	else if (value.toLowerCase().equals("random")) {
        		WorldGenerator.biomecode = -1;
        	}
        	else {
        		System.out.println("invaild biome name");
        		return;
        	}
        	System.out.println(); //�� �� ���
        	System.out.print("GAME MODE (survival,creative): ");
        	String mode = new Scanner(System.in).nextLine();
        	if ("survival".equals(mode.toLowerCase())) {
        		GAMEMODE = SURVIVAL;
        	}
        	else if ("creative".equals(mode.toLowerCase())) {
        		GAMEMODE = CREATIVE;
        	}
        	else {
        		System.out.println("invaild mode name");
        		return;
        	}
            // ������ ����(Title)�� ������. (�߿�)
            setTitle("Minicraft");
            System.out.println("Initializing Variables..");
            inv = new Inventory();
            slot1 = new Slot();
            slot2 = new Slot();
            slot3 = new Slot();
            character = new Entity(900,300,50,50,ObjectType.Cube,panel,100,10);
            if (GAMEMODE.equals(CREATIVE))
            	character.HP = 2147483647;
            easter_text = new GameObject(110500,500,50,50,ObjectType.Text,panel);
            easter_text2 = new GameObject(110500,600,50,50,ObjectType.Text,panel);
            easter_text3 = new GameObject(110500,650,50,50,ObjectType.Text,panel);
            easter_text.Y_FIXING = true;
            easter_text2.Y_FIXING = true;
            easter_text3.Y_FIXING = true;
            inv.setCurrentSlot(slot1);          
            //box.IsGround = true;
            GameObject.SetAllObjectsDefaultSetting();
            //character.X_FIXING = true;
            System.out.println("SUCCESS");
            panel.setBackground(Color.CYAN);
            this.add(panel, BorderLayout.CENTER);
            this.addKeyListener(this);
            System.out.println("Generating Map Chunks..");
            WorldGenerator.Build(panel);
            System.out.println("SUCCESS");
            // ���� ��ư ����, ���� �ɼ� ����
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // ������ â ũ�� ����(����, ����)
            setSize(1920, 1080);
            System.out.println("Get Ready!");
            easter_text.setText("Minicraft With Java");
            easter_text2.setText("With Using swing,awt API");
            easter_text3.setText("This is End of the world");
            // �� �޼ҵ带 �̿��ؾ� ������ â�� ��Ÿ����. <�߿�>!!
            setVisible(true);
            ASYNC as = new ASYNC(panel); //�񵿱� Ŭ���� (���ΰ�ħ Ŭ����) extends, Thread
            as.start();
        }
        @Override
    	public void keyPressed(KeyEvent e) { //Ű���� �̺�Ʈ (�������� ���)
    			if (e.getKeyChar() == 'a') {
    				INPUT_A = true;
    				
    			}
    			else if (e.getKeyChar() == 'd') {
    				INPUT_D = true;
    				
    			}	
    			else if (e.getKeyCode() == 0x1b) //VK_ESCAPE ����
    				System.exit(0); //��õ���� ������ �� ��.
    	}
    	@Override
    	public void keyReleased(KeyEvent e) {
    		if (e.getKeyChar() == 'a') {
    			INPUT_A = false;
    		}
    		else if (e.getKeyChar() == 'd') {
    			INPUT_D = false;
    		}
    		else if (e.getKeyCode() == 0x26) { //VK_UP
    			for (GameObject obj : Camera.Objects) {
    				obj.Move(0, 50, true);
    			}
    		}
    		else if (e.getKeyCode() == e.VK_1) {
    			inv.setCurrentSlot(slot1);
    		}
    		else if (e.getKeyCode() == e.VK_2) {
    			inv.setCurrentSlot(slot2);
    		}
    		else if (e.getKeyCode() == e.VK_3) {
    			inv.setCurrentSlot(slot3);
    		}
    		else if (e.getKeyCode() == 0x28) { //VK_DOWN
    			for (GameObject obj : Camera.Objects) {
    				obj.Move(0, -50, true);
    			}
    		}
    		else if (e.getKeyCode() == 0x45)  {//VK_E
    			String value = JOptionPane.showInputDialog("What are you going to make?");
    			if (value.equals("tnt")) {
    				for (Slot slot : Inventory.slots) {
    					if (slot.getItem() == Inventory.SAND && slot.itemCount > 19) {
    						slot.SubstractCount(20);
    						SetAutoItem("TNT");
    					}
    				}
    			}
    		}
    		else if (e.getKeyCode() == 0x51) { //VK_Q ���
    			if (inv.getCurrentSlot().getItem().equals("TNT")) {
    				
    			}
    		}
    		else if (e.getKeyCode() == 0x11) { //VK_CONTROL ����
    			for (GameObject GO : Camera.Objects)
    				if (GO.rect.intersects(character.rect) && GO != character) {
    					for (Slot s : Inventory.slots) { //Inventory System
    						if (s.getItem() == null || s.getItem() == Inventory.GRASS) {
    							if (GO.tag.equals("Chunk_Ground") && GO.color == Color.GREEN) {
    								s.setItem(Inventory.GRASS, s.itemCount + 1);
    								break;
    							}
    							else if (GO.tag.equals("Chunk_Ground") && GO.color == new Color(136,233,39)) {
    								s.setItem(Inventory.GRASS, s.itemCount + 1);
    								break;
    							}
    						}
    						if (s.getItem() == null || s.getItem() == Inventory.WATER) {
    							if (GO.tag.equals("Chunk_Water")) {
    								s.setItem(Inventory.WATER, s.itemCount + 1);
    								break;
    							}
    						}
    						if (s.getItem() == null || s.getItem() == Inventory.SAND) {
    							if (GO.tag.equals("Chunk_Ground") && GO.color.getRGB() == new Color(255,213,0).getRGB()) {
    								s.setItem(Inventory.SAND, s.itemCount + 1);
    								break;
    							}
    						}
    						if (s.getItem() == null || s.getItem() == Inventory.STONE) {
    							if (GO.tag.equals("Chunk_Stone")) {
    								s.setItem(Inventory.STONE, s.itemCount + 1);
    								break;
    							}
    						}
    						if (s.getItem() == null || s.getItem() == Inventory.WOOD) {
    							if (GO.tag.equals("Chunk_Tree")) {
    								s.setItem(Inventory.WOOD, s.itemCount + 1);
    								break;
    							}
    						}
    					}
    					GO.Delete();
    				}
    		}
    		else if (e.getKeyCode() == 0xa) { //VK_ENTER ��ġ
    			if (inv.getCurrentSlot().getItem() != null) {
    				switch (inv.getCurrentSlot().getItem()) {
    				case Inventory.GRASS:
    					GameObject grass = new GameObject(character.X,character.Y,100,200,ObjectType.Cube,panel,Color.GREEN);
    					grass.tag = "Chunk_Ground";
    					grass.IsGround = true;
    					grass.ShowWhenPaint = true;
    					inv.getCurrentSlot().SubstractCount(1);
    					break;
    				case Inventory.STONE:
    					GameObject stone = new GameObject(character.X,character.Y,100,200,ObjectType.Cube,panel,Color.GRAY);
    					stone.tag = "Chunk_Stone";
    					stone.IsGround = true;
    					stone.ShowWhenPaint = true;
    					inv.getCurrentSlot().SubstractCount(1);
    					break;
    				case Inventory.WATER:
    					GameObject water = new GameObject(character.X,character.Y,100,200,ObjectType.Cube,panel,new Color(0,0,255,100));
    					water.tag = "Chunk_Water";
    					water.IsGround = true;
    					water.collisionable = false;
    					water.ShowWhenPaint = true;
    					inv.getCurrentSlot().SubstractCount(1);
    					break;
    				case Inventory.SAND:
    					GameObject sand = new GameObject(character.X,character.Y,100,200,ObjectType.Cube,panel,new Color(255,213,0));
    					sand.tag = "Chunk_Sand";
    					sand.IsGround = true;
    					sand.ShowWhenPaint = true;
    					inv.getCurrentSlot().SubstractCount(1);
    					break;
    				case Inventory.WOOD:
    					GameObject wood = new GameObject(character.X,character.Y,50,300,ObjectType.Cube,panel,new Color(144,35,2));
    					wood.tag = "Chunk_Tree";
    					wood.IsGround = true;
    					wood.ShowWhenPaint = true;
    					inv.getCurrentSlot().SubstractCount(1);
    					break;
    				}
    				}
    				
    			}
    		else if (e.getKeyCode() == 0x20 && (character.inGround || character.CheckCollision().tag.equals("Chunk_Water") || GAMEMODE.equals(CREATIVE))) { //VK_SPACE
				JUMP_ = new JUMP(panel, character); //�񵿱� ����
				JUMP_.start();
    		}
    		
		}
    	
    	public void CheckCollision(JPanel PAN) { //�浹 üũ
    		for (GameObject GROUND : Camera.Objects) {
    			for (GameObject OBJECT : Camera.Objects) {
    				if (OBJECT != GROUND && OBJECT.rect.intersects(GROUND.rect) && GROUND.collisionable && OBJECT.collisionable) { //�浹�� Ȯ���ϴ� �޼ҵ�.
        				OBJECT.inGround = true;
        			}
        			else if (!GROUND.IsGround && OBJECT != GROUND && !OBJECT.IsGround) {
        				OBJECT.inGround = false;
        			}
    			}
    			
    		}
    	}
    	//�� �����ؾ� �Ѵٰ� �ؼ� ������
    	@Override
    	public void keyTyped(KeyEvent e) {
    		// TODO Auto Generated Method Stub
    		
    	}
    	public void SetAutoItem(String S) { //�ڵ����� �� ĭ�� ���� �� ����.
    		for (Slot slot : Inventory.slots) {
    			if (slot.getItem().equals(S) || slot.getItem() == null)
    				slot.setItem(S, slot.itemCount + 1);
    		}
    	}
    	public void GameOver(JPanel panel) { //���� ����
    		if (!overed) {
    			overed = true;
    			JOptionPane.showMessageDialog(null, "����� ������ �׾����ϴ�. \n����� ����: " + character.TEMP_X); //TEMP_X = ���ھ�� ���� (��� ���⼱)
    		}
    			//System.out.println("���� ���� �Ǽ̽��ϴ�."); 
    		try { Thread.sleep(5000); } catch(Exception e) { } //Ŭ���� ���װ� �־ 5�� �ڿ� �ڵ����� ������ �ɷ� ��. 
    		System.exit(0);
    		
    	}
    	public void GameOver(JPanel panel,String msg) { //���� ����
    		if (!overed) {
    			overed = true;
    			JOptionPane.showMessageDialog(null, msg + "\n����� ����: " + character.TEMP_X); //TEMP_X = ���ھ�� ���� (��� ���⼱)
    		}
    			//System.out.println("���� ���� �Ǽ̽��ϴ�."); 
    		try { Thread.sleep(5000); } catch(Exception e) { } //Ŭ���� ���װ� �־ 5�� �ڿ� �ڵ����� ������ �ɷ� ��. 
    		System.exit(0);
    		
    	}
        class DefaultPanel extends JPanel {
        	@Override
        	public void paintComponent(Graphics g){
                super.paintComponent(g);
                if (time >= 32767) {
    				DAY++;
    				time = 0;
    			}
    			else
    				time++;
                if (time < 8000) { //AM 6��~PM 12��
                	panel.setBackground(Color.ORANGE);
                	g.setColor(Color.yellow);
                	g.fillRect(1500, 400, 100, 100);
                }
                else if (time > 7999 && time < 15000) {//PM 12��~PM 6��	
                	panel.setBackground(Color.CYAN);
                	g.setColor(Color.yellow);
                	g.fillRect(1500, 100, 100, 100);
                }
                else if (time > 14999 && time < 25000) {//PM 6��~AM12��
                	panel.setBackground(Color.ORANGE); 
                	g.setColor(Color.WHITE);
                	g.fillRect(1500, 100, 100, 100);
                }
                else if (time > 24999) {//AM 12��~AM 6��
                	panel.setBackground(Color.BLACK);
                	g.setColor(Color.WHITE);
                	g.fillRect(1500, 100, 100, 100);
                }
                g.setColor(Color.BLACK);
                g.drawString("X " + character.TEMP_X, 20, 20);
                g.drawString("Y " + character.TEMP_Y, 20, 60);
                g.drawString("HP: " + character.HP, 20, 100);
                g.drawString("Current Slot: " + inv.getCurrentSlot().getItem(), 20, 140);
                g.drawString("Current Slot Count: " + inv.getCurrentSlot().itemCount, 20, 180);
                character.Show(g);
                GameObject.ObjectsShow(g);
                character.TEMP_Y = character.Y;
                WorldGenerator.ShowBuilded(g);
                CheckCollision(panel);
                if (INPUT_A) {
                	character.TEMP_X -= 10;
                	character.Move(-10, 0);
    				Camera.Move(10, 0); //ī�޶� �̵�
                }
                else if (INPUT_D) {
                	character.TEMP_X += 10;
                	character.Move(+10, 0);
    				Camera.Move(-10, 0);
                }
                g.setColor(Color.BLACK);
                easter_text.Show(g);
                easter_text2.Show(g);
                easter_text3.Show(g);
            }
        	
        }
        class ASYNC extends Thread {
        	private JPanel g;
        	public GameObject[] objs;
        	public JUMP jump;
        	public ASYNC(JPanel G) {
        		this.g = G;
        	}
        	@Override
        	public void run() {
        		while(true) { //�񵿱� ���ѷ���
        			g.repaint();
        			for (GameObject Os : Camera.Objects) {
        				if (!Os.inGround && !Os.IsGround && !Os.equals(character)) {
        					Os.Move(0, 7);
        				}
        				else if (!Os.inGround && !Os.IsGround && Os == character && JUMP_ != null && JUMP_.ended)
        					Os.Move(0, 7);
        				else if (!Os.inGround && !Os.IsGround && Os == character && JUMP_ == null)
        					Os.Move(0, 7);
        				if (Os != character && Os.tag.equals("Chunk_Lava") && character.rect.intersects(Os.rect)) {
        					character.HP--;
        				}
        			}
        			if (character.HP < 2 && !GAMEMODE.equals(CREATIVE))
        				GameOver(panel,"ü���� �ٴ�� �׾����ϴ�.");
        			if (character.Y > 1000 && !GAMEMODE.equals(CREATIVE))
        				GameOver(panel);
        			try { sleep(20); } catch(InterruptedException e) { } catch (Exception e) { e.printStackTrace();}
        		}
        	}
        }
    }
public static void main(String[] args) {
	gui = new setGUI();
}

static class JUMP extends Thread {
	private JPanel g;
	GameObject[] objs;
	public boolean ended = false;
	private GameObject CHAR;
	public JUMP(JPanel g,GameObject character) {
		this.g = g;
		this.CHAR = character;
	}
	@Override
	public void run() {
		int COOL = 0;
		int JUMP_Y = -2;

		while(true) {
			if (COOL < 11) {
				this.CHAR.Move(0, (int)(JUMP_Y * 1.4f));
				try {
					COOL++;
					JUMP_Y--;
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (COOL > 10 && COOL < 15) {
				JUMP_Y++;
				this.CHAR.Move(0, JUMP_Y);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				COOL++;
				JUMP_Y++;
			}/*
			else if (COOL > 36) {
				this.CHAR.Move(0, 1);*/
			else {
				ended = true;
				break;
			}	
			}
		
		}
	
	}

}
