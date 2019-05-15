package pepijno.jiter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringWriterTest {
    @Test
    public void canCreateStringWriterFromOfMethod() {
        StringWriter<Integer> stringWriter = StringWriter.of(3, "init");
        assertThat(stringWriter).isInstanceOf(StringWriter.class);
    }
}
