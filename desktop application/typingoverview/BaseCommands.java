package typingoverview;

import socketcommunication.WebCommunicator;

/* this class is to maintain the list of base commands used 
 * to modify the dictation program
 */
public class BaseCommands {
	
	/* all of the user dictation commands used to modify the dictation */
	// toggle caps mode
	public static String fullCapsCommand = "caps";
	// toggle semi caps mode (first letter)
	public static String semiCapsCommand = "semi";
	// toggle type mode
	public static String typeCommand = "typing";
	// toggle spacing mode
	public static String spacingCommand = "spacing";
	// toggle pure dictation mode
	public static String pureDictationCommand = "scribe";
	
	
	/* method to check whether a word is a command 
	 * @param word : the word to check 
	 * @return : truen if command, false if not
	 */
	public static boolean isWordCommand (String word) {
		//debug
		if (applicationentry.DesktopEntry.debug)
			System.out.printf ("checking if %s is a command\n", word);
		/* check for any mactching command requests */
		if ( 
				word.trim().equals (fullCapsCommand) || 
				word.trim().equals (semiCapsCommand) || 
				word.trim().equals (typeCommand) || 
				word.trim().equals (spacingCommand) || 
				word.trim().equals (pureDictationCommand)  
				) {
			
			if (applicationentry.DesktopEntry.debug)
				System.out.printf ("%s is a command\n", word);
			return true;
		}
		else {
			if (applicationentry.DesktopEntry.debug)
				System.out.printf ("%s is not command\n", word);
			return false;
		}
	}

	/* method to toggle any command that the user requests
	 * when a command is found, the text after the command is reprocessed
	 * @param text : the text containing the command
	 * @return : none
	 * */
	public static void toggleBaseCommand (String textWithCommand) {
		//debug
		if (applicationentry.DesktopEntry.debug)
			System.out.printf ("toggling command %s \n", textWithCommand);
		//toggle the command
		if (textWithCommand.equals (fullCapsCommand)) 
			TypingManager.fullCapsMode = (TypingManager.fullCapsMode) ? false : true;
		else if (textWithCommand.equals (semiCapsCommand))
			TypingManager.semiCapsMode = (TypingManager.semiCapsMode) ? false : true;
		else if (textWithCommand.equals (typeCommand))
			TypingManager.typingMode = (TypingManager.typingMode) ? false : true;
		else if (textWithCommand.equals (spacingCommand))
			TypingManager.spacingMode = (TypingManager.spacingMode) ? false : true;
		else if (textWithCommand.equals (pureDictationCommand))
			TypingManager.pureDictationMode = (TypingManager.pureDictationMode) ? false : true;
	}

	/* method to apply command modifications to text
	 * @param text : the text to apply the command modifications to
	 * @return : the modified text
	 */
	public static String applyCommandsToText (String text) {
		/* go through each command and apply appropriate modifications */
		if (TypingManager.fullCapsMode)
			text = fullCapsModeText (text);
		if (TypingManager.semiCapsMode)
			text = semiCapsModeText (text);
		if (TypingManager.spacingMode)
			text = spacingModeText (text);
		else
			System.out.println("not space");
		if (TypingManager.typingMode)
			text = typingModeText (text);

		return text;
	}

	/* method to modify the text to be all caps 
	 * @param textToCapitalize : the text to capitalize
	 * @return : the capitalized text 
	 * */
	public static String fullCapsModeText (String textToCapitalize) {
		/* return capitalized texts */
		return textToCapitalize.toUpperCase();
	}

	/* method to capitalize first letter of input word 
	 * @param word : the word to capitalize the first letter
	 * @return : the text with capitalized first letters
	 */
	public static String semiCapsModeText (String word) {
		/* return the text with only first letter */
		return word.substring(0,1).toUpperCase()+word.substring(1);
	}

	/* method to convert text to just its first letters for typing mode
	 * @param word : the text to get the first letter from
	 * @return : the first letters of the text
	 */
	public static String typingModeText (String word) {
		/* return the first letter lowercased */
		return word.substring(0,1);
	}
	
	/* method to add a spacing to the end of the word
	 * used for spacing mode
	 * @param word : the text to add a space to
	 * @return : the first letters of the text
	 */
	public static String spacingModeText (String word) {
		/* just add space to it */
		System.out.println("adding space");
		return new String (word+" ");
	}

}
