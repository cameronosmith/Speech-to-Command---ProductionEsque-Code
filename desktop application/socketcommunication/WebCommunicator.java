package socketcommunication;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import typingoverview.TypingManager;

public class WebCommunicator {

	public final static int port = 4242;   //the port we will be listening on
	ServerSocket listenSock; //the listening server socket
	Socket sock;			 //the socket that will be used for communication

    /* method to begin listening to web client
     * handles communication
     * @return : none
     * */
	public WebCommunicator () throws AWTException, IOException {

		//create the sock we will be listening on
		try {
		listenSock = new ServerSocket(port);
		}
		catch (IOException e) {
			/* if here than desktop application already running, just reopen web */
			return;
		}
		//debug
        if (applicationentry.DesktopEntry.debug)
			System.out.println ("listening on port "+port+"...");

		/* kepe listening for input as long as application runs */
		while (true) {	   
        	//debug
        	if (applicationentry.DesktopEntry.debug)
        		System.out.println("listening...");

        	//sock will get the listening socket
			sock = listenSock.accept();			 

            //br and bw are to read and write from and to the socket
			BufferedReader br =	new BufferedReader(new InputStreamReader(sock.getInputStream()));
			BufferedWriter bw =	new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            //receivedFromWeb is the message received from the web client
            String receivedFromWeb = "";                                
            /* read in whatever we got from the socket */
			while ((receivedFromWeb = br.readLine()) != null) {
				/* process text with log from how their dictation was handled */
				//grammarLog is the response as a json string 
				String grammarLog = TypingManager.processAndHandleText(receivedFromWeb);
				if (applicationentry.DesktopEntry.debug)
					System.out.println("returned grammar response is"+grammarLog);
				/* send back the response with a newline char to identify the end of our message */
				bw.write(grammarLog+"\n");
				bw.flush();
			}

        	//debug
        	if (applicationentry.DesktopEntry.debug)
        		System.out.println("done listening, closing streams");

			//close the streams
			bw.close();
			br.close();
			sock.close();
		}
			
	}
	public static void main(String args[]) throws AWTException, IOException {
		WebCommunicator wc = new WebCommunicator();
	}
	
}

