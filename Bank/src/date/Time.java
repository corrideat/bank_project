package date;

public class Time {
	long seconds;
	
	static private int secondEquivalencies[] = {
		24*60*60, // Day
		60*60, // Hour
		60, // Minute
		1
	};
	
	
	public Time(final int ... args) throws Exception {
		if (args.length > 4) throw new IllegalArgumentException();
		else {
			seconds=0L;
			for (byte i=0;i<args.length;i++) {
				seconds+=(long)((long)(args[i])*(long)(secondEquivalencies[i]));
			}
		}
	}
	
	public Time(final long time) {
		seconds = time;
	}

	public String toString() {
		return String.format("%c%d days, %02d:%02d:%02d", seconds<0?'-':'+', Math.abs(getDays()), Math.abs(getHours()), Math.abs(getMinutes()), Math.abs(getSeconds()));		
	}
	
	private int get(final TimeUnits type) {
		switch(type) {
		case DAY:
			return (int) (seconds/secondEquivalencies[0]);
		case HOUR:
			return (int) ((seconds%secondEquivalencies[0])/secondEquivalencies[1]);
		case MINUTE:
			return (int) ((seconds%secondEquivalencies[0]%secondEquivalencies[1])/secondEquivalencies[2]);
		case SECOND:
			return (int) ((seconds%secondEquivalencies[0]%secondEquivalencies[1]%secondEquivalencies[2])/secondEquivalencies[3]);
		default:
			return 0;
		}
	}
	
	public int getDays() {
		return get(TimeUnits.DAY);
	}

	public int getHours() {
		return get(TimeUnits.HOUR);
	}
	
	public int getMinutes() {
		return get(TimeUnits.MINUTE);
	}

	public int getSeconds() {
		return get(TimeUnits.SECOND);
	}
	
	public long getRawCountInMillis() {
		return seconds*1000L;
	}
	
	public Time add(Time time) {
		if (time != null)
			return new Time(this.seconds + time.seconds);
		else return new Time(this.seconds);
	}

	public Time subtract(Time time) {
		if (time != null)
			return new Time(this.seconds - time.seconds);
		else return new Time(this.seconds);
	}
}
