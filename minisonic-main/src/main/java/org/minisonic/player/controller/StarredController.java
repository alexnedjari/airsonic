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

import org.minisonic.player.dao.MediaFileDao;
import org.minisonic.player.domain.*;
import org.minisonic.player.service.MediaFileService;
import org.minisonic.player.service.PlayerService;
import org.minisonic.player.service.SecurityService;
import org.minisonic.player.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for showing a user's starred items.
 *
 * @author Sindre Mehus
 */
@Controller
@RequestMapping("/starred")
public class StarredController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private MediaFileDao mediaFileDao;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private MediaFileService mediaFileService;

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();

        User user = securityService.getCurrentUser(request);
        String username = user.getUsername();
        UserSettings userSettings = settingsService.getUserSettings(username);
        List<MusicFolder> musicFolders = settingsService.getMusicFoldersForUser(username);

        List<MediaFile> artists = mediaFileDao.getStarredDirectories(0, Integer.MAX_VALUE, username, musicFolders);
        List<MediaFile> albums = mediaFileDao.getStarredAlbums(0, Integer.MAX_VALUE, username, musicFolders);
        List<MediaFile> files = mediaFileDao.getStarredFiles(0, Integer.MAX_VALUE, username, musicFolders);
        mediaFileService.populateStarredDate(artists, username);
        mediaFileService.populateStarredDate(albums, username);
        mediaFileService.populateStarredDate(files, username);

        List<MediaFile> songs = new ArrayList<>();
        List<MediaFile> videos = new ArrayList<>();
        for (MediaFile file : files) {
            (file.isVideo() ? videos : songs).add(file);
        }

        map.put("user", user);
        map.put("partyModeEnabled", userSettings.isPartyModeEnabled());
        map.put("player", playerService.getPlayer(request, response));
        map.put("coverArtSize", CoverArtScheme.MEDIUM.getSize());
        map.put("artists", artists);
        map.put("albums", albums);
        map.put("songs", songs);
        map.put("videos", videos);
        return new ModelAndView("starred","model",map);
    }

}
