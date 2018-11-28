package frc.robot;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.DriveTrain;


public class Autonomous {
    double startTime;
    public Autonomous() {
        startTime = Timer.getFPGATimestamp();
    }

    public void update(){
      if(Timer.getFPGATimestamp() - startTime < 10){
        DriveTrain.getInstance().setMotorCMD(1);
      } 
      else {
        DriveTrain.getInstance().setMotorCMD(0);
      }
    }
}