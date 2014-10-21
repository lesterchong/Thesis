/*
 * TagInfoDialog.
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

package javazoom.jlgui.player.amp.tag.ui;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class define a Dialog for TagiInfo to display.
 */
public class TagInfoDialog extends Dialog implements ActionListener
{
	protected Button _close = null;
	
	/**
	 * 
	 */
	public TagInfoDialog(String title)
	{
		super(new Frame(), title, true);
		_close = new Button("Close");
		_close.addActionListener(this);
	}
	
	/**
	 * ActionListener implementation.
	 */
	public void actionPerformed(ActionEvent e)
	{
	  if (e.getSource() == _close)
	  {
		this.dispose();
	  }
	}	
}
