/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool8ball;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class KeyBoard {

    private ToolJFrame toolJFrame;
    public static boolean tryLine = false;
    public static boolean twoShootLine = false;
    public static int direction = 0;

    public KeyBoard(ToolJFrame toolJFrame) {
        this.toolJFrame = toolJFrame;
    }

    public void Pressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_SPACE: {
                Point pMouse = MouseInfo.getPointerInfo().getLocation();
                Point pJFrame = toolJFrame.getLocation();
                double x = pMouse.getX() - pJFrame.getX();
                double y = pMouse.getY() - pJFrame.getY() - toolJFrame.getjPanelMenu().getHeight() - Properties.space;
                if (x >= 0 && y >= 0) {
                    tryLine = false;
                    twoShootLine = false;
                    toolJFrame.getjPanelTable().getListPointTwoShootLine().clear();
                    JPanelTable.pointSelected = new Point((int) x, (int) y);
                    toolJFrame.getjPanelTable().takeAScreenShot();
                    toolJFrame.getjPanelTable().getjPanelImage().repaint();

                    toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
                    toolJFrame.getjPanelTable().setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(toolJFrame, "Vị trí trỏ chuột không nằm trong khung !");
                }
                break;
            }
            case KeyEvent.VK_Q: {
                toolJFrame.getjPanelMenu().clickBtnOne();
                break;
            }
            case KeyEvent.VK_W: {
                toolJFrame.getjPanelMenu().clickBtnTwo();
                break;
            }
            case KeyEvent.VK_E: {
                toolJFrame.getjPanelMenu().clickBtnThree();
                break;
            }
            case KeyEvent.VK_A: {
                toolJFrame.getjPanelMenu().clickBtnFour();
                break;
            }
            case KeyEvent.VK_S: {
                toolJFrame.getjPanelMenu().clickBtnFive();
                break;
            }
            case KeyEvent.VK_D: {
                toolJFrame.getjPanelMenu().clickBtnSix();
                break;
            }
            case KeyEvent.VK_R: {
                toolJFrame.getjPanelMenu().clickBtnAll();
                break;
            }
            case KeyEvent.VK_X: {
                if (toolJFrame.getjPanelMenu().getJrbAllLine().isSelected()) { // nếu đang được chọn thì thay đổi hướng
                    switch (JPanelTable.hole) {
                        case 1: {
                            toolJFrame.getjPanelMenu().clickBtnOne();
                            break;
                        }
                        case 3: {
                            toolJFrame.getjPanelMenu().clickBtnThree();
                            break;
                        }
                        case 4: {
                            toolJFrame.getjPanelMenu().clickBtnFour();
                            break;
                        }
                        case 6: {
                            toolJFrame.getjPanelMenu().clickBtnSix();
                            break;
                        }
                    }
                }
                JPanelMenu.isOneLine = false;
                toolJFrame.getjPanelMenu().getJrbAllLine().setSelected(true);
                break;
            }
            case KeyEvent.VK_Z: {
                JPanelMenu.isOneLine = true;
                toolJFrame.getjPanelMenu().getJrbOneLine().setSelected(true);
                break;
            }
            case KeyEvent.VK_T: {
                Point pMouse = MouseInfo.getPointerInfo().getLocation();
                Point pJFrame = toolJFrame.getLocation();
                double x = pMouse.getX() - pJFrame.getX();
                double y = pMouse.getY() - pJFrame.getY() - toolJFrame.getjPanelMenu().getHeight() - Properties.space;
                if (x >= 0 && y >= 0) {
                    tryLine = true;
                    JPanelTable.pointTryRoad = new Point((int) x, (int) y);
                    toolJFrame.getjPanelTable().getjPanelImage().repaint();

                    toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
                    toolJFrame.getjPanelTable().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(toolJFrame, "Vị trí trỏ chuột không nằm trong khung !");
                }
                break;
            }
            case KeyEvent.VK_O: {
                toolJFrame.getjPanelMenu().getjFSetting().update();
                toolJFrame.setAlwaysOnTop(false);
                if(Properties.isAlwaysFocus){
                    JPanelMenu.isFocus = true;
                }
                Properties.isAlwaysFocus = false;
                toolJFrame.getjPanelMenu().getjFSetting().setVisible(true);
                break;
            }
            case KeyEvent.VK_M: {
                int input = JOptionPane.showConfirmDialog(null, "Bạn muốn đóng ứng dụng ?", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
                if (input == 0) {
                    Data.writeData();
                    System.exit(0);
                }
                break;
            }
            case KeyEvent.VK_Y: {
                JPanelTable.listPoint.add(new Point(JPanelTable.pointAddY.x, -JPanelTable.pointAddY.y));
                break;
            }
            case KeyEvent.VK_U: {
                JPanelTable.listPoint.clear();
                break;
            }
            case KeyEvent.VK_G: {
                Point pMouse = MouseInfo.getPointerInfo().getLocation();
                Point pJFrame = toolJFrame.getLocation();
                double x = pMouse.getX() - pJFrame.getX();
                double y = pMouse.getY() - pJFrame.getY() - toolJFrame.getjPanelMenu().getHeight() - Properties.space;
                if (x >= 0 && y >= 0) {
                    twoShootLine = true;
                    toolJFrame.getjPanelTable().getListPointTwoShootLine().clear();
                    JPanelTable.pointTwoLineStart = new Point((int) x, (int) y);
                    toolJFrame.getjPanelTable().getjPanelImage().repaint();

                    toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
                    toolJFrame.getjPanelTable().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(toolJFrame, "Vị trí trỏ chuột không nằm trong khung !");
                }
                break;
            }
            case KeyEvent.VK_H: {
                direction++;
                if (direction >= toolJFrame.getjPanelTable().getListPointTwoShootLine().size()) {
                    direction = 0;
                }
                toolJFrame.getjPanelTable().getjPanelImage().repaint();

                toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
                toolJFrame.getjPanelTable().setVisible(true);
                break;
            }
            case KeyEvent.VK_K:{
                if(Properties.isAlwaysFocus){
                    JOptionPane.showMessageDialog(toolJFrame, "Đã tắt luôn luôn nhận bàn phím!");
                    Properties.isAlwaysFocus = false;
                }else{
                    JOptionPane.showMessageDialog(toolJFrame, "Đã bật luôn luôn nhận bàn phím!");
                    Properties.isAlwaysFocus = true;
                }
            }
        }
    }
}
