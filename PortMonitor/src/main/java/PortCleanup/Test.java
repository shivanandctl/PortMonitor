package PortCleanup;

import java.time.LocalDate;

public class Test {

	public static void main(String[] args) {
		String cleanupDate = "2024-03-09";
		// Parse the string date "2024-03-30" into a LocalDate
		LocalDate parsedStringDate = LocalDate.parse(cleanupDate);
		LocalDate currentDate = LocalDate.now();
		System.out.println("Current Date: " + currentDate);
		int comparisonResult = currentDate.compareTo(parsedStringDate);
		if (comparisonResult > 0 || comparisonResult == 0) {
			System.out.println("::Cleanup date has crossed or equals today's date adding for cleanup");
			System.out.println(
					"+----------------------------------------------------------------------------------+");
		} else {
			System.out.println("Cleanup date is greater than today's date not adding for cleanup");
			System.out.println(
					"+----------------------------------------------------------------------------------+");
		}
	}

}
