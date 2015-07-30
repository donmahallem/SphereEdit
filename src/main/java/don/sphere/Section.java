package don.sphere;

/**
 * Created by Don on 30.07.2015.
 */
public class Section {
    private int mId;

    public int getId() {
        return this.mId;
    }

    void setId(int id) {
        this.mId = id;
    }

    @Override
    public String toString() {
        return "Section{" +
                "mId=" + mId +
                '}';
    }
}
