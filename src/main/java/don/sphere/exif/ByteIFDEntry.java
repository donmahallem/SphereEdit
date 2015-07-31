package don.sphere.exif;

/**
 * Created by Don on 31/07/2015.
 */
public class ByteIFDEntry extends IFDEntry<Byte> {
    public ByteIFDEntry(int tag, int dataFormat, int dataComponents, Byte dataBlock) {
        super(tag, dataFormat, dataComponents, dataBlock);
    }
}
