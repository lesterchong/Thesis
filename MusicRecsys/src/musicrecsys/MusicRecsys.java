/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicrecsys;

import javazoom.jlgui.player.amp.playlist.BasePlaylist;
import javazoom.jlgui.player.amp.playlist.Playlist;
import javazoom.jlgui.player.amp.playlist.PlaylistItem;

/**
 *
 * @author Lester Chong
 */
public class MusicRecsys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String string = "07/14/2014 19:49:25, 02 Magasin by Paolo Santos.mp3 MANUAL";
        String token[];
        
        token = string.split("AUTOMATIC|MANUAL|,");
        System.out.println(token[2]);
        
    }
}