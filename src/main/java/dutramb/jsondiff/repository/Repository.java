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

import dutramb.jsondiff.model.Input;

/**
 * Provides common methods to store and access data.
 *
 * @author Marcio Branquinho Dutra
 */
public interface Repository {

    /**
     * Returns the input data that came from left endpoint
     * and is related to the id.
     *
     * @param id
     * @return
     */
    public Input getLeftInput(Integer id);

    /**
     * Stores input data from left endpoint. 
     * 
     * @param input 
     */
    public void insertLeftInput(Input input);

    /**
     * Returns the input data that came from right endpoint
     * and is related to the id.
     *
     * @param id
     * @return
     */
    public Input getRightInput(Integer id);
    
    /**
     * Stores input data from right endpoint. 
     * 
     * @param input 
     */
    public void insertRightInput(Input input);
}
