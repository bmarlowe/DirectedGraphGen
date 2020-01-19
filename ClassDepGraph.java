// Filename: ClassDepGraph.java
// Author: Brad Marlowe
// Date: 3-7-19
// Purpose: CMSC350 Project 4
// Take an input of class dependency information (txt file) and generate
// a directed graph in topological order
// and output the order of recompilation

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



public class ClassDepGraph extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    //These variables set up the GUI using the swing framework
    private static JFrame mainWindow = new JFrame("Class Dependency Graph");
    private static JButton buildBtn = new JButton("Build Directed Graph");
    private static JButton sortBtn = new JButton("Topological Order");
    private static JTextField inputTxt = new JTextField();
    private static JLabel inputLbl = new JLabel("Input file name:");
    private static JTextField classTxt = new JTextField();
    private static JLabel classLbl = new JLabel("Class to recompile:");
    private static JPanel orderPnl = new JPanel();
    private static JTextArea outputTxt = new JTextArea();

    private String fileName;
    private String className;
    private DirectedGraph<String> dGraph;
    
    private ClassDepGraph() {
        initComponents();

        buildBtn.addActionListener(new ActionListener() {  //This will happen when the Build Directed Graph button is clicked by the user
            public void actionPerformed(ActionEvent build) {
                
                dGraph = new DirectedGraph<>();

                fileName = inputTxt.getText();

                try {
                    dGraph.buildDGraphFromFile(dGraph.tokenizeFile(fileName));
                    JOptionPane.showMessageDialog(null, "Graph Created Successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "File Did Not Open", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sortBtn.addActionListener(new ActionListener() {  //This will happen when the Topological Order button is clicked by the user
            public void actionPerformed(ActionEvent sort) {
                
                className = classTxt.getText();

                try {
                    outputTxt.setText(dGraph.topOrdGeneration(className));
                } catch (InvalidClassNameException e1) {
                    JOptionPane.showMessageDialog(null, "Invalid Class Name: " + className, "Error", JOptionPane.ERROR_MESSAGE);
                } catch (CycleException e2) {
                    JOptionPane.showMessageDialog(null, "This DGraph contains a Cycle!", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void initComponents() {
        //setting up the layout of our GUI manually
        classLbl.setBounds(20, 75, 150, 25);
        classTxt.setBounds(170, 75, 200, 25);
        inputLbl.setBounds(20, 20, 150, 25);
        inputTxt.setBounds(170, 20, 200, 25);
        buildBtn.setBounds(400, 20, 150, 25);
        sortBtn.setBounds(400, 75, 150, 25);
        orderPnl.setBounds(5, 125, 570, 230);
        orderPnl.setBorder(BorderFactory.createTitledBorder("Recompilation Order"));
        outputTxt.setBounds(75, 150, 450, 200);

        outputTxt.setLineWrap(true);
        outputTxt.setWrapStyleWord(true);
        outputTxt.setEditable(false);
        
        mainWindow.add(buildBtn);
        mainWindow.add(sortBtn);
        mainWindow.add(inputTxt);
        mainWindow.add(inputLbl);
        mainWindow.add(classTxt);
        mainWindow.add(classLbl);
        mainWindow.add(orderPnl);
        orderPnl.add(outputTxt);
        
        mainWindow.setSize(600, 400);
        mainWindow.setLayout(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args)  {
    
        new ClassDepGraph();
              
    }
}