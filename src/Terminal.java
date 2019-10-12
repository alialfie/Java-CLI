import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
	String defaultDir;
	String path;

	public Terminal() {
		defaultDir = "C:/";
		path = defaultDir;
		start();
	}
	
	public void start() {
		Scanner sc = new Scanner(System.in);
		Parser parser = new Parser();
		String cmdLine;
		String cmd;
		while(true) {
			System.out.println(path);
			cmdLine = sc.nextLine();
			if(parser.parse(cmdLine)) {
				cmd = parser.getCmd();
				
				switch(cmd) {
				case "cd":
					if(parser.getArgs().size() != 0) {
						cd(parser.getArgs().get(0));
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
					
				case "mv":
					mv(parser.getArgs());
					break;
					
				case "exit":
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
		File file;
		for(int i=0; i<args.size(); i++) {
			currentPath = this.path + args.get(i);
			file = new File(currentPath);
			if(file.isDirectory() && file.list().length==0) {
				 file.delete();
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
}