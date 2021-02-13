package rob.volume.quarkus;

import rob.volume.quarkus.domain.MouseBody;
import rob.volume.quarkus.domain.MouseInfoBody;
import rob.volume.quarkus.domain.Response;
import rob.volume.quarkus.domain.VolumeInfoBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.awt.event.InputEvent;

@Path("/")
public class VolumeRestController {

    public VolumeRestController() {
        VolumeControllerUtils.checkLibrary();
    }

    @GET
    @Path("/alive")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Yes! Im alive!";
    }

    @PUT
    @Path("/volume/controller/muteOn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response muteOn() {
        Response response = new Response();

        try {
            VolumeController.INSTANCE.Java_VolumeController_muteOn();
        } catch (Exception e) {
            System.out.println("Failed trying to enabled mute: exception =" + e);

            response.setCode(01);
            response.setMessage("Failed trying to enabled mute: exception =" + e);

            return response;
        }

        response.setCode(00);
        response.setMessage("Mute Enabled");

        return response;
    }

    @PUT
    @Path("/volume/controller/muteOff")
    @Produces(MediaType.APPLICATION_JSON)
    public Response muteOff() {
        Response response = new Response();

        try {
            VolumeController.INSTANCE.Java_VolumeController_muteOff();
        } catch (Exception e) {
            System.out.println("Failed trying to disabled mute: exception = " + e);

            response.setCode(02);
            response.setMessage("Failed trying to disabled mute: exception =" + e);

            return response;
        }

        response.setCode(00);
        response.setMessage("Mute Disabled");

        return response;
    }

    @PUT
    @Path("/volume/controller/changeVolume")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeVolume(VolumeInfoBody volumeInfoBody) {
        Response response = new Response();

        try {
            int newVolume = volumeInfoBody.getVolume();
            int i = VolumeController.INSTANCE.Java_VolumeController_getVolume();
            while (i != newVolume) {
                if (i + 10 < newVolume) {
                    VolumeController.INSTANCE.Java_VolumeController_increaseBy10Percentage();
                    i += 10;
                } else if (i - 10 > newVolume) {
                    VolumeController.INSTANCE.Java_VolumeController_decreaseBy10Percentage();
                    i -= 10;
                } else if (i < newVolume) {
                    VolumeController.INSTANCE.Java_VolumeController_increaseBy1Percentage();
                    i++;
                } else if (i > newVolume) {
                    VolumeController.INSTANCE.Java_VolumeController_decreaseBy1Percentage();
                    i--;
                }
            }
        } catch (Exception e) {
            System.out.println("Failed trying to changing volume value: exception = " + e);

            response.setCode(03);
            response.setMessage("Failed trying to changing mute: exception =" + e);

            return response;
        }

        response.setCode(00);
        response.setMessage("Volume Changed");

        return response;
    }

    @GET
    @Path("/volume/controller/getVolume")
    @Produces(MediaType.APPLICATION_JSON)
    public VolumeInfoBody getVolume() {
        VolumeInfoBody response = new VolumeInfoBody();

        try {
            response.setVolume(VolumeController.INSTANCE.Java_VolumeController_getVolume());
        } catch (Exception e) {
            System.out.println("Failed trying to changing volume value: exception = " + e);
            response.setVolume(-1);
            return response;
        }

        return response;
    }

    @POST
    @Path("/mouse/controller")
    @Produces(MediaType.APPLICATION_JSON)
    public Response move(MouseBody mouseBody) {
        Response response = new Response();
        try {
            Robot robot = new Robot();
            if (mouseBody.getXCoordinate() != null && mouseBody.getYCoordinate() != null) {
                Point point = MouseInfo.getPointerInfo().getLocation();
                double xCurrent = point.getX();
                double yCurrent = point.getY();

                double xNew = xCurrent + mouseBody.getXCoordinate() * mouseBody.getPressure();
                double yNew = yCurrent + mouseBody.getYCoordinate() * mouseBody.getPressure();
                robot.mouseMove((int) Math.round(xNew), (int) Math.round(yNew));
            }

            if (mouseBody.getLeftClick() != null) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }

            if (mouseBody.getRightClick() != null) {
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            }
        } catch (Exception e) {
            System.out.println("Failed trying to changing coordinates of mouse exception = " + e);
            response.setCode(-1);
            response.setMessage("Failed trying to changing coordinates of mouse exception = " + e);
            return response;
        }

        response.setCode(0);
        return response;
    }

    @GET
    @Path("/mouse/controller/location")
    @Produces(MediaType.APPLICATION_JSON)
    public MouseInfoBody moveMouse() {
        MouseInfoBody mouseInfoBody = new MouseInfoBody();
        try {
            Point point = MouseInfo.getPointerInfo().getLocation();
            double xCoordinates = point.getX();
            double yCoordinates = point.getY();

            mouseInfoBody.setX((int) xCoordinates);
            mouseInfoBody.setY((int) yCoordinates);
        } catch (Exception e) {
            System.out.println("Failed trying to get coordinates of mouse exception = " + e);
            mouseInfoBody.setCode(-1);
            mouseInfoBody.setMessage("Failed trying to get coordinates of mouse exception = " + e);
            return mouseInfoBody;
        }

        mouseInfoBody.setCode(0);
        return mouseInfoBody;
    }
}