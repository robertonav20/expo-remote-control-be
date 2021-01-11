package rob.volume.quarkus;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

interface Mute extends Library {
    Mute INSTANCE = Native.load("./lib/VolumeController.dll", Mute.class);

    boolean Java_VolumeController_muteOn();
    boolean Java_VolumeController_muteOff();
    boolean Java_VolumeController_changeVolume(int newValue);
    int Java_VolumeController_getVolume();
    boolean Java_VolumeController_increaseBy10Percentage();
    boolean Java_VolumeController_decreaseBy10Percentage();
    boolean Java_VolumeController_increaseBy1Percentage();
    boolean Java_VolumeController_decreaseBy1Percentage();
}
public class LaunchMute {
    public static void main(String[] ss) {

        int newVolume = 54;
        int volume = Mute.INSTANCE.Java_VolumeController_getVolume();

        int i = volume;
        while(i != newVolume) {
            if (i + 10 < newVolume) {
                Mute.INSTANCE.Java_VolumeController_increaseBy10Percentage();
                i += 10;
            }
            else if (i - 10 > newVolume) {
                Mute.INSTANCE.Java_VolumeController_decreaseBy10Percentage();
                i -= 10;
            } else if (i < newVolume) {
                Mute.INSTANCE.Java_VolumeController_increaseBy1Percentage();
                i++;
            }
            else if (i > newVolume) {
                Mute.INSTANCE.Java_VolumeController_decreaseBy1Percentage();
                i--;
            }
        }

        System.out.println("Old Volume: " + volume);
        System.out.println("New Volume: " + Mute.INSTANCE.Java_VolumeController_getVolume());
    }
}