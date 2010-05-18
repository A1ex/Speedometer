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
import java.io.IOException;
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

    public boolean incarcatscenariu=false;                      //determina daca s-a afisat panelul de scenariu
    public boolean pornitscenariu=false;                        //determina daca s-a pornit scenariul
    public boolean apasatsc1=false;                             //determina daca s-a apasat optiunea de meniu "Load Scenario 1"
    public boolean apasatmute=false;                            //determina daca s-a apasat optiunea de meniu "Mute/Unmute Engine"
    public boolean apasatstart=false;                           //determina daca s-a apasat optiunea de meniu "Start"
    public boolean apasatcontrol=false;                         //determina daca s-a apasat optiunea de meniu "Turn off/on control"    
    SpeedometerPanel p=new SpeedometerPanel();                  //instanta a clasei SpeedometerPanel
    Scenario1 sc1 = new Scenario1();                            //instanta a clasei Scenario1
    int index;
    double v;
    int delay;
    int engine=0;
    public AppWindow() throws IOException, SQLException{        //Constructor
        initComponents();
        t2.start();                                             //porneste timerul
    }
    ActionListener mainActionListener = new ActionListener() {  //ActionListener pentru optiunile de meniu
         public void actionPerformed(ActionEvent actionEvent) {
             if (apasatmute)
                p.sunet=false;
             else
                p.sunet=true;
             if (apasatcontrol)
                 p.control=false;
             else
                 p.control=true;
             if (apasatsc1&&apasatcontrol&&!incarcatscenariu){              
                    sc1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    sc1.pack();
                    sc1.setVisible(true);
                    incarcatscenariu = true;                 
             }
             if (apasatstart&&!pornitscenariu){
                 t3.start();
                 pornitscenariu=true;
             }
             if (!sc1.isVisible()){
                 incarcatscenariu=false;
                 apasatsc1=false;
             }

         }
    };
    Timer t2=new Timer(10,mainActionListener);                  //seteaza intarzierea la timer

    ActionListener SCENARIOActionListener = new ActionListener() {  
         public void actionPerformed(ActionEvent actionEvent)  {
            try {
                if (sc1.rs.next()){                             //daca mai sunt inregistrari in tabelul scenariului

                    index = sc1.rs.getInt(1);
                    v = sc1.rs.getDouble(2);
                    delay = sc1.rs.getInt(3);
                    engine=sc1.rs.getInt(4);

                    if (engine==1){
                        if (!p.sac.pornit){                      //porneste motorul daca in BD coloana Engine e 1 si motorul e oprit
                            p.sac.pornit=true;
                            p.sac.apasatpornit=true;
                            p.butonStart.setText("Stop Engine");
                            p.Buton1.setIcon(p.butonverde);
                            p.sac.viteza=1;
                        }
                        else{                                   //opreste motorul daca in BD coloana Engine e 1 si motorul e oprit
                            p.sac.apasatoprit=true;
                            p.butonStart.setText("Start Engine");
                            p.sac.pornit=false;
                            p.sac.idle=false;
                            p.Buton1.setIcon(p.butonrosu);
                            p.sac.crescutturatie=false;
                        }
                    }
                    System.out.println("v="+v+" comparviteza="+p.sac.comparviteza+" "+p.sac.cresteviteza);
                    if (p.sac.comparviteza<v)                   //determina prin cresteviteza daca viteza creste
                        p.sac.cresteviteza=true;
                    else
                        p.sac.cresteviteza=false;
                    if (p.sac.comparviteza==v)                  //determina prin idle deca viteza ramane la aceeasi valoare
                       p.sac.vitezaidle=true;
                    p.sac.comparviteza=v;
                    
                    calculCoordonate();                    
                    if (p.sac.cresteviteza){
                        p.sac.calculCoordonateUR();
                        p.sac.setareZoneR();
                    }
                   
                    t3.setDelay(delay);
                }
                else{
                    System.out.println("S-a terminat");
                    t3.stop();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Scenario1.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
     };
     Timer t3=new Timer(10,SCENARIOActionListener);
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void calculCoordonate(){

        if (p.sac.s&&(p.sac.y>90+21-45))                        
            p.sac.y=0.001231321*Math.pow(v,3)-0.113265236*Math.pow(v,2)-1.713665913*v+ 305.225703136;
        if ((p.sac.x<=304)&&(p.sac.zona1==0)&&(p.sac.zona2==0)){    //Daca intra in zona mijloc stanga vitezometru
            if (p.sac.y<185+21-45){
                p.sac.zona2=1;
                p.sac.x=306;
            }
        }
        else                                                //Daca intra in zona mijloc dreapta vitezometru
            if ((p.sac.x>=590)&&(p.sac.zona2==0)&&(p.sac.zona1==0)){
                if (p.sac.y>240+21-45){
                    p.sac.zona1=1;
                    p.sac.x=595;
                }
            }
            else
                if (p.sac.zona1==1)                         //Daca e in zona jos vitezometru
                    p.sac.x=(-1)*Math.sqrt(Math.abs(22050-Math.pow((p.sac.y-p.sac.y0),2)))+p.sac.x0;
                else
                    if (p.sac.zona2==1)                     //Daca e in zona sus vitezometru
                        p.sac.x=(-1)*Math.sqrt(Math.abs(22050-Math.pow((p.sac.y-p.sac.y0),2)))+p.sac.x0;
     }
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
            p.sac.alarm.StopEngineNoise();
            Mute.setText("Unmute Engine");
        }
        else{
            apasatmute=false;
            if (p.sac.pornit)                   //porneste iar motorul
                p.sac.alarm.EngineNoise();
            Mute.setText("Mute Engine");
        }
    }//GEN-LAST:event_SetareMute

    private void ApasatStart(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ApasatStart

        if (!apasatstart){
            apasatstart=true;
            Start.setText("Stop");
        }
        else{
            apasatstart=false;
            Start.setText("Start");
        }
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

    private void IncarcaSC1(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncarcaSC1
        // TODO add your handling code here:
        apasatsc1=true;
    }//GEN-LAST:event_IncarcaSC1

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppWindow s = new AppWindow();                    
                    s.getContentPane().add(s.p,BorderLayout.CENTER);
                    s.p.setFocusable(true);
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
