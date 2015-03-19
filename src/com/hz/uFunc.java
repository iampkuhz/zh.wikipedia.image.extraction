package com.hz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Tag;

import com.spreada.utils.chinese.ZHConverter;

public class uFunc {

	public static boolean Contain(String string, String regex)
	{
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string);
		return m.find();
	}
	
	public static boolean isPunctuations(String string)
	{
		if(string == null)
			return false;
		if(string.equals(""))
			return true;
		return string.matches("[`~!@#$^&*()=|{}';',\\[\\].<>/?~！@#￥……"
				+ "&*（）——|{}【】《》，。？‘；”“'。，、？\n (and)和等_]{1,}");
	}
	// 全角转半角的 转换函数
	public static String full2HalfChange(String QJstr) {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			// 全角空格转换成半角空格
			if (Tstr.equals("　")) {
				outStrBuf.append(" ");
				continue;
			}
			try {
				b = Tstr.getBytes("unicode");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 得到 unicode 字节数据
			if (b[2] == -1) {
				// 表示全角？
				b[3] = (byte) (b[3] + 32);
				b[2] = 0;
				try {
					outStrBuf.append(new String(b, "unicode"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				outStrBuf.append(Tstr);
			}
		} // end for.
		return outStrBuf.toString();
	}
	

	public static boolean HasAttriCompnt(Tag tr, String attri, String regex) {
		// TODO Auto-generated method stub
		if(tr == null)
			return false;
		String attriValue = tr.getAttribute(attri.toUpperCase());
		if(attriValue == null)
			return false;
		//uFunc.Alert(true, "uFUnc", tr.getTagName() + "  " + attri + " " + attriValue);
		Pattern p = Pattern.compile(regex.toLowerCase());
		Matcher m = p.matcher(attriValue.toLowerCase());
		boolean result = m.find();
		return result;
	}
	
	public static int GetEditDist(ArrayList<String> c1, ArrayList<String> c2) {
		// TODO Auto-generated method stub
		int [][] dis = new int[c1.size()][c2.size()];
		for(int i = 0 ; i < c1.size(); i ++)
			for(int j = 0 ; j < c2.size(); j ++)
				dis[i][j] = 0;
		for(int i = 0 ; i < c1.size(); i ++)
			dis[i][0] = i;
		for(int j = 0; j < c2.size(); j ++)
			dis[0][j] = j;
		for(int i = 1; i < c1.size(); i ++)
			for(int j = 1; j < c2.size(); j ++)
			{
				int min = dis[i-1][j-1];
				if(c1.get(i).equals(c2.get(j)))
					min ++;
				if(min > dis[i-1][j] + 1)
					min = dis[i-1][j] + 1;
				if(min > dis[i][j-1] + 1)
					min = dis[i][j-1] + 1;
				dis[i][j] = min;
			}
		return dis[c1.size()-1][c2.size()-1];
	}

	public static String ReplaceBoundSpace(String string){
		if(string == null)
			return null;
		return string
				//.replaceAll("(&#160;)|(&lt;)|(&gt;)", "")
				//.replaceAll("&amp;", "&")
				//replace the space at the beginning
				.replaceAll("(?m)^[‡\\*\\ _\\s]+", "")
				// replace the space at the end
				.replaceAll("(?m)[ _:：‡\\*\\s]+$", "")
				// replace two or more spaces with a single space
				.replaceAll(" {2,}", " ")
				//replace the space at the beginning
				.replaceAll("(?m)^_+", "")
				// replace the space at the end
				.replaceAll("(?m)_+$", "")
				// replace two or more spaces with a single space
				.replaceAll("_{1,}", " ");
	}

	
	public static void SaveStrIntHashMap(HashMap<String, Integer> map, String path){
		uFunc.deleteFile(path);
		List<Entry<String, Integer>> infoIds =
			    new ArrayList<Entry<String, Integer>>(
			    		((HashMap<String, Integer>)map).entrySet());
		Collections.sort(infoIds, new Comparator<Entry<String, Integer>>() {   
		    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {      
		        //return (o2.getValue() - o1.getValue()); 
		        return (o2.getValue() - o1.getValue());
		    }
		});
		int size = 0;
		StringBuffer output = new StringBuffer();
		for(int i = 0 ; i < infoIds.size() ;  i ++)
		{
			output.append( infoIds.get(i).getKey() + "\t" + 
					infoIds.get(i).getValue() + "\n");
			size ++;
			if(size % 1000 == 0){
				uFunc.addFile(output.toString(), path);
				output.setLength(0);
			}
		}
		uFunc.addFile(output.toString(), path);
	}


	public static String GetTime(long minisec) {
		// TODO Auto-generated method stub
		if(minisec < 0)
			return null;
		long sec = minisec/1000;
		long min = sec/60;
		sec = sec % 60;
		if(min > 0)
			return min + "min" + sec + "sec";
		else return sec + "sec";
	}

	public static void addFile(String string, String path) {
		if(string == null || string.equals(""))
			return;
		try{
			File file=new File(path);
			if(!file.exists()){
				if(file.createNewFile() == false)
					System.out.println("path not exist: " + path);
			}
			OutputStreamWriter osw=
					new OutputStreamWriter(new FileOutputStream(file,true),"utf-8");
			osw.append(string);
			osw.close();
		}catch(Exception e){
			System.out.println(path);
			e.printStackTrace();
		}		
	}
	

	public static void addFile(StringBuffer sb, String path) {
		if(sb == null) return;
		addFile(sb.toString(), path);
		sb.setLength(0);
	}

	public static void deleteFile(String path) {
		deleteFile(path, true);
	}
	public static void deleteFile(String path, boolean info) {
		
		try{
			File file=new File(path);
			if(!file.exists() && info){
				System.out.println("file " + file.getName() + " not exist, can't delete!");
			}
			else if (file.isFile() == true){
				String name = file.getName();
				file.delete();
				if(info) System.out.println("delete " + name + " successfully!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}


	public static void alert(String c, String info) {
		System.out.println(c + ":" + info);
	}
	

	public static boolean hasChineseCharactor(String string){
		Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher matcher=pattern.matcher(string);
		if(matcher.find()){
			return true;
		}
		return false;
	}
	public static boolean isChineseChar(char c){
		return (c >= 0x4e00) && (c <= 0x9fbb);
	}
	

	private static ZHConverter SimConverter = 
			ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
	public static String Simplify(String plainTextString) {
		
		if(plainTextString == null)
			return null;
		return SimConverter.convert(plainTextString);
	}

	private static ZHConverter TraConverter = 
			ZHConverter.getInstance(ZHConverter.TRADITIONAL);
	public static String Traditional(String plainTextString) {
		
		if(plainTextString == null)
			return null;
		return TraConverter.convert(plainTextString);
	}


	public static void save2File(ResultSet rs, String savePath, 
			String intraLine, String interLine) throws SQLException {
		
		uFunc.deleteFile(savePath);
		StringBuffer out = new StringBuffer();
		int outNr = 0;
		int colNr = rs.getMetaData().getColumnCount();
		while(rs.next())
		{
			for(int i = 1; i <= colNr; i ++)
			{
				if(i != 1) out.append(intraLine);
				out.append(rs.getString(i));
			}
			out.append(interLine);
			outNr ++;
			if(outNr % 1000 == 0 )
			{
				addFile(out.toString(), savePath);
				out.setLength(0);
			}
		}
		addFile(out.toString(), savePath);
	}

	public static BufferedReader getBufferedReader(String path) {
		return getBufferedReader(path, "utf8");
	}
	

	public static BufferedReader getBufferedReader(String path, String encoding) {
		try{
			File file = new File(path);
			if(file.exists() == false){
				System.out.println("read file not exist:"+ path);
				return null;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			return reader;
		}catch( Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

	private static int fileNr;
	private static int CodelineNr;
	
	public static String OutputProjectInfo(String folder)
	{
		if(folder == null)
			folder = "src/";
		String result = "";
		File src = new File(folder);
		uFunc.fileNr = 0;
		uFunc.CodelineNr = 0;
		
		if(src.isDirectory() == false)
		{
			result += "src folder error!\n";
		}
		else
		{
			for(File file : src.listFiles())
			{
				uFunc.CountCodelines(file);
			}
		}
		result += "filNr:" + fileNr + "\n";
		result += "codelineNr:" + CodelineNr + "\n";
		return result;
	}

	private static void CountCodelines(File file) {
		if(file.isFile() == true && file.getName().endsWith(".java"))
		{
			fileNr ++;
			//System.out.println(file.getName());
			BufferedReader br = uFunc.getBufferedReader(
					file.getAbsolutePath());
			try {
				while((br.readLine() != null))
					uFunc.CodelineNr ++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(file.isDirectory() == true)
		{
			if(file.getName().equals("ansj"))
				return;
			for(File file1 : file.listFiles())
			{
				uFunc.CountCodelines(file1);
			}
		}
		else
		{
			//System.out.println("file error:" + file.getAbsolutePath());
		}
	}


	public static void SaveHashMap(HashMap<?, ?> map, String path){
		SaveHashMap(map, path, false);
	}
	public static void SaveHashMap(HashMap<?, ?> map, String path,
			boolean append){
		if(append == false)
			uFunc.deleteFile(path);
		int size = 0;
		StringBuffer output = new StringBuffer();
		Iterator<?> it = map.entrySet().iterator();
		while(it.hasNext())
		{
			java.util.Map.Entry<?,?> entry = (java.util.Map.Entry<?,?>)it.next();
			output.append(entry.getKey() + "\t" + 
					entry.getValue() + "\n");
			size ++;
			if(size % 1000 == 0){
				uFunc.addFile(output.toString(), path);
				output.setLength(0);
			}
		}
		uFunc.addFile(output.toString(), path);
	}


	public static boolean isEnglishChar(char c) {
		if((c >= 'a' && c <= 'z' ) || (c >= 'A' && c <= 'Z'))
			return true;
		return false;
	}


	public static boolean isNumber(char cc) {
		if(cc >= '0' && cc <='9')
			return true;
		return false;
	}


	public static void deleteFolder(String path) {
		// TODO Auto-generated method stub
		File folder = new File(path);
		if(folder.exists() && folder.isDirectory()){
			for(File f : folder.listFiles()){
				deleteFile(f.getAbsolutePath());
			}
			System.out.println("folder deleted:" + folder.getAbsolutePath());
		}
		else{
			System.out.println("not correct folder:" + path);
		}
	}

}
