/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package speedometer;

import java.util.logging.Level;
import java.util.logging.Logger;
import  sun.audio.*;
import  java.io.*;


/**
 *
 * @author Alexandru Popescu
 */
public class SensorAlarm {

//    AudioClip soundFile1;
//    AudioClip soundFile2;
    InputStream EngineStart,BatteryAlarm;
    AudioStream asEngineStart,asBatteryAlarm;

    SensorAlarm(){
        init();
    }

    public void init(){
        try {
            EngineStart = new FileInputStream("enginestart.wav");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            asEngineStart = new AudioStream(EngineStart);
        } catch (IOException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            BatteryAlarm = new FileInputStream("BatteryLevelAlarm.wav");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            asBatteryAlarm = new AudioStream(BatteryAlarm);
        } catch (IOException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void StartEngine(){
        AudioPlayer.player.start(asEngineStart);
    }
    public void BatteryAlarm(){
        AudioPlayer.player.start(asBatteryAlarm);
    }

    
}
