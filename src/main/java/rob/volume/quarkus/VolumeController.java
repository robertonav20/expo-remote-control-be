package rob.volume.quarkus;

import com.sun.jna.Library;
import com.sun.jna.Native;
import rob.volume.quarkus.domain.Constants;

public interface VolumeController extends Library {

    VolumeController INSTANCE = Native.load(Constants.LIBRARY_NAME, VolumeController.class);

    boolean Java_VolumeController_muteOn();

    boolean Java_VolumeController_muteOff();

    boolean Java_VolumeController_changeVolume(int newValue);

    int Java_VolumeController_getVolume();

    boolean Java_VolumeController_increaseBy10Percentage();

    boolean Java_VolumeController_decreaseBy10Percentage();

    boolean Java_VolumeController_increaseBy1Percentage();

    boolean Java_VolumeController_decreaseBy1Percentage();
}