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

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * {@code Diff} objects hold values of differences.
 *
 * @author Marcio Branquinho Dutra
 */
@XmlRootElement
public class Diff implements Serializable {

    /**
     * The index where the difference comes.
     */
    private Integer offset;
    /**
     * The quantity of different elements starting from offset.
     */
    private Integer length;

    public Diff() {

    }

    public Diff(Integer offset, Integer length) {
        this.offset = offset;
        this.length = length;
    }

    public Integer getOffset() {
        return offset;
    }

    @XmlElement
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLength() {
        return length;
    }

    @XmlElement
    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Diff)) {
            return false;
        }
        Diff other = (Diff) object;
        if ((this.offset == null && other.offset != null) || (this.offset != null && !this.offset.equals(other.offset))) {
            return false;
        }
        if ((this.length == null && other.length != null) || (this.length != null && !this.length.equals(other.length))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.offset);
        hash = 41 * hash + Objects.hashCode(this.length);
        return hash;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("offset=").append(offset)
                .append(" length=").append(length).toString();
    }
}
