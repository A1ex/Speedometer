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
    InputStream EngineStart,BatteryAlarm,FuelAlarm,OilAlarm,DoorsAlarm,SeatbeltAlarm;
    AudioStream asEngineStart,asBatteryAlarm,asFuelAlarm,asOilAlarm,asDoorsAlarm,asSeatbeltAlarm;

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
//---------------------------------------------------------------------------------------------------
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
//---------------------------------------------------------------------------------------------------
         try {
            FuelAlarm = new FileInputStream("FuelLevelAlarm.wav");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            asFuelAlarm = new AudioStream(FuelAlarm);
        } catch (IOException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
//---------------------------------------------------------------------------------------------------
         try {
            OilAlarm = new FileInputStream("OilLevelAlarm.wav");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            asOilAlarm = new AudioStream(OilAlarm);
        } catch (IOException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//---------------------------------------------------------------------------------------------------
         try {
            DoorsAlarm = new FileInputStream("DoorsOpenAlarm.wav");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            asDoorsAlarm = new AudioStream(DoorsAlarm);
        } catch (IOException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
//---------------------------------------------------------------------------------------------------
         try {
            SeatbeltAlarm = new FileInputStream("SeatbeltAlarm.wav");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            asSeatbeltAlarm = new AudioStream(SeatbeltAlarm);
        } catch (IOException ex) {
            Logger.getLogger(SensorAlarm.class.getName()).log(Level.SEVERE, null, ex);
        }
//---------------------------------------------------------------------------------------------------
    }
    
    public void StartEngine(){
        AudioPlayer.player.start(asEngineStart);
    }
    public void BatteryAlarm(){
        AudioPlayer.player.start(asBatteryAlarm);
    }
    public void PumpAlarm(){
        AudioPlayer.player.start(asFuelAlarm);
    }
    public void OilAlarm(){
        AudioPlayer.player.start(asOilAlarm);
    }
    public void DoorsAlarm(){
        AudioPlayer.player.start(asDoorsAlarm);
    }
    public void SeatbeltAlarm(){
        AudioPlayer.player.start(asSeatbeltAlarm);
    }

    
}
