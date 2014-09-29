package com.intellect.mobile.commerceApp.util;

import java.util.BitSet;

public class Base64 {
	private final static String base64Tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	public static byte[] baseEncoding(byte[] sc) {
		int srcLength;
		srcLength = sc.length;
		if (srcLength > 3 || srcLength < 1)
			return null;
		byte rc[];
		byte t1, t2, t3, t4, t5, t6;
		byte tt1, tt2, tt3, tt4, tt5, tt6;
		byte b1 = (byte) 0xFC;
		byte b2 = (byte) 0x03;
		byte b3 = (byte) 0xF0;
		byte b4 = (byte) 0x0F;
		byte b5 = (byte) 0xC0;
		byte b6 = (byte) 0x3F;

		rc = new byte[srcLength + 1];
		switch (srcLength) {
		case 1:
			t1 = (byte) (sc[0] & b1);
			t2 = (byte) (sc[0] & b2);
			tt1 = bitMove(t1, 2);
			tt2 = (byte) (t2 << 4);
			rc[0] = tt1;
			rc[1] = tt2;
			break;
		case 2:
			t1 = (byte) (sc[0] & b1);
			t2 = (byte) (sc[0] & b2);
			t3 = (byte) (sc[1] & b3);
			t4 = (byte) (sc[1] & b4);
			tt1 = bitMove(t1, 2);
			tt2 = (byte) (t2 << 4);
			tt3 = bitMove(t3, 4);
			tt4 = (byte) (t4 << 2);
			rc[0] = tt1;
			rc[1] = (byte) (tt2 | tt3);
			rc[2] = tt4;
			break;
		case 3:
			t1 = (byte) (sc[0] & b1);
			t2 = (byte) (sc[0] & b2);
			t3 = (byte) (sc[1] & b3);
			t4 = (byte) (sc[1] & b4);
			t5 = (byte) (sc[2] & b5);
			t6 = (byte) (sc[2] & b6);
			tt1 = bitMove(t1, 2);
			tt2 = (byte) (t2 << 4);
			tt3 = bitMove(t3, 4);
			tt4 = (byte) (t4 << 2);
			tt5 = bitMove(t5, 6);
			tt6 = (byte) (t6);
			rc[0] = tt1;
			rc[1] = (byte) (tt2 | tt3);
			rc[2] = (byte) (tt4 | tt5);
			rc[3] = tt6;
			break;
		}
		return rc;
	}

	public static byte[] baseDecoding(byte[] sc) {
		int srcLength;
		srcLength = sc.length;
		if (srcLength > 4 || srcLength < 2)
			return null;
		byte rc[];
		byte t1, t2, t3, t4, t5, t6;
		byte tt1, tt2, tt3, tt4, tt5, tt6;
		byte b1 = (byte) 0x3F;
		byte b2 = (byte) 0x30;
		byte b3 = (byte) 0x0F;
		byte b4 = (byte) 0x3C;
		byte b5 = (byte) 0x03;
		byte b6 = (byte) 0x3F;
		rc = new byte[srcLength - 1];
		switch (srcLength) {
		case 2:
			t1 = (byte) (sc[0] & b1);
			t2 = (byte) (sc[1] & b2);
			tt1 = (byte) (t1 << 2);
			tt2 = bitMove(t2, 4);
			rc[0] = (byte) (tt1 | tt2);
			break;
		case 3:
			t1 = (byte) (sc[0] & b1);
			t2 = (byte) (sc[1] & b2);
			t3 = (byte) (sc[1] & b3);
			t4 = (byte) (sc[2] & b4);
			tt1 = (byte) (t1 << 2);
			tt2 = bitMove(t2, 4);
			tt3 = (byte) (t3 << 4);
			tt4 = bitMove(t4, 2);
			rc[0] = (byte) (tt1 | tt2);
			rc[1] = (byte) (tt3 | tt4);
			break;
		case 4:
			t1 = (byte) (sc[0] & b1);
			t2 = (byte) (sc[1] & b2);
			t3 = (byte) (sc[1] & b3);
			t4 = (byte) (sc[2] & b4);
			t5 = (byte) (sc[2] & b5);
			t6 = (byte) (sc[3] & b6);
			tt1 = (byte) (t1 << 2);
			tt2 = bitMove(t2, 4);
			tt3 = (byte) (t3 << 4);
			tt4 = bitMove(t4, 2);
			tt5 = (byte) (t5 << 6);
			tt6 = (byte) (t6);
			rc[0] = (byte) (tt1 | tt2);
			rc[1] = (byte) (tt3 | tt4);
			rc[2] = (byte) (tt5 | tt6);
			break;
		}
		return rc;
	}

	public static byte bitMove(byte sbyte, int count) {
		BitSet abit = new BitSet(8);
		byte rbyte = (byte) 0x00;
		byte bits[];
		bits = new byte[8];
		bits[0] = (byte) 0x80;
		bits[1] = (byte) 0x40;
		bits[2] = (byte) 0x20;
		bits[3] = (byte) 0x10;
		bits[4] = (byte) 0x08;
		bits[5] = (byte) 0x04;
		bits[6] = (byte) 0x02;
		bits[7] = (byte) 0x01;
		for (int i = 0; i < 8; i++) {
			if (i < count) {
				abit.clear(i);
				continue;
			}
			if (((byte) (sbyte & bits[i - count])) == bits[i - count]) {
				abit.set(i);
			} else {
				abit.clear(i);
			}
		}
		for (int i = 0; i < 8; i++) {
			if (abit.get(i))
				rbyte = (byte) (rbyte | bits[i]);
		}
		return rbyte;
	}

	/**
	 * Base64 encoding
	 */
	public static String encode(byte[] srcBytes) {
		int count;
		int numb;
		byte tBytes[];
		byte tmpBytes[];
		// String tChars;
		String rtnStr;
		count = 0;
		rtnStr = "";
		while (true) {
			if (srcBytes.length < (count + 3)) {
				numb = srcBytes.length % 3;
			} else {
				numb = 3;
			}
			tBytes = new byte[numb];
			for (int i = 0; i < numb; i++) {
				tBytes[i] = srcBytes[count + i];
			}
			tmpBytes = baseEncoding(tBytes); // .getBytes();
			for (int i = 0; i < tmpBytes.length; i++) {
				rtnStr += base64Tab.charAt(tmpBytes[i]);
			}
			count += 3;

			if (numb < 3) {
				for (int i = 0; i < (3 - numb); i++) {
					rtnStr += "=";
				}
				break;
			}
			if (count == srcBytes.length) {
				break;
			}
		}
		return rtnStr;
	}

	/**
	 * Base64 decoding
	 */
	public static byte[] decode(String encStr) {
		int cnt = 0;
		int idx;
		int numb;
		int count;
		byte encBytes[];
		byte fBytes[];
		byte bufBytes[] = new byte[10240];
		byte decBytes[];
		count = 0;
		encBytes = encStr.getBytes();

		while (true) {
			if (encBytes.length < (count + 4)) {
				numb = encBytes.length % 4;
			} else {
				numb = 4;
			}
			fBytes = new byte[numb];
			for (int i = 0; i < numb; i++) {
				idx = getTabIndex(encBytes[count + i]);
				if (idx == -1) {
					return null;
				}
				if (idx == 64) {
					byte[] tmpBytes = new byte[4];

					tmpBytes = fBytes;

					fBytes = new byte[i];

					for (int j = 0; j < i; j++) {
						fBytes[j] = tmpBytes[j];
					}
					break;
				}
				fBytes[i] = (byte) idx;
			}

			decBytes = baseDecoding(fBytes);
			if (decBytes == null)
				return null;
			for (int i = 0; i < decBytes.length; i++) {
				bufBytes[cnt++] = decBytes[i];
			}
			count += 4;
			if (numb < 4 || count == encBytes.length) {
				break;
			}
		}
		byte rtnBytes[] = new byte[cnt];
		System.arraycopy(bufBytes, 0, rtnBytes, 0, cnt);
		return rtnBytes;
	}

	private static int getTabIndex(byte b) {
		if (b >= 65 && b <= 90) {
			return (b - 65);
		}
		if (b >= 97 && b <= 122) {
			return (b - 97 + 26);
		}
		if (b >= 48 && b <= 57) {
			return (b - 48 + 52);
		}
		switch (b) {
		case '+':
			return 62;
		case '/':
			return 63;
		case '=':
			return 64;
		}
		return -1;
	}

	public static void main(String[] args) {
		String encrypted_pass = "7f92d9af-8468-4ee6-a74c-81ba7ef653e8";
		String encodeStr = new String(Base64.encode(encrypted_pass.getBytes()));
		System.out.println(" encoded " + encodeStr + "encoded length"
				+ encodeStr.length());
		String decodeStr = new String(Base64.decode(encodeStr));
		System.out.println(" decoded " + decodeStr);
	}
}
