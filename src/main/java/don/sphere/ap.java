package don.sphere;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by Don on 30.07.2015.
 */
public class ap {


    public static void main(String... args) throws IOException {

        File folder = new File("C:\\Users\\Don\\Desktop\\New folder (3)\\");

        for (File file : folder.listFiles(new JpegFilter())) {
            Log.d("File: " + file.getAbsolutePath());
            Sections sections = JpegParser.readFile(file);
            Log.d("sections: ", sections.toString());
        }
    }

    private static final class JpegFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith("jpg");
        }
    }
}
