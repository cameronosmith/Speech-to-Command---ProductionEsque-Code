package typingoverview;

import java.awt.event.KeyEvent;

import org.json.simple.JSONObject;

import applicationentry.DesktopEntry;
import grammar.Grammar;
import socketcommunication.WebRequestHandler;

/* this class is the entry point for all speech recognition sent */

public class TypingManager {
	
	//whether the text should be capital or not
	public static boolean fullCapsMode = false;
	//whether the text should be capital at first letter of each word or not
	public static boolean semiCapsMode = false;
	//whether the text should be in typing mode (heart -> h) first letter
	public static boolean typingMode = false;
	//whether the text should have spaces in between or not
	public static boolean spacingMode = true;
	//whether the text should be taken literally, without interpreting it as a commmand
	public static boolean pureDictationMode = false;
	
	//the ghost typer to type our keys
	private static GhostWriter ghostTyper = new GhostWriter ();
	
	/* method to handle the text, checking for commands in processing dictation
	 * @param text: the text to process 
	 * @return : the string we are going to type
	 */
	public static String processAndHandleText (String text) {
		//outputText is the text we are going to type for display
		StringBuilder outputText = new StringBuilder ();
		//outputJson is the json object containing the response
		JSONObject outputJson = new JSONObject ();
		//numOfResponses is the number of output lines we need for the text
		int numOfResponses = 0;

		/* check if a text sent is a request */
		if (WebRequestHandler.isRequest(text)) {
			return WebRequestHandler.handleRequest(text);
		}
		
		/* iterate through each word, handling commands and pure dictation */
		//word is each word in the text
		for (String word : text.split (" ")) {
			System.out.println("current word is "+word); //err debug

			/* check if command and handle if so */
			if (BaseCommands.isWordCommand (text.toLowerCase()) && !pureDictationMode) {
				BaseCommands.toggleBaseCommand (text);
				outputJson.put (++numOfResponses, "toggling command: "+text);
			}
			/* check if word is a grammar vocab */
			else if (Grammar.searchForCommandGlobal(word) != null && !pureDictationMode) {
				ghostTyper.typeKeySequence (Grammar.searchForCommandGlobal(word));
				outputJson.put(++numOfResponses, 
						"grammar command: "+word+" -> "+Grammar.searchForCommandGlobal(word));
			}
			//else word is just normal dictation, just type it out with command modifications
			else {
				ghostTyper.typeKeySequence (BaseCommands.applyCommandsToText(word));
				outputJson.put(++numOfResponses, "typing "+word);
			}
		}

		return outputJson.toString ();
	}	
	
	/* method to print and return a list of the command states
	 * @return : the states object in json stringified format
	 */
	public static String getCommandStates () {
		/* print to this console for debugging */
		//commandsListing is the string with the states to be printed only to this console.
		String commandsListings = "getting command states: \n caps is: "+fullCapsMode+"\n semi caps is: "+semiCapsMode
				+ "\n typing mode is: "+typingMode+"\n spacing mode is: "+spacingMode
				+ "\n pure dictation mode is: "+pureDictationMode;
		if (DesktopEntry.debug)
			System.out.println(commandsListings);
		/* create json object to return to web client , put all states in the object*/
		JSONObject statesObj = new JSONObject();
		statesObj.put ("fullCapsMode", Boolean.toString(fullCapsMode));
		statesObj.put ("semiCapsMode", Boolean.toString(semiCapsMode));
		statesObj.put ("typingMode", Boolean.toString(typingMode));
		statesObj.put ("spacingMode", Boolean.toString(spacingMode));
		statesObj.put ("pureDictationMode", Boolean.toString(pureDictationMode));

		return statesObj.toJSONString();
	}
	
	
	/* fake main method for testing */
	public static void main (String args[]) {
		System.out.println(KeyEvent.VK_HOME);
	}
}
