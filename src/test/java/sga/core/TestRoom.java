package sga.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRoom {
    @Test
    public void testCreation() {
        Room room = new Room("C01", "Sala 1", 25);

        assertEquals("C01", room.id());
        assertEquals("Sala 1", room.name());
        assertEquals((Integer) 25, room.capacity());
    }
}