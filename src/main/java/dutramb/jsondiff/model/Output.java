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
package dutramb.jsondiff.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import dutramb.jsondiff.model.type.Result;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the result of a comparison.
 * 
 * @author Marcio Branquinho Dutra
 */
@XmlRootElement
public class Output implements Serializable {

    /**
     * Id of compared data.
     */
    private Integer id;
    /**
     * The title of comparison's result.
     */
    private Result result;
    /**
     * The list of differences between the data was compared.
     * It is provided only if Result type is DIFFERENT. 
     * It holds the offsets and lengths of all differences.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Diff> diffList;

    public Output() {
    }

    public Output(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    @XmlElement
    public void setResult(Result result) {
        this.result = result;
    }

    public List<Diff> getDiffList() {
        return diffList;
    }

    @XmlElement
    public void setDiffList(List<Diff> diffList) {
        this.diffList = diffList;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("id=").append(id) 
                .append(" result=").append(result) 
                .append(" diffList=[").append(diffList).append("]").toString();
    }
}
