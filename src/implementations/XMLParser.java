/**
 * A parser for XML files that validates the structure of XML tags.
 * The parser checks for properly nested and matching tags, and reports
 * any errors found during parsing.
 */
package implementations;

import java.io.*;
import java.util.regex.*;
import exceptions.EmptyQueueException;
import utilities.Iterator;

public class XMLParser {
	
    private MyStack<TagEntry> tagStack;
    private MyQueue<TagEntry> errorQueue;
    private MyQueue<TagEntry> extrasQueue;
    private static boolean isErrors;
    
    /**
     * Constructs a new XMLParser with empty stacks and queues.
     */
    public XMLParser() {
        tagStack = new MyStack<>();  
        errorQueue = new MyQueue<>();   
        extrasQueue = new MyQueue<>();
        isErrors = false;
    }
    
    /**
     * Represents an XML tag entry with its full text, name, and line number.
     */
    class TagEntry {
        private String fullTag;
        private String name;
        private int lineNo;
        
        /**
         * Constructs a new TagEntry with the specified parameters.
         * 
         * @param fullTag the complete text of the XML tag
         * @param tagName the name of the XML tag
         * @param lineNo the line number where the tag appears
         */
        public TagEntry(String fullTag, String tagName, int lineNo) {
            this.fullTag = fullTag;
            this.name = tagName;
            this.lineNo = lineNo;
        }
        public String getFullTag() {
            return fullTag;
        }
        public String getName() {
            return name;
        }
        public int getLineNo() {
            return lineNo;
        }
    }
    
    /**
     * Main method for command-line execution of the XML parser.
     * 
     * @param args command-line arguments (expects exactly one filename argument)
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java XMLParser <filename>");
            return;
        }

        String filePath = "res/" + args[0];
        XMLParser parser = new XMLParser();
        parser.parseXML(filePath);
        printIsErrors();
    }
    
    /**
     * Parses the XML file at the specified path and validates its structure.
     * Reports any errors found during parsing, including mismatched tags,
     * improperly nested tags, and malformed tag syntax.
     * 
     * @param filePath the path to the XML file to parse
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void parseXML(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber=1;
            
            // reading/processing one line by calling processLine() method
            while ((line = reader.readLine()) != null) {
                processLine(line, lineNumber);
                lineNumber++;  
            }

            // Repeats until both queues are empty
            while (!errorQueue.isEmpty() || !extrasQueue.isEmpty()) {
                // If stack is not empty, pop each E into errorQ
                while (!tagStack.isEmpty()) {
                    errorQueue.enqueue(tagStack.pop());
                }
                
                // If either queue is empty (but not both), report each E in both queues as error
                if ( errorQueue.isEmpty() ^ extrasQueue.isEmpty()) {
                    try {
                        while (!errorQueue.isEmpty()) {
                            printError(errorQueue.dequeue());
                        }
                    } catch (EmptyQueueException e) { } // catch should never execute, it's needed to handle EmptyQueueException        
                    try {
                        while (!extrasQueue.isEmpty()) {
                            printError(extrasQueue.dequeue());
                        }
                    } catch (EmptyQueueException e) { } // catch should never execute, it's needed to handle EmptyQueueException    
                }    
                
                // If both queues are not empty, peek both queues
                if (!errorQueue.isEmpty() && !extrasQueue.isEmpty()) {
                    try {
                        // If they don't match, dequeue from errorQ and report as error
                        if (!errorQueue.peek().getName().equals(extrasQueue.peek().getName() )) {
                            printError(errorQueue.dequeue());
                        }
                        // Else dequeue from both
                        else {
                            errorQueue.dequeue();
                            extrasQueue.dequeue();
                        }
                    } catch (EmptyQueueException e) { } // catch should never execute, it's needed to handle EmptyQueueException
                }
            }    
            
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }

    /**
     * Processes a single line of XML, extracting and validating tags.
     * Uses our MyStack.java and MyQueue.java implementations as its data structures
     * The stacks and queues consist of TagEntry objects
     * 
     * @param line the line of text to process
     * @param lineNumber the line number in the source file
     */
    private void processLine(String line, int lineNumber) {
        Pattern tagPattern = Pattern.compile("<\\s*/?([a-zA-Z0-9]+)([^>]*)\\s*/?>");
        Matcher matcher = tagPattern.matcher(line);

        while (matcher.find()) {        
            String fullTag = matcher.group(0);
            String name = matcher.group(1);
            TagEntry tag = new TagEntry(fullTag, name, lineNumber); 
            
            boolean isClosingTag = line.charAt(matcher.start() + 1) == '/';
            boolean isSelfClosing = matcher.group(0).contains("/>");

            if (isClosingTag) {
                try { 
                    // If matches top of stack, pop stack and all is well
                    if (!tagStack.isEmpty() && tagStack.peek().getName().equals(tag.getName())) {
                        tagStack.pop();
                    } 
                    // Else if matches head of errorQ, dequeue and ignore 
                    else if (!errorQueue.isEmpty() && tag.getName().equals(errorQueue.peek().getName())) {
                        printError(errorQueue.dequeue());
                    }
                    // Else if stack is empty, add to errorQ
                    else if (tagStack.isEmpty()) {
                        errorQueue.enqueue(tag);
                        isErrors = true;
                    }
                    else {
                        // Search stack for matching Start_Tag using MyStack.java's iterator()
                        Iterator<TagEntry> tagStackIterator = tagStack.iterator();                    
                        boolean containsMatch = false;         
                        
                        while (tagStackIterator.hasNext()) {
                            TagEntry containsEntry = tagStackIterator.next();
                            
                            if (containsEntry.getName().equals(tag.getName())) {
                                containsMatch = true;
                                break;
                            }
                        }
                        
                        // If stack has match, iterates again
                        if (containsMatch) {
                            Iterator<TagEntry> matchesTagStackIterator = tagStack.iterator();
                            while (matchesTagStackIterator.hasNext()) {
                                TagEntry entry = matchesTagStackIterator.next();
                                
                                if (entry.getName().equals(tag.getName())) {
                                    tagStack.pop();
                                    break;
                                }
                                // Pop each E from stack into errorQ until match, report as error
                                else {                                
                                    errorQueue.enqueue(tagStack.pop());
                                    isErrors = true;
                                }
                            }
                        }                    
                        // If no match, add E to extrasQ
                        if (!containsMatch) {
                            extrasQueue.enqueue(tag);
                            isErrors = true;
                        }
                    }
                } catch (EmptyQueueException e) { } // catch should never execute, it's needed to handle EmptyQueueException    

            // If Start_Tag
            } else if (!isSelfClosing) {
                // Push on stack
                tagStack.push(tag);
            }
            // If Self_Closing_Tag
            // Ignore
        }
    }
    
    /**
     * Prints a formatted error message for a malformed tag.
     * Includes the line no. and the full text of the XML tag.
     * 
     * @param tag the TagEntry containing the error
     */
    private void printError(TagEntry tag) {
        System.out.println("Error at line: " + tag.getLineNo() + " " + tag.getFullTag() + " is not constructed correctly.");
    }
    
    /**
     * Prints whether any errors were found during parsing.
     */
    private static void printIsErrors() {
        if (isErrors) {
            System.out.print("You have errors! Boooooo!!");
        }
        else {
            System.out.print("XML is constructed correctly. Woohoo!");
        }
    }
}