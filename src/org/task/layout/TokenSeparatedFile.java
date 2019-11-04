/** 
* <h1>TokenSperatedFile</h1> 
* Java program to and Parse file with delimiter.  
* 
* @author  Riyaz Varangal 
* @version 1.0
* @since   11/04/2019 
*/
package org.task.layout;

import java.io.*;
import java.util.*;

import org.task.entity.Employee;

public class TokenSeparatedFile extends EmpFile {
	private File file;
	private String delim = ",";
	private List<Employee> empList;
	
	public TokenSeparatedFile(File file) {
		this.file = file;
	}
	
	public String getDelimiter() {
		return delim;
	}

	public void setDelimiter(String delimiter) {
		this.delim = delimiter;
	}

	public List<Employee> parseFile() throws IOException{
		empList = new ArrayList<Employee>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		Employee emp;
		while ((line = br.readLine()) != null) {
			if (line.trim().equals(EmpFile.TOKEN_SEPARATED_FORMAT_CODE)) 
				continue;
			emp = null;
			try {
				String[] record = line.split(delim);
				String fname = record[FNAME_ORDER];
				String lname = record[LNAME_ORDER];
				String startDt = record[START_DATE_ORDER];
				String add1 = record[ADDRESS_1_ORDER];
				String add2 = record[ADDRESS_2_ORDER];
				String city   = record[CITY_ORDER];
				String state = record[STATE_ORDER];
				String country = record[COUNTRY_ORDER];
				String zip = record[ZIP_ORDER];
				
				emp = new Employee();
				if(fname.trim().length() > 0)
					emp.setFirstName(fname.trim());
				else {
					System.out.println("Current File Record does not have First Name --> "+ line);
					continue;
				}
				if(lname.trim().length() > 0)
					emp.setLastName(lname.trim());
				else {
					System.out.println("Current File Record does not have Last Name --> "+ line);
					continue;
				}
				if(startDt.trim().length() > 0) {
					try {
						/* Converting the start Date from YYYYMMDD to MM/DD/YYYY */
						startDt = startDt.trim();
						Calendar cal = Calendar.getInstance();
						cal.set(Integer.valueOf(startDt.substring(0, 4)), Integer.valueOf(startDt.substring(4, 6))-1, Integer.valueOf(startDt.substring(6, 8)), 0, 0, 0);
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
				
				emp.setAddress1(add1.trim());
				emp.setAddress2(add2.trim());
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
