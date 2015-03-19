package com.hz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Outputer {

	private String Path;
	private StringBuffer sb;
	private int lineNr = 0;

	public Outputer(String path, boolean deleteF)
	{
		init(path);
		if(deleteF) deleteFile();
	}
	public Outputer(String path){
		init(path);
		deleteFile();
	}
	
	
	private void init(String path) {
		Path = path;
		sb = new StringBuffer();
		File file=new File(path);
		if(!file.exists()){
			try {
				if(file.createNewFile() == false)
					System.out.println("path not exist: " + path);
				path = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void append(String oneLineWithEnter){
		sb.append(oneLineWithEnter);
		lineNr ++;
		if(lineNr % 1000 == 0){
			add2File();
			sb.setLength(0);
		}
	}
	
	public void close(){
		add2File();
	}
	
	public int recordNr(){
		return lineNr;
	}
	
	public void deleteFile(){
		uFunc.deleteFile(Path, false);
	}

	public void add2File() {
		if(sb.length() < 1 || Path == null)
			return;
		FileWriter fw;
		try {
			fw = new FileWriter(Path, true);
			fw.append(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Outputer: write error");
		} 
	}
}
