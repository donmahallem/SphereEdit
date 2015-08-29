package don.sphere;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
        }

        Sections sections = JpegParser.readFile(new File("C:\\Users\\Don\\Desktop\\New folder (3)\\PANO_20150317_170953.jpg"));
        Log.d("sections: ", sections.toString());

        Util.listFiles(new File("")).subscribe(new Action1<File>() {
            @Override
            public void call(File file) {
                System.out.println("Has: " + file.getAbsolutePath());
            }
        });
/*
        File fi=new File("\\");
        Observable.from(fi.listFiles()).subscribe(new Action1<File>() {
            @Override
            public void call(File file) {
                System.out.println("Test: "+file.getAbsolutePath());

            }
        });*/
    }

    private static final class JpegFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith("jpg");
        }
    }
}
