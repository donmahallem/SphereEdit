package don.sphere.exif;

/**
 * Created by Don on 31/07/2015.
 */
public class StringIFDEntry extends IFDEntry<String> {
    public StringIFDEntry(int tag, int dataFormat, int dataComponents, String dataBlock) {
        super(tag, dataFormat, dataComponents, dataBlock);
    }
}
