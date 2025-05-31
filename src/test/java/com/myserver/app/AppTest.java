package com.myserver.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void testAdd() {
        App app = new App();
        assertEquals(3, app.add(2,1));
    }
}
