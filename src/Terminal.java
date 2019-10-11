import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Terminal {

	public void date() {
		Date date = new Date();
		SimpleDateFormat aDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		System.out.println(aDate.format(date));
		}
	
	public void pwd() {
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
	}
	
	public void mkdir(String dirName) {
		boolean exists =  new File(dirName).mkdir();    
	        if(!exists)
	            System.out.println("a folder with that name already exists!");		
		
	}
	
	public void ls() {
		
	}
}
