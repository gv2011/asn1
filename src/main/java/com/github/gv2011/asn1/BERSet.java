package com.github.gv2011.asn1;

/*-
 * #%L
 * Vinz ASN.1
 * %%
 * Copyright (C) 2016 - 2017 Vinz (https://github.com/gv2011)
 * %%
 * Please note this should be read in the same way as the MIT license. (https://www.bouncycastle.org/licence.html)
 * 
 * Copyright (c) 2000-2015 The Legion of the Bouncy Castle Inc. (http://www.bouncycastle.org)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * #L%
 */


import java.util.Enumeration;

/**
 * Carrier class for an indefinite-length SET.
 */
public class BERSet
    extends ASN1Set
{
    /**
     * Create an empty SET.
     */
    public BERSet()
    {
    }

    /**
     * Create a SET containing one object.
     *
     * @param obj - a single object that makes up the set.
     */
    public BERSet(
        final ASN1Encodable obj)
    {
        super(obj);
    }

    /**
     * Create a SET containing multiple objects.
     * @param v a vector of objects making up the set.
     */
    public BERSet(
        final ASN1EncodableVector v)
    {
        super(v, false);
    }

    /**
     * Create a SET from an array of objects.
     * @param a an array of ASN.1 objects.
     */
    public BERSet(
        final ASN1Encodable[]   a)
    {
        super(a, false);
    }

    @Override
    int encodedLength()
    {
        int length = 0;
        for (final Enumeration<?> e = getObjects(); e.hasMoreElements();)
        {
            length += ((ASN1Encodable)e.nextElement()).toASN1Primitive().encodedLength();
        }

        return 2 + length + 2;
    }

    @Override
    void encode(
        final ASN1OutputStream out)
    {
        out.write(BERTags.SET | BERTags.CONSTRUCTED);
        out.write(0x80);

        final Enumeration<?> e = getObjects();
        while (e.hasMoreElements())
        {
            out.writeObject((ASN1Encodable)e.nextElement());
        }

        out.write(0x00);
        out.write(0x00);
    }
}
