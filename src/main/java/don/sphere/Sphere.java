package don.sphere;

import don.sphere.gui.Picker;

import javax.swing.*;

/**
 * Created by Don on 21/03/2015.
 */
public class Sphere {
    public static void main(String... args) {/*
        Log.d("Starting");
        File file = new File("C:\\Users\\Don\\Desktop\\New folder (3)\\PANO_20150317_170744.jpg");
        File fileIn2 = new File("C:\\Users\\Don\\Desktop\\New folder (3)\\PANO_20150317_170744_2.jpg");
        File fileOut = new File("C:\\Users\\Don\\Desktop\\New folder (3)\\PANO_20150317_170744_3.jpg");
        Log.d("File '" + file.getAbsolutePath() + "' exists: " + file.exists());
        InputStream inputStream = new FileInputStream(file);
        JpegXmpInputStream jpegXmpInputStream=new JpegXmpInputStream(inputStream);
        XMPMeta data = XMPMetaFactory.parse(jpegXmpInputStream);
        jpegXmpInputStream.close();
        inputStream.close();
        if (data != null)
            System.out.println("Data not null");
        System.out.println("DUMP: " + data.dumpObject());
        OutputStream outputStream=new FileOutputStream(fileOut);
        InputStream inputStream2 = new FileInputStream(fileIn2);
        XmpUtil.writeXMPMeta(inputStream2,outputStream,data);

        outputStream.close();
        inputStream.close();*/
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Log.e("System Look and feel couldn't be initialized");
        }
        new Picker();
    }
}
