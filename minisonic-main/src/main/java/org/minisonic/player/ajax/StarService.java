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
package org.minisonic.player.ajax;

import org.minisonic.player.dao.MediaFileDao;
import org.minisonic.player.domain.User;
import org.minisonic.player.service.SecurityService;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides AJAX-enabled services for starring.
 * <p/>
 * This class is used by the DWR framework (http://getahead.ltd.uk/dwr/).
 *
 * @author Sindre Mehus
 */
@Service("ajaxStarService")
public class StarService {

    private static final Logger LOG = LoggerFactory.getLogger(StarService.class);

    @Autowired
    private SecurityService securityService;
    @Autowired
    private MediaFileDao mediaFileDao;

    public void star(int id) {
        mediaFileDao.starMediaFile(id, getUser());
    }

    public void unstar(int id) {
        mediaFileDao.unstarMediaFile(id, getUser());
    }

    private String getUser() {
        WebContext webContext = WebContextFactory.get();
        User user = securityService.getCurrentUser(webContext.getHttpServletRequest());
        return user.getUsername();
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setMediaFileDao(MediaFileDao mediaFileDao) {
        this.mediaFileDao = mediaFileDao;
    }
}