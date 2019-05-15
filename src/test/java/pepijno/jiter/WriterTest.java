package pepijno.jiter;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WriterTest {
    @Test
    public void throwsNPEOnOfWithNullAppendMethod() {
        assertThatThrownBy(() -> Writer.of(3, "", null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void canCreateWriterFromOfMethod() {
        BiFunction<String, String, String> f = String::concat;
        Writer<Integer, String> writer = Writer.of(3, "", f);
        assertThat(writer).isInstanceOf(Writer.class);
    }

    @Test
    public void returnsValueOnValue() {
        BiFunction<String, String, String> f = String::concat;
        Writer<Integer, String> writer = Writer.of(3, "", f);
        assertThat(writer.value()).isEqualTo(3);
    }

    @Test
    public void returnsLogOnLog() {
        BiFunction<String, String, String> f = String::concat;
        Writer<Integer, String> writer = Writer.of(3, "init", f);
        assertThat(writer.log()).isEqualTo("init");
    }

    @Test
    public void appliesMethodToValueOnMap() {
        BiFunction<String, String, String> f = String::concat;
        Writer<Integer, String> writer = Writer.of(3, "init", f);
        Function<Integer, Integer> map = i -> i - 2;
        Writer<Integer, String> mapped = writer.map(map, "");
        assertThat(mapped.value()).isEqualTo(1);
    }

    @Test
    public void appliesMethodToLogOnMap() {
        BiFunction<String, String, String> f = String::concat;
        Writer<Integer, String> writer = Writer.of(3, "init ", f);
        Function<Integer, Integer> map = Function.identity();
        Writer<Integer, String> mapped = writer.map(map, "mapped identity");
        assertThat(mapped.log()).isEqualTo("init mapped identity");
    }

    @Test
    public void shouldBindValueOnFlatMap() {
        BiFunction<String, String, String> f = String::concat;
        Writer<Integer, String> writer = Writer.of(3, "init ", f);
        Function<Integer, Writer<Integer, String>> flatMap = x -> Writer.of(x * 2, "other", f);
        Writer<Integer, String> flatMapped = writer.flatMap(flatMap);
        assertThat(flatMapped.value()).isEqualTo(6);
    }

    @Test
    public void shouldBindLogsOnFlatMap() {
        BiFunction<String, String, String> f = String::concat;
        Writer<Integer, String> writer = Writer.of(3, "init ", f);
        Function<Integer, Writer<Integer, String>> flatMap = x -> Writer.of(x * 2, "other", f);
        Writer<Integer, String> flatMapped = writer.flatMap(flatMap);
        assertThat(flatMapped.log()).isEqualTo("init other");
    }
}
