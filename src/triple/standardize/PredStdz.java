package triple.standardize;

import org.htmlparser.Tag;

import com.hz.Entity;
import com.hz.TagShape;
import com.hz.myElement;
import com.hz.myTag;
import com.hz.uFunc;


public class PredStdz {


	public static myElement standardize(Tag tag, int pageid)
	{
		myTag m = new myTag(tag);
		return standardize(m, pageid);
	}

	public static myElement standardize(myTag mtag_pred, int pageid)
	{
		String result = "";
		result = standardize(mtag_pred.tag.toPlainTextString());
		// predicate is current entity's title, 
		// mustn't be a correct triple
		if(uFunc.isPunctuations(result) ||
				Entity.getId(result) == pageid)
		{
			//System.out.println("PredStdz.java:" + result + ";" + pageid);
			return null;
		}
		myTag mytag = TagShape.isA(mtag_pred.tag);
		myElement e;
		if(mytag != null)
		{
			e = new myElement(mytag.context, 
					mytag.tag.getAttribute("HREF"));
		}
		else{
			e = new myElement(result);
		}
		return e;
	}

	private static String standardize(String plainTextString) {
		String result = uFunc.ReplaceBoundSpace(
				HTMLStdz.standardize(plainTextString))
				.replaceAll(" +", " ");
		result = result
				// replace the ":" in the end
				.replaceAll("(:$)|(：$)", "");
		return result;
	}
}
