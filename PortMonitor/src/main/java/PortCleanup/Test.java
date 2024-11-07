package PortCleanup;

import io.restassured.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import PortCleanup.*;
import com.jayway.jsonpath.*;

import io.restassured.response.*;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		PortMonitor pm = new PortMonitor();
		PortMonitor2 pm2 = new PortMonitor2();
		Rubicon rubicon = new Rubicon();
		Asri asri = new Asri();
		Autopilot ap = new Autopilot();
//		updatePortMonitorIfUniNotUpdated("CO/KXFN/076384/LUMN");
//		pm.updatePortMonitorIfUniNotUpdated("CO/KXFN/067714/LUMN","LABWSTZNZG002");
//		cleanPortsViaPortMonitorData("CO/IRXX/059317/LUMN", "1");
//		cleanPortsViaPortMonitorData("CO/IRXX/066263/LUMN", "1");
//		asri.cleanIp("CO/IRXX/066263/LUMN");
//		pm2.updateRecordAfterCleanup("CO/KXFN/076377/LUMN");
		

		
//		add the below list into a arraylist
		/*
		 * CO/KXFN/075993/LUMN
		 * 
		 */
		ArrayList<String> CleanedUniList = new ArrayList<String>();
//		CleanedUniList.add("CO/KXFN/076200/LUMN");
//		CleanedUniList.add("101/GE10/DNVFCOQE00W/DNVFCOQE71W");
//		CleanedUniList.add("101/GE10/DNVFCOQE00W/DNVFCOQE71W");
		
		
//
//		
//		for (String uni : CleanedUniList) {
//			pm.cleanPortsViaPortMonitorData(uni, "1");
//        }
		
		pm.cleanPortsViaPortMonitorData("CO/KXFN/079297/LUMN", "1");
//		asri.cleanIp("CO/IRXX/067083/LUMN");
		
//		

		
		
		
		
		
		
		//add the below list into a arraylist
//		pm.validateUniForCleanup("CO/KXFN/067845/LUMN", "LABWSTZNZG002");
		
//		ap.environment = "4";
//		String token = ap.getToken("AC70068", "RobVanDam@wwf@1992#");
//		String x = ap.triggerWorkflow("91657d32-01e0-4064-b418-ffd45851038b", "2e849c00-7f18-11ef-ae3e-2b7ed5be0092", "LNAAS_DELETE_TRANSACTION_ACT_TL_V1", token);
//		System.out.println("Workflow Job::"+x);
//		System.out.println("Workflow Status::"+ap.getWorkflowStatus(x, token));
//		
//		asri.cleanIp("CO/IRXX/068433/LUMN");
//		asri.inventoryCleanUp("CO/IRXX/066284/LUMN", "1");
		
		
		
		
		


	}
	
	// Function to print final list of cleaned UNIs
	public void printCleanedUniList(ArrayList<String> CleanedUniList) {
		//
		System.out.println("PORT_MONITOR_BUILD_LOG_EXCERPT_START");
		System.out.println("<h3 style=\"background-color: #5aadff;color: #ffffff;margin-top: 7px;padding: 8px 5px;text-align: center;\">\r\n"
				+ "    CLEANED UNI LIST\r\n"
				+ "</h3><hr>");
		int rows = CleanedUniList.size()+1;
		String data[][] = new String[rows][4];
		data[0][0] = "<b>UNI SERVICE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>";
		data[0][1] = "<b>ENV&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>";
		data[0][2] = "<b>DEVICE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>";
		data[0][3] = "<b>PORT&nbsp;&nbsp;&nbsp;&nbsp;</b><hr>";
		
		int i = 1;
		for (String uni : CleanedUniList) {
			
			try {
				String query = "https://ndf-test-cleanup.kubeodc-test.corp.intranet/getUnidata/" + uni;
				String response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json")
						.and().when().get(query).then().extract().response().asString();

				String unialias = JsonPath.read(response, "$.unialias");
				String env = JsonPath.read(response, "$.environment");
				String device = JsonPath.read(response, "$.device");
				String port = JsonPath.read(response, "$.portnum");
				
				
					data[i][0] = unialias;
					data[i][1] = env;
					data[i][2] = device;
					data[i][3] = port;
				    i++;
			} catch (Exception e) {
//				System.out.println("Exception in getting UNI details::" + uni);
				data[i][0] = uni;
				data[i][1] = e.getMessage();
				data[i][2] = "";
				data[i][3] = "";
			    i++;
			}		
		}
		
		// print the data in html table format

		for (int j = 0; j < rows; j++) {
			printRow(data[j]);
			System.out.println("");
			
		}
		
		System.out.println("<hr><h5 style=\"background-color: #ececec;color: #000000;padding: 8px 5px;;\">\r\n"
				+ "              TOTAL UNIs CLEANED::"+CleanedUniList.size()+"\r\n"
				+ "</h5>");
		
		

		System.out.println("PORT_MONITOR_BUILD_LOG_EXCERPT_END");
	}

	public static void printLine() {
//		System.out.println(
//				"+-----------------------+-----------------------+-----------------------+-----------------------+");
		System.out.println(""
				+ "\n<br>--------------------------------------------------------------------------------------------------------------------------");
	}

	public static void printRow(String[] row) {
	    System.out.print("<pre>");	
		for (String cell : row) {
//			System.out.printf("  %-21s ", cell);
			System.out.print(cell+"&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		System.out.print("</pre><br>");
	}
	

	public ArrayList<String> validateUniForCleanup(String uni) {

		System.out.println("Validating UNI: " + uni + " from PortMonitor");
		ArrayList<String> cleanupUnis = new ArrayList<String>();

		Response response;

		String query = "https://ndf-test-cleanup.kubeodc-test.corp.intranet/getUnidata/" + uni;
		response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and().when()
				.get(query).then().extract().response();

		int statCode = response.getStatusCode();
		System.out.println("Status code: " + statCode);

		if (statCode == 200) {
			String sasiRes = response.asString();
			String userComment = JsonPath.read(sasiRes, "$.user_comment");
			String createdBy = JsonPath.read(sasiRes, "$.createdBy");
			System.out.println("User comment: " + userComment);
			if (userComment.equals("CAN BE CLEANED!") || userComment.equals("CLEANED")
					|| userComment.equals("CLEANED!")) {
				String cleanupDate = JsonPath.read(sasiRes, "$.cleanup_date");
				System.out.println("Created by: " + createdBy);
				System.out.println("Cleanup date: " + cleanupDate);
				System.out.println("Todays date: " + LocalDate.now().plusDays(1));// adjusting GMT to IST
				System.out.println("User comment is :" + userComment + " hence adding for cleanup");
				cleanupUnis.add(uni);
				System.out.println(
						"+----------------------------------------------------------------------------------+");
			}
			if (userComment.equals("DO NOT CLEAN!")) {
				String cleanupDate = JsonPath.read(sasiRes, "$.cleanup_date");
				System.out.println("Created by: " + createdBy);
				System.out.println("Cleanup date: " + cleanupDate);
				System.out.println("Todays date: " + LocalDate.now().plusDays(1));// adjusting GMT to IST
				LocalDate parsedStringDate = LocalDate.parse(cleanupDate);
				LocalDate currentDate = LocalDate.now().plusDays(1);// adjusting GMT to IST
				int comparisonResult = currentDate.compareTo(parsedStringDate);
				if (comparisonResult > 0 || comparisonResult == 0) {
					System.out.println(uni + "::Cleanup date is less than or equals today's date adding for cleanup");
					cleanupUnis.add(uni);
					System.out.println(
							"+----------------------------------------------------------------------------------+");
				} else {
					System.out.println("Cleanup date is greater than today's date not adding for cleanup");
					System.out.println(
							"+----------------------------------------------------------------------------------+");
				}

			}
		}
		if (statCode == 500) {
			cleanupUnis.add(uni);
			System.out.println("No record found in PortMonitor DB\nUpdating UNI::" + uni + " into port UI for Cleanup");
			updatePortMonitorIfUniNotUpdated(uni);
			System.out.println("+--------------------------------------------------------------------------+");
		}

		return cleanupUnis;
	}

	public static ArrayList<String> cleanPortsViaPortMonitorData(String service, String env) {
		Asri asri = new Asri();
		Act act = new Act();
		ArrayList<String> services = new ArrayList<String>();
		LinkedHashMap<String, String> servicesMap = asri.consolidateServices(service, env);
		ArrayList<String> storeCleanedUni = new ArrayList<String>();

		if (servicesMap.size() > 0) {

			services = asri.getRearragedServices(servicesMap, env);
//			for (Iterator iterator = services.iterator(); iterator.hasNext();)
			while (services.size() > 0)
			{

				String s = (String) services.get(0);
				System.out.println("Cleanup started for::" + s);

				
				boolean actCleanUpStatus = act.networkCleanup(s, env);
				if (actCleanUpStatus) {
					System.out.println("Act Cleanup is successful");

					// cleaning Ips
					if (s.contains("IRXX")||s.contains("MVXX")) {
						asri.cleanIp(s);
					}
					boolean asriCleanUpStatus = asri.inventoryCleanUp(s, env);
					if (asriCleanUpStatus) {
						System.out.println("ASRI Cleanup is successful");
						// update the record in portmonitor db
						if (s.contains("KXFN") && !s.contains("_")) {
							updateRecordAfterCleanup(s);
							storeCleanedUni.add(s);
						}

					} else {
						System.out.println("ASRI Cleanup is not successful");
						break;
					}
				} else {
					System.out.println("Act Cleanup is not successful");
					break;
				}
				servicesMap = asri.consolidateServices(service, env);
				services = asri.getRearragedServices(servicesMap, env);

			}
		} else {
			{
				System.out.println("Service Not found in Inventory");

				ArrayList<String> envs = new ArrayList<String>();
				envs.add("1");
				envs.add("2");
				envs.add("4");
//				
//				System.out.println("Cleaning up in Network/ACT::"+service);
//				
//				if(service.contains("IRXX")) {
//					asri.cleanIp(service);
//				}
//				
//				for (String eachEnv : envs) {
//					boolean actCleanUpStatus = act.networkCleanup(service, eachEnv);
//					if (actCleanUpStatus) {
//						System.out.println("Act Cleanup is successful");
//						boolean asriCleanUpStatus = asri.inventoryCleanUp(service, eachEnv);
//						if (asriCleanUpStatus) {
//							System.out.println("ASRI Cleanup is successful");
//						} else {
//							System.out.println("ASRI Cleanup is not successful");
//						}
//					} else {
//						System.out.println("Act Cleanup is not successful");
//					}
//				}

				System.out.println("Checking for no services found in all the Inventory try cleaning in Network");
				// if no services found in Inventory try cleaning in Network
				LinkedHashMap<String, String> servicesMap1 = asri.consolidateServices(service, "1");
				LinkedHashMap<String, String> servicesMap2 = asri.consolidateServices(service, "2");
				LinkedHashMap<String, String> servicesMap4 = asri.consolidateServices(service, "4");
				if (servicesMap1.size() == 0 && servicesMap2.size() == 0 && servicesMap4.size() == 0) {
					System.out.println("No services found in all the Inventory try cleaning in Network");
					for (String eachEnv : envs) {
						boolean actCleanUpStatus = act.networkCleanup(service, eachEnv);
						if (actCleanUpStatus) {
							System.out.println("Act Cleanup is successful");
							boolean asriCleanUpStatus = asri.inventoryCleanUp(service, eachEnv);
							if (asriCleanUpStatus) {
								System.out.println("ASRI Cleanup is successful");
							} else {
								System.out.println("ASRI Cleanup is not successful");
							}
						} else {
							System.out.println("Act Cleanup is not successful");
						}
					}
				} else {
					for (String eachEnv : envs) {
						services = new ArrayList<String>();
						servicesMap = asri.consolidateServices(service, eachEnv);

						if (servicesMap.size() > 0) {

							services = asri.getRearragedServices(servicesMap, eachEnv);
							for (Iterator iterator = services.iterator(); iterator.hasNext();) {

								String s = (String) iterator.next();
								System.out.println("Cleanup started for::" + s);

								boolean actCleanUpStatus = act.networkCleanup(s, eachEnv);
								if (actCleanUpStatus) {
									System.out.println("Act Cleanup is successful");

									// cleaning Ips
									if (s.contains("IRXX")) {
										asri.cleanIp(s);
									}
									boolean asriCleanUpStatus = asri.inventoryCleanUp(s, eachEnv);
									if (asriCleanUpStatus) {
										System.out.println("ASRI Cleanup is successful");
										// update the record in portmonitor db
										if (s.contains("KXFN") && !s.contains("_")) {
											updateRecordAfterCleanup(s);
											storeCleanedUni.add(s);
										}

									} else {
										System.out.println("ASRI Cleanup is not successful");
										break;
									}
								} else {
									System.out.println("Act Cleanup is not successful");
									break;
								}
								servicesMap = asri.consolidateServices(service, eachEnv);
								services = asri.getRearragedServices(servicesMap, eachEnv);

							}
						}
					}
				}
			}
		}
		return storeCleanedUni;

	}

	public static void updatePortMonitorIfUniNotUpdated(String uni) {

		Response response;
		String query = "https://sasi-test1.kubeodc-test.corp.intranet/inventory/v1/asri/services?name=" + uni;
		response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and().when()
				.get(query).then().extract().response();

		int statCode = response.getStatusCode();
		if (statCode == 200) {
			String environment = "Test1";
			System.out.println("UNI: " + uni + " is updated in PortMonitor in Test1");
			getSasiDetails(response, environment);
			triggerUpdateDbApi(getSasiDetails(response, environment));
		} else {
			query = "https://sasi-test2.kubeodc-test.corp.intranet/inventory/v1/asri/services?name=" + uni;
			response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and()
					.when().get(query).then().extract().response();
			statCode = response.getStatusCode();
			if (statCode == 200) {
				String environment = "Test2";
				System.out.println("UNI: " + uni + " is updated in PortMonitor in Test2");
				getSasiDetails(response, environment);
				triggerUpdateDbApi(getSasiDetails(response, environment));
			} else {
				query = "https://sasi-test4.kubeodc-test.corp.intranet/inventory/v1/asri/services?name=" + uni;
				response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and()
						.when().get(query).then().extract().response();
				statCode = response.getStatusCode();
				if (statCode == 200) {
					String environment = "Test4";
					System.out.println("UNI: " + uni + " is updated in PortMonitor in Test4");
					getSasiDetails(response, environment);
					triggerUpdateDbApi(getSasiDetails(response, environment));
				} else {
					System.out.println("UNI: " + uni
							+ " is not updated in PortMonitor\n as it is not found in ASRI Test1, Test2 and Test4");
				}
			}
		}
	}

	public static String getSasiDetails(Response response, String env) {

//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date currentDate = new Date();
//		String formattedDate = formatter.format(currentDate);

		String sasiRes = response.asString();
//		response.prettyPrint();

		ArrayList<String> unialias = JsonPath.read(sasiRes, "$..resources[0].name");

		String environment = env;
		ArrayList<String> createddate = JsonPath.read(sasiRes,
				"$..resources[0].attributes[?(@.name=='CREATEDDATE')].value");

		ArrayList<String> device = JsonPath.read(sasiRes, "$..resources[0].zend.device.name");
		String deviceName;
		if (device.size() > 0) {
			deviceName = device.get(0);
		} else {
			deviceName = "NULL";
		}

		ArrayList<String> portnum = JsonPath.read(sasiRes, "$..resources[0].zend.port.name");
		String portName;
		if (portnum.size() > 0) {
			portName = portnum.get(0);
		} else {
			portName = "NULL";
		}

		String prodType = "UNI";
		String team = "Team_Auto";
		ArrayList<String> orderId = JsonPath.read(sasiRes,
				"$..resources[0].attributes[?(@.name=='EXTERNAL_ORDER_ID')].value");
		String orderIdValue;
		if (orderId.size() > 0) {
			orderIdValue = orderId.get(0);
		} else {
			orderIdValue = "NULL";
		}

		String createdBy = "Jenkins_Auto";
		String user_comment = "CAN BE CLEANED!";

		LocalDate formattedDate = LocalDate.now().plusDays(1);
//		LocalDate formattedDate = LocalDate.now();
		LocalDate cleanup_date = formattedDate;

		String sasiDetails = unialias.get(0) + "," + environment + "," + createddate.get(0).split(" ")[0] + ","
				+ deviceName + "," + portName + "," + prodType + "," + team + "," + orderIdValue + "," + createdBy + ","
				+ user_comment + "," + cleanup_date;

//		for (String s : sasiDetails.split(",")) {
//			System.out.println(s);
//		}

		return sasiDetails;

	}

	// This function is used to trigger the update db api to update the portmonitor
	// db with the sasi details
	public static void triggerUpdateDbApi(String sasiDetails) {
		
		try {
			Response response;
			String query = "https://ndf-test-cleanup.kubeodc-test.corp.intranet/updateUnidetailsInDbFromSoa";
			String jsonBody = "{\r\n" + "    \"unialias\": \"" + sasiDetails.split(",")[0] + "\",\r\n"
					+ "    \"environment\": \"" + sasiDetails.split(",")[1] + "\",\r\n" + "    \"createddate\": \""
					+ sasiDetails.split(",")[2] + "\",\r\n" + "    \"device\": \"" + sasiDetails.split(",")[3] + "\",\r\n"
					+ "    \"portnum\": \"" + sasiDetails.split(",")[4] + "\",\r\n" + "    \"prodType\": \""
					+ sasiDetails.split(",")[5] + "\",\r\n" + "    \"team\": \"" + sasiDetails.split(",")[6] + "\",\r\n"
					+ "    \"orderId\": \"" + sasiDetails.split(",")[7] + "\",\r\n" + "    \"createdBy\": \""
					+ sasiDetails.split(",")[8] + "\",\r\n" + "    \"user_comment\": \"" + sasiDetails.split(",")[9]
					+ "\",\r\n" + "    \"cleanup_date\": \"" + sasiDetails.split(",")[10] + "\"\r\n" + "}";

//	        make a post call to update the db
			response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and()
					.body(jsonBody).when().post(query).then().extract().response();

			int statCode = response.getStatusCode();
			if (statCode == 200) {
				System.out.println("PortMonitor DB updated successfully");
			} else {
				System.out.println("PortMonitor DB not updated");
			}
		} catch (Exception e) {
			System.out.println("Exception in triggerUpdateDbApi::" + e);
			System.out.println("PortMonitor DB not updated by triggerUpdateDbApi");
		}
		
	}

	// This function is used to update the record in portmonitor db after cleanup is
	// done in network and inventory
	public static void updateRecordAfterCleanup(String uni) {

//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date currentDate = new Date();
//		String formattedDate = formatter.format(currentDate);

		try {
			Response response;
			String query = "https://ndf-test-cleanup.kubeodc-test.corp.intranet/getUnidata/" + uni;
			response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and().when()
					.get(query).then().extract().response();

			int statCode = response.getStatusCode();
			System.out.println("Status code: " + statCode);
			String sasiRes = response.asString();
			response.prettyPrint();
//			System.out.println("+++++++----------------------------------------------+++++++++++++");
//			for (String s : sasiRes.split(",")) {
//				System.out.println(s);
//			}

			String ss[] = sasiRes.split(",");
			ss[9] = "\"createdBy\":\"Jenkins_Auto\"";
			ss[10] = "\"user_comment\":\"CLEANED\"";

			LocalDate formattedDate = LocalDate.now();
//			LocalDate formattedDate = LocalDate.now();
			ss[12] = "\"cleanedDate\":\"" + formattedDate + "\"}";
			System.out.println("+++++++----------------------------------------------+++++++++++++");

			String modifiedPayload = "";
			for (String m : ss) {
				System.out.println(m);
				if (!m.contains("cleanedDate")) {
					modifiedPayload = modifiedPayload + m + ",";
				} else {
					modifiedPayload = modifiedPayload + m;
				}

			}

			System.out.println("+++++++----------------------------------------------+++++++++++++");

			System.out.println("PortMonitor DB UPDATE PAYLOAD::\n" + modifiedPayload);

			// update the record
			String updateQuery = "https://ndf-test-cleanup.kubeodc-test.corp.intranet/updateUnidetailsInDb";
			response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and()
					.body(modifiedPayload).when().post(updateQuery).then().extract().response();

			statCode = response.getStatusCode();
			System.out.println("Status code: " + statCode);
			if (statCode == 200 || statCode == 201) {
				System.out.println("PortMonitor DB updated successfully");
			} else {
				System.out.println("PortMonitor DB not updated");
			}
		} catch (Exception e) {
			System.out.println("Exception in updateRecordAfterCleanup::" + e);
			System.out.println("PortMonitor DB not updated after cleanup");
		}

	}
	
	public static void validateAttributesOfUpdatedUni(String uni, String device) {
		String query = "https://ndf-test-cleanup.kubeodc-test.corp.intranet/getUnidata/" + uni;
		Response uniResponse = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json")
				.and().when().get(query).then().extract().response();
		
		String uniRes = uniResponse.asString();
		String unialias = JsonPath.read(uniRes, "$.unialias");
		String environment = JsonPath.read(uniRes, "$.environment");
		String createdDate = JsonPath.read(uniRes, "$.createdDate");
		String deviceName = JsonPath.read(uniRes, "$.device");
		String portNum = JsonPath.read(uniRes, "$.portnum");
		String prodType = JsonPath.read(uniRes, "$.prodType");
		String team = JsonPath.read(uniRes, "$.team");
		String orderId = JsonPath.read(uniRes, "$.orderId");
		String createdBy = JsonPath.read(uniRes, "$.createdBy");
		String userComment = JsonPath.read(uniRes, "$.user_comment");
		String cleanupDate = JsonPath.read(uniRes, "$.cleanup_date");
		String cleanedDate = JsonPath.read(uniRes, "$.cleanedDate");
		
		if (deviceName.equals(device)) {
			System.out.println("Device Name is matching");
		} else {
			System.out.println("Device Name is not matching");
			updatePortMonitorIfUniNotUpdated(uni);
			
			try {
				Response response;
				String query1 = "https://ndf-test-cleanup.kubeodc-test.corp.intranet/getUnidata/" + uni;
				response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json").and().when()
						.get(query1).then().extract().response();

				int statCode = response.getStatusCode();
				System.out.println("Status code: " + statCode);
				String sasiRes = response.asString();
				response.prettyPrint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	public  void deleteRecordFromPortMonitor(String uni) {
		String query = "https://ndf-test-cleanup.kubeodc-test.corp.intranet/deleteRecord/" + uni;
		Response response = RestAssured.given().relaxedHTTPSValidation().header("Content-type", "application/json")
				.and().when().delete(query).then().extract().response();
		int statCode = response.getStatusCode();
		if (statCode == 200) {
			System.out.println("Record deleted successfully from PortMonitor");
		} else {
			System.out.println("Record not deleted from PortMonitor");
		}
	}
}
