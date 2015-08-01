package don.sphere.exif;

/**
 * Created by Don on 31/07/2015.
 */
public class DoubleIFDEntry extends IFDEntry<Double> {

    public DoubleIFDEntry(int tag, int dataFormat, int dataComponents, Double dataBlock) {
        super(tag, dataFormat, dataComponents, dataBlock);
    }
}
