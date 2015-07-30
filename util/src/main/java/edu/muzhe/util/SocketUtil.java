package edu.muzhe.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by muzhe on 15-7-28.
 * Socket中相对应的代码．
 */
public class SocketUtil {


    public static  String readStrFromBufferedReader(BufferedReader bufferedReader) throws IOException {

        StringBuffer stringBuffer = new StringBuffer("");

        char[] chars = new char[2048];


        int len = 0;

        try
        {
            while (-1 !=(len = bufferedReader.read(chars)))
            {
                if (2048 == len)
                {
                    stringBuffer.append(chars);
                }
                else
                {
                    for (int i = 0 ; i < len ; i++)
                    {
                        stringBuffer.append(chars[i]);
                    }
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return stringBuffer.toString();

    }

    public  static void  writeString2Stream(String str , BufferedWriter bufferedWriter) throws IOException {

        bufferedWriter.write(str);

        bufferedWriter.flush();

    }



}
