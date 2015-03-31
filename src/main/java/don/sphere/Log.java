package don.sphere;

/**
 * Created by Don on 21/03/2015.
 */
public class Log {
    private final static String TAG = "SphereFixer";

    public static void d(final String message) {
        d(TAG, message);
    }

    public static void d(final String tag, final String message) {
        System.out.println(tag + " : " + message);
    }

    public static void e(final String message) {
        e(TAG, message, null);
    }

    public static void e(final String tag, final String message, final Exception exception) {
        System.out.println("=== ERROR ===");
        System.out.println(tag + " : " + message);
        if (exception != null)
            exception.printStackTrace();
    }
}
