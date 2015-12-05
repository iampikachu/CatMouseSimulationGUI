import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CatMouseSimulationGUI{
    JFrame frame;
    int pixelPerMeter = 1;
    int catRadius;
    double catAngle;
    double mouseAngle;

    public static void main(String [] args){
        if(args.length != 5){
            System.out.println("Usage: java CatMouseSimulationGUI <pixelsPerMeter> <millisecondsPerSimStep> <catRadius> <catAngle> <mouseAngle>");
            return;
        }

        CatMouseSimulationGUI gui = new CatMouseSimulationGUI();

        gui.pixelPerMeter = Integer.parseInt(args[0]);
        double millisecondsPerSimStep = Double.parseDouble(args[1]);
        gui.catRadius = Integer.parseInt(args[2]);
        gui.catAngle = Double.parseDouble(args[3]);
        gui.mouseAngle = Double.parseDouble(args[4]);

        gui.go();
    }

    public void go(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //draw panel for animation
        DrawPanel drawPanel = new DrawPanel();

        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("reset");
        JButton stepButton = new JButton("step");
        JButton runButton = new JButton("run");
        JButton quitButton = new JButton("quit");
        JLabel timeLabel = new JLabel("Time: ");
        JLabel currentTimeLabel = new JLabel("0");
        
        //Add buttons to panel
        buttonPanel.add(resetButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(runButton);
        buttonPanel.add(quitButton);
        buttonPanel.add(timeLabel);
        buttonPanel.add(currentTimeLabel);

        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.getContentPane().add(drawPanel);
        
        frame.setSize(400, 400);
        frame.setVisible(true); 
        
        /*
        try{
            Thread.sleep(10000);
        }catch(Exception ex){}    
        */
        //currentTimeLabel.setText("2");
    }
    
    class DrawPanel extends JPanel{
        public void paintComponent(Graphics g){
            //1 meter = 1 pixel
            int radius = pixelPerMeter;
            int diameter = radius * 2;

            //Note only top right corner
            int cornerx = getWidth()/2 - diameter/2;
            int cornery = getHeight()/2 - diameter/2;

            //middle
            int middlex = cornerx + diameter/2;
            int middley = cornery + diameter/2;
    
            //for border
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(cornerx, cornery, diameter, diameter);
            //for fill
            g.setColor(Color.gray);
            g.fillOval(cornerx, cornery, diameter, diameter);

            //first ball (mouse)
            int arrowTipx = (int)(middlex + radius * Math.cos(mouseAngle));
            int arrowTipy = (int)(middley - radius * Math.sin(mouseAngle));
            int mouseDiameter = radius/6;
            int mouseCenterx = arrowTipx - (mouseDiameter)/2;
            int mouseCentery = arrowTipy - (mouseDiameter)/2;

            g.setColor(Color.black);
            g.drawOval(mouseCenterx, mouseCentery, mouseDiameter, mouseDiameter);
            g.setColor(Color.yellow);
            g.fillOval(mouseCenterx, mouseCentery, mouseDiameter, mouseDiameter);
            
            //direction indicator
            int mouseDirectionTipx = (int)(middlex + radius * Math.cos(mouseAngle + .07));
            int mouseDirectionTipy = (int)(middley - radius * Math.sin(mouseAngle + .07));
            int mouseDirectionDiameter = radius/18;
            int mouseDirectionCenterx = mouseDirectionTipx - (mouseDirectionDiameter)/2;
            int mouseDirectionCentery = mouseDirectionTipy - (mouseDirectionDiameter)/2;

            g.setColor(Color.black);
            g.drawOval(mouseDirectionCenterx, mouseDirectionCentery, mouseDirectionDiameter, mouseDirectionDiameter);
            g.setColor(Color.red);
            g.fillOval(mouseDirectionCenterx, mouseDirectionCentery, mouseDirectionDiameter, mouseDirectionDiameter);

            //second ball (cat)
            int arrowTip2x = (int)(middlex + catRadius * Math.cos(catAngle));
            int arrowTip2y = (int)(middley - catRadius * Math.sin(catAngle));
            int catDiameter = radius/6;
            int catCenterx = arrowTip2x - (catDiameter)/2;
            int catCentery = arrowTip2y - (catDiameter)/2;

            g.setColor(Color.black);
            g.drawOval(catCenterx, catCentery, catDiameter, catDiameter);
            g.setColor(Color.blue);
            g.fillOval(catCenterx, catCentery, catDiameter, catDiameter);
            
            //direction indicator
            int catDirectionTipx = (int)(middlex + radius * Math.cos(catAngle + .07));
            int catDirectionTipy = (int)(middley - radius * Math.sin(catAngle + .07));
            int catDirectionDiameter = radius/18;
            int catDirectionCenterx = catDirectionTipx - (catDirectionDiameter)/2;
            int catDirectionCentery = catDirectionTipy - (catDirectionDiameter)/2;

            g.setColor(Color.black);
            g.drawOval(catDirectionCenterx, catDirectionCentery, catDirectionDiameter, catDirectionDiameter);
            g.setColor(Color.red);
            g.fillOval(catDirectionCenterx, catDirectionCentery, catDirectionDiameter, catDirectionDiameter);

        }
    }             
        
}           
