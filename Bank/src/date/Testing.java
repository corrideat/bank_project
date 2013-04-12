package date;

public class Testing {
	
	 /**public static void waiting (int n){
	        
	        long t0, t1;

	        t0 =  System.currentTimeMillis();

	        do{
	            t1 = System.currentTimeMillis();
	        }
	        while (t1 - t0 < n);
	    }**/

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DateTime b = new DateTime();
			
			DateTime a = new DateTime(2012, 02, 28, 1, 1, 2);
			
			DateTime c = new DateTime(2011, 02, 28, 1, 1, 2);
			
			System.out.println(a.toString());
			// waiting(2000);
			System.out.println(a.toString());
			
			System.out.println(b.toString());
			
			System.out.println(a.add(new Time(1, 2)).toString());
			System.out.println(c.add(new Time(1, 2)).toString());
			System.out.println(c.add(new Time(-1, 2, -1, 2)).toString());
			System.out.println(c.subtract(new Time(-1, 2, -1, 2)).toString());
			
			System.out.println((new Time(-1, 2, 1)).toString());
			
			System.out.println(b.add(b.subtract(a)).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
