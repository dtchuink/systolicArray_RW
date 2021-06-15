package convolution;

import java.util.ArrayList;
import java.util.Random;

import com.xilinx.rapidwright.design.Design;
import com.xilinx.rapidwright.design.Module;
import com.xilinx.rapidwright.design.ModuleInst;
import com.xilinx.rapidwright.device.Device;
import com.xilinx.rapidwright.device.Site;
import com.xilinx.rapidwright.edif.EDIFCell;
import com.xilinx.rapidwright.edif.EDIFCellInst;
import com.xilinx.rapidwright.edif.EDIFDirection;
import com.xilinx.rapidwright.edif.EDIFNet;
import com.xilinx.rapidwright.edif.EDIFPort;
import com.xilinx.rapidwright.edif.EDIFPortInst;

import keywords.DesignProperties;
import util.Utils;

public class SystolicArray {
	static Random rand = new Random();
	
	/**
	 * Connect first row of systolic array
	 * @param d1
	 * @param topCell
	 * @param firstCell
	 * @param secondCell
	 * @param clkNet
	 * @param portToConnect
	 * @param rstNet
	 */
	
	public static void connectPE(Design d1, EDIFCell topCell, EDIFCellInst firstCell, EDIFCellInst secondCell,  EDIFNet clkNet, int portToConnect, EDIFNet rstNet) {
//		System.out.println("in function connectPE 1");
		
//		System.out.println("Conecting PE "+firstCell.getName()+" -> "+secondCell.getName());
//		System.out.println("PE Ports "+firstCell.getCellPorts().toString());
		
		for (int i = 0; i < ConvolutionResources.INPUTS_PORTS_MAC.length; i++) {
//			System.out.println(" Mapping port ="+firstCell.getPort(ConvolutionResources.INPUTS_PORTS_MAC[i]));

			if (firstCell.getPort(ConvolutionResources.INPUTS_PORTS_MAC[i]).getWidth() == 1) {

				
//				System.out.println(" Creation of clock");
				clkNet.createPortInst("CLK", firstCell);
				clkNet.createPortInst("CLK", secondCell);
				
				rstNet.createPortInst("SCLR", firstCell);
				rstNet.createPortInst("SCLR", secondCell);
				
			} else { 
				if(i==portToConnect){

//					System.out.println(" Mapping Port  --->: "+ConvolutionResources.INPUTS_PORTS_MAC[i]+" and "+ConvolutionResources.OUTPUTs_PORTS_MAC[i]);
					for (int j = 0; j < firstCell.getPort(ConvolutionResources.INPUTS_PORTS_MAC[i]).getWidth(); j++) {
						String netName = firstCell.getName() + "_"+ secondCell.getName() + "_"
								+ ConvolutionResources.INPUTS_PORTS_MAC[i] + System.currentTimeMillis()+rand.nextLong();
						EDIFNet net = topCell.createNet(netName);
						net.createPortInst(ConvolutionResources.INPUTS_PORTS_MAC[i], j, secondCell);
						net.createPortInst(ConvolutionResources.OUTPUTs_PORTS_MAC[i], j, firstCell);
					}
//					System.out.println(" Mapping C -> P");
				}
			}
		}
	}
	
	public static void connectPE(Design d1, EDIFCell topCell, EDIFCellInst upCell, EDIFCellInst buttomCell, EDIFCellInst leftCell,  EDIFNet clkNet, EDIFNet rstNet) {
//		System.out.println("in function connectPE 2");
		
		for (int i = 0; i < ConvolutionResources.INPUTS_PORTS_MAC.length; i++) {
//			System.out.println("connectPE Mapping port ="+upCell.getPort(ConvolutionResources.INPUTS_PORTS_MAC[i]));

			if (upCell.getPort(ConvolutionResources.INPUTS_PORTS_MAC[i]).getWidth() == 1) {

				clkNet.createPortInst("CLK", buttomCell);
				clkNet.createPortInst("CLK", leftCell);
				
				rstNet.createPortInst("SCLR", buttomCell);
				rstNet.createPortInst("SCLR", leftCell);
//				System.out.println(" Creation of clock");
				
			} else { 
				if(i==1){
						for (int j = 0; j < buttomCell.getPort(ConvolutionResources.INPUTS_PORTS_MAC[i]).getWidth(); j++) {
							String netName = buttomCell.getName() + "_"+ leftCell.getName() + "_"
									+ ConvolutionResources.INPUTS_PORTS_MAC[i] + j+ System.currentTimeMillis()+rand.nextLong();
							EDIFNet net = topCell.createNet(netName);
							net.createPortInst(ConvolutionResources.OUTPUTs_PORTS_MAC[i], j, leftCell);
							net.createPortInst(ConvolutionResources.INPUTS_PORTS_MAC[i], j, buttomCell);
						}
					}
				
				else if(i==3) {
					for (int j = 0; j < upCell.getPort(ConvolutionResources.INPUTS_PORTS_MAC[i]).getWidth(); j++) {
						String scdNetName = upCell.getName() + "_"+ buttomCell.getName() + "_"
										+ ConvolutionResources.INPUTS_PORTS_MAC[i] + j+ System.currentTimeMillis()+rand.nextLong();
						EDIFNet scdNet = topCell.createNet(scdNetName);
						scdNet.createPortInst(ConvolutionResources.OUTPUTs_PORTS_MAC[i], j, upCell);
						scdNet.createPortInst(ConvolutionResources.INPUTS_PORTS_MAC[i], j, buttomCell);
					}
				}
			}
		}
	}
	
	/**
	 * Connect peripheral PE to  FIFO in master mode
	 * @param d1
	 * @param topCell
	 * @param CUCell
	 * @param FIFOCell
	 * @param clkNet
	 * @param rstNet
	 */
	public static void connectMasterPESlaveFIFO(Design d1, EDIFCell topCell, EDIFCellInst CUCell, EDIFCellInst FIFOCell, EDIFNet clkNet, EDIFNet rstNet) {
//		System.out.println("in function connectMasterPESlaveFIFO");
		//Connect master interface
		for (int i = 0; i < ConvolutionResources.MASTER_PORTS_PROTOCOL.length-1; i++) {
			
			if (CUCell.getPort(ConvolutionResources.MASTER_PORTS_PROTOCOL[i]).getWidth() == 1) {
				
				String netName = CUCell.getName() + "_"+ FIFOCell.getName() + "_"+ ConvolutionResources.MASTER_PORTS_PROTOCOL[i]+ System.currentTimeMillis()+rand.nextLong();
				EDIFNet net = topCell.createNet(netName);
				net.createPortInst(ConvolutionResources.SLAVE_PORTS_FIFO[i],  FIFOCell);
				net.createPortInst(ConvolutionResources.MASTER_PORTS_PROTOCOL[i],  CUCell);
			} 
			
			 else { 
					for (int j = 0; j < CUCell.getPort(ConvolutionResources.MASTER_PORTS_PROTOCOL[i]).getWidth(); j++) {
						String netName = CUCell.getName() + "_"+ FIFOCell.getName() + "_"
								+ ConvolutionResources.MASTER_PORTS_PROTOCOL[i] + j;
						EDIFNet net = topCell.createNet(netName);
						net.createPortInst(ConvolutionResources.SLAVE_PORTS_FIFO[i], j, FIFOCell);
						net.createPortInst(ConvolutionResources.MASTER_PORTS_PROTOCOL[i], j, CUCell);
					}
			 }
		}
		connectRemainingFIFOPorts(d1, topCell, FIFOCell, clkNet, rstNet);
//		System.out.println("END ********* function connectMasterPESlaveFIFO");
	}
	
/**
 * Connected periperal PE to FIFO in slave mode
 * @param d1
 * @param topCell
 * @param CUCell
 * @param FIFOCell
 * @param clkNet
 * @param rstNet
 */
	public static void connectSlavePEMasterFIFO(Design d1, EDIFCell topCell, EDIFCellInst CUCell, EDIFCellInst FIFOCell, EDIFNet clkNet, EDIFNet rstNet) {
//		System.out.println("in function connectSlavePEMasterFIFO");
		//Connect master interface
		for (int i = 0; i < ConvolutionResources.SLAVE_PORTS_PROTOCOL.length-1; i++) {

//			System.out.println("connectPE to CUCell ="+ CUCell.getPort("result"));
//			System.out.println("connectPE to FIFO ="+FIFOCell.getPort("i_clk"));
			if (CUCell.getPort(ConvolutionResources.SLAVE_PORTS_PROTOCOL[i]).getWidth() == 1) {
				
				String netName = CUCell.getName() + "_"+ FIFOCell.getName() + "_"+ ConvolutionResources.SLAVE_PORTS_PROTOCOL[i]+ System.currentTimeMillis()+rand.nextLong();
				EDIFNet net = topCell.createNet(netName);
				net.createPortInst(ConvolutionResources.MASTER_PORTS_FIFO[i],  FIFOCell);
				net.createPortInst(ConvolutionResources.SLAVE_PORTS_PROTOCOL[i],  CUCell);
				
			} 
			 else { 
							for (int j = 0; j < CUCell.getPort(ConvolutionResources.SLAVE_PORTS_PROTOCOL[i]).getWidth(); j++) {
								String netName = CUCell.getName() + "_"+ FIFOCell.getName() + "_"
										+ ConvolutionResources.SLAVE_PORTS_PROTOCOL[i] + j+ System.currentTimeMillis()+rand.nextLong();
								EDIFNet net = topCell.createNet(netName);
								net.createPortInst(ConvolutionResources.MASTER_PORTS_FIFO[i], j, FIFOCell);
								net.createPortInst(ConvolutionResources.SLAVE_PORTS_PROTOCOL[i], j, CUCell);
							}
		}
	}
		connectRemainingFIFOPorts(d1, topCell, FIFOCell, clkNet, rstNet);
//		System.out.println("END ********* function connectSlavePEMasterFIFO");
	}
	
	public static void connectRemainingFIFOPorts(Design d1, EDIFCell topCell, EDIFCellInst FIFOCell,  EDIFNet clkNet,  EDIFNet rstNet) {
//		System.out.println("in function connectRemainingFIFOPorts = "+ConvolutionResources.OTHER_PORTS_FIFO.length);
		
		for (int i = 0; i < ConvolutionResources.OTHER_PORTS_FIFO.length; i++) {
			if (FIFOCell.getPort(ConvolutionResources.OTHER_PORTS_FIFO[i]).getWidth() == 1) {
				if(i==0)
					clkNet.createPortInst(ConvolutionResources.OTHER_PORTS_FIFO[i], FIFOCell);
				
				if(i==1)
					rstNet.createPortInst(ConvolutionResources.OTHER_PORTS_FIFO[i], FIFOCell);
								
//				System.out.println(" Creation of clock");
				
			}
		}
//	}System.out.println("END ******** function connectRemainingFIFOPorts = "+ConvolutionResources.OTHER_PORTS_FIFO.length);
	}

	
	static void buildSystolicArray(int width, int high, long index, SystolicConfiguration array, Design d1, EDIFNet clkNet, EDIFNet rstNet) throws NullPointerException{

//		System.out.println("in function buildSystolicArray");
		Device dev = d1.getDevice();
		Site dsp = dev.getSite("DSP48E2_X9Y60");//DSP48E2_X9Y60
		EDIFCell topCell = d1.getNetlist().getTopCell();
				
		int count = 1;
		int i = 0;
		int j = 0;
		for(i=0; i< width; i++){
			for(j=0; j< high; j++) {
				Module mac = null;
				ModuleInst clb1 = null;
				
				if(i<width-1 ) {
					mac = Utils.addNodeToDesign(d1, ConvolutionResources.MAC, DesignProperties.DCP_FOLDER_PATH, DesignProperties.MAC, 0, 0);
					Site slice1 = dsp.getNeighborSite(0, count);
					clb1 = d1.createModuleInst("mod" + i+j+index, mac);
					clb1.getCellInst().setCellType(mac.getNetlist().getTopCell());
					clb1.place(slice1);	
				}
				System.out.println("---------- i ="+i+"     j = "+j);
				
					if(i==0 & j>0) {
						if(j==1) {

							Module mac_peripheral = Utils.addNodeToDesign(d1, ConvolutionResources.MAC_P, DesignProperties.DCP_FOLDER_PATH, DesignProperties.MAC_P, 0, 0);
							ModuleInst clb1_peripheral = d1.createModuleInst("mac_peripheral" + i+(j-1)+index, mac_peripheral);
							clb1_peripheral.getCellInst().setCellType(mac_peripheral.getNetlist().getTopCell());
							connectPE( d1, topCell, clb1_peripheral.getCellInst(), clb1.getCellInst(), clkNet, 1, rstNet);
							connectPE( d1, topCell, clb1_peripheral.getCellInst(), clb1.getCellInst(), clkNet, 3, rstNet);
						} else {
							connectPE( d1, topCell, d1.getModuleInst("mod"+i+(j-1)+index).getCellInst(), clb1.getCellInst(), clkNet, 1, rstNet);
//							connectPE( d1, topCell, d1.getModuleInst("mod"+i+(j-1)+index).getCellInst(), clb1.getCellInst(), clkNet, 3, rstNet);
						}
					} else 
						
						if(i==0 & j==0) {
							Module mac_peripheral = Utils.addNodeToDesign(d1, ConvolutionResources.MAC_P, DesignProperties.DCP_FOLDER_PATH, DesignProperties.MAC_P, 0, 0);
							ModuleInst clb1_peripheral = d1.createModuleInst("mac_peripheral" + i+j+index, mac_peripheral);
							clb1_peripheral.getCellInst().setCellType(mac_peripheral.getNetlist().getTopCell());

							Module fifo = Utils.addNodeToDesign(d1, ConvolutionResources.FIFO, DesignProperties.DCP_FOLDER_PATH, DesignProperties.FIFO, 0, 0);
							ModuleInst fifo1 = d1.createModuleInst("fifo" + i+j+index, fifo);
							fifo1.getCellInst().setCellType(fifo.getNetlist().getTopCell());
							array.addInputModule(fifo1.getCellInst());
							
							connectSlavePEMasterFIFO(d1, topCell, clb1_peripheral.getCellInst(), fifo1.getCellInst(), clkNet, rstNet);
//						System.out.println("FIFO port connected");
					}
					else if (i>0 & j==0) {
						Module mac_peripheral = Utils.addNodeToDesign(d1, ConvolutionResources.MAC_P, DesignProperties.DCP_FOLDER_PATH, DesignProperties.MAC_P, 0, 0);
						ModuleInst clb1_peripheral = d1.createModuleInst("mac_peripheral" + i+j+index, mac_peripheral);
						clb1_peripheral.getCellInst().setCellType(mac_peripheral.getNetlist().getTopCell());
						
						Module fifo = Utils.addNodeToDesign(d1, ConvolutionResources.FIFO, DesignProperties.DCP_FOLDER_PATH, DesignProperties.FIFO, 0, 0);
						ModuleInst fifo1 = d1.createModuleInst("fifo" + i+j+index, fifo);
						fifo1.getCellInst().setCellType(fifo.getNetlist().getTopCell());
						array.addInputModule(fifo1.getCellInst());
						
						connectSlavePEMasterFIFO(d1, topCell, clb1_peripheral.getCellInst(), fifo1.getCellInst(), clkNet, rstNet);
						connectPE( d1, topCell, d1.getModuleInst("mod"+(i-1)+j+index).getCellInst(), clb1.getCellInst(), clkNet, 3, rstNet);
					}
					else if(i>0 &j>0) {
						if(i<(width-2)) {
							connectPE( d1, topCell, d1.getModuleInst("mod"+(i-1)+j+index).getCellInst(), clb1.getCellInst(), d1.getModuleInst("mod"+i+(j-1)+index).getCellInst(), clkNet, rstNet );
						}
						else if(i==(width-2)) {
							Module mac_peripheral = Utils.addNodeToDesign(d1, ConvolutionResources.MAC_P, DesignProperties.DCP_FOLDER_PATH, DesignProperties.MAC_P, 0, 0);
							ModuleInst clb1_peripheral = d1.createModuleInst("mac_peripheral" + (i+1)+j+index, mac_peripheral);
							clb1_peripheral.getCellInst().setCellType(mac_peripheral.getNetlist().getTopCell());
							
							connectPE( d1, topCell, d1.getModuleInst("mod"+(i-1)+j+index).getCellInst(), clb1_peripheral.getCellInst(), d1.getModuleInst("mod"+i+(j-1)+index).getCellInst(), clkNet, rstNet );
						}
					
						else if(i==(width-1)) {
						
								EDIFCellInst mac_P = d1.getModuleInst("mac_peripheral" + i+j+index).getCellInst();
								Module fifo = Utils.addNodeToDesign(d1, ConvolutionResources.FIFO, DesignProperties.DCP_FOLDER_PATH, DesignProperties.FIFO, 0, 0);
								ModuleInst fifo1 = d1.createModuleInst("fifo" + i+j+index, fifo);
								fifo1.getCellInst().setCellType(fifo.getNetlist().getTopCell());
								array.addOutputModule(fifo1.getCellInst());
								
								connectMasterPESlaveFIFO(d1, topCell, mac_P, fifo1.getCellInst(), clkNet, rstNet);
						}
//						connectPE( d1, topCell, d1.getModuleInst("mod"+(i-1)+j+index).getCellInst(), clb1.getCellInst(), d1.getModuleInst("mod"+i+(j-1)+index).getCellInst(), clkNet, rstNet );
//					
//					if(i==(width-1)) {
//						Module mac_peripheral = Utils.addNodeToDesign(d1, ConvolutionResources.MAC_P, DesignProperties.DCP_FOLDER_PATH, DesignProperties.MAC_P, 0, 0);
//						ModuleInst clb1_peripheral = d1.createModuleInst("mac_peripheral" + i+j+index, mac_peripheral);
//						clb1_peripheral.getCellInst().setCellType(mac_peripheral.getNetlist().getTopCell());
//
//						Module fifo = Utils.addNodeToDesign(d1, ConvolutionResources.FIFO, DesignProperties.DCP_FOLDER_PATH, DesignProperties.FIFO, 0, 0);
//						ModuleInst fifo1 = d1.createModuleInst("fifo" + i+j+index, fifo);
//						fifo1.getCellInst().setCellType(fifo.getNetlist().getTopCell());
//						array.addOutputModule(fifo1.getCellInst());
//						
//						connectMasterPESlaveFIFO(d1, topCell, clb1_peripheral.getCellInst(), fifo1.getCellInst(), clkNet, rstNet);
////						System.out.println("FIFO port connected");
//					}
					}
				count++;
//				System.out.println(d1.getModuleInstMap().toString());
			}
			}
		
	}
	
	
	
	public static void connectSystolicArrays(SystolicConfiguration array1, SystolicConfiguration array2, Design d1, EDIFNet clkNet, EDIFNet rstNet,  boolean buildBoth) {
		
		if(buildBoth)
			buildSystolicArray(array1.getWidth(), array1.getHeight(), array1.getIndex(), array1, d1, clkNet, rstNet);
		
		buildSystolicArray(array2.getWidth(), array2.getHeight(), array2.getIndex(), array2, d1, clkNet, rstNet);
		
		EDIFCell topCell = d1.getNetlist().getTopCell();
		
		
		for(int i=0; i<array2.getOutputs().size(); i++) {
			EDIFCellInst inputCell1 = array1.getInputs().get(i);
			EDIFCellInst outputCell2 = array2.getOutputs().get(i);
			
			for (int j=0; j<ConvolutionResources.SLAVE_PORTS_FIFO.length; j++) {
				for (int k = 0; k < outputCell2.getPort(ConvolutionResources.SLAVE_PORTS_FIFO[j]).getWidth(); k++) {
					String netName = inputCell1.getName() + "_"+ outputCell2.getName() + "_"
							+ ConvolutionResources.SLAVE_PORTS_FIFO[j] +j+ k+rand.nextLong();
					
					EDIFNet net = topCell.createNet(netName);
					net.createPortInst(ConvolutionResources.SLAVE_PORTS_FIFO[j], k, inputCell1);
					net.createPortInst(ConvolutionResources.MASTER_PORTS_FIFO[j], k, outputCell2);
				}
			}
		}
	}
	
	public static void buildAlexnet(Design d1) {
		EDIFCell topCell = d1.getNetlist().getTopCell();
		EDIFNet clkNet = topCell.createNet("clk");
		EDIFNet rstNet = topCell.createNet("srst");
		EDIFPort portClk = topCell.createPort("clk", EDIFDirection.INPUT, 1);
		EDIFPort portRst = topCell.createPort("srst", EDIFDirection.INPUT, 1);
		//Ports and nets creation
		new EDIFPortInst(portClk, clkNet);
		new EDIFPortInst(portRst, rstNet);
		
		SystolicConfiguration conf0 = new SystolicConfiguration(0, 79, 79);
		DesignProperties.layers.add(conf0);

		SystolicConfiguration conf1 = new SystolicConfiguration(1, 40, 40);
		DesignProperties.layers.add(conf1);

		SystolicConfiguration conf2 = new SystolicConfiguration(2, 39, 39);
		DesignProperties.layers.add(conf2);

		SystolicConfiguration conf3 = new SystolicConfiguration(3, 20, 20);
		DesignProperties.layers.add(conf3);

		SystolicConfiguration conf4 = new SystolicConfiguration(0, 11, 11);
		DesignProperties.layers.add(conf4);

		SystolicConfiguration conf5 = new SystolicConfiguration(1, 11, 11);
		DesignProperties.layers.add(conf5);

		SystolicConfiguration conf6 = new SystolicConfiguration(0, 5, 5);
		DesignProperties.layers.add(conf6);
		buildSystolicArray(5, 5, 0, conf6, d1, clkNet, rstNet);
		
//		for(int i=0; i<DesignProperties.layers.size()-1; i++) {
////			System.out.println("in for loop buildAlexnet i= "+i);
//			if(i==0)
//				connectSystolicArrays(DesignProperties.layers.get(i), DesignProperties.layers.get(i+1), d1, clkNet, rstNet, true);
//			else
//				connectSystolicArrays(DesignProperties.layers.get(i), DesignProperties.layers.get(i+1), d1, clkNet, rstNet, false);
//		}
		System.out.println("END for loop buildAlexnet ");
		
	}
	
	public static void buildVGG(ArrayList<SystolicConfiguration> model, Design d1) {
		for(int i=0; i<19; i++) {
			
		}
	}

	public static void main(String[] args) {
		Design d1 = new Design(DesignProperties.DESIGN_NAME, DesignProperties.DEVICE_NAME);
		d1.setAutoIOBuffers(false);
		
		
		try{
//			buildSystolicArray(3, 3, 0, d1);
			buildAlexnet(d1);
			d1.setDesignOutOfContext(true); //d1.setDesignAsOutOfContext();
			d1.getNetlist().exportEDIF(DesignProperties.DCP_FOLDER_PATH+"result.edif");
			d1.writeCheckpoint(DesignProperties.DCP_FOLDER_PATH+"result.dcp");
		}
		catch(Exception e) {
//			System.out.println("Exception encountered " + e.getCause());
			e.printStackTrace();
//			System.out.println(e.getMessage());
		}
	}

}
