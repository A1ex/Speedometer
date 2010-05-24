/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AppWindow.java
 *
 * Created on May 14, 2010, 11:30:06 AM
 */

package speedometer;


import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Alexandru Popescu
 */
public class AppWindow extends javax.swing.JFrame  {
   
    public boolean apasatsc1=false;                             //determina daca s-a apasat optiunea de meniu "Load Scenario 1"
    public boolean apasatsc2=false;                             //determina daca s-a apasat optiunea de meniu "Load Scenario 2"
    public boolean apasatsm1=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 1"
    public boolean apasatsm2=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 2"
    public boolean apasatsm3=false;                             //determina daca s-a apasat optiunea de meniu  "Small test Scenario 3"
    public boolean apasatmute=false;                            //determina daca s-a apasat optiunea de meniu "Mute Engine"
    public boolean apasatunmute=false;                          //determina daca s-a apasat optiunea de meniu "Unmute Engine"
    public boolean apasatstart=false;                           //determina daca s-a apasat optiunea de meniu "Start"
    public boolean apasatcontrol=false;                         //determina daca s-a apasat optiunea de meniu "Turn off/on control"
    public boolean incarcatscenariu=false;
    public boolean incarcatscenariutest=false;
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public AppWindow() throws IOException, SQLException{        //Constructor
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
        Scenario = new javax.swing.JMenu();
        Scenario1 = new javax.swing.JMenuItem();
        Scenario2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        sm1 = new javax.swing.JMenuItem();
        sm2 = new javax.swing.JMenuItem();
        sm3 = new javax.swing.JMenuItem();
        Start = new javax.swing.JMenu();
        Help = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jMenuBar1.setBackground(new java.awt.Color(0, 0, 0));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));

        Options.setBackground(new java.awt.Color(0, 0, 0));
        Options.setBorder(null);
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

        Scenario.setBackground(new java.awt.Color(0, 0, 0));
        Scenario.setBorder(null);
        Scenario.setForeground(new java.awt.Color(204, 204, 204));
        Scenario.setText("Scenario");

        Scenario1.setBackground(new java.awt.Color(0, 0, 0));
        Scenario1.setForeground(new java.awt.Color(204, 204, 204));
        Scenario1.setText("Load Scenario 1");
        Scenario1.setBorder(null);
        Scenario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSC1(evt);
            }
        });
        Scenario.add(Scenario1);

        Scenario2.setBackground(new java.awt.Color(0, 0, 0));
        Scenario2.setForeground(new java.awt.Color(204, 204, 204));
        Scenario2.setText("Load Scenario 2");
        Scenario2.setBorder(null);
        Scenario2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSC2(evt);
            }
        });
        Scenario.add(Scenario2);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setAlignmentX(0.0F);
        jSeparator1.setAlignmentY(0.0F);
        Scenario.add(jSeparator1);

        sm1.setBackground(new java.awt.Color(0, 0, 0));
        sm1.setForeground(new java.awt.Color(204, 204, 204));
        sm1.setText("Small test Scenario 1");
        sm1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM1(evt);
            }
        });
        Scenario.add(sm1);

        sm2.setBackground(new java.awt.Color(0, 0, 0));
        sm2.setForeground(new java.awt.Color(204, 204, 204));
        sm2.setText("Small test Scenario 2");
        sm2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM2(evt);
            }
        });
        Scenario.add(sm2);

        sm3.setBackground(new java.awt.Color(0, 0, 0));
        sm3.setForeground(new java.awt.Color(204, 204, 204));
        sm3.setText("Small test Scenario 3");
        sm3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IncarcaSM3(evt);
            }
        });
        Scenario.add(sm3);

        jMenuBar1.add(Scenario);

        Start.setBackground(new java.awt.Color(0, 0, 0));
        Start.setBorder(null);
        Start.setForeground(new java.awt.Color(204, 204, 204));
        Start.setText("Start");
        Start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ApasatStart(evt);
            }
        });
        jMenuBar1.add(Start);

        Help.setBackground(new java.awt.Color(0, 0, 0));
        Help.setBorder(null);
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
            .addGap(0, 402, Short.MAX_VALUE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Help;
    private javax.swing.JMenuItem Mute;
    private javax.swing.JMenu Options;
    private javax.swing.JMenu Scenario;
    private javax.swing.JMenuItem Scenario1;
    private javax.swing.JMenuItem Scenario2;
    public javax.swing.JMenu Start;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    public javax.swing.JMenuItem sm1;
    public javax.swing.JMenuItem sm2;
    public javax.swing.JMenuItem sm3;
    private javax.swing.JMenuItem swich;
    // End of variables declaration//GEN-END:variables

}
