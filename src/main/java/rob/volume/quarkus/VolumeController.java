package rob.volume.quarkus;

import javax.sound.sampled.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Path("/volume/controller")
public class VolumeController {

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/mute")
    @Produces(MediaType.TEXT_PLAIN)
    public String mute() {
        try {
            Mixer.Info[] infos = AudioSystem.getMixerInfo();
            for (Mixer.Info info : infos) {
                Mixer mixer = AudioSystem.getMixer(info);
                System.out.println("Mixer: " + info.getName());

                Line.Info[] lineInfos = mixer.getTargetLineInfo();
                for (Line.Info lineInfo : lineInfos) {
                    Line line = null;
                    boolean opened = true;

                    System.out.println("Line: " + lineInfo);
                    try {
                        line = mixer.getLine(lineInfo);
                        opened = line.isOpen() || line instanceof Clip;
                        if (!opened) {
                            line.open();
                        }
                        FloatControl volCtrl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                        volCtrl.setValue((float) 0L);
                        System.out.println("Value: " + volCtrl.getValue());
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException iaEx) {
                        //System.out.println("  -!-  " + iaEx);
                    } finally {
                        if (line != null && !opened) {
                            line.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed trying to find LINE_IN VOLUME control: exception = " + e);
        }

        return "Volume Changed!";
    }

    @GET
    @Path("/mute2")
    @Produces(MediaType.TEXT_PLAIN)
    public String mute2() {
        try {
            Mixer.Info[] infos = AudioSystem.getMixerInfo();
            for (Mixer.Info info : infos) {
                Mixer mixer = AudioSystem.getMixer(info);
                System.out.println("Mixer: " + info.getName());
                if (mixer.isLineSupported(Port.Info.SPEAKER)) {
                    try (Port port = (Port) mixer.getLine(Port.Info.SPEAKER)) {
                        System.out.println("Line: " + port.getLineInfo());
                        port.open();
                        if (port.isControlSupported(FloatControl.Type.VOLUME)) {
                            FloatControl volume = (FloatControl) port.getControl(FloatControl.Type.VOLUME);
                            volume.setValue(0L);
                            System.out.println("Value: " + volume.getValue());
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        return "Volume Changed!";
    }

    @GET
    @Path("/mute3")
    @Produces(MediaType.TEXT_PLAIN)
    public String mute3() {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : infos) {
            System.out.println("Mixer name: " + mixerInfo.getName());
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] lineInfos = mixer.getSourceLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                System.out.println("Line: " + lineInfo);
                try {
                    Line line = mixer.getLine(lineInfo);
                    line.open();
                    FloatControl volCtrl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                    volCtrl.setValue(0L);
                    System.out.println("Value: " + volCtrl.getValue());
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException iaEx) {
                    System.out.println(iaEx);
                }
            }
        }

        return "Volume Changed!";
    }
}