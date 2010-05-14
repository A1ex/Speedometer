/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package speedometer;

import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import  java.io.*;
import javax.swing.JApplet;


/**
 *
 * @author Alexandru Popescu
 */
public class SensorAlarm {

    AudioClip EngineSound,EngineStart,BatteryAlarm,FuelAlarm,OilAlarm,DoorsAlarm,SeatbeltAlarm;
    SensorAlarm(){
        init();
    }

    public void init(){
         File file0=new File("EngineSound.wav");
        try {
            EngineSound = JApplet.newAudioClip(file0.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
         File file1=new File("EngineStart.wav");
        try {
            EngineStart = JApplet.newAudioClip(file1.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
         File file2=new File("BatteryLevelAlarm.wav");
        try {
            BatteryAlarm = JApplet.newAudioClip(file2.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file3=new File("FuelLevelAlarm.wav");
        try {
            FuelAlarm = JApplet.newAudioClip(file3.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file4=new File("OilLevelAlarm.wav");
        try {
            OilAlarm = JApplet.newAudioClip(file4.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file5=new File("DoorsOpenAlarm.wav");
        try {
            DoorsAlarm = JApplet.newAudioClip(file5.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file6=new File("SeatbeltAlarm.wav");
        try {
            SeatbeltAlarm = JApplet.newAudioClip(file6.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void StartEngine(){
        EngineStart.play();
    }
    public void EngineNoise(){        
        EngineSound.loop();
    }
    public void StopEngineNoise(){
        EngineSound.stop();
    }
    public void BatteryAlarm(){
        BatteryAlarm.play();
    }
    public void PumpAlarm(){
        FuelAlarm.play();
    }
    public void OilAlarm(){
        OilAlarm.play();
    }
    public void DoorsAlarm(){
        DoorsAlarm.play();
    }
    public void SeatbeltAlarm(){
        SeatbeltAlarm.play();
    }

    
}
