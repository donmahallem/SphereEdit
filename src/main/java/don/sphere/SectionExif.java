package don.sphere;

import don.sphere.exif.IFDEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 30.07.2015.
 */
public class SectionExif extends Section {
    private Align mAlign;
    private List<IFDEntry> mIFDEntryList = new ArrayList<IFDEntry>();


    public Align getAlign() {
        return mAlign;
    }

    public void setAlign(Align align) {
        mAlign = align;
    }

    @Override
    public String toString() {
        return "SectionExif{" +
                "mAlign=" + mAlign +
                ", mIFDEntryList=" + mIFDEntryList +
                '}';
    }

    public void addIFDEntry(IFDEntry ifdEntry) {
        this.mIFDEntryList.add(ifdEntry);
    }

    public enum Align {
        INTEL, MOTOROLA, ELSE
    }

}
