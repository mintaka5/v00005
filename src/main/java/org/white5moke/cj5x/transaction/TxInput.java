package org.white5moke.cj5x.transaction;

public class TxInput {
    public String outputId; // reference to tx output's ID
    public TxOutput output; // unspent tx output

    public TxInput(String outputId) {
        setOutputId(outputId);
    }

    public String getOutputId() {
        return outputId;
    }

    public void setOutputId(String outputId) {
        this.outputId = outputId;
    }

    public TxOutput getOutput() {
        return output;
    }

    public void setOutput(TxOutput output) {
        this.output = output;
    }
}
