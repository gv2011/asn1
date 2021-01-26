package org.bouncycastle.mail.smime.examples;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;

import org.bouncycastle.cms.jcajce.ZlibCompressor;
import org.bouncycastle.mail.smime.SMIMECompressedGenerator;

/**
 * a simple example that creates a single compressed mail message using the large
 * file model.
 */
public class CreateLargeCompressedMail
{
    public static void main(
        String args[])
        throws Exception
    {
        //
        // create the generator for creating an smime/compressed message
        //
        SMIMECompressedGenerator  gen = new SMIMECompressedGenerator();
          
        //
        // create the base for our message
        //
        MimeBodyPart msg = new MimeBodyPart();

        msg.setDataHandler(new DataHandler(new FileDataSource(new File(args[0]))));
        msg.setHeader("Content-Type", "application/octet-stream");
        msg.setHeader("Content-Transfer-Encoding", "binary");

        MimeBodyPart mp = gen.generate(msg, new ZlibCompressor());

        //
        // Get a Session object and create the mail message
        //
        Properties props = System.getProperties();
        Session session = Session.getDefaultInstance(props, null);

        Address fromUser = new InternetAddress("\"Eric H. Echidna\"<eric@bouncycastle.org>");
        Address toUser = new InternetAddress("example@bouncycastle.org");

        MimeMessage body = new MimeMessage(session);
        body.setFrom(fromUser);
        body.setRecipient(Message.RecipientType.TO, toUser);
        body.setSubject("example compressed message");
        body.setContent(mp.getContent(), mp.getContentType());
        body.saveChanges();

        body.writeTo(new FileOutputStream("compressed.message"));
    }
}
