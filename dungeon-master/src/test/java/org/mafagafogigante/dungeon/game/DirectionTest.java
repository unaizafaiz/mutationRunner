package org.mafagafogigante.dungeon.game;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class DirectionTest {

    @Test
    public void checkInvert() throws Exception {
        Assert.assertEquals(Direction.UP, Direction.DOWN.invert());
    }

    @Test
    public void checkDirections() throws Exception {
        Assert.assertFalse(Direction.UP.equals(Direction.DOWN));
    }


}