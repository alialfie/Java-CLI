
public class Command {
	String name;
	int minArgs;
	int maxArgs;
	public Command(String name, int minArgs, int maxArgs) {
		this.name = name;
		this.minArgs = minArgs;
		this.maxArgs = maxArgs;		// -1 if unlimited
	}
	
	public String getName() {
		return name;
	}
	
	public int getMinArgs() {
		return minArgs;
	}
	
	public int getMaxArgs() {
		return maxArgs;
	}
}
