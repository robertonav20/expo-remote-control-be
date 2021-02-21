package rob.volume.quarkus.controller;

import rob.volume.quarkus.domain.KeyboardBody;
import rob.volume.quarkus.domain.Response;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Arrays;

@Path("/")
public class KeyboardRestController {

    private Robot robot;

    public KeyboardRestController() throws AWTException {
        this.robot = new Robot();
    }

    @POST
    @Path("/keyboard/controller")
    @Produces(MediaType.APPLICATION_JSON)
    public Response trigger(KeyboardBody keyboardBody) {
        Response response = new Response();
        try {
            if (keyboardBody.getKeyEvents() != null && keyboardBody.getKeyEvents().length > 0) {
                Arrays.stream(keyboardBody.getKeyEvents())
                        .forEach(k -> {
                            Field f;
                            try {
                                f = KeyEvent.class.getField(k);
                                int keyEvent = f.getInt(null);
                                robot.keyPress(keyEvent);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                Arrays.stream(keyboardBody.getKeyEvents())
                        .forEach(k -> {
                            Field f;
                            try {
                                f = KeyEvent.class.getField(k);
                                int keyEvent = f.getInt(null);
                                robot.keyRelease(keyEvent);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (Exception e) {
            System.out.println("Failed trying to trigger keyboard input = " + e);
            response.setCode(-1);
            response.setMessage("Failed trying to trigger keyboard input = " + e);
            return response;
        }

        response.setMessage("Keyboard event triggered!");
        response.setCode(0);
        return response;
    }
}
