package don.sphere;

import okio.ByteString;

/**
 * Created by Don on 30.07.2015.
 */
public abstract class SectionParser<T extends Section> {

    public static int readShort(ByteString substring) {
        return readShort(substring.toByteArray());
    }

    public static int readShort(byte... bytes) {
        return (bytes[0] & 0xFF) << 8 + bytes[1] & 0xFF;
    }

    public abstract boolean canParse(int marker, int length, ByteString data);

    public abstract T parse(int marker, int length, ByteString data);
}
