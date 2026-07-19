package pl.dawidkaszuba.homebudget.controller;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AppVersionControllerAdvice {

    private final ObjectProvider<BuildProperties> buildProperties;

    public AppVersionControllerAdvice(ObjectProvider<BuildProperties> buildProperties) {
        this.buildProperties = buildProperties;
    }

    @ModelAttribute("appVersion")
    public String appVersion() {
        BuildProperties properties = buildProperties.getIfAvailable();
        return properties != null ? properties.getVersion() : null;
    }
}
