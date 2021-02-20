package rob.volume.quarkus.controller;

import rob.volume.quarkus.utils.VolumeController;
import rob.volume.quarkus.utils.VolumeControllerUtils;
import rob.volume.quarkus.domain.Response;
import rob.volume.quarkus.domain.VolumeInfoBody;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}