package com.cynaptec.connect;

public class Objects {
	public static void requireNonNull(Object obj, String msg) {
		if (obj == null) 
			throw new NullPointerException(msg);
	}
}
