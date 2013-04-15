package date;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTime {
	private Calendar m_calendar; 
	
	public DateTime(final int ... args) {
		if (args.length > TimeUnits.values().length) throw new IllegalArgumentException("Too many arguments");
		if (args.length == 0) {
			m_calendar = new GregorianCalendar();
		} else {
			int[] params = new int[]{0,0,0,0,0,0};
			for(byte i=0;i<args.length;i++) {
				params[i]=args[i]; // TODO: Validate input
			}
			m_calendar = new GregorianCalendar(params[0], params[1]-1, params[2], params[3], params[4], params[5]);
		}
	}
	
	public DateTime() {
		m_calendar = new GregorianCalendar();
	}
	

	public DateTime(final long millis) {
		m_calendar = new GregorianCalendar();
		m_calendar.setTimeInMillis(millis);
	}
	
	protected DateTime(Calendar calendar) throws Exception {
		if (calendar == null) throw new NullPointerException();
		m_calendar = calendar;
	}
	
	public String toString() {
		//return String.format("%tB %d %tY, %d:%tM:%tM", getMonth(), getDay(), getYear(), getHour(), getMinute(), getSecond());
		if (m_calendar.get(Calendar.ERA)==GregorianCalendar.BC) {
			return String.format("%tB %td %tY BC, %tH:%tM:%tS", m_calendar, m_calendar, m_calendar, m_calendar, m_calendar, m_calendar);
		} else
			return String.format("%tB %td %tY, %tH:%tM:%tS", m_calendar, m_calendar, m_calendar, m_calendar, m_calendar, m_calendar);
	}
	
	public String toString2() {
		//return String.format("%tB %d %tY, %d:%tM:%tM", getMonth(), getDay(), getYear(), getHour(), getMinute(), getSecond());
		if (m_calendar.get(Calendar.ERA)==GregorianCalendar.BC) {
			return String.format("%tB %td %tY", m_calendar, m_calendar, m_calendar);
		} else
			return String.format("%tB %td %tY", m_calendar, m_calendar, m_calendar);
	}
	
	private int get(final TimeUnits type) {
		int itype=0;
		
		if (type==TimeUnits.MONTH) return m_calendar.get(Calendar.MONTH)+1;  
		if (type==TimeUnits.YEAR) return ((m_calendar.get(Calendar.ERA)==GregorianCalendar.AD)?1:-1)*m_calendar.get(Calendar.YEAR);
		
		switch(type) {
		case DAY:
			itype=Calendar.DAY_OF_MONTH;
			break;
		case HOUR:
			itype=Calendar.HOUR;
			break;
		case MINUTE:
			itype=Calendar.MINUTE;
			break;
		case SECOND:
			itype=Calendar.SECOND;
			break;
		default:
			break;
		}
		return m_calendar.get(itype);		 
	}
	
	public long getEpoch() {
		return m_calendar.getTimeInMillis();
	}
	
	public int getYear() {
		return get(TimeUnits.YEAR);
	}
	
	public int getMonth() {
		return get(TimeUnits.MONTH);
	}

	public int getDay() {
		return get(TimeUnits.DAY);
	}

	public int getHour() {
		return get(TimeUnits.HOUR);
	}
	
	public int getMinute() {
		return get(TimeUnits.MINUTE);
	}

	public int getSecond() {
		return get(TimeUnits.SECOND);
	}
	
	public int getYearMonth() {
		return get(TimeUnits.MONTH)+get(TimeUnits.YEAR)*12;
	}
	
	public DateTime add(Time time) {
		try {
			DateTime temp=new DateTime(new GregorianCalendar());			
			if (time != null) {
				temp.m_calendar.setTimeInMillis(m_calendar.getTimeInMillis()+time.getRawCountInMillis());
			}
			return temp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public DateTime subtract(Time time) {
		try {
			DateTime temp=new DateTime(new GregorianCalendar());			
			if (time != null) {
				temp.m_calendar.setTimeInMillis(m_calendar.getTimeInMillis()-time.getRawCountInMillis());
			}
			return temp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Time subtract(DateTime time) {
		return new Time((long)(this.m_calendar.getTimeInMillis()-time.m_calendar.getTimeInMillis())/1000L);
	}	
}
