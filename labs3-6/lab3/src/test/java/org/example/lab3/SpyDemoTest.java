package org.example.lab3;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SpyDemoTest {

    @Test
    void shouldDemonstrateSpy() {
        List<String> realList = new ArrayList<>();
        List<String> spyList = spy(realList);

        spyList.add("Spring");
        spyList.add("Boot");

        verify(spyList, times(1)).add("Spring");
        verify(spyList, times(1)).add("Boot");

        assertEquals(2, spyList.size());
        assertEquals("Spring", spyList.get(0));

        // Переопределение поведения
        when(spyList.size()).thenReturn(100);
        assertEquals(100, spyList.size());
    }
}