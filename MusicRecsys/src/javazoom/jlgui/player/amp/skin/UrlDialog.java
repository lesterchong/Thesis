/*
 * UrlDialog.
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

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UrlDialog class implements a DialogBox to get an URL.
 *
 * @author	E.B from JavaZOOM
 *
 * Homepage : http://www.javazoom.net
 */
public class UrlDialog extends Dialog implements ActionListener
{
	private String		_url = null;
	private Button		_open = null;
	private Button 		_cancel = null;
	private Label		_label1 = null;
	private Label		_label2 = null;
	private TextField	_textfield = null;

    /**
     * Contructor.
     */
    public UrlDialog(String title, int x, int y, int sx, int sy, String url)
    {
        super(new Frame(),title,true);
        _url = url;
        this.setSize(sx,sy);
        this.setLocation(x,y);
        _label1 = new Label("Enter an Internet location to open here :");
        _label2 = new Label("For example : http://www.server.com:8000");
        if (_url == null) _textfield = new TextField();
        else _textfield = new TextField(_url);
        _open = new Button("Open");
        _cancel = new Button("Cancel");
        _open.addActionListener(this);
        _cancel.addActionListener(this);
		// Polis : Comment out to fix a bug under XWindow
		//this.setResizable(false);
		this.setLayout(new GridLayout(4,1,0,1));
		this.add(_label1);
		this.add(_label2);
		this.add(_textfield);
		Panel selectPan = new Panel();
		selectPan.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		selectPan.add(_open);
		selectPan.add(_cancel);
		this.add(selectPan);
    }

    /**
     * Returns URL.
     */
    public String getURL()
    {
		return _url;
	}

    /**
     * Returns filename.
     */
    public String getFile()
    {
		return _url;
	}

    /**
     * ActionListener implementation.
     */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == _open)
		{
			_url = _textfield.getText();
			this.dispose();
		}
		else if (e.getSource() == _cancel)
		{
			_url = null;
			this.dispose();
		}
	}
}
