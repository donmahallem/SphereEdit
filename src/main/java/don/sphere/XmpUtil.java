package don.sphere;

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import okio.ByteString;

/**
 * Created by Don on 30.07.2015.
 */
public class XmpUtil extends SectionParser<SectionXmp> {

    public static final ByteString XMP_HEADER = ByteString.encodeUtf8("http://ns.adobe.com/xap/1.0/\0");
    public static final int XMP_HEADER_SIZE = 29;

    public static SectionXmp parse(ByteString data) {
        try {
            XMPMeta meta = XMPMetaFactory.parseFromBuffer(data.substring(XMP_HEADER_SIZE).toByteArray());
            return new SectionXmp(meta);
        } catch (XMPException e) {
            return null;
        }
    }

    @Override
    public boolean canParse(int marker, int length, ByteString data) {
        if (marker != JpegParser.JPG_APP0)
            return false;
        return data.rangeEquals(0, XMP_HEADER, 0, XMP_HEADER_SIZE);
    }

    @Override
    public SectionXmp parse(int marker, int length, ByteString data) {
        return new SectionXmp(null);
    }
}
