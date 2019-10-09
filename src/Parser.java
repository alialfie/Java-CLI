import java.util.*;
import javafx.util.Pair;

public class Parser {
	String cmd;
	ArrayList args;
	ArrayList validCommands;
	
	public Parser() {
		cmd = "";
		args = new ArrayList<String>();
		
		// must find a way to add a maximum number of arguments
		validCommands = new ArrayList<Pair<String, Integer>>() {
			{
				add(new Pair("cd", 0));
				add(new Pair("cp", 2));
				add(new Pair("clear", 0));
				add(new Pair("ls", 0));
				add(new Pair("pwd", 0));
				add(new Pair("mv", 2));
				add(new Pair("rm", 1));
				add(new Pair("mkdir", 1));
				add(new Pair("rmdir", 1));
				add(new Pair("cat", 2));
				add(new Pair("date", 0));
				add(new Pair("more", 2));
				add(new Pair("help", 0));
				add(new Pair("args", 1));
				// | ,> , >>
			}
		};
	}

	public boolean parse(String input) {
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
		
		System.out.println(validateCommand());
		
		return validateCommand();
	}
	
	//change later after being able to set a max and min argument limit to validate
	private boolean validateCommand() {
		Iterator<Pair<String, Integer>> iterator = validCommands.iterator();
		Pair<String, Integer> current;
		while(iterator.hasNext()) {
			current = iterator.next();
			if(current.getKey().equals(cmd)) {
				if(args.size() >= (Integer)current.getValue()) {
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}

	public String getCmd() {
		return cmd;
	}

	public ArrayList getArgs() {
		return args;
	}
	
	
}
