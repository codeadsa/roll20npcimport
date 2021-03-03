package org.pf1etools.roll20npc.paste;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class CopyAndPasteTransfer
{
	public CopyAndPasteTransfer()
	{
		String line1 = "This is a test";
		StringSelection ss = new StringSelection(line1);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
		String line2 = "Line 2 is here";
	}
	public static void main(String argsp[])
	{
		CopyAndPasteTransfer asdf = new CopyAndPasteTransfer();
	}
}
