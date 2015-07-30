package don.sphere;

import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 30.07.2015.
 */
public class JpegParser {
    public static final String GPANO_NAMESPACE = "http://ns.google.com/photos/1.0/panorama/";
    public static final String GPANO_PREFIX = "GPano";
    public static final int JPG_APP0 = 0xE0;
    private static final ByteString HEADER_PNG = ByteString.decodeHex("89504e470d0a1a0a");
    private static final int JPG_APP1 = 0xE1;
    private static final int JPG_EOI = 0xD9;
    private static final int JPG_SOI = 0xD8;
    private static final int JPG_SOS = 0xDA;
    private static final int JPG_MARKER = 0xFF;
    private static final String TAG = "XmpUtil";
    private static final int MAX_XMP_BUFFER_SIZE = 65502;
    private static List<SectionParser> mParser = new ArrayList<SectionParser>();

    static {
        mParser.add(new JfifUtil());
    }

    public static Sections readFile(File file) throws IOException {
        return readFile(new FileInputStream(file));
    }

    public static Sections readFile(InputStream inputStream) throws IOException {
        BufferedSource imgSource = Okio.buffer(Okio.source(inputStream));
        Sections data = parseJpg(imgSource);
        imgSource.close();
        return data;
    }

    private static Sections parseJpg(BufferedSource imgSource) throws IOException {
        return parseJpgSections(imgSource);
    }

    private static Sections parseJpgSections(BufferedSource source) throws IOException {
        if ((source.readByte() & 0xFF) != JPG_MARKER || (source.readByte() & 0xFF) != JPG_SOI) {
            Log.d(TAG, "No valid jpg header.");
            return null;
        }
        Sections sections = new Sections();
        int marker = -1;
        while (!source.exhausted()) {
            //Skip trailing FF marker
            while ((marker = (source.readByte() & 0xFF)) == JPG_MARKER) ;
            if (marker == -1) {
                return null;
            }
            if (marker == JPG_SOS) {
                return sections;
            }
            final int length = source.readShort();
            if (length < 0) {
                return null;
            }
            Section section = parseJpgSection(source.readByteString(length - 2), marker, length - 2);
            if (section != null)
                sections.add(section);
        }
        return null;
    }

    private static Section parseJpgSection(ByteString source, int marker, int length) throws EOFException {
        for (SectionParser parser : mParser) {
            if (parser.canParse(marker, length, source)) {
                return parser.parse(marker, length, source);
            }
        }
        return new Section();
    }

    private static void readPngChunk(BufferedSource imgSource) throws IOException {
        while (true) {
            Buffer chunk = new Buffer();

            // Each chunk is a length, type, data, and CRC offset.
            int length = imgSource.readInt();
            String type = imgSource.readUtf8(4);
            imgSource.readFully(chunk, length);
            int crc = imgSource.readInt();

            decodeChunk(type, chunk);
            if (type.equals("IEND")) break;
        }
    }

    private static void decodeChunk(String type, Buffer chunk) {
        if (type.equals("IHDR")) {
            int width = chunk.readInt();
            int height = chunk.readInt();
            System.out.printf("%08x: %s %d x %d%n", chunk.size(), type, width, height);
        } else if (type.equals("IDAT")) {
            System.out.printf("%08x: %s%n", chunk.size(), type);
        } else {
            System.out.printf("%08x: %s%n", chunk.size(), type);
        }
    }
}
