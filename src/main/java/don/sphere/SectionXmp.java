package don.sphere;

import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.impl.XMPMetaImpl;

/**
 * Created by Don on 30.07.2015.
 */
public class SectionXmp extends Section {

    private XMPMeta mXMPMeta;

    SectionXmp(XMPMeta xmpMeta) {
        if (xmpMeta == null)
            this.mXMPMeta = new XMPMetaImpl();
        else
            this.mXMPMeta = xmpMeta;
    }

    public XMPMeta getXMPMeta() {
        return mXMPMeta;
    }

    public void setXMPMeta(XMPMeta XMPMeta) {
        mXMPMeta = XMPMeta;
    }

    @Override
    public String toString() {
        return "SectionXmp{" +
                "mXMPMeta=" + mXMPMeta.dumpObject() +
                '}';
    }
}
