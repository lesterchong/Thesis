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
 * @author Arturo III
 */
public class MusicRecsys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Playlist pl = new BasePlaylist();
        PlaylistItem pi = new PlaylistItem("Antukin", "D:\\Music\\OPM\\09 Antukin.mp3", 1, true);
        pl.appendItem(pi);
        pi = new PlaylistItem("Dianetic", "D:\\Music\\OPM\\12 Dianetic.mp3", 1, true);
        pl.appendItem(pi);
        pi = new PlaylistItem("Shooting Star", "D:\\Music\\OPM\\Hale - Shooting Star.mp3", 1, true);
        pl.appendItem(pi);
        pl.save("reco.m3u");
    }
}