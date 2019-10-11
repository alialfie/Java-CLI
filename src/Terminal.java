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

	String defaultDir;
	String path;

	public Terminal() {
		defaultDir = "C:/";
		path = defaultDir;
	}
	
	public void date() {
		Date date = new Date();
		SimpleDateFormat aDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		System.out.println(aDate.format(date));
		}
	
	//public void pwd() {
	//	String currentDir = System.getProperty("user.dir");
	//	System.out.println(currentDir);
	//}
	
	public void cd(String path) {
		String backupPath = this.path;
		if(path.equals("")) {
			this.path = this.defaultDir;
		}else if(path.charAt(1) == ':') {
			this.path = path;
		}else if(path.charAt(0) != '/') {
			this.path = this.path + path;
		}else if(path.charAt(0) == '/') {
			this.path = "C:" + path;
		}
		
		if(this.path.charAt(this.path.length()-1) != '/') {
			this.path += "/";
		}
		
		File f = new File(this.path);
		if(!f.isDirectory()) {
			System.out.println("invalid directory");
			this.path = backupPath;
		}
	}
	
	
	public void mkdir(String dirName) {
		boolean exists =  new File(dirName).mkdir();    
	        if(!exists)
	            System.out.println("a folder with that name already exists!");		
		
	}
	
	public void ls() {
		
	}
	

	public void pwd() {
		System.out.println(path);
	}
}
