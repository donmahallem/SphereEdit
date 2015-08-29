package don.sphere;

import rx.Observable;
import rx.functions.Func1;

import java.io.File;
import java.util.List;


/**
 * Created by Don on 30.07.2015.
 */
public class Util {
    public static void sortDirectories(List<Section> sectionList) {

    }


    public static Observable<File> listFiles(File f){
        return Observable.just(f)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        System.out.println("tt1");
                        return listFilesa(file);
                    }
                });
    }
   private static Observable<File> listFilesa(File f) {
        if(f.isDirectory()){
            return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    System.out.println("tt2");
                    return Util.listFilesa(file);
                }
            });
        }
       System.out.println("Just file");
        return Observable.just(f);
    }
}
