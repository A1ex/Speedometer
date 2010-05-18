/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SpeedometerPanel.java
 *
 * Created on May 13, 2010, 11:33:12 PM
 */
package speedometer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
/**
 *
 * @author Alexandru Popescu
 */
public class SpeedometerPanel extends javax.swing.JPanel  implements KeyListener {

    Icon butonverde;                                            //imagine buton
    Icon butonrosu;                                             //imagine buton
    Icon baterierosie;                                          //imagine atentionare
    Icon bateriegri;                                            //imagine atentionare
    Icon pompagri;                                              //imagine atentionare
    Icon pomparosie;                                            //imagine atentionare
    Icon fargri;                                                //imagine atentionare
    Icon farrosie;                                              //imagine atentionare
    Icon usigri;                                                //imagine atentionare
    Icon usirosie;                                              //imagine atentionare
    Icon centuragri;                                            //imagine atentionare
    Icon centurarosie;                                          //imagine atentionare
    BufferedImage buffer;                                       //imagine folosita la double buffering
    Graphics2D gbuffer;                                         //obiect grafic folosit pt desenare in buffer
    public boolean firstTime=true;                              //determina daca s-a initializat sau nu imaginea si obiectul grafic pentru db
    public boolean control=true;                                //determina daca se tine cont de apasarea tastelor
    public boolean sunet=true;                                  //determina daca se aude sunetul motorului
    Rectangle area;                                             //folosit la crearea imaginii folosita la buffering
    Image m;                                                    //in ea se retine imaginea de background
    SetAndCalculate sac=new SetAndCalculate();                  //instanta a clasei SetAndCalculate

    public SpeedometerPanel()throws IOException, SQLException  {//Constructor
        initComponents();
        addKeyListener(this);                                   //adauga keylistener la panou
        t.start();                                              //porneste timerul
        try {                                                   //copiaza in m imaginea de background
            m =ImageIO.read(new File("src/images/Speedometer.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        butonverde = new ImageIcon("src/images/buttongreen.jpg");
        butonrosu = new ImageIcon("src/images/buttonred.jpg");
        baterierosie=new ImageIcon("src/images/battery_red.jpg");
        bateriegri=new ImageIcon("src/images/battery_gray.jpg");
        pompagri=new ImageIcon("src/images/pump_gray.jpg");
        pomparosie=new ImageIcon("src/images/pump_red.jpg");
        fargri=new ImageIcon("src/images/light_gray.jpg");
        farrosie=new ImageIcon("src/images/light_red.jpg");
        usigri=new ImageIcon("src/images/doors_gray.jpg");
        usirosie=new ImageIcon("src/images/doors_red.jpg");
        centuragri=new ImageIcon("src/images/seatbelt_gray.jpg");
        centurarosie=new ImageIcon("src/images/seatbelt_red.jpg");
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    ActionListener actionListener = new ActionListener() {
        
        public void actionPerformed(ActionEvent actionEvent) {
            if ((sac.crescutturatie==false)&&(sac.pornit==true)&&(sac.turatie<1000))
                sac.cresteTuratieLaPornire();                       //duce acul turometrului la 1000 la pornire
            sac.setarePasi();                                       //seteaza pasii de accelerare/decelerare in functie de viteza
            if (control){               
                sac.calculViteza();                                 //calculeaza viteza
            }
            sac.calculTuratie();                                    //calculeaza turatia
            if (sunet)                                          //daca nu e selectat mute sa se aplice metoda de sunet
                sac.sunet();
            if (sac.apasatpornit)                                   //daca s-a apasat butonul de start si era pe mute, sa nu repete zgomotul de pornire motor la unmute
                sac.apasatpornit=false;
            sac.actualizareTuratie();                               //actualizeaza turatia in functie de praguri (pt schimbarea de viteze)
            if (!sac.vitezaidle)
                sac.decelerareV();                                      //metoda ce simuleaza decelerarea pentru acul vitezometrului
            sac.decelerareF();
            if (((sac.crescutturatie)&&!sac.vitezaidle)||((sac.pornit==false)&&!sac.vitezaidle))
                sac.decelerareR();                                  //metoda ce simuleaza decelerarea pentru acul turometrului
            jLabel2.setText(Integer.toString((int)sac.v));
            double v2=sac.v*1.6;
            jLabel3.setText(Integer.toString((int)v2));
            sac.setareTreapta();
            jLabel7.setText(Integer.toString(sac.viteza));
            jLabel8.setText(Integer.toString((int)sac.turatie));
            if (sac.crescutturatie){                                //daca motorul a fost pornit si turatia a ajuns
                try {Thread.sleep(100);}                            //la 1000, se introduce o intarziere de 100ms
                catch (InterruptedException ex) {
                    Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            repaint();
        }
    };
    Timer t=new Timer(10,actionListener);

    @Override
    public void paintComponent(Graphics g){
        if (firstTime) {                                        //Initializare imagine buffer
          Dimension dim = getSize();
          int w = dim.width;
          int h = dim.height;
          area = new Rectangle(dim);
          buffer = (BufferedImage) createImage(w, h);
          gbuffer = buffer.createGraphics();
          gbuffer.setColor(Color.red);
          gbuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
          firstTime = false;
        }
        Graphics2D g2=(Graphics2D) gbuffer;
        g2.clearRect(0, 0, area.width, area.height);
        g2.drawImage(m, 0,0, this);                             //Deseneaza imaginea de fundal in buffer
        g2.setColor(Color.orange);                              //Setari pt obiectul grafic
	g2.setStroke(new BasicStroke(4));
	g2.drawLine((int)sac.x0, (int)sac.y0, (int)sac.x, (int)sac.y);          //Desenare ac vitezometru in buffer
        g2.drawLine((int)sac.x0-1, (int)sac.y0-1, (int)sac.x, (int)sac.y);
        g2.drawLine((int)sac.x0+1, (int)sac.y0+1, (int)sac.x, (int)sac.y);
        g2.drawLine((int)sac.x0+1, (int)sac.y0-1, (int)sac.x, (int)sac.y);
        g2.drawLine((int)sac.x0-1, (int)sac.y0+1, (int)sac.x, (int)sac.y);
        g2.drawLine((int)sac.x0r, (int)sac.y0r, (int)sac.xr, (int)sac.yr);      //Desenare ac turometru in buffer
        g2.drawLine((int)sac.x0r-1, (int)sac.y0r-1, (int)sac.xr, (int)sac.yr);
        g2.drawLine((int)sac.x0r+1, (int)sac.y0r+1, (int)sac.xr, (int)sac.yr);
        g2.drawLine((int)sac.x0r+1, (int)sac.y0r-1, (int)sac.xr, (int)sac.yr);
        g2.drawLine((int)sac.x0r-1, (int)sac.y0r+1, (int)sac.xr, (int)sac.yr);
        g2.setColor(Color.black);                               //Desenare cercuri negre
        g2.fillOval(435, 177, 30, 30);
        g2.fillOval(155, 177, 30, 30);
        
        g2.setColor(Color.orange);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine((int)sac.x0f, (int)sac.y0f, (int)sac.xf, (int)sac.yf);
        g2.drawLine((int)sac.x0f-1, (int)sac.y0f-1, (int)sac.xf, (int)sac.yf);
        g2.drawLine((int)sac.x0f+1, (int)sac.y0f+1, (int)sac.xf, (int)sac.yf);
        g2.drawLine((int)sac.x0f+1, (int)sac.y0f-1, (int)sac.xf, (int)sac.yf);
        g2.drawLine((int)sac.x0f-1, (int)sac.y0f+1, (int)sac.xf, (int)sac.yf);
        
        g.drawImage(buffer, 0, 0, this);                        //Deseneaza imaginea buffer
//        System.out.print("  s=");System.out.print(sac.s);
//        System.out.print("  x=");System.out.print(sac.x);         //Afisare coordonate varf ace
//        System.out.print("  y=");System.out.println((int)sac.y);
//        System.out.print(" turatie=");System.out.print(sac.turatie);
//        System.out.print(" viteza=");System.out.print((int)sac.v);
//        System.out.print("  xr=");System.out.print(sac.xr);
//        System.out.print("  yr=");System.out.println((int)sac.yr);
//        System.out.print("  z1=");System.out.print(sac.zona1);    //Afisare zone
//        System.out.print("  z2=");System.out.println(sac.zona2);
//        System.out.print("  z3=");System.out.print(sac.zona3);
//        System.out.print("  z4=");System.out.println(sac.zona4);
    }
//-----------------------------------------------------------------------------------
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void keyPressed(KeyEvent e) {                       //Daca se apasa o tasta
         int tasta;
         tasta=e.getKeyCode();         
         if (control){
             if (tasta==KeyEvent.VK_DOWN){                      //Daca se apasa sageata jos
                if (sac.v>0){
                    if (sac.frana==false&&sunet){                        //Zgomot de franare
                        sac.alarm.Brake();
                        sac.frana=true;
                    }
                    sac.calculCoordonateDV();
                    sac.setareZoneV();
                }
                if (sac.turatie>1000){
                    sac.calculCoordonateDR();
                    sac.setareZoneR();
                }
            }
            if (tasta==KeyEvent.VK_UP){                         //Daca se apasa sageata sus
                sac.idle=false;
                if (sac.pornit&&(sac.crescutturatie)){
                        if (sac.v<140){
                        sac.calculCoordonateUV();
                        sac.setareZoneV();
                    }
                    sac.calculCoordonateUR();
                    sac.setareZoneR();
                }
            }
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void keyReleased(KeyEvent e) {
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        butonStart = new javax.swing.JLabel();
        Buton1 = new javax.swing.JLabel();
        baterie = new javax.swing.JLabel();
        pompa = new javax.swing.JLabel();
        far = new javax.swing.JLabel();
        usi = new javax.swing.JLabel();
        centura = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(616, 390));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel2.setForeground(new java.awt.Color(255, 204, 0));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setBounds(400, 270, 60, 30);
        jLayeredPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel4.setText("M/h");
        jLabel4.setBounds(450, 270, 60, 30);
        jLayeredPane1.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel3.setForeground(new java.awt.Color(255, 204, 0));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setBounds(400, 300, 60, 30);
        jLayeredPane1.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel5.setText("Km/h");
        jLabel5.setBounds(450, 300, 60, 30);
        jLayeredPane1.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel6.setText("Gear");
        jLabel6.setBounds(180, 300, 60, 30);
        jLayeredPane1.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel7.setForeground(new java.awt.Color(255, 204, 0));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel7.setBounds(120, 300, 60, 30);
        jLayeredPane1.add(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel8.setForeground(new java.awt.Color(255, 204, 0));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel8.setBounds(120, 270, 60, 30);
        jLayeredPane1.add(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel9.setText("Revs");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel9.setBounds(180, 270, 60, 30);
        jLayeredPane1.add(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        butonStart.setFont(new java.awt.Font("Tahoma", 1, 14));
        butonStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        butonStart.setText("Start Engine");
        butonStart.setBounds(40, 350, 110, 30);
        jLayeredPane1.add(butonStart, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Buton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/buttonred.jpg"))); // NOI18N
        Buton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Buton1ApasareStartStop(evt);
            }
        });
        Buton1.setBounds(10, 350, 30, 30);
        jLayeredPane1.add(Buton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        baterie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/battery_gray.jpg"))); // NOI18N
        baterie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                baterieAlarmaBaterie(evt);
            }
        });
        baterie.setBounds(200, 340, 40, 39);
        jLayeredPane1.add(baterie, javax.swing.JLayeredPane.DEFAULT_LAYER);

        pompa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pump_gray.jpg"))); // NOI18N
        pompa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pompaAlarmaPompa(evt);
            }
        });
        pompa.setBounds(250, 340, 40, 40);
        jLayeredPane1.add(pompa, javax.swing.JLayeredPane.DEFAULT_LAYER);

        far.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_gray.jpg"))); // NOI18N
        far.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                farAlarmaUlei(evt);
            }
        });
        far.setBounds(300, 340, 40, 39);
        jLayeredPane1.add(far, javax.swing.JLayeredPane.DEFAULT_LAYER);

        usi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/doors_gray.jpg"))); // NOI18N
        usi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usiAlarmaUsi(evt);
            }
        });
        usi.setBounds(350, 340, 40, 39);
        jLayeredPane1.add(usi, javax.swing.JLayeredPane.DEFAULT_LAYER);

        centura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/seatbelt_gray.jpg"))); // NOI18N
        centura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                centuraAlarmaCentura(evt);
            }
        });
        centura.setBounds(400, 340, 40, 40);
        jLayeredPane1.add(centura, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void Buton1ApasareStartStop(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Buton1ApasareStartStop
        if (control){
            if (sac.pornit==false){                                 //Daca se porneste motorul
                sac.apasatpornit=true;
                butonStart.setText("Stop Engine");
                sac.pornit=true;
                Buton1.setIcon(butonverde);
                sac.viteza=1;
            } else{                                             //Daca se opreste motorul
                sac.apasatoprit=true;
                butonStart.setText("Start Engine");
                sac.pornit=false;
                sac.idle=false;
                Buton1.setIcon(butonrosu);
                sac.crescutturatie=false;
            }
        }
}//GEN-LAST:event_Buton1ApasareStartStop
                                                                //Metode pt activarea atentionarilor
    private void baterieAlarmaBaterie(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_baterieAlarmaBaterie
        // TODO add your handling code here:
        if (sac.alarmaBaterie){
            baterie.setIcon(bateriegri);
            sac.alarmaBaterie=false;
        } else{
            baterie.setIcon(baterierosie);
            sac.alarmaBaterie=true;
            sac.alarm.BatteryAlarm();
        }
}//GEN-LAST:event_baterieAlarmaBaterie

    private void pompaAlarmaPompa(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pompaAlarmaPompa
        // TODO add your handling code here:
        if (sac.alarmaPompa){
            pompa.setIcon(pompagri);
            sac.alarmaPompa=false;
        } else{
            pompa.setIcon(pomparosie);
            sac.alarmaPompa=true;
            sac.alarm.PumpAlarm();
        }
}//GEN-LAST:event_pompaAlarmaPompa

    private void farAlarmaUlei(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_farAlarmaUlei
        // TODO add your handling code here:
        if (sac.alarmaUlei){
            far.setIcon(fargri);
            sac.alarmaUlei=false;
        } else{
            far.setIcon(farrosie);
            sac.alarmaUlei=true;
            sac.alarm.OilAlarm();
        }
}//GEN-LAST:event_farAlarmaUlei

    private void usiAlarmaUsi(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usiAlarmaUsi
        // TODO add your handling code here:
        if (sac.alarmaUsi){
            usi.setIcon(usigri);
            sac.alarmaUsi=false;
        } else{
            usi.setIcon(usirosie);
            sac.alarmaUsi=true;
            sac.alarm.DoorsAlarm();
        }
}//GEN-LAST:event_usiAlarmaUsi

    private void centuraAlarmaCentura(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_centuraAlarmaCentura
        // TODO add your handling code here:
        if (sac.alarmaCentura){
            centura.setIcon(centuragri);
            sac.alarmaCentura=false;
        } else{
            centura.setIcon(centurarosie);
            sac.alarmaCentura=true;
            sac.alarm.SeatbeltAlarm();
        }
}//GEN-LAST:event_centuraAlarmaCentura

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel Buton1;
    private javax.swing.JLabel baterie;
    public javax.swing.JLabel butonStart;
    private javax.swing.JLabel centura;
    private javax.swing.JLabel far;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel pompa;
    private javax.swing.JLabel usi;
    // End of variables declaration//GEN-END:variables

}
