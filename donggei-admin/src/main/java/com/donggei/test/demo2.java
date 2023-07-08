package com.donggei.test;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @className: demo2
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/10/2
 **/
public class demo2 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("afile.txt");
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeInt(3);
        dos.writeChar(1);
        dos.close();
        fos.close();
    }

}
