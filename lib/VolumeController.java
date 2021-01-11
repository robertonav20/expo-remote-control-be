public class VolumeController {
    synchronized private native static boolean muteOn();
    synchronized private native static boolean muteOff();
    synchronized private native static boolean increaseBy10Percentage();
    synchronized private native static boolean decreaseBy10Percentage();
    synchronized private native static boolean increaseBy1Percentage();
    synchronized private native static boolean decreaseBy1Percentage();
    synchronized private native static boolean changeVolume(int newValue);
    synchronized private native static int getVolume();

    public static void main(String[] args) {
        VolumeController.changeVolume(new Integer(50));
    }

    static {
        System.loadLibrary("VolumeController");
    }
}