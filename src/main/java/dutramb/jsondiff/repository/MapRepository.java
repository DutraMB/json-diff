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
package dutramb.jsondiff.repository;

import dutramb.jsondiff.log.Logger;
import dutramb.jsondiff.model.Input;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a simple storage on RAM to hold and offer data.
 * It is available in a singleton pattern, just to emulate a data 
 * storage while the service is running.
 * 
 * @author Marcio Branquinho Dutra
 */
public class MapRepository implements Repository{
    
    private static Repository instance;
    
    private Map<Integer, Input> leftStorage = new HashMap<>();
    private Map<Integer, Input> rightStorage = new HashMap<>();
    
    private MapRepository(){
    }
    
    public static Repository getInstance(){
        if(instance == null){
            instance = new MapRepository();
        }
        return instance;
    }
    
    @Override
    public Input getLeftInput(Integer id){
        Input left = leftStorage.get(id);
        Logger.debug("return left input", this.getClass(), "id", id, "left", left);
        return left;
    }
    
    @Override
    public void insertLeftInput(Input input) {
        Logger.debug("insert left input", this.getClass(), "input", input);
        leftStorage.put(input.getId(), input);
    }
    
    @Override
    public Input getRightInput(Integer id){
        Input right = rightStorage.get(id);
        Logger.debug("return right input", this.getClass(), "id", id, "right", right);
        return right;
    }
    
    @Override
    public void insertRightInput(Input input){
        Logger.debug("insert right input", this.getClass(), "input", input);
        rightStorage.put(input.getId(), input);
    }
}
