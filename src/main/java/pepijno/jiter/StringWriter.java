package pepijno.jiter;

public class StringWriter<T> extends Writer<T, String> {
    protected StringWriter(T value, String log) {
        super(value, String::concat, log);
    }

    public static <T> StringWriter<T> of(final T value, final String init) {
        return new StringWriter<>(value, init);
    }
}
