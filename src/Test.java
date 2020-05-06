import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
	List <MonitoredData> data = new ArrayList <MonitoredData> ();
	public void calc() throws FileNotFoundException, IOException
    {
        StringBuilder out = new StringBuilder();
        Date start, end;
        String activity;
        List lines;
        
        try (Stream<String> stream = Files.lines(Paths.get("Activities.txt"))) {
        	lines = (List) stream.map(x -> x).collect(Collectors.toList());
        }
        
        Iterator lt = lines.iterator();
        while(lt.hasNext()) {
        	String line = (String) lt.next();
            out.append(line);
            StringTokenizer st = new StringTokenizer(line, "	");
            try {
				start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(st.nextToken());
				end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(st.nextToken());
				activity = st.nextToken();
				data.add(new MonitoredData(start, end, activity));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}  
        }
    }
	
	public List <MonitoredData> getData(){
		return this.data;
	}
	
	public int getDays() {
		List data1 = new ArrayList();
		Iterator i = data.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while(i.hasNext()) {
			MonitoredData md = (MonitoredData) i.next();
			Date dateWithoutTime1 = null;
			Date dateWithoutTime2 = null;
			try {
				dateWithoutTime1 = sdf.parse(sdf.format(md.start));
				dateWithoutTime2 = sdf.parse(sdf.format(md.start));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data1.add(dateWithoutTime1);
			data1.add(dateWithoutTime2);
		}
		List dates = (List) data1.stream().filter(distinctByKey(x->x.toString())).collect(Collectors.toList());
		return dates.size();
	}
	
	public List getDates() {
		List data1 = new ArrayList();
		Iterator i = data.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while(i.hasNext()) {
			MonitoredData md = (MonitoredData) i.next();
			Date dateWithoutTime1 = null;
			Date dateWithoutTime2 = null;
			try {
				dateWithoutTime1 = sdf.parse(sdf.format(md.start));
				dateWithoutTime2 = sdf.parse(sdf.format(md.start));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data1.add(dateWithoutTime1);
			data1.add(dateWithoutTime2);
		}
		List dates = (List) data1.stream().filter(distinctByKey(x->x.toString())).collect(Collectors.toList());
		return dates;
	}
	
	public List getActivities() {
		Iterator i = data.iterator();
		List activities = new ArrayList();
		while(i.hasNext()) {
			MonitoredData md = (MonitoredData) i.next();
			activities.add(md.activity);
		}
		
		return activities;
	}
	
	public List getUniqueActivities() {
		List nonUnique = this.getActivities();
		List unique = (List) nonUnique.stream().filter(distinctByKey(x->x.toString())).collect(Collectors.toList());
		return unique;
	}
	
	public Map<String, Integer> getCount(){
		List data1 = this.getActivities();
		
		List activities = (List) data1.stream().filter(distinctByKey(x->x.toString())).collect(Collectors.toList());
		List counts = (List) activities.stream().map(n -> Collections.frequency(data1, n)).collect(Collectors.toList());

		Iterator it = activities.iterator();
		Iterator jt = counts.iterator();
		Map <String, Integer> map = new TreeMap <String, Integer> ();
		while(it.hasNext()) {
			map.put((String) it.next(), (Integer)jt.next());
		}
		return map;
	}
	
	public Map <Integer, Map<String, Integer>> getCountStatus() throws ParseException{
		List activities = this.getUniqueActivities();
		Map <Integer, Map <String, Integer>> map = new TreeMap <Integer, Map<String, Integer>> ();
		Iterator it = this.getDates().iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//Date dateWithoutTime = null;
		int ii = 0;
		while(it.hasNext()) {
				final Date dateWithoutTime = sdf.parse(sdf.format((Date)it.next()));
				
			List dayLog = data.stream().filter(x -> x.dateMatch(dateWithoutTime)).collect(Collectors.toList());
			Map <String, Integer> dayCount = new TreeMap <String, Integer> ();
			
			Iterator at = activities.iterator();
			
			while(at.hasNext()) {
				String s = (String)at.next();
				dayCount.put(s, (int) dayLog.stream().filter(x -> x.toString().contains(s)).count());
			}
			Iterator i = activities.iterator();
			/*while(i.hasNext()) {
				String s = (String)i.next();
				System.out.println(s + " " + dayCount.get(s));
			}*/
			
			map.put(new Integer(ii), dayCount);
			ii++;
		}
		
		return map;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    KeySetView<Object, Boolean> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
	public Map <Date, String> getTotalDuration(){
		Map <Date, String> map = new TreeMap <Date, String> ();
		List durations = data.stream().filter(x -> x.durationInHours() > 9).collect(Collectors.toList());
		Iterator it = durations.iterator();
		
		while(it.hasNext()) {
			MonitoredData md = (MonitoredData) it.next();
			Duration d = Duration.ofHours(md.durationInHours());
			map.put(md.start, md.activity);	
		}
		return map;
	}
	
	public List <String> getFilteredActivities(){
		List data1 = this.getActivities();
		List activities = (List) data1.stream().filter(distinctByKey(x->x.toString())).collect(Collectors.toList());
		List counts = (List) activities.stream().map(n -> Collections.frequency(data1, n)).collect(Collectors.toList());
		List <String> freq = (List) activities.stream().filter(x -> {
			int durationOver = 0;
			int totals = 0;
			String s = x.toString();
			for(MonitoredData md: data) {
				String s1 = md.activity;
				if(s.contains(s1)) {
					if(md.durationInMinutes() > 5 && s.contains(s1))
						durationOver ++;
					totals ++;
					}
			}
			
			if(durationOver > 9 * totals / 10)
				return true;
										
			return false;}).collect(Collectors.toList());
		return freq;
	}
}
