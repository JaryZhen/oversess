﻿package com.oversea.ads.util;
import java.io.UnsupportedEncodingException; 

import sun.misc.BASE64Encoder;
/** 
 Base64解码类
 将Base64编码的string类型转换成byte[]类型
 
*/
public class Base64Decoder
{
	private char[] source;
	private int length, length2, length3;
	private int blockCount;
	private int paddingCount;
	public static Base64Decoder Decoder = new Base64Decoder();

	public Base64Decoder()
	{
	}
	public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
	
	private void init(char[] input)
	{
		int temp = 0;
		source = input;
		length = input.length;

		for (int x = 0; x < 2; x++)
		{
			if (input[length - x - 1] == '=')
			{
				temp++;
			}
		}
		paddingCount = temp;

		blockCount = length / 4;
		length2 = blockCount * 3;
	}

	public final byte[] GetDecoded(String strInput)
	{
		//初始化
		init(strInput.toCharArray());

		byte[] buffer = new byte[length];
		byte[] buffer2 = new byte[length2];

		for (int x = 0; x < length; x++)
		{
			buffer[x] = char2sixbit(source[x]);
		}

		byte b, b1, b2, b3;
		byte temp1, temp2, temp3, temp4;

		for (int x = 0; x < blockCount; x++)
		{
			temp1 = buffer[x * 4];
			temp2 = buffer[x * 4 + 1];
			temp3 = buffer[x * 4 + 2];
			temp4 = buffer[x * 4 + 3];

			b = (byte)(temp1 << 2);
			b1 = (byte)((temp2 & 48) >> 4);
			b1 += b;

			b = (byte)((temp2 & 15) << 4);
			b2 = (byte)((temp3 & 60) >> 2);
			b2 += b;

			b = (byte)((temp3 & 3) << 6);
			b3 = temp4;
			b3 += b;

			buffer2[x * 3] = b1;
			buffer2[x * 3 + 1] = b2;
			buffer2[x * 3 + 2] = b3;
		}

		length3 = length2 - paddingCount;
		byte[] result = new byte[length3];

		for (int x = 0; x < length3; x++)
		{
			result[x] = buffer2[x];
		}

		return result;
	}

	private byte char2sixbit(char c)
	{
		char[] lookupTable = new char[]{ 'A','B','C','D','E','F','G','H','I','J','K','L','M','N', 'O','P','Q','R','S','T','U','V','W','X','Y', 'Z', 'a','b','c','d','e','f','g','h','i','j','k','l','m','n', 'o','p','q','r','s','t','u','v','w','x','y','z', '0','1','2','3','4','5','6','7','8','9','+','/'};
		if (c == '=')
		{
			return 0;
		}
		else
		{
			for (int x = 0; x < 64; x++)
			{
				if (lookupTable[x] == c)
				{
					return (byte)x;
				}
			}

			return 0;
		}

	}
}
//解码类结束


