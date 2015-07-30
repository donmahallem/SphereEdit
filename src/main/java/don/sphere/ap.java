package don.sphere;

import java.io.File;
import java.io.IOException;

/**
 * Created by Don on 30.07.2015.
 */
public class ap {

    public static void main(String... args) throws IOException {
        Sections sections = JpegParser.readFile(new File("D:\\Don\\Desktop\\IMG_20150722_144044_stitch.jpg"));

        System.out.println(sections.toString());
    }
}
