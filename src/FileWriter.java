import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FileWriter {
	public void writeFile(Map <String, Integer> map, int task) {
		String fileName = new String();
		fileName = ("Task" + "-" + task + ".txt");
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			Set <String> set = map.keySet();
			Iterator <String> itSet = set.iterator();
			while(itSet.hasNext()) {
				String s = itSet.next();
				writer.println(s + " " + map.get(s));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
