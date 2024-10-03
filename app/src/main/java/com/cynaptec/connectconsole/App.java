package com.cynaptec.connectconsole;

import com.cynaptec.connect.ConnectFourPlayingServiceFactory;

public class App 
{
    public static void main( String[] args )
    {
    	ConnectFourPlayingServiceFactory modelFactory = new ConnectFourPlayingServiceFactory();
    	ConnectFourTextStreamView view = new ConnectFourTextStreamView();
		
		ConnectFourTextStreamController controller = new ConnectFourTextStreamController(modelFactory, view);
		controller.main();
	}
}
