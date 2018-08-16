package mymain;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class  Item {
	
	
	//위치  + 크기
	public Rectangle pos = new Rectangle();

	
	//행위 속성
	public abstract boolean move();
	public abstract void draw(Graphics g);
}
