package com.uno.gui;


import com.uno.cards.AbsCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import static com.uno.gui.CardManager.setCardImage;


/**
 * Created by ${gaboq} on 25/9/2017.
 */

public abstract class GameView {


    public static ImageIcon generateCardIcon(AbsCard card) {
        ImageIcon img = new ImageIcon(setCardImage(card));
        Image resizedImg = getScaledImage(img.getImage(), 156, 229);
        img = new ImageIcon(resizedImg);
        return img;
    }

    static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    static void setButtonProps(JButton btn, ImageIcon img) {
        btn.setIcon(img);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
    }

    public void displayMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    protected JFrame initFrame(String name) {
        JFrame window = new JFrame(name);
        window.setPreferredSize(new Dimension(1460, 950));
        ImageIcon img_icon = new ImageIcon("res/uno_icon.png");
        window.setIconImage(img_icon.getImage());
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setResizable(false);
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(window, "Desea cerrar la aplicacion?", "Cerrar programa", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        //window.setLocationRelativeTo(null);
        return window;
    }

    protected abstract void setComponents(JFrame window) throws Exception;


    //===================== Main ========================
    public static void main(String[] args) throws Exception {
        new MainLayout();
    }

}
