class VolumeController {
    private native short muteOn();
    private native short muteOff();
    private native short changeVolume(float value);

    public static void main(String[] args) {
        new VolumeController().muteOn();
    }

    static {
        System.loadLibrary("VolumeController");
    }
}