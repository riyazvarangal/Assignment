/** 
* <h1>Helper Class</h1> 
* Java program to get the file extension and 
* to validate File Path.  
* 
* @author  Riyaz Varangal 
* @version 1.0
* @since   11/04/2019 
*/
package org.task.util;

import java.io.File;
import java.net.URI;
import java.util.Optional;

public class Helper {
	public static Optional<String> getFileExt(String p) {
		try {
			File file = new File(p);
			if(file.exists()) {
				if(p.contains("."))
					return Optional.ofNullable(p.substring(p.lastIndexOf(".")+1));
					else
						return Optional.empty();
			}
			else
				return Optional.empty();
		} catch(Exception ex) {
			return Optional.empty();
		}
	}
	public static boolean isValidPath(String p) {
		try {
			File file = new File(p);
			 return file.exists();
		} catch (Exception ex) {
			return false;
		}
	}

}
