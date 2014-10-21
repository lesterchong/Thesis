/*
 * SkinLoader.
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

package javazoom.jlgui.player.amp.skin;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javazoom.jlgui.player.amp.util.BMPLoader;
import javazoom.jlgui.player.amp.util.Config;

/**
 * This class implements a Skin Loader.
 * WinAmp 2.x javazoom.jlgui.player.amp.skins compliant.
 */
public class SkinLoader
{
    private Hashtable 			_images = null;
	private ZipInputStream 		_zis = null;

    /**
     * Contructs a SkinLoader from a skin file.
     */
    public SkinLoader(String filename)
    {
        _images = new Hashtable();
        try
        {
			if (Config.startWithProtocol(filename)) _zis = new ZipInputStream((new URL(filename)).openStream());        	
        	else _zis = new ZipInputStream(new FileInputStream(filename));
		} catch (Exception e)
		  {
			// Try to load included default skin.
			ClassLoader cl = this.getClass().getClassLoader();
			InputStream sis = cl.getResourceAsStream("javazoom/jlgui/player/amp/metrix.wsz");
			if (sis != null) _zis = new ZipInputStream(sis);
		  }
    }

    /**
     * Contructs a SkinLoader from any input stream.
     */
    public SkinLoader(InputStream inputstream)
    {
        _images = new Hashtable();
        _zis = new ZipInputStream(inputstream);
    }


	/**
	 * Loads data (images + info) from skin.
	 */
    public void loadImages() throws Exception
    {
        ZipEntry entry = _zis.getNextEntry();
        while (entry != null)
        {
            if( entry.getName().toLowerCase().endsWith("bmp") )
            {
                BMPLoader bmp = new BMPLoader();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte buffer[] = new byte [256] ;
                do
                {
                    int bytesRead = _zis.read(buffer);
                    if(bytesRead == -1)
                        break;
                    baos.write(buffer, 0, bytesRead);
                } while(true);
                baos.close();
				/* modify by John Yang $$$$$$$$$$$$$
                StringTokenizer strtoken = new StringTokenizer(entry.getName().toLowerCase(), "/");
                String name;
                for(name = entry.getName().toLowerCase(); strtoken.hasMoreTokens(); name = (String)strtoken.nextElement())
                {
					// System.out.println ("Put skin bmp " + name) ;
                	_images.put(name, bmp.getBMPImage(new ByteArrayInputStream(baos.toByteArray())));
				}
				*/
                String name = entry.getName().toLowerCase() ;
                int pos = name.lastIndexOf ("/") ;
                if ( pos != -1 ) name = name.substring (pos+1) ;
				//System.out.println ("Put skin bmp " + name) ;
              	_images.put(name, bmp.getBMPImage(new ByteArrayInputStream(baos.toByteArray())));
            }
            // Added by John Yang (Read others entries as TXT) - 02/05/2001
            else if( entry.getName().toLowerCase().endsWith("txt") )
            {
                byte buffer[] = new byte [256] ;
                StringBuffer tmp = new StringBuffer () ;
                do
                {
                    int bytesRead = _zis.read (buffer) ;
                    if( bytesRead == -1 )
                        break ;
                    tmp.append (new String(buffer,0,bytesRead)) ;
                } while (true) ;

                String name = entry.getName().toLowerCase() ;
                int pos = name.lastIndexOf ("/") ;
                if ( pos != -1 ) name = name.substring (pos+1) ;
                _images.put (name, tmp.toString()) ;
            }
            entry = _zis.getNextEntry();
        }
        _zis.close();
    }

	/**
	 * Return Image from name.
	 */
    public Image getImage(String name)
    {
        return (Image)_images.get(name);
    }

	// Added by John Yang - 02/05/2001
	/**
	 * Return skin content (Image or String) from name.
	 */
    public Object getContent(String name)
    {
        return _images.get (name) ;
    }
}
