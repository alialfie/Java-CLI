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
				add(new Command("pwd", 0, 2));
				add(new Command("date", 0, 2));
				add(new Command("ls", 0, 2));
				add(new Command("cat", 1, -1));
				add(new Command("cp", 2, -1));
				add(new Command("rm", 1, 1));
				add(new Command("args", 1, 1));
				add(new Command("more",1, 2));
				add(new Command("help", 0, 2));
				add(new Command("exit", 0, 0));
			}
		};
	}

	public boolean parse(String input) {
		cmd = "";
		args = new ArrayList<String>();
		
		int length = input.length(), i = 0;
		while(length != 0 && input.charAt(i) != ' ') {	//gets the command line
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
		
		transformArgs();
		
		return validateCommand();
	}
	
	private boolean validateCommand() {
		Iterator<Command> iterator = validCommands.iterator();
		Command current;
		while(iterator.hasNext()) {
			current = iterator.next();
			if(current.getName().equals(cmd) && (current.getMinArgs() <= args.size()) && 
					((current.getMaxArgs() == -1) || (current.getMaxArgs() >= args.size())) ) {
				return true;
			}
		}
		return false;
	}
	
	private void transformArgs() {		//handles the double quoted args
		ArrayList<String> newArgs = new ArrayList<String>();
		Iterator<String> iterator = args.iterator();
		String arg, next;
		while(iterator.hasNext()) {
			arg = "";
			arg = iterator.next();
			if(arg.charAt(0) == '"') {
				arg = arg.replace("\"", "");
				while(iterator.hasNext()) {
					next = iterator.next();
					if(next.charAt(next.length()-1) == '"') {
						next = next.replace("\"", "");
						arg  = arg + " " + next;
						break;
					}else {
						arg  = arg + " " + next;
					}
				}
				newArgs.add(arg);
			}else {
				newArgs.add(arg);
			}
		}
		
		args = newArgs;
	}

	public String getCmd() {
		return cmd;
	}

	public ArrayList<String> getArgs() {
		return args;
	}
	
	
}