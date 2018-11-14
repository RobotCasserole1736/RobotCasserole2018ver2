package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;

public class DriveTrain{

double Direction = 0;
double Turn = 0;
double motorSpeedLeft = 0;
double motorSpeedRight = 0;

    //Constructor
 
 public void getDirectionCMD(){
    Direction = getY(GenericHID.Hand hand.kLeft);
    return getDirectionCMD;
   }
 public void getTurnCMD(){
    Turn = getX(GenericHID.Hand hand.kRight);
    return getTurnCMD;
   }

   
    // Do init (one time-stuff) here

    
 public void update(){
    //Do periodic stuff
     public void setMotorSpeedLeft Direction(){
        if(getDirectionCMD != 0){
            MotorSpeedLeft = getDirectionCMD;
            MotorSpeedRight = -1*getDirectionCMD;
        }
    }
}
     public void setMotorSpeedRight Turn(){
        if(getTurnCMD != 0){
            MotorSpeedRight = getTurnCMD;
            MotorSpeedLeft = getTurnCMD;
        }
     }
 }

 public void setSomeInput(){

 }

 public void getSomeOutput(){

 }
     } 