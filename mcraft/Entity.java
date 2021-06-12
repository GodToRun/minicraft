package mcraft;

import java.awt.*;
import java.util.Random;

import javax.swing.*;
public class Entity extends GameObject {
	public int HP;
	public int Damage;
	public Entity(int x,int y,int width,int height,ObjectType type, JPanel panel,int hp, int damage) {
		super(x,y,width,height,type,panel);
		this.HP = hp;
		this.Damage = damage;
	}
	public Entity(int x,int y, int width, int height, ObjectType type, JPanel panel, Color color,int hp, int damage) {
		super(x,y,width,height,type,panel,color);
		this.HP = hp;
		this.Damage = damage;
	}
	@Override
	public void Loop() {
		for (GameObject OBJ : Camera.Objects) {
			if (OBJ != this && !OBJ.IsGround && this.rect.intersects(OBJ.rect) && OBJ instanceof Entity) {
				Entity entity = (Entity)OBJ;
				entity.HP =- Damage / 10;
			}
		}
	}
}
