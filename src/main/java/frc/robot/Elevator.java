package frc.robot;
import edu.wpi.first.wpilibj.VictorSP;

public class Elevator {
    int channel = 0;
    VictorSP elemotor;
    public Elevator() {
        elemotor = new VictorSP(channel);
    }
}