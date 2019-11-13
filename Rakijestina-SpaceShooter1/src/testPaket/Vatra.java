package testPaket;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author leonardo
 */
public class Vatra extends JFrame {
    
    private int[] palette = new int[256];
    private BufferedImage offscreen;
    private int[] data;
    private int[] data2;
    
    public Vatra() {
        setTitle("Old (School) Fire Effect Test");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        offscreen = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
        data = ((DataBufferInt) offscreen.getRaster().getDataBuffer()).getData();
        data2 = new int[400 * 320];
        
        for (int x = 0; x < 256; x++) {
            float saturation = x > 96 ? 0 : 1f - x / 128f; 
            palette[x] = Color.HSBtoRGB(x / 576f, saturation, Math.min(1f, x / 48f));
        }
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(8);
                    } catch (InterruptedException ex) { }
                }
            }
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        
        for (int x = 0; x < 400; x++) {
            data2[x + 400 * 318] = Math.random() > 0.55 ? 0 : 255;
        }
        
        for (int y = 5; y < 320 - 2; y++) {
            for (int x = 5; x < 400 - 2; x++) {
                data2[x + 400 * y] = 
                    (int) ((int) ( (data2[x + 400 * y]
                    + data2[x + 400 * (y + 1)]
                    + data2[(x - 1) + 400 * (y + 1)]
                    + data2[(x + 1) + 400 * (y + 1)]
                    + data2[x + 400 * (y + 2)]
                        ) / 5.045 ) * 1.01);
            }
        }
        
        for (int i = 0; i < data.length; i++) {
            data[i] = palette[data2[i]];
        }
        g.drawImage(offscreen, 0, 0, 800, 600, null);
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Vatra().setVisible(true);
            }
        });
    }
    
}