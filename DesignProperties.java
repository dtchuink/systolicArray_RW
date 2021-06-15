package keywords;

import java.util.ArrayList;

import convolution.SystolicConfiguration;

public class DesignProperties {
	
	public static String DEVICE_NAME = "xcku13p-ffve900-3-e";//"xcvu9p-flgb2104-2-i";
	public static String DESIGN_NAME = "result";
	public static String DCP_FOLDER_PATH = "C:/modules/saved_dcp/";
	public static String ReLu = "relu.dcp";
	public static String ACTIVATION_SOFTMAX = "relu.dcp";
	public static String ACTIVATION_SIGMOID = "sigmoid.dcp";
	public static String ACTIVATION_THAN = "relu.dcp";
	public static String MUL = "relu.dcp";
	public static String ADD = "relu.dcp";
	public static String FIFO = "fifo.dcp";
	public static String MAC = "mac_.dcp";
	public static String MAC_P = "mac_peripheral.dcp";
	
	public static String CLK = "clk";

	public static int MAC_INPUT_WIDTH = 18;
	public static int MAC_OUTPUTT_WIDTH = 18;
	
	public static ArrayList<SystolicConfiguration> layers = new ArrayList<SystolicConfiguration>();
}
