package com.hz;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author iampkuhz
 *
 * Mar 18, 2015
 */
public class Infobox {
	private static final String c = "Infobox";
	
	public static HashMap<Integer, Integer> InfoboxIdList = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Boolean> NO = new HashMap<Integer, Boolean>();

	public Infobox(String dataFolder) throws NumberFormatException, IOException
	{
		infoboxIdListPath = dataFolder + "tripleIdList";
		LoadInfoboxNameList();
		
		NoInfoboxIdListPath = dataFolder + "NoInfoboxIdList";
		LoadNoInfoboxNameList();
	}
	
	private static String NoInfoboxIdListPath;
	private void LoadNoInfoboxNameList() throws NumberFormatException, IOException {
		String oneLine = "";
		BufferedReader br = uFunc.getBufferedReader(NoInfoboxIdListPath);
		
		while((oneLine = br.readLine()) != null)
		{
			Integer id = Integer.parseInt(oneLine);
			NO.put(id, true);
		}
		System.out.println("NoInfoboxIdList size:" + NO.size());
		
	}



	private static String infoboxIdListPath;
	private static void LoadInfoboxNameList() throws NumberFormatException, IOException {
		BufferedReader br = uFunc.getBufferedReader(infoboxIdListPath);
		if(br == null)
		{
			uFunc.alert(c , "infoboxIdListPath not exist file:" + infoboxIdListPath);
			return;
		}
		String oneLine = "";

		while((oneLine = br.readLine())!=null)
		{
			InfoboxIdList.put(Integer.parseInt(oneLine), 0);
		}
		
		System.out.println("infoboxIdList size:" + InfoboxIdList.size());
	}
}
