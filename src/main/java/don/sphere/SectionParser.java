package don.sphere;

import okio.Buffer;
import okio.ByteString;

import java.io.IOException;
import java.text.ParseException;

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

    public static int parseInt(byte... bytes) {
        int ret = 0;
        for (int i = 0; i < bytes.length && i < 4; i++) {
            ret += bytes[i] & 0xFF << i * 8;
        }
        return ret;
    }

    public static int parseUnsignedInt(byte... bytes) {
        int ret = 0;

        return ret;
    }

    public abstract boolean canParse(int marker, int length, Buffer data);

    public abstract T parse(int marker, int length, Buffer data) throws ParseException, IOException;
}
