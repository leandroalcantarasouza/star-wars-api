package br.com.leandro.starwarsapi.info;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.format.DateTimeFormatter;

@ConfigurationProperties(prefix = "build")
public class InfoConfigurationProperties {

    private String version;
    private String date;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateFormatted() {
        DateTimeFormatter inputDateTimeFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        DateTimeFormatter outputDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return outputDateTimeFormat.format(inputDateTimeFormat.parse(this.date));
    }
}
