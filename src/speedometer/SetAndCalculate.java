/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package speedometer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandru Popescu
 */
public class SetAndCalculate {
    
    SensorAlarm alarm = new SensorAlarm();
    public double x0=450;                                       //coordonata x baza ac vitezometru
    public double x0r=170;                                      //coordonata x baza ac turometru
    public double y0=191;                                       //coordonata y baza ac vitezometru
    public double y0r=191;                                      //coordonata y baza ac turometru
    public double x0f=260;                                      //coordonata x baza ac indicator combustibil
    public double y0f=215;                                      //coordonata x baza ac indicator combustibil
    public double xf=223;                                       //coordonata y varf ac indicator combustibil
    public double yf=200;                                       //coordonata y varf ac indicator combustibil
    public double x=354;                                        //coordonata x varf ac vitezometru
    public double xr=64;                                        //coordonata x varf  ac turometru
    public double y=328+21-45;                                  //coordonata y varf ac vitezometru
    public double yr=339-45;                                    //coordonata y varf ac turometru
    public double v=0;                                          //valoarea vitezei   
    public boolean alarmaBaterie=false;                         //determina daca s-a activat alarma de baterie
    public boolean alarmaPompa=false;                           //determina daca s-a activat alarma de pompa
    public boolean alarmaUlei=false;                            //determina daca s-a activat alarma de ulei
    public boolean alarmaUsi=false;                             //determina daca s-a activat alarma de usi
    public boolean alarmaCentura;                               //determina daca s-a activat alarma de centura
    public boolean apasatoprit=false;                           //determina daca se apasa butonul rosu "Stop Engine"
    public boolean apasatpornit=false;                          //determina daca se apasa butonul verde "Start Engine"
    public boolean vitezaidle=true;                             //determina daca viteza in scenariu ramane la aceeasi valoare
    public boolean pornit=false;                                //determina daca e pornit motorul
    public boolean sunetacceleratiemaxima=false;                //folosit in sunet() pt declansarea sunetului de acceleratie maxima (turatie>6500)
    public boolean sunetacceleratieoprita=false;                //folosit in sunet() pt declansarea sunetului oprire a accelerarii
    public boolean sunetacceleratie=false;                      //folosit pt declansarea sunetului de accelerare
    public boolean sunetmotor=false;                            //folosit in sunet() pentru declansarea sunetului de motor
    public boolean frana=false;                                 //folosit in sunet pentru declansarea sunetului de franare
    public boolean cresteturatie=false;                         //determina daca dupa calcularea unei noi turatii aceasta a crescut(folosita la sunet)
    public boolean crescutturatie=false;                        //determina daca la pornire turatia s-a dus la 1000
    public double turatie=0;                                    //valoarea turatiei
    public int prag1=0;                                         //Prag pentru actualizarea turatiei (declanseaza trecerea in viteza 2)
    public int prag2=0;                                         //Prag pentru actualizarea turatiei (declanseaza trecerea in viteza 3)
    public int prag3=0;                                         //Prag pentru actualizarea turatiei (declanseaza trecerea in viteza 4)
    public int prag4=0;                                         //Prag pentru actualizarea turatiei (declanseaza trecerea in viteza 5)
    public int prag5=0;                                         //Prag pentru actualizarea turatiei (declanseaza trecerea in viteza 6)
    public int viteza=0;                                        //Treapta de viteza
    public int zona1=1;                                         //zona de jos pt vitezometru
    public int zona2=0;                                         //zona de sus pt vitezometru
    public int zona3=1;                                         //zona de jos pentru turometru
    public int zona4=0;                                         //zona de sus pentru turometru
    public boolean idle=false;                                  //determina ca acul turometrului este la 1000
    public double comparturatie=0,comparviteza=0;               //folosite sa verifice daca viteza creste sau scade in actionlistener
    public boolean s=true,d=false;                              //folosite sa vada in ce zona e acul vitezometrului(schimba formula)
    public boolean cresteviteza=false;                          //determina daca dupa calcularea unei noi viteze aceasta a crescut(folosita la sunet)
    public double pas1=1;                                       //pas pt accelerare vitezometru
    public double pas2=1;                                       //pas pt decelerare vitezometru
    public double pas3=1;                                       //pas pt accelerare turometru
    public double pas4=3;                                       //pas pt decelerare turometru
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void sunet(){
        if (apasatpornit){                                      //Da drumu la sunetu de start de motor 
            alarm.StartEngine();
            apasatpornit=false;
        }
        if (apasatoprit){                                        //Da drumu la sunetu de oprire motor
            alarm.ThrottleStop();
            apasatoprit=false;
        }
        if (pornit && (sunetmotor==false)){                     //Da drumu la sunetul de motor pornit idle
            alarm.EngineNoise();
            sunetmotor=true;
        }
        if (!pornit && sunetmotor){                             //Da drumu la sunetul de oprire a motorului
            alarm.StopEngineNoise();
            sunetmotor=false;
        }
        if ((turatie>6650)&&(sunetacceleratiemaxima==false)){    //Da drumu la sunetul de acceleratie maxima
            alarm.StartMaxThrottle();
            sunetacceleratiemaxima=true;
        }
        if ((turatie<6650)&&sunetacceleratiemaxima){            //Opreste sunetul de acceleratie maxima
            alarm.StopMaxThrottle();
            sunetacceleratiemaxima=false;
        }
        if (cresteturatie&&crescutturatie){
            if ((sunetacceleratie==false)&&pornit){
                alarm.StopThrottleStop();
                alarm.StartThrottle();
                sunetacceleratieoprita=true;
                alarm.StopEngineNoise();
                sunetmotor=false;
                sunetacceleratie=true;
                frana=false;
            }
        }
        else{
            if (pornit){
                alarm.StopThrottle();
                if (sunetacceleratieoprita && (turatie<6650)){
                    alarm.ThrottleStop();
                    sunetacceleratieoprita=false;
                }
                sunetacceleratie=false;
            }
        }
    }
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
    public void actualizareTuratie(){                           //Actualizam pragurile de turatie
            if ((prag1==0)&&(turatie>5000)){                        //Ducem turatia la 2535 daca depaseste prima
                prag1=1;                                            // oara 5000 (aplicare prag 1)
                xr=28;
                yr=147;
                zona3=0;
                zona4=1;
                turatie=2535;
                alarm.StopThrottle();
                sunetacceleratie=false;
            }
            if ((prag2==0)&&(turatie>5500)){                        //Ducem turatia la 2535 daca depaseste prima
                prag2=1;                                            // oara 5500 (aplicare prag 2)
                xr=28;
                yr=147;
                zona3=0;
                zona4=1;
                turatie=2535;
                alarm.StopThrottle();
                sunetacceleratie=false;
            }
            if ((prag3==0)&&(turatie>6000)){                        //Ducem turatia la 3056 daca depaseste prima
                prag3=1;                                            // oara 6000 (aplicare prag 3)
                xr=76;
                yr=76;
                zona3=0;
                zona4=1;
                turatie=3790;
                alarm.StopThrottle();
                sunetacceleratie=false;
            }
            if ((prag4==0)&&(turatie>6300)){                        //Ducem turatia la 4769 daca depaseste prima
                prag4=1;                                            // oara 6300 (aplicare prag 4)
                xr=137;
                yr=46;
                zona3=0;
                zona4=1;
                turatie=4769;
                alarm.StopThrottle();
                sunetacceleratie=false;
            }
            if ((prag5==0)&&(turatie>6500)){                        //Ducem turatia la 5034 daca depaseste prima
                prag5=1;                                            // oara 6500 (aplicare prag 5)
                xr=155;
                yr=43;
                zona3=0;
                zona4=1;
                turatie=5034;
                alarm.StopThrottle();
                sunetacceleratie=false;
            }
             if ((prag5==1)&&(v<100))                               //Reseteaza prag5 daca viteza scade sub 100Mph
                prag5=0;
            if ((prag4==1)&&(v<90))                                 //Reseteaza prag4 daca viteza scade sub 90Mph
                prag4=0;
            if ((prag3==1)&&(v<70))                                 //Reseteaza prag3 daca viteza scade sub 70Mph
                prag3=0;
            if ((prag2==1)&&(v<40))                                 //Reseteaza prag2 daca viteza scade sub 40Mph
                prag2=0;
            if ((prag1==1)&&(v<15))                                 //Reseteaza prag1 daca viteza scade sub 20Mph
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
                idle=true;
            }
        }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculViteza(){
        comparviteza=v;
       
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
         if (comparviteza<v)
            cresteviteza=true;
        else
            cresteviteza=false;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculTuratie(){
        comparturatie=turatie;
        if (yr<91 && xr>110){                                     //Daca e in zona de turatie 4000+
            turatie=0.000305*Math.pow(xr,3)-0.165550*Math.pow(xr,2)+43.551051*xr+1125.570795;
        }
        else{                                                    //Daca e in zona de turatie 4000-
            turatie=-0.000289881*Math.pow(yr,3)+0.151079520*Math.pow(yr,2)- 40.076318640*yr+6091.969107107 ;
        }
        if (comparturatie<turatie)
            cresteturatie=true;
        else
            cresteturatie=false;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void setareZoneV(){
         if(((int)x>437)&&(s==true)){
                d=true;
                s=false;
            }
        else
            if (((int)x<437)&&(s==false)){
                d=false;
                s=true;
            }
        if((zona2==1)&&((int)y>=185+21-45)){                    //Scoate din zona2
            zona2=0;
        }
        if ((zona1==1)&&((int)y<=240+21-45)){                   //Scoate din zona1
            zona1=0;
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void setareZoneR(){
        if ((zona4==1)&&((int)yr>=185+21-45)&&(yr!=210+21-45)){ //Scoate din zona4
            zona4=0;
        }
        else
            if ((zona3==1)&&((int)yr<=245-45)&&(yr!=210-45)) {  //Scoate in zona 3
                zona3=0;
            }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void decelerareV(){
        if(y<328+21-45){
            if (zona1==1){                                      //Daca e in zona jos vitezometru
                x=x+pas2;
                y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                setareZoneV();
            }
            else
                if (zona2==1){                                  //Daca e in zona sus vitezometru
                    x=x-pas2;
                    y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                    setareZoneV();
                }
                else{
                    if (v<100){                                 //Daca intra in zona mijloc stanga vitezometru
                        y=y+3;
                        if (y>245+21-45){
                            zona1=1;
                            x=306;
                        }
                    }
                    else{                                       //Daca intra in zona mijloc dreapta vitezometru
                         y=y-3;
                        if (y<185+21-45){
                            zona2=1;
                            x=595;
                        }
                    }
                    setareZoneV();
                }
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void decelerareR(){
        if (((turatie>1000)&&pornit)||(((int)turatie>0)&&(pornit==false))){
            if ((zona3==1)&&(xr+pas4>20)){                      //Daca e in zona jos turometru
                xr=xr+pas4;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
                setareZoneR();
            }
            else                                                //Daca e in zona sus turometru
                if ((zona4==1)&&(xr+pas4>20)){
                    xr=xr-pas4;
                    yr=(-1)*Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
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
         public void cresteTuratieLaPornire(){                       //Duce acul turometrului la 100 la apasare start
            xr=xr-5;
            yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
            try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SpeedometerPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            setareZoneR();
        if (turatie>=1000)
            crescutturatie=true;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
    public void calculCoordonateUV(){
        if ((x<=304)&&(zona1==0)&&(zona2==0)){                  //Daca intra in zona mijloc stanga vitezometru
                y=y-3;
                if (y<185+21-45){
                    zona2=1;
                    x=306;
                    y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
        }
        else
            if ((x>=590)&&(zona2==0)&&(zona1==0)){              //Daca intra in zona mijloc dreapta vitezometru
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
                y=y+5;
                if (y>245+21-45){
                    zona1=1;
                    x=306;
                    y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
        }
        else
            if (x>=596){                                        //Daca intra in zona mijloc dreapta vitezometru
                y=y-2;
                if (y<185+21-45){
                    zona2=1;
                    x=595;
                    y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
            }
            else
                if(zona1==1){                                   //Daca e in zona jos vitezometru
                    x=x+pas1;
                    y=Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                }
                else
                    if (zona2==1){                              //Daca e in zona sus vitezometru
                        x=x-pas1;
                        y=(-1)*Math.sqrt(Math.abs(22050-Math.pow((x-x0),2)))+y0;
                     }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void calculCoordonateUR(){
         if (xr<270){                                           //Sa nu depaseasca valoarea maxima de turatii
             if (yr==210+21-45)
                 zona4=1;
             if ((zona3==1)&&(xr-pas3>20)){                     //Daca e in zona jos turometru
                xr=xr-pas3;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
            }
            else
                if ((zona4==1)&&(xr+pas3>20)){                  //Daca e in zona sus turometru
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
        if (turatie>1000){                                      //Sa nu depaseasca valoarea minima de turatii
            if (yr==210+21-45)
                zona3=1;
            if (zona3==1){                                      //Daca e in zona jos turometru
                xr=xr+4;
                yr=Math.sqrt(Math.abs(22050-Math.pow((xr-x0r),2)))+y0r;
            }
            else
                if (zona4==1){                                  //Daca e in zona sus turometru
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
     public void decelerareF(){
        if (yf<245){
            if (yf>=215){
                xf=xf+0.001;
                yf=Math.sqrt(Math.abs(1600-Math.pow((xf-x0f),2)))+y0f;
            }
            else{
                xf=xf-0.001;
                yf=(-1)*Math.sqrt(Math.abs(1600-Math.pow((xf-x0f),2)))+y0f;
                if (yf>214.5)
                    yf=215.1;
            }
        }
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------
     public void setarePasi(){
        if ((int)v<60)                                          //Setare pasi vitezometru
            pas1=2;
         else
            if ((int)v<90){
                pas1=1;
                pas2=0.5;
            }
            else{
                pas1=0.5;
                pas2=0.35;
            }
        if (turatie<3000)                                       //Setare pas crestere turatii functie de valoarea turatiei
            pas3=5;
        if (turatie>3000)
            pas3=4;
        if (turatie>4000){
            pas3=3;
            pas4=2;
        }
        if (turatie>5000){
            pas3=2;
            pas4=1;
        }
        if (turatie>5500){
            pas3=1.5;
            pas4=0.75;
        }
        if (turatie>6000)
            pas3=1;
    }
//-----------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------


}
