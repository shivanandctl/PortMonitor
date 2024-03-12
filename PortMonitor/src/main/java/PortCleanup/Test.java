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
//		Act act = new Act();
		
//		pm.updateRecordAfterCleanup("CO/KXFN/048739/LUMN");
//		pm.cleanPortsViaPortMonitorData("CO/KXFN/036471/LUMN", "1");
//		
//	      String[][] data = {
//	              {"UNI SERVICE", "ENVIRONMENT", "DEVICE", "PORT"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/2"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/3"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/4"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/5"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/6"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/7"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/8"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/9"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/10"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/11"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/12"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/13"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/14"},
//                  {"CO/KXFN/048739/LUMN", "TEST1", "LABBRMCOW2212", "GigabitEthernet0/0/15"}
//	          };
//
//	          printLine();
//	          for (String[] row : data) {
//	              printRow(row);
//	              printLine();
//	          }
//		
//	}
//	
//    public static void printLine() {
//        System.out.println("+-----------------------+-----------------------+-----------------------+-----------------------+");
//    }
//
//    public static void printRow(String[] row) {
//        for (String cell : row) {
//            System.out.printf("| %-21s ", cell);
//        }
//        System.out.println("|");
//    }
		ArrayList<String> uniList = new ArrayList<String>();
		uniList.add("CO/KXFN/048828/LUMN");
		uniList.add("CO/KXFN/048831/LUMN");
		uniList.add("CO/KXFN/048602/LUMN");
		uniList.add("CO/KXFN/048636/LUMN");
		uniList.add("CO/KXFN/048637/LUMN");
		uniList.add("CO/KXFN/048678/LUMN");
		uniList.add("CO/KXFN/048727/LUMN");
		uniList.add("CO/KXFN/048744/LUMN");
		uniList.add("CO/KXFN/048754/LUMN");
		pm.printCleanedUniList(uniList);
}
}
