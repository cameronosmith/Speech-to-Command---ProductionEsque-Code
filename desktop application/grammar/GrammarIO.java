package grammar;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/* this class is used to read and write grammars in json form */
public class GrammarIO {
	
	//the path to the grammars directory
    final static String grammarsPath = "src/grammar/grammars/";
    
    /* method to write a grammar to file as a json string
     * defers to the overloaded same method with a json string param
     * @param grammarToWrite : the grammar obj we need to write to file
     * @return : true if successful, false if not
     * */
	public static boolean writeGrammarToFile (Grammar grammarToWrite) throws ParseException {
        if (applicationentry.DesktopEntry.debug)
            System.out.printf ("attempting to write %s to json file...\n", grammarToWrite.getName());
        
        /* defer to overloaded method with grammar as json string */
        return writeGrammarToFile (grammarToJSONString(grammarToWrite));
    }

	/* method to write the json string grammar to a file
	 * @param grammarAsJsonString : the json string grammar to write to file
     * @return : true if successful, false if not
     * */
	public static boolean writeGrammarToFile (String grammarAsJsonString ) throws ParseException {
        if (applicationentry.DesktopEntry.debug)
            System.out.printf ("attempting to write json string to file...\n");
        /* get the grammar name for the filename and then write the json  */
        //jp is to parse the json string so we can get the grammar name
        JSONParser jp = new JSONParser();
        //grammarName is to hold the name of the grammar we are parsing
        String grammarName;

        //grammarObj is the root grammar obj to store in the file
        JSONObject grammarObj =	(JSONObject) jp.parse(grammarAsJsonString);
        grammarName = (String) grammarObj.get("grammarName");

        /* write the json string to file */
        try (FileWriter grammarFile = new FileWriter(grammarsPath+grammarName)) {
            grammarFile.write (grammarObj.toJSONString());
            grammarFile.flush ();
        } catch (IOException e) {
            e.printStackTrace(); 
            return false;
        }

        return true;
    }

	/* method to get the grammar as a json string
     * @return : true if successful, false if not
     * */
    public static String grammarToJSONString (Grammar grammarToStringify) {
        if (applicationentry.DesktopEntry.debug)
            System.out.printf ("attempting to convert %s to json ...", grammarToStringify.getName());
        //grammarObj is the root grammar obj to store in the file
        JSONObject grammarObj = new JSONObject();
        /* add the name and grammar defs object to the root object*/
        grammarObj.put ("grammarVocabulary", grammarToStringify.grammarDef);
        //write the base grammar name
        grammarObj.put ("grammarName", grammarToStringify.getName());
        //write the toggle status
        grammarObj.put("grammarOn", Boolean.toString(grammarToStringify.getGrammarOn()));

        /* write the json string to file */
        try (FileWriter grammarFile = new FileWriter(grammarsPath+grammarToStringify.getName())) {
            grammarFile.write (grammarObj.toJSONString());
            grammarFile.flush ();
        } catch (IOException e) {
            e.printStackTrace(); 
            return null;
        }

        return grammarObj.toJSONString();
    }
    
    /* method to get a specific grammar from the list of grammars
     * @param grammarName : the grammar name we are searching for
     * @return : the json object for the grammar
     */
    public static JSONObject readGrammarFromJSONFile (String grammarName) {
        //if (applicationentry.DesktopEntry.debug)
         //   System.out.println("attempting to read json..");
        //parser is an object to parse the file
        JSONParser parser = new JSONParser();
        //grammarAsJson is the root grammar object for this grammar
        JSONObject grammarAsJSON = new JSONObject();

        /*try to read and parse the file*/
        try {
        	/* get the grammar and return it */
            grammarAsJSON = (JSONObject) parser.parse(new FileReader(grammarsPath+grammarName)); 
            return grammarAsJSON;
        }
        catch (Exception e) {
        	System.out.println ("grammar not found, creating new grammar with that name...");
        }   
        //bad read if we got to here
        return null;
    }

	/* method to get a list of all the grammars in the grammars path
     * @return : an arraylist of the grammars
     * */
    public static ArrayList<Grammar> readInAllGrammars () {
        if (applicationentry.DesktopEntry.debug)
            System.out.println("attempting to read in all grammars..");
        //object to parse the file
        JSONParser parser = new JSONParser();
        //grammarsList is the list of grammars we will return
        ArrayList<Grammar> grammarsList = new ArrayList<Grammar> ();
        /* iterate through every file in grammars dir to get each grammar */
        File grammarsDir = new File (grammarsPath);
        for (File grammarFile : grammarsDir.listFiles ()) {
            //add this grammar to the list of grammars
            grammarsList.add (new Grammar (grammarFile.getName ()));
        }

        return grammarsList;    
    }

	/* method to get all grammars as a json string
     * @return : all grammars as a json string
     */
    public static String getAllGrammarsAsString () {
        if (applicationentry.DesktopEntry.debug)
            System.out.println("attempting to get all grammars as string..");
    	//combined is all the json objects combined into a json
    	JSONObject combined = new JSONObject ();
    	/* iterate through each grammar and add it to the combined json object */
    	for (Grammar grammar : readInAllGrammars ()) {
    		//add to collective object
    		combined.put(grammar.getName(), readGrammarFromJSONFile(grammar.getName()));
    	}
    	return combined.toString();
    }

	/* method to delete a json grammar file
     * @param grammarName : the name of the grammar to delete
     * @return : none
     * */
    public static void deleteGrammar (String grammarName) {
        if (applicationentry.DesktopEntry.debug)
            System.out.println("attempting to delete grammar %s grammarName\n");
        /* try to delete file if it exists */ 
        try {
			Files.deleteIfExists (Paths.get(grammarsPath + grammarName));
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
    }

}
