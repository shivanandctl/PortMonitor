package PortCleanup;

import io.restassured.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import PortCleanup.*;

import io.restassured.response.*;

public class Rubicon {

	Base base = new Base();

	// List of Lab Devices
	public ArrayList<String> listLabDevices() {
		ArrayList<String> labDevices = new ArrayList<String>();
		//ZUNI DEVICES
		labDevices.add("LAB2COZEZG001");  //LABESTZNZG001
		labDevices.add("LAB2COZEW2001");  //LABESTZNW2001
		labDevices.add("LAB2COZEW2002");  //LABESTZNW2002
		
		labDevices.add("LAB4COZW5M001");  //LABWSTZN5M001
		labDevices.add("LAB4COZW5M002");  //LABWSTZN5M002
		labDevices.add("LAB4COZWYJ001");  //LABWSTZNYJ001	
		labDevices.add("LAB4COZWZG001");  //LABWSTZNZG001
		labDevices.add("LAB4COZWZG002");  //LABWSTZNZG002
		
		//Old Device names
		
		/*		
		labDevices.add("LABESTZNZG001");
		labDevices.add("LABESTZNW2001");
		labDevices.add("LABESTZNW2002");
		
		labDevices.add("LABWSTZN5M001");
		labDevices.add("LABWSTZN5M002");
		labDevices.add("LABWSTZNYJ001");
		labDevices.add("LABWSTZNZG001");
		labDevices.add("LABWSTZNZG002");		
		*/		
		
		return labDevices;
	}

	// fetch uni's on a lab device
	public ArrayList<String> fetchUnisFromDevice(String device) {
		ArrayList<String> uniList = new ArrayList<String>();
		uniList.clear();
		uniList.add(device);

		String usernameRubicon = base.usernameRubicon;
		String passwordRubicon = base.passwordRubicon;

		// read from environment.properties file
		String rubiconURL = base.RUBICON_INT_DESCRIPTION_URL + device;

		String auth = usernameRubicon + ":" + passwordRubicon;
		String authHeader = "";
		byte[] encodedAuth;

		// encode username and password
		try {
			encodedAuth = Base64.encodeBase64(auth.getBytes("UTF-8"));
			authHeader = new String(encodedAuth);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Response response;

//		response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and().when()
//				.get(rubiconURL).then().extract().response();
		response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and().auth()
				.basic(usernameRubicon, passwordRubicon).header("authorization", "Basic " + authHeader).get(rubiconURL)
				.then().extract().response();
		String rubRes = response.asString();

		response.getStatusLine();
//		 System.out.println("Status Line:: "+response.getStatusLine());

		Pattern pattern = Pattern.compile("CO/KXFN/\\d+/LUMN");
		Matcher matcher = pattern.matcher(rubRes);
		while (matcher.find()) {
			uniList.add(matcher.group());
		}

		Pattern pattern1 = Pattern.compile("!!/KXFN/\\d+/LUMN");
		Matcher matcher1 = pattern1.matcher(rubRes);
		while (matcher1.find()) {
			uniList.add(matcher1.group());
		}
		
		Pattern pattern2 = Pattern.compile("NA/KXFN/\\d+/LUMN");
		Matcher matcher2 = pattern2.matcher(rubRes);
		while (matcher2.find()) {
			uniList.add(matcher2.group());
		}
		
		Pattern pattern3 = Pattern.compile("WA/KXFN/\\d+/LUMN");
		Matcher matcher3 = pattern3.matcher(rubRes);
		while (matcher3.find()) {
			uniList.add(matcher3.group());
		}

		return uniList;

	}

	// fetch uni's on all lab device

	public ArrayList<ArrayList> fetchUnisFromAllDevices() {
		Rubicon rubicon = new Rubicon();
		rubicon.listLabDevices();

		// create arraylist for all devices
		ArrayList<ArrayList> uniListAllDevices = new ArrayList<ArrayList>();

		// create arraylist for each device

		ArrayList<String> uniList_LABWSTZN5M001 = new ArrayList<String>();
		ArrayList<String> uniList_LABWSTZN5M002 = new ArrayList<String>();
		ArrayList<String> uniList_LABWSTZNYJ001 = new ArrayList<String>();
		ArrayList<String> uniList_LABWSTZNZG001 = new ArrayList<String>();
		ArrayList<String> uniList_LABWSTZNZG002 = new ArrayList<String>();
		ArrayList<String> uniList_LABESTZNW2001 = new ArrayList<String>();
		ArrayList<String> uniList_LABESTZNW2002 = new ArrayList<String>();
		ArrayList<String> uniList_LABESTZNZG001 = new ArrayList<String>();
		

		for (String device : rubicon.listLabDevices()) {

			if (device.contains("LABWSTZN5M001")) {
				System.out.println("Fetching UNIs on " + device);
				uniList_LABWSTZN5M001 = rubicon.fetchUnisFromDevice(device);
				uniListAllDevices.add(uniList_LABWSTZN5M001);
			} else if (device.contains("LABWSTZN5M002")) {
				System.out.println("Fetching UNIs on " + device);
				uniList_LABWSTZN5M002 = rubicon.fetchUnisFromDevice(device);
				uniListAllDevices.add(uniList_LABWSTZN5M002);
			} else if (device.contains("LABWSTZNYJ001")) {
				System.out.println("Fetching UNIs on " + device);
				uniList_LABWSTZNYJ001 = rubicon.fetchUnisFromDevice(device);
				uniListAllDevices.add(uniList_LABWSTZNYJ001);
			} else if (device.contains("LABWSTZNZG001")) {
				System.out.println("Fetching UNIs on " + device);
				uniList_LABWSTZNZG001 = rubicon.fetchUnisFromDevice(device);
				uniListAllDevices.add(uniList_LABWSTZNZG001);
			} else if (device.contains("LABWSTZNZG002")) {
				System.out.println("Fetching UNIs on " + device);
				uniList_LABWSTZNZG002 = rubicon.fetchUnisFromDevice(device);
				uniListAllDevices.add(uniList_LABWSTZNZG002);
			} else if (device.contains("LABESTZNW2001")) {
				System.out.println("Fetching UNIs on " + device);
				uniList_LABESTZNW2001 = rubicon.fetchUnisFromDevice(device);
				uniListAllDevices.add(uniList_LABESTZNW2001);
			} else if (device.contains("LABESTZNW2002")) {
				System.out.println("Fetching UNIs on " + device);
				uniList_LABESTZNW2002 = rubicon.fetchUnisFromDevice(device);
				uniListAllDevices.add(uniList_LABESTZNW2002);
			} else if (device.contains("LABESTZNZG001")) {
				System.out.println("Fetching UNIs on " + device);
				uniList_LABESTZNZG001 = rubicon.fetchUnisFromDevice(device);
				uniListAllDevices.add(uniList_LABESTZNZG001);
			} else {
				System.out.println("No UNI's found on " + device);
			}
		}

		return uniListAllDevices;

	}

	public static void main(String[] args) {
		Rubicon rubicon = new Rubicon();
		Asri asri = new Asri();
		ArrayList<ArrayList> uniLists = rubicon.fetchUnisFromAllDevices();
		for (ArrayList<String> uniList : uniLists) {
			String device = uniList.get(0);
			if (uniList.size() > 1) {
				System.out.println("\n#-------------------------------------------------------------------#");
				System.out.println("UNI's found on " + device);
				System.out.println("#-------------------------------------------------------------------#");
				for (int i = 1; i < uniList.size(); i++) {
					System.out.println(uniList.get(i) + "\t" + asri.getServiceEnvironment(uniList.get(i)));
				}
				System.out.println("#-------------------------------------------------------------------#\n\n");
			} else {
				System.out.println("\n\nNo UNI's found on " + device);
			}
		}

	}

}
