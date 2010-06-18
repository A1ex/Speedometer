/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * appWindow.java
 *
 * Created on May 14, 2010, 11:30:06 AM
 */

package Interface;


import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Alexandru Popescu
 */
public class appWindow extends javax.swing.JFrame  {
   
    public boolean apasatsc1=false;                             //determina daca s-a apasat optiunea de meniu "Load Scenario 1"
    public boolean apasatsc2=false;                             //determina daca s-a apasat optiunea de meniu "Load Scenario 2"
    public boolean apasatsm1=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 1"
    public boolean apasatsm2=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 2"
    public boolean apasatsm3=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 3"
    public boolean apasatsm4=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 4"
    public boolean apasatsm5=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 5"
    public boolean apasatsm6=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 6"
    public boolean apasatsm7=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 7"
    public boolean apasatsm8=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 8"
    public boolean apasatsm9=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 9"
    public boolean apasatsm10=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 10"
    public boolean apasatmute=false;                            //determina daca s-a apasat optiunea de meniu "Mute Engine"
    public boolean apasatunmute=false;                          //determina daca s-a apasat optiunea de meniu "Unmute Engine"
    public boolean apasatstart=false;                           //determina daca s-a apasat optiunea de meniu "Start"
    public boolean apasatcontrol=false;                         //determina daca s-a apasat optiunea de meniu "Turn off/on control"
    public boolean incarcatscenariu=false;
    public boolean incarcatscenariutest=false;
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public appWindow() throws IOException, SQLException{        //Constructor
        initComponents();
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        Options = new javax.swing.JMenu();
        Mute = new javax.swing.JMenuItem();
        swich = new javax.swing.JMenuItem();
        Start = new javax.swing.JMenu();
        ComplexTests = new javax.swing.JMenu();
        Scenario1 = new javax.swing.JMenuItem();
        Scenario2 = new javax.swing.JMenuItem();
        WarningTests = new javax.swing.JMenu();
        wt1 = new javax.swing.JMenuItem();
        wt2 = new javax.swing.JMenuItem();
        wt3 = new javax.swing.JMenuItem();
        wt4 = new javax.swing.JMenuItem();
        wt5 = new javax.swing.JMenuItem();
        wt6 = new javax.swing.JMenuItem();
        IndicatorTests = new javax.swing.JMenu();
        it1 = new javax.swing.JMenuItem();
        it2 = new javax.swing.JMenuItem();
        it3 = new javax.swing.JMenuItem();
        it4 = new javax.swing.JMenuItem();
        Help = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jMenuBar1.setBackground(new java.awt.Color(0, 0, 0));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));

        Options.setBackground(new java.awt.Color(0, 0, 0));
        Options.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Options.setForeground(new java.awt.Color(204, 204, 204));
        Options.setText("Options");

        Mute.setBackground(new java.awt.Color(0, 0, 0));
        Mute.setForeground(new java.awt.Color(204, 204, 204));
        Mute.setText("Mute Engine");
        Mute.setBorder(null);
        Mute.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SetareMute(evt);
            }
        });
        Options.add(Mute);

        swich.setBackground(new java.awt.Color(0, 0, 0));
        swich.setForeground(new java.awt.Color(204, 204, 204));
        swich.setText("Turn off manual control");
        swich.setBorder(null);
        swich.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SetareControl(evt);
            }
        });
        Options.add(swich);

        jMenuBar1.add(Options);

        Start.setBackground(new java.awt.Color(0, 0, 0));
        Start.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Start.setForeground(new java.awt.Color(204, 204, 204));
        Start.setText("Start");
        Start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ApasatStart(evt);
            }
        });
        jMenuBar1.add(Start);

        ComplexTests.setBackground(new java.awt.Color(0, 0, 0));
        ComplexTests.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ComplexTests.setForeground(new java.awt.Color(204, 204, 204));
        ComplexTests.setText("Complex Tests");

        Scenario1.setBackground(new java.awt.Color(0, 0, 0));
        Scenario1.setForeground(new java.awt.Color(204, 204, 204));
        Scenario1.setText("Complex Test Scenario 1");
        Scenario1.setBorder(null);
        Scenario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSC1(evt);
            }
        });
        ComplexTests.add(Scenario1);

        Scenario2.setBackground(new java.awt.Color(0, 0, 0));
        Scenario2.setForeground(new java.awt.Color(204, 204, 204));
        Scenario2.setText("Complex Test Scenario 2");
        Scenario2.setBorder(null);
        Scenario2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSC2(evt);
            }
        });
        ComplexTests.add(Scenario2);

        jMenuBar1.add(ComplexTests);

        WarningTests.setBackground(new java.awt.Color(0, 0, 0));
        WarningTests.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        WarningTests.setForeground(new java.awt.Color(204, 204, 204));
        WarningTests.setText("Warning Tests");

        wt1.setBackground(new java.awt.Color(0, 0, 0));
        wt1.setForeground(new java.awt.Color(204, 204, 204));
        wt1.setText("Open Doors Alarm Test");
        wt1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM2(evt);
            }
        });
        WarningTests.add(wt1);

        wt2.setBackground(new java.awt.Color(0, 0, 0));
        wt2.setForeground(new java.awt.Color(204, 204, 204));
        wt2.setText("Seatbelt Alarm Test");
        wt2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM3(evt);
            }
        });
        WarningTests.add(wt2);

        wt3.setBackground(new java.awt.Color(0, 0, 0));
        wt3.setForeground(new java.awt.Color(204, 204, 204));
        wt3.setText("Fuel Level Alarm Test");
        wt3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM5(evt);
            }
        });
        WarningTests.add(wt3);

        wt4.setBackground(new java.awt.Color(0, 0, 0));
        wt4.setForeground(new java.awt.Color(204, 204, 204));
        wt4.setText("Battery Level Alarm Test");
        wt4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM6(evt);
            }
        });
        WarningTests.add(wt4);

        wt5.setBackground(new java.awt.Color(0, 0, 0));
        wt5.setForeground(new java.awt.Color(204, 204, 204));
        wt5.setText("Lights Alarm Test");
        wt5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM9(evt);
            }
        });
        WarningTests.add(wt5);

        wt6.setBackground(new java.awt.Color(0, 0, 0));
        wt6.setForeground(new java.awt.Color(204, 204, 204));
        wt6.setText("Fuel & Battery Level Alarm Test");
        wt6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM10(evt);
            }
        });
        WarningTests.add(wt6);

        jMenuBar1.add(WarningTests);

        IndicatorTests.setBackground(new java.awt.Color(51, 51, 51));
        IndicatorTests.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        IndicatorTests.setForeground(new java.awt.Color(204, 204, 204));
        IndicatorTests.setText("Indicator Tests");

        it1.setBackground(new java.awt.Color(0, 0, 0));
        it1.setForeground(new java.awt.Color(204, 204, 204));
        it1.setText("Start/Stop Engine Test");
        it1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM1(evt);
            }
        });
        IndicatorTests.add(it1);

        it2.setBackground(new java.awt.Color(0, 0, 0));
        it2.setForeground(new java.awt.Color(204, 204, 204));
        it2.setText("Acceleration Test");
        it2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM4(evt);
            }
        });
        IndicatorTests.add(it2);

        it3.setBackground(new java.awt.Color(0, 0, 0));
        it3.setForeground(new java.awt.Color(204, 204, 204));
        it3.setText("Brake Test");
        it3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM7(evt);
            }
        });
        IndicatorTests.add(it3);

        it4.setBackground(new java.awt.Color(0, 0, 0));
        it4.setForeground(new java.awt.Color(204, 204, 204));
        it4.setText("Deceleration Test");
        it4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM8(evt);
            }
        });
        IndicatorTests.add(it4);

        jMenuBar1.add(IndicatorTests);

        Help.setBackground(new java.awt.Color(0, 0, 0));
        Help.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Help.setForeground(new java.awt.Color(204, 204, 204));
        Help.setText("Help");
        jMenuBar1.add(Help);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 617, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SetareMute(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SetareMute
        // TODO add your handling code here:
        if (apasatmute==false){
            apasatmute=true;
            apasatunmute=false;
            Mute.setText("Unmute Engine");
        }
        else{
            if (apasatunmute==false){
                apasatunmute=true;
                apasatmute=false;
                Mute.setText("Mute Engine");               
            }
        }
    }//GEN-LAST:event_SetareMute

    private void ApasatStart(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ApasatStart

        if (incarcatscenariu||incarcatscenariutest){
            if (!apasatstart){
                apasatstart=true;
                Start.setText("Pause");
            }
            else{
                apasatstart=false;
                Start.setText("Resume");
            }
        }
    }//GEN-LAST:event_ApasatStart

    private void SetareControl(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SetareControl
        // TODO add your handling code here:
        if (!apasatstart){
             if (apasatcontrol==false){
                apasatcontrol=true;
                swich.setText("Turn on manual control");
             }
             else{
                apasatcontrol=false;
                swich.setText("Turn off manual control");
             }
        }
    }//GEN-LAST:event_SetareControl

    private void IncarcaSC1(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSC1
        // TODO add your handling code here:
        if (apasatcontrol){
            if (!incarcatscenariu){
                apasatsc1=true;
                incarcatscenariu=true;
            }
        }
    }//GEN-LAST:event_IncarcaSC1

    private void IncarcaSC2(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSC2
        // TODO add your handling code here:
        if (apasatcontrol){
            if (!incarcatscenariu){
                apasatsc2=true;
                incarcatscenariu=true;
            }
        }
    }//GEN-LAST:event_IncarcaSC2

    private void IncarcaSM1(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM1
        // TODO add your handling code here:
        if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm1=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM1

    private void IncarcaSM2(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM2
        // TODO add your handling code here:
        if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm2=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM2

    private void IncarcaSM3(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM3
        // TODO add your handling code here:
         if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm3=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM3

    private void IncarcaSM4(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM4
        // TODO add your handling code here:
        if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm4=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM4

    private void IncarcaSM5(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM5
        // TODO add your handling code here:
         if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm5=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM5

    private void IncarcaSM6(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM6
        // TODO add your handling code here:
        if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm6=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM6

    private void IncarcaSM7(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM7
        // TODO add your handling code here:
        if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm7=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM7

    private void IncarcaSM8(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM8
        // TODO add your handling code here:
         if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm8=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM8

    private void IncarcaSM9(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM9
        // TODO add your handling code here:
        if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm9=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM9

    private void IncarcaSM10(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSM10
        // TODO add your handling code here:
         if (apasatcontrol){
            if (!incarcatscenariutest&&!incarcatscenariu){
                apasatsm10=true;
                incarcatscenariutest=true;
            }
        }
    }//GEN-LAST:event_IncarcaSM10


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ComplexTests;
    private javax.swing.JMenu Help;
    private javax.swing.JMenu IndicatorTests;
    private javax.swing.JMenuItem Mute;
    private javax.swing.JMenu Options;
    private javax.swing.JMenuItem Scenario1;
    private javax.swing.JMenuItem Scenario2;
    public javax.swing.JMenu Start;
    private javax.swing.JMenu WarningTests;
    public javax.swing.JMenuItem it1;
    public javax.swing.JMenuItem it2;
    public javax.swing.JMenuItem it3;
    public javax.swing.JMenuItem it4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem swich;
    public javax.swing.JMenuItem wt1;
    public javax.swing.JMenuItem wt2;
    public javax.swing.JMenuItem wt3;
    public javax.swing.JMenuItem wt4;
    public javax.swing.JMenuItem wt5;
    public javax.swing.JMenuItem wt6;
    // End of variables declaration//GEN-END:variables

}
