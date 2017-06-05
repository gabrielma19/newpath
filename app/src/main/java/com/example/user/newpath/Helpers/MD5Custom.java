package com.example.user.newpath.Helpers;

import android.util.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by User on 01/06/2017.
 */

public class MD5Custom {
    public  static String codificarMd5 (String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        BigInteger has = new BigInteger(1, md.digest(password.getBytes()));

        return  String.format("%32x", has);
    }
}
