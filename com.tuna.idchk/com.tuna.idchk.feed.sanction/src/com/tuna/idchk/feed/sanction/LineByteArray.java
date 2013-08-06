package com.tuna.idchk.feed.sanction;

public class LineByteArray {
	public byte[] lineBytes;
	public int counter = 0;
	
	public LineByteArray(int initialLength){
		lineBytes = new byte[initialLength];
	}
	
	public void add(byte toAdd){
		if(counter == lineBytes.length){
			byte[] tmp = new byte[lineBytes.length*2];
			for(int i=0;i<lineBytes.length;i++){
				tmp[i] = lineBytes[i];
			}
			lineBytes = tmp;
		}
		
		lineBytes[counter] = toAdd;
		counter++;
	}
	
	public void reset(int initialLength){
		counter= 0;
		lineBytes = new byte[initialLength];
	}
}
