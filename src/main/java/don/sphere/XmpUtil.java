package don.sphere;

<<<<<<< HEAD

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.options.SerializeOptions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to read/write xmp from a jpeg image file. It only supports jpeg
 * image format, and doesn't support extended xmp now.
 * To use it:
 * XMPMeta xmpMeta = XmpUtil.extractOrCreateXMPMeta(filename);
 * xmpMeta.setProperty(PanoConstants.GOOGLE_PANO_NAMESPACE, "property_name", "value");
 * XmpUtil.writeXMPMeta(filename, xmpMeta);
 * <p/>
 * Or if you don't care the existing XMP meta data in image file:
 * XMPMeta xmpMeta = XmpUtil.createXMPMeta();
 * xmpMeta.setPropertyBoolean(PanoConstants.GOOGLE_PANO_NAMESPACE, "bool_property_name", "true");
 * XmpUtil.writeXMPMeta(filename, xmpMeta);
 */
public class XmpUtil {
    private static final String TAG = "XmpUtil";
    private static final int XMP_HEADER_SIZE = 29;
    private static final String XMP_HEADER = "http://ns.adobe.com/xap/1.0/\0";
    private static final int MAX_XMP_BUFFER_SIZE = 65502;

    private static final String GOOGLE_PANO_NAMESPACE = "http://ns.google.com/photos/1.0/panorama/";
    private static final String PANO_PREFIX = "GPano";

    private static final int M_SOI = 0xd8; // File start marker.
    private static final int M_APP1 = 0xe1; // Marker for Exif or XMP.
    private static final int M_SOS = 0xda; // Image data marker.

    private XmpUtil() {
    }

    /**
     * Extracts XMPMeta from JPEG image file.
     *
     * @param filename JPEG image file name.
     * @return Extracted XMPMeta or null.
     */
    public static XMPMeta extractXMPMeta(String filename) {
        if (!filename.toLowerCase().endsWith(".jpg")
                && !filename.toLowerCase().endsWith(".jpeg")) {
            Log.d(TAG, "XMP parse: only jpeg file is supported");
            return null;
        }

        try {
            return extractXMPMeta(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Could not read file: " + filename, e);
            return null;
        }
    }

    /**
     * Extracts XMPMeta from a JPEG image file stream.
     *
     * @param is the input stream containing the JPEG image file.
     * @return Extracted XMPMeta or null.
     */
    public static XMPMeta extractXMPMeta(InputStream is) {
        List<Section> sections = parse(is, true);
        if (sections == null) {
            return null;
        }
        // Now we don't support extended xmp.
        for (Section section : sections) {
            if (hasXMPHeader(section.data)) {
                int end = getXMPContentEnd(section.data);
                byte[] buffer = new byte[end - XMP_HEADER_SIZE];
                System.arraycopy(
                        section.data, XMP_HEADER_SIZE, buffer, 0, buffer.length);
                try {
                    XMPMeta result = XMPMetaFactory.parseFromBuffer(buffer);
                    return result;
                } catch (XMPException e) {
                    Log.e(TAG, "XMP parse error", e);
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Creates a new XMPMeta.
     */
    public static XMPMeta createXMPMeta() {
        return XMPMetaFactory.create();
    }

    /**
     * Tries to extract XMP meta from image file first, if failed, create one.
     */
    public static XMPMeta extractOrCreateXMPMeta(String filename) {
        XMPMeta meta = extractXMPMeta(filename);
        return meta == null ? createXMPMeta() : meta;
    }

    /**
     * Writes the XMPMeta to the jpeg image file.
     */
    public static boolean writeXMPMeta(String filename, XMPMeta meta) {
        if (!filename.toLowerCase().endsWith(".jpg")
                && !filename.toLowerCase().endsWith(".jpeg")) {
            Log.d(TAG, "XMP parse: only jpeg file is supported");
            return false;
        }
        List<Section> sections = null;
        try {
            sections = parse(new FileInputStream(filename), false);
            sections = insertXMPSection(sections, meta);
            if (sections == null) {
                return false;
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Could not read file: " + filename, e);
            return false;
        }
        FileOutputStream os = null;
        try {
            // Overwrite the image file with the new meta data.
            os = new FileOutputStream(filename);
            writeJpegFile(os, sections);
        } catch (IOException e) {
            Log.e(TAG, "Write file failed:" + filename, e);
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        }
        return true;
    }

    /**
     * Updates a jpeg file from inputStream with XMPMeta to outputStream.
     */
    public static boolean writeXMPMeta(InputStream inputStream, OutputStream outputStream,
                                       XMPMeta meta) {
        List<Section> sections = parse(inputStream, false);
        sections = insertXMPSection(sections, meta);
        if (sections == null) {
            return false;
        }
        try {
            // Overwrite the image file with the new meta data.
            writeJpegFile(outputStream, sections);
        } catch (IOException e) {
            Log.e(TAG, "Write to stream failed", e);
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        }
        return true;
    }

    /**
     * Write a list of sections to a Jpeg file.
     */
    private static void writeJpegFile(OutputStream os, List<Section> sections)
            throws IOException {
        // Writes the jpeg file header.
        os.write(0xff);
        os.write(M_SOI);
        for (Section section : sections) {
            os.write(0xff);
            os.write(section.marker);
            if (section.length > 0) {
                // It's not the image data.
                int lh = section.length >> 8;
                int ll = section.length & 0xff;
                os.write(lh);
                os.write(ll);
            }
            os.write(section.data);
        }
    }
=======
import okio.Buffer;
import okio.ByteString;

/**
 * Created by Don on 30.07.2015.
 */
public class XmpUtil extends SectionParser<SectionXmp> {
>>>>>>> reattach

    public static final ByteString XMP_HEADER = ByteString.encodeUtf8("http://ns.adobe.com/xap/1.0/\0");
    public static final int XMP_HEADER_SIZE = 29;

    @Override
    public boolean canParse(int marker, int length, Buffer data) {
        if (marker != JpegParser.JPG_APP1)
            return false;
        return data.indexOfElement(XMP_HEADER) == 0;
    }

<<<<<<< HEAD
    /**
     * Parses the jpeg image file. If readMetaOnly is true, only keeps the Exif
     * and XMP sections (with marker M_APP1) and ignore others; otherwise, keep
     * all sections. The last section with image data will have -1 length.
     *
     * @param is           Input image data stream.
     * @param readMetaOnly Whether only reads the metadata in jpg.
     * @return The parse result.
     */
    private static List<Section> parse(InputStream is, boolean readMetaOnly) {
        try {
            if (is.read() != 0xff || is.read() != M_SOI) {
                return null;
            }
            List<Section> sections = new ArrayList<Section>();
            int c;
            while ((c = is.read()) != -1) {
                if (c != 0xff) {
                    return null;
                }
                // Skip padding bytes.
                while ((c = is.read()) == 0xff) {
                }
                if (c == -1) {
                    return null;
                }
                int marker = c;
                if (marker == M_SOS) {
                    // M_SOS indicates the image data will follow and no metadata after
                    // that, so read all data at one time.
                    if (!readMetaOnly) {
                        Section section = new Section();
                        section.marker = marker;
                        section.length = -1;
                        section.data = new byte[is.available()];
                        is.read(section.data, 0, section.data.length);
                        sections.add(section);
                    }
                    return sections;
                }
                int lh = is.read();
                int ll = is.read();
                if (lh == -1 || ll == -1) {
                    return null;
                }
                int length = lh << 8 | ll;
                if (!readMetaOnly || c == M_APP1) {
                    Section section = new Section();
                    section.marker = marker;
                    section.length = length;
                    section.data = new byte[length - 2];
                    is.read(section.data, 0, length - 2);
                    sections.add(section);
                } else {
                    // Skip this section since all exif/xmp meta will be in M_APP1
                    // section.
                    is.skip(length - 2);
                }
            }
            return sections;
        } catch (IOException e) {
            Log.e(TAG, "Could not parse file.", e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        }
    }

    // Jpeg file is composed of many sections and image data. This class is used
    // to hold the section data from image file.
    private static class Section {
        public int marker;
        public int length;
        public byte[] data;
    }

    static {
        try {
            XMPMetaFactory.getSchemaRegistry().registerNamespace(
                    GOOGLE_PANO_NAMESPACE, PANO_PREFIX);
        } catch (XMPException e) {
            e.printStackTrace();
        }
    }
}
=======
    @Override
    public SectionXmp parse(int marker, int length, Buffer data) {
        return new SectionXmp(null);
    }
}
>>>>>>> reattach
