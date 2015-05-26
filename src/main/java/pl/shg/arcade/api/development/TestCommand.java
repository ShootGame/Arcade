/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.development;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.CommandExecutor;
import pl.shg.arcade.api.command.CommandTest;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class TestCommand extends Command {
    private static final List<Test> tests = new ArrayList<>();
    
    public TestCommand() {
        super(new String[] {"test", "try"},
                Development.COMMAND_PREFIX + "Testuj funkcje pluginu Arcade", "test [-l]", new char[] {'l'});
        this.setOption("l", "Wyswietl liste dostepnych testów");
        this.setPermission("arcade.command.test");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 0 || args[0].equals("-l")) {
            this.printList(sender);
        } else {
            for (Test test : getTests()) {
                if (test.getName().equals(args[0].toLowerCase())) {
                    this.performTest(sender, args, test);
                    return;
                }
            }
            
            sender.sendError("Nie znaleziono zadnego testu o nazwie " + args[0] + ".");
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private void performTest(Sender sender, String[] args, Test test) throws CommandException {
        if (!test.isConsoleAllowed() && sender.isConsole()) {
            sender.sendError("Nie mozna wykonac tego testu z poziomu konsoli.");
        } if (args.length < test.minArguments()) {
            sender.sendError("Podano zbyt malo argumentów do wykonania testu.");
        } else {
            try {
                long millis = System.currentTimeMillis();
                test.execute(sender, args);
                sender.sendError("Test zostal wykonany w " + (System.currentTimeMillis() - millis) + " milisekund.");
            } catch (CommandException ex) {
                sender.sendError(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                sender.sendError("Nie udalo sie wykonac testu " + test.getName() + " poniewaz wykryto naganny blad.");
            }
        }
    }
    
    private void printList(Sender sender) {
        List<Test> list = getTests();
        
        if (list.isEmpty()) {
            sender.sendError("Obecnie brak mozliwych testów do wykonania.");
        } else {
            sender.sendMessage(Command.getTitle("Lista testów", Color.GRAY + "(" + list.size() + ")"));
            for (Test test : list) {
                String usage = test.getUsage();
                if (usage == null) {
                    usage = "";
                } else {
                    usage = " " + usage;
                }
                
                String desc = test.getDescription();
                if (desc == null) {
                    desc = Color.ITALIC + "Brak dostepnego opisu.";
                }
                
                sender.sendMessage(Color.GREEN + test.getName() + usage + Color.YELLOW + " - " + desc);
            }
            
            sender.sendMessage(Color.DARK_AQUA + "Wykonaj test uzywajac komendy /test <test>.");
        }
    }
    
    public static List<Test> getTests() {
        return tests;
    }
    
    public static void register(Test test) {
        Validate.notNull(test, "test can not be null");
        tests.add(test);
    }
    
    public static void registerDefaults() {
        if (!getTests().isEmpty()) {
            throw new UnsupportedOperationException("Could not set default tests twice");
        }
        
        new CommandTest().register();
    }
    
    public static abstract class Test implements CommandExecutor {
        private final String name;
        private final String usage;
        private final boolean console;
        private final String description;
        
        public Test(String name, String usage, boolean console, String description) {
            Validate.notNull(name, "name can not be null");
            this.name = name.toLowerCase();
            this.usage = usage;
            this.console = console;
            this.description = description;
        }
        
        @Override
        public int minArguments() {
            return 1;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String getUsage() {
            return this.usage;
        }
        
        public String getDescription() {
            return this.description;
        }
        
        public boolean isConsoleAllowed() {
            return this.console;
        }
        
        public void register() {
            TestCommand.register(this);
        }
    }
}
