import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test2 {
	public static void main(String[] args) {
		Test test = new Test();
		DataView dv = new DataView(test);
		/*System.out.println(test.getDays());
		Map <String, Integer> map = test.getCount();
		Set<String> s = map.keySet();
		Iterator it = s.iterator();
		while(it.hasNext()) {
			String str = (String) it.next();
			System.out.println(str + " " + map.get(str));
		}*/
		/*Map <String, Float> fmap = test.getCountStatus();
		Set<String> st = fmap.keySet();
		Iterator itf = st.iterator();
		while(itf.hasNext()) {
			String str = (String) itf.next();
			System.out.println(str + " " + fmap.get(str));
		}*/
		/*Map <Integer, Map <String, Integer>> map2 = null;
		try {
			 map2 = test.getCountStatus();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*Set <Integer> set = map2.keySet();
		Iterator st = set.iterator();
		while(st.hasNext()) {
			int day = (int) st.next();
			System.out.println("DAY " + day);
			Map <String, Integer> map3 = map2.get(day);
			Set <String> set2 = map3.keySet();
			Iterator s2t = set2.iterator();
			while(s2t.hasNext()) {
				String s2 = (String)s2t.next();
				System.out.println(s2 + " " + map3.get(s2));
			}
		}*/
		
		Map <Date, String> map4 = test.getTotalDuration();
		Set set3 = map4.keySet();
		Iterator it2 = set3.iterator();
		while(it2.hasNext()) {
			Date d = (Date) it2.next();
			System.out.println(d + " " + map4.get(d).toString());
		}
		
		List <String> asdf = test.getFilteredActivities();
		Iterator a1 = asdf.iterator();
		while(a1.hasNext()) {
			System.out.println(a1.next().toString());
		}
		
		
	}
}
