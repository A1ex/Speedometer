/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package speedometer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.Timer;

/**
 *
 * @author Alexandru Popescu
 */
public class TopClass {

    SpeedometerPanel p=new SpeedometerPanel();                  //instanta a clasei SpeedometerPanel
    Scenario1 sc1 = new Scenario1();                            //instanta a clasei Scenario1
    AppWindow aw = new AppWindow();
    ResultSet rs;
    public int index,delay,lights,decelerare,engine,doors,fuel;
    public int i=0;
    double v;
    public boolean incarcatscenariu=false;                      //determina daca aw-a afisat panelul de scenariu
    public boolean pornitscenariu=false;                        //determina daca aw-a pornit scenariul                         
    public boolean alarmalumini=false;
    public boolean alarmausi=false;
    public boolean alarmapompa=false;


//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public TopClass() throws IOException, SQLException{
        DBConnection();
        t3.start();
        t5.start();
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void DBConnection ()throws IOException, SQLException{//metoda ce face conexiunea la baza de date
         try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/OBCDB");
        Statement sql= c.createStatement();
        rs=sql.executeQuery("SELECT * FROM Scenario1");
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    ActionListener scenarioActionListener = new ActionListener() {
         public void actionPerformed(ActionEvent actionEvent)  {
            try {                
                if(aw.apasatstart){
                    if (rs.next()){                             //daca mai sunt inregistrari in tabelul scenariului
                        index =rs.getInt(1);
                        v = rs.getDouble(2);
                        p.sac.v=v;
                        delay = rs.getInt(3);
                        engine=rs.getInt(9);
                        lights=rs.getInt(7);
                        decelerare=rs.getInt(10);
                        doors=rs.getInt(6);
                        fuel=rs.getInt(5);
                        actualizariScenariu();
                        calculCoordonateScenariu();
                        actualizarePanouScenariu1();                        
                        t3.setDelay(delay);
                    }
                    else{
                        System.out.println("S-a terminat");
                        t3.stop();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Scenario1.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
     };
    Timer t3=new Timer(10,scenarioActionListener);
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     ActionListener menuActionListener = new ActionListener() {
         public void actionPerformed(ActionEvent actionEvent)  {
             actualizariMeniu();
         }
     };
    Timer t5=new Timer(10,menuActionListener);
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void actualizariMeniu(){
         if (aw.apasatmute){                                    //daca se apasa Mute in AppWindow
            p.sac.alarm.StopEngineSound();
            p.sunet=false;
            p.sac.sunetmotor=false;
        }
       if (aw.apasatunmute){                                    //daca se apasa Unmute in Appwindow            
            p.sunet=true;
         }
        if (aw.apasatcontrol){
            p.control=false;                               //nu se mai poate controla manual
            p.sac.control=false;                           //variabila ce determina in SetAndCalculate modificari la valorile pasilor la cresterea turatiei
        }
        else{
            p.control=true;                                //se reda controlul manual
            p.sac.control=true;                            //variabila ce determina in SetAndCalculate modificari la valorile pasilor la cresterea turatiei
            p.sac.pas4=3;
        }
        if (aw.apasatsc1){            
            sc1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            sc1.pack();
            sc1.setVisible(true);
            aw.apasatsc1=false;
            aw.incarcatscenariu=true;
        }
     }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void actualizariScenariu(){        
        if (engine==1){
            if (!p.sac.pornit){                      //porneste motorul daca in BD coloana Engine e 1 si motorul e oprit
                p.sac.pornit=true;
                p.sac.apasatpornit=true;
                p.butonStart.setText("Stop Engine");
                p.Buton1.setIcon(p.butonverde);
                p.sac.viteza=1;
            }
            else{                                   //opreste motorul daca in BD coloana Engine e 1 si motorul e pornit
                p.sac.apasatoprit=true;
                p.butonStart.setText("Start Engine");
                p.sac.pornit=false;
                p.sac.pas4=3;
                p.sac.idle=false;
                p.Buton1.setIcon(p.butonrosu);
                p.sac.crescutturatie=false;
            }
        }
        if (lights==1)                              //verifica senzorul de lumini aprinse
            if (!alarmalumini){
                alarmalumini=true;
                p.sac.alarm.StartLightsAlarm();
                p.far.setIcon(p.farrosie);
            }
            else{
                alarmalumini=false;
                p.sac.alarm.StopLightsAlarm();
                p.far.setIcon(p.fargri);
            }
        if (doors==1)                              //verifica senzorul de usi deschise
            if (!alarmausi){
                alarmausi=true;
                p.sac.alarm.StartDoorsAlarm();
                p.usi.setIcon(p.usirosie);
            }
            else{
                alarmausi=false;
                p.sac.alarm.StopDoorsAlarm();
                p.usi.setIcon(p.usigri);
            }
         if (fuel==1)                              //verifica senzorul de nivel de combustibil
            if (!alarmapompa){
                alarmapompa=true;
                p.sac.alarm.StartPumpAlarm();
                p.pompa.setIcon(p.pomparosie);
            }
            else{
                alarmapompa=false;
                p.sac.alarm.StopPumpAlarm();
                p.pompa.setIcon(p.pompagri);
            }
        if (p.sac.comparviteza<v)                   //determina prin cresteviteza daca viteza creste
            p.sac.cresteviteza=true;
        else
            p.sac.cresteviteza=false;

        if (p.sac.comparviteza==v)                      //determina prin vitezaidle deca viteza ramane la aceeasi valoare
           p.sac.vitezaidle=true;
        else
            p.sac.vitezaidle=false;        
        p.sac.comparviteza=v;

     }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void calculCoordonateScenariu(){
        if (p.sac.y<90+21-45){
            p.sac.x=-0.001010101*Math.pow(v,3)+0.227792208*Math.pow(v,2)-11.906349206*v+491.742424242;
            p.sac.y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((p.sac.x-p.sac.x0),2)))+p.sac.y0;
        }
        else{
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
     }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void actualizarePanouScenariu1(){
        if (i==0)
            if (p.sac.pornit){
                sc1.jLabel10.setForeground(Color.green);
                i=i+1;
            }
        if (i==1)
            if (alarmausi){
                sc1.jLabel10.setForeground(Color.yellow);
                sc1.jLabel1.setForeground(Color.green);
                sc1.jLabel9.setForeground(Color.green);
                i=i+1;
            }
        if (i==2)
            if (!alarmausi){
                sc1.jLabel1.setForeground(Color.yellow);
                sc1.jLabel9.setForeground(Color.yellow);
                sc1.jLabel6.setForeground(Color.green);
                i=i+1;
            }
        if (i==3)
            if (delay>100){
                sc1.jLabel6.setForeground(Color.yellow);
                sc1.jLabel4.setForeground(Color.green);
                i=i+1;
            }
        if (i==4)
            if (v>50){
                sc1.jLabel4.setForeground(Color.yellow);
                sc1.jLabel5.setForeground(Color.green);
                i=i+1;
            }
        if (i==5)
            if (delay>100){
                sc1.jLabel5.setForeground(Color.yellow);
                sc1.jLabel7.setForeground(Color.green);
                i=i+1;
            }
        if (i==6)
            if (v<70){
                sc1.jLabel7.setForeground(Color.yellow);
                sc1.jLabel22.setForeground(Color.green);
                i=i+1;
            }
        if (i==7)
            if (alarmapompa){
                sc1.jLabel22.setForeground(Color.yellow);
                sc1.jLabel3.setForeground(Color.green);
                sc1.jLabel21.setForeground(Color.green);
                i=i+1;
            }
        if (i==8)
            if(!alarmapompa){
                sc1.jLabel3.setForeground(Color.yellow);
                sc1.jLabel21.setForeground(Color.yellow);
                sc1.jLabel8.setForeground(Color.green);
                i=i+1;
            }
        if (i==9)
            if (v==0){
                sc1.jLabel8.setForeground(Color.yellow);
                sc1.jLabel12.setForeground(Color.green);
                i=i+1;
            }
        if (i==10)
            if (alarmalumini){
                sc1.jLabel12.setForeground(Color.yellow);
                sc1.jLabel11.setForeground(Color.green);
                sc1.jLabel23.setForeground(Color.green);
                i=i+1;
            }
        if (i==11)
            if (!alarmalumini){
                sc1.jLabel11.setForeground(Color.yellow);
                sc1.jLabel23.setForeground(Color.yellow);
            }
    }

//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public static void main(String args[]) {
        try {                   
            TopClass tc=new TopClass();
            tc.aw.getContentPane().add(tc.p, BorderLayout.CENTER);
            tc.p.setFocusable(true);
            tc.aw.setLayout(new GridLayout());
            tc.aw.pack();
            tc.aw.setSize(617, 450);
            tc.aw.setVisible(true);
            tc.aw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (IOException ex) {
            Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
}
