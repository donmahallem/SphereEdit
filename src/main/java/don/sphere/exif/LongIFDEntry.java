package don.sphere.exif;

/**
 * Created by Don on 31/07/2015.
 */
public class LongIFDEntry extends IFDEntry<Long> {

    public LongIFDEntry(int tag, int dataFormat, int dataComponents, Long dataBlock) {
        super(tag, dataFormat, dataComponents, dataBlock);
    }
}
