package com.aspose.words.examples.programming_documents.tables.ApplyFormatting;

import java.awt.Color;

import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.PreferredWidth;
import com.aspose.words.Table;
import com.aspose.words.examples.Utils;

public class SpecifyPreferredWidthOnACell {

	private static final String dataDir = Utils.getSharedDataDir(SpecifyAPreferredWidthOnATable.class) + "Tables/";

	public static void main(String[] args) throws Exception {
		// ExStart:SpecifyPreferredWidthOnACell
		Document doc = new Document();
		DocumentBuilder builder = new DocumentBuilder(doc);

		// Insert a table row made up of three cells which have different preferred widths.
		Table table = builder.startTable();

		// Insert an absolute sized cell.
		builder.insertCell();
		builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(40));
		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.RED);
		builder.writeln("Cell at 40 points width");

		// Insert a relative (percent) sized cell.
		builder.insertCell();
		builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(20));
		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.BLUE);
		builder.writeln("Cell at 20% width");

		// Insert a auto sized cell.
		builder.insertCell();
		builder.getCellFormat().setPreferredWidth(PreferredWidth.AUTO);
		builder.getCellFormat().getShading().setBackgroundPatternColor(Color.GREEN);
		builder.writeln("Cell automatically sized. The size of this cell is calculated from the table preferred width.");
		builder.writeln("In this case the cell will fill up the rest of the available space.");

		doc.save(dataDir + "Table.PreferredWidths Out.doc");
		// ExEnd:SpecifyPreferredWidthOnACell
	}

	public static void findPreferredWidthTypeAndValueOfATableOrCell(Table table) {
		// ExStart:FindPreferredWidthTypeAndValueOfATableOrCell
		Cell firstCell = table.getFirstRow().getFirstCell();
		int type = firstCell.getCellFormat().getPreferredWidth().getType();
		double value = firstCell.getCellFormat().getPreferredWidth().getValue();
		// ExEnd:FindPreferredWidthTypeAndValueOfATableOrCell
	}

	public static void allowAutoFit(Table table) throws Exception {
		// ExStart:AllowAutoFit
		table.setAllowAutoFit(true);
		// ExEnd:AllowAutoFit
	}

}
