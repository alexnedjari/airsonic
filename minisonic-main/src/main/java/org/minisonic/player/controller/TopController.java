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
package org.minisonic.player.controller;

import org.minisonic.player.domain.AvatarScheme;
import org.minisonic.player.domain.User;
import org.minisonic.player.domain.UserSettings;
import org.minisonic.player.service.SecurityService;
import org.minisonic.player.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the top frame.
 *
 * @author Sindre Mehus
 */
@Controller
@RequestMapping("/top")
public class TopController {

    @Autowired
    private SettingsService settingsService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();

        User user = securityService.getCurrentUser(request);
        UserSettings userSettings = settingsService.getUserSettings(user.getUsername());

        map.put("user", user);
        map.put("showSideBar", userSettings.isShowSideBar());
        map.put("showAvatar", userSettings.getAvatarScheme() != AvatarScheme.NONE);
        return new ModelAndView("top","model", map);
    }
}
