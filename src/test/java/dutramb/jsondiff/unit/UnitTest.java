/*
 * The MIT License
 *
 * Copyright 2017 Marcio Branquinho Dutra <mdutra at gmail dot com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dutramb.jsondiff.unit;

import dutramb.jsondiff.logic.Comparator;
import dutramb.jsondiff.model.Diff;
import dutramb.jsondiff.model.Input;
import dutramb.jsondiff.model.Output;
import dutramb.jsondiff.model.type.Result;
import dutramb.jsondiff.repository.MapRepository;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Provides unit tests.
 * 
 * @author Marcio Branquinho Dutra <mdutra at gmail dot com>
 */
public class UnitTest {

    /**
     * Tests if storage left and replacement of data with the same id is working.
     */
    @Test
    public void inputLeftShouldBeReplaced() {
        Integer id = 1;
        String originalValue = "YWMvZGMhIQ==";
        String replacedValue = "YWMvZGM=";
        
        // original value
        MapRepository.getInstance().insertLeftInput(new Input(id, originalValue));
        Input inputLeft = MapRepository.getInstance().getLeftInput(id);
        assertNotNull("Null input", inputLeft);
        assertEquals("Wrong input id", id, inputLeft.getId());
        assertEquals("Wrong value", originalValue, inputLeft.getValue());
        
        // replaced value (same id)
        MapRepository.getInstance().insertLeftInput(new Input(id, replacedValue));
        Input inputLeftReplaced = MapRepository.getInstance().getLeftInput(id);
        assertNotNull("Null replaced input", inputLeftReplaced);
        assertEquals("Wrong replaced input id", id, inputLeftReplaced.getId());
        assertEquals("Wrong replaced value", replacedValue, inputLeftReplaced.getValue());
    }

    /**
     * Tests if storage right and replacement of data with the same id is working.
     */
    @Test
    public void inputRightShouldBeReplaced() {
        Integer id = 1;
        String originalValue = "YWMvZGMhIQ==";
        String replacedValue = "YWMvZGM=";
        
        // original value
        MapRepository.getInstance().insertRightInput(new Input(id, originalValue));
        Input inputRight = MapRepository.getInstance().getRightInput(id);
        assertNotNull("Null input", inputRight);
        assertEquals("Wrong input id", id, inputRight.getId());
        assertEquals("Wrong value", originalValue, inputRight.getValue());
        
        // replaced value (same id)
        MapRepository.getInstance().insertRightInput(new Input(1, replacedValue));
        Input inputRightReplaced = MapRepository.getInstance().getRightInput(id);
        assertNotNull("Null replaced input", inputRightReplaced);
        assertEquals("Wrong replaced input id", id, inputRightReplaced.getId());
        assertEquals("Wrong replaced value", replacedValue, inputRightReplaced.getValue());
    }


    /**
     * Tests equal hypothesis.
     */
    @Test
    public void resultShouldBeEqual() {
        Integer id = 1;
        MapRepository.getInstance().insertLeftInput(new Input(id, "YWMvZGM="));
        MapRepository.getInstance().insertRightInput(new Input(id, "YWMvZGM="));
        Output output = new Comparator().compare(id);
        assertNotNull("Null output", output);
        assertEquals("Wrong output id", id, output.getId());
        assertNull("Not null diff list", output.getDiffList());
        assertEquals("Wrong result type", Result.EQUAL, output.getResult());
    }

    /**
     * Tests different sizes hypothesis.
     */
    @Test
    public void resultShouldBeDifferentSize() {
        Integer id = 2;
        MapRepository.getInstance().insertLeftInput(new Input(id, "YWMvZGM="));
        MapRepository.getInstance().insertRightInput(new Input(id, "YWMvZGMhIQ=="));
        Output output = new Comparator().compare(id);
        assertNotNull("Null output", output);
        assertEquals("Wrong output id", id, output.getId());
        assertNull("Not null diff list", output.getDiffList());
        assertEquals("Wrong result type", Result.DIFFERENT_SIZE, output.getResult());
    }

    
    /**
     * Tests different data hypothesis with different characters in the beginning of 
     * the sequence.
     */
    @Test
    public void resultShouldBeDifferentInTheBeginning() {
        Integer id = 3;
        MapRepository.getInstance().insertLeftInput(new Input (id, "YWMvZGM="));
        MapRepository.getInstance().insertRightInput(new Input(id, "QWMvZGM="));
        
        Object[] expectedList = {new Diff(0, 1)};
        
        Output output = new Comparator().compare(id);
        assertNotNull("Null output", output);
        assertEquals("Wrong output id", id, output.getId());
        assertEquals("Wrong result type", Result.DIFFERENT, output.getResult());
        assertNotNull("Null diff list", output.getDiffList());
        assertArrayEquals("Wrong diff (offset+len)", expectedList, output.getDiffList().toArray());
    }
    
    
    /**
     * Tests different data hypothesis with different characters in the middle of 
     * the sequence.
     */
    @Test
    public void resultShouldBeDifferentInTheMiddle() {
        Integer id = 3;
        MapRepository.getInstance().insertLeftInput(new Input (id, "YWMvZGM="));
        MapRepository.getInstance().insertRightInput(new Input(id, "YUMhZGM="));
        
        Object[] expectedList = {new Diff(1, 1), new Diff(3, 1), };
        
        Output output = new Comparator().compare(id);
        assertNotNull("Null output", output);
        assertEquals("Wrong output id", id, output.getId());
        assertEquals("Wrong result type", Result.DIFFERENT, output.getResult());
        assertNotNull("Null diff list", output.getDiffList());
        assertArrayEquals("Wrong diff (offset+len)", expectedList, output.getDiffList().toArray());
    }
    
    
    /**
     * Tests different data hypothesis with different characters in the end of 
     * the sequence.
     */
    @Test
    public void resultShouldBeDifferentInTheEnd() {
        Integer id = 3;
        MapRepository.getInstance().insertLeftInput(new Input (id, "YmxhY2sgc2FiYmF0aA=="));
        MapRepository.getInstance().insertRightInput(new Input(id, "YmxhQ0sgc2FiYkFUSA=="));
        
        Object[] expectedList = {new Diff(4, 2), new Diff(13, 1), new Diff(15, 2)};
        
        Output output = new Comparator().compare(id);
        assertNotNull("Null output", output);
        assertEquals("Wrong output id", id, output.getId());
        assertEquals("Wrong result type", Result.DIFFERENT, output.getResult());
        assertNotNull("Null diff list", output.getDiffList());
        assertArrayEquals("Wrong diff (offset+len)", expectedList, output.getDiffList().toArray());
    }
    
    
    /**
     * Tests different data hypothesis with all different characters in both sequences.
     */
    @Test
    public void resultShouldBeCompletelyDifferent() {
        Integer id = 3;
        MapRepository.getInstance().insertLeftInput(new Input (id, "YWNkYwo="));
        MapRepository.getInstance().insertRightInput(new Input(id, "emVwbGVk"));
        
        Object[] expectedList = {new Diff(0, 8)};
        
        Output output = new Comparator().compare(id);
        assertNotNull("Null output", output);
        assertEquals("Wrong output id", id, output.getId());
        assertEquals("Wrong result type", Result.DIFFERENT, output.getResult());
        assertNotNull("Null diff list", output.getDiffList());
        assertArrayEquals("Wrong diff (offset+len)", expectedList, output.getDiffList().toArray());
    }
        
}
