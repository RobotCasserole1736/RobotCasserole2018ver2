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

  CasseroleWebServer webserver = new CasseroleWebServer();
  CalWrangler wrangler = new CalWrangler();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    DriveTrain.getInstance(); //Ensure it gets init'ed once
    intk= new Intake();

    pdp = new PowerDistributionPanel();

    robotCurrentDraw = new Signal("Total Current", "A");

    webserver.startServer();
    CasseroleDataServer.getInstance().startServer();
  }

  @Override
  public void disabledPeriodic() {
    double loop_time_ms = Timer.getFPGATimestamp()*1000.0;

    DriveTrain.getInstance().update();

    robotCurrentDraw.addSample(loop_time_ms, pdp.getTotalCurrent());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    auto = new Autonomous();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    double loop_time_ms = Timer.getFPGATimestamp()*1000.0;

    auto.update();

    robotCurrentDraw.addSample(loop_time_ms, pdp.getTotalCurrent());
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double loop_time_ms = Timer.getFPGATimestamp()*1000.0;
    
    intk.update();
    DriveTrain.getInstance().update();

    robotCurrentDraw.addSample(loop_time_ms, pdp.getTotalCurrent());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
