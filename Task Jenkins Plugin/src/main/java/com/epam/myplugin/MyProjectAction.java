package com.epam.myplugin;

import hudson.model.ProminentProjectAction;

public class MyProjectAction implements ProminentProjectAction {

    @Override
    public String getIconFileName() {
        return "/images/jenkins.png";
    }

    @Override
    public String getDisplayName() {
        return "MyActionLink";
    }

    @Override
    public String getUrlName() {
        return "myactionpage";
    }
}
