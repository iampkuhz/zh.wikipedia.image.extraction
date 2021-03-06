package com.hz;

import java.util.Vector;

import org.htmlparser.Tag;

public class myObj {
	public int eleNr;
	public Vector<myElement> eles;
	public myObj(Vector<myElement> iele)
	{
		if(iele == null || iele.size() < 1)
			System.out.println("myObj init failed!");
		eleNr = iele.size();
		eles = new Vector<myElement>(iele);
	}

	public myObj()
	{
		eles = new Vector<myElement>();
		eleNr = 0;
	}
	public void addEle(myElement e)
	{
		myElement e2;
		if(e.link != null)
			e2 = new myElement(e.context, e.link);
		else e2 = new myElement(e.context);
		eles.add(e2);
		eleNr ++;
	}
	public void addEle(Vector<myElement> es)
	{
		if(es == null){
			System.out.println("myObj init failed!");
			return;
		}
			
		for(int i = 0 ; i < es.size(); i ++)
		{
			eles.add(es.get(i));
			eleNr ++;
		}
	}

	public void OutputEle(Tag tag)
	{
		System.out.println("****elements begin*****");
		if(tag != null)
			System.out.println("tag:" + tag.toHtml());
		for(int i = 0 ; i < eles.size(); i ++)
		{
			System.out.println("ele" + i + ":" + eles.get(i).context);
		}
		System.out.println("**** elements end *****");
	}

	public void OutputEle(String string) {
		// TODO Auto-generated method stub
		System.out.println(string + "\n****elements begin*****");
		for(int i = 0 ; i < eles.size(); i ++)
		{
			System.out.println("ele" + i + ":" + eles.get(i).context);
		}
		System.out.println("**** elements end *****");
	}
	
	public String toString()
	{
		String result = "";
		if(eles != null)
		{
			for(myElement ele : eles)
				result += ele.context + "，";
		}
		if(result.equals("") == false)
			result = result.substring(0, result.length() - 1);
		return result;
	}
	
}
