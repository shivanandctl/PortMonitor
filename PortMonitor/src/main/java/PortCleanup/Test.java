package PortCleanup;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import PortCleanup.PortMonitor;

public class Test {

	public static void main(String[] args) {
		// Parse the string date "2024-03-30" into a LocalDate
//		LocalDate parsedStringDate = LocalDate.parse("2024-03-30");
//		LocalDate currentDate = LocalDate.now().plusDays(1);
//		System.out.println(parsedStringDate);
//		System.out.println(currentDate);
//		
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date currentDate1 = new Date();
//		String formattedDate = formatter.format(currentDate1);
//		System.out.println(formattedDate);
		
		PortMonitor pm = new PortMonitor();
		pm.updateRecordAfterCleanup("CO/KXFN/048739/LUMN");
		
	}

}
