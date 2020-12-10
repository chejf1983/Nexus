/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sps.app.std;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import sps.dev.data.SSLinearParameter;

/**
 *
 * @author Administrator
 */
public class LinearParameterHelper {

    public static String fileEndMark = ".NCC";

    public static SSLinearParameter ReadLinearParameter(File file) throws Exception {
        if (!file.getPath().endsWith(fileEndMark)) {
            throw new Exception("Unkown File");
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int kNumber = (int) FindNumber("KNum", reader);
        SSLinearParameter par = new SSLinearParameter(kNumber);

        for (int adIndex = 0; adIndex < kNumber; adIndex++) {
            String result;
            if ((result = reader.readLine()) != null) {
                String[] wavenode = result.split("#");

                par.ADValueArray[adIndex] = Float.parseFloat(wavenode[0]);
                par.KParArray[adIndex] = Float.parseFloat(wavenode[1]);
            } else {
                reader.close();
                throw new Exception(" Cant read ADValue and K parameter");
            }
        }

        reader.close();
        return par;
    }

    private static float FindNumber(String Key, BufferedReader reader) throws Exception {
        String readLine = reader.readLine();
        if (!readLine.startsWith(Key)) {
            reader.close();
            throw new Exception("Error SynParameter File, Cant found " + Key);
        }

        return Float.valueOf(readLine.substring(readLine.indexOf(":") + 1));
    }

    public static void WriteLinearParameter(SSLinearParameter par, File dstfile) throws Exception {
        if (!dstfile.getPath().endsWith(fileEndMark)) {
            throw new Exception("Unkown File");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(dstfile));
        writer.write("KNum:" + par.NodeKNumber);
        writer.newLine();

        for (int adIndex = 0; adIndex < par.NodeKNumber; adIndex++) {
            writer.write(par.ADValueArray[adIndex] + "#" + par.KParArray[adIndex]);
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }
}
