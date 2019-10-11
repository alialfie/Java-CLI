import java.util.Scanner;

public class CLI {
	public static void main(String[] args) {
		//Parser parser = new Parser();
		Terminal T = new Terminal();
		//parser.parse("clear");
		Scanner kb = new Scanner(System.in);
		
		T.cd("E:/M.U.G.E.N");
		String comnd = kb.next();
		if (comnd.equalsIgnoreCase("date")) {
			T.date();
		}
		else if(comnd.equalsIgnoreCase("pwd")) {
			T.pwd();
		}
		else if(comnd.equalsIgnoreCase("mkdir")) {
			String filename =kb.next();
			T.mkdir(filename);
		}
		else if(comnd.equalsIgnoreCase("ls")) {
			T.ls();
		}
		
	}
	
}
