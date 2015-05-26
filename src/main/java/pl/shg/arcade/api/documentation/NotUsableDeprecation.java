/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.documentation;

/**
 *
 * @author Aleksander
 */
@Docs
public class NotUsableDeprecation implements IDeprectation {
    private Reason reason;
    
    public NotUsableDeprecation() {}
    
    public NotUsableDeprecation(Reason reason) {
        this.reason = reason;
    }
    
    @Override
    public String getHTML() {
        String text = "Ten moduł nie jest zdatny do konfiguracji";
        if (this.hasReason()) {
            return text + ": " + this.getReason();
        }
        return text + ".";
    }
    
    public Reason getReason() {
        return this.reason;
    }
    
    public boolean hasReason() {
        return this.reason != null;
    }
    
    public void setReason(Reason reason) {
        this.reason = reason;
    }
    
    public enum Reason {
        AUTO_LOAD("plugin Arcade ładuje ten moduł się automatycznie na każdej mapie."),
        BUG("moduł nie działa prawidłowo. Nasi programiści pracują nad jego naprawą."),
        OLD("moduł jest przestarzały. Użyj innego, nowszego modułu.");
        
        private final String reason;
        
        private Reason(String reason) {
            this.reason = reason;
        }
        
        @Override
        public String toString() {
            return this.reason;
        }
    }
}
