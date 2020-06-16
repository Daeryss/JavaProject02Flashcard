package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main2(String[] args) throws IOException {
        String[] arr = {"-export", "capitals3.txt"};
        main2(arr);
    }

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        justClass flash = new justClass();
        if (args.length != 0) {
            for (int i = 0; i < args.length; i += 2) {
                String path = args[i + 1];
                switch (args[i]) {
                    case "-import":
                        flash.importCards(path);
                        break;
                    case "-export":
                        flash.exportFromMain(path);
                        break;
                    default:
                        break;
                }
            }
        }
        String input = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
        flash.setLogs(input);
        System.out.println(input);
        String action = s.nextLine();
        flash.setLogs(action);
        while (!action.equalsIgnoreCase("exit")){
            switch (action){
                case "add":
                    flash.addKey();
                    break;
                case "remove":
                    flash.remove();
                    break;
                case "import":
                    System.out.println("File name:");
                    flash.setLogs("File name:");
                    String path = s.nextLine();
                    flash.importCards(path);
                    break;
                case "export":
                    flash.setLogs("File name:");
                    System.out.println("File name:");
                    String pathExport = s.nextLine();
                    flash.export(pathExport);
                    break;
                case "log":
                    flash.log();
                    break;
                case "hardest card":
                    flash.hardestCard();
                    break;
                case "reset stats":
                    flash.resetStets();
                    break;
                case "ask":
                    flash.ask();
                    break;
                default:
                    break;
            }
            flash.setLogs(input);
            System.out.println(input);
            action = s.nextLine();
            flash.setLogs(action);
            if (action.equals("")){
                action = s.nextLine();
            }
        }
        System.out.println("Bye bye!");
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-export")) {
                flash.export(args[i + 1]);
                break;
            }
        }
    }
}

class justClass {
    private Map<String, String> card = new HashMap<>();
    private Map<String, Integer> count = new HashMap<>();
    private ArrayList<String> logs = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    File temp = new File("temporary.txt");

    public void addKey() {
        setLogs("The card:");
        System.out.println("The card:");
        String newKey = scanner.nextLine();
        setLogs(newKey);
        StringBuilder str = new StringBuilder();
        if (card.containsKey(newKey)) {
            str.append("The card \"");
            str.append(newKey);
            str.append("\" already exists.");
            String temp = String.valueOf(str);
            setLogs(temp);
            System.out.println(str);
        } else {
            setLogs("The definition of the card:");
            System.out.println("The definition of the card:");
            String value = scanner.nextLine();
            setLogs(value);
            addValue(newKey, value);
        }
    }

    private void addValue(String key, String value) {
        StringBuilder str = new StringBuilder();
        if (card.containsValue(value)) {
            str.append("The definition \"");
            str.append(value);
            str.append("\" already exists.");
        } else {
            card.put(key, value);
            count.put(key, 0);
            str.append("The pair (\"");
            str.append(key);
            str.append("\":\"");
            str.append(value);
            str.append("\") has been added.");
        }
        String temp = String.valueOf(str);
        setLogs(temp);
        System.out.println(str);
    }

    public void remove () {
        setLogs("The card:");
        System.out.println("The card:");
        String key = scanner.nextLine();
        setLogs(key);
        if (card.containsKey(key)) {
            count.remove(key);
            card.remove(key);
            setLogs("The card has been removed.");
            System.out.println("The card has been removed.");
        } else {
            StringBuilder str = new StringBuilder();
            str.append("Can't remove \"");
            str.append(key);
            str.append("\": there is no such card.");
            String temp = String.valueOf(str);
            setLogs(temp);
            System.out.println(str);
        }
    }

    public void importCards (String path) throws IOException {
        setLogs(path);
        File file  = new File(path);
        int numOfCards = 0;
        try (Scanner readFile = new Scanner(file)) {
            while (readFile.hasNext()) {
                String key = readFile.nextLine().trim();
                String value = readFile.nextLine().trim();
                int errors = Integer.parseInt(readFile.nextLine().trim());
                if (card.containsKey(key)) {
                    card.replace(key, value);
                    count.replace(key, errors);
                } else {
                    card.put(key, value);
                    count.put(key, errors);
                }
                numOfCards++;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        StringBuilder str = new StringBuilder();
        str.append(numOfCards);
        str.append(" cards have been loaded.");
        String temp = String.valueOf(str);
        setLogs(temp);
        System.out.println(str);
    }

    public void export (String path) throws IOException {
        setLogs(path);
        File file = new File(path);
        int numOfCards = 0;
        try (PrintWriter print = new PrintWriter(file)){
            for (String str : card.keySet()) {
                print.println(str);
                print.println(card.get(str));
                print.println(String.valueOf(count.get(str)));
                numOfCards++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        StringBuilder str = new StringBuilder();
        str.append(numOfCards);
        str.append(" cards have been saved.");
        String temp = String.valueOf(str);
        setLogs(temp);
        System.out.println(str);
    }

    public void exportFromMain (String path) throws IOException {
        setLogs(path);
        File file = new File(path);
        int numOfCards = 0;
        try (PrintWriter print = new PrintWriter(file)){
            for (String str : card.keySet()) {
                print.println(str);
                print.println(card.get(str));
                print.println(String.valueOf(count.get(str)));
                numOfCards++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public void resetStets () {
        for (String str : count.keySet()) {
            count.replace(str, 0);
        }
        setLogs("Card statistics has been reset.");
        System.out.println("Card statistics has been reset.");
    }

    public void log () throws IOException {
        setLogs("File name:");
        System.out.println("File name:");
        String path = scanner.nextLine();
        setLogs(path);
        File file = new File(path);
        try (PrintWriter print = new PrintWriter(file)){
            for (String str : logs) {
                print.println(str);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        setLogs("The log has been saved.");
        System.out.println("The log has been saved.");
    }

    public void setLogs (String str) {
        logs.add(str);
    }

    public void hardestCard () {
        int max = 0;
        for (String str : count.keySet()) {
            if (count.get(str) > max) {
                max  = count.get(str);
            }
        }
        StringBuilder hardestCard = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        if (max == 0) {
            list.add("There are no cards with errors.");
        } else {
            for (String str : count.keySet()) {
                if (count.get(str) == max) {
                    list.add(str);
                }
            }
        }
        if (list.size() == 1) {
            if (list.get(0).equals("There are no cards with errors.")) {
                hardestCard.append("There are no cards with errors.");
            } else {
                hardestCard.append("The hardest card is ");
                hardestCard.append("\"");
                hardestCard.append(list.get(0));
                hardestCard.append("\"");
                hardestCard.append(". You have ");
                hardestCard.append(max);
                hardestCard.append(" errors answering it.");
            }
        } else {
            hardestCard.append("The hardest cards are ");
            for (String str : list) {
                hardestCard.append("\"");
                hardestCard.append(str);
                hardestCard.append("\", ");
            }
            int x = hardestCard.length();
            hardestCard.deleteCharAt(x - 1);
            hardestCard.deleteCharAt(x - 2);
            hardestCard.append(". You have ");
            hardestCard.append(max);
            hardestCard.append(" errors answering them.");
        }
        System.out.println(hardestCard);
        String temporary = String.valueOf(hardestCard);
        setLogs(temporary);
    }

    private String findKey (String values) {
        for (String str : card.keySet()) {
            if (values.equalsIgnoreCase(card.get(str))) {
                return str;
            }
        }
        return null;
    }

    private void definition (String string) {
        StringBuilder str = new StringBuilder();
        str.append("Print the definition of \"");
        str.append(string);
        str.append("\":");
        String newStr = String.valueOf(str);
        setLogs(newStr);
        System.out.println(newStr);
    }

    public void ask () {
        setLogs("How many times to ask?");
        System.out.println("How many times to ask?");
        int num = Integer.parseInt(scanner.nextLine());
        setLogs(String.valueOf(num));
        String[] list = card.keySet().toArray(new String[0]);
        int counts = 0;
        int index = 0;
        while (counts < num) {
            StringBuilder str = new StringBuilder();
            index = index >= list.length ? 0 : index;
            definition(list[index]);
            String answer = scanner.nextLine();
            setLogs(answer);
            if (answer.equalsIgnoreCase(card.get(list[index]))) {
                setLogs("Correct answer.");
                System.out.println("Correct answer.");
            } else {
                int x = count.get(list[index]) + 1;
                count.replace(list[index], x);
                if (card.containsValue(answer)) {
                    String value = findKey(answer);
                    str.append("Wrong answer. The correct one is \"");
                    str.append(card.get(list[index]));
                    str.append("\", you've just written the definition of \"");
                    str.append(value);
                    str.append("\".");
                } else {
                    str.append("Wrong answer. The correct one is \"");
                    str.append(card.get(list[index]));
                    str.append("\".");
                }
                String temporary = String.valueOf(str);
                setLogs(temporary);
                System.out.println(str);
            }
            index++;
            counts++;
        }
    }
}
