/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.usfirst.frc.team1736.lib.Calibration.CalWrangler;
import org.usfirst.frc.team1736.lib.DataServer.CasseroleDataServer;
import org.usfirst.frc.team1736.lib.DataServer.Signal;
import org.usfirst.frc.team1736.lib.LoadMon.CasseroleRIOLoadMonitor;
import org.usfirst.frc.team1736.lib.WebServer.CasseroleDriverView;
import org.usfirst.frc.team1736.lib.WebServer.CasseroleWebServer;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.DriveTrain;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  Intake intk;
  Autonomous auto;

  PowerDistributionPanel pdp;

  Signal robotCurrentDraw;
  Signal visionTargetXPos;
  Signal visionTargetYPos;
  Signal visionTargetSize;
  Signal visionTargetPresent;
  Signal visionSystemOnline;
  Signal visionPacketRxRate;
  Signal visionProcessFramerate;
  Signal visionJeVoisCPULoad;
  Signal visionJeVoisCPUTemp;
  Signal rioCPULoad;
  Signal rioMemLoad;

  CasseroleWebServer webserver;
  CalWrangler wrangler;

  JeVoisInterface visionProc;
  CasseroleRIOLoadMonitor loadMon;
  
  
  
  //////////////////////////////////////////////////////////////////////////
  // DISABLED
  //////////////////////////////////////////////////////////////////////////


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    /* Init website utilties */
    webserver = new CasseroleWebServer();
    wrangler = new CalWrangler();

    /* Init Drivetrain */
    DriveTrain.getInstance(); //Ensure it gets init'ed once

    /* Init intake */
    intk= new Intake();

    /* Init Robot parts */
    pdp = new PowerDistributionPanel();

    /* Init Vision Processing */
    visionProc = new JeVoisInterface(true);

    /* Init software utilities */
    loadMon= new CasseroleRIOLoadMonitor();

    /* Init telemetry */
    robotCurrentDraw = new Signal("Total Current", "A");
    visionTargetXPos = new Signal("Vision Target X Pos", "norm");     
    visionTargetYPos = new Signal("Vision Target Y Pos", "norm");
    visionTargetSize = new Signal("Vision Target Size", "norm");
    visionTargetPresent = new Signal("Vision Target Observed", "bool");
    visionSystemOnline = new Signal("Vision System Online", "bool");
    visionPacketRxRate = new Signal("Vision System Packet RX Rate", "Packets/Sec");
    visionProcessFramerate = new Signal("Vision System Process Framerate", "Frames/Sec");
    visionJeVoisCPULoad = new Signal("Vision System Jevois CPU Load", "Pct");
    visionJeVoisCPUTemp = new Signal("Vision System Jevois CPU Temperature", "C");
    rioCPULoad = new Signal("roboRIO CPU Load", "Pct");
    rioMemLoad = new Signal("roboRIO Memory Load", "Pct"); 

    /* Init driver view */
    CasseroleDriverView.newBoolean("Vision System Offline", "red");
    CasseroleDriverView.newBoolean("Vision Target Detected", "green");
    CasseroleDriverView.newDial("Target Angle", -50, 50, 10, -5, 5);
    CasseroleDriverView.newWebcam("Vision Proc Cam", "http://10.17.36.2:1180/stream.mjpg", 50, 50, 0);



    /* Fire up webserver & telemetry dataserver */
    webserver.startServer();
    CasseroleDataServer.getInstance().startServer();
  }
  
  /**
   * This function is called once right before the start of disabled mode.
   */
  @Override
  public void disabledInit() {
    CasseroleDataServer.getInstance().logger.stopLogging();
  }

  /**
   * This function is called periodically during disabled mode.
   */
  @Override
  public void disabledPeriodic() {
    DriveTrain.getInstance().update();

    telemetryUpdate();
  }
  
  //////////////////////////////////////////////////////////////////////////
  // AUTO
  //////////////////////////////////////////////////////////////////////////

  /**
   * This function is called once right before the start of autonomous.
   */
  @Override
  public void autonomousInit() {
    CasseroleDataServer.getInstance().logger.startLoggingAuto();
    auto = new Autonomous();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
    auto.update();

    telemetryUpdate();
  }
  
  //////////////////////////////////////////////////////////////////////////
  // TELEOP
  //////////////////////////////////////////////////////////////////////////
  
  /**
   * This function is called once right before the start of operator control.
   */
  @Override
  public void teleopInit() {
    CasseroleDataServer.getInstance().logger.startLoggingTeleop();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    intk.update();
    DriveTrain.getInstance().update();


    telemetryUpdate();
  }

  //////////////////////////////////////////////////////////////////////////
  // Test
  //////////////////////////////////////////////////////////////////////////
  
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }

  //////////////////////////////////////////////////////////////////////////
  // Utilties
  //////////////////////////////////////////////////////////////////////////
  public void telemetryUpdate(){
    double sample_time_ms = Timer.getFPGATimestamp()*1000.0;

    robotCurrentDraw.addSample(sample_time_ms, pdp.getTotalCurrent());
    visionTargetXPos.addSample(sample_time_ms,visionProc.getTgtXPos());
    visionTargetYPos.addSample(sample_time_ms,visionProc.getTgtYPos());
    visionTargetSize.addSample(sample_time_ms,visionProc.getTgtArea());
    visionTargetPresent.addSample(sample_time_ms,visionProc.isTgtVisible()?1.0:0.0);
    visionSystemOnline.addSample(sample_time_ms,visionProc.isVisionOnline()?1.0:0.0);
    visionPacketRxRate.addSample(sample_time_ms,visionProc.getPacketRxRate_PPS());
    visionProcessFramerate.addSample(sample_time_ms,visionProc.getJeVoisFramerate_FPS());
    visionJeVoisCPULoad.addSample(sample_time_ms,visionProc.getJeVoisCpuLoad_pct());
    visionJeVoisCPUTemp.addSample(sample_time_ms,visionProc.getJeVoisCPUTemp_C());
    rioCPULoad.addSample(sample_time_ms,loadMon.getCPULoadPct());
    rioMemLoad.addSample(sample_time_ms,loadMon.getMemLoadPct());
    driverViewUpdate();
  }

  public void driverViewUpdate(){
    CasseroleDriverView.setBoolean("Vision System Offline", !visionProc.isVisionOnline());
    CasseroleDriverView.setBoolean("Vision Target Detected", visionProc.isTgtVisible());
    CasseroleDriverView.setDialValue("Target Angle", (visionProc.getTgtXPos()-0.5)*100);
  }

}
