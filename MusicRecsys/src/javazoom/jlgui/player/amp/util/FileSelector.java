/*
 * FileSelector.
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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

/** 
 * This class is used to select a file for loading or saving. It's mainly
 * a wrapper class for java.awt.FileDialog.
 *
 * @author Jeremy Cloud
 *
 * @version 1.0.0
 *
 * @see java.awt.FileDialog
 */
public class FileSelector {

  public static final int OPEN    = 1;
  public static final int SAVE    = 2;
  public static final int SAVE_AS = 3;

  private static 	Frame main_window;
  private static	String working_dir = null;
  private static	String file = null;
  private static	String directory = null;


  static public String getFile() {
	  return file;
	}

	static public String getDirectory() {
	  return directory;
	}

	static public void setStartDirectory(String choice) {
	  working_dir = choice;
	}

	static public void setWindow( Frame frame) {
	  FileSelector.main_window = frame;
	}


	/** Opens a dialog box so that the user can search for a file
		*	with the given extension and returns the filename selected.
		*
		* @param extension  the extension of the filename to be selected,
		*	                  or "" if any filename can be used
		*
		* @param directory  the string to be put in the starting directory
		*
		* @param mode  the action that will be performed on the file, used to tell what
		*	             files are valid
		*
		* @return  the selected filename
		*/
  static public String selectFile( int mode, String extensions, String directory ) {
    return selectFile( mode, null, extensions, null, directory );
  }


  /** Opens a dialog box so that the user can search for a file
		*	with the given extension and returns the filename selected.
		*
		* @param extension  the extension of the filename to be selected,
		*	                  or "" if any filename can be used
		*
		* @param title_prefix the string to be put in the title, followed by : SaveAs
		*
		* @param mode  the action that will be performed on the file, used to tell what
		*	             files are valid
		*
		* @param default_name  the default name of the file
		*
		* @param directory  the string to be put in the starting directory
		*
		* @return  the selected filename
		*/
  static public String selectFile( int mode, String default_name, String extensions,
                                   String title_prefix, String dir ) {
    FileNameFilter filter = new FileNameFilter( extensions );
    FileDialog file_panel = null;
    String file_name = null, window_title;
    working_dir = dir;

    if( title_prefix != null && title_prefix.length() > 0 )
      window_title = title_prefix + ": ";
    else
      window_title = "";

    switch( mode ) {
      case OPEN:
        file_panel = new FileDialog( main_window, window_title + "Open", FileDialog.LOAD );
        break;

      case SAVE:
        file_panel = new FileDialog( main_window, window_title + "Save", FileDialog.SAVE );
        break;

      case SAVE_AS:
        file_panel = new FileDialog( main_window, window_title + "Save As", FileDialog.SAVE );
        break;
    }

    if( default_name != null )
      file_panel.setFile( default_name );

    if( working_dir != null )
      file_panel.setDirectory( working_dir );

    file_panel.setFilenameFilter( filter );

    if( default_name == null )
      file_panel.setFile( filter.getExtensionString() );

    file_panel.show();

    while( file_panel.isShowing() ) {
      try {
        Thread.sleep( 1000 );
      } catch( InterruptedException e ) {}
    }

    if( file_panel.getFile() != null ){
      file_name = filter.constructFileName( file_panel.getDirectory(), file_panel.getFile() );
      file = file_panel.getFile();
      directory = file_panel.getDirectory();
    }
    else
    {
		// Add By JOHN YANG: Fix the cancel button bug
        file_name = null ;
        file = null ;
	}
    return file_name;
  }

}

/** This class is used to filter out all of the file extensions
	* that aren't wanted.
	*
	* @author Jeremy Cloud
	*
	* @version 1.0.0
	*
	* @see java.awt.FileDialog
	*/


class FileNameFilter implements FilenameFilter {

  protected Vector extensions = new Vector( 8 );
  protected String default_extension;

	/** Constructs the list of extensions out of a string of comma-separated
		* elements, each of which represents one extension.
		*
		* @param ext_str  the list of comma-separated extensions
		*
		*/
  public FileNameFilter( String ext_str ) {
    boolean proceed = true;
    int start = 0, end;

    while( proceed ) {
      end = ext_str.indexOf( ',', start );
      if( end > 0 ) {
        String extension = ext_str.substring( start, end );
        extensions.addElement( extension );

        start = end + 1;
        while( ext_str.charAt( start ) == ' ' ) start++;
        if( default_extension == null )
          default_extension = extension;
      }
      else
        proceed = false;
    }

    String extension = ext_str.substring( start );
    extensions.addElement( extension );

    if( default_extension == null )
      default_extension = extension;
  }

	/** determines if the filename is an acceptable one. If a
		* filename ends with one of the extensions the filter was
		* initialized with, then the function returns true. if not,
		* the function returns false.
		*
		*	@param dir  the directory the file is in
		*
		* @param name  the filename of the file
		*
		*	@return  true if the filename has a valid extension, false otherwise
		*/
  public boolean accept( File dir, String name ) {
    for( int i = 0; i < extensions.size(); i++ ) {
      if( name.endsWith( (String) extensions.elementAt( i ) ) ) {

        return true;
      }
    }

    return false;
  }

	/** constructs a real filename from the given directory and the
		* wildcard - filename.
		*
		*	@param dir  the full path to the directory the file is in
		*
		* @param fname  the name of the file, which may include wildcards
		*
		* @return  the full path of the file
		*/
  public String constructFileName( String dir, String fname ) {
    String name;

    if( fname.endsWith( "*.*" ) ) {
      fname = fname.substring( 0, fname.indexOf( '.' ) );
    }

    name = (dir == null ? "" : dir) + fname;

    if( fname.indexOf( '.' ) < 0 )
      name += "." + default_extension;
    return name;
  }

	/** Returns the default extension.
		*
		* @return  the default extension
		*/
  public String getDefaultExtension() {
    return default_extension;
  }


  public void setDefaultExtension( String ext ) {
    default_extension = ext;
  }


  /**Returns a semi-colon-delimited list.
   * @return  a semi-colon-delimited list of the valid extensions. E.g., "*.gif; *.jpg; *.bmp"
   */
  public String getExtensionString() {
    Vector exts = this.extensions;
    if( exts ==null || exts.size() == 0 )
      return "";
    else {
      String ret = "";
      int n = exts.size();
      for( int i=0; i < n; i++ ) {
        ret = ret + "*." + (String)exts.elementAt( i );
        if( i != n-1 )
          ret += "; ";
      }
      return ret;
    }
  }
}