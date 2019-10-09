import java.util.ArrayList;

public class Parser {
	String cmd;
	ArrayList args;
	int x=0;
	
	public Parser() {
		cmd = "";
		args = new ArrayList<String>();
	}

	public boolean parse(String input) {
		int length = input.length(), i = 0;
		while(input.charAt(i) != ' ' && i < length) {
			cmd += input.charAt(i);
			i++;
		}
		
		i++;	//skip the space
		
		while(i < length) {
			String arg = "";
			while(input.charAt(i) != ' ') {
				arg += input.charAt(i);
				i++;
			}
		}
		
		
		return true;
	}

	public String getCmd() {
		return cmd;
	}

	public ArrayList getArgs() {
		return args;
	}
	
	
}
