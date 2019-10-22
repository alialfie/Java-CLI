import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
	
	public void start() throws IOException {
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
					pwd(parser.getArgs());
					break;
					
				case "rmdir":
					rmdir(parser.getArgs());
					break;
				
				case "ls":
					ls(parser.getArgs());
					break;	
				
				case "mkdir":
					mkdir(parser.getArgs());
					break;
				
				case "date":
					date(parser.getArgs());
					break;
				
				case "cat":
					cat(parser.getArgs());
					break;
				
				case "help":
					help(parser.getArgs());
					break;
					
				case "cp":
					cp(parser.getArgs());
					break;
				
				case "mv":
					mv(parser.getArgs());
					break;

				case "more":
					more(parser.getArgs());
					break;

				case "args":
					args(parser.getArgs());
					break;

					
				case "rm":
					rm(parser.getArgs());
					break;
					
				case "clear":
					clear();
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
	
	public void pwd(ArrayList<String> args) throws IOException {
		if (args.isEmpty()) {
			System.out.println(path);
			return;
		}
		else if (args.get(0).compareTo(">>") == 0) {
			File fp = new File(args.get(1));
			FileWriter fileWriter;
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
				fileWriter = new FileWriter(path + args.get(1), true);
				}
				else {
				fileWriter = new FileWriter(args.get(1), true);
				}
			fileWriter.write(path);
			fileWriter.close();
		} else if(args.get(0).compareTo(">") == 0){
			File fp = new File(args.get(1));
			FileWriter fileWriter;
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
				fileWriter = new FileWriter(path + args.get(1), false);
				}
				else {
				fileWriter = new FileWriter(args.get(1), false);
				}
			fileWriter.write(path);
			fileWriter.close();
		}
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

	public void date(ArrayList<String> args) throws IOException {
		Date date = new Date();
		SimpleDateFormat aDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		if(args.size() == 0) {
			System.out.println(aDate.format(date));
		}
		else if (args.get(0).compareTo(">>") == 0) {
			File fp = new File(args.get(1));
			FileWriter fileWriter;
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
				fileWriter = new FileWriter(path + args.get(1), true);
				}
				else {
				fileWriter = new FileWriter(args.get(1), true);
				}
			fileWriter.write(aDate.format(date));
			fileWriter.close();
			return;
		} else if (args.get(0).compareTo(">") == 0) {
			File fp = new File(args.get(1));
			FileWriter fileWriter;
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
				fileWriter = new FileWriter(path + args.get(1), false);
				}
				else {
				fileWriter = new FileWriter(args.get(1), false);
				}
			fileWriter.write(aDate.format(date));
			fileWriter.close();
			return;
		} 
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

	public void ls(ArrayList<String> args) throws IOException {
		if (args.size() == 0) {
		String[] files;
		File file = new File (path);
		files = file.list();
		Arrays.sort(files,String.CASE_INSENSITIVE_ORDER);
		for (String filenames : files) {
			System.out.println(filenames);
			}
		}
		else if (args.get(0).compareTo(">>") == 0) {
			String[] files;
			File file = new File(path);
			files = file.list();
			File fp = new File(args.get(1));
			FileWriter fileWriter;
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
				fileWriter = new FileWriter(path + args.get(1), true);
				}
				else {
				fileWriter = new FileWriter(args.get(1), true);
				}
			Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
			for (String filenames : files) {
				fileWriter.write(filenames);
				fileWriter.write("\n");
			}
			fileWriter.close();
		}
		// **************************************************************************************
		else if (args.get(0).compareTo(">") == 0) {
			String[] files;
			File file = new File(path);
			files = file.list();
			File fp = new File(args.get(1));
			FileWriter fileWriter;
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
				fileWriter = new FileWriter(path + args.get(1), false);
				}
				else {
				fileWriter = new FileWriter(args.get(1), false);
				}
			Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
			for (String filenames : files) {
				fileWriter.write(filenames);
				fileWriter.write("\n");
			}
			fileWriter.close();
		}
		else if (args.size()==2 && args.get(0).equalsIgnoreCase("|") && args.get(1).equalsIgnoreCase("more")) {
			Scanner kb = new Scanner (System.in);
			int nOl = 0;
			int sTop = 10;
			String sMore;
			String[] files;
			File file = new File (path);
			files = file.list();
			Arrays.sort(files,String.CASE_INSENSITIVE_ORDER);
			for (String filenames : files) {
				System.out.println(filenames);
				nOl++;
				if (nOl == sTop) {
					System.out.println("\ndo you want to show more data y/n?");
					sMore = kb.next();
					if(sMore.matches("y")){
						sTop+=10;
						continue;
					}
					else if(sMore.matches("n")) {
						break;
					}
				}
				}
		}

	}
	
	
	public void cat(ArrayList<String> args) throws IOException {

		String currentPath;
		// ***********************************************************************************
				if (args.size() > 2) {
					String fdata;
					if (args.get(1).compareTo(">>") == 0) {
						File fp = new File(args.get(0));
						Scanner freader;
						FileWriter fileWriter;
						if (!args.get(0).equalsIgnoreCase(fp.getAbsolutePath())) {
							freader = new Scanner(new File(path + args.get(0)));
							fileWriter = new FileWriter(path + args.get(2), true);
							}
							else {
							freader = new Scanner(new File(args.get(0)));
							fileWriter = new FileWriter(args.get(2), true);
							}

						while (freader.hasNext()) {
							fdata = freader.next();
							fileWriter.write(fdata);
							fileWriter.write("\n");
						}
						freader.close();
						fileWriter.close();
						return;
					}
					if (args.get(1).compareTo(">") == 0) {
						File fp = new File(args.get(0));
						Scanner freader;
						FileWriter fileWriter;
						if (!args.get(0).equalsIgnoreCase(fp.getAbsolutePath())) {
							freader = new Scanner(new File(path + args.get(0)));
							fileWriter = new FileWriter(path + args.get(2), false);
							}
							else {
							freader = new Scanner(new File(args.get(0)));
							fileWriter = new FileWriter(args.get(2), false);
							}

						while (freader.hasNext()) {
							fdata = freader.next();
							fileWriter.write(fdata);
							fileWriter.write("\n");
						}
						freader.close();
						fileWriter.close();
						return;
					}
					if (args.get(1).compareTo("|") == 0 && args.get(2).compareTo("more") == 0) {
						File fp = new File(args.get(0));int numOflines=1,j=0;
						String Continue;Scanner sc = new Scanner(System.in);
						Scanner freader;
						
						if (!args.get(0).equalsIgnoreCase(fp.getAbsolutePath())) {
							freader = new Scanner(new File(path + args.get(0)));
							}
							else {
							freader = new Scanner(new File(args.get(0)));
							}
						
						while (freader.hasNext()) {
							fdata = freader.next();
							System.out.println(fdata);
							j++;
							if (j == numOflines * 10) {
								System.out.println("if you want countinue press y");
								Continue = sc.nextLine();
								if (Continue.matches("y")) {
									numOflines++;
								} else {
									break;
								}
							}
						}
						freader.close();
						return;
					}
				}
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
		if (args.size() >= 3) {
			//checking if the last argument is a directory.
			File fp = new File (args.get(args.size()-1));
			
			if (!args.get(args.size()-1).equalsIgnoreCase(fp.getAbsolutePath())) {
			currentPath = this.path + args.get(args.size()-1);
			}
			else {
				currentPath = args.get(args.size()-1);
			}
			
			fp = new File(currentPath);
			
			if (!fp.isDirectory()) {
				System.out.println("there is no directory with the given name");
			}
			
			else {	
				for(int i=0 ; i<args.size()-1; i++) {
					//if else for long and short paths
					if (!args.get(args.size()-1).equalsIgnoreCase(fp.getAbsolutePath())) {
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
	
	
	public void help(ArrayList<String> args) throws IOException {
		String helpMessage = "cd: This command changes the current directory to another one. arguments: directoryname \n"
				+ " ls: list names of all files and directories in the current working directory\n"
				+ "cp: copies a file's contents into a new file that has been created by it or copies files into a certain directory. arguments: filename filename or filename filename directoryname"
				+ "\ncat: concatenates files and outputs their content to the console/anotherfile. arguments: filename(s)"
				+ "\nmore: lets us display the output line by line or page by page. arguments: filename"
				+ "\nmkdir: makes directories of given names in current directory or in a specified path. arguments: directoryname(s) or path(s)/dirname(s)"
				+ "\nrmdir: removes directories of given names in current directory or in a specified path. arguments: directoryname(s) or path(s)/dirname(s)"
				+ "\nmv: moves each given file into a the last given file with the same name in a specified directory. arguments: filenames/paths"
				+ "\nrm: removes each specified file. arrguments: filename(s)" + "\nargs: list all command arguments"
				+ "\ndate: current date/time" + "\npwd: shows current working directory"
				+ "\nclear: clears the current terminal screen " + "\nexit: Stop all";
		if (args.isEmpty()) {
			System.out.println(helpMessage);
			return;
		}
		
		else if (args.get(0).compareTo(">>") == 0) {
			FileWriter fileWriter;
			File fp=new File(args.get(1));
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
				fileWriter = new FileWriter(path + args.get(1), true);
				}
				else {
				fileWriter = new FileWriter(args.get(1), true);
				}

			fileWriter.write(helpMessage);
			fileWriter.close();
			return;
		}
		
		else if (args.get(0).compareTo(">") == 0) {
			FileWriter fileWriter;
			File fp=new File(args.get(1));
			if (!args.get(1).equalsIgnoreCase(fp.getAbsolutePath())) {
				fileWriter = new FileWriter(path + args.get(1), false);
				}
				else {
				fileWriter = new FileWriter(args.get(1), false);
				}
			fileWriter.write(helpMessage);
			fileWriter.close();
			return;
		}

	}

	public void rm(ArrayList<String> args) {
		File file = new File( args.get(0));

		if (!file.delete()) {
			System.out.println(args.get(0) + " does not exist");
		}

	}
	
	public void more(ArrayList<String> args) throws IOException {
		int numOflines = 1;
		String Continue;
		Scanner sc = new Scanner(System.in);
		if (args.get(0).compareTo("ls") == 0) {
			String[] files;
			File file = new File(path);
			files = file.list();
			Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);

			for (int i = 0; i < files.length; i++) {
				String filenames = files[i];
				System.out.println(filenames);
				if (i == numOflines * 10) {
					System.out.println("if you want countinue press y");
					Continue = sc.nextLine();
					if (Continue.matches("y")) {
						numOflines++;
					} else {
						break;
					}

				}
			}

		} else if (args.get(0).compareTo("cat") == 0) {
			String currentPath;
			for (int i = 0; i < args.size(); i++) {
				try {
					File fp = new File(args.get(i));
					if (!args.get(i).equalsIgnoreCase(fp.getAbsolutePath())) {
						currentPath = this.path + args.get(i);
					} else {
						currentPath = args.get(i);
					}
					Scanner freader = new Scanner(new File(currentPath));
					int j = 0;
					while (freader.hasNext()) {
						String fdata = freader.next();
						System.out.println(fdata);
						j++;
						if (j == numOflines * 10) {
							System.out.println("if you want countinue press y");
							Continue = sc.nextLine();
							if (Continue.matches("y")) {
								numOflines++;
							} else {
								break;
							}
						}
					}
					freader.close();
				} catch (FileNotFoundException e) {
					System.out.println("file not found");
				}
			}
		}
	}
	
	
	public void args(ArrayList<String> args) {
		switch(args.get(0)) {
		case "cp":
			System.out.println("sourcePath destinationPath");
			break;
			
		case "cd":
			System.out.println("[path]");
			break;
			
		case "ls":
			System.out.println("no arguments");
			break;
			
		case "cat":
			System.out.println("file [file..]");
			break;
			
		case "more":
			System.out.println("no argments");
			break;
			
		case "mkdir":
			System.out.println("directory [directory ...]");
			break;
			
		case "rmdir":
			System.out.println("directory ...");
			break;
			
		case "mv":
			System.out.println("source dest");
			break;
			
		case "rm":
			System.out.println("file ...");
			break;
			
		default:
			System.out.println("unknown command");
		}
	}

	public void clear() {
		for (int i = 0; i < 5000; i++) {
			System.out.println("\n");
		}
	}
	
} 