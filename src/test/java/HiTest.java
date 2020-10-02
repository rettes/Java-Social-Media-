package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.Hi;

public class HiTest {
    @Test

    public void testHello() {
        Hi hehe = new Hi();
        assertEquals("Hello", hehe.getHello());
    }
}