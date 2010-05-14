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
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
/**
 *
 * @author Alexandru Popescu
 */
public class SpeedometerPanel extends javax.swing.JPanel  implements KeyListener {

    public double x0=450;
    public double x0r=170;
    public double y0=215+21-45;
    public double y0r=215+21-45;
    public double x=354;
    public double xr=64;
    public double y=328+21-45;
    public double yr=339-45;
//    public double xr=34;          //Sunt comentate valorile xr si yr pt turatie la 1000(motor pornit)
//    public double yr=274;
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
    public int viteza=0;
    public int zona1=1;                                     //zona1 si zona2 pt vitezometru (jos/sus)
    public int zona2=0;
    public int zona3=1;                                     //zona3 si zona4 pt turometru (jos/sus)
    public int zona4=0;
    public boolean pornit=false;
    public boolean alarmaBaterie=false,alarmaPompa=false,alarmaUlei=false,alarmaUsi=false,alarmaCentura;
    Icon butonverde,butonrosu,baterierosie,bateriegri,pompagri,pomparosie,uleigri,uleirosie,usigri,usirosie,centuragri,centurarosie;
    public boolean crescutturatie=false;
    public boolean idle=false;
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    Graphics2D big;
    public boolean firsttime=true;
    SensorAlarm alarm1 = new SensorAlarm();
    public boolean sunetmotor=false;        //e folosit in action performed sa dea drumu la sunet doar o data
    BufferedImage buffer; // The image we use for double-buffering
    Graphics2D osg; // Graphics2D object for drawing into the buffer
    public boolean firstTime=true;
    public int a=50,b=200;
    Rectangle area;
    Image m;
    File file = new File("Speedometer.jpg");
    KeyListener l ;

    /** Creates new form SpeedometerPanel */
    public SpeedometerPanel()throws IOException, SQLException  {
        initComponents();
        addKeyListener(this);
        t.start();
        try {
            m =ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        butonverde = new ImageIcon("images/buttongreen.jpg");
        butonrosu = new ImageIcon("images/buttonred.jpg");
        baterierosie=new ImageIcon("images/battery_red.jpg");
        bateriegri=new ImageIcon("images/battery_gray.jpg");
        pompagri=new ImageIcon("images/pump_gray.jpg");
        pomparosie=new ImageIcon("images/pump_red.jpg");
        uleigri=new ImageIcon("images/oil_gray.jpg");
        uleirosie=new ImageIcon("images/oil_red.jpg");
        usigri=new ImageIcon("images/doors_gray.jpg");
        usirosie=new ImageIcon("images/doors_red.jpg");
        centuragri=new ImageIcon("images/seatbelt_gray.jpg");
        centurarosie=new ImageIcon("images/seatbelt_red.jpg");


//        try {
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/OBCDB");
//        Statement sql= c.createStatement();
//        ResultSet rs=sql.executeQuery("SELECT * FROM Scenario1");
//        while (rs.next()){
//            int index = rs.getInt(1);
//            double v3=rs.getDouble(2);
//            int delay=rs.getInt(3);
//            System.out.println(index+" "+v3+" "+delay);
//        }

    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (pornit && (sunetmotor==false)){     //Da drumu la sunetul de motor pornit
                alarm1.EngineNoise();
                sunetmotor=true;
            }
            if (!pornit && sunetmotor){
                alarm1.StopEngineNoise();
                sunetmotor=false;
            }
            if ((crescutturatie==false)&&(pornit==true)&&(turatie<1000))
                cresteTuratieLaPornire();
            calculViteza();
            calculTuratie();
            actualizareTuratie();
            decelerareV();
            if ((crescutturatie)||(pornit==false))
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
    public void setareTreapta(){
        if (pornit==false)
            viteza=0;
        else{
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
        if (y<90+21-45)
            v=0.00000146245*Math.pow( x,3)-0.00197303*Math.pow(x,2)+1.07994*x-146.124;
        else
            if ((s==true)&&(y>90+21-45))
                v=-0.000003916*Math.pow(y,3)+0.002106564*Math.pow(y,2)- 0.562179848*y+86.798186209;
            else
                v=0.000002682*Math.pow(y,3)- 0.001523562*Math.pow(y,2)+0.473605642*y+65.175332050;
        if ((x==354)&&(zona1==1))
            v=0;
        if (v<0)
          v=0;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculTuratie(){        //Trebuie calculata dupa mai multe puncte
        if (yr<115+21-45 && xr>90)                //Daca e in zona de turatie 4000+
            turatie=0.000305*Math.pow(xr,3)-0.165550*Math.pow(xr,2)+43.551051*xr+1125.570795;
        else                                //Daca e in zona de turatie 4000-
            turatie=-0.000289881*Math.pow(yr,3)+0.151079520*Math.pow(yr,2)- 40.076318640*yr+6091.969107107 ;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void setareZoneV(){
        if((zona2==1)&&((int)y>=185+21-45)){                                      //Scoate din zona2
            zona2=0;
        }
        if ((zona1==1)&&((int)y<=240+21-45)){                                     //Scoate din zona1
            zona1=0;
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void setareZoneR(){
        if ((zona4==1)&&((int)yr>=185+21-45)&&(yr!=210+21-45)){                 //Scoate din zona4
            zona4=0;
        }
        else
            if ((zona3==1)&&((int)yr<=245-45)&&(yr!=210-45)) {           //Scoate in zona 3
                zona3=0;
            }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculCoordonateUV(){
        if ((x<=304)&&(zona1==0)&&(zona2==0)){                                            //Daca intra in zona mijloc stanga vitezometru
                y=y-3;
                if (y<185+21-45){
                    zona2=1;
                    x=306;
                    y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
        }
        else
            if ((x>=590)&&(zona2==0)&&(zona1==0)){                                        //Daca intra in zona mijloc dreapta vitezometru
                y=y+2;
                if (y>240+21-45){
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
                if (y>245+21-45){
                    zona1=1;
                    x=306;
                    y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
        }
        else
            if (x>=596){                                        //Daca intra in zona mijloc dreapta vitezometru
                y=y-3;
                if (y<185+21-45){
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
             if (yr==210+21-45)
                 zona4=1;
             if ((zona3==1)&&(xr-pas3>20)){                                                     //Daca e in zona jos turometru
                xr=xr-pas3;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
            }
            else
                if ((zona4==1)&&(xr+pas3>20)){                                                   //Daca e in zona sus turometru
                    xr=xr+pas3;
                    yr=(-1)*Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                }
                else{
                    if ((yr<200+21)&&(xr+pas3>20)){
                        xr=xr+pas3;
                        yr=(-1)*Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                        zona4=1;
                    }
                    else
                        yr=210+21-45;
                }
         }
     }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculCoordonateDR(){
        if (turatie>1000){                         //Sa nu depaseasca valoarea minima de turatii
            if (yr==210+21-45)
                zona3=1;
            if (zona3==1){                                                      //Daca e in zona jos turometru
                xr=xr+4;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
            }
            else
                if (zona4==1){                                                  //Daca e in zona sus turometru
                    xr=xr-4;
                    yr=(-1)*Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                }
                else{
                    if (yr>220+21){
                        xr=xr+4;
                        yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                        zona3=1;
                    }
                    else
                        yr=210+21-45;
                }
        }
     }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    @SuppressWarnings("static-access")
    public void decelerareV(){
        pas2=1;
        if(y<328+21-45){
            if (zona1==1){                                                  //Daca e in zona jos vitezometru
                x=x+pas2;
                y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                    setareZoneV();
                }
                else{
                    if (v<100){                         //Daca intra in zona mijloc stanga vitezometru
                        y=y+3;
                        if (y>245+21-45){
                            zona1=1;
                            x=306;
                        }
                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{                               //Daca intra in zona mijloc dreapta vitezometru
                         y=y-3;
                        if (y<185+21-45){
                            zona2=1;
                            x=595;
                        }
                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        if (((turatie>1000)&&pornit)||(((int)turatie>0)&&(pornit==false))){
            if (zona3==1){                  //Daca e in zona jos turometru
                xr=xr+pas4;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                    setareZoneR();
                }
                else{
                    xr=xr+pas4;
                    yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                    if (yr>220+21-45)
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
            xr=22;
            yr=210+21-45;
            zona3=0;
            zona4=0;
            turatie=1998;
        }
        if ((prag2==0)&&(turatie>5000)){            //Ducem turatia la 2535 daca depaseste prima
            prag2=1;                                // oara 5000 (aplicare prag 2)
            xr=28;
            yr=171+21-45;
            zona3=0;
            zona4=1;
            turatie=2535;
        }
        if ((prag3==0)&&(turatie>6000)){            //Ducem turatia la 3056 daca depaseste prima
            prag3=1;                                // oara 6000 (aplicare prag 3)
            xr=76;
            yr=100+21-45;
            zona3=0;
            zona4=1;
            turatie=3790;
        }
        if ((prag4==0)&&(turatie>6300)){
            prag4=1;
            xr=137;
            yr=70+21-45;
            zona3=0;
            zona4=1;
            turatie=4769;
        }
        if ((prag5==0)&&(turatie>6500)){
            prag5=1;
            xr=155;
            yr=67+21-45;
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
        if ((turatie>=1000)&&pornit)
            crescutturatie=true;
        if ((crescutturatie==true)&&(turatie<1000)&&(pornit==true))
            turatie=1000;
        if ((pornit==false)&&(turatie<0))
            turatie=0;
        if (pornit &&(turatie<=1000)&&crescutturatie&&(idle==false)){
            xr=34;
            yr=274+21-45;
            repaint();
            idle=true;
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    @SuppressWarnings("static-access")
    public void cresteTuratieLaPornire(){
            xr=xr-5;
            yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
            try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            repaint();
            setareZoneR();
        if (turatie>=1000)
            crescutturatie=true;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    @Override
    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2=(Graphics2D) g;
        if (firstTime) {
          Dimension dim = getSize();
          int w = dim.width;
          int h = dim.height;
          area = new Rectangle(dim);
          buffer = (BufferedImage) createImage(w, h);
          osg = buffer.createGraphics();
          osg.setColor(Color.red);
          osg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

          firstTime = false;
        }
        Graphics2D g2=(Graphics2D) osg;
        g2.clearRect(0, 0, area.width, area.height);
        g2.drawImage(m, 0,0, this);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.orange);
	g2.setStroke(new BasicStroke(4));

	g2.drawLine((int)x0, (int)y0, (int)x, (int)y);          //Desenare ac vitezometru
        g2.drawLine((int)x0-1, (int)y0-1, (int)x, (int)y);
        g2.drawLine((int)x0+1, (int)y0+1, (int)x, (int)y);
        g2.drawLine((int)x0+1, (int)y0-1, (int)x, (int)y);
        g2.drawLine((int)x0-1, (int)y0+1, (int)x, (int)y);

        g2.drawLine((int)x0r, (int)y0r, (int)xr, (int)yr);      //Desenare ac turometru
        g2.drawLine((int)x0r-1, (int)y0r-1, (int)xr, (int)yr);
        g2.drawLine((int)x0r+1, (int)y0r+1, (int)xr, (int)yr);
        g2.drawLine((int)x0r+1, (int)y0r-1, (int)xr, (int)yr);
        g2.drawLine((int)x0r-1, (int)y0r+1, (int)xr, (int)yr);

        g2.setColor(Color.black);
        g2.fillOval(435, 177, 30, 30);
        g2.fillOval(155, 177, 30, 30);
//        g2.setColor(Color.yellow);
//        g2.setStroke(new BasicStroke(2));
//        g2.drawOval(435, 177, 30, 30);
//        g2.drawOval(155, 177, 30, 30);

        g.drawImage(buffer, 0, 0, this);


//        System.out.print("  s=");System.out.print(s);
//        System.out.print("  x=");System.out.print(x);         //Afisare coordonate varf ace
//        System.out.print("  y=");System.out.println((int)y);
//        System.out.print(" turatie=");System.out.print(turatie);
//        System.out.print(" viteza=");System.out.print((int)v);
//        System.out.print("  xr=");System.out.print(xr);
//        System.out.print("  yr=");System.out.println((int)yr);
//        System.out.print("  z1=");System.out.print(zona1);                  //Afisare zone
//        System.out.print("  z2=");System.out.println(zona2);
//        System.out.print("  z3=");System.out.print(zona3);
//        System.out.print("  z4=");System.out.println(zona4);
    }
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
         if (tasta==KeyEvent.VK_DOWN){     //Daca se apasa sageata jos
            if (v>0){
                calculCoordonateDV();
                setareZoneV();
            }
            if (turatie>1000){
                calculCoordonateDR();
                setareZoneR();
                repaint();
            }
        }
        if (tasta==KeyEvent.VK_UP){        //Daca se apasa sageata sus
            idle=false;
            if (pornit&&(crescutturatie)){
                    if (v<140){
                    calculCoordonateUV();
                    setareZoneV();
                }
                calculCoordonateUR();
                setareZoneR();
                repaint();

            }
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void keyReleased(KeyEvent e) {
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
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
        ulei = new javax.swing.JLabel();
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

        Buton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speedometer/buttonred.jpg"))); // NOI18N
        Buton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Buton1ApasareStartStop(evt);
            }
        });
        Buton1.setBounds(10, 350, 30, 30);
        jLayeredPane1.add(Buton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        baterie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speedometer/battery_gray.jpg"))); // NOI18N
        baterie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                baterieAlarmaBaterie(evt);
            }
        });
        baterie.setBounds(200, 340, 40, 39);
        jLayeredPane1.add(baterie, javax.swing.JLayeredPane.DEFAULT_LAYER);

        pompa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speedometer/pump_gray.jpg"))); // NOI18N
        pompa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pompaAlarmaPompa(evt);
            }
        });
        pompa.setBounds(250, 340, 40, 40);
        jLayeredPane1.add(pompa, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ulei.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speedometer/oil_gray.jpg"))); // NOI18N
        ulei.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                uleiAlarmaUlei(evt);
            }
        });
        ulei.setBounds(300, 340, 40, 39);
        jLayeredPane1.add(ulei, javax.swing.JLayeredPane.DEFAULT_LAYER);

        usi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speedometer/doors_gray.jpg"))); // NOI18N
        usi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usiAlarmaUsi(evt);
            }
        });
        usi.setBounds(350, 340, 40, 39);
        jLayeredPane1.add(usi, javax.swing.JLayeredPane.DEFAULT_LAYER);

        centura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speedometer/seatbelt_gray.jpg"))); // NOI18N
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

        if (pornit==false){                         //Daca se porneste motorul
            butonStart.setText("Stop Engine");
            pornit=true;
            Buton1.setIcon(butonverde);
            viteza=1;
        } else{                                       //Daca se opreste motorul
            butonStart.setText("Start Engine");
            pornit=false;
            idle=false;
            Buton1.setIcon(butonrosu);
            crescutturatie=false;
            //            alarm1.StopEngineNoise();
        }
        SensorAlarm alarm = new SensorAlarm();
        if (pornit)                                 //Sunet la pornire
            alarm.StartEngine();
        //        alarm1.EngineNoise();
}//GEN-LAST:event_Buton1ApasareStartStop

    private void baterieAlarmaBaterie(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_baterieAlarmaBaterie
        // TODO add your handling code here:
        if (alarmaBaterie){
            baterie.setIcon(bateriegri);
            alarmaBaterie=false;
        } else{
            SensorAlarm alarm = new SensorAlarm();
            baterie.setIcon(baterierosie);
            alarmaBaterie=true;
            alarm.BatteryAlarm();
        }
}//GEN-LAST:event_baterieAlarmaBaterie

    private void pompaAlarmaPompa(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pompaAlarmaPompa
        // TODO add your handling code here:
        if (alarmaPompa){
            pompa.setIcon(pompagri);
            alarmaPompa=false;
        } else{
            SensorAlarm alarm = new SensorAlarm();
            pompa.setIcon(pomparosie);
            alarmaPompa=true;
            alarm.PumpAlarm();
        }
}//GEN-LAST:event_pompaAlarmaPompa

    private void uleiAlarmaUlei(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_uleiAlarmaUlei
        // TODO add your handling code here:
        if (alarmaUlei){
            ulei.setIcon(uleigri);
            alarmaUlei=false;
        } else{
            SensorAlarm alarm = new SensorAlarm();
            ulei.setIcon(uleirosie);
            alarmaUlei=true;
            alarm.OilAlarm();
        }
}//GEN-LAST:event_uleiAlarmaUlei

    private void usiAlarmaUsi(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usiAlarmaUsi
        // TODO add your handling code here:
        if (alarmaUsi){
            usi.setIcon(usigri);
            alarmaUsi=false;
        } else{
            SensorAlarm alarm = new SensorAlarm();
            usi.setIcon(usirosie);
            alarmaUsi=true;
            alarm.DoorsAlarm();
        }
}//GEN-LAST:event_usiAlarmaUsi

    private void centuraAlarmaCentura(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_centuraAlarmaCentura
        // TODO add your handling code here:
        if (alarmaCentura){
            centura.setIcon(centuragri);
            alarmaCentura=false;
        } else{
            SensorAlarm alarm = new SensorAlarm();
            centura.setIcon(centurarosie);
            alarmaCentura=true;
            alarm.SeatbeltAlarm();
        }
}//GEN-LAST:event_centuraAlarmaCentura


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Buton1;
    private javax.swing.JLabel baterie;
    private javax.swing.JLabel butonStart;
    private javax.swing.JLabel centura;
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
    private javax.swing.JLabel ulei;
    private javax.swing.JLabel usi;
    // End of variables declaration//GEN-END:variables

}
