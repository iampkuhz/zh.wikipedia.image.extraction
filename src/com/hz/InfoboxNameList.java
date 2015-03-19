package com.hz;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;


public class InfoboxNameList {
	private static String EnNamePath ;
	private static String ZhNamePath ;
	private static String SimEnNamePath;

	private final static HashMap<String, Integer> InfoboxNameList = new HashMap<String, Integer>();
	private static boolean infoboxListInited = false;
	


	/**
	 * @param dataFolder
	 */
	public InfoboxNameList(String dataFolder) {
		EnNamePath = dataFolder + "enInfoboxNameList_category";
		ZhNamePath = dataFolder + "zhInfoboxNameList_category";
		SimEnNamePath = dataFolder + "simenInfoboxNameList_category";
	}

	public static boolean isInfoboxName(String name)
	{
		if(infoboxListInited == false) LoadInfoboxNameList();
		
		String n = name.replaceAll("\\s+", "_").toLowerCase();
		if(n.contains("collaps")) return false;
		
		if(n.contains("box") && n.contains("metadata") == false)
			return true;
		
		if(n.equals("toccolours") || n.equals("wikitable"))
			return true;
		
		if(InfoboxNameList.containsKey(n))
			return true;
		
		return false;
	}
	
	/**
	 * already convert to lowercase and simple chinese
	 * @throws IOException 
	 */
	private static void LoadInfoboxNameList() {
		if(infoboxListInited == true)
			return;
		LoadInfoboxNameList(EnNamePath);
		LoadInfoboxNameList(ZhNamePath);
		LoadInfoboxNameList(SimEnNamePath);
		
		System.out.println("InfoboxNameList size:" + InfoboxNameList.size());
		
		infoboxListInited = true;
	}
	
	private static void LoadInfoboxNameList(String path) {
		BufferedReader br = uFunc.getBufferedReader(path);
		String oneLine = "";
		
		try {
			while((oneLine = br.readLine()) != null){
				if(oneLine.toLowerCase().startsWith("template:")){
					String name = uFunc.Simplify(
							oneLine.substring(9).replaceAll("\\s+", "_").toLowerCase());
					if(name.contains("/")){
						name = name.substring(0, name.indexOf("/"));
					}
					InfoboxNameList.put(name, 0);
				}
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//System.out.println("InfoboxNameList size:" + InfoboxNameList.size());
		infoboxListInited = true;
	}

}
