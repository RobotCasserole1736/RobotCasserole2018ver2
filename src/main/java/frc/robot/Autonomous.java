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
        System.out.println("Motor on");
      } 
      else {
        DriveTrain.getInstance().setMotorCMD(0);
        System.out.println("Motor off");
      }
      System.out.println("It works");
      System.out.println(startTime);
    }
}