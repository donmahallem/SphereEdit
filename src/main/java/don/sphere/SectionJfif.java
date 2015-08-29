package don.sphere;

import java.util.Arrays;

/**
 * Created by Don on 30.07.2015.
 */
public class SectionJfif extends Section {

    private Version mVersion;

    private int mDensityUnits;
    private int mDensityX, mDensityY;
    private int mThumbnailX, mThumbnailY;
    private byte[] mThumbnailData;

    public Version getVersion() {
        return mVersion;
    }

    public void setVersion(Version version) {
        mVersion = version;
    }

    public int getDensityUnits() {
        return mDensityUnits;
    }

    public void setDensityUnits(int densityUnits) {
        mDensityUnits = densityUnits;
    }

    public int getDensityX() {
        return mDensityX;
    }

    public void setDensityX(int densityX) {
        mDensityX = densityX;
    }

    public int getDensityY() {
        return mDensityY;
    }

    public void setDensityY(int densityY) {
        mDensityY = densityY;
    }

    public int getThumbnailX() {
        return mThumbnailX;
    }

    public void setThumbnailX(int thumbnailX) {
        mThumbnailX = thumbnailX;
    }

    public int getThumbnailY() {
        return mThumbnailY;
    }

    public void setThumbnailY(int thumbnailY) {
        mThumbnailY = thumbnailY;
    }

    public byte[] getThumbnailData() {
        return mThumbnailData;
    }

    public void setThumbnailData(byte[] thumbnailData) {
        mThumbnailData = thumbnailData;
    }

    @Override
    public String toString() {
        return "SectionJfif{" +
                "mVersion=" + mVersion +
                ", mDensityUnits=" + mDensityUnits +
                ", mDensityX=" + mDensityX +
                ", mDensityY=" + mDensityY +
                ", mThumbnailX=" + mThumbnailX +
                ", mThumbnailY=" + mThumbnailY +
                ", mThumbnailData=" + Arrays.toString(mThumbnailData) +
                '}';
    }

    public static class Version {
        public final int MAJOR, MINOR;

        public Version(byte major, byte minor) {
            this((int) major & 0xFF, (int) minor & 0xFF);
        }

        public Version(int major, int minor) {
            this.MAJOR = major;
            this.MINOR = minor;
        }

        public static Version create(byte b, byte b1) {
            return new Version(b & 0xFF, b1 & 0xFF);
        }

        @Override
        public String toString() {
            return "Version{" +
                    MAJOR + "." + MINOR +
                    '}';
        }
    }
}
