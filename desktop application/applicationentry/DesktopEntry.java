package applicationentry;

import java.awt.AWTException;
import java.io.IOException;

import socketcommunication.WebCommunicator;
import socketcommunication.WebHost;

/* main entry point for the desktop app. basically the main class */

public class DesktopEntry {
	
	public final static boolean debug = false;
	public final static int localhostPort = 2424;
    
    /* the entry point to the whole program */
    public static void main (String args[]) throws AWTException, IOException {
    	/* host the web client */
    	WebHost.serveAndOpenWeb (localhostPort);
    	/* create the desktop host listening for dictation requests */
    	WebCommunicator wc = new WebCommunicator ();
    }    
}