package com.benjamin.projet_android;

public class Language {
    private String id;
    private String name;
    private String flag;

    public Language(String id, String name) {
        this.id = id;
        this.name = name;
        switch (id) {
            case "BG":
                this.flag = "🇧🇬";
                break;
            case "CS":
                this.flag = "🇨🇿";
                break;
            case "DA":
                this.flag = "🇩🇰";
                break;
            case "DE":
                this.flag = "🇩🇪";
                break;
            case "EL":
                this.flag = "🇬🇷";
                break;
            case "EN":
                this.flag = "🇬🇧";
                break;
            case "ES":
                this.flag = "🇪🇸";
                break;
            case "ET":
                this.flag = "🇪🇪";
                break;
            case "FI":
                this.flag = "🇫🇮";
                break;
            case "FR":
                this.flag = "🇫🇷";
                break;
            case "HU":
                this.flag = "🇭🇺";
                break;
            case "ID":
                this.flag = "🇲🇨";
                break;
            case "IT":
                this.flag = "🇮🇹";
                break;
            case "JA":
                this.flag = "🇯🇵";
                break;
            case "KO":
                this.flag = "🇰🇷";
                break;
            case "LT":
                this.flag = "🇱🇹";
                break;
            case "LV":
                this.flag = "🇱🇻";
                break;
            case "NB":
                this.flag = "🇳🇴";
                break;
            case "NL":
                this.flag = "🇳🇱";
                break;
            case "PL":
                this.flag = "🇵🇱";
                break;
            case "PT":
                this.flag = "🇵🇹";
                break;
            case "RO":
                this.flag = "🇷🇴";
                break;
            case "RU":
                this.flag = "🇷🇺";
                break;
            case "SK":
                this.flag = "🇸🇰";
                break;
            case "SL":
                this.flag = "🇸🇮";
                break;
            case "SV":
                this.flag = "🇸🇪";
                break;
            case "TR":
                this.flag = "🇹🇷";
                break;
            case "UK":
                this.flag = "🇺🇦";
                break;
            case "ZH":
                this.flag = "🇨🇳";
                break;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return flag + " " + name;
    }
}
