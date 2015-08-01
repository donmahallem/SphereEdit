package don.sphere;

import okio.Buffer;
import okio.ByteString;

/**
 * Created by Don on 30.07.2015.
 */
public class XmpUtil extends SectionParser<SectionXmp> {

    public static final ByteString XMP_HEADER = ByteString.encodeUtf8("http://ns.adobe.com/xap/1.0/\0");
    public static final int XMP_HEADER_SIZE = 29;

    @Override
    public boolean canParse(int marker, int length, Buffer data) {
        if (marker != JpegParser.JPG_APP1)
            return false;
        return data.indexOfElement(XMP_HEADER) == 0;
    }

    @Override
    public SectionXmp parse(int marker, int length, Buffer data) {
        return new SectionXmp(null);
    }
}
