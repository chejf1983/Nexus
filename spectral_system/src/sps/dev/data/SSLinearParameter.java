/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sps.dev.data;

/**
 *
 * @author Administrator
 */
public class SSLinearParameter {

    public int NodeKNumber;
    public float[] ADValueArray;
    public float[] KParArray;

    public SSLinearParameter(int NodeKNumber) {
        this.NodeKNumber = NodeKNumber;
        this.ADValueArray = new float[NodeKNumber];
        this.KParArray = new float[NodeKNumber];
    }

    public void SortNode() {
        for (int i = 0; i < NodeKNumber; i++) {
            for (int j = i + 1; j < NodeKNumber; j++) {
                if (ADValueArray[i] > ADValueArray[j]) {
                    float tmp = ADValueArray[i];
                    ADValueArray[i] = ADValueArray[j];
                    ADValueArray[j] = tmp;
                    tmp = KParArray[i];
                    KParArray[i] = KParArray[j];
                    KParArray[j] = tmp;
                }
            }
        }
    }
}
