/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool8ball;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JPanelMenu extends JPanel implements ActionListener {

    public static boolean isFocus = false;
    
    private ToolJFrame toolJFrame;
    private Color colorText = Color.WHITE;
    private Color colorBtnHole = new Color(159, 0, 163);
    public static boolean isOneLine = true;

    private JPanel jpnResize;
    private JLabel txtPlusTop, txtPlusBottom, txtPlusRight, txtPlusLeft;
    private JTextField edtTop, edtBottom, edtRight, edtLeft;
    private JButton btnResize;

    private JPanel jpnSelectedHole;
    private JLabel txtChooseHole;
    private JButton btnHoleOne, btnHoleTwo, btnHoleThree, btnHoleFour, btnHoleFive, btnHoleSix, btnAll;
    private boolean selectedOne = false, selectedTwo = false, selectedThree = false, selectedFour = false, selectedFive = false, selectedSix = false, selectedAll = false;

    private JPanel jpnSelectedLine;
    private JRadioButton jrbOneLine;
    private JRadioButton jrbAllLine;
    private ButtonGroup group;

    private JPanel jpnGroup;
    private JButton btnOption;
    private JButton btnExit;

    private JFrameSetting jFSetting;

    public JPanelMenu(ToolJFrame toolJFrame) {
        this.toolJFrame = toolJFrame;
        this.setBounds(0, 0, ToolJFrame.SCREEN_WIDTH, 150);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        this.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        this.setBackground(new Color(6, 57, 112));
        this.setFocusable(true);
        this.addKeyListener(toolJFrame);

        jpnResize = new JPanel();
        jpnResize.setLayout(new GridLayout(4, 3, 0, 5));
        jpnResize.setBackground(new Color(6, 57, 112));
        this.add(jpnResize);
        setComponentResize();

        jpnSelectedHole = new JPanel();
        jpnSelectedHole.setLayout(new GridLayout(4, 3, 5, 5));
        jpnSelectedHole.setBackground(new Color(6, 57, 112));
        jpnSelectedHole.setBorder(BorderFactory.createTitledBorder(""));
        Border border = jpnSelectedHole.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnSelectedHole.setBorder(new CompoundBorder(border, margin));
        this.add(jpnSelectedHole, FlowLayout.RIGHT);
        setComponentSelectedHole();

        jpnSelectedLine = new JPanel();
        jpnSelectedLine.setLayout(new GridLayout(3, 1, 5, 5));
        jpnSelectedLine.setBackground(new Color(6, 57, 112));
        this.add(jpnSelectedLine);
        setComponentSelectedLine();

        jpnGroup = new JPanel();
        jpnGroup.setLayout(new GridLayout(3, 1, 5, 5));
        jpnGroup.setBackground(new Color(6, 57, 112));
        this.add(jpnGroup);
        setComponentGroup();

        clickBtnOne();
        jFSetting = new JFrameSetting(toolJFrame);
        readTime();
    }

    private void setComponentResize() {
        txtPlusTop = new JLabel("  Trên   ", SwingConstants.CENTER);
        txtPlusTop.setSize(100, 30);
        txtPlusTop.setForeground(colorText);
        jpnResize.add(txtPlusTop);

        JLabel plus1 = new JLabel("+", SwingConstants.CENTER);
        plus1.setSize(100, 30);
        plus1.setForeground(colorText);
        jpnResize.add(plus1);

        edtTop = new JTextField();
        edtTop.setSize(100, 30);
        edtTop.setText("0");
        edtTop.setBorder(BorderFactory.createEmptyBorder());
        jpnResize.add(edtTop);

        txtPlusBottom = new JLabel("Dưới", SwingConstants.CENTER);
        txtPlusBottom.setSize(100, 30);
        txtPlusBottom.setForeground(colorText);
        jpnResize.add(txtPlusBottom);

        JLabel plus2 = new JLabel("+", SwingConstants.CENTER);
        plus2.setSize(100, 30);
        plus2.setForeground(colorText);
        jpnResize.add(plus2);

        edtBottom = new JTextField();
        edtBottom.setSize(100, 30);
        edtBottom.setText("0");
        edtBottom.setBorder(BorderFactory.createEmptyBorder());
        jpnResize.add(edtBottom);

        txtPlusLeft = new JLabel("Trái", SwingConstants.CENTER);
        txtPlusLeft.setSize(100, 30);
        txtPlusLeft.setForeground(colorText);
        jpnResize.add(txtPlusLeft);

        JLabel plus3 = new JLabel("+", SwingConstants.CENTER);
        plus3.setSize(100, 30);
        plus3.setForeground(colorText);
        jpnResize.add(plus3);

        edtLeft = new JTextField();
        edtLeft.setSize(100, 30);
        edtLeft.setText("0");
        edtLeft.setBorder(BorderFactory.createEmptyBorder());
        jpnResize.add(edtLeft);

        txtPlusRight = new JLabel("Phải", SwingConstants.CENTER);
        txtPlusRight.setSize(100, 30);
        txtPlusRight.setForeground(colorText);
        jpnResize.add(txtPlusRight);

        JLabel plus4 = new JLabel("+", SwingConstants.CENTER);
        plus4.setSize(100, 30);
        plus4.setForeground(colorText);
        jpnResize.add(plus4);

        edtRight = new JTextField();
        edtRight.setSize(100, 30);
        edtRight.setText("0");
        edtRight.setBorder(BorderFactory.createEmptyBorder());
        jpnResize.add(edtRight);

        btnResize = new JButton("Resize");
        btnResize.setSize(300, 30);
        btnResize.setBackground(Color.GREEN);
        btnResize.setActionCommand("btnResize");
        btnResize.addActionListener(this);
        btnResize.addKeyListener(toolJFrame);
        this.add(btnResize);
    }

    private void setComponentSelectedHole() {

        JLabel txt0 = new JLabel("");
        jpnSelectedHole.add(txt0);

        txtChooseHole = new JLabel("CHỌN LỖ NGẮM:", SwingConstants.CENTER);
        txtChooseHole.setSize(100, 30);
        txtChooseHole.setForeground(new Color(255, 255, 255));
        txtChooseHole.setFont(txtChooseHole.getFont().deriveFont(15.f));
        jpnSelectedHole.add(txtChooseHole);

        JLabel txt1 = new JLabel("");
        jpnSelectedHole.add(txt1);

        btnHoleOne = new JButton("Lỗ 1    (Q)");
        btnHoleOne.setSize(100, 30);
        btnHoleOne.setBackground(new Color(255, 210, 83));
        btnHoleOne.setActionCommand("btnOne");
        btnHoleOne.addActionListener(this);
        btnHoleOne.setFocusable(false);
        btnHoleOne.addKeyListener(toolJFrame);
        jpnSelectedHole.add(btnHoleOne);

        btnHoleTwo = new JButton("Lỗ 2    (W)");
        btnHoleTwo.setSize(100, 30);
        btnHoleTwo.setBackground(new Color(255, 210, 83));
        btnHoleTwo.setActionCommand("btnTwo");
        btnHoleTwo.addActionListener(this);
        btnHoleTwo.setFocusable(false);
        btnHoleTwo.addKeyListener(toolJFrame);
        jpnSelectedHole.add(btnHoleTwo);

        btnHoleThree = new JButton("Lỗ 3    (E)");
        btnHoleThree.setSize(100, 30);
        btnHoleThree.setBackground(new Color(255, 210, 83));
        btnHoleThree.setActionCommand("btnThree");
        btnHoleThree.addActionListener(this);
        btnHoleThree.setFocusable(false);
        btnHoleThree.addKeyListener(toolJFrame);
        jpnSelectedHole.add(btnHoleThree);

        btnHoleFour = new JButton("Lỗ 4    (A)");
        btnHoleFour.setSize(100, 30);
        btnHoleFour.setBackground(new Color(255, 210, 83));
        btnHoleFour.setActionCommand("btnFour");
        btnHoleFour.addActionListener(this);
        btnHoleFour.setFocusable(false);
        btnHoleFour.addKeyListener(toolJFrame);
        jpnSelectedHole.add(btnHoleFour);

        btnHoleFive = new JButton("Lỗ 5    (S)");
        btnHoleFive.setSize(100, 30);
        btnHoleFive.setBackground(new Color(255, 210, 83));
        btnHoleFive.setActionCommand("btnFive");
        btnHoleFive.addActionListener(this);
        btnHoleFive.setFocusable(false);
        btnHoleFive.addKeyListener(toolJFrame);
        jpnSelectedHole.add(btnHoleFive);

        btnHoleSix = new JButton("Lỗ 6    (D)");
        btnHoleSix.setSize(100, 30);
        btnHoleSix.setBackground(new Color(255, 210, 83));
        btnHoleSix.setActionCommand("btnSix");
        btnHoleSix.addActionListener(this);
        btnHoleSix.setFocusable(false);
        btnHoleSix.addKeyListener(toolJFrame);
        jpnSelectedHole.add(btnHoleSix);

        JLabel txt2 = new JLabel("");
        jpnSelectedHole.add(txt2);

        btnAll = new JButton("Tất cả (R)");
        btnAll.setSize(100, 30);
        btnAll.setBackground(new Color(255, 210, 83));
        btnAll.setActionCommand("btnAll");
        btnAll.addActionListener(this);
        btnAll.setFocusable(false);
        btnAll.addKeyListener(toolJFrame);
        jpnSelectedHole.add(btnAll);
    }

    private void setComponentSelectedLine() {

        JLabel t = new JLabel("");
        jpnSelectedLine.add(t);

        jrbOneLine = new JRadioButton("Một đường    (Z)");
        jrbOneLine.addKeyListener(toolJFrame);
        jrbOneLine.setSelected(true);
        jrbOneLine.setFocusable(false);
        jrbOneLine.setBackground(Color.cyan);
        jrbOneLine.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jrbOneLine.isSelected()) {
                    JPanelMenu.isOneLine = true;
                    jrbOneLine.setBackground(Color.cyan);
                    jrbAllLine.setBackground(Color.WHITE);
                }
                if (JPanelTable.pointSelected != null) {
                    toolJFrame.getjPanelTable().getjPanelImage().repaint();

                    toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
                    toolJFrame.getjPanelTable().setVisible(true);
                }
            }
        });
        jpnSelectedLine.add(jrbOneLine);

        jrbAllLine = new JRadioButton("Cân băng    (X)");
        jrbAllLine.addKeyListener(toolJFrame);
        jrbAllLine.setFocusable(false);
        jrbAllLine.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jrbAllLine.isSelected()) {
                    JPanelMenu.isOneLine = false;
                    jrbOneLine.setBackground(Color.white);
                    jrbAllLine.setBackground(Color.cyan);
                }
                if (JPanelTable.pointSelected != null) {
                    toolJFrame.getjPanelTable().getjPanelImage().repaint();

                    toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
                    toolJFrame.getjPanelTable().setVisible(true);
                }
            }
        });
        jpnSelectedLine.add(jrbAllLine);

        group = new ButtonGroup();
        group.add(jrbOneLine);
        group.add(jrbAllLine);
    }

    private void setComponentGroup() {

        JLabel t = new JLabel("");
        jpnGroup.add(t);

        btnOption = new JButton("Tùy chỉnh thêm   (O)");
        btnOption.setFocusable(false);
        btnOption.setBackground(Color.GREEN);
        btnOption.setActionCommand("btnOption");
        btnOption.addActionListener(this);
        btnOption.addKeyListener(toolJFrame);
        jpnGroup.add(btnOption);

        btnExit = new JButton("Thoát   (M)");
        btnExit.setBackground(Color.red);
        btnExit.setFocusable(false);
        btnExit.setForeground(Color.WHITE);
        btnExit.setActionCommand("btnExit");
        btnExit.addActionListener(this);
        btnExit.addKeyListener(toolJFrame);
        jpnGroup.add(btnExit);
    }

    public void clickBtnOne() {
        selectedOne = true;
        selectedTwo = false;
        selectedThree = false;
        selectedFour = false;
        selectedFive = false;
        selectedSix = false;
        selectedAll = false;

        btnHoleOne.setBackground(colorBtnHole);  // đỏ
        btnHoleTwo.setBackground(new Color(255, 210, 83));
        btnHoleThree.setBackground(new Color(255, 210, 83));
        btnHoleFour.setBackground(new Color(255, 210, 83));
        btnHoleFive.setBackground(new Color(255, 210, 83));
        btnHoleSix.setBackground(new Color(255, 210, 83));
        btnAll.setBackground(new Color(255, 210, 83));

        btnHoleOne.setForeground(Color.WHITE);
        btnHoleTwo.setForeground(Color.BLACK);
        btnHoleThree.setForeground(Color.BLACK);
        btnHoleFour.setForeground(Color.BLACK);
        btnHoleFive.setForeground(Color.BLACK);
        btnHoleSix.setForeground(Color.BLACK);
        btnAll.setForeground(Color.BLACK);

        JPanelTable.hole = 1;

        if (JPanelTable.pointSelected != null) {
            toolJFrame.getjPanelTable().getjPanelImage().repaint();

            toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
            toolJFrame.getjPanelTable().setVisible(true);
        }
    }

    public void clickBtnTwo() {
        selectedOne = false;
        selectedTwo = true;
        selectedThree = false;
        selectedFour = false;
        selectedFive = false;
        selectedSix = false;
        selectedAll = false;

        btnHoleOne.setBackground(new Color(255, 210, 83));
        btnHoleTwo.setBackground(colorBtnHole); // đỏ
        btnHoleThree.setBackground(new Color(255, 210, 83));
        btnHoleFour.setBackground(new Color(255, 210, 83));
        btnHoleFive.setBackground(new Color(255, 210, 83));
        btnHoleSix.setBackground(new Color(255, 210, 83));
        btnAll.setBackground(new Color(255, 210, 83));

        btnHoleOne.setForeground(Color.BLACK);
        btnHoleTwo.setForeground(Color.WHITE);
        btnHoleThree.setForeground(Color.BLACK);
        btnHoleFour.setForeground(Color.BLACK);
        btnHoleFive.setForeground(Color.BLACK);
        btnHoleSix.setForeground(Color.BLACK);
        btnAll.setForeground(Color.BLACK);

        JPanelTable.hole = 2;

        if (JPanelTable.pointSelected != null) {
            toolJFrame.getjPanelTable().getjPanelImage().repaint();

            toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
            toolJFrame.getjPanelTable().setVisible(true);
        }
    }

    public void clickBtnThree() {
        selectedOne = false;
        selectedTwo = false;
        selectedThree = true;
        selectedFour = false;
        selectedFive = false;
        selectedSix = false;
        selectedAll = false;

        btnHoleOne.setBackground(new Color(255, 210, 83));
        btnHoleTwo.setBackground(new Color(255, 210, 83));
        btnHoleThree.setBackground(colorBtnHole); // đỏ
        btnHoleFour.setBackground(new Color(255, 210, 83));
        btnHoleFive.setBackground(new Color(255, 210, 83));
        btnHoleSix.setBackground(new Color(255, 210, 83));
        btnAll.setBackground(new Color(255, 210, 83));

        btnHoleOne.setForeground(Color.BLACK);
        btnHoleTwo.setForeground(Color.BLACK);
        btnHoleThree.setForeground(Color.WHITE);
        btnHoleFour.setForeground(Color.BLACK);
        btnHoleFive.setForeground(Color.BLACK);
        btnHoleSix.setForeground(Color.BLACK);
        btnAll.setForeground(Color.BLACK);

        JPanelTable.hole = 3;

        if (JPanelTable.pointSelected != null) {
            toolJFrame.getjPanelTable().getjPanelImage().repaint();

            toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
            toolJFrame.getjPanelTable().setVisible(true);
        }
    }

    public void clickBtnFour() {
        selectedOne = false;
        selectedTwo = false;
        selectedThree = false;
        selectedFour = true;
        selectedFive = false;
        selectedSix = false;
        selectedAll = false;

        btnHoleOne.setBackground(new Color(255, 210, 83));
        btnHoleTwo.setBackground(new Color(255, 210, 83));
        btnHoleThree.setBackground(new Color(255, 210, 83));
        btnHoleFour.setBackground(colorBtnHole); // đỏ
        btnHoleFive.setBackground(new Color(255, 210, 83));
        btnHoleSix.setBackground(new Color(255, 210, 83));
        btnAll.setBackground(new Color(255, 210, 83));

        btnHoleOne.setForeground(Color.BLACK);
        btnHoleTwo.setForeground(Color.BLACK);
        btnHoleThree.setForeground(Color.BLACK);
        btnHoleFour.setForeground(Color.WHITE);
        btnHoleFive.setForeground(Color.BLACK);
        btnHoleSix.setForeground(Color.BLACK);
        btnAll.setForeground(Color.BLACK);

        JPanelTable.hole = 4;

        if (JPanelTable.pointSelected != null) {
            toolJFrame.getjPanelTable().getjPanelImage().repaint();

            toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
            toolJFrame.getjPanelTable().setVisible(true);
        }
    }

    public void clickBtnFive() {
        selectedOne = false;
        selectedTwo = false;
        selectedThree = false;
        selectedFour = false;
        selectedFive = true;
        selectedSix = false;
        selectedAll = false;

        btnHoleOne.setBackground(new Color(255, 210, 83));
        btnHoleTwo.setBackground(new Color(255, 210, 83));
        btnHoleThree.setBackground(new Color(255, 210, 83));
        btnHoleFour.setBackground(new Color(255, 210, 83));
        btnHoleFive.setBackground(colorBtnHole); // đỏ
        btnHoleSix.setBackground(new Color(255, 210, 83));
        btnAll.setBackground(new Color(255, 210, 83));

        btnHoleOne.setForeground(Color.BLACK);
        btnHoleTwo.setForeground(Color.BLACK);
        btnHoleThree.setForeground(Color.BLACK);
        btnHoleFour.setForeground(Color.BLACK);
        btnHoleFive.setForeground(Color.WHITE);
        btnHoleSix.setForeground(Color.BLACK);
        btnAll.setForeground(Color.BLACK);

        JPanelTable.hole = 5;

        if (JPanelTable.pointSelected != null) {
            toolJFrame.getjPanelTable().getjPanelImage().repaint();

            toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
            toolJFrame.getjPanelTable().setVisible(true);
        }
    }

    public void clickBtnSix() {
        selectedOne = false;
        selectedTwo = false;
        selectedThree = false;
        selectedFour = false;
        selectedFive = false;
        selectedSix = true;
        selectedAll = false;

        btnHoleOne.setBackground(new Color(255, 210, 83));
        btnHoleTwo.setBackground(new Color(255, 210, 83));
        btnHoleThree.setBackground(new Color(255, 210, 83));
        btnHoleFour.setBackground(new Color(255, 210, 83));
        btnHoleFive.setBackground(new Color(255, 210, 83));
        btnHoleSix.setBackground(colorBtnHole); // đỏ
        btnAll.setBackground(new Color(255, 210, 83));

        btnHoleOne.setForeground(Color.BLACK);
        btnHoleTwo.setForeground(Color.BLACK);
        btnHoleThree.setForeground(Color.BLACK);
        btnHoleFour.setForeground(Color.BLACK);
        btnHoleFive.setForeground(Color.BLACK);
        btnHoleSix.setForeground(Color.WHITE);
        btnAll.setForeground(Color.BLACK);

        JPanelTable.hole = 6;

        if (JPanelTable.pointSelected != null) {
            toolJFrame.getjPanelTable().getjPanelImage().repaint();

            toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
            toolJFrame.getjPanelTable().setVisible(true);
        }
    }

    public void clickBtnAll() {
        selectedOne = false;
        selectedTwo = false;
        selectedThree = false;
        selectedFour = false;
        selectedFive = false;
        selectedSix = false;
        selectedAll = true;

        btnHoleOne.setBackground(new Color(255, 210, 83));
        btnHoleTwo.setBackground(new Color(255, 210, 83));
        btnHoleThree.setBackground(new Color(255, 210, 83));
        btnHoleFour.setBackground(new Color(255, 210, 83));
        btnHoleFive.setBackground(new Color(255, 210, 83));
        btnHoleSix.setBackground(new Color(255, 210, 83));
        btnAll.setBackground(colorBtnHole); // đỏ

        btnHoleOne.setForeground(Color.BLACK);
        btnHoleTwo.setForeground(Color.BLACK);
        btnHoleThree.setForeground(Color.BLACK);
        btnHoleFour.setForeground(Color.BLACK);
        btnHoleFive.setForeground(Color.BLACK);
        btnHoleSix.setForeground(Color.BLACK);
        btnAll.setForeground(Color.WHITE);

        JPanelTable.hole = 7;

        if (JPanelTable.pointSelected != null) {
            toolJFrame.getjPanelTable().getjPanelImage().repaint();

            toolJFrame.getjPanelTable().setVisible(false); // méo hiểu sao phải có 2 dòng này thì nó mới xóa được.
            toolJFrame.getjPanelTable().setVisible(true);
        }
    }

    private void readTime() {
        edtTop.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (checkEdtTop().isEmpty()) {
                    edtTop.setForeground(Color.black);
                } else {
                    edtTop.setForeground(Color.red);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (checkEdtTop().isEmpty()) {
                    edtTop.setForeground(Color.black);
                } else {
                    edtTop.setForeground(Color.red);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (checkEdtTop().isEmpty()) {
                    edtTop.setForeground(Color.black);
                } else {
                    edtTop.setForeground(Color.red);
                }
            }
        });

        edtBottom.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (checkEdtBottom().isEmpty()) {
                    edtBottom.setForeground(Color.black);
                } else {
                    edtBottom.setForeground(Color.red);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (checkEdtBottom().isEmpty()) {
                    edtBottom.setForeground(Color.black);
                } else {
                    edtBottom.setForeground(Color.red);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (checkEdtBottom().isEmpty()) {
                    edtBottom.setForeground(Color.black);
                } else {
                    edtBottom.setForeground(Color.red);
                }
            }
        });

        edtLeft.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (checkEdtLeft().isEmpty()) {
                    edtLeft.setForeground(Color.black);
                } else {
                    edtLeft.setForeground(Color.red);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (checkEdtLeft().isEmpty()) {
                    edtLeft.setForeground(Color.black);
                } else {
                    edtLeft.setForeground(Color.red);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (checkEdtLeft().isEmpty()) {
                    edtLeft.setForeground(Color.black);
                } else {
                    edtLeft.setForeground(Color.red);
                }
            }
        });

        edtRight.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (checkEdtRight().isEmpty()) {
                    edtRight.setForeground(Color.black);
                } else {
                    edtRight.setForeground(Color.red);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (checkEdtRight().isEmpty()) {
                    edtRight.setForeground(Color.black);
                } else {
                    edtRight.setForeground(Color.red);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (checkEdtRight().isEmpty()) {
                    edtRight.setForeground(Color.black);
                } else {
                    edtRight.setForeground(Color.red);
                }
            }
        });
    }

    private String checkEdtTop() {
        String top = edtTop.getText().toString().trim();
        if (top.isEmpty()) {
            return "Không được bỏ trống trường ký tự!";
        }
        if (top.length() > 8) {
            return "Nhập tối đa 8 ký tự thôi!";
        }
        for (int i = 0; i < top.length(); i++) {
            int a = (int) top.charAt(i);
            if (top.length() == 1 && a == 45) {
                return "Hãy nhập chữ số! (Trên)";
            }
            if (i != 0 && a == 45) {
                return "Hãy nhập chữ số! (Trên)";
            }
            if ((a < 48 || a > 57) && a != 45) {
                return "Hãy nhập chữ số! (Trên)";
            }
        }
        if (Integer.parseInt(edtTop.getText().toString().trim()) > ToolJFrame.pointJFrame.y) {
            return "Quá kích thước màn hình! Hãy giảm Trên";
        }
        return "";
    }

    private String checkEdtBottom() {
        String top = edtBottom.getText().toString().trim();
        if (top.isEmpty()) {
            return "Không được bỏ trống trường ký tự!";
        }
        if (top.length() > 8) {
            return "Nhập tối đa 8 ký tự thôi!";
        }
        for (int i = 0; i < top.length(); i++) {
            int a = (int) top.charAt(i);
            if (top.length() == 1 && a == 45) {
                return "Hãy nhập chữ số! (Dưới)";
            }
            if (i != 0 && a == 45) {
                return "Hãy nhập chữ số! (Dưới)";
            }
            if ((a < 48 || a > 57) && a != 45) {
                return "Hãy nhập chữ số! (Dưới)";
            }
        }
        if (Integer.parseInt(edtBottom.getText().toString().trim()) > (Toolkit.getDefaultToolkit().getScreenSize().height - (ToolJFrame.pointJFrame.y + ToolJFrame.SCREEN_HEIGHT))) {
            return "Quá kích thước màn hình! Hãy giảm Xuống";
        }
        return "";
    }

    private String checkEdtLeft() {
        String top = edtLeft.getText().toString().trim();
        if (top.isEmpty()) {
            return "Không được bỏ trống trường ký tự!";
        }
        if (top.length() > 8) {
            return "Nhập tối đa 8 ký tự thôi!";
        }
        for (int i = 0; i < top.length(); i++) {
            int a = (int) top.charAt(i);
            if (top.length() == 1 && a == 45) {
                return "Hãy nhập chữ số! (Trái)";
            }
            if (i != 0 && a == 45) {
                return "Hãy nhập chữ số! (Trái)";
            }
            if ((a < 48 || a > 57) && a != 45) {
                return "Hãy nhập chữ số! (Trái)";
            }
        }
        if (Integer.parseInt(edtLeft.getText().toString().trim()) > ToolJFrame.pointJFrame.x) {
            return "Quá kích thước màn hình! Hãy giảm Trái";
        }
        return "";
    }

    private String checkEdtRight() {
        String top = edtRight.getText().toString().trim();
        if (top.isEmpty()) {
            return "Không được bỏ trống trường ký tự!";
        }
        if (top.length() > 8) {
            return "Nhập tối đa 8 ký tự thôi!";
        }
        for (int i = 0; i < top.length(); i++) {
            int a = (int) top.charAt(i);
            if (top.length() == 1 && a == 45) {
                return "Hãy nhập chữ số! (Phải)";
            }
            if (i != 0 && a == 45) {
                return "Hãy nhập chữ số! (Phải)";
            }
            if ((a < 48 || a > 57) && a != 45) {
                return "Hãy nhập chữ số! (Phải)";
            }
        }
        if (Integer.parseInt(edtRight.getText().toString().trim()) > (Toolkit.getDefaultToolkit().getScreenSize().width - (ToolJFrame.pointJFrame.x + ToolJFrame.SCREEN_WIDTH))) {
            return "Quá kích thước màn hình! Hãy giảm Phải";
        }
        return "";
    }

    private boolean checkResize() {
        int w = ToolJFrame.SCREEN_WIDTH + Integer.parseInt(edtLeft.getText().toString().trim()) + Integer.parseInt(edtRight.getText().toString().trim());
        int h = ToolJFrame.SCREEN_HEIGHT + Integer.parseInt(edtTop.getText().toString().trim()) + Integer.parseInt(edtBottom.getText().toString().trim());
        if (w < 200) {
            JOptionPane.showMessageDialog(this, "Nhỏ quá rồi! Chiều rộng tối thiểu 200 pixel.");
            return false;
        }
        if (h < 200) {
            JOptionPane.showMessageDialog(this, "Nhỏ quá rồi! Chiều cao tối thiểu 200 pixel.");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnResize": {
                if (checkEdtTop().isEmpty() == false) {
                    JOptionPane.showMessageDialog(this, checkEdtTop());
                    break;
                }
                if (checkEdtBottom().isEmpty() == false) {
                    JOptionPane.showMessageDialog(this, checkEdtBottom());
                    break;
                }
                if (checkEdtLeft().isEmpty() == false) {
                    JOptionPane.showMessageDialog(this, checkEdtLeft());
                    break;
                }
                if (checkEdtRight().isEmpty() == false) {
                    JOptionPane.showMessageDialog(this, checkEdtRight());
                    break;
                }
                if (checkResize() == false) {
                    break;
                }

                int top = Integer.parseInt(edtTop.getText().toString().trim());
                int bottom = Integer.parseInt(edtBottom.getText().toString().trim());
                int left = Integer.parseInt(edtLeft.getText().toString().trim());
                int right = Integer.parseInt(edtRight.getText().toString().trim());
                toolJFrame.setBoundsToolFrame(top, bottom, left, right);
                break;
            }
            case "btnOne": {
                clickBtnOne();
                break;
            }
            case "btnTwo": {
                clickBtnTwo();
                break;
            }
            case "btnThree": {
                clickBtnThree();
                break;
            }
            case "btnFour": {
                clickBtnFour();
                break;
            }
            case "btnFive": {
                clickBtnFive();
                break;
            }
            case "btnSix": {
                clickBtnSix();
                break;
            }
            case "btnAll": {
                clickBtnAll();
                break;
            }
            case "btnOption": {
                jFSetting.update();
                toolJFrame.setAlwaysOnTop(false);
                if(Properties.isAlwaysFocus){
                    JPanelMenu.isFocus = true;
                }
                Properties.isAlwaysFocus = false;
                jFSetting.setVisible(true);
                break;
            }
            case "btnExit": {
                int input = JOptionPane.showConfirmDialog(null, "Bạn muốn đóng ứng dụng ?", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
                if (input == 0) {
                    Data.writeData();
                    System.exit(0);
                }
                break;
            }
        }
    }

    public JRadioButton getJrbOneLine() {
        return jrbOneLine;
    }

    public void setJrbOneLine(JRadioButton jrbOneLine) {
        this.jrbOneLine = jrbOneLine;
    }

    public JRadioButton getJrbAllLine() {
        return jrbAllLine;
    }

    public void setJrbAllLine(JRadioButton jrbAllLine) {
        this.jrbAllLine = jrbAllLine;
    }

    public JFrameSetting getjFSetting() {
        return jFSetting;
    }

    public void setjFSetting(JFrameSetting jFSetting) {
        this.jFSetting = jFSetting;
    }

}
