/**
 * MIT License
 *
 * Copyright (c) [2017] [Marcio Branquinho Dutra]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dutramb.jsondiff.logic;

import dutramb.jsondiff.log.Logger;
import dutramb.jsondiff.model.Diff;
import dutramb.jsondiff.model.Input;
import dutramb.jsondiff.model.Output;
import dutramb.jsondiff.model.type.Result;
import dutramb.jsondiff.repository.MapRepository;
import java.util.LinkedList;
import java.util.List;

/**
 * Compares two base64 data sequences according to an informed id.
 * 
 * @author Marcio Branquinho Dutra
 */
public class Comparator {

    /**
     * Compares two data sequence according to the informed id.
     * It compares only if both input (left and right) is found for the same id.
     * 
     * @param id
     * @return <p>Null if both or at least one input (left or right) are null.
     * <p>Output with all comparison data.
     */
    public Output compare(Integer id) {

        Output output = null;

        Input inputLeft = MapRepository.getInstance().getLeftInput(id);
        Input inputRight = MapRepository.getInstance().getRightInput(id);

        Logger.debug("comparing", this.getClass(), "id", id, "left", inputLeft, "right", inputRight);
        
        if (inputLeft != null && inputRight != null) {
            output = new Output(id);
            if (inputLeft.getValue().equals(inputRight.getValue())) {
                output.setResult(Result.EQUAL);
            } else if (inputLeft.getValue().length() != inputRight.getValue().length()) {
                output.setResult(Result.DIFFERENT_SIZE);
            } else {
                output.setResult(Result.DIFFERENT);
                output.setDiffList(processDiff(inputLeft, inputRight));
            }
        }

        Logger.debug("comparison result", this.getClass(), "output", output);
        
        return output;
    }

    
    /**
     * Compares two sequences of base64 data and computes the offsets and 
     * lengths of the differences.
     * 
     * @param inputLeft Data came from left endpoint.
     * @param inputRight Data came from right endpoint.
     * @return a list of {@code Diff} with offsets and lengths of differences.
     */
    public List<Diff> processDiff(Input inputLeft, Input inputRight) {
        List<Diff> diffList = new LinkedList<>();

        int length = 0;
        int offset = -1;
        for (int i = 0; i <= inputLeft.getValue().length(); i++) {
            if (i < inputLeft.getValue().length()
                    && inputLeft.getValue().charAt(i) != inputRight.getValue().charAt(i)) {
                length++;
                if (offset < 0) {
                    offset = i;
                }
            } else if (offset != -1) {
                diffList.add(new Diff(offset, length));
                length = 0;
                offset = -1;
            }
        }

        return diffList;
    }
    
}
