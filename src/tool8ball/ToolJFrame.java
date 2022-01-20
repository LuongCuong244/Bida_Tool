/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool8ball;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Administrator
 */
public class ToolJFrame extends JFrame implements KeyListener {

    private JPanelMenu jPanelMenu;
    private JPanelTable jPanelTable;
    public static Point pointJFrame = new Point(460, 290);
    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 500;
    private KeyBoard keyBoard;

    public ToolJFrame() {
        if (Data.readData()) {
            this.setBounds(pointJFrame.x, pointJFrame.y, SCREEN_WIDTH, SCREEN_HEIGHT);
        } else {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            this.setBounds(((int) dimension.getWidth() - SCREEN_WIDTH) / 2, ((int) dimension.getHeight() - SCREEN_HEIGHT) / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
            pointJFrame = new Point(((int) dimension.getWidth() - SCREEN_WIDTH) / 2, ((int) dimension.getHeight() - SCREEN_HEIGHT) / 2);
        }
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.00f));
        this.setLayout(new BorderLayout(0, Properties.space));
        this.setAlwaysOnTop(Properties.isOnTop);

        keyBoard = new KeyBoard(this);
        jPanelMenu = new JPanelMenu(this);
        jPanelTable = new JPanelTable(this);
        this.add(jPanelMenu, BorderLayout.NORTH);
        this.add(jPanelTable, BorderLayout.CENTER);
        this.addKeyListener(this);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Data.writeData();
            }
        });
        this.setVisible(true);
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                if (Properties.isAlwaysFocus) {
                    ToolJFrame.this.toFront();
                    ToolJFrame.this.requestFocus();
                    ToolJFrame.this.setState(JFrame.ICONIFIED);
                    ToolJFrame.this.setState(JFrame.NORMAL);
                }
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
            }
        });
    }

    public void setBoundsToolFrame(int top, int bottom, int left, int right) {
        Point pJFrame = this.getLocation();
        int width = this.getWidth() + left + right;
        int height = this.getHeight() + top + bottom;
        pJFrame.setLocation(pJFrame.x - left, pJFrame.y - top);
        this.setBounds(pJFrame.x, pJFrame.y, width, height);
        ToolJFrame.pointJFrame.setLocation(pJFrame);
        ToolJFrame.SCREEN_HEIGHT = ToolJFrame.SCREEN_HEIGHT + top + bottom;
        ToolJFrame.SCREEN_WIDTH = ToolJFrame.SCREEN_WIDTH + left + right;
    }

    public JPanelMenu getjPanelMenu() {
        return jPanelMenu;
    }

    public void setjPanelMenu(JPanelMenu jPanelMenu) {
        this.jPanelMenu = jPanelMenu;
    }

    public JPanelTable getjPanelTable() {
        return jPanelTable;
    }

    public void setjPanelTable(JPanelTable jPanelTable) {
        this.jPanelTable = jPanelTable;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyBoard.Pressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        new ToolJFrame();
    }
}
