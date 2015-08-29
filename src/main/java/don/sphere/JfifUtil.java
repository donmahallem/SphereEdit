package don.sphere;

import okio.Buffer;
import okio.ByteString;

import java.io.IOException;

/**
 * Created by Don on 30.07.2015.
 */
public class JfifUtil extends SectionParser<SectionJfif> {

    public static final ByteString JFIF_HEADER = ByteString.encodeUtf8("JFIF\0");
    public static final int JFIF_HEADER_SIZE = JFIF_HEADER.size();

    @Override
    public boolean canParse(int marker, int length, Buffer data) {
        if (marker != JpegParser.JPG_APP0)
            return false;
        return data.indexOfElement(JFIF_HEADER) == 0;
    }

    @Override
    public SectionJfif parse(int marker, int length, Buffer sourceData) throws IOException {
        sourceData.skip(JFIF_HEADER_SIZE);
        Log.d("jfif parse", "DATA: " + sourceData.snapshot().hex());
        SectionJfif sectionJfif = new SectionJfif();
        sectionJfif.setVersion(SectionJfif.Version.create(sourceData.readByte(), sourceData.readByte()));
        sectionJfif.setDensityUnits(sourceData.readByte());
        sectionJfif.setDensityX(sourceData.readShort());
        sectionJfif.setDensityY(sourceData.readShort());
        sectionJfif.setThumbnailX(sourceData.readByte());
        sectionJfif.setThumbnailY(sourceData.readByte());
        final int thumbSize = sectionJfif.getThumbnailX() * sectionJfif.getThumbnailY();
        sectionJfif.setThumbnailData(sourceData.readByteArray(thumbSize));
        return sectionJfif;
    }

}
