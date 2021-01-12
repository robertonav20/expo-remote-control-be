public class VolumeController {
    private static final String LIBRARY_NAME = "VolumeController.dll";

    static {
        try {
            System.loadLibrary(LIBRARY_NAME);
        } catch (UnsatisfiedLinkError e) {
            System.out.println("ERROR --> Try to load dll from embedded package!");
        }
    }

    synchronized protected native static boolean muteOn();

    synchronized protected native static boolean muteOff();

    synchronized protected native static boolean increaseBy10Percentage();

    synchronized protected native static boolean decreaseBy10Percentage();

    synchronized protected native static boolean increaseBy1Percentage();

    synchronized protected native static boolean decreaseBy1Percentage();

    synchronized protected native static boolean changeVolume(int newValue);

    synchronized protected native static int getVolume();

    public static void main(String[] args) {
        System.out.println(VolumeController.getVolume());
    }
}