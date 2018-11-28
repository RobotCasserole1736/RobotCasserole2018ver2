package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;

public class DriveTrain {
	private static DriveTrain  dTrain = null;

   XboxController driverController;
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
   public DriveTrain() {

      R1 = new Spark(4);
      R2 = new Spark(5);
      R3 = new Spark(6);
      L1 = new Spark(7);
      L2 = new Spark(8);
      L3 = new Spark(9);

      driverController = new XboxController(0);
   }
   // Constructor
   public double deadZone(double in){
      if (in < 0.1 && in > -0.1) {
         return 0;
      } else {
         return in;
      }
   }
   public double getDirectionCMD() {
      double joyVal = driverController.getY(GenericHID.Hand.kLeft);
      return deadZone(joyVal);

   }

   public double getTurnCMD() {
      double joyVal = driverController.getX(GenericHID.Hand.kRight);
      return deadZone(joyVal);
   }

   public void setMotorCMD(double command){
      R1.set(-1*command);
      R2.set(-1*command);
      R3.set(-1*command);
      L1.set(command);
      L2.set(command);
      L3.set(command);
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
       
      motorSpeedLeftCMD = DirectionCMD - TurnCMD;
      motorSpeedRightCMD = -1 * DirectionCMD + TurnCMD;
      
      R1.set(motorSpeedRightCMD);
      R2.set(motorSpeedRightCMD);
      R3.set(motorSpeedRightCMD);
      L1.set(motorSpeedLeftCMD);
      L2.set(motorSpeedLeftCMD);
      L3.set(motorSpeedLeftCMD);

      

      
   }

   
}