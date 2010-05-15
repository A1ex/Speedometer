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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Alexandru Popescu
 */
public class AppWindow extends javax.swing.JFrame  {

    public boolean apasatmute=false;                            //determina daca s-a apasat optiunea de meniu "Mute/Unmute Engine"
    public boolean apasatstart=false;                           //determina daca s-a apasat optiunea de meniu "Start"
    public boolean apasatcontrol=false;                         //determina daca s-a apasat optiunea de meniu "Turn off/on control"
    SpeedometerPanel p=new SpeedometerPanel();                  //instanta a clasei SpeedometerPanel

    public AppWindow() throws IOException, SQLException{        //Constructor
        initComponents();
        t2.start();                                             //porneste timerul
//        DBConnection();                                       //face conexiunea la baza de date
    }
    public void DBConnection ()throws IOException, SQLException{//metoda ce face conexiunea la baza de date
         try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/OBCDB");
//        Statement sql= c.createStatement();
//        ResultSet rs=sql.executeQuery("SELECT * FROM Scenario1");
//        while (rs.next()){
//            int index = rs.getInt(1);
//            double v3=rs.getDouble(2);
//            int delay=rs.getInt(3);
//            System.out.println(index+" "+v3+" "+delay);
//        }        
    }
    ActionListener mainActionListener = new ActionListener() {
         public void actionPerformed(ActionEvent actionEvent) {
             if (apasatmute)
                p.sunet=false;             
             else
                 p.sunet=true;
             if (apasatcontrol)
                 p.control=false;
             else
                 p.control=true;
         }
    };
    Timer t2=new Timer(10,mainActionListener);                  //seteaza intarzierea la timer
    
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
        Scenario3 = new javax.swing.JMenuItem();
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
        Scenario.add(Scenario1);

        Scenario2.setBackground(new java.awt.Color(0, 0, 0));
        Scenario2.setForeground(new java.awt.Color(204, 204, 204));
        Scenario2.setText("Load Scenario 2");
        Scenario2.setBorder(null);
        Scenario.add(Scenario2);

        Scenario3.setBackground(new java.awt.Color(0, 0, 0));
        Scenario3.setForeground(new java.awt.Color(204, 204, 204));
        Scenario3.setText("Load Scenario 3");
        Scenario3.setBorder(null);
        Scenario.add(Scenario3);

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
            .addGap(0, 401, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SetareMute(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SetareMute
        // TODO add your handling code here:
        if (apasatmute==false){
            apasatmute=true;
            p.alarm.StopEngineNoise();
            Mute.setText("Unmute Engine");
        }
        else{
            apasatmute=false;
            if (p.pornit)                   //porneste iar motorul
                p.alarm.EngineNoise();
            Mute.setText("Mute Engine");
        }
    }//GEN-LAST:event_SetareMute

    private void ApasatStart(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ApasatStart
        // TODO add your handling code here:
        apasatstart=true;
    }//GEN-LAST:event_ApasatStart

    private void SetareControl(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SetareControl
        // TODO add your handling code here:
         if (apasatcontrol==false){
            apasatcontrol=true;
            swich.setText("Turn on manual control");
         }
        else{
            apasatcontrol=false;
            swich.setText("Turn off manual control");
        }
    }//GEN-LAST:event_SetareControl

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppWindow s = new AppWindow();
//                    SpeedometerPanel p=new SpeedometerPanel();
                    s.p.setFocusable(true);
                    s.getContentPane().add(s.p,BorderLayout.CENTER);
                    s.setLayout(new GridLayout());
                    s.pack();
                    s.setSize(617, 450);
                    s.setVisible(true);
                    s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (IOException ex) {
                    Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Help;
    private javax.swing.JMenuItem Mute;
    private javax.swing.JMenu Options;
    private javax.swing.JMenu Scenario;
    private javax.swing.JMenuItem Scenario1;
    private javax.swing.JMenuItem Scenario2;
    private javax.swing.JMenuItem Scenario3;
    private javax.swing.JMenu Start;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem swich;
    // End of variables declaration//GEN-END:variables

}
