package convolution;

import java.util.ArrayList;

import com.xilinx.rapidwright.edif.EDIFCellInst;

public class SystolicConfiguration {
	
	private long index;
	private int height;
	private int width;
	//Slave cell
	private ArrayList<EDIFCellInst> inputs = new ArrayList<EDIFCellInst>();
	//Master cell
	private ArrayList<EDIFCellInst> outputs = new ArrayList<EDIFCellInst>();
	
	public long getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public ArrayList<EDIFCellInst> getInputs() {
		return inputs;
	}
	public void setInputs(ArrayList<EDIFCellInst> inputs) {
		this.inputs = inputs;
	}
	public ArrayList<EDIFCellInst> getOutputs() {
		return outputs;
	}
	public void setOutputs(ArrayList<EDIFCellInst> outputs) {
		this.outputs = outputs;
	}
	
	public void addInputModule(EDIFCellInst cell) {
		getInputs().add(cell);
	}

	public void addOutputModule(EDIFCellInst cell) {
		getOutputs().add(cell);
	}
	
	public SystolicConfiguration(long index, int height, int width) {
		super();
		this.index = index;
		this.height = height;
		this.width = width;
	}

	
	
}
