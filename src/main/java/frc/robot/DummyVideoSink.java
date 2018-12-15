package frc.robot;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.VideoEvent;
import edu.wpi.cscore.VideoSink;
import java.util.function.Consumer;

public class DummyVideoSink extends VideoSink {

    DummyListener lis;

    public DummyVideoSink(){
        super(CameraServerJNI.createCvSink("Dummy"));

        lis = new DummyListener();

        CameraServerJNI.addListener(lis, 0, false);
    }

}

class DummyListener implements Consumer<VideoEvent>{

    public DummyListener(){
        super();
    }


    @Override
    public void accept(VideoEvent t) {
        return;
    }
}