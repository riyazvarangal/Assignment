/** 
* <h1>Employee Java Object</h1> 
* Java program to read in a list of employee records from an ascii input file 
* and write out a sorted list to an ascii output file.  
* 
* @author  Riyaz Varangal 
* @version 1.0
* @since   11/04/2019 
*/
package org.task.layout;

import java.io.IOException;
import java.util.List;

import org.task.entity.Employee;

public abstract class EmpFile {
	public static final String FIXED_LENGTH_FORMAT_CODE = "1";
	public static final String TOKEN_SEPARATED_FORMAT_CODE = "2";
	public static final String TEXT_FILE_EXRENSION = "txt";
	public static final String OUTPUT_FILE_PREFIX = "EmpRecord";
	public List<Employee> empList;
	public String formatCode;
	
	public static final Integer FNAME_ORDER = 0;
	public static final Integer LNAME_ORDER  = 1;
	public static final Integer START_DATE_ORDER = 2;
	public static final Integer ADDRESS_1_ORDER  = 3;
	public static final Integer ADDRESS_2_ORDER  = 4;
	public static final Integer CITY_ORDER       = 5;
	public static final Integer STATE_ORDER      = 6;
	public static final Integer COUNTRY_ORDER    = 7;
	public static final Integer ZIP_ORDER        = 8;
	
	public abstract List<Employee> parseFile() throws IOException;
	 
	public String getFormatCode() {
		return formatCode;
	}
	 
	public void setFormatCode(String formatCode) {
		this.formatCode= formatCode;
	}
}
