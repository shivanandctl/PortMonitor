package PortCleanup;

import java.time.LocalDate;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		Act act = new Act();
		ArrayList<String> requestIDs =act.getRequestIDs("CO/IRXX/046748/LUMN", "4");
		for (String requestID : requestIDs) {
			System.out.println(requestID);
		}
	}

}
