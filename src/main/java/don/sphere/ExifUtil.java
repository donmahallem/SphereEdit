package don.sphere;

import don.sphere.exif.IFDEntry;
import okio.Buffer;
import okio.ByteString;

import java.io.EOFException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Don on 30.07.2015.
 */
public class ExifUtil extends SectionParser<SectionExif> {
    public static final ByteString EXIF_HEADER = ByteString.encodeUtf8("Exif\0\0");
    public static final int EXIF_HEADER_SIZE = 6;
    public static final ByteString ALIGN_INTEL = ByteString.decodeHex("49492A00");
    public static final ByteString ALIGN_MOTOROLA = ByteString.decodeHex("4d4d002A");

    @Override
    public String toString() {
        return "ExifUtil{}";
    }

    @Override
    public boolean canParse(int marker, int length, Buffer data) {
        if (marker != JpegParser.JPG_APP1)
            return false;
        Log.d("EXIF", "MAYBE PARSE - " + data.indexOfElement(EXIF_HEADER));
        return data.indexOfElement(EXIF_HEADER) == 0;
    }

    @Override
    public SectionExif parse(int marker, int length, Buffer data) throws ParseException, IOException {
        data.skip(EXIF_HEADER_SIZE);
        SectionExif sectionExif = new SectionExif();
        final ByteString alignemnt = data.readByteString(4);
        if (alignemnt.rangeEquals(0, ALIGN_INTEL, 0, 4))
            sectionExif.setByteOrder(SectionExif.ByteOrder.INTEL);
        else if (alignemnt.rangeEquals(0, ALIGN_MOTOROLA, 0, 4))
            sectionExif.setByteOrder(SectionExif.ByteOrder.MOTOROLA);
        else
            throw new ParseException("Couldnt parse exif section. Unknown ALignment", 2);
        if (sectionExif.getByteOrder() == SectionExif.ByteOrder.INTEL)
            parseIntelByteOrder(sectionExif, data);
        else
            parseIntelByteOrder(sectionExif, data);
        return sectionExif;
    }

    private void parseMotorolaByteOrder(SectionExif exif, Buffer source) {

    }

    private void parseIntelByteOrder(SectionExif exif, Buffer source) throws EOFException {
        //Read first IFD OFFSET AND SKIP
        final int offset = source.readIntLe() - 8;
        source.skip(offset);
        while (true) {
            final int ifdEntries = source.readShortLe();
            Log.d("IFD BLOCK", "START==== " + ifdEntries + " Entries");
            int tag, dataFormat, dataComponents, dataBlock;
            for (int i = 0; i < ifdEntries; i++) {
                //PARSE IFD ENTRY
                tag = source.readShortLe();
                dataFormat = source.readShortLe();
                dataComponents = source.readIntLe();
                Buffer offsetBuffer = new Buffer();
                if (IFDEntry.tagBytes(dataFormat) * dataComponents > 4) {
                    source.copyTo(offsetBuffer, (exif.getByteOrder() == SectionExif.ByteOrder.INTEL) ? source.readIntLe() : source.readInt(), dataComponents);
                } else {
                    source.copyTo(offsetBuffer, 0, 4);
                }
                exif.addIFDEntry(IFDEntry.create(exif.getByteOrder(), tag, dataFormat, dataComponents, offsetBuffer));
                offsetBuffer.close();

            }
            int ifdBlockOffset = source.readIntLe();
            if (ifdBlockOffset == 0) {
                //No further ifd block
                Log.d("IFD BLOCK", "LAST====");
                break;
            } else {
                Log.d("IFD BLOCK", "NEXT BLOCK OFFSET: " + ifdBlockOffset);
                source.skip(ifdBlockOffset);
            }
            Log.d("IFD BLOCK", "END====");
        }
    }
}
