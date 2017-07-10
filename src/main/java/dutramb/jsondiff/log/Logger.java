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
package dutramb.jsondiff.log;

import java.util.Optional;
import java.util.logging.Handler;
import java.util.logging.Level;

/**
 * The {@code Logger} class is responsible to log. It offers easy access to a
 * {@code java.util.logging.Logger} instance and formatted messages.
 *
 * @author Marcio Branquinho Dutra <mdutra at gmail dot com>
 */
public class Logger {

    public static final Optional<String> level;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("dutramb.jsondiff");

    static {
        level = Optional.ofNullable(System.getenv("JSONDIFF_LOG_LEVEL"));
        for (Handler h : logger.getHandlers()) {
//            h.setLevel(Level.parse(level.orElse("INFO")));
            h.setLevel(Level.FINE);
        }
    }

    public static void info(String title, Class c, Object... obj) {
        logger.log(Level.INFO, composeMessage(c, title, obj));
    }

    public static void warn(String title, Class c, Throwable e, Object... obj) {
        logger.log(Level.WARNING, composeMessage(c, title, obj), e);
    }

    public static void severe(String title, Class c, Throwable e, Object... obj) {
        logger.log(Level.SEVERE, composeMessage(c, title, obj), e);
    }

    public static void debug(String title, Class c, Object... obj) {
        logger.log(Level.FINE, composeMessage(c, title, obj));
    }

    public static void debug(String title, Class c, Throwable e, Object... obj) {
        logger.log(Level.FINE, composeMessage(c, title, obj), e);
    }

    /**
     * Returns a formatted log message. Information appear in the order: (Log
     * level): (called Class) MESSAGE:[title] obj1:[obj1 value] obj2:[obj2
     * value] ...
     * <p>
     * Example:
     * <blockquote><pre>
     * "INFO: dutramb.jsondiff.Main MESSAGE:[JSON Diff app is available at http://localhost:8080/json-diff/...]"
     * "INFO: dutramb.jsondiff.ws.DiffWS MESSAGE:[Request on left] id:[1] input:[id=1 value=YWMvZGMhIQ==]"
     * </pre></blockquote>
     *
     * @param c The class where it was called from.
     * @param title A Message Title
     * @param obj List of objects to record on log. To a better format, toggle a
     * String with a title and the object with corresponding value. Example:
     * ..., "id", id, "value", value, ...
     * @return Formatted message
     */
    private static String composeMessage(Class c, String title, Object... obj) {
        StringBuilder strb = new StringBuilder();
        strb.append(c.getCanonicalName());
        strb.append(" ");
        if (title != null && !title.trim().equals("")) {
            strb.append("MESSAGE:[");
            strb.append(title);
            strb.append("] ");
        }
        for (int i = 0; i < obj.length; i = i + 2) {
            strb.append(obj[i]);
            strb.append(":[");
            if ((i + 1) < obj.length) {
                strb.append(obj[i + 1]);
            }
            strb.append("] ");
        }
        return strb.toString();
    }
}
