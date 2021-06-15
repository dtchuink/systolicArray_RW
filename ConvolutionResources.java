package convolution;

public class ConvolutionResources {

		
		public static final String[] SLAVE_PORTS_FIFO={/*node on top in the LinkBlaze topology*/
				"i_wr_en", "o_full", "i_wr_data"
		};
			
		public static final String[] MASTER_PORTS_FIFO={/*node at the bottom in the LinkBlaze topology*/
				 "o_empty",  "i_rd_en", "o_rd_data"
		};	
		
		public static final String[] OTHER_PORTS_FIFO={/*node at the bottom in the LinkBlaze topology*/
				"i_clk", "i_rst_sync"
		};	

		public static final String[] INPUTS_PORTS_MAC={/*node on top in the LinkBlaze topology*/
				"CLK", "A", "B","C", "SCLR"
		};
			
		public static final String[] OUTPUTs_PORTS_MAC={/*node at the bottom in the LinkBlaze topology*/
				"", "ACOUT", "", "P"
		};
		
		
		public static final String[] OUTPUTs_PORTS_MAC_PERIPHERAL={/*node on top in the LinkBlaze topology*/
				"s_ready", "result", "m_valid", "ACOUT"
		};
		
		public static final String[] SLAVE_PORTS_PROTOCOL={/*node on top in the LinkBlaze topology*/
				"s_valid", "s_ready", "A"
		};
			
		public static final String[] MASTER_PORTS_PROTOCOL={/*node at the bottom in the LinkBlaze topology*/
				"m_valid", "m_ready", "P"
		};
		public static final String MAC = "mac";
		public static final String MAC_P = "mac_peripheral";
		public static final String FIFO = "fifo";
		public static final String ADD = "add";
		public static final String CLK = "clk";

}
