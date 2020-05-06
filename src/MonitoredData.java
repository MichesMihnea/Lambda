import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MonitoredData {
	public Date start;
	public Date end;
	public String activity;
	
	public MonitoredData(Date start, Date end, String activity) {
		this.start = start;
		this.end = end;
		this.activity = activity;
	}
	
	public String toString() {
		return this.start.toString() + " " + this.end.toString() + " " + this.activity;
	}
	
	public Boolean dateMatch(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = null;
		try {
			dateWithoutTime  = sdf.parse(sdf.format(this.start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dateWithoutTime.toString().contains(d.toString()))
			return true;
		return false;
	}
	
	public long durationInHours() {
		long diffInMillies = Math.abs(this.end.getTime() - this.start.getTime());
		long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}
	
	public long durationInMinutes() {
		long diffInMillies = Math.abs(this.end.getTime() - this.start.getTime());
		long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}
}
