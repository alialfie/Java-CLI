import java.util.ArrayList;

public class CLI {
	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.parse("cp 3 3");
		
		ArrayList<String> argss = new ArrayList<String>();
		argss.add("t.txt");
		argss.add("test2");
		argss.add("test9");
		
		Terminal terminal = new Terminal();
		terminal.cd("E:/test");
		terminal.mv(argss);
	}
}