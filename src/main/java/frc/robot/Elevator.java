package frc.robot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.DigitalInput;

public class Elevator {
    //Constants
	public final double ELEV_ENC_PULSES_PER_REV = 1024;
	public final double ELEV_WINCH_DIAMETER_IN = 1.6;
	
	//Derived Constants
    public final double ELEV_HEIGHT_IN_PER_WINCH_REV = 2*Math.PI*(ELEV_WINCH_DIAMETER_IN/2.0); //Linear rope distance is 2*pi*r_winch
    
    boolean lowerLimitReach = false;
    boolean upperLimitReach = false;
    boolean isInitializing = false;

    int channel = 0;
    int currentLevel = -1;
    int desiredLevel = 0;
    double currentHeightInches = -1;
    double desiredHeightInches = 0;
    VictorSP eleMotor;
    Encoder eleEncoder;
    DigitalInput lowerLimit;
    DigitalInput upperLimit;

    public Elevator() {
        eleMotor = new VictorSP(channel);
        //TODO:Update Encoder Number/channels
        eleEncoder = new Encoder(1,2);
        eleEncoder.setDistancePerPulse(ELEV_HEIGHT_IN_PER_WINCH_REV);
        lowerLimit = new DigitalInput(3);
        upperLimit = new DigitalInput(4);
    }

    public void initializeElevator() {
        if(currentLevel < 0) {
            if(!lowerLimitReach) 
                eleMotor.set(-0.5);
                isInitializing = true;
            else {

            }
        }

    }

    public void setElevatorDesiredLevel(int level) {
        desiredLevel = level;
    }

    public void updateDesiredHeightInches() {
        //TODO: Update Heights
        if(desiredLevel == 0)
            desiredHeightInches = 0;
        else if(desiredLevel == 1)
            desiredHeightInches = 20;
        else if(desiredLevel == 2)
            desiredHeightInches = 60;
        else if(desiredLevel == 3)
            desiredHeightInches = 75;
    }

    public void update() {
        updateDesiredHeightInches();
        lowerLimitReach = lowerLimit.get();
        upperLimitReach = upperLimit.get();
    }
}