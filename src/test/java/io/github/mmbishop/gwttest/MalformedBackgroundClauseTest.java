package io.github.mmbishop.gwttest;

import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.core.exceptions.MalformedTestException;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MalformedBackgroundClauseTest {

    private final GwtTest<Context> gwt = new GwtTest<>(Context.class);

    @Test
    void malformed_test_detected_when_AND_precedes_GIVEN_in_background_clause() {
        assertThrows(MalformedTestException.class, () -> {
            gwt.background()
                    .and(something)
                    .given(something);
        });
    }

    private final GwtFunction<Context> something = context -> {};
}
