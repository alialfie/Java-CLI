import java.util.*;
import javafx.util.Pair;

public class Parser {
	String cmd;
	ArrayList args;
	ArrayList validCommands;
	
	public Parser() {
		cmd = "";
		args = new ArrayList<String>();
		
		validCommands = new ArrayList<Pair<String, Integer>>() {
			{
				add(new Pair("cd", 0));
				add(new Pair("cp", 2));
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
		
		return validateCommand();
	}
	
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
