import java.util.*;

public class Parser {
	String cmd;
	ArrayList<String> args;
	ArrayList<Command> validCommands;
	
	public Parser() {
		cmd = "";
		args = new ArrayList<String>();
		
		validCommands = new ArrayList<Command>() {
			{
				add(new Command("cd", 0, 1));
				add(new Command("mv", 2, -1));
				add(new Command("rmdir", 1, -1));
				add(new Command("mkdir", 1, -1));
				add(new Command("pwd", 0, 0));
				add(new Command("date", 0, 0));
				add(new Command("ls", 0, 0));
				add(new Command("cat", 1, -1));
				add(new Command("cp", 2, 3));
				add(new Command("help", 0, 0));
				add(new Command("exit", 0, 0));
			}
		};
	}

	public boolean parse(String input) {
		cmd = "";
		args = new ArrayList<String>();
		
		int length = input.length(), i = 0;
		while(input.charAt(i) != ' ' && length != 0) {	//gets the command line
			cmd += input.charAt(i);
			i++;
			if(i >= length) break;	//in case the index is out of range
		}
		
		i++;	//skip the space
		
		while(i < length) {		//gets the arguments
			String arg = "";
			while(input.charAt(i) != ' ') {
				arg += input.charAt(i);
				i++;
				if(i >= length) break;	//in case the index is out of range
			}
			args.add(arg);
			i++;	//skip the space
		}
		
		return validateCommand();
	}
	
	private boolean validateCommand() {
		Iterator<Command> iterator = validCommands.iterator();
		Command current;
		while(iterator.hasNext()) {
			current = iterator.next();
			if(current.getName().equals(cmd) && 
					((!(current.getMinArgs() < args.size())) || (current.getMaxArgs() == -1) || (!(current.getMaxArgs() > args.size()))) ) {
				return true;
			}
		}
		return false;
	}

	public String getCmd() {
		return cmd;
	}

	public ArrayList<String> getArgs() {
		return args;
	}
	
	
}