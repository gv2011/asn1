package com.github.gv2011.asn1;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.gv2011.util.bytes.ByteUtils;
import com.github.gv2011.util.bytes.Bytes;

/**
 * Set sorting test example
 */
public class SetTest{

    private void checkedSortedSet(final int attempt, final ASN1Set s)
    {
        if (s.getObjectAt(0) instanceof ASN1Boolean
            && s.getObjectAt(1) instanceof ASN1Integer
            && s.getObjectAt(2) instanceof DERBitString
            && s.getObjectAt(3) instanceof DEROctetString)
        {
            return;
        }

        fail("sorting failed on attempt: " + attempt);
    }

    @Test
    public void test(){
        ASN1EncodableVector v = new ASN1EncodableVector();
        final Bytes data = ByteUtils.parseHex("00 00 00 00 00 00 00 00 00 00");

        v.add(new DEROctetString(data));
        v.add(new DERBitString(data));
        v.add(new ASN1Integer(100));
        v.add(ASN1Boolean.getInstance(true));

        checkedSortedSet(0, new DERSet(v));

        v = new ASN1EncodableVector();
        v.add(new ASN1Integer(100));
        v.add(ASN1Boolean.getInstance(true));
        v.add(new DEROctetString(data));
        v.add(new DERBitString(data));

        checkedSortedSet(1, new DERSet(v));

        v = new ASN1EncodableVector();
        v.add(ASN1Boolean.getInstance(true));
        v.add(new DEROctetString(data));
        v.add(new DERBitString(data));
        v.add(new ASN1Integer(100));


        checkedSortedSet(2, new DERSet(v));

        v = new ASN1EncodableVector();
        v.add(new DERBitString(data));
        v.add(new DEROctetString(data));
        v.add(new ASN1Integer(100));
        v.add(ASN1Boolean.getInstance(true));

        checkedSortedSet(3, new DERSet(v));

        v = new ASN1EncodableVector();
        v.add(new DEROctetString(data));
        v.add(new DERBitString(data));
        v.add(new ASN1Integer(100));
        v.add(ASN1Boolean.getInstance(true));

        ASN1Set s = new BERSet(v);

        if (!(s.getObjectAt(0) instanceof DEROctetString))
        {
            fail("BER set sort order changed.");
        }

        // create an implicitly tagged "set" without sorting
        final ASN1TaggedObject tag = new DERTaggedObject(false, 1, new DERSequence(v));
        s = ASN1Set.getInstance(tag, false);

        if (s.getObjectAt(0) instanceof ASN1Boolean)
        {
            fail("sorted when shouldn't be.");
        }

        // equality test
        v = new ASN1EncodableVector();

        v.add(ASN1Boolean.getInstance(true));
        v.add(ASN1Boolean.getInstance(true));
        v.add(ASN1Boolean.getInstance(true));

        s = new DERSet(v);
    }

}
