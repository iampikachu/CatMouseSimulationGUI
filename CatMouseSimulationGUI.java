import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CatMouseSimulationGUI{
    JFrame frame;

    public static void main(String [] args){
        if(args.length != 5){
            System.out.println("Usage: java CatMouseSimulationGUI <pixelsPerMeter> <millisecondsPerSimStep> <catRadius> <catAngle> <mouseAngle>");
            return;
        }
        double pixelPerMeter = Double.parseDouble(args[0]);
        double millisecondsPerSimStep = Double.parseDouble(args[1]);
        double catRadius = Double.parseDouble(args[2]);
        double catAngle = Double.parseDouble(args[3]);
        double mouseAngle = Double.parseDouble(args[4]);

        CatMouseSimulationGUI gui = new CatMouseSimulationGUI();
        gui.go();
    }

    public void go(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("reset");
        JButton stepButton = new JButton("step");
        JButton runButton = new JButton("run");
        JButton quitButton = new JButton("quit");
        
        //Add buttons to panel
        buttonPanel.add(resetButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(runButton);
        buttonPanel.add(quitButton);

        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        
        frame.setSize(300, 300);
        frame.setVisible(true); 
    }
}           
