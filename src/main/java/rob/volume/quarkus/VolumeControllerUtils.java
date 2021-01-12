package rob.volume.quarkus;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import rob.volume.quarkus.domain.Constants;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VolumeControllerUtils {

    public static void checkLibrary() {
        try {
            if (Files.exists(Paths.get("./" + Constants.LIBRARY_NAME))) {
                System.out.println("SUCCES --> DLL ready to load !");
            } else {
                loadLib();
                System.out.println("SUCCES --> Successfully load DLL " + Constants.LIBRARY_NAME + "!");
            }
        } catch (Exception e) {
            System.out.println("ERROR --> Try to load dll from embedded package!");
            e.printStackTrace();
        }
    }

    private static void loadLib() throws Exception {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.LIBRARY_NAME);
            File fileOut = new File( "./" + Constants.LIBRARY_NAME);
            OutputStream out = FileUtils.openOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();
            System.load(fileOut.getAbsolutePath());
        } catch (Exception e) {
            throw new Exception("Failed to load required DLL", e);
        }
    }
}