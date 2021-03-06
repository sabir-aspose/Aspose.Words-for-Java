
package com.aspose.words.examples.programming_documents.document;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.examples.Utils;


public class DocumentBuilderMoveToBookmark {
    public static void main(String[] args) throws Exception {
        //ExStart:1
        // The path to the documents directory.
        String dataDir = Utils.getDataDir(DocumentBuilderMoveToBookmark.class);

        // Open the document.
        Document doc = new Document(dataDir + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToBookmark("CoolBookmark");
        builder.writeln("This is a very cool bookmark.");

        doc.save(dataDir + "output.doc");
        //ExEnd:1
    }
}