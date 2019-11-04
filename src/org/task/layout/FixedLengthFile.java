/** 
* <h1>FixedLengthFile</h1> 
* Java program to read in a list of employee records from an ascii input file 
* and write out a sorted list to an ascii output file.  
* 
* @author  Riyaz Varangal 
* @version 1.0
* @since   11/04/2019 
*/
package org.task.layout;

import java.io.*;
import java.util.*;

import org.task.entity.Employee;
public class FixedLengthFile extends EmpFile {
     
	private int recLength = 80;
	private File file;
	
	private static final Integer FNAME_LENGTH = 10;
	private static final Integer LNAME_LENGTH  = 17;
	private static final Integer START_DATE_LENGTH = 8;
	private static final Integer ADDRESS_1_LENGTH  = 10;
	private static final Integer ADDRESS_2_LENGTH  = 10;
	private static final Integer CITY_LENGTH       = 10;
	private static final Integer STATE_LENGTH      = 2;
	private static final Integer COUNTRY_LENGTH    = 3;
	private static final Integer ZIP_LENGTH        = 10;
	
	public Integer calcEleBeginIndex(String element) {
		Map<String, Integer> eleLengthMap = getElementLengthMap();
		List<String> eleOrderList = getElementList();
		Integer beginIndex = 0;
		Integer index = eleOrderList.indexOf(element);
		for(int i = 0; i< index; i++) {
			beginIndex = beginIndex + eleLengthMap.get(eleOrderList.get(i));
		}
		
		return beginIndex;
	}
	
	public FixedLengthFile(File file) {
	 this.file = file;	
	}

	public int getRecordLength() {
		return recLength;
	}

	public void setRecordLength(int recordLength) {
		this.recLength = recordLength;
	}
	
	public Map<String, Integer> getElementLengthMap(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(Employee.FIRST_NAME, FNAME_LENGTH);
		map.put(Employee.LAST_NAME, LNAME_LENGTH);
		map.put(Employee.START_DATE, START_DATE_LENGTH);
		map.put(Employee.ADDRESS_1, ADDRESS_1_LENGTH);
		map.put(Employee.ADDRESS_2, ADDRESS_2_LENGTH);
		map.put(Employee.CITY, CITY_LENGTH);
		map.put(Employee.STATE, STATE_LENGTH);
		map.put(Employee.COUNTRY, COUNTRY_LENGTH);
		map.put(Employee.ZIP, ZIP_LENGTH);
		return map;
	}
	private List<String> getElementList(){
		List<String> list = new ArrayList<String>();
		list.add(FNAME_ORDER,Employee.FIRST_NAME);
		list.add(LNAME_ORDER,Employee.LAST_NAME);
		list.add(START_DATE_ORDER,Employee.START_DATE);
		list.add(ADDRESS_1_ORDER,Employee.ADDRESS_1);
		list.add(ADDRESS_2_ORDER,Employee.ADDRESS_2);
		list.add(CITY_ORDER,Employee.CITY);
		list.add(STATE_ORDER,Employee.STATE);
		list.add(COUNTRY_ORDER, Employee.COUNTRY);
		list.add(ZIP_ORDER,Employee.ZIP);
		return list;
	}
	
	@Override
	public List<Employee> parseFile() throws IOException{
		empList = new ArrayList<Employee>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		Employee emp;
		Map<String, Integer> eleLengthMap = getElementLengthMap();
		List<String> elementOrderList = getElementList();
		while ((line = br.readLine()) != null) {
			if (line.trim().equals(EmpFile.FIXED_LENGTH_FORMAT_CODE)) 
				continue;
			if(line.length() != recLength) {
				System.out.println("Current File Record is not in the expected record length  "+recLength+" --> "+line);
				continue;
			}
			emp = null;
			try {
				String firstName = line.substring(calcEleBeginIndex(Employee.FIRST_NAME), calcEleBeginIndex(Employee.FIRST_NAME)+eleLengthMap.get(Employee.FIRST_NAME));
				String lastName = line.substring(calcEleBeginIndex(Employee.LAST_NAME), calcEleBeginIndex(Employee.LAST_NAME)+eleLengthMap.get(Employee.LAST_NAME));
				String startDate = line.substring(calcEleBeginIndex(Employee.START_DATE), calcEleBeginIndex(Employee.START_DATE)+eleLengthMap.get(Employee.START_DATE));
				String address1 = line.substring(calcEleBeginIndex(Employee.ADDRESS_1), calcEleBeginIndex(Employee.ADDRESS_1)+eleLengthMap.get(Employee.ADDRESS_1));
				String address2 = line.substring(calcEleBeginIndex(Employee.ADDRESS_2), calcEleBeginIndex(Employee.ADDRESS_2)+eleLengthMap.get(Employee.ADDRESS_2));
				String city = line.substring(calcEleBeginIndex(Employee.CITY), calcEleBeginIndex(Employee.CITY)+eleLengthMap.get(Employee.CITY));
				String state = line.substring(calcEleBeginIndex(Employee.STATE), calcEleBeginIndex(Employee.STATE)+eleLengthMap.get(Employee.STATE));
				String country = line.substring(calcEleBeginIndex(Employee.COUNTRY), calcEleBeginIndex(Employee.COUNTRY)+eleLengthMap.get(Employee.COUNTRY));
				String zip = line.substring(calcEleBeginIndex(Employee.ZIP), calcEleBeginIndex(Employee.ZIP)+eleLengthMap.get(Employee.ZIP));
				
				emp = new Employee();
				if(firstName.trim().length() > 0)
					emp.setFirstName(firstName.trim());
				else {
					System.out.println("Current File Record does not have First Name --> "+ line);
					continue;
				}
				if(lastName.trim().length() > 0)
					emp.setLastName(lastName.trim());
				else {
					System.out.println("Current File Record does not have Last Name --> "+ line);
					continue;
				}
				if(startDate.trim().length() > 0) {
					try {
						/* Converting start Date format from YYYYMMDD to MM/DD/YYYY	*/
						startDate = startDate.trim();
						Calendar cal = Calendar.getInstance();
						cal.set(Integer.valueOf(startDate.substring(0, 4)), Integer.valueOf(startDate.substring(4, 6))-1, Integer.valueOf(startDate.substring(6, 8)), 0, 0, 0);
					    emp.setStartDate(cal.getTime());
					}
					catch(Exception e) {
						System.out.println("Current File Record has incorrect Date format --> "+ line);
						continue;
					}
				}
				else {
					System.out.println("Current File Record does not have Start Date --> "+ line);
					continue;
				}
				
				emp.setAddress1(address1.trim());
				emp.setAddress2(address2.trim());
				emp.setCity(city.trim());
				if(state.trim().length() > 0)
					emp.setState(state);
				if(country.trim().length() > 0)
					emp.setCountry(country);
				if(zip.trim().length() > 0) {
					try {
					emp.setZip(Integer.valueOf(zip.trim()));
					}
					catch(Exception e) {
						System.out.println("Current File Record has incorrect Zip format --> "+ line);
						continue;
					}
				}
			}
			catch(Exception e) {
				System.out.println("Current File Record is not in the expected layout --> "+ line);
			}
			if(null != emp)
			   empList.add(emp);
		}
		return empList;
	}
	
	

}
