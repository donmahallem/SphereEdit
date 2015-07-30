package don.sphere;

import okio.ByteString;

/**
 * Created by Don on 30.07.2015.
 */
public class JfifUtil extends SectionParser<SectionJfif> {

    public static final ByteString JFIF_HEADER = ByteString.encodeUtf8("JFIF\0");
    public static final int JFIF_HEADER_SIZE = JFIF_HEADER.size();

    @Override
    public boolean canParse(int marker, int length, ByteString data) {
        if (marker != JpegParser.JPG_APP0)
            return false;
        return data.rangeEquals(0, JFIF_HEADER, 0, JFIF_HEADER_SIZE);
    }

    @Override
    public SectionJfif parse(int marker, int length, ByteString sourceData) {
        ByteString data = sourceData.substring(JFIF_HEADER_SIZE);
        SectionJfif sectionJfif = new SectionJfif();
        sectionJfif.setVersion(new SectionJfif.Version(data.getByte(0), data.getByte(1)));
        sectionJfif.setDensityUnits(data.getByte(2) & 0xFF);
        sectionJfif.setDensityX(readShort(data.getByte(3), data.getByte(4)));
        sectionJfif.setDensityY(readShort(data.getByte(5), data.getByte(6)));
        sectionJfif.setThumbnailX(data.getByte((7) & 0xFF));
        sectionJfif.setThumbnailY(data.getByte((8) & 0xFF));
        final int thumbSize = sectionJfif.getThumbnailX() * sectionJfif.getThumbnailY();
        sectionJfif.setThumbnailData(data.substring(9, 9 + thumbSize).toByteArray());
        return sectionJfif;
    }

}
