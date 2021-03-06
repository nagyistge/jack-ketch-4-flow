/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Alejo Ceballos
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

package somossuinos.jackketch.transform.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import somossuinos.jackketch.transform.exception.Jk4flowTranformerException;

/**
 * Cleans all HTML leaving only the XML data to be transformed onto a
 * workflow object.
 * <p>
 * To transform the incoming file into a valid XML structure, the reader
 * appends on the XML text between "&lt;![CDATA[" and "]]>", appending it to
 * a valid XML header ("&lt;?xml version="1.0" encoding="UTF-8" standalone="no" ?>").
 * </p>
 */
public class VioletReader implements Jk4flowReader {

    private static final String START_STRING = "<![CDATA[";
    private static final String END_STRING = "]]>";

    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";

    /**
     * {@inheritDoc}
     *
     * @param file The file that holds the diagram data.
     * @return A clean XML structured string with a valid format possible to
     * transform into a <b>VltWorkflow</b> object.
     */
    @Override
    public String read(final File file) {
        final StringBuilder xml = new StringBuilder(XML_HEADER);

        try {
            FileReader reader = new FileReader(file);
            final byte[] byteContent = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            final String content = new String(byteContent);

            final int START_IX = content.indexOf(START_STRING) + START_STRING.length();
            final int END_IX = content.indexOf(END_STRING);

            if (START_IX < 0 || END_IX < 0) {
                throw new Jk4flowTranformerException("Invalid file format! Not a compatible Violet 2.1.0 file version.");
            }

            xml.append(content.substring(START_IX, END_IX));

        } catch (IOException e) {
            throw new Jk4flowTranformerException(e);
        }

        return xml.toString();

    }

}
