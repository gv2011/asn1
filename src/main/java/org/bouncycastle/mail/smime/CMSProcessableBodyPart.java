package org.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.OutputStream;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;

import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;

/**
 * a holding class for a BodyPart to be processed.
 */
public class CMSProcessableBodyPart
    implements CMSProcessable
{
    private BodyPart   bodyPart;

    public CMSProcessableBodyPart(
        BodyPart    bodyPart)
    {
        this.bodyPart = bodyPart;
    }

    @Override
    public void write(
        OutputStream out)
        throws IOException, CMSException
    {
        try
        {
            bodyPart.writeTo(out);
        }
        catch (MessagingException e)
        {
            throw new CMSException("can't write BodyPart to stream.", e);
        }
    }

    @Override
    public Object getContent()
    {
        return bodyPart;
    }
}
