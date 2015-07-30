package don.sphere;

import okio.ByteString;

/**
 * Created by Don on 30.07.2015.
 */
public class ExifUtil extends SectionParser<SectionExif> {
    public static final ByteString EXIF_HEADER = ByteString.encodeUtf8("Exif\0\0");
    public static final int EXIF_HEADER_SIZE = EXIF_HEADER.size();

    public static SectionExif parse(ByteString byteString) {
        return new SectionExif();
    }

    @Override
    public String toString() {
        return "ExifUtil{}";
    }

    @Override
    public boolean canParse(int marker, int length, ByteString data) {
        if (marker != JpegParser.JPG_APP1)
            return false;
        return data.rangeEquals(0, EXIF_HEADER, 0, EXIF_HEADER_SIZE);
    }

    @Override
    public SectionExif parse(int marker, int length, ByteString data) {
        return new SectionExif();
    }
}
