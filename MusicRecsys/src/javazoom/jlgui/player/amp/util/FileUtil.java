/*
 * FileUtil.
 * 
 * JavaZOOM : jlgui@javazoom.net
 *            http://www.javazoom.net 
 *
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */

package javazoom.jlgui.player.amp.util;

import java.io.File;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;


/**
 * @author Scott Pennell
 */
public class FileUtil {
    private static Vector supportedExtensions = null;

    public static File[] findFilesRecursively(File directory) {
        if(directory.isFile()) {
            File[] f = new File[1];
            f[0] = directory;
            return(f);
        }
        Vector v = new Vector();
        addSongsRecursive(v, directory);
        return((File[])v.toArray(new File[v.size()]));
    }

    private static void addSongsRecursive(Vector found, File rootDir) {
        if(rootDir == null) return; // we do not want waste time
        String[] lists = rootDir.list();
        if(lists == null) return;
        for(int i = 0; i < lists.length; i++) {
            File file = new File(rootDir + File.separator + lists[i]);
            if(file.isDirectory())
                addSongsRecursive(found, file);
            else {
                if(isMusicFile(lists[i])) {
                    found.addElement(file);
                }
            }
        }
    }

    public static boolean isMusicFile(String f) {
        Vector exts = getSupportedExtensions();
        int sz = exts.size();
        for(int i = 0; i < sz; i++) {
            String ext = (String)exts.elementAt(i);
            if(ext.equals(".wsz") || ext.equals(".m3u"))
                continue;
            if(f.endsWith(exts.elementAt(i).toString()))
                return true;
        }
        return false;
    }

    public static Vector getSupportedExtensions() {
        if(supportedExtensions == null) {
            String ext = Config.getInstance().getExtensions();
            StringTokenizer st = new StringTokenizer(ext, ",");
            supportedExtensions = new Vector();
            while(st.hasMoreTokens())
                supportedExtensions.add("." + st.nextElement());
        }
        return (supportedExtensions);
    }

    public static String getSupprtedExtensions() {
        Vector exts = getSupportedExtensions();
        String s = "";
        int sz = exts.size();
        for(int i = 0; i < sz; i++) {
            String ext = (String)exts.elementAt(i);
            if(ext.equals(".wsz") || ext.equals(".m3u"))
                continue;
            if(i == 0)
                s += ext;
            else
                s += ";" + ext;
            ;
        }

        return(s);
    }

    public static String padString(String s, int length) {
        return(padString(s, ' ', length));
    }

    public static String padString(String s, char padChar, int length) {
        int slen, numPads = 0;

        if(s == null) {
            s = "";
            numPads = length;
        }
        else if((slen = s.length()) > length) {
            s = s.substring(0, length);
        }
        else if(slen < length) {
            numPads = length - slen;
        }

        if(numPads == 0)
            return(s);

        char[] c = new char[numPads];
        Arrays.fill(c, padChar);
        return(s + new String(c));
    }

    public static String rightPadString(String s, int length) {
        return(rightPadString(s, ' ', length));
    }

    public static String rightPadString(String s, char padChar, int length) {
        int slen, numPads = 0;

        if(s == null) {
            s = "";
            numPads = length;
        }
        else if((slen = s.length()) > length) {
            s = s.substring(length);
        }
        else if(slen < length) {
            numPads = length - slen;
        }

        if(numPads == 0)
            return(s);

        char[] c = new char[numPads];
        Arrays.fill(c, padChar);
        return(new String(c) + s);
    }

}
