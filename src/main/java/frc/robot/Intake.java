package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Spark;

public class Intake{

    /** This is another comment*/
    //Constructor
    private XboxController xboxCont;
    Spark leftMotor;
    Spark rightMotor;
    public Intake(){
        xboxCont = new XboxController(0);
        leftMotor = new Spark( 0);
        rightMotor = new Spark( 1);
    }
    //init stuff
    public void update() {
        double leftMotorCmd;
        double rightMotorCmd;


        boolean Abutton = xboxCont.getAButton();
        boolean Bbutton = xboxCont.getBButton();
        if(Abutton){
            leftMotorCmd = 1.0;
            rightMotorCmd = 1.0;
        } else if(Bbutton){
            leftMotorCmd = -1.0;
            rightMotorCmd = -1.0;
        } else{
            leftMotorCmd = 0;
            rightMotorCmd = 0;
        }
        System.out.println(Abutton);
        leftMotor.set(leftMotorCmd);
        rightMotor.set(-1*rightMotorCmd);
    }

}

