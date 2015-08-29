package don.sphere;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 30.07.2015.
 */
public class Sections {


    private List<Section> mDirectories = new ArrayList<Section>();

    public <T extends Section> T getOrCreate(Class<T> clazz) {
        try {
            return (T) clazz.getConstructors()[0].newInstance(2);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Sections{" +
                "mDirectories=" + mDirectories +
                '}';
    }

    public <T extends Section> List<Section> getDirectoriesByType(Class<T> clazz) {
        /*List<Directory> directories=new ArrayList<>();
        for(Directory directory:this.mDirectories){
            if(directory instanceof clazz.class){
                directories.add(directory);
            }
        }
        return directories;*/
        return null;
    }

    void add(Section section) {
        section.setId(this.mDirectories.size());
        this.mDirectories.add(section);
    }
}
