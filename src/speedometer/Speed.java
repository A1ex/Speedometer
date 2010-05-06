/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Speed.java
 *
 * Created on Mar 13, 2010, 9:33:07 AM
 */

package speedometer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandru Popescu
 */
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
public class Speed extends javax.swing.JFrame implements KeyListener {

    public double x0=450;
    public double x0r=170;
    public double y0=215;
    public double y0r=215;
    public double x=354;
//    public double xr=64;      //Sunt comentate valorile xr si yr pt turatie la 0(motor oprit)
    public double y=328;
//    public double yr=318;
    public double xr=34;
    public double yr=274;
    public double v=0;
    public double turatie=0;
    public boolean s=true,d=false;
    public double pas1=1;                                   //pas1/pas2 pt accelerare/decelerare vitezometru
    public double pas2=1;                                   //pas3/pas4 pt accelerare/decelerare turometru
    public double pas3=1;
    public double pas4=3;
    public int prag1=0;                                     //Praguri pentru actualizarea turatiei
    public int prag2=0;
    public int prag3=0;
    public int prag4=0;
    public int prag5=0;
    public double i=1;
    public int viteza=1;
    public int zona1=1;                                     //zona1 si zona2 pt vitezometru (jos/sus)
    public int zona2=0;
    public int zona3=1;                                     //zona3 si zona4 pt turometru (jos/sus)
    public int zona4=0;
//    Graphics bufferGraphics;
//    Image offscreen;
//    Dimension dim;
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public Speed() {
        initComponents();
//        dim = getSize();
//        offscreen = createImage(dim.width,dim.height);
//        bufferGraphics =offscreen.getGraphics();
        addKeyListener(this);
        t.start();    
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            calculViteza();
            calculTuratie();
            actualizareTuratie();
            decelerareV();
            decelerareR();
            jLabel2.setText(Integer.toString((int)v));
            double v2=v*1.6;
            jLabel3.setText(Integer.toString((int)v2));
            setareTreapta();
            jLabel7.setText(Integer.toString(viteza));
            jLabel8.setText(Integer.toString((int)turatie));
        }        
    };    
    Timer t=new Timer(10,actionListener);
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
//    @Override
//   public void update (Graphics g) {
//      paint(g);
//}
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void setareTreapta(){
        if ((prag1==1)&&(prag2==0)&&(prag3==0)&&(prag4==0)&&(prag5==0))
            viteza=2;
        else
            if((prag1==1)&&(prag2==1)&&(prag3==0)&&(prag4==0)&&(prag5==0))
                viteza=3;
            else
                if((prag1==1)&&(prag2==1)&&(prag3==1)&&(prag4==0)&&(prag5==0))
                    viteza=4;
                else
                    if((prag1==1)&&(prag2==1)&&(prag3==1)&&(prag4==1)&&(prag5==0))
                        viteza=5;
                    else
                        if((prag1==1)&&(prag2==1)&&(prag3==1)&&(prag4==1)&&(prag5==1))
                        viteza=6;
                        else
                            viteza=1;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculViteza(){
        if(((int)x>437)&&(s==true)){
                d=true;
                s=false;
            }
        else
            if (((int)x<437)&&(s==false)){
                d=false;
                s=true;
            }
        if (y<90)
            v=0.00000146245*Math.pow( x,3)-0.00197303*Math.pow(x,2)+1.07994*x-146.124;
        else
            if ((s==true)&&(y>90))
                v=-0.00000497923*Math.pow(y,3)+0.00310161*Math.pow(y,2)-0.814108*y+110.267;
            else
                v=0.00000267795*Math.pow(y,3)-0.00170627*Math.pow(y,2)+0.551114*y+52.2706;
        if ((x==354)&&(zona1==1))
            v=0;
        if (v<0)
          v=0;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculTuratie(){        //Trebuie calculata dupa mai multe puncte
        if (yr<115 && xr>90)                //Daca e in zona de turatie 4000+
            turatie=0.000305*Math.pow(xr,3)-0.165550*Math.pow(xr,2)+43.551051*xr+1125.570795;
        else                                //Daca e in zona de turatie 4000-
            turatie=-0.000290*Math.pow(yr,3)+0.171951*Math.pow(yr,2)-47.829050*yr+7144.829871;
        if (turatie<100)
            turatie=0;        
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void setareZoneV(){
        if((zona2==1)&&((int)y>=185)){                                      //Scoate din zona2
            zona2=0;
        }
        if ((zona1==1)&&((int)y<=240)){                                     //Scoate din zona1
            zona1=0;
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void setareZoneR(){
        if ((zona4==1)&&((int)yr>=185)&&(yr!=210)){                 //Scoate din zona4
            zona4=0;
        }
        else
            if ((zona3==1)&&((int)yr<=245)&&(yr!=210)) {           //Scoate in zona 3
                zona3=0;
            }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculCoordonateUV(){
        if ((x<=304)&&(zona1==0)&&(zona2==0)){                                            //Daca intra in zona mijloc stanga vitezometru
                y=y-3;
                if (y<185){
                    zona2=1;
                    x=306;
                    y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
        }
        else
            if ((x>=590)&&(zona2==0)&&(zona1==0)){                                        //Daca intra in zona mijloc dreapta vitezometru
                y=y+2;
                if (y>240){
                    zona1=1;
                    x=595;
                    y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
            }
            else
                if(zona1==1){                                   //Daca e in zona jos vitezometru
                    x=x-pas1;
                    y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
                else
                    if (zona2==1){                              //Daca e in zona sus vitezometru
                        x=x+pas1;
                        y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                    }        
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculCoordonateDV(){
        if (x<=304){                                            //Daca intra in zona mijloc stanga vitezometru
                y=y+3;
                if (y>245){
                    zona1=1;
                    x=306;
                    y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
        }
        else
            if (x>=596){                                        //Daca intra in zona mijloc dreapta vitezometru
                y=y-3;
                if (y<185){
                    zona2=1;
                    x=595;
                    y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
            }
            else
                if(zona1==1){                                               //Daca e in zona jos vitezometru
                    x=x+pas1;
                    y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
                else
                    if (zona2==1){                                          //Daca e in zona sus vitezometru
                        x=x-pas1;
                        y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                     }        
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void calculCoordonateUR(){
         if (xr<270){                               //Sa nu depaseasca valoarea maxima de turatii
             if (yr==210)
                 zona4=1;
             if (zona3==1){                                                     //Daca e in zona jos turometru
                xr=xr-pas3;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
            }
            else
                if (zona4==1){                                                   //Daca e in zona sus turometru
                    xr=xr+pas3;
                    yr=(-1)*Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                }
                else{
                    if (yr<200){
                        xr=xr+pas3;
                        yr=(-1)*Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                        zona4=1;
                    }
                    else
                        yr=210;
                }
         }
     }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculCoordonateDR(){
        if (turatie>1000){                         //Sa nu depaseasca valoarea minima de turatii
            if (yr==210)
                zona3=1;
            if (zona3==1){                                                      //Daca e in zona jos turometru
                xr=xr+4;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
            }
            else
                if (zona4==1){                                   //Daca e in zona sus turometru                    
                    xr=xr-4;
                    yr=(-1)*Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                }
                else{
                    if (yr>220){                        
                        xr=xr+4;
                        yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                        zona3=1;                       
                    }
                    else
                        yr=210;
                }
        }
     }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    @SuppressWarnings("static-access")
    public void decelerareV(){
        pas2=1;        
        if(y<328){            
            if (zona1==1){                                                  //Daca e in zona jos vitezometru
                x=x+pas2;
                y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Speed.class.getName()).log(Level.SEVERE, null, ex);
                }
                repaint();
                setareZoneV();
            }
            else
                if (zona2==1){                                              //Daca e in zona sus vitezometru
                    x=x-pas2;
                    y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Speed.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                    setareZoneV();
                }
                else{
                    if (v<100){                         //Daca intra in zona mijloc stanga vitezometru
                        y=y+3;
                        if (y>245){
                            zona1=1;
                            x=306;
                        }
                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Speed.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{                               //Daca intra in zona mijloc dreapta vitezometru
                         y=y-3;
                        if (y<185){
                            zona2=1;
                            x=595;
                        }
                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Speed.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    repaint();
                    setareZoneV();
                }
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void decelerareR(){
        if (turatie>1000){
            if (zona3==1){                  //Daca e in zona jos turometru               
                xr=xr+pas4;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Speed.class.getName()).log(Level.SEVERE, null, ex);
                }
                repaint();
                setareZoneR();                
            }
            else                                            //Daca e in zona sus turometru
                if (zona4==1){
                    xr=xr-pas4;
                    yr=(-1)*Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Speed.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                    setareZoneR();
                }
                else{
                    xr=xr+pas4;
                    yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                    if (yr>220)                      
                        zona3=1;                    
                }
        }
    }

//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void setarePasi(){
        if ((int)v<60)                      //Setare pasi vitezometru
            pas1=2;
         else
            if ((int)v<90)
                pas1=1;
            else
                pas1=0.5;
        if (turatie<3000)                      //Setare pas crestere turatii functie de valoarea turatiei
            pas3=5;
        if (turatie>3000)
            pas3=4;
        if (turatie>4000)
            pas3=3;
        if (turatie>5000)
            pas3=2;
        if (turatie>5500)
            pas3=1.5;
        if (turatie>6000)
            pas3=1;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void actualizareTuratie(){               //Actualizam pragurile de turatie
        if ((prag1==0)&&(turatie>4000)){            //Ducem turatia la 2000 daca depaseste prima
            prag1=1;                                // oara 4000 (aplicare prag 1)
            xr=27;
            yr=174;
            zona3=0;
            zona4=1;
            turatie=2486;
        }
        if ((prag2==0)&&(turatie>5000)){            //Ducem turatia la 2535 daca depaseste prima
            prag2=1;                                // oara 5000 (aplicare prag 2)
            xr=50;
            yr=127;
            zona3=0;
            zona4=1;
            turatie=3240;
        }
        if ((prag3==0)&&(turatie>6000)){            //Ducem turatia la 3056 daca depaseste prima
            prag3=1;                                // oara 6000 (aplicare prag 3)
            xr=76;
            yr=100;
            zona3=0;
            zona4=1;
            turatie=3790;
        }
        if ((prag4==0)&&(turatie>6300)){
            prag4=1;
            xr=137;
            yr=70;
            zona3=0;
            zona4=1;
            turatie=4769;
        }
        if ((prag5==0)&&(turatie>6500)){
            prag5=1;
            xr=155;
            yr=67;
            zona3=0;
            zona4=1;
            turatie=5034;
        }
         if ((prag5==1)&&(v<100))
            prag5=0;
        if ((prag4==1)&&(v<90))
            prag4=0;
        if ((prag3==1)&&(v<70))
            prag3=0;
        if ((prag2==1)&&(v<40))             //Reseteaza prag2 daca viteza scade sub 40Mph
            prag2=0;
        if ((prag1==1)&&(v<15))             //Reseteaza prag1 daca viteza scade sub 20Mph
            prag1=0;
        if (turatie<=1000){
            xr=34;
            yr=274;
            turatie=1000;
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
//    @Override
//    @SuppressWarnings("static-access")
    @Override
     public void paint(Graphics g) {
        
        super.paint(g);
//        Graphics2D g2=(Graphics2D) bufferGraphics;
        Graphics2D g2=(Graphics2D) g;

        g2.setColor(Color.orange);
	g2.setStroke(new BasicStroke(4));
        //Sterge tot ce a fost desenat inainte
//        g2.clearRect(0,0,dim.width,dim.width);

        
	g2.drawLine((int)x0, (int)y0, (int)x, (int)y);
        g2.drawLine((int)x0-1, (int)y0-1, (int)x, (int)y);
        g2.drawLine((int)x0+1, (int)y0+1, (int)x, (int)y);
        g2.drawLine((int)x0+1, (int)y0-1, (int)x, (int)y);
        g2.drawLine((int)x0-1, (int)y0+1, (int)x, (int)y);

        
        g2.drawLine((int)x0r, (int)y0r, (int)xr, (int)yr);
        g2.drawLine((int)x0r-1, (int)y0r-1, (int)xr, (int)yr);
        g2.drawLine((int)x0r+1, (int)y0r+1, (int)xr, (int)yr);
        g2.drawLine((int)x0r+1, (int)y0r-1, (int)xr, (int)yr);
        g2.drawLine((int)x0r-1, (int)y0r+1, (int)xr, (int)yr);
        g2.setColor(Color.black);
//        g2.fillOval(435, 200, 30, 30);
//        System.out.print("  x=");System.out.print(x);                       //Afisare coordonate varf ace
//        System.out.print("  y=");System.out.print((int)y);
        System.out.print(" turatie=");System.out.print(turatie);
        System.out.print("  xr=");System.out.print(xr);
        System.out.print("  yr=");System.out.print((int)yr);
//        System.out.print("  z1=");System.out.print(zona1);                  //Afisare zone
//        System.out.print("  z2=");System.out.println(zona2);
        System.out.print("  z3=");System.out.print(zona3);
        System.out.print("  z4=");System.out.println(zona4);

        
        
//         g.drawImage(offscreen,0,0,this);
        
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------   
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
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLayeredPane1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jLayeredPane1.setDoubleBuffered(true);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel2.setForeground(new java.awt.Color(255, 204, 0));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setBounds(400, 280, 60, 30);
        jLayeredPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel4.setText("M/h");
        jLabel4.setBounds(450, 280, 60, 30);
        jLayeredPane1.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel3.setForeground(new java.awt.Color(255, 204, 0));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setBounds(400, 310, 60, 30);
        jLayeredPane1.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel5.setText("Km/h");
        jLabel5.setBounds(450, 310, 60, 30);
        jLayeredPane1.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel6.setText("Gear");
        jLabel6.setBounds(450, 340, 60, 30);
        jLayeredPane1.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel7.setForeground(new java.awt.Color(255, 204, 0));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel7.setBounds(400, 340, 60, 30);
        jLayeredPane1.add(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel8.setForeground(new java.awt.Color(255, 204, 0));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel8.setBounds(120, 280, 60, 30);
        jLayeredPane1.add(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24));
        jLabel9.setText("Revs");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel9.setBounds(180, 280, 60, 30);
        jLayeredPane1.add(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speedometer/Speedometer 4_modif.jpg"))); // NOI18N
        jLabel1.setDebugGraphicsOptions(javax.swing.DebugGraphics.BUFFERED_OPTION);
        jLabel1.setDoubleBuffered(true);
        jLabel1.setBounds(0, 0, 617, 390);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Speed().setVisible(true);
            }
        }
        );
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables

//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void keyPressed(KeyEvent e) {
         int tasta;
         tasta=e.getKeyCode();
         setarePasi();
         if (tasta==KeyEvent.VK_DOWN){     //Daca se apasa sageata sus
            if (v>0){
                calculCoordonateDV();
                setareZoneV();
            }
            calculCoordonateDR();
            setareZoneR();
            repaint();
        }
        if (tasta==KeyEvent.VK_UP){        //Daca se apasa sageata jos
            if (v<140){
                calculCoordonateUV();
                setareZoneV();
            }
            calculCoordonateUR();
            setareZoneR();
            repaint();
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void keyReleased(KeyEvent e) {
    }
}
