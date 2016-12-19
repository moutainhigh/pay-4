package com.pay.txncore.utils;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class HexCoder extends Coder{
    private static final char hexTab[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };


    String encode(byte[] text)
    {

        int  textLength = text.length;
        byte c;

        StringBuffer encodedText = new StringBuffer(textLength * 2);

        for (int i = 0; i < textLength; i++)
        {
            c = text[i];

            encodedText.append(hexTab[(c >> 4) & 0x0F]);
            encodedText.append(hexTab[c & 0x0F]);
        }

        return encodedText.toString();
    }


    byte[] decode(String text)
            throws IllegalArgumentException
    {

        int  len = text.length();

        if ((len % 2) != 0)
            throw new IllegalArgumentException("parameter text: illegal format");

        byte[] decodedByte = new byte[len / 2];

        byte b1;
        byte b2;
        int decodedByteIndex = 0;
        for (int i = 0; i < len; i += 2)
        {
            b1 = (byte) text.charAt(i);
            b1 -= 48;
            if (b1 > 9)
            {
                b1 -= 7;
                if (b1 > 15)
                    b1 -= 32;
            }

            b2 = (byte) text.charAt(i + 1);
            b2 -= 48;
            if (b2 > 9)
            {
                b2 -= 7;
                if (b2 > 15)
                    b2 -= 32;
            }

            decodedByte[decodedByteIndex] = ((byte) ((b1 * 16 + b2) & 0x00ff));
            decodedByteIndex++;
        }

        return decodedByte;
    }
}
