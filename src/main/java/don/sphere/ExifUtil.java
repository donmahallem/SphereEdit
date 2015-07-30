package don.sphere;

import okio.ByteString;

/**
 * Created by Don on 30.07.2015.
 */
public class ExifUtil {
    public static final ByteString EXIF_HEADER = ByteString.encodeUtf8("Exif\0\0");
    public static final int EXIF_HEADER_SIZE = EXIF_HEADER.size();

    public static SectionExif parse(ByteString byteString) {
        return new SectionExif();
    }

    @Override
    public String toString() {
        return "ExifUtil{}";
    }
}
