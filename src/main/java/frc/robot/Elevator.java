package frc.robot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;

public class Elevator {
    int channel = 0;
    VictorSP elemotor;
    public Elevator() {
        elemotor = new VictorSP(channel);
    }
}