package don.sphere;

import don.sphere.exif.IFDEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 30.07.2015.
 */
public class SectionExif extends Section {
    private ByteOrder mByteOrder;
    private List<IFDEntry> mIFDEntryList = new ArrayList<IFDEntry>();


    public ByteOrder getByteOrder() {
        return mByteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        mByteOrder = byteOrder;
    }

    @Override
    public String toString() {
        return "SectionExif{" +
                "mAlign=" + mByteOrder +
                ", mIFDEntryList=" + mIFDEntryList +
                '}';
    }

    public void addIFDEntry(IFDEntry ifdEntry) {
        this.mIFDEntryList.add(ifdEntry);
    }

    public enum ByteOrder {
        INTEL, MOTOROLA, ELSE
    }

}
