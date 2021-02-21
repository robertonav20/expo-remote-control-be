package rob.volume.quarkus.controller;

import rob.volume.quarkus.domain.MouseBody;
import rob.volume.quarkus.domain.MouseInfoBody;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.awt.event.InputEvent;

@Path("/")
public class MouseRestController {

    private Robot robot;

    public MouseRestController() throws AWTException {
        this.robot = new Robot();
    }

    @POST
    @Path("/mouse/controller")
    @Produces(MediaType.APPLICATION_JSON)
    public MouseInfoBody move(MouseBody mouseBody) {
        MouseInfoBody response = new MouseInfoBody();
        try {
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

            Point point = MouseInfo.getPointerInfo().getLocation();
            double xCoordinates = point.getX();
            double yCoordinates = point.getY();

            response.setX((int) xCoordinates);
            response.setY((int) yCoordinates);

        } catch (Exception e) {
            System.out.println("Failed trying to changing coordinates of mouse exception = " + e);
            response.setCode(-1);
            response.setMessage("Failed trying to changing coordinates of mouse exception = " + e);
            return response;
        }

        response.setMessage("Mouse position changed!");
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
