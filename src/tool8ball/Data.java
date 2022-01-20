/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool8ball;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Data {
    
    private static String path = System.getProperty("user.dir") + File.separator + "data" + File.separator + "data.bin";
    private static String pathData = System.getProperty("user.dir") + File.separator + "data";
    
    public static boolean readData(){
        
        File file = new File(path);
        if(file.exists() == false){
            return false;
        }
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            ToolJFrame.SCREEN_WIDTH = Integer.parseInt(br.readLine());
            ToolJFrame.SCREEN_HEIGHT = Integer.parseInt(br.readLine());
            ToolJFrame.pointJFrame = new Point(Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()));
            Properties.LineSize = Integer.parseInt(br.readLine());
            Properties.LineSizeOuterBorder = Integer.parseInt(br.readLine());
            Properties.soDuongDapBang = Integer.parseInt(br.readLine());
            Properties.colorLine = new Color(Integer.parseInt(br.readLine()));
            Properties.colorOuterBorder = new Color(Integer.parseInt(br.readLine()));
            Properties.isAutoBall = Boolean.parseBoolean(br.readLine());
            Properties.ratioBall = Double.parseDouble(br.readLine());
            Properties.space = Integer.parseInt(br.readLine());
            Properties.isOnTop = Boolean.parseBoolean(br.readLine());
            Properties.isAlwaysFocus = Boolean.parseBoolean(br.readLine());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
    
    public static void writeData(){
        File file = new File(path);
        if(file.exists() == false){
            try {
                File file1 = new File(pathData);
                if(file1.exists() == false){
                    file1.mkdirs();
                }
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(ToolJFrame.SCREEN_WIDTH + "\n");
            bw.write(ToolJFrame.SCREEN_HEIGHT + "\n");
            bw.write(ToolJFrame.pointJFrame.x + "\n");
            bw.write(ToolJFrame.pointJFrame.y + "\n");
            bw.write(Properties.LineSize + "\n");
            bw.write(Properties.LineSizeOuterBorder + "\n");
            bw.write(Properties.soDuongDapBang + "\n");
            bw.write(Properties.colorLine.getRGB() + "\n");
            bw.write(Properties.colorOuterBorder.getRGB() + "\n");
            bw.write(Properties.isAutoBall + "\n");
            bw.write(Properties.ratioBall + "\n");
            bw.write(Properties.space + "\n");
            bw.write(Properties.isOnTop + "\n");
            bw.write(Properties.isAlwaysFocus + "\n");
        } catch (Exception e) {
        }finally{
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
