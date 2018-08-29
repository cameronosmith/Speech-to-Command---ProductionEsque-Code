package typingoverview;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/* this class is to simulate key presses given a key sequence to execute */

public class GhostWriter {
	
	//robot to do the typing
	private static Robot robot;
	//rkp to check for keyboard requests
	ReservedKeyboardPhrases rkp;
	//debug toggle
	boolean debug = true;

	/* method to create the robot typer
	 * @return : none
	 */
	public GhostWriter () {
		try {
			robot = new Robot ();
			robot.setAutoDelay(10); //for better results for combination keys
			rkp = new ReservedKeyboardPhrases(this);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/* method to get type a key sequence 
	 * @param keySequence : the key sequence to type
	 * @return : none
	 * */
	public void typeKeySequence (String keySequence) {
		System.out.println("sent key sequnece "+keySequence);
		//split is the input split by word
		String [] split = keySequence.split(" ");
		/* iterate through each word to type */
		for (int index = 0; index < split.length; index++) {
			//possible space is a space to append if not the last word
			String possibleSpace = (index != split.length-1) ? " ":"";

			typeWord (split[index]+possibleSpace); 
		}
	}
	/* method to type a word
	 * @param word: the word to type
	 * @return : none
	 */
	public void typeWord (String word) {
		System.out.println("type word was sent "+word);
		/* check if this word is a key press request and if so delegate it */
		if (rkp.isRequest(word)) {
			System.out.println("found request inside gw");
			rkp.handleKeyPressRequest(word);
		}
		/* else just type each char */
		else {
			for (char letter : word.toCharArray()) {
				doType (getKeyCodeFromChar (letter));
			}
		}
	}
	
	/* method to type any character
	 * @param character : the char to type
	 * @return : none
	 */
	public static int[] getKeyCodeFromChar (char character) {

		  //the key codes to return
		  int[] returnKeyCodes = null;
		  
	      switch (character) {
	      //case 'a': returnKeyCodes = new int[] {KeyEvent.VK_A}; break;
	      case 'a': returnKeyCodes = new int[] {KeyEvent.VK_A}; break;
	      case 'b': returnKeyCodes = new int[] {KeyEvent.VK_B}; break;
	      case 'c': returnKeyCodes = new int[] {KeyEvent.VK_C}; break;
	      case 'd': returnKeyCodes = new int[] {KeyEvent.VK_D}; break;
	      case 'e': returnKeyCodes = new int[] {KeyEvent.VK_E}; break;
	      case 'f': returnKeyCodes = new int[] {KeyEvent.VK_F}; break;
	      case 'g': returnKeyCodes = new int[] {KeyEvent.VK_G}; break;
	      case 'h': returnKeyCodes = new int[] {KeyEvent.VK_H}; break;
	      case 'i': returnKeyCodes = new int[] {KeyEvent.VK_I}; break;
	      case 'j': returnKeyCodes = new int[] {KeyEvent.VK_J}; break;
	      case 'k': returnKeyCodes = new int[] {KeyEvent.VK_K}; break;
	      case 'l': returnKeyCodes = new int[] {KeyEvent.VK_L}; break;
	      case 'm': returnKeyCodes = new int[] {KeyEvent.VK_M}; break;
	      case 'n': returnKeyCodes = new int[] {KeyEvent.VK_N}; break;
	      case 'o': returnKeyCodes = new int[] {KeyEvent.VK_O}; break;
	      case 'p': returnKeyCodes = new int[] {KeyEvent.VK_P}; break;
	      case 'q': returnKeyCodes = new int[] {KeyEvent.VK_Q}; break; 
	      case 'r': returnKeyCodes = new int[] {KeyEvent.VK_R}; break;
	      case 's': returnKeyCodes = new int[] {KeyEvent.VK_S}; break;
	      case 't': returnKeyCodes = new int[] {KeyEvent.VK_T}; break;
	      case 'u': returnKeyCodes = new int[] {KeyEvent.VK_U}; break;
	      case 'v': returnKeyCodes = new int[] {KeyEvent.VK_V}; break;
	      case 'w': returnKeyCodes = new int[] {KeyEvent.VK_W}; break;
	      case 'x': returnKeyCodes = new int[] {KeyEvent.VK_X}; break;
	      case 'y': returnKeyCodes = new int[] {KeyEvent.VK_Y}; break;
	      case 'z': returnKeyCodes = new int[] {KeyEvent.VK_Z}; break;
	      case 'A': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_A}; break;
	      case 'B': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_B}; break;
	      case 'C': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_C}; break;
	      case 'D': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_D}; break;
	      case 'E': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_E}; break;
	      case 'F': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_F}; break;
	      case 'G': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_G}; break;
	      case 'H': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_H}; break;
	      case 'I': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_I}; break;
	      case 'J': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_J}; break;
	      case 'K': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_K}; break;
	      case 'L': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_L}; break;
	      case 'M': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_M}; break;
	      case 'N': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_N}; break;
	      case 'O': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_O}; break;
	      case 'P': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_P}; break;
	      case 'Q': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_Q}; break;
	      case 'R': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_R}; break;
	      case 'S': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_S}; break;
	      case 'T': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_T}; break;
	      case 'U': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_U}; break;
	      case 'V': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_V}; break;
	      case 'W': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_W}; break;
	      case 'X': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_X}; break;
	      case 'Y': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_Y}; break;
	      case 'Z': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_Z}; break;
	      case '0': returnKeyCodes = new int[] {KeyEvent.VK_0}; break;
	      case '1': returnKeyCodes = new int[] {KeyEvent.VK_1}; break;
	      case '2': returnKeyCodes = new int[] {KeyEvent.VK_2}; break;
	      case '3': returnKeyCodes = new int[] {KeyEvent.VK_3}; break;
	      case '4': returnKeyCodes = new int[] {KeyEvent.VK_4}; break;
	      case '5': returnKeyCodes = new int[] {KeyEvent.VK_5}; break;
	      case '6': returnKeyCodes = new int[] {KeyEvent.VK_6}; break;
	      case '7': returnKeyCodes = new int[] {KeyEvent.VK_7}; break;
	      case '8': returnKeyCodes = new int[] {KeyEvent.VK_8}; break;
	      case '9': returnKeyCodes = new int[] {KeyEvent.VK_9}; break;
	      case '-': returnKeyCodes = new int[] {KeyEvent.VK_MINUS}; break;
	      case '~': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE}; break;
	      case '!': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_1}; break;
	      case '@': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_2}; break;
	      case '#': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_3}; break;
	      case '$': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_4}; break;
	      case '%': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_5}; break;
	      case '^': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_6}; break;
	      case '&': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_7}; break;
	      case '*': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_8}; break;
	      case '(': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_9}; break;
	      case ')': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_0}; break;
	      case '_': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_MINUS}; break;
	      case '=': returnKeyCodes = new int[] {KeyEvent.VK_EQUALS}; break;
	      case '+': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT,KeyEvent.VK_EQUALS}; break;
	      case '\t': returnKeyCodes = new int[] {KeyEvent.VK_TAB}; break;
	      case '\n': returnKeyCodes = new int[] {KeyEvent.VK_ENTER}; break;
	      case '[': returnKeyCodes = new int[] {KeyEvent.VK_OPEN_BRACKET}; break;
	      case ']': returnKeyCodes = new int[] {KeyEvent.VK_CLOSE_BRACKET}; break;
	      case '\\': returnKeyCodes = new int[] {KeyEvent.VK_BACK_SLASH}; break;
	      case '{': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET}; break;
	      case '}': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET}; break;
	      case '|': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH}; break;
	      case ';': returnKeyCodes = new int[] {KeyEvent.VK_SEMICOLON}; break; 
	      case ':': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT,KeyEvent.VK_SEMICOLON}; break;
	      case '\'': returnKeyCodes = new int[] {KeyEvent.VK_QUOTE}; break;
	      case '"': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_QUOTEDBL}; break; 
	      case ',': returnKeyCodes = new int[] {KeyEvent.VK_COMMA}; break;
	      case '<': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_COMMA}; break;
	      case '.': returnKeyCodes = new int[] {KeyEvent.VK_PERIOD}; break;
	      case '>': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_PERIOD}; break;
	      case '/': returnKeyCodes = new int[] {KeyEvent.VK_SLASH}; break;
	      case '?': returnKeyCodes = new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH}; break;
	      case ' ': returnKeyCodes = new int[] {KeyEvent.VK_SPACE}; break;
	      default:
	          System.err.println ("cannot print char: "+character);
	      }
	      
	      return returnKeyCodes;
	  }
	
	
	/* method to press/release multiple key codes
	 * @param keycode : the keycode to press/release
	 * @return : none
	 */
	  public void doType (int... keyCodes) {
		  /*if just one char delegate to the single type */
		  if (keyCodes.length == 1) {
			  doTypeSingle(keyCodes[0]);
			  return;
		  }
		  if (debug)
			  System.out.println("dotype arr was sent:"+Arrays.toString(keyCodes));
	      /* press and release all keys */
		  for(int i=0;i<keyCodes.length;i++) {
			  if (debug)
				  System.out.println("gonna press:"+keyCodes[i]+
						  " which is:"+(char)keyCodes[i]);
			  robot.keyPress(keyCodes[i]);
		  }
		  for(int i=keyCodes.length-1;i>=0;i--) {
			  if (debug)
				  System.out.println("gonna release:"+keyCodes[i]+
						  " which is:"+(char)keyCodes[i]);
			  robot.keyRelease(keyCodes[i]);
		  }
	  }

	  /* more efficient do type for one char */
	  public void doTypeSingle (int keyCode) {
		  if (debug)
			  System.out.println("dotype single was sent:"+keyCode);
		  robot.keyPress(keyCode);
		  robot.keyRelease(keyCode);
	  }
	  
	  //fake main method for testing
	  public static void main (String args[]) throws AWTException {
		  GhostWriter gw = new GhostWriter();
		  //gw.typeKeySequence("a keyPress(Control,Shift,+)"); 
		  try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  Robot r = new Robot();
		  r.keyPress(KeyEvent.VK_SHIFT); 
		  r.keyPress(KeyEvent.VK_5); 
		  robot.delay(300);
		  r.keyRelease(KeyEvent.VK_5);
		  r.keyRelease(KeyEvent.VK_SHIFT);
	  }
	 
}
