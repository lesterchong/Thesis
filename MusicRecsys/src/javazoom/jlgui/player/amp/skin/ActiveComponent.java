/*
 * ActiveComponent.
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

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


/**
 * ActiveComponent is used to manage two state graphical components with optional
 * checkbox property.
 */
public class ActiveComponent extends Canvas implements SkinComponent
{ 
	private Image offScreenImage = null;
	private Graphics offScreenGraphics = null;
   
	private Image releasedImage;
	private Image pressedImage;
	private long actionMask;
	private boolean pressed = false;
	private int mouseX, mouseY;
	private int button;
	private ActionListener actionListener;
	private String actionCommand;
	private boolean checkbox = false;
	private boolean checkboxState = false; // True = selected.
	private PopupMenu popup = null;


	/**
	 * Contructor that create the active Component with 2 images and checkbox property.
	 */
	public ActiveComponent(Image releasedImage, Image pressedImage, long theMask, boolean checkbox, boolean checkboxinitstate)
	{
	  this.releasedImage = releasedImage;
	  this.pressedImage = pressedImage;
	  this.actionMask = theMask;
	  this.checkbox = checkbox;
	  this.pressed = checkboxinitstate;
	  this.checkboxState = checkboxinitstate;
	  setSize(releasedImage.getWidth(null), releasedImage.getHeight(null));
	  enableEvents(theMask);
	}
  
	/**
	 * Contructor that create the active Component with 2 images.
	 */
	public ActiveComponent(Image releasedImage, Image pressedImage, long theMask)
	{
	  this(releasedImage, pressedImage, theMask, false, false);
	}


	public void paint(Graphics g)
	{	
	  if (offScreenImage == null)
	  {
		  offScreenImage = createImage(getSize().width,getSize().height);
		  offScreenGraphics = offScreenImage.getGraphics();
		  update(g);		
	  }
	  if ( (offScreenImage != null) && (g != null))
	  {
		   g.drawImage(offScreenImage, 0, 0, this);
	  }
	  /*g.setColor(Color.black);
	  int width = getSize().width, height = getSize().height;
	  if (pressed == false) g.drawImage(releasedImage, 0, 0, width, height, this);
	  else g.drawImage(pressedImage, 0, 0, width, height, this);*/
	}

	public void update(Graphics graphics)
	{
	  if (offScreenGraphics != null)
	  {
		  offScreenGraphics.setColor(Color.black);
		  int width = getSize().width, height = getSize().height;
		  if (pressed == false) offScreenGraphics.drawImage(releasedImage, 0, 0, width, height, this);
		  else offScreenGraphics.drawImage(pressedImage, 0, 0, width, height, this);
	  }
	  paint(graphics);	
	}
  
	public Dimension getPreferredSize()
	{
	  return getSize();
	}

	public void processEvent(AWTEvent e)
	{
	  button = ((MouseEvent) e).getModifiers();
	  if (e.getID() == MouseEvent.MOUSE_PRESSED)
	  {
		if (checkbox == true)
		{

		} 
		else pressed = true;
		repaint();
	  } 
	  else if (e.getID() == MouseEvent.MOUSE_RELEASED)
	  {
		if (checkbox == true)
		{
		  if (checkboxState == true)
		  {
			checkboxState = false;
			pressed = false;
		  } 
		  else
		  {
			checkboxState = true;
			pressed = true;
		  }
		} 
		else pressed = false;
		repaint();
		fireEvent();
	  } 
	  else if (e.getID() == MouseEvent.MOUSE_DRAGGED)
	  {
		mouseX = ((MouseEvent) e).getX();
		mouseY = ((MouseEvent) e).getY();
		pressed = true;
		repaint();
		fireEvent();
	  }
	  super.processEvent(e);
	}

	public void setActionCommand(String actionCommand)
	{
	  this.actionCommand = actionCommand;
	}

	public void addActionListener(ActionListener l)
	{
	  actionListener = AWTEventMulticaster.add(actionListener, l);
	}

	public void removeActionListener(ActionListener l)
	{
	  actionListener = AWTEventMulticaster.remove(actionListener, l);
	}

	public void fireEvent()
	{
	  if (actionListener != null)
	  {
		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand);
		actionListener.actionPerformed(event);
	  }
	}

	public void setPopup(PopupMenu pop)
	{
	  popup = pop;
	  this.add(popup);
	}

	public void processMouseEvent(MouseEvent e)
	{
	  if (e.isPopupTrigger())
	  {
		if (popup != null) popup.show(e.getComponent(), e.getX(), e.getY());
	  }
	  super.processMouseEvent(e);
	}

	public final int getMouseX()
	{
	  return mouseX;
	}

	public final int getMouseY()
	{
	  return mouseY;
	}

	public final boolean isMousePressed()
	{
	  return pressed;
	}

	public final int getMouseButton()
	{
	  return button;
	}

	public boolean getCheckboxState()
	{
	  return checkboxState;
	}
	
	public void display()
	{
		paintAll(getGraphics());
	}

}
