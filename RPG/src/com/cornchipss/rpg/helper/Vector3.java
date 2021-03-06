package com.cornchipss.rpg.helper;

public class Vector3 
{
	private int x, y, z;
	
	public Vector3(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3 add(Vector3 vec)
	{
		x += vec.getX();
		y += vec.getY();
		z += vec.getZ();
		
		return this;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public Vector3 sign()
	{
		int xx, yy, zz;
		
		xx = Helper.sign(x);
		yy = Helper.sign(y);
		zz = Helper.sign(z);
		
		return new Vector3(xx, yy, zz);
	}
	
	
}
