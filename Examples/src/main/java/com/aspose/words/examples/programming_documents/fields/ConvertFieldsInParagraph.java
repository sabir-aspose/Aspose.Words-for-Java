package com.aspose.words.examples.programming_documents.fields;

import com.aspose.words.*;
import com.aspose.words.examples.Utils;

import java.util.ArrayList;


public class ConvertFieldsInParagraph {
    public static void main(String[] args) throws Exception {
        //ExStart:1
        // The path to the documents directory.
        String dataDir = Utils.getDataDir(ConvertFieldsInParagraph.class);

        Document doc = new Document(dataDir + "TestFile.doc");

        // Pass the appropriate parameters to convert all IF fields to static text that are encountered only in the last
        // paragraph of the document.
        FieldsHelper.convertFieldsToStaticText(doc.getFirstSection().getBody().getLastParagraph(), FieldType.FIELD_IF);

        // Save the document with fields transformed to disk.
        doc.save(dataDir + "output.doc");
        //ExEnd:1

        System.out.println("Converted fields in the paragraph with text successfully.");
    }

    //ExStart:2
    private static class FieldsHelper extends DocumentVisitor {
        private int mFieldDepth = 0;
        private ArrayList mNodesToSkip = new ArrayList();
        private int mTargetFieldType;

        private FieldsHelper(int targetFieldType) {
            mTargetFieldType = targetFieldType;
        }

        public static void convertFieldsToStaticText(CompositeNode compositeNode, int targetFieldType) throws Exception {
            String originalNodeText = compositeNode.toString(SaveFormat.TEXT); //ExSkip
            FieldsHelper helper = new FieldsHelper(targetFieldType);
            compositeNode.accept(helper);

            assert (originalNodeText.equals(compositeNode.toString(SaveFormat.TEXT))) : "Error: Text of the node converted differs from the original"; //ExSkip
            for (Node node : (Iterable<Node>) compositeNode.getChildNodes(NodeType.ANY, true)) //ExSkip
                assert (!(node instanceof FieldChar && ((FieldChar) node).getFieldType() == targetFieldType)) : "Error: A field node that should be removed still remains."; //ExSkip
        }

        public int visitFieldStart(FieldStart fieldStart) {
            // We must keep track of the starts and ends of fields incase of any nested fields.
            if (fieldStart.getFieldType() == mTargetFieldType) {
                mFieldDepth++;
                fieldStart.remove();
            } else {
                // This removes the field start if it's inside a field that is being converted.
                CheckDepthAndRemoveNode(fieldStart);
            }

            return VisitorAction.CONTINUE;
        }

        public int visitFieldSeparator(FieldSeparator fieldSeparator) {
            // When visiting a field separator we should decrease the depth level.
            if (fieldSeparator.getFieldType() == mTargetFieldType) {
                mFieldDepth--;
                fieldSeparator.remove();
            } else {
                // This removes the field separator if it's inside a field that is being converted.
                CheckDepthAndRemoveNode(fieldSeparator);
            }

            return VisitorAction.CONTINUE;
        }

        public int visitFieldEnd(FieldEnd fieldEnd) {
            if (fieldEnd.getFieldType() == mTargetFieldType)
                fieldEnd.remove();
            else
                CheckDepthAndRemoveNode(fieldEnd); // This removes the field end if it's inside a field that is being converted.

            return VisitorAction.CONTINUE;
        }

        public int visitRun(Run run) {
            // Remove the run if it is between the FieldStart and FieldSeparator of the field being converted.
            CheckDepthAndRemoveNode(run);

            return VisitorAction.CONTINUE;
        }

        public int visitParagraphEnd(Paragraph paragraph) {
            if (mFieldDepth > 0) {
                // The field code that is being converted continues onto another paragraph. We
                // need to copy the remaining content from this paragraph onto the next paragraph.
                Node nextParagraph = paragraph.getNextSibling();

                // Skip ahead to the next available paragraph.
                while (nextParagraph != null && nextParagraph.getNodeType() != NodeType.PARAGRAPH)
                    nextParagraph = nextParagraph.getNextSibling();

                // Copy all of the nodes over. Keep a list of these nodes so we know not to remove them.
                while (paragraph.hasChildNodes()) {
                    mNodesToSkip.add(paragraph.getLastChild());
                    ((Paragraph) nextParagraph).prependChild(paragraph.getLastChild());
                }

                paragraph.remove();
            }

            return VisitorAction.CONTINUE;
        }

        public int visitTableStart(Table table) {
            CheckDepthAndRemoveNode(table);

            return VisitorAction.CONTINUE;
        }

        /**
         * Checks whether the node is inside a field or should be skipped and then removes it if necessary.
         */
        private void CheckDepthAndRemoveNode(Node node) {
            if (mFieldDepth > 0 && !mNodesToSkip.contains(node))
                node.remove();
        }
    }
    //ExEnd:2

}