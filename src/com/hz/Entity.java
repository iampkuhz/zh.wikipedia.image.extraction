package com.hz;
import java.io.BufferedReader;
import java.util.HashMap;

import com.hz.uFunc;






public class Entity {
	public static String id2titFile;
	public static String tit2idFile;
	public static String FreqFile ;
	
	public Entity(String folder)
	{
		id2titFile = folder + "EntityTitles";
		tit2idFile = folder + "EntityTitle2Id";
	}
	
	public static int getId(String title)
	{
		if(Tit2IdInited == false)
			initTitIdMap();
		
		if(Tit2IdMap.containsKey(title))return Tit2IdMap.get(title);

		title = uFunc.Simplify(title);
		if(Tit2IdMap.containsKey(title)) return Tit2IdMap.get(title);
		
		title = uFunc.Traditional(title);
		if(Tit2IdMap.containsKey(title)) return Tit2IdMap.get(title);
		
		return 0;
	}

	public static String getTitles(int pageid)
	{
		if(Id2TitsInited == false)
			initId2TitsMap();
		if(Id2TitsMap.containsKey(pageid))
			return Id2TitsMap.get(pageid);
		return null;
	}
	
	public static String getTitle(int pageid)
	{
		if(Id2TitsInited == false)
			initId2TitsMap();
		if(Id2TitsMap.containsKey(pageid))
		{
			String tits = Id2TitsMap.get(pageid);
			if(tits.contains("####"))
				return tits.split("####")[0];
			return tits;
		}
		return null;
	}


	
	public static HashMap<String, Integer> Str2Id = new HashMap<String, Integer>();
	
	
	// <13, 数学;数学科学;数学系;>
	private static HashMap<Integer, String> Id2TitsMap = 
			new HashMap<Integer, String>();
	private static boolean Id2TitsInited = false;
	private static void initId2TitsMap() {
		if(Id2TitsInited == true)
			return;
		String oneLine = "";
		try{
			BufferedReader reader = uFunc.getBufferedReader(
					id2titFile);
			while((oneLine = reader.readLine())!= null){
				String [] ts = oneLine.split("\t");
				if(ts.length < 2)
				{
					System.out.println("id2tit error:" + oneLine);
					continue;
				}
				int Eid = Integer.parseInt(ts[0]);
				String titles = ts[1];
				Id2TitsMap.put(Eid, titles);
			}
			System.out.println("Id2TitsMap.size:"+ 
					Id2TitsMap.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		Id2TitsInited = true;
	}


	// <数学, 13>
	private static HashMap<String, Integer> Tit2IdMap = 
			new HashMap<String, Integer>();
	private static boolean Tit2IdInited = false;
	private static void initTitIdMap() {
		if(Tit2IdInited == true)
			return;
		String oneLine = "";
		try{
			BufferedReader reader = uFunc.getBufferedReader(tit2idFile);
			while((oneLine = reader.readLine())!= null){
				String [] ts = oneLine.split("\t");
				if(ts.length < 2)
				{
					System.out.println("tit2id error:" + oneLine);
					continue;
				}
				int Eid = Integer.parseInt(ts[1]);
				String title = ts[0];
				if(title == null || title.equals(""))
					continue;
				// new method ensure that there is no chance of one title linking
				// to different pageIds
				Tit2IdMap.put(title, Eid);
			}
			System.out.println("TitsIdMap.size:"+ 
					Tit2IdMap.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		Tit2IdInited = true;
	}

}
