/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

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
public class sensorAlarm {

    AudioClip EngineSound,EngineStart,BatteryAlarm,FuelAlarm,LightsAlarm,DoorsAlarm,SeatbeltAlarm,Throttle,ThrottleStop,MaxThrottle,Brake,BrakeLoop;
    sensorAlarm(){
        init();
    }

    public void init(){
         File file0=new File("Sounds/EngineSound.wav");
        try {
            EngineSound = JApplet.newAudioClip(file0.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
         File file1=new File("Sounds/EngineStart.wav");
        try {
            EngineStart = JApplet.newAudioClip(file1.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
         File file2=new File("Sounds/BatteryLevelAlarm.wav");
        try {
            BatteryAlarm = JApplet.newAudioClip(file2.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file3=new File("Sounds/FuelLevelAlarm.wav");
        try {
            FuelAlarm = JApplet.newAudioClip(file3.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file4=new File("Sounds/LightsOnAlarm.wav");
        try {
            LightsAlarm = JApplet.newAudioClip(file4.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file5=new File("Sounds/DoorsOpenAlarm.wav");
        try {
            DoorsAlarm = JApplet.newAudioClip(file5.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file6=new File("Sounds/SeatbeltAlarm.wav");
        try {
            SeatbeltAlarm = JApplet.newAudioClip(file6.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file7=new File("Sounds/Throttle.wav");
        try {
            Throttle = JApplet.newAudioClip(file7.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file8=new File("Sounds/ThrottleStop.wav");
        try {
            ThrottleStop = JApplet.newAudioClip(file8.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file9=new File("Sounds/MaxThrottle.wav");
        try {
            MaxThrottle = JApplet.newAudioClip(file9.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file10=new File("Sounds/Brake.wav");
        try {
            Brake = JApplet.newAudioClip(file10.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(sensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void startEngine(){
        EngineStart.play();
    }
    public void engineSound(){
        EngineSound.loop();
    }
    public void stopEngineSound(){
        EngineSound.stop();
    }
    public void startBatteryAlarm(){
        BatteryAlarm.loop();
    }
    public void stopBatteryAlarm(){
        BatteryAlarm.stop();
    }
    public void startPumpAlarm(){
        FuelAlarm.loop();
    }
    public void stopPumpAlarm(){
        FuelAlarm.stop();
    }
    public void startLightsAlarm(){
        LightsAlarm.loop();
    }
    public void stopLightsAlarm(){
        LightsAlarm.stop();
    }
    public void startDoorsAlarm(){
        DoorsAlarm.loop();
    }
    public void stopDoorsAlarm(){
        DoorsAlarm.stop();
    }
    public void startSeatbeltAlarm(){
        SeatbeltAlarm.loop();
    }
    public void stopSeatbeltAlarm(){
        SeatbeltAlarm.stop();
    }
    public void startThrottle(){
        Throttle.play();
    }
    public void stopThrottle(){
        Throttle.stop();
    }
    public void throttleStop(){
        ThrottleStop.play();
    }
    public void stopThrottleStop(){
        ThrottleStop.stop();
    }
    public void startMaxThrottle(){
        MaxThrottle.loop();
    }
    public void stopMaxThrottle(){
        MaxThrottle.stop();
    }
     public void brake(){
        Brake.play();
    }
}
