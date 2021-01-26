package org.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.InputStream;

import org.bouncycastle.cms.CMSCompressedData;
import org.bouncycastle.cms.CMSException;

import jakarta.mail.MessagingException;
import jakarta.mail.Part;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimePart;

/**
 * containing class for an S/MIME pkcs7-mime MimePart.
 */
public class SMIMECompressed
    extends CMSCompressedData
{
    MimePart                message;

    private static InputStream getInputStream(
        Part    bodyPart)
        throws MessagingException
    {
        try
        {
            return bodyPart.getInputStream();
        }
        catch (IOException e)
        {
            throw new MessagingException("can't extract input stream: " + e);
        }
    }

    public SMIMECompressed(
        MimeBodyPart    message) 
        throws MessagingException, CMSException
    {
        super(getInputStream(message));

        this.message = message;
    }

    public SMIMECompressed(
        MimeMessage    message) 
        throws MessagingException, CMSException
    {
        super(getInputStream(message));

        this.message = message;
    }

    public MimePart getCompressedContent()
    {
        return message;
    }
}
