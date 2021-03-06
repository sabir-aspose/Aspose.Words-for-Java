package com.aspose.words.examples.programming_documents.bookmarks;

import com.aspose.words.Bookmark;
import com.aspose.words.Document;
import com.aspose.words.examples.Utils;


public class GetAndSetBookmarkNameAndText
{
    /**
     * The main entry point for the application.
     */
    public static void main(String[] args) throws Exception
    {
        // ExStart:GetAndSetBookmarkNameAndText
        // The path to the documents directory.
        String dataDir = Utils.getDataDir(GetAndSetBookmarkNameAndText.class);

        Document doc = new Document(dataDir + "Bookmark.doc");
        // Use the indexer of the Bookmarks collection to obtain the desired bookmark.
        Bookmark bookmark = doc.getRange().getBookmarks().get("MyBookmark");

        // Get the name and text of the bookmark.
        String name = bookmark.getName();
        String text = bookmark.getText();

        // Set the name and text of the bookmark.
        bookmark.setName("RenamedBookmark");
        bookmark.setText("This is a new bookmarked text.");
       // ExEnd:GetAndSetBookmarkNameAndText
        System.out.println("\nBookmark name and text set successfully.");

    }

}