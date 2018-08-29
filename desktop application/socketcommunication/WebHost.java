package socketcommunication;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import applicationentry.DesktopEntry;

public class WebHost {
	
	//the path and arguments to create the php server
	final static String currentDir = System.getProperty("user.dir"); //dir w src,bin,etc
	final static String windowsPhp = "phpForWin/php.exe"; 
	final static String nonWindowsPhp = "php"; 
	final static String webFilesPath = currentDir+"/webFiles";
	final static String phpArg1 = "-S localhost:"; //add port
	final static String phpArg2 = "-t "+webFilesPath;
	final static String space = " ";
	//the website we are hosting the dictation on
	final static String webAddress = "http://localhost:"+DesktopEntry.localhostPort;
	//the args for opening the chrome exe
	final static String chromesDir = currentDir + "/chromePortables/";
	final static String windowsChromePath = chromesDir + "windows/chrome.exe"; //check this when we get the chrome portable exe for win
	final static String linuxChromePath = chromesDir + "linux/chrome/google-chrome";
	final static String macChromePath = chromesDir + "macosx/Contents/MacOS/chrome";
	final static String chromeAppArg = "--app=http://www.localhost:"+DesktopEntry.localhostPort;

	/* method to check if the current operating system is windows
	 * @return : true if windows OS
	 */
	public static boolean isWindows () {
		return (System.getProperty("os.name").toLowerCase().indexOf("win")>=0);
	}
	/* method to check if the current operating system is mac osx
	 * @return : true if mac osx
	 */
	public static boolean isMac () {
		return (System.getProperty("os.name").toLowerCase().indexOf("mac")>=0);
	}
	/* method to check if the current operating system is linux
	 * @return : true if linux
	 */
	public static boolean isLinux () {
		//for shorter reference
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix")>=0);
	}
	/* method to get the phpPath
	 * @return : the php path
	 */
	public static String getPhpPath () {
		return (isWindows()) ? currentDir+windowsPhp : nonWindowsPhp;
	}
	/* method to get the chrome path 
	 * @return : the path to the chrome executable
	 */
	public static String getChromePath () {
		if (isLinux()) {
			return null;
		}
		return null;
	}
	/* method to create the socket from the command line 
	 * @param port: the port to create the socket on 
	 * @return : none
	 */
	public static void createPhpSocket (final int port) {
		//debug
		if (applicationentry.DesktopEntry.debug)
			System.out.println ("creating php local web server on port: "+port);

		// phpCommand is the command to write into the terminal
		String phpCommand = getPhpPath()+space+phpArg1+port+space+phpArg2;

		/* call the php socket to host the web server */
        try {
			Runtime.getRuntime().exec(phpCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/* method to open the web window with the dictation website
	 * @return : none 
	 */
	public static void openWeb () {
		//debug
		if (applicationentry.DesktopEntry.debug)
			System.out.println ("opening web browser at " + webAddress);
		//chromePath is the correct path to the chrome exe
		String chromePath;
		/* open the chrome browswer with correct args */
		if (isWindows())
			chromePath = windowsChromePath;
		else if (isMac())
			chromePath = macChromePath;
		else if (isLinux())
			chromePath = linuxChromePath;
		else 
			chromePath = linuxChromePath; //any os we didnt cover?

		/* open the browser */
		try {
			Runtime.getRuntime().exec(new String[] {chromePath, chromeAppArg});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* method to create the server and open the web browser 
	 * @param port: the port to serve on
	 * @return : nothing
	 */
	public static void serveAndOpenWeb (int port) {
		/* delegate to the respective functions */
		createPhpSocket (port);
		openWeb();
	}

	//fake main for testing
	public static void main (String args[]) {
		createPhpSocket(2424);
		openWeb();
	}
}