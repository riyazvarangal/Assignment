/** 
* <h1>Programming Exercise</h1> 
* Java program to read in a list of employee records from an ascii input file 
* and write out a sorted list to an ascii output file.  
* 
* @author  Riyaz Varangal 
* @version 1.0
* @since   11/04/2019 
*/
package org.task;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import org.task.layout.*;
import org.task.entity.Employee;
import org.task.util.Helper;

public class Main {
	public static void main(String[] args) {
		String input = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("-- Please enter the file path and name of Employee record Input file --");
			System.out.println("-- Press 0+Enter anytime to exit --");
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("IO Exception in reading the input");
			}
			if(input != null && input.equals("0") )
				System.exit(0);
			if(input != null && input.length() > 0) {
				try {
					processInput(input);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			} else
				System.out.println("Invalid Input");
			System.out.println("-- End of Program Execution --");
		}
	}
	public static void processInput(String userInput) throws IOException {
		/* (1) Validate File Path */
		boolean validPath = Helper.isValidPath(userInput);
		if (!validPath) {
			System.out.println("The user supplied File path is invalid");
			return;
		}
		/* (2) Validate file to read for file type .txt	 */
		Optional<String> optional = Helper.getFileExt(userInput);
		if (optional.isPresent()) {
			if (!(optional.get().equalsIgnoreCase(EmpFile.TEXT_FILE_EXRENSION))) {
				System.out.println("The file extension is invalid; only "+EmpFile.TEXT_FILE_EXRENSION+" file is accepted");
				return;
			}
		} else {
			System.out.println("File Extension is not valid. Only "+EmpFile.TEXT_FILE_EXRENSION+" file is expected");
			return;
		}
		/* (3) Check the file code and parse the lines to Employee Objects based on layouts */
		File file = new File(userInput);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		EmpFile efile = null;
		List<Employee> empList=null;
		while ((line = br.readLine()) != null) {
			System.out.println("line.trim()? "+line.trim());
			if (line.trim().equals(efile.FIXED_LENGTH_FORMAT_CODE)) {
				efile = new FixedLengthFile(file);
			} else if (line.trim().equals(efile.TOKEN_SEPARATED_FORMAT_CODE)) {
				efile = new TokenSeparatedFile(file);
			}
			else {
				System.out.println("Invalid file format code; It should be one of these --> "+efile.FIXED_LENGTH_FORMAT_CODE +" or "+efile.TOKEN_SEPARATED_FORMAT_CODE);
				break;
			}
			br.close();
			efile.setFormatCode(line.trim());
			empList = efile.parseFile();
			break;	
		}
		/* (4) Sort Employee Objects and Print to output file */
		if(null != empList && empList.size() > 0) {
			Collections.sort(empList);
			StringBuffer fileContent = new StringBuffer();
			int index = 1;
			String outFilePath = file.getAbsoluteFile().getParent()+File.separator+efile.OUTPUT_FILE_PREFIX+efile.getFormatCode()+"."+efile.TEXT_FILE_EXRENSION;
			PrintWriter out = new PrintWriter(new FileWriter(outFilePath));
			for(Employee employee: empList) {
				out.println(index);
				out.println(" "+employee.getFirstName()+" "+employee.getLastName()+", "+"(" +new SimpleDateFormat("MM/dd/YYYY").format(employee.getStartDate())+")");
				out.println(" "+employee.getAddress1()+", "+employee.getAddress2());
				out.println(" "+employee.getCity()+", "+employee.getState());
				out.println(" "+employee.getCountry()+", "+employee.getZip());
				++index;
			}
			out.close();
			System.out.println("Formatted Employee output file path: "+outFilePath);
		}
	}
}
