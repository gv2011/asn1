package org.bouncycastle.mail.smime.handlers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jakarta.activation.ActivationDataFlavor;
import jakarta.activation.DataContentHandler;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;

import org.bouncycastle.mail.smime.SMIMEStreamingProcessor;

public class PKCS7ContentHandler 
    implements DataContentHandler 
{
    private final ActivationDataFlavor _adf;
    private final ActivationDataFlavor[]         _dfs;
    
    PKCS7ContentHandler(
        ActivationDataFlavor adf,
        ActivationDataFlavor[]         dfs)
    {
        _adf = adf;
        _dfs = dfs;
    }

    @Override
    public Object getContent(
        DataSource ds)
        throws IOException
    {
        return ds.getInputStream();
    }
    
    @Override
    public Object getTransferData(
        ActivationDataFlavor df, 
        DataSource ds) 
        throws IOException 
    { 
        if (_adf.equals(df))
        {
            return getContent(ds);
        }
        else 
        {
            return null;
        }
    }
    
    @Override
    public ActivationDataFlavor[] getTransferDataFlavors() 
    {
        return _dfs;
    }
    
    @Override
    public void writeTo(
        Object obj, 
        String mimeType,
        OutputStream os) 
        throws IOException 
    {
        if (obj instanceof MimeBodyPart) 
        {
            try 
            {
                ((MimeBodyPart)obj).writeTo(os);
            }
            catch (MessagingException ex)
            {
                throw new IOException(ex.getMessage());
            }
        }
        else if (obj instanceof byte[]) 
        {
            os.write((byte[])obj);
        }
        else if (obj instanceof InputStream)
        {
            int         b;
            InputStream in = (InputStream)obj;
            
            if (!(in instanceof BufferedInputStream))
            {
                in = new BufferedInputStream(in);
            }

            while ((b = in.read()) >= 0)
            {
                os.write(b);
            }

            in.close();
        }
        else if (obj instanceof SMIMEStreamingProcessor)
        {
            SMIMEStreamingProcessor processor = (SMIMEStreamingProcessor)obj;

            processor.write(os);
        }
        else
        {
            // TODO it would be even nicer if we could attach the object to the exception
            //     as well since in deeply nested messages, it is not always clear which
            //     part caused the problem. Thus I guess we would have to subclass the
            //     IOException

            throw new IOException("unknown object in writeTo " + obj);
        }
    }
}
