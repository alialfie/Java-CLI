import java.util.*;
import javafx.util.Pair;

public class Parser {
	String cmd;
	ArrayList<String> args;
	ArrayList<Pair<String, Integer>> validCommands;
	
	public Parser() {
		cmd = "";
		args = new ArrayList<String>();
		
		validCommands = new ArrayList<Pair<String, Integer>>() {
			{
				add(new Pair<String, Integer>("cd", 0));
				add(new Pair<String, Integer>("cp", 2));
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

	public ArrayList<String> getArgs() {
		return args;
	}
	
	
}
