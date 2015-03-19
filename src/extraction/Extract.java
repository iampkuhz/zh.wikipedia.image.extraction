package extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.util.NodeList;

import com.hz.Entity;
import com.hz.Infobox;
import com.hz.InfoboxNameList;
import com.hz.Outputer;
import com.hz.uFunc;



/**
 * @author iampkuhz
 *
 * Mar 18, 2015
 */
public class Extract {

	/**
	 * @param append 
	 * @param outPath 
	 * @param dataFolder 
	 * @param save2file 
	 * @param onlineCraw 
	 * @param htmlFolder 
	 * 
	 */
	private static String c = "Extract";
	private static Outputer out;
	private static HashMap<Integer, Integer> extractedPages;
	private static boolean saveFile;
	
	public static void Do(String htmlFolder1, boolean onlineCraw, boolean save2file, 
			String dataFolder, String outPath, boolean append) throws NumberFormatException, IOException {
		@SuppressWarnings("unused")
		Infobox inf = new Infobox(dataFolder);
		
		@SuppressWarnings("unused")
		InfoboxNameList infName = new InfoboxNameList(dataFolder);
		
		@SuppressWarnings("unused")
		Entity entityInit = new Entity(dataFolder);
		
		saveFile = save2file;
		
		extractedPages = new HashMap<Integer, Integer>();
		
		if(append)
		{
			LoadextractedPages(outPath);
			out = new Outputer(outPath, false);
		}
		else
		{
			out = new Outputer(outPath);
		}
		
		int pNr = 0;
		long stTime = System.currentTimeMillis();
		
		
		if(onlineCraw == false)
		{
			File topFolder = new File(htmlFolder1);
			int fNr = 0;
			for(File folder : topFolder.listFiles())
			{
				for(File file : folder.listFiles())
				{
					pNr ++;
					ExtractOneFile(file);
				}
				if((++fNr) % 5 == 0) uFunc.alert(c, fNr + " folders passed, " + pNr + " pages passed, " + out.recordNr() + " img url extracted!\t cost:" + uFunc.GetTime(System.currentTimeMillis() - stTime));
			}
			uFunc.alert(c, fNr + " folders total, " + pNr + " pages passed, " + out.recordNr() + " img url extracted!\t cost:" + uFunc.GetTime(System.currentTimeMillis() - stTime) + " total!");
		}
		else if(onlineCraw == true)
		{
			
			ScanEntityList(Entity.id2titFile, htmlFolder1);
		}
		out.close();
		
	}

	private static String htmlFolder;
	private static int subFolderNr;
	private static long stTime;
	
	private static void ScanEntityList(String id2titFile, String htmlFolder1) throws NumberFormatException, IOException {
		htmlFolder = htmlFolder1;
		subFolderNr = 1;
		stTime = System.currentTimeMillis();
		
		uFunc.deleteFolder(htmlFolder1);
		
		BufferedReader br = uFunc.getBufferedReader(id2titFile);
		
		String oneline = "";
		
		while((oneline = br.readLine()) != null)
		{
			int id = Integer.parseInt(oneline.split("\t")[0]);
			
			String html = ExtractOneFile(id, "http://zh.wikipedia.org/wiki?curid=" + id);
			if(saveFile)
			{
				File folder;
				while(true)
				{
					folder = new File(htmlFolder + subFolderNr + "/");
					if(folder.exists() == false)
					{
						folder.mkdir();
						break;
					}
					else{
						if(folder.listFiles().length > 10)
						{
							uFunc.alert(c, subFolderNr + " folders passed, " + subFolderNr + " pages passed, " + out.recordNr() + " img url extracted!\t cost:" + uFunc.GetTime(System.currentTimeMillis() - stTime));
							subFolderNr ++;
							continue;
						}
						else break;
					}
				}
				uFunc.deleteFile(htmlFolder + subFolderNr + "/" + id, false);
				uFunc.addFile(html, htmlFolder + subFolderNr + "/" + id); 
			}
		}
	}

	private static void ExtractOneFile(File file) throws IOException {
		int pageid = Integer.parseInt(file.getName().substring(0, file.getName().indexOf("_")));
		String Path = file.getAbsolutePath();

		// for empty file, may be extract null
		if(file.exists() == false || file.length() < 10)
			Path = "http://zh.wikipedia.org/wiki?curid=" + pageid;

		ExtractOneFile(pageid, Path);
	}

	
	private static String ExtractOneFile(int pageid, String Path) {
		if(Infobox.NO.containsKey(pageid) || extractedPages.containsKey(pageid)) return null ;
		
		String title = uFunc.Simplify(Entity.getTitles(pageid));
		if(title != null && (title.contains("列表") || title.contains("年表") 
				|| title.endsWith("时间表") || title.endsWith("系表")))
			return null;
		
		Parser pageParser ;
		try {
			try{
				pageParser = new Parser(Path);
			}catch(Exception e1){
				pageParser = new Parser("http://zh.wikipedia.org/wiki?curid=" + pageid);
			}
			pageParser.setEncoding("UTF-8");
			NodeList pageNodeList = pageParser.parse(null);
			PageNode pageNode = new PageNode(pageid, pageNodeList);
			pageNode.GetTriples(true);
			return pageNodeList.toHtml();
		} catch (Exception e) {
			//com.hz.uFunc.alert(c , Path);
			//e.printStackTrace();
			return null;
		}
	}

	private static void LoadextractedPages(String outPath) throws NumberFormatException, IOException {
		BufferedReader br = uFunc.getBufferedReader(outPath);
		if(br == null) return;
		
		String oneline = "";
		while((oneline = br.readLine()) != null)
		{
			int pid = Integer.parseInt(oneline.split("\t")[0]);
			extractedPages.put(pid, 0);
		}
		uFunc.alert(c, "extracted IMG number:" + extractedPages.size() + ", start appending!");
	}
	
	private static int lastId = 0;
	public static void AddOneIMG(Tag tag, int pageid) {
		if(pageid == lastId) return;
		
		String url = tag.getAttribute("SRC");
		if(url == null) return;
		
		if(url.startsWith("//"))
			url = url.substring(2);
		
		String info = pageid + "\t" + Entity.getTitle(pageid) + "\t" + url + "\t" + 
				tag.getAttribute("WIDTH") + "\t" + tag.getAttribute("HEIGHT") + "\n";
		out.append(info);
		
		lastId = pageid;
	}

}
