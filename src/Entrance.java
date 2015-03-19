import java.io.File;
import java.io.IOException;

import com.hz.uFunc;

import extraction.Extract;




/**
 * @author iampkuhz
 *
 * Mar 18, 2015
 */
public class Entrance {
	private static boolean errorInit = false;

	private static final String c = "Main";
	private static final int paraNr = 5;
	
	private static String htmlFolder = null;
	private static boolean onlineCraw = false, save2file = false;

	private static String dataFolder = null;
	
	private static String outPath = null;
	private static boolean append = false;
	
	private static final String helpInfo = "para format:\n"
			+ "java ZhwikiIMGExtractor.jar htmlFolderPath status dataFolderPath outputFilePath appendFile\n"
			+ "\t" + "status = 0, 1 or 2\n"
			+ "\t\t0: use htmlPages inside Folder, 2 layer, e. g. \"htmlFolderPath/103/472824_張家輝.html\"\n"
			+ "\t\t1: extract htmlPages online, without saving the htmlPages\n"
			+ "\t\t2: extract htmlPages online, as well as saving the htmlPages under htmlFolderPath\n"
			
			+ "\n\t" + "appendFile = 0 or 1\n"
			+ "\t\t0: delete origin output file if exist\"\n"
			+ "\t\t1: append tail to existing output file\n";
	
	private static final String welcomeInfo = "Hello!\n"
			+ "this program aims at automatically extracting entities' images inside Infoboxes in chinese wikipedia.\n"
			+ "the Infobox list is already filtered by myself using various rules. I hope you can enjoying using it!\n"
			+ "hanzhe(iampkuhz.cn)\n";
	
	/**
	 * @param args
	 */
	public Entrance(String[] args) {
		if(args.length < paraNr)
		{
			uFunc.alert(c, "para not matched, " + paraNr + " needed while " + args.length + " given!");
			uFunc.alert(c, helpInfo);
			errorInit = true;
			return;
		}
		
		if(!(args[1].equals("1") || args[1].equals("2") || args[1].equals("0")))
		{
			uFunc.alert(c, "status error:" + args[1] + "\n" + helpInfo);
			errorInit = true;
			return;
		}
		
		File htmlF = new File(args[0]);
		if(args[1].equals("1") && !(htmlF.exists() && htmlF.isDirectory()))
		{
			uFunc.alert(c, "htmlFolder not a folder:" + args[0]);
			uFunc.alert(c, helpInfo);
			errorInit = true;
			return;
		}
		
		File dataf = new File(args[2]);
		if(!(dataf.exists() && dataf.isDirectory()))
		{
			uFunc.alert(c, "dataFolderPath error:" + args[2] + "\n" + helpInfo);
			errorInit = true;
			return;
		}
		
		if(args[3].contains("/") == false)
		{
			uFunc.alert(c, "outPath error, path must contains \"/\"\n" + helpInfo);
			errorInit = true;
			return;
		}
		
		File outf = new File(args[3].substring(0, args[3].lastIndexOf("/")));
		if(!(outf.exists() && outf.isDirectory()))
		{
			uFunc.alert(c, "outPath error:" + args[3] + "\n" + helpInfo);
			errorInit = true;
			return;
		}
		
		if(!(args[4].equals("1") || args[4].equals("0")))
		{
			uFunc.alert(c, "appendFile error:" + args[1] + "\n" + helpInfo);
			errorInit = true;
			return;
		}
		
		boolean append1 = args[4].equals("1") ? true : false;

		Init(args[0], Integer.parseInt(args[1]), args[2], args[3], append1);
	}

	/**
	 * @param string
	 * @param parseInt
	 * @param string2
	 * @param string3
	 * @param append1
	 */
	public void Init(String htmlFolder1, int status, String dataFolder1, String outPath1,
			boolean append1) {
		htmlFolder = htmlFolder1;
		if(status > 0 ) onlineCraw = true;
		if(status > 1 ) save2file = true;
		dataFolder = dataFolder1;
		outPath = outPath1;
		append = append1;
	}

	/**
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * 
	 */
	private void Exec() throws NumberFormatException, IOException {
		if(errorInit) return;
		else
		{
			System.out.println(welcomeInfo);
			Extract.Do(htmlFolder, onlineCraw, save2file, dataFolder, outPath, append);
		}
	}

	public static void main(String [] args) throws NumberFormatException, IOException 
	{
		Entrance one = new Entrance(args);
		one.Exec();
	}

	
}
