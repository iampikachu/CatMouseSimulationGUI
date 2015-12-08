import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CatMouseSimulationGUI{
    JFrame frame;
    int pixelPerMeter = 1;
    int catRadius;
    double catAngle;
    double mouseAngle;
    double millisecondsPerTimeStep;
    int time = 0;
    JLabel currentTimeLabel;
    DrawPanel drawPanel;

    //started cat and mouse coordinates
    Position cat_position;
    Position mouse_position;

    //save reset positions;
    Position reset_cat;
    Position reset_mouse; 

    Cat cat;
    Mouse mouse;
    boolean eaten = false;
    boolean done = true;

    public static void main(String [] args){
        if(args.length != 5){
            System.out.println("Usage: java CatMouseSimulationGUI <pixelsPerMeter> <millisecondsPerSimStep> <catRadius> <catAngle> <mouseAngle>");
            return;
        }

        CatMouseSimulationGUI gui = new CatMouseSimulationGUI();

        gui.pixelPerMeter = Integer.parseInt(args[0]);
        gui.millisecondsPerTimeStep = Double.parseDouble(args[1]);
        gui.catRadius = Integer.parseInt(args[2]);
        gui.catAngle = Double.parseDouble(args[3]);
        gui.mouseAngle = Double.parseDouble(args[4]);

        gui.cat_position = new Position(gui.catRadius, gui.catAngle);
        gui.mouse_position = new Position(1.0, gui.mouseAngle);
        gui.cat = new Cat(gui.cat_position);
        gui.mouse = new Mouse(gui.mouse_position);

        //for reset
        gui.reset_cat = new Position(gui.cat_position);
        gui.reset_mouse = new Position(gui.mouse_position);

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(UnsupportedLookAndFeelException e){}
         catch(ClassNotFoundException e){}
         catch(InstantiationException e){}
         catch(IllegalAccessException e){}
              
        gui.go();
    }

    public void go(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //draw panel for animation
        drawPanel = new DrawPanel();

        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("reset");
        JButton stepButton = new JButton("step");
        JButton runButton = new JButton("run");
        JButton quitButton = new JButton("quit");
        JLabel timeLabel = new JLabel("Time: ");
        currentTimeLabel = new JLabel(Integer.toString(time));

        //Add buttons to panel
        buttonPanel.add(resetButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(runButton);
        buttonPanel.add(quitButton);
        buttonPanel.add(timeLabel);
        buttonPanel.add(currentTimeLabel);

        //add listeners for each button
        resetButton.addActionListener(new resetListener());
        quitButton.addActionListener(new quitListener());
        stepButton.addActionListener(new StepListener());
        runButton.addActionListener(new runListener());

        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.getContentPane().add(drawPanel);
        
        frame.setSize(400, 400);
        frame.setVisible(true); 
    }
    
    //Button classes
    class resetListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            eaten = false;
            cat_position = new Position(reset_cat);
            mouse_position = new Position(reset_mouse);
            cat.changePosition(cat_position);
            mouse.changePosition(mouse_position);
            catAngle = cat.getPosition().getAngle();
            catRadius = (int)cat.getPosition().getRadius();
            mouseAngle = mouse.getPosition().getAngle();

            //redraw
            drawPanel.repaint();

            //restart time label
            time = 0;
            done = true;
            currentTimeLabel.setText(Integer.toString(time));
        }
    }
    class StepListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(eaten){
                String ret = time + " eaten " + mouse.getPosition().toString() + " " + cat.getPosition().toString();
                JOptionPane.showMessageDialog(frame, ret);
                done = false;
            }    
            else if(time > 30){
                String ret = time + " escaped " + mouse.getPosition().toString() + " " + cat.getPosition().toString();
                JOptionPane.showMessageDialog(frame, ret);
                done = false;
            }
            else{    
                eaten = cat.move(mouse.getPosition());
                mouse.move();
                mouseAngle = mouse.getPosition().getAngle();
                catAngle = cat.getPosition().getAngle();
                catRadius = (int)cat.getPosition().getRadius();
                
                //redraw the panel
                drawPanel.repaint();
                try{
                    Thread.sleep((int)millisecondsPerTimeStep);
                }catch(Exception ex) {}    

                time++;
                currentTimeLabel.setText(Integer.toString(time));
            }
        }
    }
    class runListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            StepListener stepListener = new StepListener();
            while(time < 31 && done){
                stepListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        }
    }
    class quitListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            System.exit(0);
        }
    }
    
    class DrawPanel extends JPanel{
        public void paintComponent(Graphics g){
            //redrawing the panel
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

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
            //need to scale catRadius by multiplying by radius (radius * catRadius)
            int arrowTip2x = (int)(middlex + radius * catRadius * Math.cos(catAngle));
            int arrowTip2y = (int)(middley - radius * catRadius * Math.sin(catAngle));
            int catDiameter = radius/6;
            int catCenterx = arrowTip2x - (catDiameter)/2;
            int catCentery = arrowTip2y - (catDiameter)/2;

            g.setColor(Color.black);
            g.drawOval(catCenterx, catCentery, catDiameter, catDiameter);
            g.setColor(Color.blue);
            g.fillOval(catCenterx, catCentery, catDiameter, catDiameter);

            //draw line between cat and mouse
            g.setColor(Color.black);
            g.drawLine(arrowTipx, arrowTipy, arrowTip2x - (int)(catDiameter/2 * Math.cos(catAngle)), arrowTip2y + (int)(catDiameter/2 * Math.sin(catAngle)));

            //direction indicator
            int catDirectionTipx;
            int catDirectionTipy;
            if(radius * catRadius == radius){
                catDirectionTipx = (int)(middlex + radius * Math.cos(catAngle + .07));
                catDirectionTipy = (int)(middley - radius * Math.sin(catAngle + .07));
            }
            else{
                catDirectionTipx = arrowTip2x - (int)(catDiameter/2 * Math.cos(catAngle));
                catDirectionTipy = arrowTip2y +  (int)(catDiameter/2 * Math.sin(catAngle));
            }
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
