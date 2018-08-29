package grammar;

import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/* this file serves as the grammar file unit, where we will store the
 * grammar in object notation to search for commands and toggle the grammar 
 * */

public class Grammar {

    //grammarDef is the object holding all of the grammar vocabulary
    JSONObject grammarDef;
    //grammarOn is true if the the grammar should be searched for command
    boolean grammarOn = true;
    //grammarName is the name of the grammar
    String grammarName;

    /* constructor for the grammar, initializes all data fields
     * @param grammarName : the name of the grammar at its file 
     * @return : none
     * */
    public Grammar (String grammarName) {
        /* set grammar data fields */
        this.grammarName = new String (grammarName);
        //rootOfGrammar is the whole grammar object
        JSONObject rootOfGrammar = GrammarIO.readGrammarFromJSONFile (grammarName);
        /* get the grammar definitions vocabulary */
        grammarDef = (JSONObject) rootOfGrammar.get("grammarVocabulary");
        /* get the grammar on status */
        grammarOn = (new String("true")).equals(rootOfGrammar.get("grammarOn"));
    }
    
    /* method to get the grammarOn status of the grammar
     * @return : true if the grammar is on
     */
    public boolean getGrammarOn () {
    	return grammarOn;
    }

    /* method to toggle whether the grammar should be checked or not 
     * @return : the new state of the grammar
     * */
    public boolean toggle() {
        /* set toggle to opposite value */
        grammarOn = (grammarOn) ? false : true;
        /* write the grammar now that we updated it */
        try {
			GrammarIO.writeGrammarToFile (this);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /* return the state of the grammar */
        return grammarOn;
    }
    
    /* method to traverse the grammar for a matching command in this grmamar
     * @param commandSearchingFor : the command to search for
     * @return : the keySequence if found matching command, null if not
     * */
    public String searchForCommandLocal (String commandSearchingFor) {
        //break out if we aren't supposed to be checking this grammar
        if (!grammarOn) {
        	if (applicationentry.DesktopEntry.debug)
        		System.out.printf("not using command since grammar %s is toggled off\n", getName());
            return null;
        }
        /* traverse the grammar for a matching command */
        //iteator is used to traverse the grammar
        for (Iterator iterator = grammarDef.keySet().iterator(); iterator.hasNext();) {
            //key is the vocab command, check if this is the command we are looking for
            String command = (String) iterator.next();
            if (command.equals (commandSearchingFor))
                return (String) grammarDef.get (command);
        }
        //did not find a grammar if code reaches here
        return null;
    }

    /* method to traverse all grammars to search for a command
     * @param commandSearchingFor : the command to search for
     * @return : the keySequence if found matching command, null if not
     * */
    public static String searchForCommandGlobal (String commandSearchingFor) {
        /* iterate through all grammars to search for the command */
        for (Grammar grammar : GrammarIO.readInAllGrammars ()) {
        	//keySequence is the possible returned key sequence for this command
            String keySequence;
            if ( (keySequence = grammar.searchForCommandLocal (commandSearchingFor) ) != null)
                return keySequence;
            //if no command found keep searching
        }
        //if got to this point no grammar contains the command
        return null;
    }

    /* method to delete a grammar. delegates to json writer delete grammar method
     * @param grammarName : the name of the grammar to delete
     * @return : none
     */
    public static void deleteGrammar (String grammarName) {
        //delegate to json writer's delete grammar to preserve layer ordering
        GrammarIO.deleteGrammar(grammarName); 
    }

    /* getter for the grammar name 
     * @return : the grammar name
     * */
    public String getName () {
        return grammarName;
    }
    
    /* debugging helper to print grammar as json string
     * @return : the grammar as a string
     * */
    public String toString () {
    	/* return the grammar as a json string */
        return GrammarIO.grammarToJSONString(this); 
    }

    /* main method to test functionality. delete for production release */
    public static void main (String args[]) {
    }
}