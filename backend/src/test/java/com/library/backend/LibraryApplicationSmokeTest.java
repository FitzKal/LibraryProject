package com.library.backend;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.assertj.core.api.Assertions.assertThatCode;

class LibraryApplicationSmokeTest {

    @Test
    void main_shouldInvokeSpringApplicationRun_withoutException() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(LibraryApplication.class, new String[]{}))
                    .thenReturn(null);

            assertThatCode(() -> LibraryApplication.main(new String[]{}))
                    .doesNotThrowAnyException();

            mocked.verify(() -> SpringApplication.run(LibraryApplication.class, new String[]{}));
        }
    }
}
