package xmlManagement;

import java.util.HashMap;

import javafx.scene.paint.Color;

public abstract class StyleWriter {
	protected final String myDestinationFile;
	protected final String myGridShape;
	protected final String myGridEdge;
	protected final boolean myScrollable;
	protected final boolean myOutlined;
	protected final HashMap<Integer,Color> myColorMap=new HashMap<Integer,Color>();
	protected final HashMap<Integer,String> myImageMap=new HashMap<Integer,String>();

	public StyleWriter(String fileName,String gridShape,String gridEdge,boolean scrollable,boolean outlined)
	{
	myDestinationFile=fileName;
	myGridShape=gridShape;
	myGridEdge=gridEdge;
	myScrollable=scrollable;
	myOutlined=outlined;
	populateColorMap();
	populateImageMap();
	}
	
	public abstract void populateColorMap();
	
	public abstract void populateImageMap();
	
	public void writeStyleSheet(){
		
		
	}
	
}
