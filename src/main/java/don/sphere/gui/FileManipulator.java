package don.sphere.gui;

import com.adobe.xmp.XMPMeta;
import don.sphere.XmpUtil;

import java.io.*;

/**
 * Created by Don on 23/03/2015.
 */
public class FileManipulator {
    private File mFileOriginal, mFileModified, mFileExport;

    public void reset() {
        this.mFileExport = null;
        this.mFileModified = null;
        this.mFileOriginal = null;
    }

    public void setFileOriginal(File fileOriginal) {
        if (fileOriginal == this.mFileModified)
            throw new IllegalArgumentException("Original and modified File cant be the same");
        if (fileOriginal == this.mFileExport)
            throw new IllegalArgumentException("Original and export File cant be the same");
        this.mFileOriginal = fileOriginal;
    }

    public void setFileModified(File fileModified) {
        if (fileModified == this.mFileExport)
            throw new IllegalArgumentException("Modified file and export cant be the same");
        if (fileModified == this.mFileOriginal)
            throw new IllegalArgumentException("Modified file and original cant be the same");
        this.mFileModified = fileModified;
    }

    public void setFileExport(File fileExport) {
        if (fileExport == this.mFileModified)
            throw new IllegalArgumentException("Modified file and export cant be the same");
        if (fileExport == this.mFileOriginal)
            throw new IllegalArgumentException("Export file and original cant be the same");
        this.mFileExport = fileExport;
    }

    public final void exportFile(final File exportFile) throws Exception {
        if (this.mFileOriginal == null)
            throw new Exception("Original File must be set");
        if (this.mFileModified == null)
            throw new Exception("Modified File must be set");
        InputStream inputStreamOriginal = null, inputStreamModified = null;
        OutputStream outputStreamExport = null;
        try {
            inputStreamOriginal = new FileInputStream(this.mFileOriginal);
            inputStreamModified = new FileInputStream(this.mFileModified);
            XMPMeta data = XmpUtil.extractXMPMeta(inputStreamOriginal);
            inputStreamOriginal.close();
            if (data != null)
                System.out.println("Data not null");
            System.out.println("DUMP: " + data.dumpObject());
            OutputStream outputStream = new FileOutputStream(exportFile);
            XmpUtil.writeXMPMeta(inputStreamModified, outputStream, data);
        } catch (Exception xmpException) {

        } finally {
            if (outputStreamExport != null)
                outputStreamExport.close();
            if (inputStreamModified != null)
                inputStreamModified.close();
            if (inputStreamOriginal != null)
                inputStreamOriginal.close();
        }
    }
}
