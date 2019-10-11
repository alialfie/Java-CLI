import java.io.File;

public class Terminal {
	String defaultDir;
	String path;

	public Terminal() {
		defaultDir = "C:/";
		path = defaultDir;
	}
	
	public void cd(String path) {
		String backupPath = this.path;
		if(path.equals("")) {
			this.path = this.defaultDir;
		}else if(path.charAt(1) == ':') {
			this.path = path;
		}else if(path.charAt(0) != '/') {
			this.path = this.path + path;
		}else if(path.charAt(0) == '/') {
			this.path = "C:" + path;
		}
		
		if(this.path.charAt(this.path.length()-1) != '/') {
			this.path += "/";
		}
		
		File f = new File(this.path);
		if(!f.isDirectory()) {
			System.out.println("invalid directory");
			this.path = backupPath;
		}
	}
	
	public void pwd() {
		System.out.println(path);
	}
}