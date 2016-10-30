package br.edu.unigranrio.ect.si.cfa.commons.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class Strings {

	private static final String HEX_STRING_FORMAT = "%1$032x";

	private Strings() {	}

	public static byte[] string2Bytes(String value) {
		return string2Bytes(value, StandardCharsets.UTF_8);
	}

	public static byte[] string2Bytes(String value, Charset charset) {
		return (value != null && charset != null) ? value.getBytes(charset) : null;
	}

	public static String byte2String(byte[] value) {
		return byte2String(value, StandardCharsets.UTF_8);
	}

	public static String byte2String(byte[] value, Charset charset){
		return new String(value, charset);
	}

	public static String bytes2Hex(byte[] value) {
		return value != null ? String.format(HEX_STRING_FORMAT, new BigInteger(1, value)) : null;
	}

}
