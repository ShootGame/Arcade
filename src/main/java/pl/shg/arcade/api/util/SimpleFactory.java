/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aleksander
 */
public abstract class SimpleFactory implements IFactory {
    private final List<Variable> variables;
    
    public SimpleFactory() {
        this.variables = new ArrayList<>();
    }
    
    public boolean canBuild() {
        boolean result = true;
        for (Variable var : this.getAllVariables()) {
            if (!var.isNullable() && !var.isSet()) {
                result = false;
                break;
            }
        }
        return result;
    }
    
    public boolean contains(String variable) {
        Validate.notNull(variable, "variable can not be null");
        return this.getVariable(variable) != null;
    }
    
    public Object get(String variable) {
        Validate.notNull(variable, "variable can not be null");
        Variable var = this.getVariable(variable);
        if (var != null) {
            return var.getValue();
        }
        return null;
    }
    
    public List<Variable> getAllVariables() {
        return this.variables;
    }
    
    public Variable getVariable(String name) {
        Validate.notNull(name, "name can not be null");
        for (Variable var : this.getAllVariables()) {
            if (var.getName().equals(name.toLowerCase())) {
                return var;
            }
        }
        return null;
    }
    
    public boolean isSet(String variable) {
        Validate.notNull(variable, "variable can not be null");
        Variable var = this.getVariable(variable);
        if (var != null) {
            return var.isSet();
        }
        return false;
    }
    
    public void register(String variable, boolean nullable) {
        this.register(variable, nullable, null);
    }
    
    public void register(String variable, boolean nullable, Class clazz) {
        Validate.notNull(variable, "variable can not be null");
        Variable var = this.getVariable(variable);
        if (var != null) {
            this.variables.add(new Variable(clazz, variable, nullable));
        }
    }
    
    public void set(String variable, Object value) {
        Validate.notNull(variable, "variable can not be null");
        Variable var = this.getVariable(variable);
        if (var != null) {
            var.setValue(value);
        }
    }
    
    public class Variable {
        private final Class clazz;
        private final String name;
        private final boolean notNull;
        private Object value;
        
        public Variable(Class clazz, String name, boolean notNull) {
            Validate.notNull(name, "name can not be null");
            this.clazz = clazz;
            this.name = name.toLowerCase();
            this.notNull = notNull;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Class getType() {
            return this.clazz;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public boolean isNullable() {
            return !this.notNull;
        }
        
        public boolean isSet() {
            return this.value != null;
        }
        
        public void setValue(Object obj) {
            this.value = obj;
        }
    }
}
