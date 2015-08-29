package don.sphere.exif;

import don.sphere.SectionExif;
import okio.Buffer;

import java.io.EOFException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Don on 31/07/2015.
 */
public class IFDEntry<DATA> {
    private final static int FORMAT_UNSIGNED_BYTE = 1, FORMAT_ASCII_STRING = 2, FORMAT_UNSIGNED_SHORT = 3,
            FORMAT_UNSIGNED_LONG = 4, FORMAT_UNSIGNED_RATIONAL = 5, FORMAT_SIGNED_BYTE = 6, FORMAT_UNDEFINED = 7,
            FORMAT_SIGNED_SHORT = 8, FORMAT_SIGNED_LONG = 9, FORMAT_SIGNED_RATIONAL = 10, FORMAT_SIGNED_FLOAT = 11, FORMAT_DOUBLE_FLOAT = 12;
    private int mTag;
    private int mDataFormat;
    private int mDataComponents;
    private DATA mData;

    public IFDEntry(int tag, int dataFormat, int dataComponents, DATA dataBlock) {
        this.mTag = tag;
        this.mDataFormat = dataFormat;
        this.mDataComponents = dataComponents;
        this.mData = dataBlock;
    }

    public static int tagBytes(int tag) {
        switch (tag) {
            case 1:
            case 2:
            case 6:
            case 7:
                return 1;
            case 3:
            case 8:
                return 2;
            case 4:
            case 9:
            case 11:
                return 4;
            case 5:
            case 10:
            case 12:
                return 8;
            default:
                return 1;
            //throw new IllegalArgumentException("Tag "+tag+" is unknown");
        }
    }

    public static IFDEntry create(final SectionExif.ByteOrder byteOrder, int tag, int dataFormat, int dataComponents, Buffer bytes) throws EOFException {
        if (dataFormat == FORMAT_UNSIGNED_BYTE) {
            return new ByteIFDEntry(tag, dataFormat, dataComponents, bytes.readByteArray(dataComponents));
        } else if (dataFormat == FORMAT_ASCII_STRING) {
            return new StringIFDEntry(tag, dataFormat, dataComponents, bytes.readString(dataComponents, StandardCharsets.US_ASCII));
        } else if (dataFormat == FORMAT_UNSIGNED_SHORT) {
            return new ShortIFDEntry(tag, dataFormat, dataComponents, (byteOrder == SectionExif.ByteOrder.INTEL) ? bytes.readShortLe() : bytes.readShort());
        } else if (dataFormat == FORMAT_UNSIGNED_LONG) {
            return new LongIFDEntry(tag, dataFormat, dataComponents, (byteOrder == SectionExif.ByteOrder.INTEL) ? bytes.readLongLe() : bytes.readLong());
        } else if (dataFormat == FORMAT_UNSIGNED_RATIONAL) {
            return new DoubleIFDEntry(tag, dataFormat, dataComponents, (double) ((byteOrder == SectionExif.ByteOrder.INTEL) ? (bytes.readShortLe() / bytes.readShortLe()) : (bytes.readShort() / bytes.readShort())));
        } else if (dataFormat == FORMAT_SIGNED_BYTE) {
            return new ByteIFDEntry(tag, dataFormat, dataComponents, bytes.readByteArray(dataComponents));
        } else
            return null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "mTag=" + mTag +
                ", mDataFormat=" + mDataFormat +
                ", mDataComponents=" + mDataComponents +
                ", mData=" + mData +
                '}';
    }

    public DATA getData() {
        return this.mData;
    }

    public void setData(DATA data) {
        this.mData = data;
    }

    public enum Format {
        UNSIGNED_BYTE(1), ASCII_STRING(2), UNSIGNED_SHORT(3), UNSIGNED_LONG(4), UNSIGNED_RATIONAL(5), SIGNED_BYTE(6),
        UNDEFINED(7), SIGNED_SHORT(8), SIGNED_LONG(9), SIGNED_RATIONAL(10), SIGNED_FLOAT(11), DOUBLE_FLOAT(12);

        public final int VAL;

        Format(int val) {
            this.VAL = val;
        }
    }
}