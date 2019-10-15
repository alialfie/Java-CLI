import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

public class Terminal {
	String defaultDir;
	String path;

	public Terminal() {
		defaultDir = "C:/";
		path = defaultDir;
	}
	
	public void start() {
		Scanner sc = new Scanner(System.in);
		Parser parser = new Parser();
		String cmdLine;
		String cmd;
		while(true) {
			cmdLine = sc.nextLine();
			if(parser.parse(cmdLine)) {
				cmd = parser.getCmd();
				
				switch(cmd) {
				case "cd":
					if(parser.getArgs().size() != 0) {
						cd((String) parser.getArgs().get(0));
					}else {
						cd("");
					}
					break;
					
				case "pwd":
					pwd();
					break;
					
				case "rmdir":
					rmdir(parser.getArgs());
					break;
				
				case "ls":
					ls();
					break;	
				
				case "mkdir":
					mkdir(parser.getArgs());
					break;
				
				case "date":
					date();
					break;
				
				case "cat":
					cat(parser.getArgs());
					break;
				
				case "help":
					help();
					break;
					
				case "cp":
					cp(parser.getArgs());
					break;
				
				case "mv":
					mv(parser.getArgs());
					break;
					
				case "exit":
					sc.close();
					return;
				}
				
			}else {
				System.out.println("invalid command or arguments");
			}
		}
	}
	
	public void cd(String path) {
		String backupPath = this.path;
		if(path.equals("")) {
			this.path = this.defaultDir;
		}else if(path.length() >= 3 && path.charAt(1) == ':') {
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
	
	public void pwd() {
		System.out.println(path);
	}
	
	public void rmdir(ArrayList<String> args) {
		String currentPath;
		for(int i=0; i<args.size(); i++) {
			File fp = new File (args.get(i));
			if (!args.get(i).equalsIgnoreCase(fp.getAbsolutePath())) {
			currentPath = this.path + args.get(i);
			}
			else {
				currentPath = args.get(i);
			}
			fp = new File(currentPath);
	        if(fp.isDirectory()) {
	            fp.delete();	
	        	}	
	        else {
	        	System.out.println("directory does not exist");
	        }
	   }
	}
	
	public void mv(ArrayList<String> args) {
		String destination = this.path + args.get(args.size()-1);
		File destFile = new File(destination);
		if(destFile.isDirectory()) {
			String currentPath;
			File currentFile;
			for(int i=0; i<args.size()-1; i++) {
				currentPath = this.path + args.get(i);
				currentFile = new File(currentPath);
				if(currentFile.exists()) {
					try {
						Files.move(Paths.get(currentPath), 
								Paths.get(destination + "/" + currentFile.getName()), 
								StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
					    //moving file failed.
					    e.printStackTrace();
					}
				}
			}
		}else if(destFile.isFile()) {
			String currentPath;
			File currentFile;
			for(int i=0; i<args.size()-1; i++) {
				currentPath = this.path + args.get(i);
				currentFile = new File(currentPath);
				if(currentFile.isFile()) {
					try {
						Files.move(Paths.get(currentPath), 
								Paths.get(destination), 
								StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
					    //moving file failed.
					    e.printStackTrace();
					}
				}else {
					System.out.println(currentPath + " is not a file while destination is a file");
				}
			}
		}else {		//file doesn't exist
			String currentPath;
			File currentFile;
			for(int i=0; i<args.size()-1; i++) {
				currentPath = this.path + args.get(i);
				currentFile = new File(currentPath);
				
				String extension = "";
				int x = currentPath.lastIndexOf('.');
				if (x >= 0) {
				    extension = currentPath.substring(x+1);
				}
				
				if(currentFile.exists()) {
					if(extension.length() == 0) {
						currentFile.renameTo(destFile);
					}else {
						currentFile.renameTo(new File(destination + "." + extension));
					}
				}
			}
		}
	}
	
////////////////////////////////////////////////////////////////////////////////

	public void date() {
		Date date = new Date();
		SimpleDateFormat aDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		System.out.println(aDate.format(date));
		}
	
	public void mkdir(ArrayList<String> args) {
		String currentPath;
		for(int i=0; i<args.size(); i++) {
			File fp = new File (args.get(i));
			if (!args.get(i).equalsIgnoreCase(fp.getAbsolutePath())) {
			currentPath = this.path + args.get(i);
			}
			else {
				currentPath = args.get(i);
			}
			fp = new File(currentPath);
	        if(!fp.mkdir()) {
	            System.out.println("a folder with that name already exists!");	
	        	}		
	        }
		
	}

	public void ls() {
		String[] files;
		File file = new File (path);
		files = file.list();
		Arrays.sort(files,String.CASE_INSENSITIVE_ORDER);
		for (String filenames : files) {
			System.out.println(filenames);
		}

	}
	
	
	public void cat(ArrayList<String> args) {
		String currentPath;
		for (int i=0; i<args.size(); i++) {
		try {
			File fp = new File (args.get(i));
			if (!args.get(i).equalsIgnoreCase(fp.getAbsolutePath())) {
			currentPath = this.path + args.get(i);
			}
			else {
				currentPath = args.get(i);
			}
			Scanner freader = new Scanner(new File(currentPath));
			while (freader.hasNext()) {
				String fdata = freader.next();
				System.out.println(fdata);
			}
			freader.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		}
	}
	
	
	public void cp(ArrayList<String> args) {
		String currentPath;
		//could be changed to copy any number of files into a directory
		if (args.size() == 3) {
			//checking if the last argument is a directory.
			File fp = new File (args.get(2));
			
			if (!args.get(2).equalsIgnoreCase(fp.getAbsolutePath())) {
			currentPath = this.path + args.get(2);
			}
			else {
				currentPath = args.get(2);
			}
			
			fp = new File(currentPath);
			
			if (!fp.isDirectory()) {
				System.out.println("there is no directory with the given name");
			}
			
			else {
				ArrayList[] files;
				
				for(int i=0 ; i<2; i++) {
					//if else for long and short paths
					if (!args.get(i).equalsIgnoreCase(fp.getAbsolutePath())) {
						currentPath = this.path + args.get(i);
						}
						else {
							currentPath = args.get(i);
						}
					
				File file= new File(currentPath);
				
				try {
					FileUtils.copyFileToDirectory(file, fp);
				} catch (IOException e) {
					System.out.println("a file you wrote does not exist");
				}
				}
				
			}
		}
		
		//copying a file onto another file as written in the command in the lab; can be changed to copy several files into one.
		else if(args.size() == 2) {
			//checking if the second path provided is to a file and not a directory
			File fp=new File(args.get(1));
			
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
			currentPath = this.path + args.get(1);
			}
			else {
				currentPath = args.get(1);
			}
			
			fp = new File(currentPath);
			
			if (!fp.isFile()) {
				System.out.println("there is no file with the given name");
			}
			
			else {
				if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
					currentPath = this.path + args.get(0);
					}
					else {
						currentPath = args.get(0);
					}
				
				File file=new File(currentPath);
				
				try {
					FileUtils.copyFile(file, fp);
				} catch (IOException e) {
					System.out.println("a file you entered does not exist");
				}
			}
			
		}
		
	}
	
	public void help() {
		System.out.println("cd: This command changes the current directory to another one. arguments: directoryname");
		System.out.println("ls: list names of all files and directories in the current working directory");
		System.out.println("cp: copies a file's contents into a new file that has been created by it or copies files into a certain directory. arguments: filename filename or filename filename directoryname");
		System.out.println("cat: concatenates files and outputs their content to the console/anotherfile. arguments: filename(s)");
		System.out.println("more: lets us display the output line by line or page by page. arguments: filename");
		System.out.println("mkdir: makes directories of given names in current directory or in a specified path. arguments: directoryname(s) or path(s)/dirname(s)");
		System.out.println("rmdir: removes directories of given names in current directory or in a specified path. arguments: directoryname(s) or path(s)/dirname(s)");
		System.out.println("mv: moves each given file into a the last given file with the same name in a specified directory. arguments: filenames/paths");
		System.out.println("rm: removes each specified file. arrguments: filename(s)");
		System.out.println("args: list all command arguments");
		System.out.println("date: current date/time");
		System.out.println("pwd: shows current working directory");
		System.out.println("clear: clears the current terminal screen ");
		System.out.println("exit: Stop all");
	}
	
} 