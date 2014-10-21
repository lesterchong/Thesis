/*
 * OggVorbisDialog.
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
import java.awt.TextArea;
import java.text.DecimalFormat;
import java.util.Vector;

import javazoom.jlgui.player.amp.tag.OggVorbisInfo;

/**
 * OggVorbisDialog class implements a DialogBox to diplay OggVorbis info.
 */
public class OggVorbisDialog extends TagInfoDialog
{
  private OggVorbisInfo _vorbisinfo = null;

  /**
   * Contructor.
   */
  public OggVorbisDialog(String title, int sx, int sy, OggVorbisInfo ovi)
  {
	this(title,-1,-1,sx,sy,ovi);
  }
  
  /**
   * Contructor.
   */
  public OggVorbisDialog(String title, int x, int y, int sx, int sy, OggVorbisInfo ovi)
  {
    super(title);
    _vorbisinfo = ovi;
    this.setSize(sx, sy);
	if ((x != -1) && (y != -1)) this.setLocation(x, y);
    // Polis : Comment out to fix a bug under XWindow
    //this.setResizable(false);
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setFont(new Font("Helvetica", Font.PLAIN, 11));
    this.setLayout(gridbag);
    c.fill = GridBagConstraints.BOTH;
    // File/URL Label.
    c.weightx = 1.0;
    c.gridwidth = GridBagConstraints.REMAINDER;
    Label flabel = new Label("File/URL : "+_vorbisinfo.getLocation());
    gridbag.setConstraints(flabel,c);
    this.add(flabel);
    // Standard Tags.
    c.weightx = 1.0;
    c.gridwidth = GridBagConstraints.RELATIVE;
    Label labeltag = new Label("Standard Tags");
    gridbag.setConstraints(labeltag,c);
    this.add(labeltag);
    // File/URL TextField.
    Label labelinfo = new Label("File/Stream info");
    c.weightx = 1.0;
    c.gridwidth = GridBagConstraints.REMAINDER;
    gridbag.setConstraints(labelinfo,c);
    this.add(labelinfo);
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
    // Ogg Vorbis Comments.
    Panel leftPan = new Panel();
    leftPan.setLayout(new GridLayout(1, 1, 0, 1));
    TextArea area = new TextArea();
    leftPan.add(area);
    if ((_vorbisinfo.getTitle() != null) && ((!_vorbisinfo.getTitle().equals("")))) area.append("Title="+_vorbisinfo.getTitle()+"\n");
    if ((_vorbisinfo.getArtist() != null) && ((!_vorbisinfo.getArtist().equals("")))) area.append("Artist="+_vorbisinfo.getArtist()+"\n");
    if ((_vorbisinfo.getAlbum() != null) && ((!_vorbisinfo.getAlbum().equals("")))) area.append("Album="+_vorbisinfo.getAlbum()+"\n");
    if (_vorbisinfo.getTrack() > 0) area.append("Track="+_vorbisinfo.getTrack()+"\n");
    if ((_vorbisinfo.getYear() != null) && ((!_vorbisinfo.getYear().equals("")))) area.append("Year="+_vorbisinfo.getYear()+"\n");
    if ((_vorbisinfo.getGenre() != null) && ((!_vorbisinfo.getGenre().equals("")))) area.append("Genre="+_vorbisinfo.getGenre()+"\n");
    Vector comments = _vorbisinfo.getComment();
    for (int i=0;i<comments.size(); i++)
    {
      area.append((String)comments.elementAt(i)+"\n");
    }
    // Ogg Vorbis File info.
    Panel rightPan = new Panel();
    GridBagLayout rightgridbag = new GridBagLayout();
    rightPan.setLayout(rightgridbag);
    GridBagConstraints rightc = new GridBagConstraints();
    rightc.gridx = 0; // note: gridy == RELATIVE
    rightc.fill = GridBagConstraints.HORIZONTAL;
    Label rlabel = null;
    int secondsAmount = Math.round(_vorbisinfo.getPlayTime());
    int minutes = (int) (secondsAmount / 60);
    int seconds = (int) (secondsAmount - (minutes * 60));
    rightPan.add(rlabel = new Label("Length : "+minutes+":"+seconds));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Average bitrate : "+_vorbisinfo.getAverageBitrate()/1000+" kbps"));
    rightgridbag.setConstraints(rlabel, rightc);
    DecimalFormat df = new DecimalFormat("#,###,###");
    rightPan.add(rlabel = new Label("File size : "+df.format(_vorbisinfo.getSize())+" bytes"));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Nominal bitrate : "+(_vorbisinfo.getBitRate()/1000)+" kbps"));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Max bitrate : "+_vorbisinfo.getMaxBitrate()/1000+" kbps"));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Min bitrate : "+_vorbisinfo.getMinBitrate()/1000+" kbps"));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Channel : "+_vorbisinfo.getChannels()));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Sampling rate : "+_vorbisinfo.getSamplingRate()+" Hz"));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Serial number : "+_vorbisinfo.getSerial()));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Version : "+_vorbisinfo.getVersion()));
    rightgridbag.setConstraints(rlabel, rightc);
    rightPan.add(rlabel = new Label("Vendor : "+_vorbisinfo.getVendor()));
    rightgridbag.setConstraints(rlabel, rightc);
    mainPan.add(leftPan);
    mainPan.add(rightPan);
    selectPan.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 4));
    selectPan.add(_close);
  }

  /**
   * Returns VorbisInfo.
   */
  public OggVorbisInfo getOggVorbisInfo()
  {
    return _vorbisinfo;
  }

}
