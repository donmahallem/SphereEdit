package don.sphere;

import okio.ByteString;

import java.text.ParseException;

/**
 * Created by Don on 30.07.2015.
 */
public class ExifUtil extends SectionParser<SectionExif> {
    public static final ByteString EXIF_HEADER = ByteString.encodeUtf8("Exif\0\0");
    public static final int EXIF_HEADER_SIZE = 6;
    public static final ByteString ALIGN_INTEL = ByteString.decodeHex("49492A00");
    public static final ByteString ALIGN_MOTOROLA = ByteString.decodeHex("4d4d002A");
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
    public SectionExif parse(int marker, int length, ByteString data) throws ParseException {
        SectionExif sectionExif = new SectionExif();
        Log.d("parseExif", data.hex());
        if (data.rangeEquals(6, ALIGN_INTEL, 0, 4))
            sectionExif.setAlign(SectionExif.Align.INTEL);
        else if (data.rangeEquals(6, ALIGN_MOTOROLA, 0, 4))
            sectionExif.setAlign(SectionExif.Align.MOTOROLA);
        else
            throw new ParseException("Couldnt parse exif section. Unknown ALignment", 2);
        int offset = 6 + parseInt(data.substring(10, 14).toByteArray());
        while (true) {
            Log.d("IFD BLOCK", "START====");
            int ifdEntries = parseInt(data.substring(offset, offset + 2).toByteArray());
            offset += 2;
            Log.d("Entries", "" + ifdEntries);
            int tag, dataFormat, dataComponents, dataBlock;
            for (int i = 0; i < ifdEntries; i++) {
                //PARSE IFD ENTRY
                Log.d("ENTRY", " DATA: " + data.substring(offset, offset + 12).hex());
                tag = parseInt(data.substring(offset, offset + 2).toByteArray());
                dataFormat = parseInt(data.substring(offset + 2, offset + 4).toByteArray());
                dataComponents = parseInt(data.substring(offset + 4, offset + 8).toByteArray());
                if (SectionExif.IFDEntry.tagBytes(tag) * dataComponents > 4) {
                    //DATA PART IS OFFSET
                    Log.d("OFFSET", "YES format: " + dataFormat);
                    int entryOffset = parseInt(data.substring(8, 12).toByteArray());
                    sectionExif.addIFDEntry(new SectionExif.IFDEntry(tag, dataFormat, dataComponents, data.substring(6 + entryOffset, 6 + entryOffset + dataComponents).toByteArray()));
                } else {
                    //DATA IS NO OFFSET
                    Log.d("OFFSET", "NO format: " + dataFormat);
                    sectionExif.addIFDEntry(SectionExif.IFDEntry.create(tag, dataFormat, dataComponents, data.substring(offset + 8, offset + 12).toByteArray()));
                }
                offset += 12;
            }
            int ifdBlockOffset = parseInt(data.substring(offset, offset + 4).toByteArray());
            offset += 4 + ifdBlockOffset;
            if (ifdBlockOffset == 0) {
                //No further ifd block
                Log.d("IFD BLOCK", "LAST====");
                break;
            }
            Log.d("IFD BLOCK", "END====");
        }
        return sectionExif;
    }
}
