/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * speedometerPanel.java
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
public class speedometerPanel extends javax.swing.JPanel  implements KeyListener {

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
    setAndCalculate sac=new setAndCalculate();                  //instanta a clasei setAndCalculate

    public speedometerPanel()throws IOException, SQLException  {//Constructor
        initComponents();
        addKeyListener(this);                                   //adauga keylistener la panou
        t.start();                                              //porneste timerul
        try {                                                   //copiaza in m imaginea de background
            m =ImageIO.read(new File("src/images/Speedometer.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(speedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                sac.cresteTuratieLaPornire();                   //duce acul turometrului la 1000 la pornire
            sac.setarePasi();                                   //seteaza pasii de accelerare/decelerare in functie de viteza
            sac.setareZoneV();
            if (control){
                sac.calculViteza();                             //calculeaza viteza
                t4.stop();
            }
            else
                t4.start();
            if (sac.turatieidle&&control){                      //face acul turometrului sa tremure la idle atunci cand userul are control
                sac.pas3=1;
                sac.calculCoordonateUR();
                sac.setareZoneR();
            }
            sac.calculTuratie();                                //calculeaza turatia
            if (sunet)                                          //daca nu e selectat mute sa se aplice metoda de sunet
                sac.sunet();
            if (sac.apasatpornit)                               //daca s-a apasat butonul de start si era pe mute, sa nu repete zgomotul de pornire motor la unmute
                sac.apasatpornit=false;
            sac.actualizareTuratie();                           //actualizeaza turatia in functie de praguri (pt schimbarea de viteze)
            if (sac.control)
                sac.decelerareV();                              //metoda ce simuleaza decelerarea pentru acul vitezometrului
            else
                sac.decelerareF();                              //metoda ce semnaleaza decelerarea pentru acul indicatorului de combustibil
            if ((sac.crescutturatie)||(sac.pornit==false)){
                sac.decelerareR();                              //metoda ce simuleaza decelerarea pentru acul turometrului
            }
            jLabel2.setText(Integer.toString((int)sac.v));
            double v2=sac.v*1.6;
            jLabel3.setText(Integer.toString((int)v2));
            sac.setareTreapta();
            jLabel7.setText(Integer.toString(sac.viteza));
            jLabel8.setText(Integer.toString((int)sac.turatie));
            if (sac.crescutturatie){                            //daca motorul a fost pornit si turatia a ajuns
                try {Thread.sleep(100);}                        //la 1000, se introduce o intarziere de 100ms
                catch (InterruptedException ex) {
                    Logger.getLogger(speedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            repaint();
        }
    };
    Timer t=new Timer(10,actionListener);

    ActionListener RevmeteractionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (sac.cresteviteza){
                sac.calculCoordonateUR();                
                sac.setareZoneR();
            }
            else
                if (sac.turatieidle){
                    sac.pas3=1;
                    sac.calculCoordonateUR();
                    sac.setareZoneR();
                }
        }
    };
       Timer t4=new Timer(10,RevmeteractionListener);

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
//        System.out.println(sac.y);
        g.drawImage(buffer, 0, 0, this);                        //Deseneaza imaginea buffer
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
                    if (sac.frana==false&&sunet){               //Zgomot de franare
                        sac.alarm.brake();
                        sac.frana=true;
                    }
                    sac.calculCoordonateDV();
                    sac.setareZoneV();
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

        setPreferredSize(new java.awt.Dimension(616, 451));

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
                AlarmaBaterie(evt);
            }
        });
        baterie.setBounds(200, 340, 40, 39);
        jLayeredPane1.add(baterie, javax.swing.JLayeredPane.DEFAULT_LAYER);

        pompa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pump_gray.jpg"))); // NOI18N
        pompa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AlarmaPompa(evt);
            }
        });
        pompa.setBounds(250, 340, 40, 40);
        jLayeredPane1.add(pompa, javax.swing.JLayeredPane.DEFAULT_LAYER);

        far.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/light_gray.jpg"))); // NOI18N
        far.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AlarmaFar(evt);
            }
        });
        far.setBounds(300, 340, 40, 39);
        jLayeredPane1.add(far, javax.swing.JLayeredPane.DEFAULT_LAYER);

        usi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/doors_gray.jpg"))); // NOI18N
        usi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AlarmaUsi(evt);
            }
        });
        usi.setBounds(350, 340, 40, 39);
        jLayeredPane1.add(usi, javax.swing.JLayeredPane.DEFAULT_LAYER);

        centura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/seatbelt_gray.jpg"))); // NOI18N
        centura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AlarmaCentura(evt);
            }
        });
        centura.setBounds(400, 340, 40, 40);
        jLayeredPane1.add(centura, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void Buton1ApasareStartStop(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Buton1ApasareStartStop
        if (control){
            if (sac.pornit==false){                             //Daca se porneste motorul
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
    private void AlarmaBaterie(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlarmaBaterie
        // TODO add your handling code here:
        if (control){
            if (sac.alarmaBaterie){
                baterie.setIcon(bateriegri);
                sac.alarmaBaterie=false;
                sac.alarm.stopBatteryAlarm();
            } else{
                baterie.setIcon(baterierosie);
                sac.alarmaBaterie=true;
                sac.alarm.startBatteryAlarm();
            }
        }
}//GEN-LAST:event_AlarmaBaterie

    private void AlarmaPompa(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlarmaPompa
        // TODO add your handling code here:
        if (control){
            if (sac.alarmaPompa){
                pompa.setIcon(pompagri);
                sac.alarmaPompa=false;
                sac.alarm.stopPumpAlarm();
            } else{
                pompa.setIcon(pomparosie);
                sac.alarmaPompa=true;
                sac.alarm.startPumpAlarm();
            }
        }
}//GEN-LAST:event_AlarmaPompa

    private void AlarmaFar(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlarmaFar
        // TODO add your handling code here:
        if (control){
            if (sac.alarmaFar){
                far.setIcon(fargri);
                sac.alarmaFar=false;
                sac.alarm.stopLightsAlarm();
            } else{
                far.setIcon(farrosie);
                sac.alarmaFar=true;
                sac.alarm.startLightsAlarm();
            }
        }
}//GEN-LAST:event_AlarmaFar

    private void AlarmaUsi(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlarmaUsi
        // TODO add your handling code here:
        if (control){
            if (sac.alarmaUsi){
                usi.setIcon(usigri);
                sac.alarmaUsi=false;
                sac.alarm.stopDoorsAlarm();
            } else{
                usi.setIcon(usirosie);
                sac.alarmaUsi=true;
                sac.alarm.startDoorsAlarm();
            }
        }
}//GEN-LAST:event_AlarmaUsi

    private void AlarmaCentura(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlarmaCentura
        // TODO add your handling code here:
        if (control){
            if (sac.alarmaCentura){
                centura.setIcon(centuragri);
                sac.alarmaCentura=false;
                sac.alarm.stopSeatbeltAlarm();
            } else{
                centura.setIcon(centurarosie);
                sac.alarmaCentura=true;
                sac.alarm.startSeatbeltAlarm();
            }
        }
}//GEN-LAST:event_AlarmaCentura

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel Buton1;
    public javax.swing.JLabel baterie;
    public javax.swing.JLabel butonStart;
    public javax.swing.JLabel centura;
    public javax.swing.JLabel far;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    public javax.swing.JLabel pompa;
    public javax.swing.JLabel usi;
    // End of variables declaration//GEN-END:variables

}
