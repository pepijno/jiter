package pepijno.jiter;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Writer<T, R> {
    private final T value;
    private final R log;
    private final BiFunction<? super R, ? super R, ? extends R> append;

    protected Writer(final T value, final BiFunction<? super R, ? super R, ? extends R> append, final R log) {
        Objects.requireNonNull(append);
        this.value = value;
        this.log = log;
        this.append = append;
    }

    public T value() {
        return this.value;
    }

    public R log() {
        return this.log;
    }

    public <T1> Writer<T1, R> map(final Function<? super T, ? extends T1> f, final R log) {
        final R newLog = this.append.apply(this.log, log);
        final T1 newVal = f.apply(this.value);
        return new Writer<>(newVal, this.append, newLog);
    }

    public <T1> Writer<T1, R> flatMap(final Function<? super T, ? extends Writer<T1, R>> f) {
        final Writer<T1, R> functionResult = f.apply(this.value);
        return new Writer<>(functionResult.value, this.append, this.append.apply(this.log, functionResult.log));
    }

    public static <T, R> Writer<T, R> of(final T value, final R init, final BiFunction<? super R, ? super R, ? extends R> append) {
        return new Writer<>(value, append, init);
    }
}
