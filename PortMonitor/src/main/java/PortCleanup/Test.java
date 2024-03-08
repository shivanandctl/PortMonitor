package PortCleanup;

public class Test {

	public static void main(String[] args) {
		String s = "CO/KXFN/048458/LUMN";
		if (s.contains("KXFN")&& !s.contains("_")) {
			System.out.println("add to clean up list");
		}else {
			System.out.println("no need to clean up");			
		}
	}

}
