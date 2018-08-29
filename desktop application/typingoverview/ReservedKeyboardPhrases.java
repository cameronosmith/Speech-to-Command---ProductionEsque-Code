package typingoverview;
/* this file is to check for reserved keyboard phrases, such as a request to press
 * the enter or tab key. each request beings with a delimitter key(), with the 
 * key phrase(s) inside the paranthesis
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.scene.input.KeyCode;

public class ReservedKeyboardPhrases {

	// beginDelim and endDelim are the delimiters to check for reserved key request
	private static String beginDelim = "keyPress(";
	private static String endDelim = ")";
	//delimDelim is to separate the keys within the request
	private static String delimDelim = ",";

	//the list of keys and their codes
	JSONObject keysList;
	//the typer to press our keys
	GhostWriter gw;
		
	/* public constructor to create the key map dictionary
	 * @param gw : the ghost writer to type our keys
	 * @return : none 
	 */
	public ReservedKeyboardPhrases (GhostWriter gw) {
		keysList = createCommandsMap ();
		this.gw = gw;
	}
	
	
	/* method to create the key commands map, since can't init values,
	 * used since we don't want to keep everything static
	 * @return : the json object of the key list we created
	 */
	public static JSONObject createCommandsMap () {
		//parser is an object to parse the file
        JSONParser parser = new JSONParser();
        //keysList is the root object for this json
        JSONObject keysAsJSON = new JSONObject();

        /*try to read and parse the file*/
        try {
        	/* get the grammar and return it */
            keysAsJSON = (JSONObject) parser.parse(new FileReader("src/typingoverview/stringifiedKeysList.txt")); 
        }
        catch (Exception e) {
        	System.out.println ("check keys list file name, not found");
        }

		return keysAsJSON;
	}
	
	/* method to check whether word is a key press request
	 * @param word : the word to check
	 * @return : true if request
	 */
	public boolean isRequest (String word) {
		/*check for the request delim to check if key press request */
		if (!word.contains (beginDelim) || !word.contains (endDelim))
			return false;
		else 
			return true;
	}

	/* method to handle request for reserved key commands
	 * @param text : the request
	 * @return : none
	 */
	public void handleKeyPressRequest(String text) {
		//keys is the text in between the delimiters
		String keys = text.substring(text.indexOf(beginDelim) + beginDelim.length(), 
				text.indexOf(endDelim));
		// keyCodesList is the list of keycodes to type after iterating
		ArrayList<Integer> keyCodesList = new ArrayList<Integer>();
		//debug
		if (applicationentry.DesktopEntry.debug)
			System.out.println(Arrays.toString(keys.split(delimDelim)));
		/* iterate through each key and type that reserved key */
		for (String keyName : keys.split (delimDelim)) {
			/* check if key is reserved key request, replace it with key code if so*/
			if (keysList.containsKey(keyName.trim())) {
				if (applicationentry.DesktopEntry.debug)
					System.out.println("key match"+keyName);
				/* append the key to the list of keys to press */
				keyCodesList.add(Integer.parseInt((String)keysList.get(keyName.trim())));
			}
			else {
				System.out.println("invalid key press request, sending as string");
				//send each char's code
				for (char invalidLetter : keyName.toCharArray()) {
					//iterate it in case gw sends back multiple codes 
					for (int code : gw.getKeyCodeFromChar(invalidLetter))
						keyCodesList.add(code);
				}
			}
		}
		//debug
		if (applicationentry.DesktopEntry.debug)
			System.out.println("key press request: "+keyCodesList.toString());
		/* convert Integer list to int[] and then press the keys */
		int[] intKeyCodes = keyCodesList.stream().mapToInt(Integer::intValue).toArray();
		gw.doType(intKeyCodes);
	}

	//fake main method for testing
	public static void main (String args[]) throws AWTException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ReservedKeyboardPhrases kb = new ReservedKeyboardPhrases(new GhostWriter());
		kb.handleKeyPressRequest("keyPress(abcd)");
	}

}
