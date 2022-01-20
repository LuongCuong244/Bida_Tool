/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool8ball;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import static tool8ball.ToolJFrame.SCREEN_HEIGHT;
import static tool8ball.ToolJFrame.SCREEN_WIDTH;

public class JPanelTable extends JPanel {

    public static int hole = 1;
    public static Point pointTryRoad = null;
    public static Point pointSelected = null;
    public static Point pointTwoLineStart = null;
    public static Point pointTwoLineEnd = new Point(0, 0);
    private ToolJFrame toolJFrame;
    private BufferedImage imgTable;
    public Color colorMain = null;
    private JPanelImage jPanelImage;
    private boolean horizontal = true;

    private int cXTryRoad = 0;
    private int cYTryRoad = 0;

    private int cXTwoShootLine = 0;
    private int cYTwoShootLine = 0;
    private ArrayList<Point> listPointTwoShootLine = new ArrayList<>();

    public static Point pointAddY = new Point(0, 0);
    public static ArrayList<Point> listPoint = new ArrayList<>();

    public static int saiSoCanBang = 15;
    public static int customSaiSo = 7 * saiSoCanBang;

    public JPanelTable(ToolJFrame toolJFrame) {
        this.toolJFrame = toolJFrame;
        this.setBounds(0, 150 + Properties.space, ToolJFrame.SCREEN_WIDTH, ToolJFrame.SCREEN_HEIGHT - 150 - Properties.space);
        this.setBorder(new LineBorder(Properties.colorOuterBorder, Properties.LineSizeOuterBorder));
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.f));
        this.setFocusable(true);
        this.addKeyListener(toolJFrame);
        this.setLayout(new BorderLayout());
        jPanelImage = new JPanelImage(this);
        jPanelImage.setVisible(true);
        this.add(jPanelImage, BorderLayout.CENTER);
        saiSoCanBang = (int) (this.getHeight() / (2 * Properties.ratioBall));
        customSaiSo = 7 * saiSoCanBang;
    }

    public void takeAScreenShot() {
        BufferedImage screenShot = null;
        try {
            Robot robot = new Robot();
            screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException ex) {
            Logger.getLogger(JPanelTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage subImage = null;
        if (screenShot != null) {
            Point p = toolJFrame.getLocation();
            subImage = screenShot.getSubimage(p.x, p.y + toolJFrame.getjPanelMenu().getHeight() + Properties.space, this.getWidth(), this.getHeight());
        }
        imgTable = subImage;
    }

    private int getLimitTop(int[][] arrGradient, int i, int j, int minGradient) {
        // tìm giới hạn trên theo trục Oy.
        int upperLimit = 0;
        while (i >= 0) {
            if (arrGradient[i][j] > minGradient) {
                boolean check = true;
                for (int vt = 1; vt < 5; vt++) {
                    if ((i - vt) < 0) {
                        break;
                    }
                    if (arrGradient[i - vt][j] > minGradient) {
                        check = false;
                    }
                }
                if (check) {
                    upperLimit = pointSelected.y - 20 + i;
                }
            }
            i--;
        }
        return upperLimit;
    }

    private int getLimitBottom(int[][] arrGradient, int i, int j, int minGradient) {
        // tìm giới hạn dưới theo trục Oy.
        int lowerLimit = 0;
        i = 20;
        j = 20;
        while (i < arrGradient.length) {
            if (arrGradient[i][j] > minGradient) {
                boolean check = true;
                for (int vt = 1; vt < 5; vt++) {
                    if ((i + vt) >= arrGradient.length) {
                        break;
                    }
                    if (arrGradient[i + vt][j] > minGradient) {
                        check = false;
                    }
                }
                if (check) {
                    lowerLimit = pointSelected.y - 20 + i;
                }
            }
            i++;
        }
        return lowerLimit;
    }

    private int getLimitRight(int[][] arrGradient, int i, int j, int minGradient) {
        // tìm giới hạn phải theo trục Ox.
        int rightLimit = 0;
        i = 20;
        j = 20;
        while (j < arrGradient.length) {
            if (arrGradient[i][j] > minGradient) {
                boolean check = true;
                for (int vt = 1; vt < 5; vt++) {
                    if ((j + vt) >= arrGradient.length) {
                        break;
                    }
                    if (arrGradient[i][j + vt] > minGradient) {
                        check = false;
                    }
                }
                if (check) {
                    rightLimit = pointSelected.x - 20 + j;
                }
            }
            j++;
        }
        return rightLimit;
    }

    private int getLimitLeft(int[][] arrGradient, int i, int j, int minGradient) {
        // tìm giới hạn trái theo trục Ox.
        int leftLimit = 0;
        i = 20;
        j = 20;
        while (j >= 0) {
            if (arrGradient[i][j] > minGradient) {
                boolean check = true;
                for (int vt = 1; vt < 5; vt++) {
                    if ((j - vt) < 0) {
                        break;
                    }
                    if (arrGradient[i][j - vt] > minGradient) {
                        check = false;
                    }
                }
                if (check) {
                    leftLimit = pointSelected.x - 20 + j;
                }
            }
            j--;
        }
        return leftLimit;
    }

    public void locateBall(Graphics graphics) {
        if (pointSelected != null) {

            int width = pointSelected.x - 20;
            int height = pointSelected.y - 20;

            int[][] arrGradient = new int[40][40];

            for (int i = width; i < width + 40; i++) {
                for (int j = height; j < height + 40; j++) {

                    if (i < 1) {
                        continue;
                    };
                    if (j < 1) {
                        continue;
                    };
                    if (i >= (this.getWidth() - 1)) {
                        continue;
                    };
                    if (j >= (this.getHeight() - 1)) {
                        continue;
                    };

                    int val00 = getGrayScale(imgTable.getRGB(i - 1, j - 1));
                    int val01 = getGrayScale(imgTable.getRGB(i - 1, j));
                    int val02 = getGrayScale(imgTable.getRGB(i - 1, j + 1));

                    int val10 = getGrayScale(imgTable.getRGB(i, j - 1));
                    int val11 = getGrayScale(imgTable.getRGB(i, j));
                    int val12 = getGrayScale(imgTable.getRGB(i, j + 1));

                    int val20 = getGrayScale(imgTable.getRGB(i + 1, j - 1));
                    int val21 = getGrayScale(imgTable.getRGB(i + 1, j));
                    int val22 = getGrayScale(imgTable.getRGB(i + 1, j + 1));

                    int gx = ((-1 * val00) + (0 * val01) + (1 * val02))
                            + ((-2 * val10) + (0 * val11) + (2 * val12))
                            + ((-1 * val20) + (0 * val21) + (1 * val22));

                    int gy = ((-1 * val00) + (-2 * val01) + (-1 * val02))
                            + ((0 * val10) + (0 * val11) + (0 * val12))
                            + ((1 * val20) + (2 * val21) + (1 * val22));

                    double gval = Math.sqrt((gx * gx) + (gy * gy));
                    int g = (int) gval;
                    arrGradient[i - width][j - height] = g;
                }
            }
            int minGradient = 110;

            int upperLimit = getLimitTop(arrGradient, 20, 20, minGradient);
            int leftLimit = getLimitLeft(arrGradient, 20, 20, minGradient);
            int rightLimit = getLimitRight(arrGradient, 20, 20, minGradient);
            int lowerLimit = getLimitBottom(arrGradient, 20, 20, minGradient);

            int radius = (int) ((int) this.getHeight() / (2 * Properties.ratioBall));

            Graphics2D g2 = (Graphics2D) graphics;
            g2.setStroke(new BasicStroke(Properties.LineSize));
            if (KeyBoard.tryLine) {
                if (leftLimit > 0 && upperLimit > 0 && Properties.isAutoBall) {
                    g2.setColor(Properties.colorLine);
                    g2.drawOval(cXTryRoad - radius, cYTryRoad - radius, 2 * radius, 2 * radius);
                    pointSelected.setLocation(cXTryRoad, cYTryRoad);
                }
            } else if (KeyBoard.twoShootLine) {
                if (leftLimit > 0 && upperLimit > 0 && Properties.isAutoBall) {
                    g2.setColor(Properties.colorLine);
                    g2.drawOval(cXTwoShootLine - radius, cYTwoShootLine - radius, 2 * radius, 2 * radius);
                    g2.drawOval(pointTwoLineStart.x - radius, pointTwoLineStart.y - radius, 2 * radius, 2 * radius);
                    pointTwoLineEnd.setLocation(cXTwoShootLine, cYTwoShootLine);
                }
            } else {
                cXTryRoad = leftLimit + radius;
                cYTryRoad = upperLimit + radius;
                if (JPanelMenu.isOneLine) {
                    findOneLine(g2, JPanelTable.hole, radius, leftLimit, upperLimit);
                } else {
                    findAllLine(g2, JPanelTable.hole, radius, leftLimit, upperLimit);
                }
            }
            plusBall(g2, radius, hole);
        }
    }

    public void findOneLine(Graphics2D g, int hole, int radius, int leftLimit, int upperLimit) {
        int x, y;
        if (leftLimit > 0 && upperLimit > 0 && Properties.isAutoBall) {
            g.setColor(Properties.colorLine);
            g.drawOval(leftLimit, upperLimit, 2 * radius, 2 * radius);
            x = leftLimit + radius;
            y = upperLimit + radius;
        } else {
            x = pointSelected.x;
            y = pointSelected.y;
        }
        pointAddY.setLocation(x, y);
        int spaceRadius = 2 * (radius + 1);
        switch (hole) {
            case 1: {
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(x * x + y * y));
                    int cX = ((x) * (length + spaceRadius)) / length;
                    int cY = ((y) * (length + spaceRadius)) / length;
                    g.drawOval(cX - radius, cY - radius, 2 * radius, 2 * radius);
                    cXTwoShootLine = cX;
                    cYTwoShootLine = cY;
                }
                g.drawOval(4, 4, 2 * radius, 2 * radius);
                g.drawLine(radius + 3, radius + 3, x, y);
                break;
            }
            case 2: {
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow(Math.abs(x - this.getWidth() / 2), 2) + y * y));
                    int cX = ((Math.abs(x - this.getWidth() / 2)) * (length + spaceRadius)) / length;
                    int cY = ((y) * (length + spaceRadius)) / length;
                    if (x < this.getWidth() / 2) {
                        cX = this.getWidth() / 2 - cX;
                    } else {
                        cX = this.getWidth() / 2 + cX;
                    }
                    g.drawOval(cX - radius, cY - radius, 2 * radius, 2 * radius);
                    cXTwoShootLine = cX;
                    cYTwoShootLine = cY;
                }
                g.drawOval(this.getWidth() / 2 - radius, -radius, 2 * radius, 2 * radius);
                g.drawLine(this.getWidth() / 2, radius / 2, x, y);
                break;
            }
            case 3: {
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow(this.getWidth() - x, 2) + y * y));
                    int cX = ((this.getWidth() - x) * (length + spaceRadius)) / length;
                    int cY = ((y) * (length + spaceRadius)) / length;
                    g.drawOval(this.getWidth() - cX - radius, cY - radius, 2 * radius, 2 * radius);
                    cXTwoShootLine = this.getWidth() - cX;
                    cYTwoShootLine = cY;
                }
                g.drawOval(this.getWidth() - 2 * radius - 6, 4, 2 * radius, 2 * radius);
                g.drawLine(this.getWidth() - radius - 5, radius + 4, x, y);
                break;
            }
            case 4: {
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(x * x + Math.pow(this.getHeight() - y, 2)));
                    int cX = ((x) * (length + spaceRadius)) / length;
                    int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    g.drawOval(cX - radius, this.getHeight() - cY - radius, 2 * radius, 2 * radius);
                    cXTwoShootLine = cX;
                    cYTwoShootLine = this.getHeight() - cY;
                }
                g.drawOval(4, this.getHeight() - 2 * radius - 6, 2 * radius, 2 * radius);
                g.drawLine(radius + 4, this.getHeight() - radius - 6, x, y);
                break;
            }
            case 5: {
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow((Math.abs(x - this.getWidth() / 2)), 2) + Math.pow(this.getHeight() - y, 2)));
                    int cX = ((Math.abs(x - this.getWidth() / 2)) * (length + spaceRadius)) / length;
                    int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    if (x < this.getWidth() / 2) {
                        cX = this.getWidth() / 2 - cX;
                    } else {
                        cX = this.getWidth() / 2 + cX;
                    }
                    g.drawOval(cX - radius, this.getHeight() - cY - radius, 2 * radius, 2 * radius);
                    cXTwoShootLine = cX;
                    cYTwoShootLine = this.getHeight() - cY;
                }
                g.drawOval(this.getWidth() / 2 - radius, this.getHeight() - radius, 2 * radius, 2 * radius);
                g.drawLine(this.getWidth() / 2, this.getHeight() - radius / 2, x, y);
                break;
            }
            case 6: {
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow(this.getWidth() - x, 2) + Math.pow(this.getHeight() - y, 2)));
                    int cX = ((this.getWidth() - x) * (length + spaceRadius)) / length;
                    int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    g.drawOval(this.getWidth() - cX - radius, this.getHeight() - cY - radius, 2 * radius, 2 * radius);
                    cXTwoShootLine = this.getWidth() - cX;
                    cYTwoShootLine = this.getHeight() - cY;
                }
                g.drawOval(this.getWidth() - 2 * radius - 6, this.getHeight() - 2 * radius - 6, 2 * radius, 2 * radius);
                g.drawLine(this.getWidth() - radius - 6, this.getHeight() - radius - 5, x, y);
                break;
            }
            case 7: { // vẽ tất cả 6 lỗ
                // lỗ 1
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(x * x + y * y));
                    int cX = ((x) * (length + spaceRadius)) / length;
                    int cY = ((y) * (length + spaceRadius)) / length;
                    cXTwoShootLine = cX;
                    cYTwoShootLine = cY;
                }
                g.drawOval(4, 4, 2 * radius, 2 * radius);
                g.drawLine(radius + 3, radius + 3, x, y);
                // lỗ 2
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow(Math.abs(x - this.getWidth() / 2), 2) + y * y));
                    int cX = ((Math.abs(x - this.getWidth() / 2)) * (length + spaceRadius)) / length;
                    int cY = ((y) * (length + spaceRadius)) / length;
                    if (x < this.getWidth() / 2) {
                        cX = this.getWidth() / 2 - cX;
                    } else {
                        cX = this.getWidth() / 2 + cX;
                    }
                    cXTwoShootLine = cX;
                    cYTwoShootLine = cY;
                }
                g.drawOval(this.getWidth() / 2 - radius, -radius, 2 * radius, 2 * radius);
                g.drawLine(this.getWidth() / 2, radius / 2, x, y);
                // lỗ 3
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow(this.getWidth() - x, 2) + y * y));
                    int cX = ((this.getWidth() - x) * (length + spaceRadius)) / length;
                    int cY = ((y) * (length + spaceRadius)) / length;
                    cXTwoShootLine = this.getWidth() - cX;
                    cYTwoShootLine = cY;
                }
                g.drawOval(this.getWidth() - 2 * radius - 6, 4, 2 * radius, 2 * radius);
                g.drawLine(this.getWidth() - radius - 5, radius + 4, x, y);
                // lỗ 4
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(x * x + Math.pow(this.getHeight() - y, 2)));
                    int cX = ((x) * (length + spaceRadius)) / length;
                    int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    cXTwoShootLine = cX;
                    cYTwoShootLine = this.getHeight() - cY;
                }
                g.drawOval(4, this.getHeight() - 2 * radius - 6, 2 * radius, 2 * radius);
                g.drawLine(radius + 4, this.getHeight() - radius - 6, x, y);
                // lỗ 5
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow((Math.abs(x - this.getWidth() / 2)), 2) + Math.pow(this.getHeight() - y, 2)));
                    int cX = ((Math.abs(x - this.getWidth() / 2)) * (length + spaceRadius)) / length;
                    int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    if (x < this.getWidth() / 2) {
                        cX = this.getWidth() / 2 - cX;
                    } else {
                        cX = this.getWidth() / 2 + cX;
                    }
                    cXTwoShootLine = cX;
                    cYTwoShootLine = this.getHeight() - cY;
                }
                g.drawOval(this.getWidth() / 2 - radius, this.getHeight() - radius, 2 * radius, 2 * radius);
                g.drawLine(this.getWidth() / 2, this.getHeight() - radius / 2, x, y);
                // lỗ 6
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow(this.getWidth() - x, 2) + Math.pow(this.getHeight() - y, 2)));
                    int cX = ((this.getWidth() - x) * (length + spaceRadius)) / length;
                    int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    cXTwoShootLine = this.getWidth() - cX;
                    cYTwoShootLine = this.getHeight() - cY;
                }
                g.drawOval(this.getWidth() - 2 * radius - 6, this.getHeight() - 2 * radius - 6, 2 * radius, 2 * radius);
                g.drawLine(this.getWidth() - radius - 6, this.getHeight() - radius - 5, x, y);
            }
        }
    }

    public void findAllLine(Graphics2D g, int hole, int radius, int leftLimit, int upperLimit) {
        int x, y;
        if (leftLimit > 0 && upperLimit > 0 && Properties.isAutoBall) {
            g.setColor(Properties.colorLine);
            g.drawOval(leftLimit, upperLimit, 2 * radius, 2 * radius);
            x = leftLimit + radius;
            y = upperLimit + radius;
        } else {
            x = pointSelected.x;
            y = pointSelected.y;
        }
        pointAddY.setLocation(x, y);
        int spaceRadius = 2 * (radius + 2);
        switch (hole) {
            case 1: {
                horizontal = !horizontal;
                if (horizontal) {
                    int b = (this.getHeight() * x) / (this.getHeight() + this.getHeight() - y);
                    int pX = b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((x - pX) * (x - pX) + Math.pow(this.getHeight() - y, 2)));
                        int cX = ((x - pX) * (length + spaceRadius)) / length;
                        int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                        g.drawOval(cX + pX - radius, this.getHeight() - cY - radius, 2 * radius, 2 * radius);
                        pointAddY.setLocation(cX + pX, this.getHeight() - cY);
                    }
                    if (Math.abs(this.getHeight() - y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(pX, this.getHeight() - saiSoCanBang, x, y);
                        g.drawLine(pX, this.getHeight() - saiSoCanBang, radius + 3, radius + 3);
                        g.drawOval(pX - radius, this.getHeight() - saiSoCanBang - radius, 2 * radius, 2 * radius); // điểm đập
                    } else {
                        g.drawLine(pX, this.getHeight(), x, y);
                        g.drawLine(pX, this.getHeight(), radius + 3, radius + 3);
                    }

                } else {
                    int b = (this.getWidth() * y) / (this.getWidth() + this.getWidth() - x);
                    int pY = b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((y - pY) * (y - pY) + Math.pow(this.getWidth() - x, 2)));
                        int cX = ((this.getWidth() - x) * (length + spaceRadius)) / length;
                        int cY = ((y - pY) * (length + spaceRadius)) / length;
                        g.drawOval(this.getWidth() - cX - radius, pY + cY - radius, 2 * radius, 2 * radius);
                        pointAddY.setLocation(this.getWidth() - cX, pY + cY);
                    }
                    if (Math.abs(this.getWidth() - x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(this.getWidth() - saiSoCanBang, pY, x, y);
                        g.drawLine(this.getWidth() - saiSoCanBang, pY, radius + 4, radius + 4);

                        g.drawOval(this.getWidth() - saiSoCanBang - radius, pY - radius, 2 * radius, 2 * radius); // điểm đập
                    } else {
                        g.drawLine(this.getWidth(), pY, x, y);
                        g.drawLine(this.getWidth(), pY, radius + 4, radius + 4);
                    }
                }
                g.drawOval(4, 4, 2 * radius, 2 * radius);
                break;
            }
            case 2: {
                int c = Math.abs(this.getWidth() / 2 - x); // ta có a + b = c
                int b = (this.getHeight() * c) / (this.getHeight() + this.getHeight() - y);
                int pX = 0;
                if (x > this.getWidth() / 2) {
                    pX = this.getWidth() / 2 + b;
                } else {
                    pX = this.getWidth() / 2 - b;
                }
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow(Math.abs(x - pX), 2) + Math.pow(this.getHeight() - y, 2)));
                    int cX = ((Math.abs(x - pX)) * (length + spaceRadius)) / length;
                    int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    if (x < pX) {
                        cX = pX - cX;
                    } else {
                        cX = pX + cX;
                    }
                    g.drawOval(cX - radius, this.getHeight() - cY - radius, 2 * radius, 2 * radius);
                    pointAddY.setLocation(cX, this.getHeight() - cY);
                }
                g.drawOval(this.getWidth() / 2 - radius, -radius, 2 * radius, 2 * radius);
                if (Math.abs(this.getHeight() - y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    g.drawLine(pX, this.getHeight() - saiSoCanBang, x, y);
                    g.drawLine(pX, this.getHeight() - saiSoCanBang, this.getWidth() / 2, 0);

                    g.drawOval(pX - radius, this.getHeight() - saiSoCanBang - radius, 2 * radius, 2 * radius);
                } else {
                    g.drawLine(pX, this.getHeight(), x, y);
                    g.drawLine(pX, this.getHeight(), this.getWidth() / 2, 0);
                }

                break;
            }
            case 3: {
                horizontal = !horizontal;
                if (horizontal) {
                    int b = (this.getHeight() * (this.getWidth() - x)) / (this.getHeight() + this.getHeight() - y);
                    int pX = this.getWidth() - b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((pX - x) * (pX - x) + Math.pow(this.getHeight() - y, 2)));
                        int cX = ((pX - x) * (length + spaceRadius)) / length;
                        int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                        g.drawOval(pX - cX - radius, this.getHeight() - cY - radius, 2 * radius, 2 * radius);
                        pointAddY.setLocation(pX - cX, this.getHeight() - cY);
                    }

                    if (Math.abs(this.getHeight() - y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(pX, this.getHeight() - saiSoCanBang, x, y);
                        g.drawLine(pX, this.getHeight() - saiSoCanBang, this.getWidth() - radius - 6, radius + 3);

                        g.drawOval(pX - radius, this.getHeight() - saiSoCanBang - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(pX, this.getHeight(), x, y);
                        g.drawLine(pX, this.getHeight(), this.getWidth() - radius - 6, radius + 3);
                    }
                } else {
                    int b = (this.getWidth() * y) / (this.getWidth() + x);
                    int pY = b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((y - pY) * (y - pY) + Math.pow(x, 2)));
                        int cX = ((x) * (length + spaceRadius)) / length;
                        int cY = ((y - pY) * (length + spaceRadius)) / length;
                        g.drawOval(cX - radius, pY + cY - radius, 2 * radius, 2 * radius);
                        pointAddY.setLocation(cX, pY + cY);
                    }

                    if (Math.abs(x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(0 + saiSoCanBang, pY, x, y);
                        g.drawLine(0 + saiSoCanBang, pY, this.getWidth() - radius - 6, 4 + radius);

                        g.drawOval(0 + saiSoCanBang - radius, pY - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(0, pY, x, y);
                        g.drawLine(0, pY, this.getWidth() - radius - 6, 4 + radius);
                    }
                }
                g.drawOval(this.getWidth() - 2 * radius - 6, 4, 2 * radius, 2 * radius);
                break;
            }
            case 4: {
                horizontal = !horizontal;
                if (horizontal) {
                    int b = (this.getHeight() * x) / (this.getHeight() + y);
                    int pX = b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((x - pX) * (x - pX) + Math.pow(y, 2)));
                        int cX = ((x - pX) * (length + spaceRadius)) / length;
                        int cY = ((y) * (length + spaceRadius)) / length;
                        g.drawOval(cX + pX - radius, cY - radius, 2 * radius, 2 * radius);
                        pointAddY.setLocation(cX + pX, cY);
                    }

                    if (Math.abs(y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(pX, 0 + saiSoCanBang, x, y);
                        g.drawLine(pX, 0 + saiSoCanBang, radius + 4, this.getHeight() - radius - 6);

                        g.drawOval(pX - radius, 0 + saiSoCanBang - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(pX, 0, x, y);
                        g.drawLine(pX, 0, radius + 4, this.getHeight() - radius - 6);
                    }
                } else {
                    int b = (this.getWidth() * (this.getHeight() - y)) / (this.getWidth() + this.getWidth() - x);
                    int pY = this.getHeight() - b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((pY - y) * (pY - y) + Math.pow(this.getWidth() - x, 2)));
                        int cX = ((this.getWidth() - x) * (length + spaceRadius)) / length;
                        int cY = ((pY - y) * (length + spaceRadius)) / length;
                        g.drawOval(this.getWidth() - cX - radius, pY - cY - radius, 2 * radius, 2 * radius);
                        pointAddY.setLocation(this.getWidth() - cX, pY - cY);
                    }

                    if (Math.abs(this.getWidth() - x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(this.getWidth() - saiSoCanBang, pY, x, y);
                        g.drawLine(this.getWidth() - saiSoCanBang, pY, radius + 6, this.getHeight() - radius - 4);

                        g.drawOval(this.getWidth() - saiSoCanBang - radius, pY - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(this.getWidth(), pY, x, y);
                        g.drawLine(this.getWidth(), pY, radius + 6, this.getHeight() - radius - 4);
                    }
                }
                g.drawOval(4, this.getHeight() - 2 * radius - 6, 2 * radius, 2 * radius);
                break;
            }
            case 5: {
                int c = Math.abs(this.getWidth() / 2 - x); // ta có a + b = c
                int b = (this.getHeight() * c) / (this.getHeight() + y);
                int pX = 0;
                if (x > this.getWidth() / 2) {
                    pX = this.getWidth() / 2 + b;
                } else {
                    pX = this.getWidth() / 2 - b;
                }
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow((Math.abs(x - pX)), 2) + Math.pow(y, 2)));
                    int cX = ((Math.abs(x - pX)) * (length + spaceRadius)) / length;
                    int cY = ((y) * (length + spaceRadius)) / length;
                    if (x < pX) {
                        cX = pX - cX;
                    } else {
                        cX = pX + cX;
                    }
                    g.drawOval(cX - radius, cY - radius, 2 * radius, 2 * radius);
                    pointAddY.setLocation(cX, cY);
                }
                g.drawOval(this.getWidth() / 2 - radius, this.getHeight() - radius, 2 * radius, 2 * radius);
                if (Math.abs(y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    g.drawLine(pX, 0 + saiSoCanBang, x, y);
                    g.drawLine(pX, 0 + saiSoCanBang, this.getWidth() / 2, this.getHeight() - radius / 2);

                    g.drawOval(pX - radius, 0 + saiSoCanBang - radius, 2 * radius, 2 * radius);
                } else {
                    g.drawLine(pX, 0, x, y);
                    g.drawLine(pX, 0, this.getWidth() / 2, this.getHeight() - radius / 2);
                }

                break;
            }
            case 6: {
                horizontal = !horizontal;
                if (horizontal) {
                    int b = (this.getHeight() * (this.getWidth() - x)) / (this.getHeight() + y);
                    int pX = this.getWidth() - b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((pX - x) * (pX - x) + Math.pow(y, 2)));
                        int cX = ((pX - x) * (length + spaceRadius)) / length;
                        int cY = ((y) * (length + spaceRadius)) / length;
                        g.drawOval(pX - cX - radius, cY - radius, 2 * radius, 2 * radius);
                        pointAddY.setLocation(pX - cX, cY);
                    }

                    if (Math.abs(y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(pX, 0 + saiSoCanBang, x, y);
                        g.drawLine(pX, 0 + saiSoCanBang, this.getWidth() - radius - 6, this.getHeight() - radius - 6);

                        g.drawOval(pX - radius, 0 + saiSoCanBang - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(pX, 0, x, y);
                        g.drawLine(pX, 0, this.getWidth() - radius - 6, this.getHeight() - radius - 6);
                    }
                } else {
                    int b = (this.getWidth() * (this.getHeight() - y)) / (this.getWidth() + x);
                    int pY = this.getHeight() - b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((pY - y) * (pY - y) + Math.pow(x, 2)));
                        int cX = ((x) * (length + spaceRadius)) / length;
                        int cY = ((pY - y) * (length + spaceRadius)) / length;
                        g.drawOval(cX - radius, pY - cY - radius, 2 * radius, 2 * radius);
                        pointAddY.setLocation(cX, pY - cY);
                    }

                    if (Math.abs(x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(0 + saiSoCanBang, pY, x, y);
                        g.drawLine(0 + saiSoCanBang, pY, this.getWidth() - radius - 4, this.getHeight() - radius - 4);

                        g.drawOval(0 + saiSoCanBang - radius, pY - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(0, pY, x, y);
                        g.drawLine(0, pY, this.getWidth() - radius - 4, this.getHeight() - radius - 4);
                    }
                }
                g.drawOval(this.getWidth() - 2 * radius - 6, this.getHeight() - 2 * radius - 6, 2 * radius, 2 * radius);
                break;
            }
            case 7: {
                horizontal = !horizontal;
                // lỗ 1

                if (horizontal) {
                    int b = (this.getHeight() * x) / (this.getHeight() + this.getHeight() - y);
                    int pX = b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((x - pX) * (x - pX) + Math.pow(this.getHeight() - y, 2)));
                        int cX = ((x - pX) * (length + spaceRadius)) / length;
                        int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    }
                    if (Math.abs(this.getHeight() - y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(pX, this.getHeight() - saiSoCanBang, x, y);
                        g.drawLine(pX, this.getHeight() - saiSoCanBang, radius + 3, radius + 3);
                        g.drawOval(pX - radius, this.getHeight() - saiSoCanBang - radius, 2 * radius, 2 * radius); // điểm đập
                    } else {
                        g.drawLine(pX, this.getHeight(), x, y);
                        g.drawLine(pX, this.getHeight(), radius + 3, radius + 3);
                    }

                } else {
                    int b = (this.getWidth() * y) / (this.getWidth() + this.getWidth() - x);
                    int pY = b;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((y - pY) * (y - pY) + Math.pow(this.getWidth() - x, 2)));
                        int cX = ((this.getWidth() - x) * (length + spaceRadius)) / length;
                        int cY = ((y - pY) * (length + spaceRadius)) / length;
                    }
                    if (Math.abs(this.getWidth() - x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(this.getWidth() - saiSoCanBang, pY, x, y);
                        g.drawLine(this.getWidth() - saiSoCanBang, pY, radius + 4, radius + 4);

                        g.drawOval(this.getWidth() - saiSoCanBang - radius, pY - radius, 2 * radius, 2 * radius); // điểm đập
                    } else {
                        g.drawLine(this.getWidth(), pY, x, y);
                        g.drawLine(this.getWidth(), pY, radius + 4, radius + 4);
                    }
                }
                g.drawOval(4, 4, 2 * radius, 2 * radius);

                // lỗ 2
                int c = Math.abs(this.getWidth() / 2 - x); // ta có a + b = c
                int b = (this.getHeight() * c) / (this.getHeight() + this.getHeight() - y);
                int pX = 0;
                if (x > this.getWidth() / 2) {
                    pX = this.getWidth() / 2 + b;
                } else {
                    pX = this.getWidth() / 2 - b;
                }
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow(Math.abs(x - pX), 2) + Math.pow(this.getHeight() - y, 2)));
                    int cX = ((Math.abs(x - pX)) * (length + spaceRadius)) / length;
                    int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    if (x < pX) {
                        cX = pX - cX;
                    } else {
                        cX = pX + cX;
                    }
                }
                g.drawOval(this.getWidth() / 2 - radius, -radius, 2 * radius, 2 * radius);
                if (Math.abs(this.getHeight() - y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    g.drawLine(pX, this.getHeight() - saiSoCanBang, x, y);
                    g.drawLine(pX, this.getHeight() - saiSoCanBang, this.getWidth() / 2, 0);

                    g.drawOval(pX - radius, this.getHeight() - saiSoCanBang - radius, 2 * radius, 2 * radius);
                } else {
                    g.drawLine(pX, this.getHeight(), x, y);
                    g.drawLine(pX, this.getHeight(), this.getWidth() / 2, 0);
                }

                // lỗ 3
                if (horizontal) {
                    int b3 = (this.getHeight() * (this.getWidth() - x)) / (this.getHeight() + this.getHeight() - y);
                    int pX3 = this.getWidth() - b3;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((pX3 - x) * (pX3 - x) + Math.pow(this.getHeight() - y, 2)));
                        int cX = ((pX3 - x) * (length + spaceRadius)) / length;
                        int cY = ((this.getHeight() - y) * (length + spaceRadius)) / length;
                    }

                    if (Math.abs(this.getHeight() - y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(pX3, this.getHeight() - saiSoCanBang, x, y);
                        g.drawLine(pX3, this.getHeight() - saiSoCanBang, this.getWidth() - radius - 6, radius + 3);

                        g.drawOval(pX3 - radius, this.getHeight() - saiSoCanBang - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(pX3, this.getHeight(), x, y);
                        g.drawLine(pX3, this.getHeight(), this.getWidth() - radius - 6, radius + 3);
                    }
                } else {
                    int b3 = (this.getWidth() * y) / (this.getWidth() + x);
                    int pY = b3;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((y - pY) * (y - pY) + Math.pow(x, 2)));
                        int cX = ((x) * (length + spaceRadius)) / length;
                        int cY = ((y - pY) * (length + spaceRadius)) / length;
                    }

                    if (Math.abs(x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(0 + saiSoCanBang, pY, x, y);
                        g.drawLine(0 + saiSoCanBang, pY, this.getWidth() - radius - 6, 4 + radius);

                        g.drawOval(0 + saiSoCanBang - radius, pY - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(0, pY, x, y);
                        g.drawLine(0, pY, this.getWidth() - radius - 6, 4 + radius);
                    }
                }
                g.drawOval(this.getWidth() - 2 * radius - 6, 4, 2 * radius, 2 * radius);

                // lỗ 4
                if (horizontal) {
                    int b4 = (this.getHeight() * x) / (this.getHeight() + y);
                    int pX4 = b4;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((x - pX4) * (x - pX4) + Math.pow(y, 2)));
                        int cX = ((x - pX4) * (length + spaceRadius)) / length;
                        int cY = ((y) * (length + spaceRadius)) / length;
                    }

                    if (Math.abs(y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(pX4, 0 + saiSoCanBang, x, y);
                        g.drawLine(pX4, 0 + saiSoCanBang, radius + 4, this.getHeight() - radius - 6);

                        g.drawOval(pX4 - radius, 0 + saiSoCanBang - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(pX4, 0, x, y);
                        g.drawLine(pX4, 0, radius + 4, this.getHeight() - radius - 6);
                    }
                } else {
                    int b4 = (this.getWidth() * (this.getHeight() - y)) / (this.getWidth() + this.getWidth() - x);
                    int pY = this.getHeight() - b4;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((pY - y) * (pY - y) + Math.pow(this.getWidth() - x, 2)));
                        int cX = ((this.getWidth() - x) * (length + spaceRadius)) / length;
                        int cY = ((pY - y) * (length + spaceRadius)) / length;
                    }

                    if (Math.abs(this.getWidth() - x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(this.getWidth() - saiSoCanBang, pY, x, y);
                        g.drawLine(this.getWidth() - saiSoCanBang, pY, radius + 6, this.getHeight() - radius - 4);

                        g.drawOval(this.getWidth() - saiSoCanBang - radius, pY - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(this.getWidth(), pY, x, y);
                        g.drawLine(this.getWidth(), pY, radius + 6, this.getHeight() - radius - 4);
                    }
                }
                g.drawOval(4, this.getHeight() - 2 * radius - 6, 2 * radius, 2 * radius);

                // lỗ 5
                int c5 = Math.abs(this.getWidth() / 2 - x); // ta có a + b = c
                int b5 = (this.getHeight() * c5) / (this.getHeight() + y);
                int pX5 = 0;
                if (x > this.getWidth() / 2) {
                    pX5 = this.getWidth() / 2 + b5;
                } else {
                    pX5 = this.getWidth() / 2 - b5;
                }
                if (leftLimit > 0 && upperLimit > 0) {
                    int length = (int) (Math.sqrt(Math.pow((Math.abs(x - pX5)), 2) + Math.pow(y, 2)));
                    int cX = ((Math.abs(x - pX5)) * (length + spaceRadius)) / length;
                    int cY = ((y) * (length + spaceRadius)) / length;
                    if (x < pX5) {
                        cX = pX5 - cX;
                    } else {
                        cX = pX5 + cX;
                    }
                }
                g.drawOval(this.getWidth() / 2 - radius, this.getHeight() - radius, 2 * radius, 2 * radius);
                if (Math.abs(y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    g.drawLine(pX5, 0 + saiSoCanBang, x, y);
                    g.drawLine(pX5, 0 + saiSoCanBang, this.getWidth() / 2, this.getHeight() - radius / 2);

                    g.drawOval(pX5 - radius, 0 + saiSoCanBang - radius, 2 * radius, 2 * radius);
                } else {
                    g.drawLine(pX5, 0, x, y);
                    g.drawLine(pX5, 0, this.getWidth() / 2, this.getHeight() - radius / 2);
                }

                // lỗ 6
                if (horizontal) {
                    int b6 = (this.getHeight() * (this.getWidth() - x)) / (this.getHeight() + y);
                    int pX6 = this.getWidth() - b6;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((pX - x) * (pX6 - x) + Math.pow(y, 2)));
                        int cX = ((pX6 - x) * (length + spaceRadius)) / length;
                        int cY = ((y) * (length + spaceRadius)) / length;
                    }

                    if (Math.abs(y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(pX6, 0 + saiSoCanBang, x, y);
                        g.drawLine(pX6, 0 + saiSoCanBang, this.getWidth() - radius - 6, this.getHeight() - radius - 6);

                        g.drawOval(pX6 - radius, 0 + saiSoCanBang - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(pX6, 0, x, y);
                        g.drawLine(pX6, 0, this.getWidth() - radius - 6, this.getHeight() - radius - 6);
                    }
                } else {
                    int b6 = (this.getWidth() * (this.getHeight() - y)) / (this.getWidth() + x);
                    int pY = this.getHeight() - b6;
                    if (leftLimit > 0 && upperLimit > 0) {
                        int length = (int) (Math.sqrt((pY - y) * (pY - y) + Math.pow(x, 2)));
                        int cX = ((x) * (length + spaceRadius)) / length;
                        int cY = ((pY - y) * (length + spaceRadius)) / length;
                    }

                    if (Math.abs(x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                        g.drawLine(0 + saiSoCanBang, pY, x, y);
                        g.drawLine(0 + saiSoCanBang, pY, this.getWidth() - radius - 4, this.getHeight() - radius - 4);

                        g.drawOval(0 + saiSoCanBang - radius, pY - radius, 2 * radius, 2 * radius);
                    } else {
                        g.drawLine(0, pY, x, y);
                        g.drawLine(0, pY, this.getWidth() - radius - 4, this.getHeight() - radius - 4);
                    }
                }
                g.drawOval(this.getWidth() - 2 * radius - 6, this.getHeight() - 2 * radius - 6, 2 * radius, 2 * radius);
            }
        }
    }

    public void tryRoad(Graphics2D g, int n) {
        // Xong 1 đường
        Point pBorderOne = new Point(pointSelected.x, pointSelected.y);
        // tìm phương trình đường thẳng y = ax + b;
        double a = (-pointSelected.getY() + pointTryRoad.getY()) / (pointSelected.getX() - pointTryRoad.getX());
        double b = -pointSelected.getY() - a * pointSelected.getX();
        if (pointSelected.getX() > pointTryRoad.getX()) {
            if (pointSelected.getY() > pointTryRoad.getY()) { // góc phần tư thứ 2
                double x = -b / a;
                if (x >= 0) { // đường thẳng cắt trục Ox
                    pBorderOne.setLocation(x, 0 - saiSoCanBang);
                } else { // đường thẳng cắt trục Oy
                    pBorderOne.setLocation(0 + saiSoCanBang, b);
                }
            } else { // góc phần tư thứ 3
                double x = (-this.getHeight() - b) / a;
                if (x >= 0) {
                    pBorderOne.setLocation(x, -this.getHeight() + saiSoCanBang); // cắt y = this.getHeight()
                } else {
                    pBorderOne.setLocation(0 + saiSoCanBang, b);
                }
            }
        } else {
            if (pointSelected.getY() > pointTryRoad.getY()) { // góc phần tư thứ 1
                double x = -b / a;
                if (x >= this.getWidth()) { // đường thẳng cắt x = getWidth()
                    pBorderOne.setLocation(this.getWidth() - saiSoCanBang, b + this.getWidth() * a);
                } else { // đường thẳng cắt trục Ox
                    pBorderOne.setLocation(x, 0 - saiSoCanBang);
                }
            } else { // góc phần tư thứ 4
                double x = (-this.getHeight() - b) / a;
                if (x >= this.getWidth()) {
                    pBorderOne.setLocation(this.getWidth() - saiSoCanBang, b + this.getWidth() * a);
                } else {
                    pBorderOne.setLocation(x, -this.getHeight() + saiSoCanBang);
                }
            }
        }
        g.drawLine(pointSelected.x, pointSelected.y, pBorderOne.x, -pBorderOne.y);

        // Vẽ các đường còn lại.
        double aOld = a; // lưu hệ số góc của đường thẳng trước đó.
        double bOld = b; // lưu .....
        Point pBorderOld = pBorderOne; // lưu điểm cũ để dùng tính phương trình đường thẳng mới
        Point pBorder = new Point(pBorderOne); // dùng lưu điểm mới
        for (int i = 0; i < n; i++) {
            double aNew = aOld;
            double bNew = bOld;
            if (pBorderOld.getY() == (-saiSoCanBang) || pBorderOld.getY() == 0) { // điểm này thuộc cạnh trên
                aNew = -aOld;
                bNew = -bOld;

                if (bNew <= 0 && bNew >= -this.getHeight()) { // xét thuộc cạnh trái
                    if (Math.sqrt(Math.pow(bNew, 2) + Math.pow(pBorderOld.x, 2)) > customSaiSo) {
                        pBorder.setLocation(0 + saiSoCanBang, bNew);
                    } else {
                        pBorder.setLocation(0, bNew);
                    }
                } else if ((this.getWidth() * aNew + bNew) <= 0 && (this.getWidth() * aNew + bNew) >= -this.getHeight()) { // xét thuộc cạnh phải
                    if (Math.sqrt(Math.pow(this.getHeight() - this.getWidth() * aNew + bNew, 2) + Math.pow(this.getWidth() - pBorderOld.x, 2)) > customSaiSo) {
                        pBorder.setLocation(this.getWidth() - saiSoCanBang, this.getWidth() * aNew + bNew);
                    } else {
                        pBorder.setLocation(this.getWidth(), this.getWidth() * aNew + bNew);
                    }
                } else { // xét thuộc cạnh dưới
                    pBorder.setLocation((-this.getHeight() - bNew) / aNew, -this.getHeight() + saiSoCanBang);
                }
            }
            if (pBorderOld.getY() == (-this.getHeight() + saiSoCanBang) || pBorderOld.getY() == -this.getHeight()) {  // điểm này thuộc cạnh dưới
                aNew = -aOld;
                bNew = (-2 * this.getHeight()) - bOld;

                if (bNew <= 0 && bNew >= -this.getHeight()) { // xét thuộc cạnh trái
                    if (Math.sqrt(Math.pow(bNew, 2) + Math.pow(pBorderOld.x, 2)) > customSaiSo) {
                        pBorder.setLocation(0 + saiSoCanBang, bNew);
                    } else {
                        pBorder.setLocation(0, bNew);
                    }
                } else if ((this.getWidth() * aNew + bNew) <= 0 && (this.getWidth() * aNew + bNew) >= -this.getHeight()) { // xét thuộc cạnh phải
                    if (Math.sqrt(Math.pow(this.getHeight() - this.getWidth() * aNew + bNew, 2) + Math.pow(this.getWidth() - pBorderOld.x, 2)) > customSaiSo) {
                        pBorder.setLocation(this.getWidth() - saiSoCanBang, this.getWidth() * aNew + bNew);
                    } else {
                        pBorder.setLocation(this.getWidth(), this.getWidth() * aNew + bNew);
                    }
                } else { // xét thuộc cạnh trên
                    pBorder.setLocation(-bNew / aNew, 0 - saiSoCanBang);
                }
            }
            if (pBorderOld.getX() == saiSoCanBang || pBorderOld.getX() == 0) { // điểm này thuộc cạnh trái
                aNew = -aOld;
                bNew = bOld;

                if ((-bNew / aNew) >= 0 && (-bNew / aNew) <= this.getWidth()) { // xét thuộc cạnh trên
                    if (Math.sqrt(Math.pow(-bNew / aNew, 2) + Math.pow(pBorderOld.y, 2)) > customSaiSo) {
                        pBorder.setLocation(-bNew / aNew, 0 - saiSoCanBang);
                    } else {
                        pBorder.setLocation(-bNew / aNew, 0);
                    }
                } else if (((-this.getHeight() - bNew) / aNew) >= 0 && ((-this.getHeight() - bNew) / aNew) <= this.getWidth()) { // xét thuộc cạnh dưới
                    if (Math.sqrt(Math.pow((-this.getHeight() - bNew) / aNew, 2) + Math.pow(this.getHeight() - pBorderOld.y, 2)) > customSaiSo) {
                        pBorder.setLocation((-this.getHeight() - bNew) / aNew, -this.getHeight() + saiSoCanBang);
                    } else {
                        pBorder.setLocation((-this.getHeight() - bNew) / aNew, -this.getHeight());
                    }
                } else { // xét thuộc cạnh phải
                    pBorder.setLocation(this.getWidth() - saiSoCanBang, this.getWidth() * aNew + bNew);
                }
            }
            if (pBorderOld.getX() == (this.getWidth() - saiSoCanBang) || pBorderOld.getX() == this.getWidth()) { // điểm này thuộc cạnh phải
                aNew = -aOld;
                bNew = ((-2 * this.getWidth()) - bOld / aOld) * aNew;

                if ((-bNew / aNew) >= 0 && (-bNew / aNew) <= this.getWidth()) { // xét thuộc cạnh trên
                    if (Math.sqrt(Math.pow(-bNew / aNew, 2) + Math.pow(pBorderOld.y, 2)) > customSaiSo) {
                        pBorder.setLocation(-bNew / aNew, 0 - saiSoCanBang);
                    } else {
                        pBorder.setLocation(-bNew / aNew, 0);
                    }
                } else if (((-this.getHeight() - bNew) / aNew) >= 0 && ((-this.getHeight() - bNew) / aNew) <= this.getWidth()) { // xét thuộc cạnh dưới
                    if (Math.sqrt(Math.pow((-this.getHeight() - bNew) / aNew, 2) + Math.pow(this.getHeight() - pBorderOld.y, 2)) > customSaiSo) {
                        pBorder.setLocation((-this.getHeight() - bNew) / aNew, -this.getHeight() + saiSoCanBang);
                    } else {
                        pBorder.setLocation((-this.getHeight() - bNew) / aNew, -this.getHeight());
                    }
                } else { // xét thuộc cạnh trái
                    pBorder.setLocation(0 + saiSoCanBang, bNew);
                }
            }
            g.drawLine(pBorderOld.x, -pBorderOld.y, pBorder.x - 2, -pBorder.y);
            pBorderOld.setLocation(pBorder);
            aOld = aNew;
            bOld = bNew;
        }
    }

    public void plusBall(Graphics2D g, int radius, int hole) {
        if (listPoint.size() > 1) {
            Point p2 = new Point(radius, -radius);
            switch (hole) {
                case 1: {
                    p2.setLocation(4 + radius, -4 - radius);
                    break;
                }
                case 2: {
                    p2.setLocation(this.getWidth() / 2, 0);
                    break;
                }
                case 3: {
                    p2.setLocation(this.getWidth() - radius - 6, -4 - radius);
                    break;
                }
                case 4: {
                    p2.setLocation(4 + radius, -this.getHeight() + radius + 6);
                    break;
                }
                case 5: {
                    p2.setLocation(this.getWidth() / 2, -this.getHeight());
                    break;
                }
                case 6: {
                    p2.setLocation(this.getWidth() - radius - 6, -this.getHeight() + radius + 6);
                    break;
                }
            }
            Point p1 = new Point(0, 0);
            Point p = new Point(0, 0);
            for (int i = listPoint.size() - 1; i >= 0; i--) {
                p1.setLocation(listPoint.get(i));
                double a = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());  // hệ số góc
                double b = p1.getY() - a * p1.getX(); // cơ số

                double a1 = 1 + a*a;
                double b1 = -2*p1.getX() + 2*a*b - 2*a*p1.getY();
                double c1 = Math.pow(p1.getX(), 2) + b*b - 2*b*p1.getY() + Math.pow(p1.getY(), 2) - 4*radius*radius;  // tính phương trình bậc 2
                
                double x = 0,y = 0;
                
                // tính delta
                double delta = b1 * b1 - 4 * a1 * c1;
                double x1;
                double x2;
                // tính nghiệm
                if (delta > 0) {
                    x1 = (double) ((-b1 + Math.sqrt(delta)) / (2 * a1));
                    x2 = (double) ((-b1 - Math.sqrt(delta)) / (2 * a1));
                    if(x1 >= 0  && (a*x1 + b) < 0 && (((x1 > p1.getX()) && (x1 > p2.getX()))||((x1 < p1.getX())&&(x1 < p2.getX())))){
                        x = x1;
                        y = a*x1 + b;
                    }else if(x2 >= 0 && (a*x2 + b) < 0 && (((x2 > p1.getX()) && (x2 > p2.getX()))||((x2 < p1.getX())&&(x2 < p2.getX()))) ){
                        x = x2;
                        y = a*x2 + b;
                    }else{
                        System.out.println("Đ xác định được");
                        return;
                    }
                } else if (delta == 0) {
                    x1 = (-b / (2 * a));
                    if(x1 >= 0){
                        x = x1;
                        y = a*x + b;
                        if(y >= 0){
                            System.out.println("Đ xác định được");
                            return;
                        }
                    }
                } else {
                    System.out.println("Đ xác định được");
                    return;
                }

                p.setLocation(x, y);
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.RED);
                g.drawOval(p.x - radius, -p.y - radius, radius * 2, radius * 2);
                g.setStroke(new BasicStroke(1));
                g.setColor(Properties.colorLine);
                g.drawLine(p.x, -p.y, p2.x, -p2.y);
                p2.setLocation(x, y);
            }
        }
    }

    public void twoShootLine(Graphics2D g, int radius, int hole, int direction) {
        // cạnh trái
        if (pointTwoLineStart.y > pointTwoLineEnd.y) {
            double y = (pointTwoLineEnd.getX() * Math.abs(pointTwoLineEnd.getY() - pointTwoLineStart.getY())) / (pointTwoLineEnd.getX() + pointTwoLineStart.getX()) + pointTwoLineEnd.getY();
            if(pointTwoLineEnd.x > customSaiSo){
                listPointTwoShootLine.add(new Point(0 + saiSoCanBang, (int) y));
            }else{
                listPointTwoShootLine.add(new Point(0, (int) y));
            }
        } else {
            double y = (pointTwoLineStart.getX() * Math.abs(pointTwoLineEnd.getY() - pointTwoLineStart.getY())) / (pointTwoLineStart.getX() + pointTwoLineEnd.getX()) + pointTwoLineStart.getY();
            if(pointTwoLineEnd.x > customSaiSo){
                listPointTwoShootLine.add(new Point(0 + saiSoCanBang, (int) y));
            }else{
                listPointTwoShootLine.add(new Point(0, (int) y));
            }
        }
        // cạnh phải
        if (pointTwoLineStart.y > pointTwoLineEnd.y) {
            double y = ((this.getWidth() - pointTwoLineEnd.getX()) * Math.abs(pointTwoLineEnd.getY() - pointTwoLineStart.getY())) / ((this.getWidth() - pointTwoLineEnd.getX()) + (this.getWidth() - pointTwoLineStart.getX())) + pointTwoLineEnd.getY();
            if((this.getWidth() - pointTwoLineEnd.x) > customSaiSo){
                listPointTwoShootLine.add(new Point(this.getWidth() - saiSoCanBang, (int) y));
            }else{
                listPointTwoShootLine.add(new Point(this.getWidth(), (int) y));
            }
        } else {
            double y = ((this.getWidth() - pointTwoLineStart.getX()) * Math.abs(pointTwoLineEnd.getY() - pointTwoLineStart.getY())) / (2 * this.getWidth() - pointTwoLineStart.getX() - pointTwoLineEnd.getX()) + pointTwoLineStart.getY();
            if((this.getWidth() - pointTwoLineEnd.x) > customSaiSo){
                listPointTwoShootLine.add(new Point(this.getWidth() - saiSoCanBang, (int) y));
            }else{
                listPointTwoShootLine.add(new Point(this.getWidth(), (int) y));
            }
        }
        // cạnh trên
        if (pointTwoLineStart.x > pointTwoLineEnd.x) {
            double x = (pointTwoLineEnd.getY() * Math.abs(pointTwoLineEnd.getX() - pointTwoLineStart.getX())) / (pointTwoLineEnd.getY() + pointTwoLineStart.getY()) + pointTwoLineEnd.getX();
            if(pointTwoLineEnd.y > customSaiSo){
                listPointTwoShootLine.add(new Point((int) x, 0 + saiSoCanBang));
            }else{
                listPointTwoShootLine.add(new Point((int) x, 0));
            }            
        } else {
            double x = (pointTwoLineStart.getY() * Math.abs(pointTwoLineEnd.getX() - pointTwoLineStart.getX())) / (pointTwoLineStart.getY() + pointTwoLineEnd.getY()) + pointTwoLineStart.getX();
            if(pointTwoLineEnd.y > customSaiSo){
                listPointTwoShootLine.add(new Point((int) x, 0 + saiSoCanBang));
            }else{
                listPointTwoShootLine.add(new Point((int) x, 0));
            }
        }
        // cạnh dưới
        if (pointTwoLineStart.x > pointTwoLineEnd.x) {
            double x = ((this.getHeight() - pointTwoLineEnd.getY()) * Math.abs(pointTwoLineEnd.getX() - pointTwoLineStart.getX())) / (2 * this.getHeight() - pointTwoLineEnd.getY() - pointTwoLineStart.getY()) + pointTwoLineEnd.getX();            
            if((this.getHeight() - pointTwoLineEnd.y) > customSaiSo){
                listPointTwoShootLine.add(new Point((int) x, this.getHeight() - saiSoCanBang));
            }else{
                listPointTwoShootLine.add(new Point((int) x, this.getHeight()));
            }
        } else {
            double x = ((this.getHeight() - pointTwoLineStart.getY()) * Math.abs(pointTwoLineEnd.getX() - pointTwoLineStart.getX())) / (2 * this.getHeight() - pointTwoLineStart.getY() - pointTwoLineEnd.getY()) + pointTwoLineStart.getX();
            if((this.getHeight() - pointTwoLineEnd.y) > customSaiSo){
                listPointTwoShootLine.add(new Point((int) x, this.getHeight() - saiSoCanBang));
            }else{
                listPointTwoShootLine.add(new Point((int) x, this.getHeight()));
            }
        }
        if (direction < listPointTwoShootLine.size()) {
            g.drawLine(pointTwoLineStart.x, pointTwoLineStart.y, listPointTwoShootLine.get(direction).x, listPointTwoShootLine.get(direction).y);
            g.drawLine(pointTwoLineEnd.x, pointTwoLineEnd.y, listPointTwoShootLine.get(direction).x, listPointTwoShootLine.get(direction).y);
        } else {
            direction = 0;
        }
        switch (hole) {
            case 1: {
                g.drawLine(radius + 3, radius + 3, pointTwoLineEnd.x, pointTwoLineEnd.y);
                break;
            }
            case 2: {
                g.drawLine(this.getWidth() / 2, radius / 2, pointTwoLineEnd.x, pointTwoLineEnd.y);
                break;
            }
            case 3: {
                g.drawLine(this.getWidth() - radius - 5, radius + 4, pointTwoLineEnd.x, pointTwoLineEnd.y);
                break;
            }
            case 4: {
                g.drawLine(radius + 4, this.getHeight() - radius - 6, pointTwoLineEnd.x, pointTwoLineEnd.y);
                break;
            }
            case 5: {
                g.drawLine(this.getWidth() / 2, this.getHeight() - radius / 2, pointTwoLineEnd.x, pointTwoLineEnd.y);
                break;
            }
            case 6: {
                g.drawLine(this.getWidth() - radius - 6, this.getHeight() - radius - 5, pointTwoLineEnd.x, pointTwoLineEnd.y);
                break;
            }
        }
    }

    public ToolJFrame getToolJFrame() {
        return toolJFrame;
    }

    public void setToolJFrame(ToolJFrame toolJFrame) {
        this.toolJFrame = toolJFrame;
    }

    public JPanelImage getjPanelImage() {
        return jPanelImage;
    }

    public void setjPanelImage(JPanelImage jPanelImage) {
        this.jPanelImage = jPanelImage;
    }

    public ArrayList<Point> getListPointTwoShootLine() {
        return listPointTwoShootLine;
    }

    public void setListPointTwoShootLine(ArrayList<Point> listPointTwoShootLine) {
        this.listPointTwoShootLine = listPointTwoShootLine;
    }

    public static int getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;
        int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
        return gray;
    }
}

class JPanelImage extends JPanel {

    private JPanelTable jPanelTable;

    public JPanelImage(JPanelTable jPanelTable) {
        this.jPanelTable = jPanelTable;
        this.setBounds(0, 0, jPanelTable.getWidth(), jPanelTable.getHeight());
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Properties.colorLine);
        jPanelTable.locateBall(g);
        if (KeyBoard.tryLine && JPanelTable.pointSelected != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(Properties.LineSize));
            jPanelTable.tryRoad(g2, Properties.soDuongDapBang);
        }
        if (KeyBoard.twoShootLine && JPanelTable.pointSelected != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(Properties.LineSize));
            int radius = (int) ((int) this.getHeight() / (2 * Properties.ratioBall));
            jPanelTable.twoShootLine(g2, radius, JPanelTable.hole, KeyBoard.direction);
        }
    }
}
