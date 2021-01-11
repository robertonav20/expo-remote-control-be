package rob.volume.quarkus;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

interface Mute extends StdCallLibrary {
    Mute INSTANCE = Native.loadLibrary("./lib/VolumeController.dll", Mute.class);

    short Java_VolumeController_muteOn();
    short Java_VolumeController_muteOff();
    short Java_VolumeController_changeVolume(Float value);
}

public class LaunchMute {
    public static void main(String[] ss) {
        System.out.println(Mute.INSTANCE.Java_VolumeController_muteOn());
        System.out.println(Mute.INSTANCE.Java_VolumeController_muteOff());
        System.out.println(Mute.INSTANCE.Java_VolumeController_changeVolume(new Float(0.5f)));
    }
}