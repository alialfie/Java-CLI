
public class CLI {
	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.parse("cp 3 3");
		
		Terminal terminal = new Terminal();
		terminal.cd("E:/C++ Codeblocks");
		terminal.cd("reg sys/objf");
	}
	
}
