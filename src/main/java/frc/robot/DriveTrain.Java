package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;

public class DriveTrain {
	private static DriveTrain  dTrain = null;

   XboxController driverController;
   JeVoisInterface visionProc;
   double Direction = 0;
   double Turn = 0;
   double motorSpeedLeft = 0;
   double motorSpeedRight = 0;
   Spark R1;
   Spark R2;
   Spark R3;
   Spark L1;
   Spark L2;
   Spark L3;

   public static synchronized DriveTrain getInstance() {
		if ( dTrain == null)
         dTrain = new DriveTrain();
		return dTrain;
	}
   private DriveTrain() {

      R1 = new Spark(4);
      R2 = new Spark(5);
      R3 = new Spark(6);
      L1 = new Spark(7);
      L2 = new Spark(8);
      L3 = new Spark(9);

      driverController = new XboxController(0);
   }
   // Constructor
   
   public double getDirectionCMD() {
      double joyVal = Math.pow(driverController.getY(GenericHID.Hand.kLeft),3);
      return joyVal;

   }

   public void setVisionProc(JeVoisInterface in){
      visionProc = in;
   }

   public double getTurnCMD() {
      double joyVal = Math.pow(driverController.getX(GenericHID.Hand.kRight),3);

      //hale auto-align to vision target
      if(driverController.getBumper(GenericHID.Hand.kRight)){
          
          if(visionProc.isTgtVisible() && visionProc.isVisionOnline()){
            joyVal = 0.75 * -1.0 * (visionProc.getTgtXPos() - 0.5); //Target X pos of 0.5
          } else {
            joyVal = 0;
          }
          
      }

      return joyVal;
   }

   public void setMotorCMD(double command){
      R1.set(-1*command);
      R2.set(-1*command);
      R3.set(-1*command);
      L1.set(command);
      L2.set(command);
      L3.set(command);
      System.out.println("command:" + Double.toString(command));
   }


   // Do init (one time-stuff) here

   public void update() {
      // Do periodic stuff
      double DirectionCMD;
      double TurnCMD;
      double motorSpeedLeftCMD = 0;
      double motorSpeedRightCMD = 0;

      DirectionCMD = getDirectionCMD();
      TurnCMD = getTurnCMD();
       
      if(Math.abs(DirectionCMD) < 0.15){
         DirectionCMD = 0;
      }

      if(Math.abs(TurnCMD) < 0.15){
         TurnCMD = 0;
      }

      motorSpeedLeftCMD = DirectionCMD - TurnCMD;
      motorSpeedRightCMD = -1 * (DirectionCMD + TurnCMD);

      R1.set(motorSpeedRightCMD);
      R2.set(motorSpeedRightCMD);
      R3.set(motorSpeedRightCMD);
      L1.set(motorSpeedLeftCMD);
      L2.set(motorSpeedLeftCMD);
      L3.set(motorSpeedLeftCMD);

      

      
   }

   
}