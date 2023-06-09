package tanks.game.display;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import javax.swing.JFrame;

import tanks.game.myinput.MyInput;

public abstract class Display {
	private static boolean created = false;
    private static JFrame window;
    private static Canvas content;
 
    private static BufferedImage bufferImage;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clierColor;
    private static BufferStrategy bufferStrategy;
 
    public static void create(int width, int height, String title,
            int _clierColor, int numBuffers) {
 
        if (created) {
            return;
        }
 
        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = new Canvas();
 
        Dimension size = new Dimension(width, height);
        content.setPreferredSize(size);
 
        window.setResizable(true);
        window.getContentPane().add(content);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
 
        bufferImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) (bufferImage.getRaster().getDataBuffer()))
                .getData();
        bufferGraphics = bufferImage.getGraphics();
        ((Graphics2D) bufferGraphics).setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        clierColor = _clierColor;
 
        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();
 
        created = true;
    }
 
    public static void clear() {
        Arrays.fill(bufferData, clierColor);
    }
 
    public static void swapBuffer() {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(bufferImage, 0, 0, null);
        bufferStrategy.show();
    }
 
    public static Graphics2D getGraphics2d() {
        return (Graphics2D) bufferGraphics;
    }
 
    public static void destroyWindow() {
        if (!created) {
            return;
        }
        window.dispose();
    }
 
    public static void setTitle(String title) {
        window.setTitle(title);
    }

	public static void addInputListener(MyInput enterButton) {
		window.add(enterButton);
		
	}
}
