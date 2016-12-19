package com.pay.txncore.utils;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public abstract class Coder {
    abstract String encode(byte[] text);
    abstract byte[] decode(String text);
}
