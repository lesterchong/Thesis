/*
 * EmptyDialog.
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

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

import javazoom.jlgui.player.amp.tag.TagInfo;

/**
 * EmptyDialog class implements NotAvailable info.
 */
public class EmptyDialog extends TagInfoDialog
{

  private TagInfo _info = null;
	
  /**
   * Contructor.
   */
  public EmptyDialog(String title, int sx, int sy, TagInfo mi)
  {
  	this(title,-1,-1,sx,sy,mi);	
  }
  
  /**
   * Contructor.
   */
  public EmptyDialog(String title, int x, int y, int sx, int sy, TagInfo mi)
  {
    super(title);
    _info = mi;
    this.setSize(sx, sy);
    if ((x != -1) && (y != -1)) this.setLocation(x, y);
    // Polis : Comment out to fix a bug under XWindow
    //this.setResizable(false);
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setFont(new Font("Helvetica", Font.PLAIN, 12));
    this.setLayout(gridbag);
    c.fill = GridBagConstraints.BOTH;
    // File/URL Label.
    c.weightx = 1.0;
    c.gridwidth = GridBagConstraints.REMAINDER;
    Label flabel = new Label("No Information Available",Label.CENTER);
    gridbag.setConstraints(flabel,c);
    this.add(flabel);
    // Standard Tags.
    c.weightx = 1.0;
    c.gridwidth = GridBagConstraints.RELATIVE;
    //Label labeltag = new Label("Standard Tags");
    //gridbag.setConstraints(labeltag,c);
    //this.add(labeltag);
    // File/URL TextField.
    //Label labelinfo = new Label("File/Stream info");
    c.weightx = 1.0;
    c.gridwidth = GridBagConstraints.REMAINDER;
    //gridbag.setConstraints(labelinfo,c);
    //this.add(labelinfo);
    // Main Panel.
    c.weightx = 1.0;
    c.gridwidth = GridBagConstraints.REMAINDER;
    Panel mainPan = new Panel();
    gridbag.setConstraints(mainPan,c);
    this.add(mainPan);
    Panel selectPan = new Panel();
    c.weightx = 1.0;
    gridbag.setConstraints(selectPan,c);
    this.add(selectPan);
    mainPan.setLayout(new GridLayout(1, 2, 0, 1));
    // Left.
    Panel leftPan = new Panel();
    leftPan.setLayout(new GridLayout(1, 1, 0, 1));
	// Right
    Panel rightPan = new Panel();
    GridBagLayout rightgridbag = new GridBagLayout();
    rightPan.setLayout(rightgridbag);
    GridBagConstraints rightc = new GridBagConstraints();
    rightc.gridx = 0; // note: gridy == RELATIVE
    rightc.fill = GridBagConstraints.HORIZONTAL;
    mainPan.add(leftPan);
    mainPan.add(rightPan);
    selectPan.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 4));
    selectPan.add(_close);
  }
  
  /**
   * Returns TagInfo.
   */
  public TagInfo getTagInfo()
  {
	return _info;
  }
}
