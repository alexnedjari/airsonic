/*
 This file is part of Minisonic.

 Minisonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Minisonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Minisonic.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2016 (C) Minisonic Authors
 Based upon Minisonic, Copyright 2009 (C) Sindre Mehus
 */
package org.minisonic.player.theme;

import org.minisonic.player.domain.Theme;
import org.minisonic.player.service.SettingsService;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;

/**
 * Theme source implementation which uses two resource bundles: the
 * theme specific (e.g., barents.properties), and the default (default.properties).
 *
 * @author Sindre Mehus
 */
public class CustomThemeSource extends ResourceBundleThemeSource {

    private SettingsService settingsService;
    private String basenamePrefix;

    @Override
    protected MessageSource createMessageSource(String basename) {
        ResourceBundleMessageSource messageSource = (ResourceBundleMessageSource) super.createMessageSource(basename);

        // Create parent theme recursively.
        for (Theme theme : settingsService.getAvailableThemes()) {
            if (basename.equals(basenamePrefix + theme.getId()) && theme.getParent() != null) {
                String parent = basenamePrefix + theme.getParent();
                messageSource.setParentMessageSource(createMessageSource(parent));
                break;
            }
        }
        return messageSource;
    }

    @Override
    public void setBasenamePrefix(String basenamePrefix) {
        this.basenamePrefix = basenamePrefix;
        super.setBasenamePrefix(basenamePrefix);
    }

    public void setSettingsService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }
}
