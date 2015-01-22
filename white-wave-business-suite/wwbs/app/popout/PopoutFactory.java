package app.popout;

import app.MainApplication;

public class PopoutFactory {
	
	/*
	 * Create a new popout with the type popoutID 
	 */
	public static IPopout createPopout(PopoutType type, String content) {
		if(type == PopoutType.NEWUSER)
			return new NewUserPopout(content);

		else if(type == PopoutType.EXPANDEDTICKET)
			return new ExpandedTicketPopout(content);
		
		else if(type == PopoutType.NEWCLIENT)
			return new NewClientPopout(content);

		else if(type == PopoutType.EXPANDEDCLIENT)
			return new ExpandedClientPopout(content);
		
		else if(type == PopoutType.NEWASSET)
			return new NewAssetPopout(content);
		
		else if(type == PopoutType.EXPANDEDASSET)
			return new ExpandedAssetPopout(content);
		
		else if(type == PopoutType.NEWSUPPORTEVENT)
			return new NewSupportEventPopout(content);
		
		else if(type == PopoutType.EXPANDEDSUPPORTEVENT)
			return new ExpandedSupportEventPopout(content);
		
		else if(type == PopoutType.NEWTICKET)
			return new NewTicketPopout(content);

		else if(type == PopoutType.INVOICEGENERATOR)
			return new InvoiceGeneratorPopout(content);
		
		else if(type == PopoutType.BASICSUPPORTEVENT)
			return new BasicSupportEventPopout(content);
		
		else {
			if(MainApplication.DEBUG_ON)
				System.err.println("Could not create new popout: No valid object for factory to build");
			return null;
		}
	}
}
