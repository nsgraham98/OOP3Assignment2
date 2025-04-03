package implementations;

import java.io.*;
import java.util.regex.*;
import exceptions.EmptyQueueException;
import java.util.EmptyStackException;
import implementations.MyStack;
import implementations.MyQueue;

public class XMLParser {
    private MyStack<String> tagStack;
    private MyQueue<String> errorQueue;
    
    public XMLParser() {
        tagStack = new MyStack<>();  
        errorQueue = new MyQueue<>();   
    }

    public void parseXML(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber=1;
            
            while ((line = reader.readLine()) != null) {
                processLine(line, lineNumber);
                lineNumber++;  
            }

            while (!tagStack.isEmpty()) {
                errorQueue.enqueue("Unclosed tag: <" + tagStack.pop() + ">");
            }

            printErrors();
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }

    private void processLine(String line, int lineNumber) {
        Pattern tagPattern = Pattern.compile("</?([a-zA-Z0-9]+)(/?)>");
        Matcher matcher = tagPattern.matcher(line);

        while (matcher.find()) {
            String tagName = matcher.group(1);
            boolean isClosingTag = line.charAt(matcher.start() + 1) == '/';
            boolean isSelfClosing = "/".equals(matcher.group(2));

            if (isClosingTag) {
                try {
                    if (!tagStack.isEmpty() && tagStack.peek().equals(tagName)) {
                        tagStack.pop();
                    } else {
                        errorQueue.enqueue("Mismatched closing tag </" + tagName + "> at line " + lineNumber);
                    }
                } catch (EmptyStackException e) {  
                    errorQueue.enqueue("Unexpected closing tag </" + tagName + "> at line " + lineNumber);
                }
            } else if (!isSelfClosing) {
                tagStack.push(tagName);
            }
        }
    }

    private void printErrors() {
        if (errorQueue.isEmpty()) {
            System.out.println("XML is valid");
        } else {
            System.out.println("Errors found:");
            while (!errorQueue.isEmpty()) {
                try {
                    System.out.println(" - " + errorQueue.dequeue());
                } catch (EmptyQueueException e) {  
                    System.err.println("Queue issue");
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java XMLParser <filename>");
            return;
        }

        String filePath = "res/" + args[0];
        XMLParser parser = new XMLParser();
        parser.parseXML(filePath);
    }
}
