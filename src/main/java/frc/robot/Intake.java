package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DigitalInput;

public class Intake{

    /** This is another comment*/
    //Constructor
    private XboxController xboxCont;
    Spark leftMotor;
    Spark rightMotor;
    Spark elbowMotor;
    DigitalInput sensor;
    Boolean cubeHeld = false;
    Boolean elbowMotorRaised = true;



    public Intake(){
        xboxCont = new XboxController(0);
        leftMotor = new Spark( 0);
        rightMotor = new Spark( 1);
        sensor = new DigitalInput(3);
        elbowMotor = new Spark(8);
    }
    //init stuff
    public void update() {
        double leftMotorCmd;
        double rightMotorCmd; 
        if(elbowMotorRaised == true){
            elbowMotor.set(0);
            //we're not sure about 0
            elbowMotorRaised = false;
        }
        
        if(sensor.get() == true) {
             cubeHeld = true;
        }else{ 
            cubeHeld = false;

        }


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

