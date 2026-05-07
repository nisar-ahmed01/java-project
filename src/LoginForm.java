//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Path2D;
//
////class CustomRadiusPanel extends JPanel {
////
////    private int tl, tr, br, bl;
////
////    public CustomRadiusPanel(int tl, int tr, int br, int bl) {
////        this.tl = tl;
////        this.tr = tr;
////        this.br = br;
////        this.bl = bl;
////        setOpaque(false);
////    }
////
////    @Override
////    protected void paintComponent(Graphics g) {
////        Graphics2D g2 = (Graphics2D) g.create();
////
////        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
////                RenderingHints.VALUE_ANTIALIAS_ON);
////
////        int w = getWidth();
////        int h = getHeight();
////
////        Path2D path = new Path2D.Float();
////
////        // Start top-left
////        path.moveTo(tl, 0);
////
////        // Top edge
////        path.lineTo(w - tr, 0);
////        path.quadTo(w, 0, w, tr);
////
////        // Right edge
////        path.lineTo(w, h - br);
////        path.quadTo(w, h, w - br, h);
////
////        // Bottom edge
////        path.lineTo(bl, h);
////        path.quadTo(0, h, 0, h - bl);
////
////        // Left edge
////        path.lineTo(0, tl);
////        path.quadTo(0, 0, tl, 0);
////
////        path.closePath();
////
////        // Fill background
////        g2.setColor(getBackground());
////        g2.fill(path);
////
////        g2.dispose();
////
////        super.paintComponent(g);
////    }
////}
//public class LoginForm{
//public static void main(String[] args) {
//
//    JButton button = new JButton("click");
//
//    CustomRadiusPanel rightPanel = new CustomRadiusPanel(160,30,30,100);
//    rightPanel.setBackground(new Color(82, 60, 175));
//    rightPanel.setBounds(340,0,340,430);
//    rightPanel.setLayout(null);
//    rightPanel.add(button);
//    rightPanel.setLocation(340, 0); // start outside screen.
//
//
//    button.setBounds(120,330,100,30);
//    button.setFocusable(false);
//    button.addActionListener(e -> {
//
//        int startX = rightPanel.getX();
//        int endX = (startX == 340) ? 0 : 340;
//
//        int duration = 1000; // ms (adjust for speed)
//        long startTime = System.currentTimeMillis();
//
//        Timer timer = new Timer(10, null);
//
//        timer.addActionListener(ev -> {
//
//            float fraction = (System.currentTimeMillis() - startTime) / (float) duration;
//
//            if (fraction > 1) fraction = 1;
//
//            // EASING (easeOutCubic)
//            float ease = (float)(1 - Math.pow(1 - fraction, 3));
//
//            int x = (int)(startX + (endX - startX) * ease);
//
//            rightPanel.setLocation(x, 0);
//
//            if (fraction == 1) {
//                timer.stop();
//            }
//        });
//
//        timer.start();
//    });
//
//
//    CustomRadiusPanel panel1 = new CustomRadiusPanel(30,30,30,30);
//    panel1.setBounds(300,145,680,430);
//    panel1.setBackground(Color.white);
//    panel1.setLayout(null);
//    panel1.add(rightPanel);
//
//    JFrame  frame = new JFrame();
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//    frame.getContentPane().setBackground(new Color(217, 222, 237));
//    frame.setLayout(null);
//    frame.add(panel1);
//    frame.setVisible(true);
//
//    }
//}
//
