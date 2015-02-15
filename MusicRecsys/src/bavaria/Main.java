/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bavaria;

import java.awt.Frame;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.player.amp.Player;

/**
 *
 * @author Arturo III
 */
public class Main {
    private static Player theGUI;
    
    public static void main(String args[]){
        Tracker t;
        String currentArg = null;
        String currentValue = null;
        String skin = null;
        
        theGUI = new Player(skin, new Frame("R2ROKID9's Music Recommendation"));
        t = new Tracker("Music Tracker");
	// Instantiate low-level player.
	BasicPlayer bplayer = new BasicPlayer();
	// Register the front-end to low-level player events.
	bplayer.addBasicPlayerListener(theGUI);
	// Adds controls for front-end to low-level player.
	theGUI.setController(bplayer);
        theGUI.setTracker(t);
        theGUI.loadPlaylist("default.m3u");
	// Display.
        theGUI.show();
        
        
        //if (autoRun == true) theGUI.pressStart();
    }
    
    public Player getCurrentPlayer(){
        return theGUI;
    }
}