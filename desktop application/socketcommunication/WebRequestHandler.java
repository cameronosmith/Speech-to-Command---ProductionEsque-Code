package socketcommunication;

import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import grammar.Grammar;
import grammar.GrammarIO;
import typingoverview.TypingManager;

/* this file is to handle the requests coming from the js client,
 * such as requesting to delete a grammar, create a new grammar, 
 * and requesting a grammar's vocabulary for the recognition grammar list
 * */

public class WebRequestHandler {

    /* declare all of the possible requests. note: we need to share this
     * section of declarations with the web client to maintain proper request handling */
    /* all requests begin with a delimiter %%% */
    final static String requestDelim = "%%%";
    final static String deleteGrammarRequest = "delete grammar: ";
    final static String getAllGrammarsRequest = "return all grammars";
    final static String writeGrammarRequest = "write grammar: ";
    final static String toggleGrammarRequest = "toggle grammar: ";
    final static String readGrammarRequest = "requesting grammar: ";
    final static String getSpeechModesRequest = "requesting speech modes";
    final static String writeSpeechModesRequest = "sending speech modes: ";

    /* method to check whether the message from js is a request
     * just checks if the message begins with the request delim
     * @param message : the message sent from the web client
     * @return : true if the message is a request, false if not
     * */
    public static boolean isRequest (String message) {
        //if the message is shorter than the delimiter, not a request
        if (message.length() <= requestDelim.length())
            return false;
        //check if the first chars are the request delim
        if (message.substring(0,requestDelim.length()).equals (requestDelim))
            return true;

        //if here than not a request
        return false;
    }
    
    /* method to handle every request. delegates to the specific
     * request handler methods if needed 
     * @param request : the unfiltered request from the web client 
     * @return : none
     * */
    public static String handleRequest (String request) {
    	if (applicationentry.DesktopEntry.debug)
    		System.out.println("handling request: "+request);
        //only handle if this is a request 
        if (!isRequest (request) )
            return null;
        //output is the results of any possible request
        String output;
        /* check each request handler to see if matching request */ 
        if ((output = deleteGrammarRequest (request)) != null)
            return output;
        else if ((output = toggleGrammarRequest (request)) != null)
            return output;
        else if ((output = getAllGrammarsRequest (request)) != null)
            return output;
        else if ((output = readGrammarRequest (request)) != null)
            return output;
        else if ((output = writeGrammarRequest (request)) != null)
            return output;
        else if ((output = getSpeechModesRequest (request)) != null)
            return output;
        else if ((output = writeSpeechModesRequest (request)) != null)
            return output;
        //bad if reached this point
		return null;
    }

	/* method to handle deleteGrammarRequest 
     * @param request : the delete grammar request
     * @return : "success" if correct request
     * */
    public static String deleteGrammarRequest (String request) {
        //check if this is a proper detete grammar request
        if (request.length() <= requestDelim.length()+deleteGrammarRequest.length())
            return null;
        if (!deleteGrammarRequest.equals(request.substring (requestDelim.length(),
                requestDelim.length()+deleteGrammarRequest.length())))
            return null;
        //get the name of the grammar client is trying to delete and delete it
        String nameOfGrammarToDelete = request.substring (
            requestDelim.length()+deleteGrammarRequest.length());
        //delete grammar
        GrammarIO.deleteGrammar(nameOfGrammarToDelete);
        
        return new String("success");
    }

    /* method to handle request to get all grammars 
     * @param request : the get all grammars request
     * @return : "success" if correct request, null if not
     * */
    public static String getAllGrammarsRequest (String request) {
        //check if this is a proper get all grammars request
        if (request.length() < requestDelim.length()+getAllGrammarsRequest.length())
            return null;
        if (!getAllGrammarsRequest.equals(request.substring (requestDelim.length(),
                        requestDelim.length()+getAllGrammarsRequest.length())))
            return null;
        //return all grammars as json string
        return GrammarIO.getAllGrammarsAsString();
    } 

	/* method to write a grammar to file
     * @param request : the write a grammar request
     * @return : "success" if correct request, null if not
     * */
    private static String writeGrammarRequest(String request) {
		//check if this is a proper edit grammar request
        if (request.length() <= requestDelim.length()+writeGrammarRequest.length())
            return null;
        if (!writeGrammarRequest.equals(request.substring (requestDelim.length(),
                requestDelim.length()+writeGrammarRequest.length())))
            return null;
        //get the name of the grammar client is trying to write
        String grammarAsJsonString = request.substring (
            requestDelim.length()+writeGrammarRequest.length());
        /* write the grammar */
        try {
			GrammarIO.writeGrammarToFile(grammarAsJsonString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        return new String ("success");
	}

    /* method to toggle a grammar 
     * @param request : the toggle a grammar request
     * @return : the new state of the grammar
     * */
    private static String toggleGrammarRequest(String request) {
		//check if this is a proper edit grammar request
        if (request.length() <= requestDelim.length()+toggleGrammarRequest.length())
            return null;
        if (!toggleGrammarRequest.equals(request.substring (requestDelim.length(),
                requestDelim.length()+toggleGrammarRequest.length())))
            return null;
        //get the name of the grammar client is trying to toggle
        String nameOfGrammarToToggle = request.substring (
            requestDelim.length()+toggleGrammarRequest.length());
        /* toggle the grammar and write it */
        //gr is the temp grammar created to toggle
        Grammar gr = new Grammar (nameOfGrammarToToggle);
        
        return Boolean.toString(gr.toggle());
	}

	/* method to return a grammar json string
     * @param request : the toggle a grammar request
     * @return : json string if correct request, null if not
     * */
    private static String readGrammarRequest(String request) {
		//check if this is a proper edit grammar request
        if (request.length() <= requestDelim.length()+readGrammarRequest.length())
            return null;
        if (!readGrammarRequest.equals(request.substring (requestDelim.length(),
                requestDelim.length()+readGrammarRequest.length())))
            return null;
        //nameOfGrammarToStringify is the grammar we want to get as json
        String nameOfGrammarToStringify = request.substring (
            requestDelim.length()+readGrammarRequest.length());
        /* return the grammar string */
        
        return (new Grammar (nameOfGrammarToStringify)).toString();
	}

	/* method to handle request to get speech modes of dictation (spacing, caps, etc.)
     * @param request : the get speech modes request
     * @return : the stringified object containing the speech modes
     * */
    public static String getSpeechModesRequest (String request) {
        //check if this is a proper get speech modes request
        if (request.length() < requestDelim.length()+getSpeechModesRequest.length())
            return null;
        if (!getSpeechModesRequest.equals(request.substring (requestDelim.length(),
                        requestDelim.length()+getSpeechModesRequest.length())))
            return null;
        //return all grammars as json string
        return TypingManager.getCommandStates();
    }
    
        
     /* method to write the states of the dictation modes
     * @param request : the write speech modes request
     * @return : "success" if correct request, null if not
     * */
    private static String writeSpeechModesRequest(String request) {
		//check if this is a proper write speech modes request
        if (request.length() <= requestDelim.length()+writeSpeechModesRequest.length())
            return null;
        if (!writeSpeechModesRequest.equals(request.substring (requestDelim.length(),
                requestDelim.length()+writeSpeechModesRequest.length())))
            return null;
        //get the stringified states obj
        String statesObjAsString = request.substring (
            requestDelim.length()+writeSpeechModesRequest.length());

        /* write the states */
        //jp is a json parser to parse the input states object
        JSONParser jp = new JSONParser();
        //returnStatesObj is the parsed object from web containing the dictation states
		JSONObject returnStatesObj = null;
        try {
			returnStatesObj = (JSONObject) jp.parse(statesObjAsString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        /* set states */
        TypingManager.fullCapsMode = (boolean) returnStatesObj.get("full-caps");
        TypingManager.semiCapsMode = (boolean) returnStatesObj.get("semi-caps");
        TypingManager.spacingMode = (boolean) returnStatesObj.get("spacing");
        TypingManager.typingMode = (boolean) returnStatesObj.get("typing");
        TypingManager.pureDictationMode = (boolean) returnStatesObj.get("pure dictation");
        
        return new String ("success");
	}



    //fake main method to use during testing
    public static void main (String args[]) {
    }

}