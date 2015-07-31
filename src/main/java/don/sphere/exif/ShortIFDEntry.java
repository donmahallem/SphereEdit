package don.sphere.exif;

/**
 * Created by Don on 31/07/2015.
 */
public class ShortIFDEntry extends IFDEntry<Short> {

    public ShortIFDEntry(int tag, int dataFormat, int dataComponents, Short dataBlock) {
        super(tag, dataFormat, dataComponents, dataBlock);
    }
}
